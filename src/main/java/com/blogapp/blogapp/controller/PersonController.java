package com.blogapp.blogapp.controller;
import com.blogapp.blogapp.dto.LoginDto;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/blog/person")
public class PersonController {

    @Autowired
    private PersonService personService;


    @PostMapping("/create")
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto){
        return personService.creatPerson(personDto);
    }

    @PutMapping("/update")
    public ResponseEntity<String> editPerson(@RequestBody PersonDto personDto,HttpSession session){
        PersonDto personDto1 = (PersonDto) session.getAttribute("person");
        if(personDto1!=null){
            personService.editPerson(personDto1.getId(),personDto);
        }
        return null;
    }

    @GetMapping("/all-persons")
    public ResponseEntity<List<PersonDto>> getAllPersons(HttpSession session){
            return personService.getAllPerson(session);
    }
    @GetMapping("/get-one-person/{personId}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable(value = "personId") Long personId, HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto!=null){
            return  personService.getPersonById(personId);
        }
        return null;
    }
    @PostMapping("/login")
   public ResponseEntity<String> loginAUser(@RequestBody LoginDto loginDto,HttpSession session){
        return  personService.loginPerson(loginDto,session);
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logOutPerson(HttpSession session){
        return  personService.logOut(session);
    }
}
