package com.svetanis.agents.tutor;

import com.google.adk.agents.LlmAgent;
import com.google.adk.web.AdkWebServer;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.tutor.TutorApp

public class TutorApp {

	public static void main(String[] agrs) {
		LlmAgent root = new TutorAgent().get();
		AdkWebServer.start(root);
	}
}
