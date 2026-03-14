package com.svetanis.agents.adk.tutor;

import com.google.adk.agents.LlmAgent;
import com.google.adk.web.AdkWebServer;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.adk.tutor.TutorApp

public class TutorApp {

	public static void main(String[] agrs) {
		LlmAgent root = new RootTutorAgent().get();
		AdkWebServer.start(root);
	}
}
