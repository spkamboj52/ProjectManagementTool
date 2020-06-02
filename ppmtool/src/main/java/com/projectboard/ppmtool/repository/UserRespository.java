package com.projectboard.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectboard.ppmtool.domain.User;

@Repository
public interface UserRespository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	User getById(Long Id);

}
