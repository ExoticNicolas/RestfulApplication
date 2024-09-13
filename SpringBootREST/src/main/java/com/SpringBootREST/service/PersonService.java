package com.SpringBootREST.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SpringBootREST.controllers.PersonController;
import com.SpringBootREST.data.vo.PersonVO;
import com.SpringBootREST.exceptions.NullEntityException;
import com.SpringBootREST.exceptions.ResourceNotFoundException;
import com.SpringBootREST.mapper.DozerMapper;
import com.SpringBootREST.model.Person;
import com.SpringBootREST.repositories.PersonRepository;

import jakarta.transaction.Transactional;


@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public PersonVO findById(Long id) {
		Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo((methodOn(PersonController.class)).findById(id)).withSelfRel());
		return vo;
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		personRepository.disablePerson(id);
		Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo((methodOn(PersonController.class)).findById(id)).withSelfRel());
		return vo;
	}

	public Page<PersonVO> findAll(Pageable pageable){
		
			var personPage = personRepository.findAll(pageable);
			var personsvoPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
			personsvoPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
			return personsvoPage;
	}

	public PersonVO create(PersonVO personVO) {
		Person entity = DozerMapper.parseObject(personVO, Person.class);
		PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		personRepository.delete(entity);
	}

	public PersonVO update(PersonVO personVO) {

		if(personVO == null) {
			throw new NullEntityException();
		}

		Person entity = personRepository.findById(personVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

}
