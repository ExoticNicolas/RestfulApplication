package com.SpringBootREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBootREST.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
