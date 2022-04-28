package com.blogapp.blogapp.service.impl;

import com.blogapp.blogapp.dataconverter.PersonConverter;
import com.blogapp.blogapp.dto.LoginDto;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.blogapp.repository.PersonRepository;
import com.blogapp.blogapp.service.PersonService;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
   @Autowired
    private PersonConverter personConverter;

//    public PersonServiceImpl(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//
//    }

    @Override
    public ResponseEntity<PersonDto> creatPerson(PersonDto personDto) {
       Person person = personConverter.convertDtoToEntity(personDto);
       Person newPerson = personRepository.save(person);

       PersonDto response = personConverter.convertEntityToDto(newPerson);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PersonDto> editPerson(Long personId,PersonDto personDto) {
        Person person = personRepository.findById(personId).orElseThrow(()->new ResourceNotFoundException("Person","id",personId));
        person.setName(personDto.getName());
        person.setEmail(personDto.getEmail());
        person.setPassword(personDto.getPassword());
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(personConverter.convertEntityToDto(savedPerson),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PersonDto>> getAllPerson(HttpSession session) {
        Person person = (Person) session.getAttribute("person");
        if(person.getRole().equals("ADMIN")){
            List<Person> listOfPersons = personRepository.findAll();
            List<PersonDto> listOfPersonsDto = new ArrayList<>();
            for(Person pr:listOfPersons){
                PersonDto personDto = personConverter.convertEntityToDto(pr);
                listOfPersonsDto.add(personDto);
            }
            return new ResponseEntity<>(listOfPersonsDto,HttpStatus.OK);
        }
        return null;
    }

    @Override
    public ResponseEntity<PersonDto> getPersonById(Long personId) {
        Person person = personRepository.findById(personId).orElseThrow(()->new ResourceNotFoundException("Person","id",personId));
        return new ResponseEntity<>(personConverter.convertEntityToDto(person),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> loginPerson(LoginDto loginDto, HttpSession session) {
        Optional<Person> person = personRepository.findByEmail(loginDto.getEmail());
        if(person.isPresent()){
            Person loggedInPerson = person.get();
            if(loggedInPerson.getPassword().equals(loginDto.getPassword())){
                session.setAttribute("person",loggedInPerson);
                return new ResponseEntity<>("Login was successful",HttpStatus.OK);
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<String> logOut(HttpSession session) {
        Person person = (Person) session.getAttribute("person");
        session.invalidate();
        return new ResponseEntity<>(person.getName()+" has successfully logged out",HttpStatus.OK);
    }


}
