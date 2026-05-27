package com.example.socket.springbootwebsocket.repository;

import com.example.socket.springbootwebsocket.entity.Match;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT m FROM Match m LEFT JOIN FETCH m.createdBy ORDER BY m.createdAt DESC")
    List<Match> findAllWithCreatedBy();

    @Query("SELECT m FROM Match m LEFT JOIN FETCH m.createdBy WHERE m.id = :id")
    Optional<Match> findByIdWithCreatedBy(@Param("id") Long id);
}
