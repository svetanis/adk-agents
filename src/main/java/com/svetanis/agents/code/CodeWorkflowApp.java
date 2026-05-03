package com.svetanis.agents.code;

import com.google.adk.web.AdkWebServer;
import com.svetanis.agents.base.AgentConfigsProvider;

/*
 * run command:
 * mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.code.CodeWorkflowApp
 */

public final class CodeWorkflowApp {

  public static void main(String[] agrs) {
    AgentConfigsProvider configs = new AgentConfigsProvider();
    CodeRootAgent root = new CodeRootAgent(configs.get());
    AdkWebServer.start(root.get());
  }
}
