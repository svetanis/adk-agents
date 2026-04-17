package com.svetanis.agents;

import static com.google.common.collect.ImmutableMap.copyOf;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.GoogleSearchTool;
import com.google.common.collect.ImmutableMap;

import jakarta.inject.Provider;

public class SearchAgentToolProvider implements Provider<AgentTool> {

  private static final String GSA_KEY = "search.agent";

  public SearchAgentToolProvider(Map<String, AgentConf> configs) {
    this.configs = copyOf(configs);
  }

  private final ImmutableMap<String, AgentConf> configs;

  @Override
  public AgentTool get() {
    GoogleSearchTool gst = new GoogleSearchTool();
    AgentContext ctx = AgentContext.build(configs.get(GSA_KEY), gst);
    LlmAgent searchAgent = new LlmAgentProvider(ctx).get();
    return AgentTool.create(searchAgent);
  }
}
