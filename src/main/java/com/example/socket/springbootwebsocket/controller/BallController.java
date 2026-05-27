package com.example.socket.springbootwebsocket.controller;

import com.example.socket.springbootwebsocket.dto.BallRequest;
import com.example.socket.springbootwebsocket.entity.User;
import com.example.socket.springbootwebsocket.security.AppUserDetails;
import com.example.socket.springbootwebsocket.service.BallService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matches/{matchId}/balls")
public class BallController {

    private final BallService ballService;

    public BallController(BallService ballService) {
        this.ballService = ballService;
    }

    @GetMapping({"/", ""})
    public List<Map<String, Object>> list(@PathVariable Long matchId) {
        return ballService.listBalls(matchId);
    }

    @PostMapping({"/", ""})
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> create(
            @PathVariable Long matchId,
            @Valid @RequestBody BallRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        User admin = userDetails.getUser();
        return ballService.addBall(matchId, request, admin);
    }
}
