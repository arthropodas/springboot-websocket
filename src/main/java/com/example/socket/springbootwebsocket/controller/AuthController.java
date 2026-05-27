package com.example.socket.springbootwebsocket.controller;

import com.example.socket.springbootwebsocket.dto.LoginRequest;
import com.example.socket.springbootwebsocket.dto.RegisterRequest;
import com.example.socket.springbootwebsocket.entity.User;
import com.example.socket.springbootwebsocket.security.AppUserDetails;
import com.example.socket.springbootwebsocket.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/")
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login/")
    public Object login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/profile/")
    public Map<String, Object> profile(@AuthenticationPrincipal AppUserDetails userDetails) {
        User user = userDetails.getUser();
        return authService.profile(user);
    }
}
