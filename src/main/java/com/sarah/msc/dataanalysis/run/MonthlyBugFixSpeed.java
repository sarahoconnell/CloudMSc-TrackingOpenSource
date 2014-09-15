package com.sarah.msc.dataanalysis.run;
/**
 * As GitHub does not explicitly record the bug fixing speed as an attribute, 
 * this class iterates through each issue in the dataset and calculates the lifespan of that issue in days. 
 * 
 * @author Sarah O'Connell
 */
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.sarah.msc.dataanalysis.model.Issues;
import com.sarah.msc.dataanalysis.model.ScoringSetQuarterlyMatix;
import com.sarah.msc.dataanalysis.model.TrainingSetQuarterlyMatix;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;

public class MonthlyBugFixSpeed {

	private static Logger logger = Logger.getLogger(GitHubPopularRepoGetter.class);
	
	/** 
	 * Calculate the monthly bug fix speed for this repository 
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args){

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		// TRAINING 
		List<TrainingSetQuarterlyMatix> trainingProjects = session.createQuery("from TrainingSetQuarterlyMatix").list();
		
		int count=1;
		for (TrainingSetQuarterlyMatix trainingProject : trainingProjects) {

			logger.info("TRAINING Project: "+count +"/"+ trainingProjects.size()); 
			
			Integer projectId = trainingProject.getProjectId();
			logger.info("Project: "+projectId); 

			List<Issues> issues = session.createQuery("from Issues where projectId = "+projectId +"and state='closed'  and closedMonthNumber >0").list();			

			int bugFixSpeed1 = 0;
			int bugFixSpeed2 = 0;
			int bugFixSpeed3 = 0;
			int bugFixSpeed4 = 0;

			int numberBugs1 = 0;
			int numberBugs2 = 0;
			int numberBugs3 = 0;
			int numberBugs4 = 0;
			
			for (Issues issue : issues) {
				
				// 1,2,3
				if(issue.getClosedMonthNumber()<=3){
					bugFixSpeed1 += issue.getLifespan();	
					numberBugs1++;
				}
				else // 4,5,6
					if(issue.getClosedMonthNumber()>3 && issue.getClosedMonthNumber()<=6){
						bugFixSpeed2 += issue.getLifespan();	
						numberBugs2++;
				}
				else // 7,8,9
					if(issue.getClosedMonthNumber()>6 && issue.getClosedMonthNumber()<=9){
						bugFixSpeed3 += issue.getLifespan();	
						numberBugs3++;
				}
				else // 10,11,12
					if(issue.getClosedMonthNumber()>9 && issue.getClosedMonthNumber()<=12){
						bugFixSpeed4 += issue.getLifespan();	
						numberBugs4++;		
				}
			
			}
			
			if(numberBugs1>0)
				bugFixSpeed1 = bugFixSpeed1/numberBugs1;
			if(numberBugs2>0)
				bugFixSpeed2 = bugFixSpeed2/numberBugs2;
			if(numberBugs3>0)
				bugFixSpeed3 = bugFixSpeed3/numberBugs3;
			if(numberBugs4>0)
				bugFixSpeed4 = bugFixSpeed4/numberBugs4;

			trainingProject.setBugFixSpeed1(bugFixSpeed1);
			trainingProject.setBugFixSpeed2(bugFixSpeed2);
			trainingProject.setBugFixSpeed3(bugFixSpeed3);
			trainingProject.setBugFixSpeed4(bugFixSpeed4);
			
			Session session2 = HibernateUtil.getSessionFactory().openSession();
			try{
				session2.beginTransaction();			
				session2.update(trainingProject);
				session2.getTransaction().commit();
			}
			catch(Exception e)			{

				logger.error("Failed: trainingProject ERROR! - rerun??" + e.getMessage());
				logger.error("Problem saving trainingProject: "+ trainingProject);	
				e.printStackTrace();
			}
			session2.close();
			count++;
		}
		
		
		// SCORING 
		List<ScoringSetQuarterlyMatix> scoringProjects = session.createQuery("from ScoringSetQuarterlyMatix").list();
		
		int count2=1;
		for (ScoringSetQuarterlyMatix scoringProject : scoringProjects) {

			logger.info("SCORING Project: "+count2 +"/"+ scoringProjects.size()); 
			
			Integer projectId = scoringProject.getProjectId();
			logger.info("Project: "+projectId); 

			List<Issues> issues = session.createQuery("from Issues where projectId = "+projectId +"and state='closed' and closedMonthNumber >0").list();			

			int bugFixSpeed1 = 0;
			int bugFixSpeed2 = 0;
			int bugFixSpeed3 = 0;
			int bugFixSpeed4 = 0;

			int numberBugs1 = 0;
			int numberBugs2 = 0;
			int numberBugs3 = 0;
			int numberBugs4 = 0;
			
			for (Issues issue : issues) {
				
				// 1,2,3
				if(issue.getClosedMonthNumber()<=3){
					bugFixSpeed1 += issue.getLifespan();	
					numberBugs1++;
				}
				else // 4,5,6
					if(issue.getClosedMonthNumber()>3 && issue.getClosedMonthNumber()<=6){
						bugFixSpeed2 += issue.getLifespan();	
						numberBugs2++;
				}
				else // 7,8,9
					if(issue.getClosedMonthNumber()>6 && issue.getClosedMonthNumber()<=9){
						bugFixSpeed3 += issue.getLifespan();	
						numberBugs3++;
				}
				else // 10,11,12
					if(issue.getClosedMonthNumber()>9 && issue.getClosedMonthNumber()<=12){
						bugFixSpeed4 += issue.getLifespan();	
						numberBugs4++;		
				}
			
			}
			
			if(numberBugs1>0)
				bugFixSpeed1 = bugFixSpeed1/numberBugs1;
			if(numberBugs2>0)
				bugFixSpeed2 = bugFixSpeed2/numberBugs2;
			if(numberBugs3>0)
				bugFixSpeed3 = bugFixSpeed3/numberBugs3;
			if(numberBugs4>0)
				bugFixSpeed4 = bugFixSpeed4/numberBugs4;


			scoringProject.setBugFixSpeed1(bugFixSpeed1);
			scoringProject.setBugFixSpeed2(bugFixSpeed2);
			scoringProject.setBugFixSpeed3(bugFixSpeed3);
			scoringProject.setBugFixSpeed4(bugFixSpeed4);
			
			Session session2 = HibernateUtil.getSessionFactory().openSession();
			try{
				session2.beginTransaction();			
				session2.update(scoringProject);
				session2.getTransaction().commit();
			}
			catch(Exception e)			{

				logger.error("Failed: scoringProject ERROR! - rerun??" + e.getMessage());
				logger.error("Problem saving scoringProject: "+ scoringProject);	
				e.printStackTrace();
			}
			session2.close();
			count2++;
		}
	}
}
