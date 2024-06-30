package com.example.shopbackend.repository;

import com.example.shopbackend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    List<Wishlist> findAllByUserId(Long userId);
}
