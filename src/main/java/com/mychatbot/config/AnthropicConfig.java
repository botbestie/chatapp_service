package com.mychatbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "anthropic.api")
public class AnthropicConfig {
    
    private String key;
    private String baseUrl;
    private String model;
    private Integer maxTokens;
    private Double temperature;
}
