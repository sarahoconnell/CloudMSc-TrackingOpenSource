package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "commit_users_todo", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "userId") })
public class CommitUsersTodo implements java.io.Serializable{

	private Integer userId;
	private String userLogin;
		
	public CommitUsersTodo() {
		// TODO Auto-generated constructor stub
	}
	

	@Id
	@Column(name = "userId", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "userLogin", unique = false, nullable = true, length = 500)
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	
}

