package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "weekly_projects_todo", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class CrowstonViewWeeklyTodo {
	
	private Integer id;
	private String fullName;
	private String createdAt;
	

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "fullName", unique = true, nullable = false, length = 500)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	

	@Column(name = "createdAt", unique = true, nullable = true, length = 500)
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
