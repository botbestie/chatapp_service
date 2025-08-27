package com.mychatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnthropicRequest {
    
    private String model;
    
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    
    private Double temperature;
    
    private List<Message> messages;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
