package com.svetanis.agents.adk;

import java.io.File;

import javax.inject.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class AgentConfigProvider implements Provider<AgentConfig> {

	private static final String BASE = "src/main/resources/tutor/";

	private final String name;

	public AgentConfigProvider(String name) {
		this.name = name;
	}

	@Override
	public AgentConfig get() {
		try {
			File file = new File(BASE + name);
			return readYaml(file);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static AgentConfig readYaml(final File file) throws Exception {
		final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
		return mapper.readValue(file, AgentConfig.class);
	}

}
