package com.svetanis.agents.blogger;

import com.google.adk.agents.LlmAgent;
import com.google.adk.web.AdkWebServer;
import com.svetanis.agents.AgentConfigsProvider;

import jakarta.inject.Provider;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.blogger.BloggerApp

// Run the daily executive briefing on Tech, Health, and Finance

public final class BloggerApp {

  public static void main(String[] agrs) {
    Provider<LlmAgent> root = new RootAgent(new AgentConfigsProvider());
    AdkWebServer.start(root.get());
  }
}
