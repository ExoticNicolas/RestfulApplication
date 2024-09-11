package com.SpringBootREST.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringBootREST.data.vo.security.AccountCredentialsVO;
import com.SpringBootREST.data.vo.security.TokenVO;
import com.SpringBootREST.repositories.UserRepository;
import com.SpringBootREST.security.JWT.JWTTokenProvider;

@Service
public class AuthService {

	@Autowired
	JWTTokenProvider tokenProvider;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signIn(AccountCredentialsVO data) {
		try {
			var username = data.getUserName();
			var password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			var user = userRepository.findByUsername(username);
			
			var tokenResponse = new TokenVO();
			if(user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username" + username + "Not Found");
			}
			return ResponseEntity.ok(tokenResponse);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid Username, Password supplied");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		var user = userRepository.findByUsername(username);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
}
