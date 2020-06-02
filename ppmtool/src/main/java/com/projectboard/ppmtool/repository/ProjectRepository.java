package com.projectboard.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectboard.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{
	
	@Override
	Iterable<Project> findAll();
	
	Iterable<Project> findAllByProjectLeader(String userName);

	Project findByProjectIdentifier(String projectId);

}
