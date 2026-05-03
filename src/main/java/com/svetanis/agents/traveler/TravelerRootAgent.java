package com.svetanis.agents.traveler;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableMap.copyOf;
import static java.util.Arrays.asList;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.ParallelAgent;
import com.google.adk.agents.SequentialAgent;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.base.AgentConfig;
import com.svetanis.agents.base.AgentContext;
import com.svetanis.agents.base.LlmAgentProvider;

import jakarta.inject.Provider;

public class TravelerRootAgent implements Provider<LlmAgent> {

  private static final String TRA_KEY = "traveler.root.agent";
  private static final String TIA_KEY = "traveler.itinerary.agent";

  public TravelerRootAgent(Map<String, AgentConfig> configs) {
    this.configs = copyOf(checkNotNull(configs, "configs"));
  }

  private final ImmutableMap<String, AgentConfig> configs;

  @Override
  public LlmAgent get() {
    SequentialAgent subAgent = tripAssistant(configs);
    AgentConfig rootConfig = configs.get(TRA_KEY);
    AgentContext rootCtx =
        AgentContext.builder() //
            .withConfig(rootConfig) //
            .withSubAgents(asList(subAgent)) //
            .build(); //
    return new LlmAgentProvider(rootCtx).get();
  }

  private SequentialAgent tripAssistant(Map<String, AgentConfig> configs) {
    ParallelAgent team = new TripSearchTeam(configs).get();
    LlmAgent itinerary = new LlmAgentProvider(configs.get(TIA_KEY)).get();
    return SequentialAgent.builder() //
        .name("TripAssistantWorkflow") //
        .description("Travel Planning Agent with parallel search") //
        .subAgents(team, itinerary) //
        .build();
  }
}
