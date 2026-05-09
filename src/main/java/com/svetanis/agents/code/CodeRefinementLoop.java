package com.svetanis.agents.code;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableMap.copyOf;

import java.util.Map;

import com.google.adk.agents.Callbacks;
import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.LoopAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.ExitLoopTool;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;

import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Provider;

public class CodeRefinementLoop implements Provider<LoopAgent> {

  private static final int MAX_ITER = 3;
  private static final String CRA_KEY = "code.review.agent";
  private static final String CFA_KEY = "code.refiner.agent";
  private static final String GEN_CODE = "generated_code";
  public static final String FOP_KEY = "final_output";

  private static final String DESC = """
      Improves code performance based on review feedback or signals completion.
      """;

  public CodeRefinementLoop(AgentTool execTool, Map<String, AgentConfig> configs) {
    this.execTool = checkNotNull(execTool);
    this.configs = copyOf(configs);
  }

  private final AgentTool execTool;
  private final ImmutableMap<String, AgentConfig> configs;

  @Override
  public LoopAgent get() {
    LlmAgent review = llmAgent(CRA_KEY, execTool);
    LlmAgent refiner = llmAgent(CFA_KEY, execTool, ExitLoopTool.INSTANCE);
    return LoopAgent.builder() //
        .name("CodeRefinementLoop") //
        .description(DESC) //
        .subAgents(review, refiner) //
        .maxIterations(MAX_ITER) //
        .afterAgentCallback(publishFinalFromState(GEN_CODE, FOP_KEY))//
        .build();
  }

  private LlmAgent llmAgent(String key, BaseTool... tools) {
    AgentConfig config = configs.get(key);
    AgentContext ctx = AgentContext.builder() //
        .withConfig(config) //
        .withTools(tools) //
        .build();
    return new LlmAgentProvider(ctx).get();
  }

  private Callbacks.AfterAgentCallback publishFinalFromState(String src, String target) {
    return callbackContext -> {
      Object stateValue = callbackContext.invocationContext().session().state().get(src);
      if (stateValue instanceof String refinedCode) {
        String trimmed = refinedCode.trim();
        if (!trimmed.isBlank()) {
          callbackContext.invocationContext().session().state().put(target, trimmed);
        }
      }
      return Maybe.empty();
    };
  }

}
