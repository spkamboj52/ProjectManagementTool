package com.projectboard.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {
	String ProjectNotFound;
	
	public ProjectNotFoundExceptionResponse(String ProjectNotFound) {
		// TODO Auto-generated constructor stub
		this.ProjectNotFound = ProjectNotFound;
	}

	public String getProjectNotFound() {
		return ProjectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		ProjectNotFound = projectNotFound;
	}

	
}
