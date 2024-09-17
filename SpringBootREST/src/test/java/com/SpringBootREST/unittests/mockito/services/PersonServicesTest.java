package com.SpringBootREST.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
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
		assertEquals(true ,result.getEnabled());
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
		assertEquals(true ,result.getEnabled());
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
		assertEquals(true,result.getEnabled());
	}

}
