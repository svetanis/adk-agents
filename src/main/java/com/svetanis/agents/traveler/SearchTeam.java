package com.svetanis.agents.traveler;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.ParallelAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BaseTool;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;
import com.svetanis.agents.tools.MapsAgentToolProvider;
import com.svetanis.agents.tools.SearchAgentToolProvider;

import jakarta.inject.Provider;

public class SearchTeam implements Provider<ParallelAgent> {

  private static final String DESC = """
      The ParallelSearchTeam agent searches flights, 
      accomodations, dining and activity options concurrently.
      """;

  private static final String TFA_KEY = "traveler.flight.agent";
  private static final String THA_KEY = "traveler.accomodation.agent";
  private static final String TAA_KEY = "traveler.activity.agent";
  private static final String TDA_KEY = "traveler.dining.agent";

  public SearchTeam(Map<String, AgentConf> configs) {
    this.configs = ImmutableMap.copyOf(configs);
  }

  private final ImmutableMap<String, AgentConf> configs;

  @Override
  public ParallelAgent get() {
    List<BaseTool> tools = tools();
    List<LlmAgent> subAgents = subAgents(tools);
    return ParallelAgent.builder() //
        .name("ParallelSearchTeam") //
        .description(DESC) //
        .subAgents(subAgents) //
        .build();
  }

  private ImmutableList<LlmAgent> subAgents(List<BaseTool> tools) {
    List<String> keys = asList(TFA_KEY, THA_KEY, TAA_KEY, TDA_KEY);
    return copyOf(transform(keys, k -> llmAgent(k, tools)));
  }

  private List<BaseTool> tools() {
    AgentTool sat = new SearchAgentToolProvider(configs).get();
    AgentTool mat = new MapsAgentToolProvider(configs).get();
    return ImmutableList.of(sat, mat);
  }

  private LlmAgent llmAgent(String key, List<BaseTool> tools) {
    AgentConf config = configs.get(key);
    AgentContext ctx = AgentContext.builder()//
        .withConfig(config)//
        .withTools(tools)//
        .build();//
    return new LlmAgentProvider(ctx).get();
  }
}
