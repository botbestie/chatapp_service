package com.mychatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnthropicResponse {
    
    private String id;
    private String type;
    private String role;
    private List<Content> content;
    
    @JsonProperty("model")
    private String model;
    
    @JsonProperty("stop_reason")
    private String stopReason;
    
    @JsonProperty("stop_sequence")
    private String stopSequence;
    
    @JsonProperty("usage")
    private Usage usage;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private String type;
        private String text;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Usage {
        @JsonProperty("input_tokens")
        private Integer inputTokens;
        
        @JsonProperty("output_tokens")
        private Integer outputTokens;
    }
}
