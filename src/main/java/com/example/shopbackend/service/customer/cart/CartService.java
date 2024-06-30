package com.example.shopbackend.service.customer.cart;

import com.example.shopbackend.dto.AddProductInCartDto;
import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto applyCoupon(Long userId, String code);
    OrderDto increaseProductQuality(AddProductInCartDto addProductInCartDto);
    OrderDto decreaseProductQuality(AddProductInCartDto addProductInCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto> getMyPlacedOrders(Long userId);
    OrderDto searchOrderByTrackingId(UUID trackingId);
}
