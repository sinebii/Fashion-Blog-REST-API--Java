package com.blogapp.blogapp.dataconverter;

import com.blogapp.blogapp.dto.CategoryDto;
import com.blogapp.blogapp.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public Category convertDtoToEntity(CategoryDto categoryDto){

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    public CategoryDto convertEntityToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }
}
