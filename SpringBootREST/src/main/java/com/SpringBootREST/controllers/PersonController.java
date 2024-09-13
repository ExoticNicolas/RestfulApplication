package com.SpringBootREST.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootREST.data.vo.PersonVO;
import com.SpringBootREST.service.PersonService;
import com.SpringBootREST.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin
@RestController
@RequestMapping(value = "/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

	@Autowired
	private PersonService personService;

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds a Person", description = "Finds a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<PersonVO> findById(@PathVariable Long id)	{
		PersonVO vo = personService.findById(id);
		return ResponseEntity.ok().body(vo);
	}
	
	@PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Disabling a a Person", description = "Disable a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<PersonVO> disablePerson(@PathVariable Long id)	{
		PersonVO vo = personService.disablePerson(id);
		return ResponseEntity.ok().body(vo);
	}

	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds All People", description = "Finds All People",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<Page<PersonVO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction){
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		return ResponseEntity.ok(personService.findAll(pageable));
	}

	@CrossOrigin(origins = {"http://localhost:8080","https://erudio.com.br"})
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			     produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Creating a Person", description = "Creating a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO personVO){
		PersonVO vo = personService.create(personVO);
		return ResponseEntity.ok().body(vo);
	}

	@PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
			    produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Updating a Person", description = "Updating a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO personVO){
		PersonVO vo = personService.update(personVO);
		return ResponseEntity.ok().body(vo);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deleting a Person", description = "Deleting a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable Long id){
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
