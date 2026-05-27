package com.example.socket.springbootwebsocket.controller;

import com.example.socket.springbootwebsocket.dto.MatchCreateRequest;
import com.example.socket.springbootwebsocket.entity.User;
import com.example.socket.springbootwebsocket.security.AppUserDetails;
import com.example.socket.springbootwebsocket.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping({"/", ""})
    public List<Map<String, Object>> list() {
        return matchService.listMatches();
    }

    @PostMapping("/create/")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> create(
            @Valid @RequestBody MatchCreateRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        User admin = userDetails.getUser();
        return matchService.createMatch(request, admin);
    }

    @GetMapping("/{id}/")
    public Map<String, Object> detail(@PathVariable Long id) {
        return matchService.getMatch(id);
    }
}
