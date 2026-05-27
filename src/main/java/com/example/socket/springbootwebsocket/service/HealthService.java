package com.example.socket.springbootwebsocket.service;

import org.springframework.stereotype.Service;

import java.util.Map;


public interface HealthService {
    public Map<String,String> health();
}
