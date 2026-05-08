package com.bookmydine.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class SystemController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "timestamp", Instant.now()
        );
    }

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
                "app", "BookMyDine",
                "version", "1.0.0"
        );
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}