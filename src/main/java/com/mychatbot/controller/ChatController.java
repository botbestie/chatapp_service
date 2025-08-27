package com.mychatbot.controller;

import com.mychatbot.dto.ChatRequest;
import com.mychatbot.dto.ChatResponse;
import com.mychatbot.service.AnthropicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChatController {

    private final AnthropicService anthropicService;

    @PostMapping("/send")
    public Mono<ResponseEntity<ChatResponse>> sendChatMessage(@Valid @RequestBody ChatRequest chatRequest) {
        log.info("Received chat request from user: {}", chatRequest.getUserId());
        
        return anthropicService.sendChatMessage(chatRequest)
                .map(response -> {
                    log.info("Chat response generated successfully for user: {}", chatRequest.getUserId());
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(error -> {
                    log.error("Error processing chat request: {}", error.getMessage());
                    return Mono.just(ResponseEntity.internalServerError()
                            .body(ChatResponse.builder()
                                    .status("ERROR")
                                    .errorMessage("Internal server error occurred")
                                    .build()));
                });
    }

    @PostMapping("/send-simple")
    public Mono<ResponseEntity<String>> sendSimpleMessage(@RequestParam String message, 
                                                         @RequestParam(required = false) String model) {
        log.info("Received simple message request: {}", message);
        
        return anthropicService.sendMessage(message, model)
                .map(response -> {
                    log.info("Simple message response generated successfully");
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(error -> {
                    log.error("Error processing simple message: {}", error.getMessage());
                    return Mono.just(ResponseEntity.internalServerError()
                            .body("Error: " + error.getMessage()));
                });
    }

    @GetMapping("/health")
    public Mono<ResponseEntity<String>> healthCheck() {
        return Mono.just(ResponseEntity.ok("Chat service is running"));
    }
}
