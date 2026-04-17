package com.svetanis.agents.currency;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.BuiltInCodeExecutionTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentConfigProvider;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;

public class CalculatorAgent implements Provider<LlmAgent> {

	private static final String CALCULATOR = "currency/calculator-agent";

	@Override
	public LlmAgent get() {
		AgentContext ctx = ctx();
		return new LlmAgentProvider(ctx).get();
	}

	// TODO: add retry logic
	private AgentContext ctx() {
		AgentConf config = new AgentConfigProvider(CALCULATOR).get();
		return AgentContext//
				.builder()//
				.withConfig(config)//
				.withTools(ImmutableList.of(new BuiltInCodeExecutionTool()))//
				.build();
	}
}