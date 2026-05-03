package com.svetanis.agents.report;

import com.google.adk.web.AdkWebServer;
import com.svetanis.agents.base.AgentConfigsProvider;

/*
 * run command:
 * mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.report.ReportWriterApp
 */

public final class ReportWriterApp {

  public static void main(String[] agrs) {
    AgentConfigsProvider configs = new AgentConfigsProvider();
    ReportRootAgent root = new ReportRootAgent(configs.get());
    AdkWebServer.start(root.get());
  }
}
