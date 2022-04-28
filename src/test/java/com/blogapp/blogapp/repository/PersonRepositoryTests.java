package com.blogapp.blogapp.repository;

import com.blogapp.blogapp.entity.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    //JUnit test for save person

    @DisplayName("JUnit Test for Save Person Operation")
    @Test
    public void givenPersonObject_whenSave_thenSaveEmployee(){

        Person person = new Person();
        person.setName("Sinebi Innazo");
        person.setEmail("sinebi.innazo@yahoo.com");
        person.setRole("CUSTOMER");
        person.setPassword("1234567");

        Person savedPerson = personRepository.save(person);
        Assertions.assertThat(savedPerson).isNotNull();
        Assertions.assertThat(savedPerson.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit Test to get all users")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){

        Person person = new Person();
        person.setName("Sinebi Innazo");
        person.setEmail("sinebi.innazo@yahoo.com");
        person.setRole("CUSTOMER");
        person.setPassword("1234567");

        Person person1 = new Person();
        person1.setName("Tapiya Innazo");
        person1.setEmail("tapiya.innazo@yahoo.com");
        person1.setRole("CUSTOMER");
        person1.setPassword("1234567");
        personRepository.save(person);
        personRepository.save(person1);

        List<Person> personList = personRepository.findAll();
        Assertions.assertThat(personList).isNotNull();
        Assertions.assertThat(personList.size()).isEqualTo(2);

    }

    @Test
    public void givenPersonObject_whenFindById_thenPerson(){

        Person person = new Person();
        person.setName("Sinebi Innazo");
        person.setEmail("sinebi.innazo@yahoo.com");
        person.setRole("CUSTOMER");
        person.setPassword("1234567");
        Person newPerson = personRepository.save(person);
        Assertions.assertThat(personRepository.findById(newPerson.getId()).get()).isNotNull();
    }

    @Test
    public void shouldUpdateAPersonInformation(){

        Person person = new Person();
        person.setName("Sinebi Innazo");
        person.setEmail("sinebi.innazo@yahoo.com");
        person.setRole("CUSTOMER");
        person.setPassword("1234567");

        Person newPerson = personRepository.save(person);
        newPerson.setName("Pete Verde");
        newPerson.setEmail("abc@ymail.com");
        newPerson.setRole("ADMIN");
        newPerson.setPassword("45334");
        Person updatedPerson = personRepository.save(newPerson);

        Assertions.assertThat(updatedPerson.getName()).isEqualTo("Pete Verde");
        Assertions.assertThat(updatedPerson.getEmail()).isEqualTo("abc@ymail.com");
        Assertions.assertThat(updatedPerson.getRole()).isEqualTo("ADMIN");
        Assertions.assertThat(updatedPerson.getPassword()).isEqualTo("45334");
    }

    @Test
    public void givenPersonObject_whenFindByEmailAndPassword(){

        Person person = new Person();
        person.setName("Sinebi Innazo");
        person.setEmail("sinebi.innazo@yahoo.com");
        person.setRole("CUSTOMER");
        person.setPassword("1234567");
        Person newPerson = personRepository.save(person);
        Assertions.assertThat(personRepository.findByEmailAndPassword(newPerson.getEmail(), newPerson.getPassword())).get().isNotNull();

    }
}


