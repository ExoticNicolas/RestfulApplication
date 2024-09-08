package com.SpringBootREST.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import com.SpringBootREST.data.vo.v1.PersonVO;
import com.SpringBootREST.exceptions.RequiredObjectIsNotNullException;
import com.SpringBootREST.model.Person;
import com.SpringBootREST.repositories.PersonRepository;
import com.SpringBootREST.services.PersonService;
import com.SpringBootREST.unittests.mapper.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	MockPerson input;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	private PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Adress Test1",result.getAdress());
		assertEquals("First Name Test1",result.getFirstName());
		assertEquals("Last Name Test1",result.getLastName());
		assertEquals("Female",result.getGender());
	}

	@Test
	void testFindAll() {
		
		List<Person> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var people = service.findAll();
		assertNotNull(people);
		assertEquals(14,people.size());
		
		var person1 = people.get(1);
		assertNotNull(person1);
		assertNotNull(person1.getKey());
		assertNotNull(person1.getLinks());
		assertTrue(person1.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Adress Test1",person1.getAdress());
		assertEquals("First Name Test1",person1.getFirstName());
		assertEquals("Last Name Test1",person1.getLastName());
		assertEquals("Female",person1.getGender());
		
		var person4 = people.get(4);
		assertNotNull(person4);
		assertNotNull(person4.getKey());
		assertNotNull(person4.getLinks());
		assertTrue(person4.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
		assertEquals("Adress Test4",person4.getAdress());
		assertEquals("First Name Test4",person4.getFirstName());
		assertEquals("Last Name Test4",person4.getLastName());
		assertEquals("Male",person4.getGender());
		
		var person7 = people.get(7);
		assertNotNull(person7);
		assertNotNull(person7.getKey());
		assertNotNull(person7.getLinks());
		assertTrue(person7.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
		assertEquals("Adress Test7",person7.getAdress());
		assertEquals("First Name Test7",person7.getFirstName());
		assertEquals("Last Name Test7",person7.getLastName());
		assertEquals("Female",person7.getGender());
	

	}

	@Test
	void testCreate() {
		
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
		assertEquals("Adress Test1",result.getAdress());
		assertEquals("First Name Test1",result.getFirstName());
		assertEquals("Last Name Test1",result.getLastName());
		assertEquals("Female",result.getGender());
		
		}

	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNotNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "Its not Allowed to persist a null Object";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
		}
	
	
	@Test
	void testDelete() {
		
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
		
	}

	@Test
	void testUpdate() {
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
		assertEquals("Adress Test1",result.getAdress());
		assertEquals("First Name Test1",result.getFirstName());
		assertEquals("Last Name Test1",result.getLastName());
		assertEquals("Female",result.getGender());
		
	}
	
	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNotNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "Its not Allowed to persist a null Object";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
		}

}
