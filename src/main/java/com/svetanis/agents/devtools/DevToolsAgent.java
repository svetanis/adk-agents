package com.svetanis.agents.devtools;

import static com.google.common.collect.ImmutableList.copyOf;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BaseTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.adk.AgentConfig;
import com.svetanis.agents.adk.AgentConfigProvider;
import com.svetanis.agents.adk.AgentContext;
import com.svetanis.agents.adk.LlmAgentProvider;

public class DevToolsAgent implements Provider<LlmAgent> {

	private static final String ROOT = "devtools/root-agent";

	@Override
	public LlmAgent get() {
		AgentContext ctx = ctx(ROOT, tools());
		return new LlmAgentProvider(ctx).get();
	}

	private ImmutableList<BaseTool> tools() {
		List<BaseTool> list = new ArrayList<>();
		list.add(AgentTool.create(new ReadmeAgent().get()));
		list.add(AgentTool.create(new FileSystemAgent().get()));
		list.add(AgentTool.create(new BuilderAgent().get()));
		return copyOf(list);
	}

	private AgentContext ctx(String fragment, List<BaseTool> tools) {
		AgentConfig config = new AgentConfigProvider(fragment).get();
		return AgentContext.builder()//
				.withConfig(config)//
				.withTools(tools)//
				.build();//
	}
}
