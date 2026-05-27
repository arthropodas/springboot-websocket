package com.example.socket.springbootwebsocket.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "cricket_ball_delivery",
        uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "innings", "over_number", "ball_number"}))
public class BallDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    private Integer innings = 1;
    private Integer overNumber;
    private Integer ballNumber;
    private Integer runs = 0;
    private Boolean isWicket = false;
    private String wicketType = "";
    private String batsmanName = "";
    private String bowlerName = "";
    private String extrasType = "none";

    @Column(columnDefinition = "TEXT")
    private String commentary;

    private String eventSummary;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }
    public Integer getInnings() { return innings; }
    public void setInnings(Integer innings) { this.innings = innings; }
    public Integer getOverNumber() { return overNumber; }
    public void setOverNumber(Integer overNumber) { this.overNumber = overNumber; }
    public Integer getBallNumber() { return ballNumber; }
    public void setBallNumber(Integer ballNumber) { this.ballNumber = ballNumber; }
    public Integer getRuns() { return runs; }
    public void setRuns(Integer runs) { this.runs = runs; }
    public Boolean getIsWicket() { return isWicket; }
    public void setIsWicket(Boolean wicket) { isWicket = wicket; }
    public String getWicketType() { return wicketType; }
    public void setWicketType(String wicketType) { this.wicketType = wicketType; }
    public String getBatsmanName() { return batsmanName; }
    public void setBatsmanName(String batsmanName) { this.batsmanName = batsmanName; }
    public String getBowlerName() { return bowlerName; }
    public void setBowlerName(String bowlerName) { this.bowlerName = bowlerName; }
    public String getExtrasType() { return extrasType; }
    public void setExtrasType(String extrasType) { this.extrasType = extrasType; }
    public String getCommentary() { return commentary; }
    public void setCommentary(String commentary) { this.commentary = commentary; }
    public String getEventSummary() { return eventSummary; }
    public void setEventSummary(String eventSummary) { this.eventSummary = eventSummary; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
