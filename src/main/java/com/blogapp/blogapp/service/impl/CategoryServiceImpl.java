package com.blogapp.blogapp.service.impl;

import com.blogapp.blogapp.dataconverter.CategoryConverter;
import com.blogapp.blogapp.dto.CategoryDto;
import com.blogapp.blogapp.entity.Category;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.repository.CategoryRepository;
import com.blogapp.blogapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;


    @Override
    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto, HttpSession session) {
        Person person = (Person) session.getAttribute("person");
        if(person.getRole().equals("ADMIN")){
            Category category = categoryConverter.convertDtoToEntity(categoryDto);
            Category category1 = categoryRepository.save(category);
            return new ResponseEntity<>(categoryConverter.convertEntityToDto(category1), HttpStatus.CREATED);
        }

        return null;
    }
}
