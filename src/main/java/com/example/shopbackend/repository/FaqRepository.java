package com.example.shopbackend.repository;

import com.example.shopbackend.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findAllByProductId(Long productId);
}
