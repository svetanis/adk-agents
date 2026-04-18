package com.svetanis.agents.a2aclient;

import static com.google.api.client.util.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

import java.util.Map;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;

import jakarta.inject.Provider;

public class CustomerSupportAgent implements Provider<LlmAgent> {

  private static final String CSA_KEY = "a2aclient.customer.support.agent";

  public CustomerSupportAgent(Provider<ImmutableMap<String, AgentConf>> provider) {
    this.provider = checkNotNull(provider, "provider");
  }

  private final Provider<ImmutableMap<String, AgentConf>> provider;

  @Override
  public LlmAgent get() {
    AgentContext ctx = agentContext(provider.get());
    return new LlmAgentProvider(ctx).get();
  }

  private AgentContext agentContext(Map<String, AgentConf> configs) {
    BaseAgent pca = new RemoteProductCatalogAgent().get();
    AgentConf config = configs.get(CSA_KEY);
    return AgentContext//
        .builder()//
        .withConfig(config)//
        .withSubAgents(asList(pca))//
        .build();
  }
}