package com.mychatbot.service;

import com.mychatbot.dto.ChatRequest;
import com.mychatbot.dto.ChatResponse;
import reactor.core.publisher.Mono;

public interface AnthropicService {
    
    /**
     * Send a chat message to Anthropic API and get response
     * 
     * @param chatRequest The chat request containing the message
     * @return Mono containing the chat response
     */
    Mono<ChatResponse> sendChatMessage(ChatRequest chatRequest);
    
    /**
     * Send a direct message to Anthropic API
     * 
     * @param message The message to send
     * @param model The model to use (optional, will use default if null)
     * @return Mono containing the response from Anthropic
     */
    Mono<String> sendMessage(String message, String model);
}
