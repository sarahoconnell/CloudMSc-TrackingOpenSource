package com.sarah.msc.dataanalysis.run;
/**
 * Assembles the monthly activity for a given repository 
 * 
 * Monthly activity is comprised of: 
 * - Commits 
 * - Issues 
 * - Pull Requests 
 */
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.sarah.msc.dataanalysis.model.Commits;
import com.sarah.msc.dataanalysis.model.CrowstonViewWeeklyTodo;
import com.sarah.msc.dataanalysis.model.Issues;
import com.sarah.msc.dataanalysis.model.User;
import com.sarah.msc.dataanalysis.model.MonthlyActivity;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;
import com.sarah.msc.dataanalysis.utils.Utils;
 
public class GetMonthlyActivity {  
	
	private static Utils utils;
	private static Logger logger = Logger.getLogger(GetMonthlyActivity.class);

	HashMap<Integer, User> usersMap = new HashMap<Integer, User>();
	
	public GetMonthlyActivity(){
	}   
	/**
	 * Assembles the monthly activity information for a given repository before saving 
	 * the repository to the MySQL database. 
	 * 
	 * @param repo
	 * @param usersMap
	 * @return
	 */
	public HashMap<Integer, User> getRepo(CrowstonViewWeeklyTodo repo, HashMap<Integer, User>usersMap)
	{
		    this.usersMap = usersMap;
		    
			logger.info("Start GetMONTHLYActivity");
												
			Integer projectId = repo.getId();

			Session session = HibernateUtil.getSessionFactory().openSession();
			
			// Get the activity for this project and month
			for(int i=1; i<=Utils.MONTHS_12; i++){

				Integer monthNumber = i;

				logger.info(" monthNumber: " + monthNumber);
				
				MonthlyActivity activity = new MonthlyActivity();
				activity.setProjectId(projectId);
				activity.setMonthNumber(monthNumber);

				activity = getCommitActivity(monthNumber, projectId, activity);
				activity = getIssueActivity(monthNumber, projectId, activity);
				activity = getPullActivity(monthNumber, projectId, activity);

			    int averageActivitySociability = activity.getAvgCommitSociability()+ activity.getAvgIssueSociability()+ activity.getAvgPullSociability();
				int averageActivityReputation = activity.getAvgCommitReputation()+ activity.getAvgIssueReputation()+ activity.getAvgPullReputation();
				int projectActivityOutdegree = activity.getAvgCommitOutdegree()+ activity.getAvgIssueOutdegree()+ activity.getAvgPullOutdegree();
				int totalActivity = activity.getTotalCommits()+ activity.getTotalIssues()+ activity.getTotalPulls();
				int totalActivityUsers = activity.getTotalCommitsUsers()+ activity.getTotalIssuesUsers()+ activity.getTotalPullsUsers();
				
				activity.setAvgActivityOutdegree(projectActivityOutdegree);
				activity.setAvgActivityReputation(averageActivityReputation);
				activity.setAvgActivitySociability(averageActivitySociability);
				activity.setTotalActivity(totalActivity);
				activity.setTotalActivityUsers(totalActivityUsers);
				
				logger.info(" Save: " + projectId);
				try{
					session.beginTransaction();			
					session.save(activity);
					session.getTransaction().commit();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}	

			session.close();
			return usersMap;
	}

   /** 
    * Assembles the commit activity for a given month.
    * 
    * @param monthNumber
    * @param projectId
    * @param activity
    * @return
    */
   public MonthlyActivity getCommitActivity(Integer monthNumber, Integer projectId, MonthlyActivity activity)
   {
		logger.info(" getCommitActivity: ");
		Session session = HibernateUtil.getSessionFactory().openSession();
		
	    int averageSociability = 0;
		int averageReputation = 0;
		int projectOutdegree = 0;
		
		// COMMITS
		@SuppressWarnings("unchecked")
		String commitQuery = "from Commits where projectId="+ projectId + " and monthNumber="+monthNumber;
		List<Commits> commits = session.createQuery(commitQuery).list();
		
		int totalNumberCommits = commits.size();
		
		@SuppressWarnings("unchecked")
		String uniqueCommitQuery = "from Commits where projectId="+ projectId + " and monthNumber="+monthNumber + " group by userId";
		List<Commits> uniqueCommitList = session.createQuery(uniqueCommitQuery).list();
		
		int totalNumberCommitUsers = uniqueCommitList.size();
				
		// Get the Avg sociability/reputation/outdegree of each unique developer
		for (Commits commit : uniqueCommitList) {
			
			User user;
			
			Integer userId = commit.getUserId();
			
			// check for -1 = INACTIVE user
			if(userId != -1){
				if(usersMap.containsKey(userId))
					user = usersMap.get(userId);
				else{
					user = (User) session.createQuery("from User where id="+ userId).uniqueResult();
					usersMap.put(userId, user);
				}
				
				if(user != null){
					// sociability
					int numberFollowing = user.getFollowing();
					
					// reputation
					int numberFollowers = user.getFollowers();
					
					// Project outdegree is based on average number of public_repos for each team member
					int numberRepos = user.getPublicRepos();
					
					averageSociability += numberFollowing;
					averageReputation += numberFollowers;
					projectOutdegree += numberRepos;
				}
			}
		}

		if(totalNumberCommitUsers!=0){
			projectOutdegree = Math.round(projectOutdegree/totalNumberCommitUsers);
			averageSociability = Math.round(averageSociability/totalNumberCommitUsers);
			averageReputation = Math.round(averageReputation/totalNumberCommitUsers);
		}
		
		activity.setAvgCommitOutdegree(projectOutdegree);
		activity.setAvgCommitReputation(averageReputation);
		activity.setAvgCommitSociability(averageSociability);
		activity.setTotalCommits(totalNumberCommits);
		activity.setTotalCommitsUsers(totalNumberCommitUsers);

		session.close();
		return activity;
				
   }	
   /**
    * Assembles the issue activity for a given month.
    *     
    * @param monthNumber
    * @param projectId
    * @param activity
    * @return
    */
   public MonthlyActivity getIssueActivity(Integer monthNumber, Integer projectId, MonthlyActivity activity)
   {
		logger.info(" getIssueActivity: ");
		Session session = HibernateUtil.getSessionFactory().openSession();
		
	    int averageSociability = 0;
		int averageReputation = 0;
		int projectOutdegree = 0;
		
		// COMMITS
		@SuppressWarnings("unchecked")
		String issueQuery = "from Issues where projectId="+ projectId + " and pullRequest = 0 and (openedMonthNumber="+monthNumber+ " or closedMonthNumber="+monthNumber+")";
		List<Issues> issues = session.createQuery(issueQuery).list();
		
		int totalNumberIssues = issues.size();
		
		@SuppressWarnings("unchecked")
		String uniqueIssueQuery = "from Issues where projectId="+ projectId + " and pullRequest = 0 and (openedMonthNumber="+monthNumber+ " or closedMonthNumber="+monthNumber+")  group by userId";
		List<Issues> uniqueIssueList = session.createQuery(uniqueIssueQuery).list();
		
		int totalNumberIssueUsers = uniqueIssueList.size();
				
		// Get the Avg sociability/reputation/outdegree of each unique developer
		for (Issues issue : uniqueIssueList) {
			
			User user;
			
			Integer userId = issue.getUserId();
			
			// check for -1 = INACTIVE user
			if(userId != -1){
				if(usersMap.containsKey(userId))
					user = usersMap.get(userId);
				else{
					user = (User) session.createQuery("from User where id="+ userId).uniqueResult();
					usersMap.put(userId, user);
				}
				
				if(user != null){
					// sociability
					int numberFollowing = user.getFollowing();
					
					// reputation
					int numberFollowers = user.getFollowers();
					
					// Project outdegree is based on average number of public_repos for each team member
					int numberRepos = user.getPublicRepos();
					
					averageSociability += numberFollowing;
					averageReputation += numberFollowers;
					projectOutdegree += numberRepos;
				}
			}
		}

		if(totalNumberIssueUsers!=0){
			projectOutdegree = Math.round(projectOutdegree/totalNumberIssueUsers);
			averageSociability = Math.round(averageSociability/totalNumberIssueUsers);
			averageReputation = Math.round(averageReputation/totalNumberIssueUsers);
		}
		
		activity.setAvgIssueOutdegree(projectOutdegree);
		activity.setAvgIssueReputation(averageReputation);
		activity.setAvgIssueSociability(averageSociability);
		activity.setTotalIssues(totalNumberIssues);
		activity.setTotalIssuesUsers(totalNumberIssueUsers);

		session.close();
		return activity;
				
   }	
   
   /**
    * Assembles the pull request activity for a given month.
    * 
    * @param monthNumber
    * @param projectId
    * @param activity
    * @return
    */
   public MonthlyActivity getPullActivity(Integer monthNumber, Integer projectId, MonthlyActivity activity)
   {
		logger.info(" getPullActivity: ");
		Session session = HibernateUtil.getSessionFactory().openSession();
		
	    int averageSociability = 0;
		int averageReputation = 0;
		int projectOutdegree = 0;
		
		// COMMITS
		@SuppressWarnings("unchecked")
		String pullQuery = "from Issues where projectId="+ projectId + " and pullRequest = 1 and (openedMonthNumber="+monthNumber+ " or closedMonthNumber="+monthNumber+")";
		List<Issues> pulls = session.createQuery(pullQuery).list(); 

		logger.info(" pullQuery: "+ pullQuery);
		int totalNumberPulls = pulls.size();
		
		@SuppressWarnings("unchecked")
		String uniquePullQuery = "from Issues where projectId="+ projectId + " and pullRequest = 1 and (openedMonthNumber="+monthNumber+ " or closedMonthNumber="+monthNumber+")  group by userId";

		logger.info(" uniquePullQuery: "+ uniquePullQuery);
		List<Issues> uniquePullList = session.createQuery(uniquePullQuery).list();
		
		int totalNumberPullUsers = uniquePullList.size();
				
		// Get the Avg sociability/reputation/outdegree of each unique developer
		for (Issues pull : uniquePullList) {
			
			User user;
			
			Integer userId = pull.getUserId();
			
			// check for -1 = INACTIVE user
			if(userId != -1){
				if(usersMap.containsKey(userId))
					user = usersMap.get(userId);
				else{
					user = (User) session.createQuery("from User where id="+ userId).uniqueResult();
					usersMap.put(userId, user);
				}
				
				if(user != null){
					// sociability
					int numberFollowing = user.getFollowing();
					
					// reputation
					int numberFollowers = user.getFollowers();
					
					// Project outdegree is based on average number of public_repos for each team member
					int numberRepos = user.getPublicRepos();
					
					averageSociability += numberFollowing;
					averageReputation += numberFollowers;
					projectOutdegree += numberRepos;
				}
			}
		}

		if(totalNumberPullUsers!=0){
			projectOutdegree = Math.round(projectOutdegree/totalNumberPullUsers);
			averageSociability = Math.round(averageSociability/totalNumberPullUsers);
			averageReputation = Math.round(averageReputation/totalNumberPullUsers);
		}
		
		activity.setAvgPullOutdegree(projectOutdegree);
		activity.setAvgPullReputation(averageReputation);
		activity.setAvgPullSociability(averageSociability);
		activity.setTotalPulls(totalNumberPulls);
		activity.setTotalPullsUsers(totalNumberPullUsers);

		session.close();
		return activity;
				
   }	
}
