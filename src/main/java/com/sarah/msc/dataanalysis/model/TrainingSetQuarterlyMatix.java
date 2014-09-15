package com.sarah.msc.dataanalysis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "trainingset_quarterly_matrix", 
       catalog = "repository",
       uniqueConstraints = {@UniqueConstraint(columnNames = "projectId") })
public class TrainingSetQuarterlyMatix implements java.io.Serializable{

	private Integer projectId;
	private String outcome;
	
	private Integer totalCommits1 = new Integer(0);
	private Integer totalIssues1 = new Integer(0);
	private Integer totalPulls1 = new Integer(0);
	private Integer totalActivity1 = new Integer(0);
	
	private Integer totalCommitsUsers1 = new Integer(0);
	private Integer totalIssuesUsers1 = new Integer(0);
	private Integer totalPullsUsers1 = new Integer(0);
	private Integer totalActivityUsers1 = new Integer(0);

	private Integer avgCommitSociability1 = new Integer(0);
	private Integer avgCommitReputation1 = new Integer(0);
	private Integer avgCommitOutdegree1 = new Integer(0);

	private Integer avgIssueSociability1 = new Integer(0);
	private Integer avgIssueReputation1 = new Integer(0);
	private Integer avgIssueOutdegree1 = new Integer(0);

	private Integer avgPullSociability1 = new Integer(0);
	private Integer avgPullReputation1 = new Integer(0);
	private Integer avgPullOutdegree1 = new Integer(0);
	
	private Integer avgActivitySociability1 = new Integer(0);
	private Integer avgActivityReputation1 = new Integer(0);
	private Integer avgActivityOutdegree1 = new Integer(0);
	

	private Integer totalCommits2 = new Integer(0);
	private Integer totalIssues2 = new Integer(0);
	private Integer totalPulls2 = new Integer(0);
	private Integer totalActivity2 = new Integer(0);
	
	private Integer totalCommitsUsers2 = new Integer(0);
	private Integer totalIssuesUsers2 = new Integer(0);
	private Integer totalPullsUsers2 = new Integer(0);
	private Integer totalActivityUsers2 = new Integer(0);

	private Integer avgCommitSociability2 = new Integer(0);
	private Integer avgCommitReputation2 = new Integer(0);
	private Integer avgCommitOutdegree2 = new Integer(0);

	private Integer avgIssueSociability2 = new Integer(0);
	private Integer avgIssueReputation2 = new Integer(0);
	private Integer avgIssueOutdegree2 = new Integer(0);

	private Integer avgPullSociability2 = new Integer(0);
	private Integer avgPullReputation2 = new Integer(0);
	private Integer avgPullOutdegree2 = new Integer(0);
	
	private Integer avgActivitySociability2 = new Integer(0);
	private Integer avgActivityReputation2 = new Integer(0);
	private Integer avgActivityOutdegree2 = new Integer(0);
	
	private Integer totalCommits3 = new Integer(0);
	private Integer totalIssues3 = new Integer(0);
	private Integer totalPulls3 = new Integer(0);
	private Integer totalActivity3 = new Integer(0);
	
	private Integer totalCommitsUsers3 = new Integer(0);
	private Integer totalIssuesUsers3 = new Integer(0);
	private Integer totalPullsUsers3 = new Integer(0);
	private Integer totalActivityUsers3 = new Integer(0);

	private Integer avgCommitSociability3 = new Integer(0);
	private Integer avgCommitReputation3 = new Integer(0);
	private Integer avgCommitOutdegree3 = new Integer(0);

	private Integer avgIssueSociability3 = new Integer(0);
	private Integer avgIssueReputation3 = new Integer(0);
	private Integer avgIssueOutdegree3 = new Integer(0);

	private Integer avgPullSociability3 = new Integer(0);
	private Integer avgPullReputation3 = new Integer(0);
	private Integer avgPullOutdegree3 = new Integer(0);
	
