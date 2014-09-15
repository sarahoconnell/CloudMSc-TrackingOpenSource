package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "commits", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class Commits implements java.io.Serializable{

	private Integer id;
	private Integer projectId;
	private String sha;
	private String date;
	private String userName;
	private String userLogin;
	private Integer userId;
	private String type;
	private String message;
	private String since;
	private String until;
	private Integer monthNumber;
	
	
	public Commits() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "projectId", unique = false, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "sha", unique = true, nullable = false, length = 500)
	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	@Column(name = "date", unique = false, nullable = true, length = 500)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "userName", unique = false, nullable = true, length = 500)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "userLogin", unique = false, nullable = true, length = 500)
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	@Column(name = "userId", unique = false, nullable = true)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "userType", unique = false, nullable = true, length = 500)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "message", unique = false, nullable = true, length = 5000)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "since", unique = false, nullable = true, length = 5000)
	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	@Column(name = "until", unique = false, nullable = true, length = 5000)
	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

	@Column(name = "monthNumber", unique = false, nullable = true)
	public Integer getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(Integer monthNumber) {
		this.monthNumber = monthNumber;
	}
	
}
