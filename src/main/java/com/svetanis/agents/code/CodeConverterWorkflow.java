package com.svetanis.agents.code;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.SequentialAgent;
import com.google.adk.tools.AgentTool;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;
import com.svetanis.agents.base.tools.CodeExecutionToolProvider;

import jakarta.inject.Provider;

public class CodeConverterWorkflow implements Provider<SequentialAgent> {

  private static final String CTA_KEY = "code.converter.agent";
  private static final String CCA_KEY = "code.critic.agent";
  private static final String CFA_KEY = "code.refactor.agent";

  public CodeConverterWorkflow(Map<String, AgentConfig> configs) {
    this.configs = ImmutableMap.copyOf(configs);
  }

  private final ImmutableMap<String, AgentConfig> configs;

  @Override
  public SequentialAgent get() {
    AgentTool tool = new CodeExecutionToolProvider(configs).get();
    LlmAgent convert = new LlmAgentProvider(AgentContext.build(configs.get(CTA_KEY), tool)).get();
    LlmAgent review = new LlmAgentProvider(AgentContext.build(configs.get(CCA_KEY))).get();
    LlmAgent refactor = new LlmAgentProvider(AgentContext.build(configs.get(CFA_KEY), tool)).get();
    return SequentialAgent.builder()//
        .name("CodeConverterWorkflow")//
        .description("Runs code conversion with review-refactor")//
        .subAgents(convert, review, refactor)//
        .build();
  }
}