	private Integer avgActivitySociability3 = new Integer(0);
	private Integer avgActivityReputation3 = new Integer(0);
	private Integer avgActivityOutdegree3 = new Integer(0);
	
	private Integer totalCommits4 = new Integer(0);
	private Integer totalIssues4 = new Integer(0);
	private Integer totalPulls4 = new Integer(0);
	private Integer totalActivity4 = new Integer(0);
	
	private Integer totalCommitsUsers4 = new Integer(0);
	private Integer totalIssuesUsers4 = new Integer(0);
	private Integer totalPullsUsers4 = new Integer(0);
	private Integer totalActivityUsers4 = new Integer(0);

	private Integer avgCommitSociability4 = new Integer(0);
	private Integer avgCommitReputation4 = new Integer(0);
	private Integer avgCommitOutdegree4 = new Integer(0);

	private Integer avgIssueSociability4 = new Integer(0);
	private Integer avgIssueReputation4 = new Integer(0);
	private Integer avgIssueOutdegree4 = new Integer(0);

	private Integer avgPullSociability4 = new Integer(0);
	private Integer avgPullReputation4 = new Integer(0);
	private Integer avgPullOutdegree4 = new Integer(0);
	
	private Integer avgActivitySociability4 = new Integer(0);
	private Integer avgActivityReputation4 = new Integer(0);
	private Integer avgActivityOutdegree4 = new Integer(0);


	private Integer bugFixSpeed1 = new Integer(0);	
	private Integer bugFixSpeed2 = new Integer(0);
	private Integer bugFixSpeed3 = new Integer(0);
	private Integer bugFixSpeed4 = new Integer(0);
	
