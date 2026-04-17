package com.svetanis.agents.blogger;

import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getProperty;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.web.AdkWebServer;
import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentConfigsProvider;

import jakarta.inject.Provider;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.blogger.BloggerApp

// PROMPT: Run the daily executive briefing on Tech, Health, and Finance

public class BloggerApp {

  private static final String KEY = "blogger.refinement.loop";
  private static final boolean REFINE = parseBoolean(getProperty(KEY, "true"));

  public static void main(String[] agrs) {
    Map<String, AgentConf> map = new AgentConfigsProvider().get();
    
    if(true) {
      return;
    }
    
    Provider<LlmAgent> root = new BloggerRootAgent(REFINE, new AgentConfigsProvider());
    AdkWebServer.start(root.get());
  }
}
