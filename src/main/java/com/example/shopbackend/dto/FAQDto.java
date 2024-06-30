package com.example.shopbackend.dto;

import com.example.shopbackend.model.Product;
import lombok.Data;

@Data
public class FAQDto {

    private Long id;
    private String question;
    private String answer;
    private Product product;
}
