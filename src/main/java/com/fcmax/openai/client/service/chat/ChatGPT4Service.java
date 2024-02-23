package com.fcmax.openai.client.service.chat;

import com.fcmax.openai.client.config.OpenAiApiProperties;
import com.fcmax.openai.client.dto.ChatResponseDto;
import com.fcmax.openai.client.dto.OpenAiRequest;
import com.fcmax.openai.client.dto.OpenAiResponse;
import com.fcmax.openai.client.dto.OpenAiResponse.Usage;
import com.fcmax.openai.client.dto.Price;
import com.fcmax.openai.client.service.price.PriceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.fcmax.openai.client.config.OpenAiApiProperties.Token1KPrice;
import static com.fcmax.openai.client.dto.ChatResponseDto.Stats;


@Slf4j
@RequiredArgsConstructor
@Service("gpt4")
public class ChatGPT4Service implements Chat {

    private final RestTemplate restTemplate;
    private final OpenAiApiProperties apiProperties;
    private final PriceCalculator priceCalculator;

    @Override
    public ChatResponseDto chat(String prompt) {
        OpenAiResponse response = executeRequest(prompt);
        log.info("Response: {}", response);

        String content = getResponseContent(response);
        Usage usage = response.usage();
        Price price = calculatePrice(usage);
        Stats stats = new Stats(usage.totalTokens(), response.model(), price);

        return new ChatResponseDto(prompt, content, stats);
    }

    private OpenAiResponse executeRequest(String prompt) {
        OpenAiRequest request = OpenAiRequest.buildRequest(
                prompt,
                apiProperties.model().gpt4().engine(),
                apiProperties.maxTokens(),
                apiProperties.temperature(),
                apiProperties.topP(),
                apiProperties.frequencyPenalty(),
                apiProperties.presencePenalty()
        );

        return restTemplate.postForObject(apiProperties.url(), request, OpenAiResponse.class);
    }

    private Price calculatePrice(Usage usage) {
        Token1KPrice token1KPrice = apiProperties
                .model()
                .gpt4()
                .token1KPrice();

        return priceCalculator.calculate(usage.promptTokens(), usage.completionTokens(), token1KPrice.input(), token1KPrice.output());
    }
}
