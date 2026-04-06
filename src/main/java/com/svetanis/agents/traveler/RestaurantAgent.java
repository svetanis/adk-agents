package com.svetanis.agents.traveler;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.GoogleMapsTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.adk.AgentConfig;
import com.svetanis.agents.adk.AgentConfigProvider;
import com.svetanis.agents.adk.AgentContext;
import com.svetanis.agents.adk.LlmAgentProvider;

public class RestaurantAgent implements Provider<LlmAgent> {

	private static final String RESTAURANT = "traveler/restaurant-agent";

	@Override
	public LlmAgent get() {
		AgentContext ctx = ctx();
		return new LlmAgentProvider(ctx).get();
	}

	private AgentContext ctx() {
		AgentConfig config = new AgentConfigProvider(RESTAURANT).get();
		return AgentContext//
				.builder()//
				.withConfig(config)//
				.withTools(ImmutableList.of(new GoogleMapsTool()))//
				.build();
	}

}
