package com.svetanis.agents.github;

import com.google.adk.agents.LlmAgent;
import com.google.adk.web.AdkWebServer;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.github.GithubApp

// Suggested PROMPTs: 
// Please review and improve README.md file for {repository}.

public class GithubApp {

	public static void main(String[] args) throws Exception {
		LlmAgent rma = new ReadmeAgent().get();
		// Run your agent with the ADK Dev UI
		AdkWebServer.start(rma);
	}
}
