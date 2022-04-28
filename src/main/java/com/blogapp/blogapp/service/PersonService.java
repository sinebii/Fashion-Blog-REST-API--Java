package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dto.LoginDto;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.entity.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {
    ResponseEntity<PersonDto> creatPerson(PersonDto personDto);
    ResponseEntity<PersonDto> editPerson(Long personId,PersonDto personDto);
    ResponseEntity<List<PersonDto>> getAllPerson();
    PersonDto getPersonById(Long personId);
    PersonDto loginPerson(LoginDto loginDto);
}
