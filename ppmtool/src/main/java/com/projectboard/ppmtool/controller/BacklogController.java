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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectboard.ppmtool.domain.ProjectTask;
import com.projectboard.ppmtool.services.ProjectTaskService;
import com.projectboard.ppmtool.services.ValidationErrorService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private ValidationErrorService errorService;
	
	@PostMapping("/{backlogId}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
			BindingResult result,@PathVariable String backlogId,Principal principal){
		
		ResponseEntity<?> errorMap = errorService.errorService(result);
		if(errorMap!=null)
			return errorMap;
		
		ProjectTask projectTask2  = projectTaskService.addProjectTask(backlogId, projectTask,principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTask2,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{backlogId}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlogId){
		
		return projectTaskService.findBacklogById(backlogId);
	}
	
	@GetMapping("/{backlogId}/{ptId}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlogId,@PathVariable String ptId){
		
		ProjectTask projectTask = projectTaskService.findPtByProjectSequence(backlogId,ptId);
		
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	
	@PatchMapping("/{backlogId}/{ptId}")
	public ResponseEntity<?> updateProjecTask(@Valid @RequestBody ProjectTask projectTask,BindingResult result,
			@PathVariable String backlogId,@PathVariable String ptId){
	
		ResponseEntity<?> errorMap = errorService.errorService(result);
		if(errorMap!=null)
			return errorMap;
		
		ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlogId, ptId);
		
		return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{backlogId}/{ptId}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId,@PathVariable String ptId)
	{
		
		
		projectTaskService.deletePtByProjSequence(backlogId, ptId);
		return new ResponseEntity<String>("Project Task "+ptId+" was deleted successfully",HttpStatus.OK);
	}
	
}
