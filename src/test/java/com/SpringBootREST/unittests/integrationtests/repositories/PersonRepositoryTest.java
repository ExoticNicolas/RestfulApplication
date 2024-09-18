package com.SpringBootREST.unittests.integrationtests.repositories;

import static org.junit.Assert.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.SpringBootREST.model.Person;
import com.SpringBootREST.repositories.PersonRepository;
import com.SpringBootREST.unittests.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest{

	@Autowired
	public PersonRepository repository;
	
	private static Person person;
	
	@BeforeAll
	public static void setUp()	{
		person = new Person();
	}
	
	@Test
	@Order(0)
	public void testFindPersonByName() throws JsonMappingException, JsonProcessingException {
			
		Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
		person = repository.findPersonsByName("ayr", pageable).getContent().get(0);
		
		assertNotNull(person);
		
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());
		assertEquals(true,person.getEnabled());

		assertEquals(1, person.getId());
		
		assertEquals("Ayrton", person.getFirstName());
		assertEquals("Senna", person.getLastName());
		assertEquals("São Paulo", person.getAddress());
		assertEquals("Male", person.getGender());
		assertEquals(true, person.getEnabled());
	}
	
	@Test
	@Order(1)
	public void testDisablePerson() throws JsonMappingException, JsonProcessingException {
		
			
		repository.disablePerson(person.getId());
		Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
		person = repository.findPersonsByName("ayr", pageable).getContent().get(0);
		
		assertNotNull(person);
		
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());
		
		assertFalse(person.getEnabled());

		assertEquals(1, person.getId());
		
		assertEquals("Ayrton", person.getFirstName());
		assertEquals("Senna", person.getLastName());
		assertEquals("São Paulo", person.getAddress());
		assertEquals("Male", person.getGender());
	}
	
}
