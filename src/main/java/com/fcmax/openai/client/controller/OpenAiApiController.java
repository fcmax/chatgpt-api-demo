package com.fcmax.openai.client.controller;

import com.fcmax.openai.client.dto.ChatResponseDto;
import com.fcmax.openai.client.service.chat.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OpenAiApiController {

    private final Map<String, Chat> chatServices;

    @GetMapping("/chat/{gptModel}")
    public ModelAndView chat(@PathVariable("gptModel") String gptModel, @RequestParam("prompt") String prompt) {
        Chat chatService = Optional.ofNullable(chatServices.get(gptModel))
                .orElseThrow(() -> new IllegalArgumentException("No service found for model: " + gptModel));

        ChatResponseDto chatResponseDto = chatService.chat(prompt);

        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("data", chatResponseDto);

        return modelAndView;
    }
}
