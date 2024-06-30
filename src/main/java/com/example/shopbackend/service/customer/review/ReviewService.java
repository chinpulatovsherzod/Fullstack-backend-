package com.example.shopbackend.service.customer.review;

import com.example.shopbackend.dto.OrderedProductsResponseDto;
import com.example.shopbackend.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {

    OrderedProductsResponseDto getOrderedProductsByOrdersId(Long orderId);
    ReviewDto getReview(ReviewDto reviewDto) throws IOException;
}
