package com.fcmax.openai.client.dto;

public record ChatResponseDto(String promt, String content, Stats stats) {
    public record Stats(int usedTokens, String modelEngine, Price price) {
    }
}
