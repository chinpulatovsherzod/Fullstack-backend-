package com.example.shopbackend.service.admin;

import com.example.shopbackend.dto.CategoryDto;
import com.example.shopbackend.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategories();
}
