package com.fcmax.openai.client.service.chat;

import com.fcmax.openai.client.dto.ChatResponseDto;
import com.fcmax.openai.client.dto.OpenAiResponse;

import java.util.Objects;

public interface Chat {
    ChatResponseDto chat(String prompt);

    default String getResponseContent(OpenAiResponse response) {
        return Objects.requireNonNull(response)
                .choices()
                .stream()
                .findFirst()
                .map(choice -> choice.message().content())
                .orElse("No response")
                .replace("\n", "<br>");
    }
}
