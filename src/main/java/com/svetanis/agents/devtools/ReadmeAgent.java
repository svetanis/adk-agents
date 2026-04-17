package com.svetanis.agents.devtools;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.StreamableHttpServerParameters;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentConfigProvider;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;

// https://adk.dev/integrations/github/
// https://docs.github.com/en/copilot/tutorials/customization-library/prompt-files/create-readme
// https://docs.github.com/en/copilot/tutorials/customization-library/custom-agents/your-first-custom-agent

public class ReadmeAgent implements Provider<LlmAgent> {

	private static final String README = "devtools/readme-agent";

	@Override
	public LlmAgent get() {
		StreamableHttpServerParameters params = new ServerParamsProvider().get();
		try (McpToolset mcp = new McpToolset(params)) {
			AgentContext ctx = ctx(mcp);
			return new LlmAgentProvider(ctx).get();
		}
	}

	private AgentContext ctx(McpToolset mcp) {
		// TODO: add Tool predicate
		AgentConf config = new AgentConfigProvider(README).get();
		return AgentContext//
				.builder()//
				.withConfig(config)//
				.withTools(mcp.getTools(null).toList().blockingGet())//
				.build();
	}
}
