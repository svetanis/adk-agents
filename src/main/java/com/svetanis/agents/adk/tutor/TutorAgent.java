package com.svetanis.agents.adk.tutor;

import static com.google.common.collect.ImmutableList.copyOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.GoogleSearchTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.adk.AgentConfig;
import com.svetanis.agents.adk.AgentConfigProvider;
import com.svetanis.agents.adk.AgentContext;
import com.svetanis.agents.adk.LlmAgentProvider;

public class TutorAgent implements Provider<LlmAgent> {

	private static final String ROOT = "tutor/root_tutor_agent";
	private static final String CODE = "tutor/code_tutor_agent";
	private static final String MATH = "tutor/math_tutor_agent";
	private static final String SCIENCE = "tutor/science_tutor_agent";

	@Override
	public LlmAgent get() {
		GoogleSearchTool gst = new GoogleSearchTool();
		AgentContext ctx = ctx(ROOT, tools(gst));
		return new LlmAgentProvider(ctx).get();
	}

	private ImmutableList<BaseTool> tools(GoogleSearchTool gst) {
		List<BaseTool> list = new ArrayList<>();
		list.add(AgentTool.create(new LlmAgentProvider(ctx(CODE, gst)).get()));
		list.add(AgentTool.create(new LlmAgentProvider(ctx(MATH, gst)).get()));
		list.add(AgentTool.create(new LlmAgentProvider(ctx(SCIENCE, gst)).get()));
		return copyOf(list);
	}

	private AgentContext ctx(String fragment, BaseTool... tools) {
		return ctx(fragment, Arrays.asList(tools));
	}

	private AgentContext ctx(String fragment, List<BaseTool> tools) {
		AgentConfig config = new AgentConfigProvider(fragment).get();
		return AgentContext.builder()//
				.withConfig(config)//
				.withTools(tools)//
				.build();//
	}
}
