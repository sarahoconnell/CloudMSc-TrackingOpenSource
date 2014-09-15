package com.sarah.msc.dataanalysis.run;
/**
 * Following the execution of the data retrieval process, this is one of the data preparation steps necessary before
 * using the data in RapidMiner 
 * 
 * This class aggregates data that has been collected in monthly chunks into more manageable quarterly sections.
 * This will only need to be run once following data retrieval. 
 * 
 * Data is aggregated for both the training and scoring/testing sets.
 * 
 * @author Sarah O'Connell
 */
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.sarah.msc.dataanalysis.model.ScoringSetQuarterlyMatix;
import com.sarah.msc.dataanalysis.model.TrainingSetQuarterlyMatix;
import com.sarah.msc.dataanalysis.model.ScoringSetLabelled;
import com.sarah.msc.dataanalysis.model.TrainingSetLabelled;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;

public class CreateTimelineMatrix {

	private static Logger logger = Logger.getLogger(GitHubPopularRepoGetter.class);

	public static void main(String[] args){

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		// TRAINING 
		List<TrainingSetLabelled> projectList = session.createQuery("from TrainingSetLabelled group by projectId").list();
		
		int count=1;
		for (TrainingSetLabelled project : projectList) {

			logger.info("TRAINING Project: "+count +"/"+ projectList.size()); 
			
			Integer projectId = project.getProjectId();
			logger.info("Project: "+projectId); 

			List<TrainingSetLabelled> projectMonths = session.createQuery("from TrainingSetLabelled where projectId = "+projectId).list();			

			TrainingSetQuarterlyMatix newProject = new TrainingSetQuarterlyMatix();
			newProject.setProjectId(projectId);
			newProject.setOutcome(project.getOutcome());
			for (TrainingSetLabelled projectMonth : projectMonths) {
				
				// 1,2,3
				if(projectMonth.getMonthNumber()<=3){
					newProject.setTotalCommits1(newProject.getTotalCommits1()+projectMonth.getTotalCommits());
					newProject.setTotalIssues1(newProject.getTotalIssues1()+projectMonth.getTotalIssues());
					newProject.setTotalPulls1(newProject.getTotalPulls1()+projectMonth.getTotalPulls());
					newProject.setTotalActivity1(newProject.getTotalActivity1()+projectMonth.getTotalActivity());
					
					newProject.setTotalCommitsUsers1(newProject.getTotalCommitsUsers1()+projectMonth.getTotalCommitsUsers());
					newProject.setTotalIssuesUsers1(newProject.getTotalIssuesUsers1()+projectMonth.getTotalIssuesUsers());
					newProject.setTotalPullsUsers1(newProject.getTotalPullsUsers1()+projectMonth.getTotalPullsUsers());
					newProject.setTotalActivityUsers1(newProject.getTotalActivityUsers1()+projectMonth.getTotalActivityUsers());
					
					newProject.setAvgCommitSociability1(newProject.getAvgCommitSociability1()+projectMonth.getAvgCommitSociability());
					newProject.setAvgIssueSociability1(newProject.getAvgIssueSociability1()+projectMonth.getAvgIssueSociability());
					newProject.setAvgPullSociability1(newProject.getAvgPullSociability1()+projectMonth.getAvgPullSociability());
					newProject.setAvgActivitySociability1(newProject.getAvgActivitySociability1()+projectMonth.getAvgActivitySociability());
					
					newProject.setAvgCommitReputation1(newProject.getAvgCommitReputation1()+projectMonth.getAvgCommitReputation());
					newProject.setAvgIssueReputation1(newProject.getAvgIssueReputation1()+projectMonth.getAvgIssueReputation());
					newProject.setAvgPullReputation1(newProject.getAvgPullReputation1()+projectMonth.getAvgPullReputation());
					newProject.setAvgActivityReputation1(newProject.getAvgActivityReputation1()+projectMonth.getAvgActivityReputation());

					newProject.setAvgCommitOutdegree1(newProject.getAvgCommitOutdegree1()+projectMonth.getAvgCommitOutdegree());
					newProject.setAvgIssueOutdegree1(newProject.getAvgIssueOutdegree1()+projectMonth.getAvgIssueOutdegree());
					newProject.setAvgPullOutdegree1(newProject.getAvgPullOutdegree1()+projectMonth.getAvgPullOutdegree());
					newProject.setAvgActivityOutdegree1(newProject.getAvgActivityOutdegree1()+projectMonth.getAvgActivityOutdegree());						
				}
				else // 4,5,6
					if(projectMonth.getMonthNumber()>3 && projectMonth.getMonthNumber()<=6){
						newProject.setTotalCommits2(newProject.getTotalCommits2()+projectMonth.getTotalCommits());
						newProject.setTotalIssues2(newProject.getTotalIssues2()+projectMonth.getTotalIssues());
						newProject.setTotalPulls2(newProject.getTotalPulls2()+projectMonth.getTotalPulls());
						newProject.setTotalActivity2(newProject.getTotalActivity2()+projectMonth.getTotalActivity());
						
						newProject.setTotalCommitsUsers2(newProject.getTotalCommitsUsers2()+projectMonth.getTotalCommitsUsers());
						newProject.setTotalIssuesUsers2(newProject.getTotalIssuesUsers2()+projectMonth.getTotalIssuesUsers());
						newProject.setTotalPullsUsers2(newProject.getTotalPullsUsers2()+projectMonth.getTotalPullsUsers());
						newProject.setTotalActivityUsers2(newProject.getTotalActivityUsers2()+projectMonth.getTotalActivityUsers());
						
						newProject.setAvgCommitSociability2(newProject.getAvgCommitSociability2()+projectMonth.getAvgCommitSociability());
						newProject.setAvgIssueSociability2(newProject.getAvgIssueSociability2()+projectMonth.getAvgIssueSociability());
						newProject.setAvgPullSociability2(newProject.getAvgPullSociability2()+projectMonth.getAvgPullSociability());
						newProject.setAvgActivitySociability2(newProject.getAvgActivitySociability2()+projectMonth.getAvgActivitySociability());
						
						newProject.setAvgCommitReputation2(newProject.getAvgCommitReputation2()+projectMonth.getAvgCommitReputation());
						newProject.setAvgIssueReputation2(newProject.getAvgIssueReputation2()+projectMonth.getAvgIssueReputation());
						newProject.setAvgPullReputation2(newProject.getAvgPullReputation2()+projectMonth.getAvgPullReputation());
						newProject.setAvgActivityReputation2(newProject.getAvgActivityReputation2()+projectMonth.getAvgActivityReputation());

						newProject.setAvgCommitOutdegree2(newProject.getAvgCommitOutdegree2()+projectMonth.getAvgCommitOutdegree());
						newProject.setAvgIssueOutdegree2(newProject.getAvgIssueOutdegree2()+projectMonth.getAvgIssueOutdegree());
						newProject.setAvgPullOutdegree2(newProject.getAvgPullOutdegree2()+projectMonth.getAvgPullOutdegree());
						newProject.setAvgActivityOutdegree2(newProject.getAvgActivityOutdegree2()+projectMonth.getAvgActivityOutdegree());	
				}
				else // 7,8,9
					if(projectMonth.getMonthNumber()>6 && projectMonth.getMonthNumber()<=9){
						newProject.setTotalCommits3(newProject.getTotalCommits3()+projectMonth.getTotalCommits());
						newProject.setTotalIssues3(newProject.getTotalIssues3()+projectMonth.getTotalIssues());
						newProject.setTotalPulls3(newProject.getTotalPulls3()+projectMonth.getTotalPulls());
						newProject.setTotalActivity3(newProject.getTotalActivity3()+projectMonth.getTotalActivity());
						
						newProject.setTotalCommitsUsers3(newProject.getTotalCommitsUsers3()+projectMonth.getTotalCommitsUsers());
						newProject.setTotalIssuesUsers3(newProject.getTotalIssuesUsers3()+projectMonth.getTotalIssuesUsers());
						newProject.setTotalPullsUsers3(newProject.getTotalPullsUsers3()+projectMonth.getTotalPullsUsers());
						newProject.setTotalActivityUsers3(newProject.getTotalActivityUsers3()+projectMonth.getTotalActivityUsers());
						
						newProject.setAvgCommitSociability3(newProject.getAvgCommitSociability3()+projectMonth.getAvgCommitSociability());
						newProject.setAvgIssueSociability3(newProject.getAvgIssueSociability3()+projectMonth.getAvgIssueSociability());
						newProject.setAvgPullSociability3(newProject.getAvgPullSociability3()+projectMonth.getAvgPullSociability());
						newProject.setAvgActivitySociability3(newProject.getAvgActivitySociability3()+projectMonth.getAvgActivitySociability());
						
						newProject.setAvgCommitReputation3(newProject.getAvgCommitReputation3()+projectMonth.getAvgCommitReputation());
						newProject.setAvgIssueReputation3(newProject.getAvgIssueReputation3()+projectMonth.getAvgIssueReputation());
						newProject.setAvgPullReputation3(newProject.getAvgPullReputation3()+projectMonth.getAvgPullReputation());
						newProject.setAvgActivityReputation3(newProject.getAvgActivityReputation3()+projectMonth.getAvgActivityReputation());

						newProject.setAvgCommitOutdegree3(newProject.getAvgCommitOutdegree3()+projectMonth.getAvgCommitOutdegree());
						newProject.setAvgIssueOutdegree3(newProject.getAvgIssueOutdegree3()+projectMonth.getAvgIssueOutdegree());
						newProject.setAvgPullOutdegree3(newProject.getAvgPullOutdegree3()+projectMonth.getAvgPullOutdegree());
						newProject.setAvgActivityOutdegree3(newProject.getAvgActivityOutdegree3()+projectMonth.getAvgActivityOutdegree());	
				}
				else // 10,11,12
					if(projectMonth.getMonthNumber()>9 && projectMonth.getMonthNumber()<=12){
						
						newProject.setTotalCommits4(newProject.getTotalCommits4()+projectMonth.getTotalCommits());
						newProject.setTotalIssues4(newProject.getTotalIssues4()+projectMonth.getTotalIssues());
						newProject.setTotalPulls4(newProject.getTotalPulls4()+projectMonth.getTotalPulls());
						newProject.setTotalActivity4(newProject.getTotalActivity4()+projectMonth.getTotalActivity());
						
						newProject.setTotalCommitsUsers4(newProject.getTotalCommitsUsers4()+projectMonth.getTotalCommitsUsers());
						newProject.setTotalIssuesUsers4(newProject.getTotalIssuesUsers4()+projectMonth.getTotalIssuesUsers());
						newProject.setTotalPullsUsers4(newProject.getTotalPullsUsers4()+projectMonth.getTotalPullsUsers());
						newProject.setTotalActivityUsers4(newProject.getTotalActivityUsers4()+projectMonth.getTotalActivityUsers());
						
						newProject.setAvgCommitSociability4(newProject.getAvgCommitSociability4()+projectMonth.getAvgCommitSociability());
						newProject.setAvgIssueSociability4(newProject.getAvgIssueSociability4()+projectMonth.getAvgIssueSociability());
						newProject.setAvgPullSociability4(newProject.getAvgPullSociability4()+projectMonth.getAvgPullSociability());
						newProject.setAvgActivitySociability4(newProject.getAvgActivitySociability4()+projectMonth.getAvgActivitySociability());
						
						newProject.setAvgCommitReputation4(newProject.getAvgCommitReputation4()+projectMonth.getAvgCommitReputation());
						newProject.setAvgIssueReputation4(newProject.getAvgIssueReputation4()+projectMonth.getAvgIssueReputation());
						newProject.setAvgPullReputation4(newProject.getAvgPullReputation4()+projectMonth.getAvgPullReputation());
						newProject.setAvgActivityReputation4(newProject.getAvgActivityReputation4()+projectMonth.getAvgActivityReputation());

						newProject.setAvgCommitOutdegree4(newProject.getAvgCommitOutdegree4()+projectMonth.getAvgCommitOutdegree());
						newProject.setAvgIssueOutdegree4(newProject.getAvgIssueOutdegree4()+projectMonth.getAvgIssueOutdegree());
						newProject.setAvgPullOutdegree4(newProject.getAvgPullOutdegree4()+projectMonth.getAvgPullOutdegree());
						newProject.setAvgActivityOutdegree4(newProject.getAvgActivityOutdegree4()+projectMonth.getAvgActivityOutdegree());											
				}
			
			}

			Session session2 = HibernateUtil.getSessionFactory().openSession();
			try{
				session2.beginTransaction();			
				session2.save(newProject);
				session2.getTransaction().commit();
			}
			catch(Exception e)			{

				logger.error("Failed: newProject ERROR! - rerun??" + e.getMessage());
				logger.error("Problem saving newProject: "+ newProject);	
				e.printStackTrace();
			}
			session2.close();
			count++;
		}
		
		
		// SCORING 
		List<ScoringSetLabelled> projectList2 = session.createQuery("from ScoringSetLabelled group by projectId").list();
		
		int count2=1;
		for (ScoringSetLabelled project : projectList2) {

			logger.info("SCORING Project: "+count2 +"/"+ projectList2.size()); 
			
			Integer projectId = project.getProjectId();
			logger.info("Project: "+projectId); 
			
			List<ScoringSetLabelled> projectMonths2 = session.createQuery("from ScoringSetLabelled where projectId = "+projectId).list();			

			ScoringSetQuarterlyMatix newProject2 = new ScoringSetQuarterlyMatix();
			newProject2.setProjectId(projectId);
			newProject2.setOutcome(project.getOutcome());
			
			for (ScoringSetLabelled projectMonth : projectMonths2) {
				
				// 1,2,3
				if(projectMonth.getMonthNumber()<=3){
					newProject2.setTotalCommits1(newProject2.getTotalCommits1()+projectMonth.getTotalCommits());
					newProject2.setTotalIssues1(newProject2.getTotalIssues1()+projectMonth.getTotalIssues());
					newProject2.setTotalPulls1(newProject2.getTotalPulls1()+projectMonth.getTotalPulls());
					newProject2.setTotalActivity1(newProject2.getTotalActivity1()+projectMonth.getTotalActivity());
					
					newProject2.setTotalCommitsUsers1(newProject2.getTotalCommitsUsers1()+projectMonth.getTotalCommitsUsers());
					newProject2.setTotalIssuesUsers1(newProject2.getTotalIssuesUsers1()+projectMonth.getTotalIssuesUsers());
					newProject2.setTotalPullsUsers1(newProject2.getTotalPullsUsers1()+projectMonth.getTotalPullsUsers());
					newProject2.setTotalActivityUsers1(newProject2.getTotalActivityUsers1()+projectMonth.getTotalActivityUsers());
					
					newProject2.setAvgCommitSociability1(newProject2.getAvgCommitSociability1()+projectMonth.getAvgCommitSociability());
					newProject2.setAvgIssueSociability1(newProject2.getAvgIssueSociability1()+projectMonth.getAvgIssueSociability());
					newProject2.setAvgPullSociability1(newProject2.getAvgPullSociability1()+projectMonth.getAvgPullSociability());
					newProject2.setAvgActivitySociability1(newProject2.getAvgActivitySociability1()+projectMonth.getAvgActivitySociability());
					
					newProject2.setAvgCommitReputation1(newProject2.getAvgCommitReputation1()+projectMonth.getAvgCommitReputation());
					newProject2.setAvgIssueReputation1(newProject2.getAvgIssueReputation1()+projectMonth.getAvgIssueReputation());
					newProject2.setAvgPullReputation1(newProject2.getAvgPullReputation1()+projectMonth.getAvgPullReputation());
					newProject2.setAvgActivityReputation1(newProject2.getAvgActivityReputation1()+projectMonth.getAvgActivityReputation());

					newProject2.setAvgCommitOutdegree1(newProject2.getAvgCommitOutdegree1()+projectMonth.getAvgCommitOutdegree());
					newProject2.setAvgIssueOutdegree1(newProject2.getAvgIssueOutdegree1()+projectMonth.getAvgIssueOutdegree());
					newProject2.setAvgPullOutdegree1(newProject2.getAvgPullOutdegree1()+projectMonth.getAvgPullOutdegree());
					newProject2.setAvgActivityOutdegree1(newProject2.getAvgActivityOutdegree1()+projectMonth.getAvgActivityOutdegree());						
				}
				else // 4,5,6
					if(projectMonth.getMonthNumber()>3 && projectMonth.getMonthNumber()<=6){
						newProject2.setTotalCommits2(newProject2.getTotalCommits2()+projectMonth.getTotalCommits());
						newProject2.setTotalIssues2(newProject2.getTotalIssues2()+projectMonth.getTotalIssues());
						newProject2.setTotalPulls2(newProject2.getTotalPulls2()+projectMonth.getTotalPulls());
						newProject2.setTotalActivity2(newProject2.getTotalActivity2()+projectMonth.getTotalActivity());
						
						newProject2.setTotalCommitsUsers2(newProject2.getTotalCommitsUsers2()+projectMonth.getTotalCommitsUsers());
						newProject2.setTotalIssuesUsers2(newProject2.getTotalIssuesUsers2()+projectMonth.getTotalIssuesUsers());
						newProject2.setTotalPullsUsers2(newProject2.getTotalPullsUsers2()+projectMonth.getTotalPullsUsers());
						newProject2.setTotalActivityUsers2(newProject2.getTotalActivityUsers2()+projectMonth.getTotalActivityUsers());
						
						newProject2.setAvgCommitSociability2(newProject2.getAvgCommitSociability2()+projectMonth.getAvgCommitSociability());
						newProject2.setAvgIssueSociability2(newProject2.getAvgIssueSociability2()+projectMonth.getAvgIssueSociability());
						newProject2.setAvgPullSociability2(newProject2.getAvgPullSociability2()+projectMonth.getAvgPullSociability());
						newProject2.setAvgActivitySociability2(newProject2.getAvgActivitySociability2()+projectMonth.getAvgActivitySociability());
						
						newProject2.setAvgCommitReputation2(newProject2.getAvgCommitReputation2()+projectMonth.getAvgCommitReputation());
						newProject2.setAvgIssueReputation2(newProject2.getAvgIssueReputation2()+projectMonth.getAvgIssueReputation());
						newProject2.setAvgPullReputation2(newProject2.getAvgPullReputation2()+projectMonth.getAvgPullReputation());
						newProject2.setAvgActivityReputation2(newProject2.getAvgActivityReputation2()+projectMonth.getAvgActivityReputation());

						newProject2.setAvgCommitOutdegree2(newProject2.getAvgCommitOutdegree2()+projectMonth.getAvgCommitOutdegree());
						newProject2.setAvgIssueOutdegree2(newProject2.getAvgIssueOutdegree2()+projectMonth.getAvgIssueOutdegree());
						newProject2.setAvgPullOutdegree2(newProject2.getAvgPullOutdegree2()+projectMonth.getAvgPullOutdegree());
						newProject2.setAvgActivityOutdegree2(newProject2.getAvgActivityOutdegree2()+projectMonth.getAvgActivityOutdegree());	
				}
				else // 7,8,9
					if(projectMonth.getMonthNumber()>6 && projectMonth.getMonthNumber()<=9){
						newProject2.setTotalCommits3(newProject2.getTotalCommits3()+projectMonth.getTotalCommits());
						newProject2.setTotalIssues3(newProject2.getTotalIssues3()+projectMonth.getTotalIssues());
						newProject2.setTotalPulls3(newProject2.getTotalPulls3()+projectMonth.getTotalPulls());
						newProject2.setTotalActivity3(newProject2.getTotalActivity3()+projectMonth.getTotalActivity());
						
						newProject2.setTotalCommitsUsers3(newProject2.getTotalCommitsUsers3()+projectMonth.getTotalCommitsUsers());
						newProject2.setTotalIssuesUsers3(newProject2.getTotalIssuesUsers3()+projectMonth.getTotalIssuesUsers());
						newProject2.setTotalPullsUsers3(newProject2.getTotalPullsUsers3()+projectMonth.getTotalPullsUsers());
						newProject2.setTotalActivityUsers3(newProject2.getTotalActivityUsers3()+projectMonth.getTotalActivityUsers());
						
						newProject2.setAvgCommitSociability3(newProject2.getAvgCommitSociability3()+projectMonth.getAvgCommitSociability());
						newProject2.setAvgIssueSociability3(newProject2.getAvgIssueSociability3()+projectMonth.getAvgIssueSociability());
						newProject2.setAvgPullSociability3(newProject2.getAvgPullSociability3()+projectMonth.getAvgPullSociability());
						newProject2.setAvgActivitySociability3(newProject2.getAvgActivitySociability3()+projectMonth.getAvgActivitySociability());
						
						newProject2.setAvgCommitReputation3(newProject2.getAvgCommitReputation3()+projectMonth.getAvgCommitReputation());
						newProject2.setAvgIssueReputation3(newProject2.getAvgIssueReputation3()+projectMonth.getAvgIssueReputation());
						newProject2.setAvgPullReputation3(newProject2.getAvgPullReputation3()+projectMonth.getAvgPullReputation());
						newProject2.setAvgActivityReputation3(newProject2.getAvgActivityReputation3()+projectMonth.getAvgActivityReputation());

						newProject2.setAvgCommitOutdegree3(newProject2.getAvgCommitOutdegree3()+projectMonth.getAvgCommitOutdegree());
						newProject2.setAvgIssueOutdegree3(newProject2.getAvgIssueOutdegree3()+projectMonth.getAvgIssueOutdegree());
						newProject2.setAvgPullOutdegree3(newProject2.getAvgPullOutdegree3()+projectMonth.getAvgPullOutdegree());
						newProject2.setAvgActivityOutdegree3(newProject2.getAvgActivityOutdegree3()+projectMonth.getAvgActivityOutdegree());	
				}
				else // 10,11,12
					if(projectMonth.getMonthNumber()>9 && projectMonth.getMonthNumber()<=12){
						
						newProject2.setTotalCommits4(newProject2.getTotalCommits4()+projectMonth.getTotalCommits());
						newProject2.setTotalIssues4(newProject2.getTotalIssues4()+projectMonth.getTotalIssues());
						newProject2.setTotalPulls4(newProject2.getTotalPulls4()+projectMonth.getTotalPulls());
						newProject2.setTotalActivity4(newProject2.getTotalActivity4()+projectMonth.getTotalActivity());
						
						newProject2.setTotalCommitsUsers4(newProject2.getTotalCommitsUsers4()+projectMonth.getTotalCommitsUsers());
						newProject2.setTotalIssuesUsers4(newProject2.getTotalIssuesUsers4()+projectMonth.getTotalIssuesUsers());
						newProject2.setTotalPullsUsers4(newProject2.getTotalPullsUsers4()+projectMonth.getTotalPullsUsers());
						newProject2.setTotalActivityUsers4(newProject2.getTotalActivityUsers4()+projectMonth.getTotalActivityUsers());
						
						newProject2.setAvgCommitSociability4(newProject2.getAvgCommitSociability4()+projectMonth.getAvgCommitSociability());
						newProject2.setAvgIssueSociability4(newProject2.getAvgIssueSociability4()+projectMonth.getAvgIssueSociability());
						newProject2.setAvgPullSociability4(newProject2.getAvgPullSociability4()+projectMonth.getAvgPullSociability());
						newProject2.setAvgActivitySociability4(newProject2.getAvgActivitySociability4()+projectMonth.getAvgActivitySociability());
						
						newProject2.setAvgCommitReputation4(newProject2.getAvgCommitReputation4()+projectMonth.getAvgCommitReputation());
						newProject2.setAvgIssueReputation4(newProject2.getAvgIssueReputation4()+projectMonth.getAvgIssueReputation());
						newProject2.setAvgPullReputation4(newProject2.getAvgPullReputation4()+projectMonth.getAvgPullReputation());
						newProject2.setAvgActivityReputation4(newProject2.getAvgActivityReputation4()+projectMonth.getAvgActivityReputation());

						newProject2.setAvgCommitOutdegree4(newProject2.getAvgCommitOutdegree4()+projectMonth.getAvgCommitOutdegree());
						newProject2.setAvgIssueOutdegree4(newProject2.getAvgIssueOutdegree4()+projectMonth.getAvgIssueOutdegree());
						newProject2.setAvgPullOutdegree4(newProject2.getAvgPullOutdegree4()+projectMonth.getAvgPullOutdegree());
						newProject2.setAvgActivityOutdegree4(newProject2.getAvgActivityOutdegree4()+projectMonth.getAvgActivityOutdegree());											
				}
			
			}

			Session session3 = HibernateUtil.getSessionFactory().openSession();
			try{
				session3.beginTransaction();			
				session3.save(newProject2);
				session3.getTransaction().commit();
			}
			catch(Exception e)			{

				logger.error("Failed: newProject2 ERROR! - rerun??" + e.getMessage());
				logger.error("Problem saving newProject2: "+ newProject2);	
				e.printStackTrace();
			}
			session3.close();
			count2++;
		}
	}
}
