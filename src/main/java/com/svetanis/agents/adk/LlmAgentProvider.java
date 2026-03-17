package com.svetanis.agents.adk;

import javax.inject.Provider;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.BaseTool;
import com.google.common.collect.ImmutableList;

import io.reactivex.rxjava3.core.Maybe;

public class LlmAgentProvider implements Provider<LlmAgent> {

	private final AgentConfig config;
	private final ImmutableList<BaseTool> tools;
	private final ImmutableList<BaseAgent> subAgents;

	public LlmAgentProvider(String path) {
		this(AgentContext.build(path));
	}

	public LlmAgentProvider(String path, BaseTool tool) {
		this(AgentContext.build(path, tool));
	}

	public LlmAgentProvider(AgentContext ctx) {
		this.config = ctx.getConfig();
		this.tools = ctx.getTools();
		this.subAgents = ctx.getSubAgents();
	}

	@Override
	public LlmAgent get() {
		LlmAgent.Builder builder = LlmAgent.builder();
		builder.name(config.getName());
		builder.description(config.getDescription());
		builder.model(config.getModel());
		builder.instruction(config.getInstruction());
		if (config.getAfterAgentCallback().isPresent()) {
			builder.afterAgentCallback(cb -> {//
				cb.eventActions().setTransferToAgent(config.getAfterAgentCallback().get());//
				return Maybe.empty();
			});
		}
		if (config.getOutputKey().isPresent()) {
			builder.outputKey(config.getOutputKey().get());
		}
		if (tools.size() > 0) {
			builder.tools(tools);
		}
		if (subAgents.size() > 0) {
			builder.subAgents(subAgents);
		}
		return builder.build();
	}
}
