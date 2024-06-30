package com.example.shopbackend.service.admin.coupon;

import com.example.shopbackend.exceptions.ValidationException;
import com.example.shopbackend.model.Coupon;
import com.example.shopbackend.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService{

    private final CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon){
        if (couponRepository.existByCode(coupon.getCode())){
            throw new ValidationException("Coupon already exists");
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
}
