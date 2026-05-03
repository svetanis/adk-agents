package com.svetanis.agents.devtools;

import com.google.adk.web.AdkWebServer;
import com.svetanis.agents.base.AgentConfigsProvider;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.devtools.DevToolsApp

public class DevToolsApp {

  public static void main(String[] args) throws Exception {
    AgentConfigsProvider configs = new AgentConfigsProvider();
    CommitAgent agent = new CommitAgent(configs.get());
    // Run your agent with the ADK Dev UI
    AdkWebServer.start(agent.get());
  }
}
