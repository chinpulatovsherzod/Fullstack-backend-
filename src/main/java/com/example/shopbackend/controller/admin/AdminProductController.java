package com.example.shopbackend.controller.admin;

import com.example.shopbackend.dto.FAQDto;
import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.service.admin.faq.FaqService;
import com.example.shopbackend.service.adminproduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    @Autowired
    private final AdminProductService adminProductService;

    @Autowired
    private final FaqService faqService;


    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = adminProductService.getAllProduct();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productDtos = adminProductService.getAllProductByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        boolean deleted = adminProductService.deleteProduct(productId);
        if (deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFaq(@PathVariable Long productId, @RequestBody FAQDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFaq(productId,faqDto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto productDto = adminProductService.getProductById(productId);
        if (productDto != null){
            return ResponseEntity.ok(productDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ProductDto> updatateProduct(@PathVariable Long productId, @ModelAttribute ProductDto productDto) throws IOException{
        ProductDto updateProduct = adminProductService.updateProduct(productId,productDto);
        if (updateProduct != null){
            return ResponseEntity.ok(updateProduct);
        }
        return ResponseEntity.notFound().build();
    }
}
