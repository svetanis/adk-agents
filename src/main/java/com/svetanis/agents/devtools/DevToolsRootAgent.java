package com.svetanis.agents.devtools;

import static com.google.api.client.util.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.copyOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BaseTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentConfigsProvider;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;

import jakarta.inject.Provider;

public class DevToolsRootAgent implements Provider<LlmAgent> {

  private static final String DRA_KEY = "devtools.root.agent";

  public DevToolsRootAgent(AgentConfigsProvider provider) {
    this.provider = checkNotNull(provider);
  }

  private final AgentConfigsProvider provider;

  @Override
  public LlmAgent get() {
    Map<String, AgentConfig> configs = provider.get();
    AgentContext ctx = AgentContext.builder()//
        .withConfig(configs.get(DRA_KEY))//
        .withTools(tools(configs))//
        .build();//
    return new LlmAgentProvider(ctx).get();
  }

  private ImmutableList<BaseTool> tools(Map<String, AgentConfig> configs) {
    List<BaseTool> list = new ArrayList<>();
    list.add(AgentTool.create(new ReadmeAgent(configs).get()));
    list.add(AgentTool.create(new CommitAgent(configs).get()));
    list.add(AgentTool.create(new BuilderAgent(configs).get()));
    list.add(AgentTool.create(new FileSystemAgent(configs).get()));
    return copyOf(list);
  }
}
