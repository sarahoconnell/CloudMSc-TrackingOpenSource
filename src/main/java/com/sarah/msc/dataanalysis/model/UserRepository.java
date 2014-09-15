package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "userRepository", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class UserRepository implements java.io.Serializable{

	private Integer id;
	private Integer userId;
	private Integer projectId;
	private Integer owner;
		
	public UserRepository() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "userId", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "projectId", unique = true, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "owner", unique = true, nullable = true)
	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	
	
}
