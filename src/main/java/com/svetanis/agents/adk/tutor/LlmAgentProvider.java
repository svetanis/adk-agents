package com.svetanis.agents.adk.tutor;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.GoogleSearchTool;

public class LlmAgentProvider implements Provider<LlmAgent> {

	private final String path;

	public LlmAgentProvider(String path) {
		this.path = path;
	}

	@Override
	public LlmAgent get() {
		AgentConfig config = new AgentConfigProvider(path).get();
		return LlmAgent.builder()//
				.name(config.getName())//
				.description(config.getDescription())//
				.model(config.getModel())//
				.instruction(config.getInstruction())//
				.tools(new GoogleSearchTool())//
				.build();
	}
}
