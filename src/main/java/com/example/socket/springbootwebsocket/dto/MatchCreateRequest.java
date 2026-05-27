package com.example.socket.springbootwebsocket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MatchCreateRequest(
        @NotBlank String title,
        @NotBlank String teamA,
        @NotBlank String teamB,
        String venue,
        @NotNull Integer totalOvers,
        String battingTeam
) {}
