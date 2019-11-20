package com.projectboard.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectboard.ppmtool.domain.Project;
import com.projectboard.ppmtool.services.ProjectService;
import com.projectboard.ppmtool.services.ValidationErrorService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ValidationErrorService errorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult){

		ResponseEntity<?> errorMap = errorService.errorService(bindingResult);
		
		if(errorMap!=null) 
			return errorMap;
		
		projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project,HttpStatus.CREATED);
	}
}
