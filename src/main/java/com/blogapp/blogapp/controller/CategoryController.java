package com.blogapp.blogapp.controller;

import com.blogapp.blogapp.dto.CategoryDto;
import com.blogapp.blogapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/blog/post")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // create a new Category
    @PostMapping("/create-category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto, HttpSession session){
       return categoryService.createCategory(categoryDto, session);
    }
}
