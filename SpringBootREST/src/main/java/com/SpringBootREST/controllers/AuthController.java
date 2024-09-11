package com.SpringBootREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootREST.data.vo.security.AccountCredentialsVO;
import com.SpringBootREST.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a User and Returns a Token")
	@PostMapping("/signin")
	public ResponseEntity signIn(@RequestBody AccountCredentialsVO data) {
		if(ckeckIsParamIsNotNull(data)) 
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");
		var token = authService.signIn(data);
		if(token == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");
		return token;
	}
	
	private boolean ckeckIsParamIsNotNull(AccountCredentialsVO data) {
		return data == null || 
				data.getUserName() == null || 
				data.getUserName().isBlank() || 
				data.getPassword().isBlank();
	}
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable String username,
			@RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		var token = authService.refreshToken(username, refreshToken);
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		return token;
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() ||
				username == null || username.isBlank();
	}

}
