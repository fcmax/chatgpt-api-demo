package com.fcmax.openai.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAiRequest(
        String model,
        List<Message> messages,
        @JsonProperty("max_tokens") int maxTokens,
        double temperature,
        @JsonProperty("top_p") double topP,
        @JsonProperty("frequency_penalty") double frequencyPenalty,
        @JsonProperty("presence_penalty") double presencePenalty
) {

    public static OpenAiRequest buildRequest(String prompt, String model, int maxTokens, double temperature, double topP, double frequencyPenalty, double presencePenalty) {

        return new OpenAiRequest(
                model,
                List.of(new Message(prompt, "user")), maxTokens, temperature, topP, frequencyPenalty, presencePenalty);
    }
}
