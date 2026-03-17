package com.svetanis.agents.adk;

import static com.google.common.collect.ImmutableList.copyOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.adk.agents.BaseAgent;
import com.google.adk.tools.BaseTool;
import com.google.common.collect.ImmutableList;

public final class AgentContext {

	private final AgentConfig config;
	private final ImmutableList<BaseTool> tools;
	private final ImmutableList<BaseAgent> subAgents;

	public static final Builder builder() {
		return new Builder();
	}

	public static final AgentContext build(String path) {
		AgentConfig config = new AgentConfigProvider(path).get();
		return builder().withConfig(config).build();
	}

	public static final AgentContext build(String path, BaseTool tool) {
		AgentConfig config = new AgentConfigProvider(path).get();
		return builder().withConfig(config).withTools(Arrays.asList(tool)).build();
	}

	private AgentContext(Builder builder) {
		this.config = builder.config;
		this.tools = copyOf(builder.tools);
		this.subAgents = copyOf(builder.subAgents);
	}

	public static class Builder {

		private AgentConfig config;
		private List<BaseTool> tools = new ArrayList<>();
		private List<BaseAgent> subAgents = new ArrayList<>();

		public final Builder withConfig(AgentConfig config) {
			this.config = config;
			return this;
		}

		public final Builder withTools(BaseTool... tools) {
			return withTools(Arrays.asList(tools));
		}
		
		public final Builder withTools(List<BaseTool> tools) {
			this.tools = tools;
			return this;
		}

		public final Builder withSubAgents(List<BaseAgent> subAgents) {
			this.subAgents = subAgents;
			return this;
		}

		public AgentContext build() {
			return new AgentContext(this);
		}
	}

	public AgentConfig getConfig() {
		return config;
	}

	public ImmutableList<BaseTool> getTools() {
		return tools;
	}

	public ImmutableList<BaseAgent> getSubAgents() {
		return subAgents;
	}
}
