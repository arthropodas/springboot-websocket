package com.example.socket.springbootwebsocket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BallRequest(
        @NotNull Integer innings,
        @NotNull Integer overNumber,
        @NotNull Integer ballNumber,
        @NotNull Integer runs,
        Boolean isWicket,
        String wicketType,
        String batsmanName,
        String bowlerName,
        String extrasType,
        @NotBlank String commentary,
        @NotBlank String eventSummary
) {}
