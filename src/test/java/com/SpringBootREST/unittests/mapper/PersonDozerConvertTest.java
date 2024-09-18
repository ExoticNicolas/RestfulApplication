package com.SpringBootREST.unittests.mapper;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.SpringBootREST.data.vo.PersonVO;
import com.SpringBootREST.mapper.DozerMapper;
import com.SpringBootREST.model.Person;

public class PersonDozerConvertTest {

	private MockPerson inputObject;

	@BeforeEach
	public void setUp() {
		inputObject = new MockPerson();
	}

	@Test
	public void convertEntityToVO(){
		PersonVO vo = DozerMapper.parseObject(inputObject.mockEntity(), PersonVO.class);

		assertEquals(Long.valueOf(0), vo.getKey());
		assertEquals("Test First Name0", vo.getFirstName());
		assertEquals("Test Last Name0", vo.getLastName());
		assertEquals("Test Address0", vo.getAddress());
		assertEquals("Male", vo.getGender());
		assertTrue(vo.getEnabled());
	}

	@Test
	public void convertVOToEntity() {
		Person person = DozerMapper.parseObject(inputObject.mockVO(), Person.class);

		assertEquals(Long.valueOf(0),person.getId());
		assertEquals("Test First Name0", person.getFirstName());
		assertEquals("Test Last Name0", person.getLastName());
		assertEquals("Test Address0", person.getAddress());
		assertEquals("Male", person.getGender());
		assertTrue(person.getEnabled());
	}

	@Test
	public void convertEntityListToVOList() {
		List<PersonVO> listVO = DozerMapper.parseListObject(inputObject.mockEntityList(), PersonVO.class);

		PersonVO vo0 = listVO.get(0);

		assertEquals(Long.valueOf(0), vo0.getKey());
		assertEquals("Test First Name0", vo0.getFirstName());
		assertEquals("Test Last Name0", vo0.getLastName());
		assertEquals("Test Address0", vo0.getAddress());
		assertEquals("Male", vo0.getGender());
		assertTrue(vo0.getEnabled());

		PersonVO vo2 = listVO.get(2);

		assertEquals(Long.valueOf(2), vo2.getKey());
		assertEquals("Test First Name2", vo2.getFirstName());
		assertEquals("Test Last Name2", vo2.getLastName());
		assertEquals("Test Address2", vo2.getAddress());
		assertEquals("Male", vo2.getGender());
		assertTrue(vo2.getEnabled());

		PersonVO vo3 = listVO.get(3);

		assertEquals(Long.valueOf(3), vo3.getKey());
		assertEquals("Test First Name3", vo3.getFirstName());
		assertEquals("Test Last Name3", vo3.getLastName());
		assertEquals("Test Address3", vo3.getAddress());
		assertEquals("Female", vo3.getGender());
		assertTrue(vo3.getEnabled());
	}

	@Test
	public void convertVOListToEntityList() {
		List<Person> persons = DozerMapper.parseListObject(inputObject.mockVOList(), Person.class);

		Person person1 = persons.get(0);

		assertEquals(Long.valueOf(0), person1.getId());
		assertEquals("Test First Name0", person1.getFirstName());
		assertEquals("Test Last Name0", person1.getLastName());
		assertEquals("Test Address0", person1.getAddress());
		assertEquals("Male", person1.getGender());
		assertTrue(person1.getEnabled());

		Person person2 = persons.get(2);

		assertEquals(Long.valueOf(2), person2.getId());
		assertEquals("Test First Name2", person2.getFirstName());
		assertEquals("Test Last Name2", person2.getLastName());
		assertEquals("Test Address2", person2.getAddress());
		assertEquals("Male", person2.getGender());
		assertTrue(person2.getEnabled());

		Person person3 = persons.get(3);

		assertEquals(Long.valueOf(3), person3.getId());
		assertEquals("Test First Name3", person3.getFirstName());
		assertEquals("Test Last Name3", person3.getLastName());
		assertEquals("Test Address3", person3.getAddress());
		assertEquals("Female", person3.getGender());
		assertTrue(person3.getEnabled());
	}



}
