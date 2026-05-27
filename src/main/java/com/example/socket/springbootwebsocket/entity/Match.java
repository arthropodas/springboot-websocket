package com.example.socket.springbootwebsocket.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cricket_match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String teamA;

    @Column(nullable = false)
    private String teamB;

    private String venue = "";

    private Integer totalOvers = 20;

    @Enumerated(EnumType.STRING)
    private MatchStatus status = MatchStatus.scheduled;

    private Integer teamARuns = 0;
    private Integer teamAWickets = 0;
    private BigDecimal teamAOvers = BigDecimal.ZERO;

    private Integer teamBRuns = 0;
    private Integer teamBWickets = 0;
    private BigDecimal teamBOvers = BigDecimal.ZERO;

    private String battingTeam = "A";
    private Integer currentInnings = 1;
    private Integer targetRuns;
    private String resultSummary = "";

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTeamA() { return teamA; }
    public void setTeamA(String teamA) { this.teamA = teamA; }
    public String getTeamB() { return teamB; }
    public void setTeamB(String teamB) { this.teamB = teamB; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public Integer getTotalOvers() { return totalOvers; }
    public void setTotalOvers(Integer totalOvers) { this.totalOvers = totalOvers; }
    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }
    public Integer getTeamARuns() { return teamARuns; }
    public void setTeamARuns(Integer teamARuns) { this.teamARuns = teamARuns; }
    public Integer getTeamAWickets() { return teamAWickets; }
    public void setTeamAWickets(Integer teamAWickets) { this.teamAWickets = teamAWickets; }
    public BigDecimal getTeamAOvers() { return teamAOvers; }
    public void setTeamAOvers(BigDecimal teamAOvers) { this.teamAOvers = teamAOvers; }
    public Integer getTeamBRuns() { return teamBRuns; }
    public void setTeamBRuns(Integer teamBRuns) { this.teamBRuns = teamBRuns; }
    public Integer getTeamBWickets() { return teamBWickets; }
    public void setTeamBWickets(Integer teamBWickets) { this.teamBWickets = teamBWickets; }
    public BigDecimal getTeamBOvers() { return teamBOvers; }
    public void setTeamBOvers(BigDecimal teamBOvers) { this.teamBOvers = teamBOvers; }
    public String getBattingTeam() { return battingTeam; }
    public void setBattingTeam(String battingTeam) { this.battingTeam = battingTeam; }
    public Integer getCurrentInnings() { return currentInnings; }
    public void setCurrentInnings(Integer currentInnings) { this.currentInnings = currentInnings; }
    public Integer getTargetRuns() { return targetRuns; }
    public void setTargetRuns(Integer targetRuns) { this.targetRuns = targetRuns; }
    public String getResultSummary() { return resultSummary; }
    public void setResultSummary(String resultSummary) { this.resultSummary = resultSummary; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
