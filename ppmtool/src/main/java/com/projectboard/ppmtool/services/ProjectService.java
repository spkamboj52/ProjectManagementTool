package com.projectboard.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectboard.ppmtool.domain.Backlog;
import com.projectboard.ppmtool.domain.Project;
import com.projectboard.ppmtool.domain.User;
import com.projectboard.ppmtool.exceptions.ProjectIdException;
import com.projectboard.ppmtool.exceptions.ProjectNotFoundException;
import com.projectboard.ppmtool.repository.BacklogRepository;
import com.projectboard.ppmtool.repository.ProjectRepository;
import com.projectboard.ppmtool.repository.UserRespository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private UserRespository userRespository;
	public Project saveOrUpdateProject(Project project,String username) {
		//project.getid!=null
		
		if(project.getId()!=null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject!=null && (!project.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			}
			else if(existingProject==null) {
				throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be "
						+ "updated because it doesnot exists");
			}
		}
		
		
		try {
			
			User user = userRespository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			
			
			String pIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(pIdentifier);
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(pIdentifier);
			}
			if(project.getId()!=null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(pIdentifier));
			}
			return projectRepository.save(project);
			
		} catch (Exception e) {
			throw new ProjectIdException("Project Id "+project.getProjectIdentifier().toUpperCase()+" already exists");
			// TODO: handle exception
		}
		
	}
	
	public Project findProjectByIdentifier(String projectId,String username) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project==null) {
			throw new ProjectIdException("Project Id '"+projectId+"' does not exists");
		}
		
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project Not Found in your Account");
		}
		
		return project;
	}
	
	public Iterable<Project> findAllProjects(String userName){
		return projectRepository.findAllByProjectLeader(userName);
		
	}
	
	public void deleteProjectByIdentifier(String projectId,String username) {
		
			projectRepository.delete(findProjectByIdentifier(projectId, username));
		
	}
	
}
