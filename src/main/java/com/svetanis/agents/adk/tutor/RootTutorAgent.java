package com.svetanis.agents.adk.tutor;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;

public class RootTutorAgent implements Provider<LlmAgent> {

	private static final String ROOT = "root_tutor_agent.yaml";
	private static final String CODE = "code_tutor_agent.yaml";
	private static final String MATH = "math_tutor_agent.yaml";
	private static final String SCIENCE = "science_tutor_agent.yaml";

	@Override
	public LlmAgent get() {
		AgentTool cat = AgentTool.create(new LlmAgentProvider(CODE).get());
		AgentTool mat = AgentTool.create(new LlmAgentProvider(MATH).get());
		AgentTool sat = AgentTool.create(new LlmAgentProvider(SCIENCE).get());
		AgentConfig config = new AgentConfigProvider(ROOT).get();
		return LlmAgent.builder().name(config.getName())//
				.description(config.getDescription())//
				.model(config.getModel())//
				.instruction(config.getInstruction())//
				.tools(cat, mat, sat)//
				.build();
	}
}
