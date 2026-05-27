package com.example.socket.springbootwebsocket.websocket;

import com.example.socket.springbootwebsocket.security.JwtService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    public JwtHandshakeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String token = extractToken(request);
        if (token == null || !jwtService.isValid(token)) {
            return false;
        }
        Long userId = jwtService.extractUserId(token);
        Long matchId = extractMatchId(request.getURI().getPath());
        if (matchId == null) {
            return false;
        }
        attributes.put("userId", userId);
        attributes.put("matchId", matchId);
        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
    }

    private String extractToken(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String q = servletRequest.getServletRequest().getQueryString();
            if (q != null) {
                for (String part : q.split("&")) {
                    if (part.startsWith("token=")) {
                        return part.substring(6);
                    }
                }
            }
        }
        return null;
    }

    private Long extractMatchId(String path) {
        // /ws/match/5/ or /ws/match/5
        String[] parts = path.split("/");
        for (int i = 0; i < parts.length; i++) {
            if ("match".equals(parts[i]) && i + 1 < parts.length) {
                try {
                    return Long.parseLong(parts[i + 1]);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
