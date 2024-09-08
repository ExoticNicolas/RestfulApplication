package com.SpringBootREST.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootREST.data.vo.v1.PersonVO;
import com.SpringBootREST.data.vo.v2.PersonVOV2;
import com.SpringBootREST.services.PersonService;
import com.SpringBootREST.util.MediaType;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public PersonVO findById(@PathVariable Long id) {
		return personService.findById(id); 
		
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML , MediaType.APPLICATION_YML})
	public List<PersonVO> findAll() {
		return personService.findAll();
		
	}
	
	@PostMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML , MediaType.APPLICATION_YML}, 
			     consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML , MediaType.APPLICATION_YML} )
	public PersonVO create(@RequestBody PersonVO personVO) {
		return personService.create(personVO);
		
	}
	
	@PostMapping(value = "/v2",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML , MediaType.APPLICATION_YML}, 
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML , MediaType.APPLICATION_YML} )
	public PersonVOV2 createV2(@RequestBody PersonVOV2 personVOV2) {
		return personService.createV2(personVOV2);
		
	}
	
	@PutMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML , MediaType.APPLICATION_YML}, 
				consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML , MediaType.APPLICATION_YML} )
	public PersonVO update(@RequestBody PersonVO personVO) {
		return personService.update(personVO);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 personService.delete(id);
		 return ResponseEntity.noContent().build();
		
	}
	

}
