package com.svetanis.agents.code;

import static com.google.api.client.util.Preconditions.checkNotNull;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.LoopAgent;
import com.google.adk.agents.SequentialAgent;
import com.google.adk.tools.AgentTool;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentConfigsProvider;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;
import com.svetanis.agents.base.tools.CodeExecutionToolProvider;

import jakarta.inject.Provider;

public class CodeRootAgent implements Provider<LlmAgent> {

  private static final String CRA_KEY = "code.root.agent";
  private static final String CGA_KEY = "code.generator.agent";
  private static final String CTA_KEY = "code.converter.agent";
  private static final String CCA_KEY = "code.critic.agent";
  private static final String CFA_KEY = "code.refactor.agent";
  private static final String CBA_KEY = "code.bundler.agent";

  public CodeRootAgent(AgentConfigsProvider provider) {
    this.provider = checkNotNull(provider, "provider");
  }

  private final AgentConfigsProvider provider;

  @Override
  public LlmAgent get() {
    Map<String, AgentConfig> configs = provider.get();
    AgentTool generator = AgentTool.create(generationWorkflow(configs));
    AgentTool converter = AgentTool.create(conversionWorkflow(configs));
    AgentTool fullLoop = AgentTool.create(fullLoop(configs));
    AgentContext ctx = AgentContext.builder()//
        .withConfig(configs.get(CRA_KEY))//
        .withTools(generator, converter, fullLoop)//
        .build();//
    return new LlmAgentProvider(ctx).get();
  }

  private SequentialAgent fullLoop(Map<String, AgentConfig> configs) {
    SequentialAgent generator = generationWorkflow(configs);
    SequentialAgent converter = conversionWorkflow(configs);
    LlmAgent bundler = new LlmAgentProvider(AgentContext.build(configs.get(CBA_KEY))).get();
    return SequentialAgent.builder() //
        .name("FullLoopWorkflow") //
        .description("Generates and converts code") //
        .subAgents(generator, converter, bundler) //
        .build();
  }

  private SequentialAgent conversionWorkflow(Map<String, AgentConfig> configs) {
    AgentTool tool = new CodeExecutionToolProvider(configs).get();
    LlmAgent convert = new LlmAgentProvider(AgentContext.build(configs.get(CTA_KEY), tool)).get();
    LlmAgent review = new LlmAgentProvider(AgentContext.build(configs.get(CCA_KEY))).get();
    LlmAgent refactor = new LlmAgentProvider(AgentContext.build(configs.get(CFA_KEY), tool)).get();
    return SequentialAgent.builder()//
        .name("CodeConversionWorkflow")//
        .description("Converts code with review-refactor")//
        .subAgents(convert, review, refactor)//
        .build();
  }

  private SequentialAgent generationWorkflow(Map<String, AgentConfig> configs) {
    AgentTool tool = new CodeExecutionToolProvider(configs).get();
    AgentContext ctx = AgentContext.build(configs.get(CGA_KEY), tool);
    LlmAgent generator = new LlmAgentProvider(ctx).get();
    LoopAgent refiner = new CodeRefinementLoop(tool, configs).get();
    return SequentialAgent.builder() //
        .name("CodeGenerationWorkflow") //
        .description("Generates code with Refinement Loop") //
        .subAgents(generator, refiner) //
        .build();
  }
}
