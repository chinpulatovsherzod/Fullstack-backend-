package com.example.shopbackend.repository;

import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.enums.OrderStatus;
import com.example.shopbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndStatus(Long userId, OrderStatus status);

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByStatusIn(List<OrderStatus> statuses);

    List<Order> findByUserIdAndOrderStatusIn(Long user_id, Collection<OrderStatus> orderStatus);

    Optional<Order> findByTrackingId(UUID trackingId);

    List<Order> findByDateBetweenAndStatus(Date startMonth, Date endOfMonth, OrderStatus orderStatus);

    Long countByOrderStatus(OrderStatus orderStatus);
}
