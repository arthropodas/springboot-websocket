package com.example.socket.springbootwebsocket.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String passwordConfirm,
        String email
) {}
