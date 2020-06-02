package com.projectboard.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectboard.ppmtool.domain.Backlog;
import com.projectboard.ppmtool.domain.Project;
import com.projectboard.ppmtool.domain.ProjectTask;
import com.projectboard.ppmtool.exceptions.ProjectNotFoundException;
import com.projectboard.ppmtool.repository.BacklogRepository;
import com.projectboard.ppmtool.repository.ProjectRepository;
import com.projectboard.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectService projectService;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask,String username) {
		
		Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);
		projectTask.setBacklog(backlog);
		
		Integer backLogSequence = backlog.getPTSequence();
		backLogSequence++;
		
		backlog.setPTSequence(backLogSequence);
		
		projectTask.setProjectSequence(projectIdentifier+"-"+backLogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		if(projectTask.getPriority()==null || projectTask.getPriority()==0) {
			projectTask.setPriority(3);						// 3 is low and 1 is high
		}
		
		if(projectTask.getStatus()==null || projectTask.getStatus()=="") {
			projectTask.setStatus("TO_DO");								//change to enums
		}
		
		return projectTaskRepository.save(projectTask);
		
		
	}

	public Iterable<ProjectTask> findBacklogById(String backlogId) {
		
		Project project = projectRepository.findByProjectIdentifier(backlogId);
		if(project==null) {
			throw new ProjectNotFoundException("Project Id "+backlogId.toUpperCase()+" does not exists");
		}
		// TODO Auto-generated method stub
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
	}

	public ProjectTask findPtByProjectSequence(String backlogId,String ptId) {
		
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlogId);
		if(backlog==null) {
			throw new ProjectNotFoundException("Project Id '"+backlogId.toUpperCase()+"' does not exists");
		}
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(ptId);
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project Task '"+ptId+"' does not exists");
		}
		if(!projectTask.getProjectIdentifier().equals(backlogId)) {
			throw new ProjectNotFoundException("Project Task '"+ptId+"'"
					+ "' does not exists in project "+backlogId.toUpperCase());
		}
		
		return projectTask;
	}
	
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask,String backlogId,String ptId) {
		
		
		ProjectTask projectTask = findPtByProjectSequence(backlogId, ptId);
		projectTask = updatedTask;
		
		
		return projectTaskRepository.save(projectTask);
		
	}
	
	public void deletePtByProjSequence(String backlogId,String ptId) {
		ProjectTask projectTask = findPtByProjectSequence(backlogId, ptId);
		projectTaskRepository.delete(projectTask);
	}
	
}
