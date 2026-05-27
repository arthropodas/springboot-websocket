package com.example.socket.springbootwebsocket.config;

import com.example.socket.springbootwebsocket.websocket.JwtHandshakeInterceptor;
import com.example.socket.springbootwebsocket.websocket.MatchWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MatchWebSocketHandler matchWebSocketHandler;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    public WebSocketConfig(
            MatchWebSocketHandler matchWebSocketHandler,
            JwtHandshakeInterceptor jwtHandshakeInterceptor) {
        this.matchWebSocketHandler = matchWebSocketHandler;
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(matchWebSocketHandler, "/ws/match/{matchId}/")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*");
    }
}
