package com.example.socket.springbootwebsocket.controller;

import com.example.socket.springbootwebsocket.service.HealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping({"/health", "/health/"})
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(healthService.health());
    }
}
