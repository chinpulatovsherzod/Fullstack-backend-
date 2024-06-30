package com.example.shopbackend.controller.customer;


import com.example.shopbackend.dto.OrderedProductsResponseDto;
import com.example.shopbackend.dto.ReviewDto;
import com.example.shopbackend.service.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> getOrderedProducts(@PathVariable Long orderId){
        return ResponseEntity.ok(reviewService.getOrderedProductsByOrdersId(orderId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto reviewDto1 = reviewService.getReview(reviewDto);
        if (reviewDto1 == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went wrong");
        }
         return  ResponseEntity.status(HttpStatus.CREATED).body(reviewDto1 );
    }

}
