package com.projectboard.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectboard.ppmtool.domain.Project;
import com.projectboard.ppmtool.exceptions.ProjectIdException;
import com.projectboard.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
			
		} catch (Exception e) {
			throw new ProjectIdException("Project Id "+project.getProjectIdentifier().toUpperCase()+" already exists");
			// TODO: handle exception
		}
		
	}
}
