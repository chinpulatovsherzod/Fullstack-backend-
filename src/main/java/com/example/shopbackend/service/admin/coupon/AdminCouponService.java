package com.example.shopbackend.service.admin.coupon;

import com.example.shopbackend.model.Coupon;

import java.util.List;

public interface AdminCouponService {
    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
