package com.example.msaorderservice.service;

import com.example.msaorderservice.dto.OrderDto;
import com.example.msaorderservice.entity.OrderEntity;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDetails);

    OrderDto getOrderByOrderId(String orderId);

    Iterable<OrderEntity> getOrderByUserId(String userId);
}
