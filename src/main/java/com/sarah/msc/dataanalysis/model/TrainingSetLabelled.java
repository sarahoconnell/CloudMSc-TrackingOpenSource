package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "trainingset_monthly_labelled", 
       catalog = "repository",
    	 uniqueConstraints = {@UniqueConstraint(columnNames = "id") })
public class TrainingSetLabelled implements java.io.Serializable{


	private Integer id;
	private String name;
	private Integer projectId;
	private Integer monthNumber;
	
	private Integer totalCommits;
	private Integer totalIssues;
	private Integer totalPulls;
	private Integer totalActivity;
	
	private Integer totalCommitsUsers;
	private Integer totalIssuesUsers;
	private Integer totalPullsUsers;
	private Integer totalActivityUsers;

	private Integer avgCommitSociability;
	private Integer avgCommitReputation;
	private Integer avgCommitOutdegree;

	private Integer avgIssueSociability;
	private Integer avgIssueReputation;
	private Integer avgIssueOutdegree;

	private Integer avgPullSociability;
	private Integer avgPullReputation;
	private Integer avgPullOutdegree;
	
	private Integer avgActivitySociability;
	private Integer avgActivityReputation;
	private Integer avgActivityOutdegree;
	private String outcome;
	
	public TrainingSetLabelled() {
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
	
	@Column(name = "name", unique = true, nullable = true, length = 500)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "outcome", unique = true, nullable = true, length = 500)
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	
	@Column(name = "projectId", unique = false, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "monthNumber", unique = false, nullable = true)
	public Integer getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(Integer monthNumber) {
		this.monthNumber = monthNumber;
	}

	@Column(name = "totalCommits", unique = false, nullable = true)
	public Integer getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(Integer totalCommits) {
		this.totalCommits = totalCommits;
	}
	

	@Column(name = "totalIssues", unique = false, nullable = true)
	public Integer getTotalIssues() {
		return totalIssues;
	}

	public void setTotalIssues(Integer totalIssues) {
		this.totalIssues = totalIssues;
	}	

	@Column(name = "totalPulls", unique = false, nullable = true)
	public Integer getTotalPulls() {
		return totalPulls;
	}

	public void setTotalPulls(Integer totalPulls) {
		this.totalPulls = totalCommits;
	}

	@Column(name = "totalActivity", unique = false, nullable = true)
	public Integer getTotalActivity() {
		return totalActivity;
	}

	public void setTotalActivity(Integer totalActivity) {
		this.totalActivity = totalActivity;
	}


	@Column(name = "totalCommitsUsers", unique = false, nullable = true)
	public Integer getTotalCommitsUsers() {
		return totalCommitsUsers;
	}

	public void setTotalCommitsUsers(Integer totalCommitsUsers) {
		this.totalCommitsUsers = totalCommitsUsers;
	}
	

	@Column(name = "totalIssuesUsers", unique = false, nullable = true)
	public Integer getTotalIssuesUsers() {
		return totalIssuesUsers;
	}

	public void setTotalIssuesUsers(Integer totalIssuesUsers) {
		this.totalIssuesUsers = totalIssuesUsers;
	}
	

	@Column(name = "totalPullsUsers", unique = false, nullable = true)
	public Integer getTotalPullsUsers() {
		return totalPullsUsers;
	}

	public void setTotalPullsUsers(Integer totalPullsUsers) {
		this.totalPullsUsers = totalPullsUsers;
	}


	@Column(name = "totalActivityUsers", unique = false, nullable = true)
	public Integer getTotalActivityUsers() {
		return totalActivityUsers;
	}

	public void setTotalActivityUsers(Integer totalActivityUsers) {
		this.totalActivityUsers = totalActivityUsers;
	}

	@Column(name = "avgCommitSociability", unique = false, nullable = true)
	public Integer getAvgCommitSociability() {
		return avgCommitSociability;
	}

	public void setAvgCommitSociability(Integer avgCommitSociability) {
		this.avgCommitSociability = avgCommitSociability;
	}

	@Column(name = "avgCommitReputation", unique = false, nullable = true)
	public Integer getAvgCommitReputation() {
		return avgCommitReputation;
	}

	public void setAvgCommitReputation(Integer avgCommitReputation) {
		this.avgCommitReputation = avgCommitReputation;
	}

	@Column(name = "avgCommitOutdegree", unique = false, nullable = true)
	public Integer getAvgCommitOutdegree() {
		return avgCommitOutdegree;
	}

	public void setAvgCommitOutdegree(Integer avgCommitOutdegree) {
		this.avgCommitOutdegree = avgCommitOutdegree;
	}
	

	@Column(name = "avgIssueSociability", unique = false, nullable = true)
	public Integer getAvgIssueSociability() {
		return avgIssueSociability;
	}

	public void setAvgIssueSociability(Integer avgIssueSociability) {
		this.avgIssueSociability = avgIssueSociability;
	}

	@Column(name = "avgIssueReputation", unique = false, nullable = true)
	public Integer getAvgIssueReputation() {
		return avgIssueReputation;
	}

	public void setAvgIssueReputation(Integer avgIssueReputation) {
		this.avgIssueReputation = avgIssueReputation;
	}

	@Column(name = "avgIssueOutdegree", unique = false, nullable = true)
	public Integer getAvgIssueOutdegree() {
		return avgIssueOutdegree;
	}

	public void setAvgIssueOutdegree(Integer avgIssueOutdegree) {
		this.avgIssueOutdegree = avgIssueOutdegree;
	}
	


	@Column(name = "avgPullSociability", unique = false, nullable = true)
	public Integer getAvgPullSociability() {
		return avgPullSociability;
	}

	public void setAvgPullSociability(Integer avgPullSociability) {
		this.avgPullSociability = avgPullSociability;
	}

	@Column(name = "avgPullReputation", unique = false, nullable = true)
	public Integer getAvgPullReputation() {
		return avgPullReputation;
	}

	public void setAvgPullReputation(Integer avgPullReputation) {
		this.avgPullReputation = avgPullReputation;
	}

	@Column(name = "avgPullOutdegree", unique = false, nullable = true)
	public Integer getAvgPullOutdegree() {
		return avgPullOutdegree;
	}

	public void setAvgPullOutdegree(Integer avgPullOutdegree) {
		this.avgPullOutdegree = avgPullOutdegree;
	}
	

	@Column(name = "avgActivitySociability", unique = false, nullable = true)
	public Integer getAvgActivitySociability() {
		return avgActivitySociability;
	}

	public void setAvgActivitySociability(Integer avgActivitySociability) {
		this.avgActivitySociability = avgActivitySociability;
	}

	@Column(name = "avgActivityReputation", unique = false, nullable = true)
	public Integer getAvgActivityReputation() {
		return avgActivityReputation;
	}

	public void setAvgActivityReputation(Integer avgActivityReputation) {
		this.avgActivityReputation = avgActivityReputation;
	}

	@Column(name = "avgActivityOutdegree", unique = false, nullable = true)
	public Integer getAvgActivityOutdegree() {
		return avgActivityOutdegree;
	}

	public void setAvgActivityOutdegree(Integer avgActivityOutdegree) {
		this.avgActivityOutdegree = avgActivityOutdegree;
	}
}
