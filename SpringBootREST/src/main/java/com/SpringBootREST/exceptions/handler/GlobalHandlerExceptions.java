package com.SpringBootREST.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.SpringBootREST.exceptions.IdNotNullException;
import com.SpringBootREST.exceptions.InvalidJWTAuthenticationException;
import com.SpringBootREST.exceptions.NullEntityException;
import com.SpringBootREST.exceptions.ResourceNotFoundException;
import com.SpringBootREST.exceptions.StandardError;

@ControllerAdvice
public class GlobalHandlerExceptions {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> notFoundException(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<>(error,status);
	}
	
	@ExceptionHandler(IdNotNullException.class)
	public ResponseEntity<StandardError> IdNullException(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<>(error,status);
	}
	
	@ExceptionHandler(NullEntityException.class)
	public ResponseEntity<StandardError> nullEntityException(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<>(error,status);
	}
	
	@ExceptionHandler(InvalidJWTAuthenticationException.class)
	public ResponseEntity<StandardError> handleInvalidJWTAuthentication(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError error = new StandardError(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<>(error,status);
	}
	

}
