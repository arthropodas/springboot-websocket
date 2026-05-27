package com.example.socket.springbootwebsocket.dto;

import java.util.Map;

public class LiveEventPayload {
    private String event;
    private Map<String, Object> match;
    private Map<String, Object> ball;

    public LiveEventPayload() {}

    public LiveEventPayload(String event, Map<String, Object> match, Map<String, Object> ball) {
        this.event = event;
        this.match = match;
        this.ball = ball;
    }

    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }
    public Map<String, Object> getMatch() { return match; }
    public void setMatch(Map<String, Object> match) { this.match = match; }
    public Map<String, Object> getBall() { return ball; }
    public void setBall(Map<String, Object> ball) { this.ball = ball; }
}
