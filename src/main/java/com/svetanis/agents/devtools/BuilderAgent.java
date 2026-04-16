package com.svetanis.agents.devtools;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.BuiltInCodeExecutionTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.AgentConfig;
import com.svetanis.agents.AgentConfigProvider;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;

public class BuilderAgent implements Provider<LlmAgent> {

	private static final String BUILDER = "devtools/builder-agent";

	@Override
	public LlmAgent get() {
		AgentContext ctx = ctx();
		return new LlmAgentProvider(ctx).get();
	}

	private AgentContext ctx() {
		AgentConfig config = new AgentConfigProvider(BUILDER).get();
		return AgentContext//
				.builder()//
				.withConfig(config)//
				.withTools(ImmutableList.of(new BuiltInCodeExecutionTool()))//
				.build();
	}
}
