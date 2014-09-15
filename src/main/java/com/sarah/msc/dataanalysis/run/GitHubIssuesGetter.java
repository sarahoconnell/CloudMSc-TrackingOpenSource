package com.sarah.msc.dataanalysis.run;
/**
 * Retrieve information about issues and pull requests from the GitHub API 
 * 
 * @author Sarah O'Connell
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sarah.msc.dataanalysis.model.CrowstonViewIssuesTodo;
import com.sarah.msc.dataanalysis.model.Issues;
import com.sarah.msc.dataanalysis.model.MonthNumber;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;
import com.sarah.msc.dataanalysis.utils.Utils;
 
 /*
 *  NOTE: next time fix the DONE flag to actually stop running extra queries when it has bypassed the 12 months mark
 *
 */
public class GitHubIssuesGetter {  
	
	private Utils utils;
	private static Logger logger = Logger.getLogger(GitHubIssuesGetter.class);
	private String credentials = "";
	
	private static String GITHUB_REPOS_API = "https://api.github.com/repos";
	private static final String PER_PAGE_100 = "&per_page=100";

	boolean done = false;
	
	public GitHubIssuesGetter(String credentials){
		this.credentials = credentials;
	}
	
	/** 
	 * Get the list of issues from the GitHub API for the given repository. 
	 * 
	 * @param repo
	 * @param utils
	 */
	public  void getIssues(CrowstonViewIssuesTodo repo, Utils utils)
	{		    
			logger.info("Start getIssuesData");
			logger.info(repo.getFullName() + " " + repo.getCreatedAt());
			
		    this.utils = utils;
		    String since = "";
			String until = "";
			try
			{				
				ArrayList<MonthNumber> weekNumbers = new ArrayList<MonthNumber>();
				String startDB = repo.getCreatedAt(); //'2011-07-29T21:19:00Z'
			    Date startDate = utils.getDateFromDBString(startDB); // 2011-07-29
				
				// Utils.MONTHS_12 = every weeks for 12 months
			    int monthNumber = 1;
				for(int i=0; i<13; i++){  // go extra month for closed issues

					Date endDate = utils.addFourWeeksToDate(startDate); // 2011-08-05  
					weekNumbers.add(new MonthNumber(monthNumber, startDate, endDate));					
					startDate = utils.addOneDayToDate(endDate); 
					monthNumber++;			
				}
				
								
				getIssuesData(repo, weekNumbers);				
			}
			catch(Exception e){
				logger.error("Failed - ISSUE ERROR! - rerun?? "+e.getMessage());
				logger.error(GITHUB_REPOS_API+"/"+repo.getFullName()+"/issues?since="+since+"&until="+until);	
				
			}					
			
			logger.info("End getIssuesData");		
	}

	
	/** 
	 * Get the list of issues from the GitHub API. 
	 * @param repo
	 * @param monthNumbers
	 * @throws Exception
	 */
	private void getIssuesData(CrowstonViewIssuesTodo repo, ArrayList<MonthNumber> monthNumbers) throws Exception{
		
			Session session = HibernateUtil.getSessionFactory().openSession();
			runQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/issues?state=closed&sort=created&direction=asc", session, repo.getId(), monthNumbers);
			done = false;
			Thread.sleep(500);// give the API a break!		
			runQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/issues?state=open&sort=created&direction=asc", session, repo.getId(), monthNumbers);
			done = false;
			Thread.sleep(500);// give the API a break!			
			session.close();
	}
	/**
	 * Runs the query against the GitHub API and persist the results to the MySQL database.
	 * @param queryURL
	 * @param session
	 * @param projectId
	 * @param monthNumbers
	 * @throws Exception
	 */
	private void runQuery(String queryURL, Session session, Integer projectId, ArrayList<MonthNumber> monthNumbers) throws Exception {

		logger.info("runQuery: "+ queryURL);
		done = false; // move this out further...
		if(!done){
			HttpURLConnection conn = getConnection(queryURL + PER_PAGE_100);
						
			if (conn.getResponseCode() == 410){
				
				logger.error("ERROR - Failed : HTTP error code : "
							+ conn.getResponseCode());
				return;
			}
			
			if (conn.getResponseCode() != 200) {			
				logger.error("ERROR - Failed : HTTP error code : "
						+ conn.getResponseCode());
				
				return;
			}

			// GET JSON and create RepoIssues[]
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			final StringBuffer buffer = new StringBuffer();
		
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
				
			// Extract JSON from the JSONP envelope
			String json = buffer.toString();
				
			Issues[] issues = getIssuesFromJSON(json, projectId, monthNumbers);
			for (Issues issue : issues) {
				try{

					session.beginTransaction();			
					session.save(issue);
					session.getTransaction().commit();
				}
				catch (Exception e) {
					
					logger.error("Failed - ERROR =PROBLEM WITH ISSUE FROM: "+projectId + " queryURL: "+ queryURL);
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
			
			// Check if it's paged
			String link = conn.getHeaderField("Link");
			if(link!=null && link.contains("rel=\"next\"")){
				// GET rel="next" link
				link = link.replaceFirst("<", "");
				link = link.substring(0, link.indexOf(">"));
				runQuery(link, session, projectId, monthNumbers);
			}

			conn.disconnect();
		}
	}

	/** 
	 * Parse the JSON response from the GitHub API and populate a list of Issues objects. 
	 * 
	 * @param json
	 * @param projectId
	 * @param monthNumbers
	 * @return
	 */
	private Issues[] getIssuesFromJSON(String json, Integer projectId, ArrayList<MonthNumber> monthNumbers) {
		ArrayList<Issues> issuesList = new ArrayList<Issues>();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(json);
			 			
			JSONArray iArray = (JSONArray) obj;	
			for (Object iObj : iArray) {

				if(!done){
					
						try {
							JSONObject issue = (JSONObject) iObj;
							JSONObject user = (JSONObject)issue.get("user");
							
							Integer id = ((Long)issue.get("id")).intValue();
							Integer number = ((Long)issue.get("number")).intValue();
							String title = (String) issue.get("title");
							String userLogin = (String) user.get("login");
							Integer userId = ((Long)user.get("id")).intValue();
							String state = (String) issue.get("state");
							String createdAt = (String) issue.get("created_at");
							String closedAt = (String) issue.get("closed_at");
							JSONObject pullRequestOBJ = (JSONObject) issue.get("pull_request");
							String pullRequestHTML = (String) pullRequestOBJ.get("html_url");
							
							Issues issuesDB = new Issues();
							issuesDB.setId(id);
							issuesDB.setProjectId(projectId);
							issuesDB.setNumber(number);
							issuesDB.setTitle(title);
							issuesDB.setUserId(userId);
							issuesDB.setUserLogin(userLogin);
							issuesDB.setState(state);
							issuesDB.setCreatedAt(createdAt);
							issuesDB.setClosedAt(closedAt);
							issuesDB.setPullRequest(pullRequestHTML==null?0:1);
		
							int openedMonthNumber = -1;
							Date issueCreatedAtDate = utils.getDateFromDBString(createdAt);
							for (MonthNumber monthNumber : monthNumbers) {
								if(issueCreatedAtDate.after(monthNumber.getStartDate()) 
										&& issueCreatedAtDate.before(monthNumber.getEndDate())){	

									openedMonthNumber = monthNumber.getMonthNumber();
									issuesDB.setOpenedMonthNumber(openedMonthNumber);
									break;
								}								
							}
		
							int closedMonthNumber = -1;
							if(closedAt!=null){
		
								Date closedAtDate = utils.getDateFromDBString(closedAt);
								for (MonthNumber monthNumber : monthNumbers) {
									if(closedAtDate.after(monthNumber.getStartDate())
											&& closedAtDate.before(monthNumber.getEndDate())){
										closedMonthNumber = monthNumber.getMonthNumber();	
										issuesDB.setClosedMonthNumber(closedMonthNumber);
										break;						
									}
								}
								
								// get lifespan
		
								long diff = closedAtDate.getTime() - issueCreatedAtDate.getTime();
								long diffDays = diff / (24 * 60 * 60 * 1000);
								issuesDB.setLifespan((new Long(diffDays)).intValue());
																
							}
							else{ // use today for lifespan
		
								long diff = (new Date()).getTime() - issueCreatedAtDate.getTime();
								long diffDays = diff / (24 * 60 * 60 * 1000);
								issuesDB.setLifespan((new Long(diffDays)).intValue());
							}		

							logger.info("openedWeekNumber: "+openedMonthNumber);
							
							if(openedMonthNumber!=-1 && openedMonthNumber<13)	{	
								issuesList.add(issuesDB);							
							}
							else{

								logger.info("DONE!!!: last createdAt: "+createdAt + " / id: "+ id);
								done = true;							
							}
						}
						catch (Exception e) {
							// IGNORE - move on
							e.printStackTrace();
							logger.error(e.getMessage());
						}
					}
				}
			} catch (Exception e) {
				// IGNORE - move on
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		
		return issuesList.toArray(new Issues[issuesList.size()]);
	}
	
	/** 
	 * Build a connection to the GitHub API with the required credentials 
	 * 
	 * @param queryURL
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private HttpURLConnection getConnection(String queryURL)
			throws MalformedURLException, IOException, ProtocolException {
		
		URL url = new URL(queryURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.setRequestProperty("Authorization", "Basic " + this.credentials);
		conn.setRequestProperty("Accept", "application/vnd.github.preview");
		return conn;
	}


}
