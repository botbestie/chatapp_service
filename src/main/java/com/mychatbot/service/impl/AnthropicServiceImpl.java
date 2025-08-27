package com.mychatbot.service.impl;

import com.mychatbot.config.AnthropicConfig;
import com.mychatbot.dto.AnthropicRequest;
import com.mychatbot.dto.AnthropicResponse;
import com.mychatbot.dto.ChatRequest;
import com.mychatbot.dto.ChatResponse;
import com.mychatbot.service.AnthropicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnthropicServiceImpl implements AnthropicService {

    private final WebClient webClient;
    private final AnthropicConfig anthropicConfig;

    @Override
    public Mono<ChatResponse> sendChatMessage(ChatRequest chatRequest) {
        log.info("Sending chat message: {}", chatRequest.getMessage());
        
        AnthropicRequest request = AnthropicRequest.builder()
                .model(anthropicConfig.getModel())
                .maxTokens(anthropicConfig.getMaxTokens())
                .temperature(anthropicConfig.getTemperature())
                .messages(List.of(
                        AnthropicRequest.Message.builder()
                                .role("user")
                                .content(chatRequest.getMessage())
                                .build()
                ))
                .build();

        return callAnthropicAPI(request)
                .map(response -> mapToChatResponse(response, chatRequest))
                .onErrorResume(this::handleError);
    }

    @Override
    public Mono<String> sendMessage(String message, String model) {
        log.info("Sending direct message to model: {}", model != null ? model : anthropicConfig.getModel());
        
        AnthropicRequest request = AnthropicRequest.builder()
                .model(model != null ? model : anthropicConfig.getModel())
                .maxTokens(anthropicConfig.getMaxTokens())
                .temperature(anthropicConfig.getTemperature())
                .messages(List.of(
                        AnthropicRequest.Message.builder()
                                .role("user")
                                .content(message)
                                .build()
                ))
                .build();

        return callAnthropicAPI(request)
                .map(this::extractResponseText)
                .onErrorResume(this::handleStringError);
    }

    private Mono<AnthropicResponse> callAnthropicAPI(AnthropicRequest request) {
        return webClient.post()
                .uri(anthropicConfig.getBaseUrl() + "/v1/messages")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + anthropicConfig.getKey())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("anthropic-version", "2023-06-01")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AnthropicResponse.class);
    }

    private ChatResponse mapToChatResponse(AnthropicResponse response, ChatRequest chatRequest) {
        return ChatResponse.builder()
                .message(extractResponseText(response))
                .userId(chatRequest.getUserId())
                .sessionId(chatRequest.getSessionId())
                .timestamp(LocalDateTime.now())
                .model(response.getModel())
                .inputTokens(response.getUsage() != null ? response.getUsage().getInputTokens() : null)
                .outputTokens(response.getUsage() != null ? response.getUsage().getOutputTokens() : null)
                .status("SUCCESS")
                .build();
    }

    private String extractResponseText(AnthropicResponse response) {
        if (response.getContent() != null && !response.getContent().isEmpty()) {
            return response.getContent().get(0).getText();
        }
        return "No response content available";
    }

    private Mono<ChatResponse> handleError(Throwable error) {
        log.error("Error calling Anthropic API: {}", error.getMessage());
        
        return Mono.just(ChatResponse.builder()
                .status("ERROR")
                .errorMessage(error.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    private Mono<String> handleStringError(Throwable error) {
        log.error("Error calling Anthropic API: {}", error.getMessage());
        
        if (error instanceof WebClientResponseException) {
            WebClientResponseException webClientError = (WebClientResponseException) error;
            return Mono.just("Error: " + webClientError.getStatusCode() + " - " + webClientError.getResponseBodyAsString());
        }
        
        return Mono.just("Error: " + error.getMessage());
    }
}
