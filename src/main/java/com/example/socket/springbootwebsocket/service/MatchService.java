package com.example.socket.springbootwebsocket.service;

import com.example.socket.springbootwebsocket.dto.MatchCreateRequest;
import com.example.socket.springbootwebsocket.entity.Match;
import com.example.socket.springbootwebsocket.entity.MatchStatus;
import com.example.socket.springbootwebsocket.entity.User;
import com.example.socket.springbootwebsocket.mapper.ApiMapper;
import com.example.socket.springbootwebsocket.repository.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Map<String, Object>> listMatches() {
        return matchRepository.findAllWithCreatedBy().stream()
                .map(ApiMapper::matchToMap)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getMatch(Long id) {
        Match match = findMatch(id);
        return ApiMapper.matchToMap(match);
    }

    public Map<String, Object> createMatch(MatchCreateRequest request, User admin) {
        Match match = new Match();
        match.setTitle(request.title());
        match.setTeamA(request.teamA());
        match.setTeamB(request.teamB());
        match.setVenue(request.venue() != null ? request.venue() : "");
        match.setTotalOvers(request.totalOvers() != null ? request.totalOvers() : 20);
        if (request.battingTeam() != null && !request.battingTeam().isBlank()) {
            match.setBattingTeam(request.battingTeam());
        }
        match.setCreatedBy(admin);
        match.setCreatedAt(Instant.now());
        match.setUpdatedAt(Instant.now());
        matchRepository.save(match);
        return ApiMapper.matchToMap(match);
    }

    public Match findMatch(Long id) {
        return matchRepository.findByIdWithCreatedBy(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }
}
