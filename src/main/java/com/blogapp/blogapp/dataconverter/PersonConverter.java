package com.blogapp.blogapp.dataconverter;

import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {
    public Person convertDtoToEntity(PersonDto personDto){

        Person person = new Person();
        person.setId(personDto.getId());
        person.setName(personDto.getName());
        person.setEmail(personDto.getEmail());
        person.setRole(personDto.getRole());
        person.setPassword(personDto.getPassword());
        return person;
    }

    public PersonDto convertEntityToDto(Person person){
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        personDto.setEmail(person.getEmail());
        personDto.setRole(person.getRole());
        personDto.setPassword(person.getPassword());

        return personDto;
    }
}
