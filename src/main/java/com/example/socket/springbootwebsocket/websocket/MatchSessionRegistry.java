package com.example.socket.springbootwebsocket.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class MatchSessionRegistry {

    private static final Logger log = LoggerFactory.getLogger(MatchSessionRegistry.class);

    private final Map<Long, CopyOnWriteArraySet<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    public void add(Long matchId, WebSocketSession session) {
        rooms.computeIfAbsent(matchId, id -> new CopyOnWriteArraySet<>()).add(session);
    }

    public void remove(Long matchId, WebSocketSession session) {
        CopyOnWriteArraySet<WebSocketSession> set = rooms.get(matchId);
        if (set != null) {
            set.remove(session);
            if (set.isEmpty()) {
                rooms.remove(matchId);
            }
        }
    }

    public void broadcast(Long matchId, String json) {
        CopyOnWriteArraySet<WebSocketSession> set = rooms.get(matchId);
        if (set == null) {
            return;
        }
        TextMessage message = new TextMessage(json);
        for (WebSocketSession session : set) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    log.warn("Failed to send WS message to session {}", session.getId(), e);
                }
            }
        }
    }
}
