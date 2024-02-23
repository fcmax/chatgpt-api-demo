package com.fcmax.openai.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai.api")
public record OpenAiApiProperties(
        Model model,
        String url,
        int maxTokens,
        int temperature,
        int topP,
        int frequencyPenalty,
        int presencePenalty
) {
    public record Model(
            Gpt gpt3_5,
            Gpt gpt4
    ) {
    }

    public record Gpt(
            String engine,
            Token1KPrice token1KPrice
    ) {
    }

    public record Token1KPrice(
            double input,
            double output
    ) {
    }
}
