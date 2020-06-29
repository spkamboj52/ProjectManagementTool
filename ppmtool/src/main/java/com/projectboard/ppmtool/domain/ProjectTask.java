package com.projectboard.ppmtool.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(updatable = false,unique = true)
	private String projectSequence;			//search individual tasks
	@NotBlank(message = "Please include a project summary")
	private String summary;
	private String status;
	private String acceptanceCriteria;
	private Integer priority;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dueDate;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date createdAt;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date updatedAt;
	
	@Column(updatable = false)
	private String projectIdentifier;

	//Many to one with backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlogId",updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;
	
	
	public Backlog getBacklog() {
		return backlog;
	}


	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}


	public ProjectTask() {
		// TODO Auto-generated constructor stub
	}
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getProjectSequence() {
		return projectSequence;
	}


	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}


	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}


	public Integer getPriority() {
		return priority;
	}


	public void setPriority(Integer priority) {
		this.priority = priority;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public String getProjectIdentifier() {
		return projectIdentifier;
	}


	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}


	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
