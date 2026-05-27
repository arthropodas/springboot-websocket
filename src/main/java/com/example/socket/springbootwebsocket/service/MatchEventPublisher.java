package com.example.socket.springbootwebsocket.service;

import com.example.socket.springbootwebsocket.dto.LiveEventPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MatchEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(MatchEventPublisher.class);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public MatchEventPublisher(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Long matchId, LiveEventPayload payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            redisTemplate.convertAndSend("match:" + matchId, json);
        } catch (JsonProcessingException e) {
            log.error("Failed to publish match event for match {}", matchId, e);
        }
    }
}
