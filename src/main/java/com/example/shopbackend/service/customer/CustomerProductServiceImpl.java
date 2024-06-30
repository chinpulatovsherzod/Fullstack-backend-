package com.example.shopbackend.service.customer;

import com.example.shopbackend.dto.ProductDetailDto;
import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.model.FAQ;
import com.example.shopbackend.model.Product;
import com.example.shopbackend.model.Review;
import com.example.shopbackend.repository.FaqRepository;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{


    private final ProductRepository productRepository;
    private final FaqRepository faqRepository;
    private final ReviewRepository reviewRepository;


     public List<ProductDto> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }


    public List<ProductDto> searchProductByTitle(String name){
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDetailDto getProductDetailById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            List<FAQ> faqList = faqRepository.findAllByProductId(productId);
            List<Review> reviewList = reviewRepository.findAllByProductId(productId);

            ProductDetailDto productDetailDto = new ProductDetailDto();

            productDetailDto.setProductDto(optionalProduct.get().getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFaqDto).collect(Collectors.toList()));
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));

            return productDetailDto;

        }
        return null;
    }
}
