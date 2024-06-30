package com.example.shopbackend.service.admin.adminorder;

import com.example.shopbackend.dto.AnalyticsResponse;
import com.example.shopbackend.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    List<OrderDto> getAllPlacedOrders();
    OrderDto changeOrderStatus(Long orderId, String status);
    AnalyticsResponse calculateAnalytics(Long orderId);
}
