package com.blogapp.blogapp.repository;

import com.blogapp.blogapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    Optional<Person> findByEmailAndPassword(String email, String password);
}
