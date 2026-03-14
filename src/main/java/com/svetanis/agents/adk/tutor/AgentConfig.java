package com.svetanis.agents.adk.tutor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgentConfig {
	@JsonProperty
	private String name;
	@JsonProperty
	private String model;
	@JsonProperty
	private String description;
	@JsonProperty
	private String instruction;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	@Override
	public String toString() {
		return "AgentConfig name=" + name;
	}

}
