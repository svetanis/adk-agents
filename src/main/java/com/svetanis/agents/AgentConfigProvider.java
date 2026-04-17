package com.svetanis.agents;

import java.io.File;

import javax.inject.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Deprecated
public class AgentConfigProvider implements Provider<AgentConf> {

	private static final String SRC = "src/main/resources/agents/%s.yaml";

	private final String fragment;

	public AgentConfigProvider(String fragment) {
		this.fragment = fragment;
	}

	@Override
	public AgentConf get() {
		try {
			String path = String.format(SRC, fragment);
			File file = new File(path);
			return readYaml(file);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static AgentConf readYaml(final File file) throws Exception {
		final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // jackson databind
		return mapper.readValue(file, AgentConf.class);
	}

}
