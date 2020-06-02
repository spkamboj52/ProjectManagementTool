package com.projectboard.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projectboard.ppmtool.domain.User;
import com.projectboard.ppmtool.exceptions.UsernameExistsException;
import com.projectboard.ppmtool.repository.UserRespository;

@Service
public class UserService {

	@Autowired
	private UserRespository userRespository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			//username has to be unique custom exception
			newUser.setUsername(newUser.getUsername());
			//make sure password and confirm password mathces
			
			//we don't persist or show confirm password
			newUser.setConfirmPassword("");
			return userRespository.save(newUser);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new UsernameExistsException("Username: '"+newUser.getUsername()+"' already exists");
		}
		
		
	}
	
}
