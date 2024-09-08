package com.SpringBootREST.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.SpringBootREST.data.vo.PersonVO;
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
	}
	
	@Test
	public void convertVOToEntity() {
		Person person = DozerMapper.parseObject(inputObject.mockVO(), Person.class);
		
		assertEquals(Long.valueOf(0),person.getId());
		assertEquals("Test First Name0", person.getFirstName());
		assertEquals("Test Last Name0", person.getLastName());
		assertEquals("Test Address0", person.getAddress());
		assertEquals("Male", person.getGender());
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
		
		PersonVO vo2 = listVO.get(2);
		
		assertEquals(Long.valueOf(2), vo2.getKey());
		assertEquals("Test First Name2", vo2.getFirstName());
		assertEquals("Test Last Name2", vo2.getLastName());
		assertEquals("Test Address2", vo2.getAddress());
		assertEquals("Male", vo2.getGender());
		
		PersonVO vo3 = listVO.get(3);
		
		assertEquals(Long.valueOf(3), vo3.getKey());
		assertEquals("Test First Name3", vo3.getFirstName());
		assertEquals("Test Last Name3", vo3.getLastName());
		assertEquals("Test Address3", vo3.getAddress());
		assertEquals("Female", vo3.getGender());
	}
	
	@Test
	public void convertVOListToEntityList() {
		List<Person> listVO = DozerMapper.parseListObject(inputObject.mockVOList(), Person.class);
		
		Person vo0 = listVO.get(0);
		
		assertEquals(Long.valueOf(0), vo0.getId());
		assertEquals("Test First Name0", vo0.getFirstName());
		assertEquals("Test Last Name0", vo0.getLastName());
		assertEquals("Test Address0", vo0.getAddress());
		assertEquals("Male", vo0.getGender());
		
		Person vo2 = listVO.get(2);
		
		assertEquals(Long.valueOf(2), vo2.getId());
		assertEquals("Test First Name2", vo2.getFirstName());
		assertEquals("Test Last Name2", vo2.getLastName());
		assertEquals("Test Address2", vo2.getAddress());
		assertEquals("Male", vo2.getGender());
		
		Person vo3 = listVO.get(3);
		
		assertEquals(Long.valueOf(3), vo3.getId());
		assertEquals("Test First Name3", vo3.getFirstName());
		assertEquals("Test Last Name3", vo3.getLastName());
		assertEquals("Test Address3", vo3.getAddress());
		assertEquals("Female", vo3.getGender());
	}
	
	

}
