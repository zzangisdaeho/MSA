package com.example.msaorderservice.repository;

import com.example.msaorderservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderId(String orderId);

    Iterable<OrderEntity> findByUserId(String userId);
}
