package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dataconverter.PersonConverter;
import com.blogapp.blogapp.dto.LoginDto;
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

import javax.servlet.http.HttpSession;
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
    LoginDto loginDto;
    
    @Mock
    private HttpSession session;
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
        person.setName("CUSTOMER");
        person.setEmail("ebube@yahoo.com");
        person.setRole("ADMIN");
        person.setPassword("12345");

        loginDto = new LoginDto();
        loginDto.setEmail("innazo@yahoo.com");
        loginDto.setPassword("1234567");


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
    void canGetAllCustomers() {
        List<Person> personList = List.of(new Person(2L, "Chisom","sinebi.innazo@yahoo.com", "CUSTOMER", "1234"));
        when(personRepository.findAll()).thenReturn(personList);
        when(session.getAttribute("person")).thenReturn(person);
        ResponseEntity<List<PersonDto>> response = personService.getAllPerson(session);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(personList.size());
    }

    @Test
    void shouldGetOnePersonById(){
        Mockito.when(personRepository.findById(1L)).thenReturn(of(person));
        when(personConverter.convertEntityToDto(person)).thenReturn(personDto);
        ResponseEntity<PersonDto> responseEntity =  personService.getPersonById(1L);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isEqualTo("Sinebi Innazo");

    }

//    @Test
//    void shouldFindPersonByEmailAndPassword(){
//        Mockito.when(personRepository.findById(1L)).thenReturn(of(person));
//        when(personConverter.convertEntityToDto(person)).thenReturn(personDto);
//        ResponseEntity<String> responseEntity =  personService.loginPerson(loginDto,session);
//        
//    }


}
