package com.SpringBootREST.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNotNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public RequiredObjectIsNotNullException(String msg) {
		super(msg);
	}
	
	public RequiredObjectIsNotNullException() {
		super("Its not Allowed to persist a null Object");
	}
	

	
}
