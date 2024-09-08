package com.SpringBootREST.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.SpringBootREST.data.vo.v2.PersonVOV2;
import com.SpringBootREST.model.Person;

@Service
public class PersonMapper {

	public PersonVOV2 convertEntityToVO(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setAdress(person.getAdress());
		vo.setGender(person.getGender());
		vo.setBirthDate(new Date());
		return vo;
	}
	
	public Person convertVOToEntity(PersonVOV2 personVOV2) {
		Person entity = new Person();
		entity.setId(personVOV2.getId());
		entity.setFirstName(personVOV2.getFirstName());
		entity.setLastName(personVOV2.getLastName());
		entity.setAdress(personVOV2.getAdress());
		entity.setGender(personVOV2.getGender());
		//entity.personVOV2(new Date());
		return entity;
	}
}
