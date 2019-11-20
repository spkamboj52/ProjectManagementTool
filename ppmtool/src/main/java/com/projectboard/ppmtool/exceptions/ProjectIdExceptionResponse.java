package com.projectboard.ppmtool.exceptions;

public class ProjectIdExceptionResponse {

	private String projectIdentifier;
	public ProjectIdExceptionResponse(String projectIdentifier) {
		// TODO Auto-generated constructor stub
		this.projectIdentifier = projectIdentifier;
	}
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
}
