package com.projectboard.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameExistsException extends RuntimeException{

	public UsernameExistsException(String message) {
		// TODO Auto-generated constructor stub
	
		super(message);
	}
	
	
}
