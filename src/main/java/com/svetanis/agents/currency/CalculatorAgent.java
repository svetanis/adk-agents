package com.svetanis.agents.currency;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.BuiltInCodeExecutionTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.adk.AgentConfig;
import com.svetanis.agents.adk.AgentConfigProvider;
import com.svetanis.agents.adk.AgentContext;
import com.svetanis.agents.adk.LlmAgentProvider;

public class CalculatorAgent implements Provider<LlmAgent> {

	private static final String CALCULATOR = "currency/calculator-agent";

	@Override
	public LlmAgent get() {
		AgentContext ctx = ctx();
		return new LlmAgentProvider(ctx).get();
	}

	// TODO: add retry logic
	private AgentContext ctx() {
		AgentConfig config = new AgentConfigProvider(CALCULATOR).get();
		return AgentContext//
				.builder()//
				.withConfig(config)//
				.withTools(ImmutableList.of(new BuiltInCodeExecutionTool()))//
				.build();
	}
}