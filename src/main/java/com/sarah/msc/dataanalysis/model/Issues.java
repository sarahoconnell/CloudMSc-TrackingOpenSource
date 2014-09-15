package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "issues", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class Issues implements java.io.Serializable{

	private Integer id;
	private Integer projectId;
	private Integer number;
	private String title;
	private String userLogin;
	private Integer userId;
	private String state;
	private String createdAt;
	private String closedAt;
	private Integer openedMonthNumber;
	private Integer closedMonthNumber;
	private Integer lifespan;
	private Integer pullRequest;
		
	public Issues() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
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

	@Column(name = "number", unique = false, nullable = true)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "title", unique = false, nullable = true, length = 500)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "state", unique = false, nullable = true, length = 45)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "createdAt", unique = false, nullable = true, length = 500)
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
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

	@Column(name = "closedAt", unique = false, nullable = true, length = 500)
	public String getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(String closedAt) {
		this.closedAt = closedAt;
	}

	@Column(name = "openedMonthNumber", unique = false, nullable = true)
	public Integer getOpenedMonthNumber() {
		return openedMonthNumber;
	}

	public void setOpenedMonthNumber(Integer openedMonthNumber) {
		this.openedMonthNumber = openedMonthNumber;
	}

	@Column(name = "closedMonthNumber", unique = false, nullable = true)
	public Integer getClosedMonthNumber() {
		return closedMonthNumber;
	}

	public void setClosedMonthNumber(Integer closedMonthNumber) {
		this.closedMonthNumber = closedMonthNumber;
	}

	@Column(name = "lifespan", unique = false, nullable = true)
	public Integer getLifespan() {
		return lifespan;
	}

	public void setLifespan(Integer lifespan) {
		this.lifespan = lifespan;
	}

	@Column(name = "pullRequest", unique = false, nullable = true)
	public Integer getPullRequest() {
		return pullRequest;
	}

	public void setPullRequest(Integer pullRequest) {
		this.pullRequest = pullRequest;
	}
	
}
