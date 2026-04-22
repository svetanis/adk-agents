package com.svetanis.agents.devtools;

import com.google.adk.web.AdkWebServer;
import com.svetanis.agents.base.AgentConfigsProvider;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.devtools.DevToolsApp

// Suggested PROMPTs: 
// Please review and improve README.md file for {repository}.

public class DevToolsApp {

  public static void main(String[] args) throws Exception {
    AgentConfigsProvider configs = new AgentConfigsProvider();
    DevToolsRootAgent root = new DevToolsRootAgent(configs);
    // Run your agent with the ADK Dev UI
    AdkWebServer.start(root.get());
  }
}
