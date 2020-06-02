package com.projectboard.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice	//mechanism helps to match exceptions declared
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectIdException(ProjectIdException exception, WebRequest request){
		ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(exception.getMessage());
		
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException exception, WebRequest request){
		ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(exception.getMessage());
		
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler
	public final ResponseEntity<Object> usernameAlreadyExistsException(UsernameExistsException exception, WebRequest request){
		UniqueUserResponse exceptionResponse = new UniqueUserResponse(exception.getMessage());
		
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	
}
