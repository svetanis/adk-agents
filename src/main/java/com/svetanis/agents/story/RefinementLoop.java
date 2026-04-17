package com.svetanis.agents.story;

import static com.google.common.collect.ImmutableMap.copyOf;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.LoopAgent;
import com.google.adk.tools.ExitLoopTool;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;

import jakarta.inject.Provider;

public class RefinementLoop implements Provider<LoopAgent> {

  private static final String SCA_KEY = "story.critic.agent";
  private static final String SRA_KEY = "story.refiner.agent";

  private static final String DESC = """
      LoopAgent contains the agents that
      will run repeatedly:
      Critic -> Refiner
      """;

  public RefinementLoop(Map<String, AgentConf> configs) {
    this.configs = copyOf(configs);
  }

  private final ImmutableMap<String, AgentConf> configs;

  @Override
  public LoopAgent get() {
    LlmAgent critic = new LlmAgentProvider(configs.get(SCA_KEY)).get();
    AgentContext ctx = AgentContext.build(configs.get(SRA_KEY), ExitLoopTool.INSTANCE);
    LlmAgent refiner = new LlmAgentProvider(ctx).get();
    return LoopAgent.builder().name("RefinementLoop") //
        .description(DESC) //
        .subAgents(critic, refiner) //
        .maxIterations(2) //
        .build();
  }
}
