package com.SpringBootREST.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBootREST.data.vo.PersonVO;
import com.SpringBootREST.mapper.MockPerson;
import com.SpringBootREST.model.Person;
import com.SpringBootREST.repositories.PersonRepository;
import com.SpringBootREST.service.PersonService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {
	
	MockPerson input;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PersonRepository repository;
	
	@BeforeEach
	void setUpmocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
			
		}
	
	@Test
	void testFindById() throws ParseException {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Test First Name1",result.getFirstName());
		assertEquals("Test Last Name1",result.getLastName());
		assertEquals("Test Address1",result.getAddress());
		assertEquals("Female",result.getGender());
	}
	
	@Test
	void testFindAll() throws ParseException {
		
		List<Person> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var persons = service.findAll();
		assertNotNull(persons);
		assertEquals(5,persons.size());
		
		var person1 = persons.get(1);
		assertNotNull(person1);
		assertNotNull(person1.getKey());
		assertNotNull(person1.getLinks());
		assertTrue(person1.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Test First Name1",person1.getFirstName());
		assertEquals("Test Last Name1",person1.getLastName());
		assertEquals("Test Address1",person1.getAddress());
		assertEquals("Female",person1.getGender());
		
		var person2 = persons.get(2);
		assertNotNull(person2);
		assertNotNull(person2.getKey());
		assertNotNull(person2.getLinks());
		assertTrue(person2.toString().contains("links: [</api/person/v1/2>;rel=\"self\"]"));
		assertEquals("Test First Name2",person2.getFirstName());
		assertEquals("Test Last Name2",person2.getLastName());
		assertEquals("Test Address2",person2.getAddress());
		assertEquals("Male",person2.getGender());
		
		var person3 = persons.get(3);
		assertNotNull(person3);
		assertNotNull(person3.getKey());
		assertNotNull(person3.getLinks());
		assertTrue(person3.toString().contains("links: [</api/person/v1/3>;rel=\"self\"]"));
		assertEquals("Test First Name3",person3.getFirstName());
		assertEquals("Test Last Name3",person3.getLastName());
		assertEquals("Test Address3",person3.getAddress());
		assertEquals("Female",person3.getGender());
	}
	
	@Test
	void testCreate() throws ParseException {
		
		Person entity = input.mockEntity(1);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Test First Name1",result.getFirstName());
		assertEquals("Test Last Name1",result.getLastName());
		assertEquals("Test Address1",result.getAddress());
		assertEquals("Female",result.getGender());
		}
	
	@Test
	void testDelete() throws ParseException {
		
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}
	
	@Test
	void testUpdate() throws ParseException {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Test First Name1",result.getFirstName());
		assertEquals("Test Last Name1",result.getLastName());
		assertEquals("Test Address1",result.getAddress());
		assertEquals("Female",result.getGender());
	}

}
