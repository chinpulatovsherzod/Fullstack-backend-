package com.example.shopbackend.model;


import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus status;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;
    private OrderStatus orderStatus;
    private String orderDescription;
  

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setAmount(amount);
        orderDto.setAddress(address);
        orderDto.setPayment(payment);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setDiscount(discount);
        orderDto.setTrackingId(trackingId);
        orderDto.setUserName(user.getName());
        orderDto.setOrderDescription(orderDto.getOrderDescription());
        if (coupon != null){
            orderDto.setCouponName(coupon.getName());
        }
        return orderDto;
    }
}
