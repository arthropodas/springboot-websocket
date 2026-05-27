package com.example.socket.springbootwebsocket.websocket;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MatchRedisSubscriber implements MessageListener {

    private final MatchSessionRegistry registry;

    public MatchRedisSubscriber(MatchSessionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        if (!channel.startsWith("match:")) {
            return;
        }
        Long matchId = Long.parseLong(channel.substring("match:".length()));
        registry.broadcast(matchId, body);
    }
}
