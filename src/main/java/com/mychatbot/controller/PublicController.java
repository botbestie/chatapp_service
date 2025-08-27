package com.mychatbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping("/info")
    public Mono<ResponseEntity<Map<String, Object>>> getServiceInfo() {
        Map<String, Object> info = Map.of(
                "serviceName", "Chat App Service",
                "version", "1.0.0",
                "description", "Spring Boot service for integrating with Anthropic API",
                "timestamp", LocalDateTime.now(),
                "status", "RUNNING"
        );
        
        return Mono.just(ResponseEntity.ok(info));
    }

    @GetMapping("/ping")
    public Mono<ResponseEntity<String>> ping() {
        return Mono.just(ResponseEntity.ok("pong"));
    }
}
