package com.svetanis.agents.devtools;

import static com.google.common.collect.ImmutableMap.copyOf;
import static java.lang.String.format;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Provider;

import com.google.adk.tools.mcp.StreamableHttpServerParameters;
import com.google.common.collect.ImmutableMap;

public class ServerParamsProvider implements Provider<StreamableHttpServerParameters> {

	private static final String API_KEY = "Bearer %s";
	private static final String CGT_KEY = "COPILOT_GITHUB_TOKEN";
	private static final String URL = "https://api.githubcopilot.com/mcp";

	@Override
	public StreamableHttpServerParameters get() {
		String apiKey = apiKey(CGT_KEY);
		return StreamableHttpServerParameters//
				.builder()//
				.url(URL)//
				.headers(headers(apiKey))//
				.build();
	}

	private ImmutableMap<String, String> headers(String apiKey) {
		Map<String, String> map = new HashMap<>();
		map.put("Authorization", apiKey);
		map.put("X-MCP-Toolsets", "all");
		map.put("X-MCP-Readonly", "true");
		return copyOf(map);
	}

	private String apiKey(String key) {
		String apiKey = Optional.ofNullable(System.getenv(key))
				.orElseThrow(() -> new IllegalStateException(format("%s is not set", key)));
		return format(API_KEY, apiKey);
	}
}
