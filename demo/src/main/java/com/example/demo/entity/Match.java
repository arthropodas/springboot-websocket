package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "match")
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String matchName;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();


}
