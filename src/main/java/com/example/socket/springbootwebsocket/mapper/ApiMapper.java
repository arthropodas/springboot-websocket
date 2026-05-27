package com.example.socket.springbootwebsocket.mapper;

import com.example.socket.springbootwebsocket.entity.BallDelivery;
import com.example.socket.springbootwebsocket.entity.Match;
import com.example.socket.springbootwebsocket.entity.Role;
import com.example.socket.springbootwebsocket.entity.User;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ApiMapper {

    private ApiMapper() {}

    public static Map<String, Object> userToMap(User user) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", user.getId());
        m.put("username", user.getUsername());
        m.put("email", user.getEmail() != null ? user.getEmail() : "");
        m.put("role", user.getRole().name().toLowerCase());
        m.put("isAdmin", user.getRole() == Role.ADMIN);
        m.put("isSubscriber", user.getRole() == Role.SUBSCRIBER);
        return m;
    }

    public static Map<String, Object> matchToMap(Match match) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", match.getId());
        m.put("title", match.getTitle());
        m.put("teamA", match.getTeamA());
        m.put("teamB", match.getTeamB());
        m.put("venue", match.getVenue());
        m.put("totalOvers", match.getTotalOvers());
        m.put("status", match.getStatus().name());
        m.put("teamARuns", match.getTeamARuns());
        m.put("teamAWickets", match.getTeamAWickets());
        m.put("teamAOvers", match.getTeamAOvers());
        m.put("teamBRuns", match.getTeamBRuns());
        m.put("teamBWickets", match.getTeamBWickets());
        m.put("teamBOvers", match.getTeamBOvers());
        m.put("battingTeam", match.getBattingTeam());
        m.put("currentInnings", match.getCurrentInnings());
        m.put("targetRuns", match.getTargetRuns());
        m.put("resultSummary", match.getResultSummary());
        m.put("liveScoreDisplay", liveScoreDisplay(match));
        if (match.getCreatedBy() != null) {
            m.put("createdByUsername", match.getCreatedBy().getUsername());
        }
        m.put("createdAt", match.getCreatedAt().toString());
        m.put("updatedAt", match.getUpdatedAt().toString());
        return m;
    }

    public static Map<String, Object> ballToMap(BallDelivery ball) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", ball.getId());
        m.put("matchId", ball.getMatch().getId());
        m.put("innings", ball.getInnings());
        m.put("overNumber", ball.getOverNumber());
        m.put("ballNumber", ball.getBallNumber());
        m.put("overBall", ball.getOverNumber() + "." + ball.getBallNumber());
        m.put("runs", ball.getRuns());
        m.put("isWicket", ball.getIsWicket());
        m.put("wicketType", ball.getWicketType());
        m.put("batsmanName", ball.getBatsmanName());
        m.put("bowlerName", ball.getBowlerName());
        m.put("extrasType", ball.getExtrasType());
        m.put("commentary", ball.getCommentary());
        m.put("eventSummary", ball.getEventSummary());
        if (ball.getCreatedBy() != null) {
            m.put("createdByUsername", ball.getCreatedBy().getUsername());
        }
        m.put("createdAt", ball.getCreatedAt().toString());
        return m;
    }

    private static String liveScoreDisplay(Match match) {
        if ("A".equals(match.getBattingTeam())) {
            return match.getTeamA() + ": " + match.getTeamARuns() + "/" + match.getTeamAWickets()
                    + " (" + match.getTeamAOvers() + ")";
        }
        return match.getTeamB() + ": " + match.getTeamBRuns() + "/" + match.getTeamBWickets()
                + " (" + match.getTeamBOvers() + ")";
    }
}
