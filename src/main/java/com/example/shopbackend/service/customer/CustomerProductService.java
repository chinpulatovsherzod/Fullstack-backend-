package com.example.shopbackend.service.customer;

import com.example.shopbackend.dto.ProductDetailDto;
import com.example.shopbackend.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {


    List<ProductDto> getAllProduct();

    List<ProductDto> searchProductByTitle(String title);

    ProductDetailDto getProductDetailById(Long productId);

}
