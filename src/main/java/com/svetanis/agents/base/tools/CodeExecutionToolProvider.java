package com.svetanis.agents.base.tools;

import static com.google.common.collect.ImmutableMap.copyOf;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.codeexecutors.BuiltInCodeExecutor;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BuiltInCodeExecutionTool;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.DefaultContentConfigProvider;

import jakarta.inject.Provider;

public class CodeExecutionToolProvider implements Provider<AgentTool> {

  private static final String KEY = "tool.code.execution";

  public CodeExecutionToolProvider(Map<String, AgentConfig> configs) {
    this.configs = copyOf(configs);
  }

  private final ImmutableMap<String, AgentConfig> configs;

  @Override
  public AgentTool get() {
    AgentConfig config = configs.get(KEY);
    LlmAgent.Builder builder = LlmAgent.builder();
    builder.name(config.getName());
    builder.description(config.getDescription());
    builder.model(config.getModel());
    builder.instruction(config.getInstruction());
    builder.tools(new BuiltInCodeExecutionTool());
    builder.codeExecutor(new BuiltInCodeExecutor());
    builder.generateContentConfig(new DefaultContentConfigProvider().get());
    return AgentTool.create(builder.build());
  }
}
