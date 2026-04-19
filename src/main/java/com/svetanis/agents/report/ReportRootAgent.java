package com.svetanis.agents.report;

import static com.google.api.client.util.Preconditions.checkNotNull;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentConfigsProvider;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;
import com.svetanis.agents.base.tools.SearchAgentToolProvider;

import autovalue.shaded.com.google.common.collect.ImmutableList;
import jakarta.inject.Provider;

public class ReportRootAgent implements Provider<LlmAgent> {

  private static final String RRW_KEY = "report.root.writer";
  private static final String RTR_KEY = "report.topic.researcher";
  private static final String RCA_KEY = "report.content.analyst";
  private static final String RAA_KEY = "report.research.coordinator";

  public ReportRootAgent(AgentConfigsProvider provider) {
    this.provider = checkNotNull(provider, "provider");
  }

  private final AgentConfigsProvider provider;

  @Override
  public LlmAgent get() {
    Map<String, AgentConfig> configs = provider.get();
    AgentTool assistant = AgentTool.create(researchAssistant(configs));
    AgentContext ctx = AgentContext.build(configs.get(RRW_KEY), assistant);
    return new LlmAgentProvider(ctx).get();
  }

  private LlmAgent researchAssistant(Map<String, AgentConfig> configs) {
    LlmAgent search = webSearch(configs);
    LlmAgent analyst = summarizer(configs);
    AgentConfig config = configs.get(RAA_KEY);
    AgentContext ctx = AgentContext.builder()//
        .withConfig(config)//
        .withSubAgents(ImmutableList.of(search, analyst))//
        .build();
    return new LlmAgentProvider(ctx).get();
  }

  private LlmAgent summarizer(Map<String, AgentConfig> configs) {
    AgentContext ctx = AgentContext.build(configs.get(RCA_KEY));
    return new LlmAgentProvider(ctx).get();
  }

  private LlmAgent webSearch(Map<String, AgentConfig> configs) {
    AgentTool search = new SearchAgentToolProvider(configs).get();
    AgentContext ctx = AgentContext.build(configs.get(RTR_KEY), search);
    return new LlmAgentProvider(ctx).get();
  }
}
