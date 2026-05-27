package com.example.socket.springbootwebsocket.websocket;

import com.example.socket.springbootwebsocket.repository.MatchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
public class MatchWebSocketHandler extends TextWebSocketHandler {

    private final MatchSessionRegistry registry;
    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;

    public MatchWebSocketHandler(
            MatchSessionRegistry registry,
            MatchRepository matchRepository,
            ObjectMapper objectMapper) {
        this.registry = registry;
        this.matchRepository = matchRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long matchId = (Long) session.getAttributes().get("matchId");
        if (!matchRepository.existsById(matchId)) {
            session.close(new CloseStatus(4004));
            return;
        }
        registry.add(matchId, session);
        String payload = objectMapper.writeValueAsString(Map.of(
                "event", "connected",
                "match_id", matchId,
                "message", "Subscribed to live match updates"
        ));
        session.sendMessage(new TextMessage(payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long matchId = (Long) session.getAttributes().get("matchId");
        if (matchId != null) {
            registry.remove(matchId, session);
        }
    }
}
