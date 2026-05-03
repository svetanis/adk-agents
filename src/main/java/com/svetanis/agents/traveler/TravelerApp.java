package com.svetanis.agents.traveler;

import com.google.adk.web.AdkWebServer;
import com.svetanis.agents.base.AgentConfigsProvider;

/*
 * run command:
 * mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.traveler.TravelerApp
 */
public class TravelerApp {

  public static void main(String[] args) throws Exception {
    AgentConfigsProvider configs = new AgentConfigsProvider();
    TravelerRootAgent root = new TravelerRootAgent(configs.get());
    AdkWebServer.start(root.get());
  }
}
