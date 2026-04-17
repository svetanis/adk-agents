package com.svetanis.agents.blogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.SequentialAgent;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.LlmAgentProvider;

import jakarta.inject.Provider;

public class BlogPipeline implements Provider<SequentialAgent> {

  private static final String DESC =
      "Sequential agent runs the sub-agents in the order that they are listed";

  private static final String BAA_KEY = "blogger.aggregator.agent";
  private static final String BWA_KEY = "blogger.writer.agent";
  private static final String BEA_KEY = "blogger.editor.agent";
  private static final String BPA_KEY = "blogger.presenter.agent";

  public BlogPipeline(boolean refine, Map<String, AgentConf> configs) {
    this.refine = refine;
    this.configs = ImmutableMap.copyOf(configs);
  }

  private final boolean refine;
  private final ImmutableMap<String, AgentConf> configs;

  @Override
  public SequentialAgent get() {
    List<BaseAgent> subAgents = subAgents(refine);
    return SequentialAgent.builder() //
        .name("BlogPipeline") //
        .description(DESC) //
        .subAgents(subAgents) //
        .build();
  }

  private ImmutableList<BaseAgent> subAgents(boolean refine) {
    List<BaseAgent> list = new ArrayList<>();
    // 1. parallel research team
    list.add(new ResearchTeam(configs).get());
    // 2. aggregator
    list.add(new LlmAgentProvider(configs.get(BAA_KEY)).get());
    // 3. writer
    list.add(new LlmAgentProvider(configs.get(BWA_KEY)).get());
    // 4. refiner
    list.add(refiner(refine));
    // 5. presenter
    list.add(new LlmAgentProvider(configs.get(BPA_KEY)).get());
    return ImmutableList.copyOf(list);
  }

  private BaseAgent refiner(boolean refine) {
    if (refine) {
      return new BlogRefinementLoop(configs).get();
    }
    return new LlmAgentProvider(configs.get(BEA_KEY)).get();
  }
}
