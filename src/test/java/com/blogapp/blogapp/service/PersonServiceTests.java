package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dataconverter.PersonConverter;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.repository.PersonRepository;
import com.blogapp.blogapp.service.impl.PersonServiceImpl;
import org.apache.catalina.mapper.Mapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonServiceImpl personService;
    @Mock
    private PersonConverter personConverter;
    Person person;
    PersonDto personDto;
    @BeforeEach
    public void setup(){

        personDto = new PersonDto();
        personDto.setName("Sinebi Innazo");
        personDto.setEmail("innazo@yahoo.com");
        personDto.setId(1L);
        personDto.setRole("CUSTOMER");
        personDto.setPassword("1234567");

        person = new Person();
        person.setId(1L);
        person.setName("Ebube");
        person.setEmail("ebube@yahoo.com");
        person.setRole("CUSTOMER");
        person.setPassword("12345");


    }

    @Test
    public void shouldSaveANewPerson(){

        when(personConverter.convertDtoToEntity(personDto)).thenReturn(person);
        Mockito.when(personRepository.save(any())).thenReturn(person);
        when(personConverter.convertEntityToDto(person)).thenReturn(personDto);
        ResponseEntity<PersonDto> responseEntity =  personService.creatPerson(personDto);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isNotNull();
        verify(personRepository, times(1)).save(any());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    public void shouldEditAAUser(){
        Mockito.when(personRepository.findById(1L)).thenReturn(of(person));
        when(personRepository.save(person)).thenReturn(person);
        when(personConverter.convertEntityToDto(person)).thenReturn(personDto);
        ResponseEntity<PersonDto> responseEntity =  personService.editPerson(1L, personDto);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isEqualTo("Sinebi Innazo");
    }

    @Test
    public void shouldGetAllUsers(){
        java.util.List<Person> personList = new ArrayList<>();
        Mockito.when(personRepository.findAll()).thenReturn(personList);

    }


}
