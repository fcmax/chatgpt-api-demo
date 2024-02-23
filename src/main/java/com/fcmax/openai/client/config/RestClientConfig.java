package com.fcmax.openai.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestClientConfig {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(createInterceptor());

        return restTemplate;
    }
    
    private ClientHttpRequestInterceptor createInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().set(AUTHORIZATION, BEARER + openaiApiKey);
            try {
                return execution.execute(request, body);
            } catch (IOException e) {
                throw new RuntimeException("Error executing HTTP request", e);
            }
        };
    }
}
