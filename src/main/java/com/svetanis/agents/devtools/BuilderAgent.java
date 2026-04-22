package com.svetanis.agents.devtools;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.BuiltInCodeExecutionTool;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;

import jakarta.inject.Provider;

public class BuilderAgent implements Provider<LlmAgent> {

  private static final String BLD_KEY = "devtools.builder.agent";

  public BuilderAgent(Map<String, AgentConfig> configs) {
    this.configs = ImmutableMap.copyOf(configs);
  }

  private final ImmutableMap<String, AgentConfig> configs;

  @Override
  public LlmAgent get() {
    AgentContext ctx = ctx();
    return new LlmAgentProvider(ctx).get();
  }

  private AgentContext ctx() {
    AgentConfig config = configs.get(BLD_KEY);
    return AgentContext//
        .builder()//
        .withConfig(config)//
        .withTools(ImmutableList.of(new BuiltInCodeExecutionTool()))//
        .build();
  }
}
