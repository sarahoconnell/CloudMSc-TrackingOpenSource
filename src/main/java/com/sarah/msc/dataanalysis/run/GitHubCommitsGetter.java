package com.sarah.msc.dataanalysis.run;

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
import org.json.simple.parser.ParseException;

import com.sarah.msc.dataanalysis.model.Commits;
import com.sarah.msc.dataanalysis.model.CrowstonViewCommitsTodo;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;
import com.sarah.msc.dataanalysis.utils.Utils;

public class GitHubCommitsGetter {
	
	private static Logger logger = Logger.getLogger(GitHubCommitsGetter.class);
	private  String credentials = "";
	
	private static String GITHUB_REPOS_API = "https://api.github.com/repos";
	private static final String PER_PAGE_100 = "&per_page=100";

	public GitHubCommitsGetter(String credentials){
		this.credentials = credentials;
	}
	/** 
	 * Retrieval all commits for a given repository across a number of months.
	 * @param repo
	 * @param utils
	 */
	public void getCommits(CrowstonViewCommitsTodo repo, Utils utils)
	{
		logger.info("Start getCommitData");
		logger.info(repo.getFullName());
		String since = "";
		String until = "";
		try
		{
			String startAtDB = repo.getCreatedAt(); //'2011-07-29T21:19:00Z'
		    Date startAtDate = utils.getDateFromDBString(startAtDB); // 2011-07-29
			// Utils.MONTHS_12 = every 4 weeks for 12 months
		    int monthNumber = 1;
			for(int i=0; i<Utils.MONTHS_12; i++){
				since = utils.getStringFromDate(startAtDate); // 2011-07-29	
				
				Date untilDate = utils.addFourWeeksToDate(startAtDate);  
				until = utils.getStringFromDate(untilDate); // 2011-08-05

				getCommitDataForMonth(repo, since, until, monthNumber);
				startAtDate = utils.addOneDayToDate(untilDate); 
				monthNumber++;				    				
			}
		}
		catch(Exception e){
			logger.error("Failed: COMMIT ERROR! - rerun??" + e.getMessage());
			logger.error(GITHUB_REPOS_API+"/"+repo.getFullName()+"/commits?since="+since+"&until="+until);	
			
		}		
		logger.info("End getCommitData");
	}	
	
	/** 
	 * Get the commit data for a given week
	 * @param repo
	 * @param since
	 * @param until
	 * @param monthNumber
	 * @throws Exception
	 */
	private void getCommitDataForMonth(CrowstonViewCommitsTodo repo, String since, String until, int monthNumber) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		runCommitsQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/commits?since="+since+"&until="+until, repo.getId(), session, since, until, monthNumber);
		session.close();
		Thread.sleep(500);// give the API a break!
	}
	
	/**
	 * Run the commit query against the GitHub API, persisting the parsed results to the MySQL database
	 * @param queryURL
	 * @param projectId
	 * @param session
	 * @param since
	 * @param until
	 * @param monthNumber
	 * @throws Exception
	 */
	private void runCommitsQuery(String queryURL, Integer projectId, Session session, String since, String until, int monthNumber) throws Exception  {
		
			logger.info(queryURL);
			HttpURLConnection conn = getConnection(queryURL + PER_PAGE_100);
						
			if (conn.getResponseCode() != 200) {
				logger.error("ERROR - Failed : HTTP error code : "
						+ conn.getResponseCode());

				return;
			}

			// GET JSON and create TimelineCommits[]
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			final StringBuffer buffer = new StringBuffer();
		
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
			
			// Extract JSON from the JSONP envelope
			String json = buffer.toString();
				
			Commits[] commits = getTimelineCommitsFromJSON(json, projectId, since, until, monthNumber);
			for (Commits timelineCommits : commits) {
				session.beginTransaction();			
				session.save(timelineCommits);
				session.getTransaction().commit();
			}
			
			// Check if it's paged
			String link = conn.getHeaderField("Link");
			if(link!=null && link.contains("rel=\"next\"")){
				// GET rel="next" link
				link = link.replaceFirst("<", "");
				link = link.substring(0, link.indexOf(">"));
				runCommitsQuery(link, projectId, session, since, until, monthNumber);
			}

			conn.disconnect();
			
	}

	/** 
	 * Parses the JSON response from the GitHub API requests and populates the Commit objects. 
	 * 
	 * @param json
	 * @param projectId
	 * @param since
	 * @param until
	 * @param monthNumber
	 * @return
	 */
	private Commits[] getTimelineCommitsFromJSON(String json, Integer projectId, String since, String until, int monthNumber) {
		ArrayList<Commits> commitsList = new ArrayList<Commits>();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(json);
			 			
			JSONArray commitArray = (JSONArray) obj;	
			for (Object commitObj : commitArray) {

				try
				{
					JSONObject commit = (JSONObject) commitObj;
					JSONObject commitInternal =  (JSONObject) commit.get("commit");
					JSONObject commitCommitter =  (JSONObject) commitInternal.get("committer");
					JSONObject committer =  (JSONObject) commit.get("committer");
	
					String sha = (String) commit.get("sha");
					String date = (String) commitCommitter.get("date");
					String userName = (String) commitCommitter.get("name");
					String message = (String) commitInternal.get("message");
					Integer userId = new Integer(-1);
					String userLogin = "";
					String type = "";
					if(committer!=null){
	
						userId = ((Long)committer.get("id")).intValue();
						userLogin = (String) committer.get("login");
						type = (String) committer.get("type");
					}
					
					Commits tCommit = new Commits();
					tCommit.setProjectId(projectId);
					tCommit.setSince(since);
					tCommit.setUntil(until);
					tCommit.setMonthNumber(monthNumber);
					tCommit.setDate(date);
					tCommit.setSha(sha);
					tCommit.setType(type);
					tCommit.setUserId(userId);
					tCommit.setUserLogin(userLogin);
					tCommit.setUserName(userName);
					if(message.length()>5000)
						message = message.substring(0, 4900)+"...";
					tCommit.setMessage(message);
						
					commitsList.add(tCommit);
				}
				catch(Exception e)
				{
					logger.error("PROBLEM WITH COMMIT FROM: "+projectId + " since: "+ since +" until "+until);
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return commitsList.toArray(new Commits[commitsList.size()]);
	}


	/** 
	 * Builds a connection to GitHub API with the required credentials 
	 * 
	 * @param queryURL
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private  HttpURLConnection getConnection(String queryURL)
			throws MalformedURLException, IOException, ProtocolException {
		
		URL url = new URL(queryURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.setRequestProperty("Authorization", "Basic " + this.credentials);
		conn.setRequestProperty("Accept", "application/vnd.github.preview");
		return conn;
	}
}
