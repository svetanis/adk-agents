package com.svetanis.agents.blogger;

import static com.google.api.client.util.Preconditions.checkNotNull;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentConfigsProvider;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;

import jakarta.inject.Provider;

public class BlogRootAgent implements Provider<LlmAgent> {

  private static final String BRA_KEY = "blogger.root.agent";

  public BlogRootAgent(AgentConfigsProvider provider) {
    this.provider = checkNotNull(provider, "provider");
  }

  private final AgentConfigsProvider provider;

  @Override
  public LlmAgent get() {
    Map<String, AgentConfig> configs = provider.get();
    AgentContext ctx = agentCtx(BRA_KEY, configs);
    return new LlmAgentProvider(ctx).get();
  }

  private AgentContext agentCtx(String key, Map<String, AgentConfig> configs) {
    AgentConfig config = configs.get(key);
    AgentTool pipeline = AgentTool.create(new BlogCreationPipeline(configs).get());
    return AgentContext.build(config, pipeline);
  }
}
