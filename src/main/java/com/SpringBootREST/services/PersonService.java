package com.SpringBootREST.services;

import java.util.List;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.SpringBootREST.controllers.PersonController;
import com.SpringBootREST.data.vo.v1.PersonVO;
import com.SpringBootREST.data.vo.v2.PersonVOV2;
import com.SpringBootREST.exceptions.RequiredObjectIsNotNullException;
import com.SpringBootREST.exceptions.ResourceNotFoundException;
import com.SpringBootREST.mapper.DozerMapper;
import com.SpringBootREST.mapper.custom.PersonMapper;
import com.SpringBootREST.model.Person;
import com.SpringBootREST.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonMapper personMapper;

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	public PersonVO findById(Long id) {
		logger.info("Finding one Person");
		var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public List<PersonVO> findAll() {
		logger.info("Finding All People");
		var persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO create(PersonVO personVO) {
		if(personVO == null) {
			throw new RequiredObjectIsNotNullException();
		}
		logger.info("Creating one Person");
		var entity = DozerMapper.parseObject(personVO, Person.class);
		var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 personVOV2) {
		logger.info("Creating one Person V2");
		var person = personMapper.convertVOToEntity(personVOV2);
		var vo = personMapper.convertEntityToVO(personRepository.save(person));
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting One Person");
		Person deletePerson = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));
		personRepository.delete(deletePerson);

	}

	public PersonVO update(PersonVO personVO) {
		if(personVO == null) {
			throw new RequiredObjectIsNotNullException();
		}
		logger.info("Updating One Person");
		var entity = personRepository.findById(personVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAdress(personVO.getAdress());
		entity.setGender(personVO.getGender());
		var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
}
