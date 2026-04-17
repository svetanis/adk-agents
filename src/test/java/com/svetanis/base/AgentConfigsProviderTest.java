package com.svetanis.base;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.svetanis.agents.AgentConf;
import com.svetanis.agents.AgentConfigsProvider;

public class AgentConfigsProviderTest {

  @Test
  public void test() throws IOException {
    AgentConfigsProvider provider = new AgentConfigsProvider();
    Map<String, AgentConf> map = provider.get();
    System.out.println(map.size());
    for (String key : map.keySet()) {
      System.out.println(key);
    }
  }
}
