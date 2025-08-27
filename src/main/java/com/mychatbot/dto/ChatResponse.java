package com.mychatbot.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    
    private String message;
    private String userId;
    private String sessionId;
    private LocalDateTime timestamp;
    private String model;
    private Integer inputTokens;
    private Integer outputTokens;
    private String status;
    private String errorMessage;
}
