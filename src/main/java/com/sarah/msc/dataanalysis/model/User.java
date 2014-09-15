package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "user", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class User implements java.io.Serializable{

	private Integer id;
	private String login;
	private Integer followers;
	private Integer following;
	private Integer publicRepos;
	private Integer publicGists;
	private String createdAt;
	private Integer contributions;
	private Integer owner; // not a DB column
		
	public User() {
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

	@Column(name = "login", unique = false, nullable = true, length = 500)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "followers", unique = false, nullable = true)
	public Integer getFollowers() {
		return followers;
	}

	public void setFollowers(Integer followers) {
		this.followers = followers;
	}

	@Column(name = "following", unique = false, nullable = true)
	public Integer getFollowing() {
		return following;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

	@Column(name = "publicRepos", unique = false, nullable = true)
	public Integer getPublicRepos() {
		return publicRepos;
	}

	public void setPublicRepos(Integer publicRepos) {
		this.publicRepos = publicRepos;
	}

	@Column(name = "publicGists", unique = false, nullable = true)
	public Integer getPublicGists() {
		return publicGists;
	}

	public void setPublicGists(Integer publicGists) {
		this.publicGists = publicGists;
	}

	@Column(name = "createdAt", unique = false, nullable = true, length = 500)
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}	

	@Column(name = "contributions", unique = true, nullable = true)
	public Integer getContributions() {
		return contributions;
	}

	public void setContributions(Integer contributions) {
		this.contributions = contributions;
	}

	@Column(name = "owner", unique = true, nullable = true)
	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", followers="
				+ followers + ", following=" + following + ", publicRepos="
				+ publicRepos + ", publicGists=" + publicGists + ", createdAt="
				+ createdAt + ", contributions=" + contributions + ", owner="
				+ owner + "]";
	}
		
}
