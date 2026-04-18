package com.svetanis.agents.tools;

import static com.google.common.collect.ImmutableMap.copyOf;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BuiltInCodeExecutionTool;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;

import jakarta.inject.Provider;

public class CodeExecutionToolProvider implements Provider<AgentTool> {

  private static final String KEY = "tool.code.execution";

  public CodeExecutionToolProvider(Map<String, AgentConf> configs) {
    this.configs = copyOf(configs);
  }

  private final ImmutableMap<String, AgentConf> configs;

  @Override
  public AgentTool get() {
    BuiltInCodeExecutionTool tool = new BuiltInCodeExecutionTool();
    AgentContext ctx = AgentContext.build(configs.get(KEY), tool);
    LlmAgent agent = new LlmAgentProvider(ctx).get();
    return AgentTool.create(agent);
  }
}
