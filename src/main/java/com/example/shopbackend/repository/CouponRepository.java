package com.example.shopbackend.repository;

import com.example.shopbackend.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default boolean existByCode(String code) {
        return false;
    }


    Optional<Coupon> findByCode(String code);
}
