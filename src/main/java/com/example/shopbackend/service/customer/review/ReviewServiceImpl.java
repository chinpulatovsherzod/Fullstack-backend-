package com.example.shopbackend.service.customer.review;


import com.example.shopbackend.dto.OrderedProductsResponseDto;
import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.dto.ReviewDto;
import com.example.shopbackend.model.*;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.ReviewRepository;
import com.example.shopbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public OrderedProductsResponseDto getOrderedProductsByOrdersId(Long orderId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        OrderedProductsResponseDto responseDto = new OrderedProductsResponseDto();
        if (optionalOrder.isPresent()){
            responseDto.setOrderAmount(optionalOrder.get().getAmount());

            List<ProductDto> productDtos = new ArrayList<>();
            for (CartItems cartItems: optionalOrder.get().getCartItems()){
                ProductDto productDto = new ProductDto();
                productDto.setId(cartItems.getId());
                productDto.setName(cartItems.getProduct().getName());
                productDto.setPrice(cartItems.getPrice());
                productDto.setQuantity(cartItems.getQuantity());

                productDto.setByteImg(cartItems.getProduct().getImg());
                productDtos.add(productDto);
            }
            responseDto.setProductDtoList(productDtos);
        }
        return responseDto;
    }

    public ReviewDto getReview(ReviewDto reviewDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(reviewDto.getProductId());
        Optional<User> optionalUser = userRepository.findById(reviewDto.getUserId());
        if (optionalUser.isPresent() && optionalProduct.isPresent()){
            Review review = new Review();

            review.setDescription(reviewDto.getDescription());
            review.setProduct(optionalProduct.get());
            review.setImg(reviewDto.getImg().getBytes());
            review.setUser(optionalUser.get());
            review.setRating(Long.valueOf(String.valueOf(reviewDto.getRating())));
            return reviewRepository.save(review).getDto();
        }
        return null;
    }
}
