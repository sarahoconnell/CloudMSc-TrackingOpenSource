package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "repository", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class Repository implements java.io.Serializable{

	private Integer id;
	private String name;
	private String fullName;
	private String url;
	private String ownerLogin;
	private Integer ownerId;
	private String description;
	private String createdAt;
	private String updatedAt;
	private String pushedAt;
	private Integer size;
	private Integer stars;
	private Integer forks;
	private Integer tags;
	private Integer contributorsTotal;
	private Integer issueUsersTotal;
	private Integer pullUsersTotal;
	private Integer openIssues;
	private Integer closedIssues;
	private Integer openPulls;
	private Integer closedPulls;
	private Integer commits;
	private Integer watchers; // subscribers
	private String language;

	private Integer projectOutdegree;
	private Integer averageSociability;
	private Integer averageReputation;
		
	public Repository() {
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
	
	@Column(name = "name", unique = true, nullable = true, length = 500)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "fullName", unique = true, nullable = false, length = 500)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "ownerLogin", unique = true, nullable = true, length = 500)
	public String getOwnerLogin() {
		return ownerLogin;
	}
	public void setOwnerLogin(String ownerLogin) {
		this.ownerLogin = ownerLogin;
	}
	
	@Column(name = "ownerId", unique = true, nullable = true)
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "description", unique = true, nullable = true, length = 500)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "createdAt", unique = true, nullable = true, length = 500)
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	

	@Column(name = "updatedAt", unique = true, nullable = true, length = 500)
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}


	@Column(name = "pushedAt", unique = true, nullable = true, length = 500)
	public String getPushedAt() {
		return pushedAt;
	}
	public void setPushedAt(String pushedAt) {
		this.pushedAt = pushedAt;
	}

	
	@Column(name = "size", unique = true, nullable = true)
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

	@Column(name = "stars", unique = true, nullable = true)
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}

	@Column(name = "forks", unique = true, nullable = true)
	public Integer getForks() {
		return forks;
	}
	public void setForks(Integer forks) {
		this.forks = forks;
	}

	@Column(name = "openIssues", unique = true, nullable = true)
	public Integer getOpenIssues() {
		return openIssues;
	}

	public void setOpenIssues(Integer openIssues) {
		this.openIssues = openIssues;
	}

	@Column(name = "closedIssues", unique = true, nullable = true)
	public Integer getClosedIssues() {
		return closedIssues;
	}

	public void setClosedIssues(Integer closedIssues) {
		this.closedIssues = closedIssues;
	}
	

	@Column(name = "url", unique = true, nullable = true, length = 1000)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "contributorsTotal", unique = true, nullable = true)
	public Integer getContributorsTotal() {
		return contributorsTotal;
	}

	public void setContributorsTotal(Integer contributorsTotal) {
		this.contributorsTotal = contributorsTotal;
	}

	@Column(name = "issueUsersTotal", unique = true, nullable = true)
	public Integer getIssueUsersTotal() {
		return issueUsersTotal;
	}

	public void setIssueUsersTotal(Integer issueUsersTotal) {
		this.issueUsersTotal = issueUsersTotal;
	}

	@Column(name = "pullUsersTotal", unique = true, nullable = true)
	public Integer getPullUsersTotal() {
		return pullUsersTotal;
	}

	public void setPullUsersTotal(Integer pullUsersTotal) {
		this.pullUsersTotal = pullUsersTotal;
	}

	@Column(name = "openPulls", unique = true, nullable = true)
	public Integer getOpenPulls() {
		return openPulls;
	}

	public void setOpenPulls(Integer openPulls) {
		this.openPulls = openPulls;
	}

	@Column(name = "closedPulls", unique = true, nullable = true)
	public Integer getClosedPulls() {
		return closedPulls;
	}

	public void setClosedPulls(Integer closedPulls) {
		this.closedPulls = closedPulls;
	}

	@Column(name = "watchers", unique = true, nullable = true)
	public Integer getWatchers() {
		return watchers;
	}

	public void setWatchers(Integer watchers) {
		this.watchers = watchers;
	}

	@Column(name = "tags", unique = true, nullable = true)
	public Integer getTags() {
		return tags;
	}

	public void setTags(Integer tags) {
		this.tags = tags;
	}
	@Column(name = "commits", unique = true, nullable = true)
	public Integer getCommits() {
		return commits;
	}

	public void setCommits(Integer commits) {
		this.commits = commits;
	}
	public String getLanguage() {
		return language;
	}

	@Column(name = "language", unique = true, nullable = true, length = 1000)
	public void setLanguage(String language) {
		this.language = language;
	}


	@Column(name = "averageReputation", unique = true, nullable = true)
	public Integer getAverageReputation() {
		return averageReputation;
	}

	public void setAverageReputation(Integer averageReputation) {
		this.averageReputation = averageReputation;
	}

	@Column(name = "averageSociability", unique = true, nullable = true)
	public Integer getAverageSociability() {
		return averageSociability;
	}

	public void setAverageSociability(Integer averageSociability) {
		this.averageSociability = averageSociability;
	}

	@Column(name = "projectOutdegree", unique = true, nullable = true)
	public Integer getProjectOutdegree() {
		return projectOutdegree;
	}

	public void setProjectOutdegree(Integer projectOutdegree) {
		this.projectOutdegree = projectOutdegree;
	}

	@Override
	public String toString() {
		return "Repository [id=" + id + ", name=" + name + ", fullName="
				+ fullName + ", url=" + url + ", ownerLogin=" + ownerLogin
				+ ", ownerId=" + ownerId + ", description=" + description
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", size=" + size + ", stars=" + stars + ", forks=" + forks
				+ ", tags=" + tags + ", contributorsTotal=" + contributorsTotal
				+ ", issueUsersTotal=" + issueUsersTotal + ", pullUsersTotal="
				+ pullUsersTotal + ", openIssues=" + openIssues
				+ ", closedIssues=" + closedIssues + ", openPulls=" + openPulls
				+ ", closedPulls=" + closedPulls + ", commits=" + commits
				+ ", watchers=" + watchers + ", language=" + language
				+ ", projectOutdegree=" + projectOutdegree
				+ ", averageSociability=" + averageSociability
				+ ", averageReputation=" + averageReputation + "]";
	}
	
}
