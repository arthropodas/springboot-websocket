package com.example.socket.springbootwebsocket.repository;

import com.example.socket.springbootwebsocket.entity.BallDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BallDeliveryRepository extends JpaRepository<BallDelivery, Long> {
    List<BallDelivery> findByMatchIdOrderByCreatedAtDesc(Long matchId);
}
