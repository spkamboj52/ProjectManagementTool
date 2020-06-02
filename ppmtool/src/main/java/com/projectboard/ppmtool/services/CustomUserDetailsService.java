package com.projectboard.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectboard.ppmtool.domain.User;
import com.projectboard.ppmtool.repository.UserRespository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRespository userrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userrepository.findByUsername(username);
		if(user == null) new UsernameNotFoundException("User Not Found");
		
		return user;
	}

	@Transactional
	public User loadUserById(Long id) {
		
		User user  = userrepository.getById(id);
		
		if(user == null) new UsernameNotFoundException("User Not Found");
		
		return user;	
		}
	
	
}
