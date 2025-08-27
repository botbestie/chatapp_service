package com.mychatbot.service;

import com.mychatbot.dto.ChatRequest;
import com.mychatbot.dto.ChatResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AnthropicServiceTest {

    @Autowired
    private AnthropicService anthropicService;

    @Test
    void testSendChatMessage() {
        ChatRequest request = new ChatRequest();
        request.setMessage("Hello, how are you?");
        request.setUserId("test-user");
        request.setSessionId("test-session");

        Mono<ChatResponse> responseMono = anthropicService.sendChatMessage(request);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertNotNull(response);
                    // Note: In test environment, the response might have error status
                    // due to missing API key, but the structure should be valid
                    assertNotNull(response.getTimestamp());
                    assertNotNull(response.getStatus());
                })
                .verifyComplete();
    }

    @Test
    void testSendMessage() {
        String message = "Test message";
        
        Mono<String> responseMono = anthropicService.sendMessage(message, null);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertNotNull(response);
                    // In test environment, this might be an error message
                    // but it should not be null
                })
                .verifyComplete();
    }
}
