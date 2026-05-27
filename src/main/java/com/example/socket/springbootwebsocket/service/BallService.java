package com.example.socket.springbootwebsocket.service;

import com.example.socket.springbootwebsocket.dto.BallRequest;
import com.example.socket.springbootwebsocket.dto.LiveEventPayload;
import com.example.socket.springbootwebsocket.entity.BallDelivery;
import com.example.socket.springbootwebsocket.entity.Match;
import com.example.socket.springbootwebsocket.entity.MatchStatus;
import com.example.socket.springbootwebsocket.entity.User;
import com.example.socket.springbootwebsocket.mapper.ApiMapper;
import com.example.socket.springbootwebsocket.repository.BallDeliveryRepository;
import com.example.socket.springbootwebsocket.repository.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BallService {

    private final BallDeliveryRepository ballRepository;
    private final MatchRepository matchRepository;
    private final MatchEventPublisher eventPublisher;

    public BallService(
            BallDeliveryRepository ballRepository,
            MatchRepository matchRepository,
            MatchEventPublisher eventPublisher) {
        this.ballRepository = ballRepository;
        this.matchRepository = matchRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Map<String, Object>> listBalls(Long matchId) {
        ensureMatchExists(matchId);
        return ballRepository.findByMatchIdOrderByCreatedAtDesc(matchId).stream()
                .map(ApiMapper::ballToMap)
                .collect(Collectors.toList());
    }

    @Transactional
    public Map<String, Object> addBall(Long matchId, BallRequest request, User admin) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));

        BallDelivery ball = new BallDelivery();
        ball.setMatch(match);
        ball.setInnings(request.innings());
        ball.setOverNumber(request.overNumber());
        ball.setBallNumber(request.ballNumber());
        ball.setRuns(request.runs());
        ball.setIsWicket(Boolean.TRUE.equals(request.isWicket()));
        ball.setWicketType(request.wicketType() != null ? request.wicketType() : "");
        ball.setBatsmanName(request.batsmanName() != null ? request.batsmanName() : "");
        ball.setBowlerName(request.bowlerName() != null ? request.bowlerName() : "");
        ball.setExtrasType(request.extrasType() != null ? request.extrasType() : "none");
        ball.setCommentary(request.commentary());
        ball.setEventSummary(request.eventSummary());
        ball.setCreatedBy(admin);

        try {
            ballRepository.save(ball);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ball already exists for this over/ball number");
        }

        updateScore(match, ball);
        match.setUpdatedAt(Instant.now());
        matchRepository.save(match);

        Map<String, Object> matchMap = ApiMapper.matchToMap(match);
        Map<String, Object> ballMap = ApiMapper.ballToMap(ball);
        eventPublisher.publish(matchId, new LiveEventPayload("ball_added", matchMap, ballMap));

        return ballMap;
    }

    private void ensureMatchExists(Long matchId) {
        if (!matchRepository.existsById(matchId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
    }

    private boolean isLegalBall(String extrasType) {
        return List.of("none", "bye", "leg_bye").contains(extrasType);
    }

    private void updateScore(Match match, BallDelivery ball) {
        if ("A".equals(match.getBattingTeam())) {
            match.setTeamARuns(match.getTeamARuns() + ball.getRuns());
            if (Boolean.TRUE.equals(ball.getIsWicket())) {
                match.setTeamAWickets(match.getTeamAWickets() + 1);
            }
            if (isLegalBall(ball.getExtrasType())) {
                match.setTeamAOvers(nextOver(match.getTeamAOvers()));
            }
        } else {
            match.setTeamBRuns(match.getTeamBRuns() + ball.getRuns());
            if (Boolean.TRUE.equals(ball.getIsWicket())) {
                match.setTeamBWickets(match.getTeamBWickets() + 1);
            }
            if (isLegalBall(ball.getExtrasType())) {
                match.setTeamBOvers(nextOver(match.getTeamBOvers()));
            }
        }
        if (match.getStatus() == MatchStatus.scheduled) {
            match.setStatus(MatchStatus.live);
        }
    }

    private BigDecimal nextOver(BigDecimal current) {
        int balls = current.multiply(BigDecimal.TEN).intValue() + 1;
        int whole = balls / 10;
        int frac = balls % 10;
        if (frac == 0 && balls > 0) {
            return BigDecimal.valueOf(whole);
        }
        return new BigDecimal(whole + "." + frac);
    }
}
