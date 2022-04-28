package com.blogapp.blogapp.controller;

import com.blogapp.blogapp.dto.CommentDto;
import com.blogapp.blogapp.dto.LoginDto;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.service.CommentService;
import com.blogapp.blogapp.service.LikeService;
import com.blogapp.blogapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto.getRole().equals("ADMIN")){
            return personService.getAllPerson();
        }
        return null;
    }
    @GetMapping("/get-one-person/{personId}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable(value = "personId") Long personId, HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto!=null){
            return  new ResponseEntity<>(personService.getPersonById(personId),HttpStatus.OK);
        }
        return null;
    }
    @PostMapping("/login")
   public ResponseEntity<String> loginAUser(@RequestBody LoginDto loginDto,HttpSession session){

        PersonDto personDto = personService.loginPerson(loginDto);
        if(personDto!=null){
            session.setAttribute("person",personDto);
            ResponseEntity<String> responseEntity = new ResponseEntity<>("Your Login was successful",HttpStatus.OK);
            return responseEntity;
        }

        ResponseEntity<String> responseEntity = new ResponseEntity<>("Wrong email or password please try again",HttpStatus.OK);
        return responseEntity;
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logOutPerson(HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        session.invalidate();
        return new ResponseEntity<>(personDto.getName()+" has successfully logged out",HttpStatus.OK);
    }
}
