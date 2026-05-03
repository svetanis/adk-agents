package com.svetanis.agents.devtools;

import static java.util.Arrays.asList;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.adk.JsonBaseModel;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.StreamableHttpServerParameters;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;

import jakarta.inject.Provider;

public class CommitAgent implements Provider<LlmAgent> {

  private static final String TOOL_NAME = "create_or_update_file";
  private static final String CCA_KEY = "devtools.committer.agent";

  public CommitAgent(Map<String, AgentConfig> configs) {
    this.configs = ImmutableMap.copyOf(configs);
  }

  private final ImmutableMap<String, AgentConfig> configs;

  @Override
  public LlmAgent get() {
    McpToolset mcp = mcp(TOOL_NAME);
    AgentContext ctx = ctx(mcp);
    return new LlmAgentProvider(ctx).get();
  }

  private McpToolset mcp(String tool) {
    ObjectMapper mapper = JsonBaseModel.getMapper();
    StreamableHttpServerParameters params = new ServerParamsProvider().get();
    return new McpToolset(params, mapper, asList(tool));
  }

  private AgentContext ctx(McpToolset mcp) {
    AgentConfig config = configs.get(CCA_KEY);
    return AgentContext //
        .builder() //
        .withConfig(config) //
        .withTools(mcp.getTools(null).toList().blockingGet()) //
        .build();
  }
}
