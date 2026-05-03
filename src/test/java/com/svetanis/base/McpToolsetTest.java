package com.svetanis.base;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.adk.JsonBaseModel;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.StreamableHttpServerParameters;
import com.svetanis.agents.devtools.ServerParamsProvider;

// create-or-update-file-contents
// https://adk.dev/integrations/github/#available-tools
// https://docs.github.com/en/rest/repos/contents?apiVersion=2026-03-10#create-or-update-file-contents
// https://github.com/google/adk-java/blob/main/contrib/samples/configagent/core_generate_content_config_config/root_agent.yaml

// https://adk.dev/integrations/github/
// https://docs.github.com/en/copilot/tutorials/customization-library/prompt-files/create-readme
// https://docs.github.com/en/copilot/tutorials/customization-library/custom-agents/your-first-custom-agent

public class McpToolsetTest {

  private static final String TOOL_NAME = "create_or_update_file";

  @Test
  public void test() throws IOException {
    ObjectMapper mapper = JsonBaseModel.getMapper();
    StreamableHttpServerParameters params = new ServerParamsProvider().get();
    try (McpToolset mcp = new McpToolset(params, mapper, asList(TOOL_NAME))) {
      List<BaseTool> tools = mcp.getTools(null).toList().blockingGet();
      for (BaseTool tool : tools) {
//        System.out.println(tool.name());
      }
    }
  }
}
