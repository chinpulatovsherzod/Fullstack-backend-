package com.example.shopbackend.controller.customer;


import com.example.shopbackend.dto.ProductDetailDto;
import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.service.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = customerProductService.getAllProduct();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search{name}")
    public ResponseEntity<List<ProductDto>> searchProductByTitle(@PathVariable String title){
        List<ProductDto> productDtos = customerProductService.searchProductByTitle(title);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetail(@PathVariable Long productId){
        ProductDetailDto productDetailDto = customerProductService.getProductDetailById(productId);
        if (productDetailDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailDto);
    }


}
