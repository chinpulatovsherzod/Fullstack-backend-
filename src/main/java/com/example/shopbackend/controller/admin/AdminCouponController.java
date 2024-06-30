package com.example.shopbackend.controller.admin;


import com.example.shopbackend.exceptions.ValidationException;
import com.example.shopbackend.model.Coupon;
import com.example.shopbackend.service.admin.coupon.AdminCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    public final AdminCouponService adminCouponService;


    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon){
        try {
            Coupon createCoupon = adminCouponService.createCoupon(coupon);
            return ResponseEntity.ok(createCoupon);
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Coupon>>  getAllCoupons(){
        return ResponseEntity.ok(adminCouponService.getAllCoupons());
    }


}
