package com.svetanis.agents.devtools;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.SequentialAgent;

public class PipelineAgent implements Provider<SequentialAgent> {

	@Override
	public SequentialAgent get() {
		LlmAgent rma = new ReadmeAgent().get();
		LlmAgent fsa = new FileSystemAgent().get();
		return SequentialAgent.builder()//
				.name("ReadmePipeline")//
				.subAgents(rma, fsa)//
				.build();
	}
}
