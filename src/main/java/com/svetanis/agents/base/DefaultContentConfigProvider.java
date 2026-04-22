package com.svetanis.agents.base;

import com.google.genai.types.GenerateContentConfig;

import jakarta.inject.Provider;

public class DefaultContentConfigProvider implements Provider<GenerateContentConfig> {

  @Override
  public GenerateContentConfig get() {
    return GenerateContentConfig.builder()//
        .temperature(Double.valueOf(0.1).floatValue())//
        .maxOutputTokens(2000)//
        .build();
  }

}
