package com.example.socket.springbootwebsocket.service.impl;

import com.example.socket.springbootwebsocket.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class HealthServiceImpl implements HealthService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;

    @Override
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();

        response.put("status", "ok");

        // Database check
        try {
            Integer dbStatus = jdbcTemplate.queryForObject(
                    "SELECT 1",
                    Integer.class
            );

            response.put(
                    "database",
                    dbStatus != null && dbStatus == 1
                            ? "up"
                            : "down"
            );

        } catch (Exception e) {
            response.put("database", "down");
            response.put("databaseError", e.getMessage());
        }

        // Redis check
        try {

            if (redisTemplate != null &&
                    redisTemplate.getConnectionFactory() != null) {

                String redisPing = redisTemplate
                        .getConnectionFactory()
                        .getConnection()
                        .ping();

                response.put(
                        "redis",
                        "PONG".equalsIgnoreCase(redisPing)
                                ? "up"
                                : "down"
                );

            } else {
                response.put("redis", "not-configured");
            }

        } catch (Exception e) {
            response.put("redis", "down");
            response.put("redisError", e.getMessage());
        }

        return response;
    }

}
