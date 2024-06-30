package com.example.shopbackend.service.adminproduct;

import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.model.Category;
import com.example.shopbackend.model.Product;
import com.example.shopbackend.repository.CategoryRepository;
import com.example.shopbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    public ProductDto addProduct(ProductDto productDto) throws IOException{
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(product.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    public List<ProductDto> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductByName(String name){
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get().getDto();
        } else {
            return null;
        }
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException{
        Optional<Product> product = productRepository.findById(productId);
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (product.isPresent() && category.isPresent()){
            Product product1 = product.get();
            product1.setName(productDto.getName());
            product1.setDescription(productDto.getDescription());
            product1.setPrice(productDto.getPrice());
            if (productDto.getImg() != null){
                product1.setImg(productDto.getByteImg());
            }
            return productRepository.save(product1).getDto();
        }else {
            return null;
        }
    }
}
