package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface CategoryService {

    ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto, HttpSession session);
}
