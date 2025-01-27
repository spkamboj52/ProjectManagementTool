package com.projectboard.ppmtool.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectboard.ppmtool.domain.Project;
import com.projectboard.ppmtool.services.ProjectService;
import com.projectboard.ppmtool.services.ValidationErrorService;


@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ValidationErrorService errorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult,Principal principal){

		ResponseEntity<?> errorMap = errorService.errorService(bindingResult);
		
		if(errorMap!=null) 
			return errorMap;
		
		projectService.saveOrUpdateProject(project,principal.getName());
		return new ResponseEntity<Project>(project,HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId,Principal principal){
		Project project = projectService.findProjectByIdentifier(projectId,principal.getName());
		return new ResponseEntity<Project>(project,HttpStatus.OK); 
	}
	
	@GetMapping("/all")
	public Iterable<Project> findAllProjects(Principal principal){
		return projectService.findAllProjects(principal.getName());
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteByProjectIdentifier(@PathVariable String projectId,Principal principal){
		projectService.deleteProjectByIdentifier(projectId,principal.getName());
		return new ResponseEntity<String>("Project with Id '"+projectId+"' was deleted successfully",HttpStatus.OK);
		
	}
}
