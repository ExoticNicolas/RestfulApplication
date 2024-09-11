package com.SpringBootREST.mapper;

import java.util.ArrayList;
import java.util.List;

import com.SpringBootREST.data.vo.PersonVO;
import com.SpringBootREST.model.Person;

public class MockPerson {

	public Person mockEntity() {
		return mockEntity(0);
	}

	public PersonVO mockVO() {
		return mockVO(0);
	}

	public Person mockEntity(Integer number) {
		Person person = new Person();

		person.setId(number.longValue());
		person.setFirstName("Test First Name" + number);
		person.setLastName("Test Last Name" + number);
		person.setAddress("Test Address" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		return person;
	}

	public PersonVO mockVO(Integer number) {
		PersonVO person = new PersonVO();

		person.setKey(number.longValue());
		person.setFirstName("Test First Name" + number);
		person.setLastName("Test Last Name" + number);
		person.setAddress("Test Address" + number);
		person.setGender(((number % 2) == 0) ? "Male" : "Female");
		return person;
	}

	public List<Person> mockEntityList(){
		List<Person> persons = new ArrayList<>();
		for(int i = 0; i<5; i++) {
			persons.add(mockEntity(i));
		}
		return persons;
	}

	public List<PersonVO> mockVOList(){
		List<PersonVO> persons = new ArrayList<>();
		for(int i = 0; i<5; i++) {
			persons.add(mockVO(i));
		}
		return persons;
	}



}