	public TrainingSetQuarterlyMatix() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name = "projectId", unique = false, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}


	@Column(name = "outcome", unique = true, nullable = true, length = 500)
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	
	@Column(name = "totalCommits1", unique = false, nullable = true)
	public Integer getTotalCommits1() {
		return totalCommits1;
	}

	public void setTotalCommits1(Integer totalCommits1) {
		this.totalCommits1 = totalCommits1;
	}

	@Column(name = "totalIssues1", unique = false, nullable = true)
	public Integer getTotalIssues1() {
		return totalIssues1;
	}

	public void setTotalIssues1(Integer totalIssues1) {
		this.totalIssues1 = totalIssues1;
	}

	@Column(name = "totalPulls1", unique = false, nullable = true)
	public Integer getTotalPulls1() {
		return totalPulls1;
	}

	public void setTotalPulls1(Integer totalPulls1) {
		this.totalPulls1 = totalPulls1;
	}

	@Column(name = "totalActivity1", unique = false, nullable = true)
	public Integer getTotalActivity1() {
		return totalActivity1;
	}

	public void setTotalActivity1(Integer totalActivity1) {
		this.totalActivity1 = totalActivity1;
	}

	@Column(name = "totalCommitsUsers1", unique = false, nullable = true)
	public Integer getTotalCommitsUsers1() {
		return totalCommitsUsers1;
	}

	public void setTotalCommitsUsers1(Integer totalCommitsUsers1) {
		this.totalCommitsUsers1 = totalCommitsUsers1;
	}

	@Column(name = "totalIssuesUsers1", unique = false, nullable = true)
	public Integer getTotalIssuesUsers1() {
		return totalIssuesUsers1;
	}

	public void setTotalIssuesUsers1(Integer totalIssuesUsers1) {
		this.totalIssuesUsers1 = totalIssuesUsers1;
	}

	@Column(name = "totalPullsUsers1", unique = false, nullable = true)
	public Integer getTotalPullsUsers1() {
		return totalPullsUsers1;
	}

	public void setTotalPullsUsers1(Integer totalPullsUsers1) {
		this.totalPullsUsers1 = totalPullsUsers1;
	}

	@Column(name = "totalActivityUsers1", unique = false, nullable = true)
	public Integer getTotalActivityUsers1() {
		return totalActivityUsers1;
	}

	public void setTotalActivityUsers1(Integer totalActivityUsers1) {
		this.totalActivityUsers1 = totalActivityUsers1;
	}

	@Column(name = "avgCommitSociability1", unique = false, nullable = true)
	public Integer getAvgCommitSociability1() {
		return avgCommitSociability1;
	}

	public void setAvgCommitSociability1(Integer avgCommitSociability1) {
		this.avgCommitSociability1 = avgCommitSociability1;
	}

	@Column(name = "avgCommitReputation1", unique = false, nullable = true)
	public Integer getAvgCommitReputation1() {
		return avgCommitReputation1;
	}

	public void setAvgCommitReputation1(Integer avgCommitReputation1) {
		this.avgCommitReputation1 = avgCommitReputation1;
	}

	@Column(name = "avgCommitOutdegree1", unique = false, nullable = true)
	public Integer getAvgCommitOutdegree1() {
		return avgCommitOutdegree1;
	}

	public void setAvgCommitOutdegree1(Integer avgCommitOutdegree1) {
		this.avgCommitOutdegree1 = avgCommitOutdegree1;
	}

	@Column(name = "avgIssueSociability1", unique = false, nullable = true)
	public Integer getAvgIssueSociability1() {
		return avgIssueSociability1;
	}

	public void setAvgIssueSociability1(Integer avgIssueSociability1) {
		this.avgIssueSociability1 = avgIssueSociability1;
	}

	@Column(name = "avgIssueReputation1", unique = false, nullable = true)
	public Integer getAvgIssueReputation1() {
		return avgIssueReputation1;
	}

	public void setAvgIssueReputation1(Integer avgIssueReputation1) {
		this.avgIssueReputation1 = avgIssueReputation1;
	}

	@Column(name = "avgIssueOutdegree1", unique = false, nullable = true)
	public Integer getAvgIssueOutdegree1() {
		return avgIssueOutdegree1;
	}

	public void setAvgIssueOutdegree1(Integer avgIssueOutdegree1) {
		this.avgIssueOutdegree1 = avgIssueOutdegree1;
	}

	@Column(name = "avgPullSociability1", unique = false, nullable = true)
	public Integer getAvgPullSociability1() {
		return avgPullSociability1;
	}

	public void setAvgPullSociability1(Integer avgPullSociability1) {
		this.avgPullSociability1 = avgPullSociability1;
	}

	@Column(name = "avgPullReputation1", unique = false, nullable = true)
	public Integer getAvgPullReputation1() {
		return avgPullReputation1;
	}

	public void setAvgPullReputation1(Integer avgPullReputation1) {
		this.avgPullReputation1 = avgPullReputation1;
	}

	@Column(name = "avgPullOutdegree1", unique = false, nullable = true)
	public Integer getAvgPullOutdegree1() {
		return avgPullOutdegree1;
	}

	public void setAvgPullOutdegree1(Integer avgPullOutdegree1) {
		this.avgPullOutdegree1 = avgPullOutdegree1;
	}

	@Column(name = "avgActivitySociability1", unique = false, nullable = true)
	public Integer getAvgActivitySociability1() {
		return avgActivitySociability1;
	}

	public void setAvgActivitySociability1(Integer avgActivitySociability1) {
		this.avgActivitySociability1 = avgActivitySociability1;
	}

	@Column(name = "avgActivityReputation1", unique = false, nullable = true)
	public Integer getAvgActivityReputation1() {
		return avgActivityReputation1;
	}

	@Column(name = "avgActivityOutdegree1", unique = false, nullable = true)
	public void setAvgActivityReputation1(Integer avgActivityReputation1) {
		this.avgActivityReputation1 = avgActivityReputation1;
	}

	public Integer getAvgActivityOutdegree1() {
		return avgActivityOutdegree1;
	}

	@Column(name = "avgActivityOutdegree1", unique = false, nullable = true)
	public void setAvgActivityOutdegree1(Integer avgActivityOutdegree1) {
		this.avgActivityOutdegree1 = avgActivityOutdegree1;
	}

	

	@Column(name = "totalCommits2", unique = false, nullable = true)
	public Integer getTotalCommits2() {
		return totalCommits2;
	}

	public void setTotalCommits2(Integer totalCommits2) {
		this.totalCommits2 = totalCommits2;
	}

	@Column(name = "totalIssues2", unique = false, nullable = true)
	public Integer getTotalIssues2() {
		return totalIssues2;
	}

	public void setTotalIssues2(Integer totalIssues2) {
		this.totalIssues2 = totalIssues2;
	}

	@Column(name = "totalPulls2", unique = false, nullable = true)
	public Integer getTotalPulls2() {
		return totalPulls2;
	}

	public void setTotalPulls2(Integer totalPulls2) {
		this.totalPulls2 = totalPulls2;
	}

	@Column(name = "totalActivity2", unique = false, nullable = true)
	public Integer getTotalActivity2() {
		return totalActivity2;
	}

	public void setTotalActivity2(Integer totalActivity2) {
		this.totalActivity2 = totalActivity2;
	}

	@Column(name = "totalCommitsUsers2", unique = false, nullable = true)
	public Integer getTotalCommitsUsers2() {
		return totalCommitsUsers2;
	}

	public void setTotalCommitsUsers2(Integer totalCommitsUsers2) {
		this.totalCommitsUsers2 = totalCommitsUsers2;
	}

	@Column(name = "totalIssuesUsers2", unique = false, nullable = true)
	public Integer getTotalIssuesUsers2() {
		return totalIssuesUsers2;
	}

	public void setTotalIssuesUsers2(Integer totalIssuesUsers2) {
		this.totalIssuesUsers2 = totalIssuesUsers2;
	}

	@Column(name = "totalPullsUsers2", unique = false, nullable = true)
	public Integer getTotalPullsUsers2() {
		return totalPullsUsers2;
	}

	public void setTotalPullsUsers2(Integer totalPullsUsers2) {
		this.totalPullsUsers2 = totalPullsUsers2;
	}

	@Column(name = "totalActivityUsers2", unique = false, nullable = true)
	public Integer getTotalActivityUsers2() {
		return totalActivityUsers2;
	}

	public void setTotalActivityUsers2(Integer totalActivityUsers2) {
		this.totalActivityUsers2 = totalActivityUsers2;
	}

	@Column(name = "avgCommitSociability2", unique = false, nullable = true)
	public Integer getAvgCommitSociability2() {
		return avgCommitSociability2;
	}

	public void setAvgCommitSociability2(Integer avgCommitSociability2) {
		this.avgCommitSociability2 = avgCommitSociability2;
	}

	@Column(name = "avgCommitReputation2", unique = false, nullable = true)
	public Integer getAvgCommitReputation2() {
		return avgCommitReputation2;
	}

	public void setAvgCommitReputation2(Integer avgCommitReputation2) {
		this.avgCommitReputation2 = avgCommitReputation2;
	}

	@Column(name = "avgCommitOutdegree2", unique = false, nullable = true)
	public Integer getAvgCommitOutdegree2() {
		return avgCommitOutdegree2;
	}

	public void setAvgCommitOutdegree2(Integer avgCommitOutdegree2) {
		this.avgCommitOutdegree2 = avgCommitOutdegree2;
	}

	@Column(name = "avgIssueSociability2", unique = false, nullable = true)
	public Integer getAvgIssueSociability2() {
		return avgIssueSociability2;
	}

	public void setAvgIssueSociability2(Integer avgIssueSociability2) {
		this.avgIssueSociability2 = avgIssueSociability2;
	}

	@Column(name = "avgIssueReputation2", unique = false, nullable = true)
	public Integer getAvgIssueReputation2() {
		return avgIssueReputation2;
	}

	public void setAvgIssueReputation2(Integer avgIssueReputation2) {
		this.avgIssueReputation2 = avgIssueReputation2;
	}

	@Column(name = "avgIssueOutdegree2", unique = false, nullable = true)
	public Integer getAvgIssueOutdegree2() {
		return avgIssueOutdegree2;
	}

	public void setAvgIssueOutdegree2(Integer avgIssueOutdegree2) {
		this.avgIssueOutdegree2 = avgIssueOutdegree2;
	}

	@Column(name = "avgPullSociability2", unique = false, nullable = true)
	public Integer getAvgPullSociability2() {
		return avgPullSociability2;
	}

	public void setAvgPullSociability2(Integer avgPullSociability2) {
		this.avgPullSociability2 = avgPullSociability2;
	}

	@Column(name = "avgPullReputation2", unique = false, nullable = true)
	public Integer getAvgPullReputation2() {
		return avgPullReputation2;
	}

	public void setAvgPullReputation2(Integer avgPullReputation2) {
		this.avgPullReputation2 = avgPullReputation2;
	}

	@Column(name = "avgPullOutdegree2", unique = false, nullable = true)
	public Integer getAvgPullOutdegree2() {
		return avgPullOutdegree2;
	}

	public void setAvgPullOutdegree2(Integer avgPullOutdegree2) {
		this.avgPullOutdegree2 = avgPullOutdegree2;
	}

	@Column(name = "avgActivitySociability2", unique = false, nullable = true)
	public Integer getAvgActivitySociability2() {
		return avgActivitySociability2;
	}

	public void setAvgActivitySociability2(Integer avgActivitySociability2) {
		this.avgActivitySociability2 = avgActivitySociability2;
	}

	@Column(name = "avgActivityReputation2", unique = false, nullable = true)
	public Integer getAvgActivityReputation2() {
		return avgActivityReputation2;
	}

	@Column(name = "avgActivityOutdegree2", unique = false, nullable = true)
	public void setAvgActivityReputation2(Integer avgActivityReputation2) {
		this.avgActivityReputation2 = avgActivityReputation2;
	}

	public Integer getAvgActivityOutdegree2() {
		return avgActivityOutdegree2;
	}

	@Column(name = "avgActivityOutdegree2", unique = false, nullable = true)
	public void setAvgActivityOutdegree2(Integer avgActivityOutdegree2) {
		this.avgActivityOutdegree2 = avgActivityOutdegree2;
	}

	

	@Column(name = "totalCommits3", unique = false, nullable = true)
	public Integer getTotalCommits3() {
		return totalCommits3;
	}

	public void setTotalCommits3(Integer totalCommits3) {
		this.totalCommits3 = totalCommits3;
	}

	@Column(name = "totalIssues3", unique = false, nullable = true)
	public Integer getTotalIssues3() {
		return totalIssues3;
	}

	public void setTotalIssues3(Integer totalIssues3) {
		this.totalIssues3 = totalIssues3;
	}

	@Column(name = "totalPulls3", unique = false, nullable = true)
	public Integer getTotalPulls3() {
		return totalPulls3;
	}

	public void setTotalPulls3(Integer totalPulls3) {
		this.totalPulls3 = totalPulls3;
	}

	@Column(name = "totalActivity3", unique = false, nullable = true)
	public Integer getTotalActivity3() {
		return totalActivity3;
	}

	public void setTotalActivity3(Integer totalActivity3) {
		this.totalActivity3 = totalActivity3;
	}

	@Column(name = "totalCommitsUsers3", unique = false, nullable = true)
	public Integer getTotalCommitsUsers3() {
		return totalCommitsUsers3;
	}

	public void setTotalCommitsUsers3(Integer totalCommitsUsers3) {
		this.totalCommitsUsers3 = totalCommitsUsers3;
	}

	@Column(name = "totalIssuesUsers3", unique = false, nullable = true)
	public Integer getTotalIssuesUsers3() {
		return totalIssuesUsers3;
	}

	public void setTotalIssuesUsers3(Integer totalIssuesUsers3) {
		this.totalIssuesUsers3 = totalIssuesUsers3;
	}

	@Column(name = "totalPullsUsers3", unique = false, nullable = true)
	public Integer getTotalPullsUsers3() {
		return totalPullsUsers3;
	}

	public void setTotalPullsUsers3(Integer totalPullsUsers3) {
		this.totalPullsUsers3 = totalPullsUsers3;
	}

	@Column(name = "totalActivityUsers3", unique = false, nullable = true)
	public Integer getTotalActivityUsers3() {
		return totalActivityUsers3;
	}

	public void setTotalActivityUsers3(Integer totalActivityUsers3) {
		this.totalActivityUsers3 = totalActivityUsers3;
	}

	@Column(name = "avgCommitSociability3", unique = false, nullable = true)
	public Integer getAvgCommitSociability3() {
		return avgCommitSociability3;
	}

	public void setAvgCommitSociability3(Integer avgCommitSociability3) {
		this.avgCommitSociability3 = avgCommitSociability3;
	}

	@Column(name = "avgCommitReputation3", unique = false, nullable = true)
	public Integer getAvgCommitReputation3() {
		return avgCommitReputation3;
	}

	public void setAvgCommitReputation3(Integer avgCommitReputation3) {
		this.avgCommitReputation3 = avgCommitReputation3;
	}

	@Column(name = "avgCommitOutdegree3", unique = false, nullable = true)
	public Integer getAvgCommitOutdegree3() {
		return avgCommitOutdegree3;
	}

	public void setAvgCommitOutdegree3(Integer avgCommitOutdegree3) {
		this.avgCommitOutdegree3 = avgCommitOutdegree3;
	}

	@Column(name = "avgIssueSociability3", unique = false, nullable = true)
	public Integer getAvgIssueSociability3() {
		return avgIssueSociability3;
	}

	public void setAvgIssueSociability3(Integer avgIssueSociability3) {
		this.avgIssueSociability3 = avgIssueSociability3;
	}

	@Column(name = "avgIssueReputation3", unique = false, nullable = true)
	public Integer getAvgIssueReputation3() {
		return avgIssueReputation3;
	}

	public void setAvgIssueReputation3(Integer avgIssueReputation3) {
		this.avgIssueReputation3 = avgIssueReputation3;
	}

	@Column(name = "avgIssueOutdegree3", unique = false, nullable = true)
	public Integer getAvgIssueOutdegree3() {
		return avgIssueOutdegree3;
	}

	public void setAvgIssueOutdegree3(Integer avgIssueOutdegree3) {
		this.avgIssueOutdegree3 = avgIssueOutdegree3;
	}

	@Column(name = "avgPullSociability3", unique = false, nullable = true)
	public Integer getAvgPullSociability3() {
		return avgPullSociability3;
	}

	public void setAvgPullSociability3(Integer avgPullSociability3) {
		this.avgPullSociability3 = avgPullSociability3;
	}

	@Column(name = "avgPullReputation3", unique = false, nullable = true)
	public Integer getAvgPullReputation3() {
		return avgPullReputation3;
	}

	public void setAvgPullReputation3(Integer avgPullReputation3) {
		this.avgPullReputation3 = avgPullReputation3;
	}

	@Column(name = "avgPullOutdegree3", unique = false, nullable = true)
	public Integer getAvgPullOutdegree3() {
		return avgPullOutdegree3;
	}

	public void setAvgPullOutdegree3(Integer avgPullOutdegree3) {
		this.avgPullOutdegree3 = avgPullOutdegree3;
	}

	@Column(name = "avgActivitySociability3", unique = false, nullable = true)
	public Integer getAvgActivitySociability3() {
		return avgActivitySociability3;
	}

	public void setAvgActivitySociability3(Integer avgActivitySociability3) {
		this.avgActivitySociability3 = avgActivitySociability3;
	}

	@Column(name = "avgActivityReputation3", unique = false, nullable = true)
	public Integer getAvgActivityReputation3() {
		return avgActivityReputation3;
	}

	@Column(name = "avgActivityOutdegree3", unique = false, nullable = true)
	public void setAvgActivityReputation3(Integer avgActivityReputation3) {
		this.avgActivityReputation3 = avgActivityReputation3;
	}

	public Integer getAvgActivityOutdegree3() {
		return avgActivityOutdegree3;
	}

	@Column(name = "avgActivityOutdegree3", unique = false, nullable = true)
	public void setAvgActivityOutdegree3(Integer avgActivityOutdegree3) {
		this.avgActivityOutdegree3 = avgActivityOutdegree3;
	}


	@Column(name = "totalCommits4", unique = false, nullable = true)
	public Integer getTotalCommits4() {
		return totalCommits4;
	}

	public void setTotalCommits4(Integer totalCommits4) {
		this.totalCommits4 = totalCommits4;
	}

	@Column(name = "totalIssues4", unique = false, nullable = true)
	public Integer getTotalIssues4() {
		return totalIssues4;
	}

	public void setTotalIssues4(Integer totalIssues4) {
		this.totalIssues4 = totalIssues4;
	}

	@Column(name = "totalPulls4", unique = false, nullable = true)
	public Integer getTotalPulls4() {
		return totalPulls4;
	}

	public void setTotalPulls4(Integer totalPulls4) {
		this.totalPulls4 = totalPulls4;
	}

	@Column(name = "totalActivity4", unique = false, nullable = true)
	public Integer getTotalActivity4() {
		return totalActivity4;
	}

	public void setTotalActivity4(Integer totalActivity4) {
		this.totalActivity4 = totalActivity4;
	}

	@Column(name = "totalCommitsUsers4", unique = false, nullable = true)
	public Integer getTotalCommitsUsers4() {
		return totalCommitsUsers4;
	}

	public void setTotalCommitsUsers4(Integer totalCommitsUsers4) {
		this.totalCommitsUsers4 = totalCommitsUsers4;
	}

	@Column(name = "totalIssuesUsers4", unique = false, nullable = true)
	public Integer getTotalIssuesUsers4() {
		return totalIssuesUsers4;
	}

	public void setTotalIssuesUsers4(Integer totalIssuesUsers4) {
		this.totalIssuesUsers4 = totalIssuesUsers4;
	}

	@Column(name = "totalPullsUsers4", unique = false, nullable = true)
	public Integer getTotalPullsUsers4() {
		return totalPullsUsers4;
	}

	public void setTotalPullsUsers4(Integer totalPullsUsers4) {
		this.totalPullsUsers4 = totalPullsUsers4;
	}

	@Column(name = "totalActivityUsers4", unique = false, nullable = true)
	public Integer getTotalActivityUsers4() {
		return totalActivityUsers4;
	}

	public void setTotalActivityUsers4(Integer totalActivityUsers4) {
		this.totalActivityUsers4 = totalActivityUsers4;
	}

	@Column(name = "avgCommitSociability4", unique = false, nullable = true)
	public Integer getAvgCommitSociability4() {
		return avgCommitSociability4;
	}

	public void setAvgCommitSociability4(Integer avgCommitSociability4) {
		this.avgCommitSociability4 = avgCommitSociability4;
	}

	@Column(name = "avgCommitReputation4", unique = false, nullable = true)
	public Integer getAvgCommitReputation4() {
		return avgCommitReputation4;
	}

	public void setAvgCommitReputation4(Integer avgCommitReputation4) {
		this.avgCommitReputation4 = avgCommitReputation4;
	}

	@Column(name = "avgCommitOutdegree4", unique = false, nullable = true)
	public Integer getAvgCommitOutdegree4() {
		return avgCommitOutdegree4;
	}

	public void setAvgCommitOutdegree4(Integer avgCommitOutdegree4) {
		this.avgCommitOutdegree4 = avgCommitOutdegree4;
	}

	@Column(name = "avgIssueSociability4", unique = false, nullable = true)
	public Integer getAvgIssueSociability4() {
		return avgIssueSociability4;
	}

	public void setAvgIssueSociability4(Integer avgIssueSociability4) {
		this.avgIssueSociability4 = avgIssueSociability4;
	}

	@Column(name = "avgIssueReputation4", unique = false, nullable = true)
	public Integer getAvgIssueReputation4() {
		return avgIssueReputation4;
	}

	public void setAvgIssueReputation4(Integer avgIssueReputation4) {
		this.avgIssueReputation4 = avgIssueReputation4;
	}

	@Column(name = "avgIssueOutdegree4", unique = false, nullable = true)
	public Integer getAvgIssueOutdegree4() {
		return avgIssueOutdegree4;
	}

	public void setAvgIssueOutdegree4(Integer avgIssueOutdegree4) {
		this.avgIssueOutdegree4 = avgIssueOutdegree4;
	}

	@Column(name = "avgPullSociability4", unique = false, nullable = true)
	public Integer getAvgPullSociability4() {
		return avgPullSociability4;
	}

	public void setAvgPullSociability4(Integer avgPullSociability4) {
		this.avgPullSociability4 = avgPullSociability4;
	}

	@Column(name = "avgPullReputation4", unique = false, nullable = true)
	public Integer getAvgPullReputation4() {
		return avgPullReputation4;
	}

	public void setAvgPullReputation4(Integer avgPullReputation4) {
		this.avgPullReputation4 = avgPullReputation4;
	}

	@Column(name = "avgPullOutdegree4", unique = false, nullable = true)
	public Integer getAvgPullOutdegree4() {
		return avgPullOutdegree4;
	}

	public void setAvgPullOutdegree4(Integer avgPullOutdegree4) {
		this.avgPullOutdegree4 = avgPullOutdegree4;
	}

	@Column(name = "avgActivitySociability4", unique = false, nullable = true)
	public Integer getAvgActivitySociability4() {
		return avgActivitySociability4;
	}

	public void setAvgActivitySociability4(Integer avgActivitySociability4) {
		this.avgActivitySociability4 = avgActivitySociability4;
	}

	@Column(name = "avgActivityReputation4", unique = false, nullable = true)
	public Integer getAvgActivityReputation4() {
		return avgActivityReputation4;
	}

	@Column(name = "avgActivityOutdegree4", unique = false, nullable = true)
	public void setAvgActivityReputation4(Integer avgActivityReputation4) {
		this.avgActivityReputation4 = avgActivityReputation4;
	}

	public Integer getAvgActivityOutdegree4() {
		return avgActivityOutdegree4;
	}

	@Column(name = "avgActivityOutdegree4", unique = false, nullable = true)
	public void setAvgActivityOutdegree4(Integer avgActivityOutdegree4) {
		this.avgActivityOutdegree4 = avgActivityOutdegree4;
	}

	@Column(name = "bugFixSpeed1", unique = false, nullable = true)
	public Integer getBugFixSpeed1() {
		return bugFixSpeed1;
	}

	public void setBugFixSpeed1(Integer bugFixSpeed1) {
		this.bugFixSpeed1 = bugFixSpeed1;
	}

	@Column(name = "bugFixSpeed2", unique = false, nullable = true)
	public Integer getBugFixSpeed2() {
		return bugFixSpeed2;
	}

	public void setBugFixSpeed2(Integer bugFixSpeed2) {
		this.bugFixSpeed2 = bugFixSpeed2;
	}

	@Column(name = "bugFixSpeed3", unique = false, nullable = true)
	public Integer getBugFixSpeed3() {
		return bugFixSpeed3;
	}

	public void setBugFixSpeed3(Integer bugFixSpeed3) {
		this.bugFixSpeed3 = bugFixSpeed3;
	}

	@Column(name = "bugFixSpeed4", unique = false, nullable = true)
	public Integer getBugFixSpeed4() {
		return bugFixSpeed4;
	}

	public void setBugFixSpeed4(Integer bugFixSpeed4) {
		this.bugFixSpeed4 = bugFixSpeed4;
	}
	
}
