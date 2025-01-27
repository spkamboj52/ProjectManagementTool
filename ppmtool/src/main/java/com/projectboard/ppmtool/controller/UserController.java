package com.projectboard.ppmtool.controller;

import static com.projectboard.ppmtool.Security.SecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectboard.ppmtool.Security.JWTTokenProvider;
import com.projectboard.ppmtool.domain.User;
import com.projectboard.ppmtool.payload.JWTLoginSuccessResponse;
import com.projectboard.ppmtool.payload.LoginRequest;
import com.projectboard.ppmtool.services.UserService;
import com.projectboard.ppmtool.services.ValidationErrorService;
import com.projectboard.ppmtool.validator.UserValidator;
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ValidationErrorService errorService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private JWTTokenProvider tokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	   @PostMapping("/login")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
	        ResponseEntity<?> errorMap = errorService.errorService(result);
	        if(errorMap != null) return errorMap;

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getUsername(),
	                        loginRequest.getPassword()
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

	        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	    }
	
 
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user,BindingResult result){
		//validate passwords match
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap = errorService.errorService(result);
		if(errorMap!=null) return errorMap;
		
		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser,HttpStatus.CREATED);
	}
}
