package com.blogapp.blogapp.service.impl;

import com.blogapp.blogapp.dataconverter.CategoryConverter;
import com.blogapp.blogapp.dto.CategoryDto;
import com.blogapp.blogapp.entity.Category;
import com.blogapp.blogapp.repository.CategoryRepository;
import com.blogapp.blogapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = categoryConverter.convertDtoToEntity(categoryDto);
        Category category1 = categoryRepository.save(category);

        CategoryDto response = categoryConverter.convertEntityToDto(category1);
        return response;

    }
}
