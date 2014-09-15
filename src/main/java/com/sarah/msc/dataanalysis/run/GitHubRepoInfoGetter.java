package com.sarah.msc.dataanalysis.run;
/**
 * This class retrieves the basic information for a GitHub repository through the API 
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
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sarah.msc.dataanalysis.model.Repository;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;
import com.sarah.msc.dataanalysis.utils.Utils;

public class GitHubRepoInfoGetter {

	private static String GITHUB_REPOS_API = "https://api.github.com/repos";
	
	private static Logger logger = Logger.getLogger(GitHubRepoInfoGetter.class);

	private static String credentials = "";

	private Repository repo = new Repository();
	
	private int watchersCount = 0;
	private int commitsCount = 0;
	
	public GitHubRepoInfoGetter(){
		
	}
	
	public GitHubRepoInfoGetter(String creds){
		this.credentials = creds;
	}
	
	

	/**
	 * Get the basic information for a GitHub repository 
	 */
	public void getRepoData() {

			Session session = HibernateUtil.getSessionFactory().openSession();
			
			// 1. Repo Call (stars/forks/size/contributors)			
			getBasicRepoInfo();
			
			// 2. Tags Call (#tags)
			getNumberOfTags();		
			
			// 3. Contributors Call - (#Contributors) Get Users - Add login to HashMap
			getContributors();	
						
			// 4. Pulls Call - (#pulls - open/closed) Get Users  - Add login to HashMap
			getPulls();		
			
			// 5. Issues Call - (#issues (minus pulls - open/closed) Get Users - Add login to HashMap
			getIssues();	
			
            // 6. Subscribers call = #watchers https://api.github.com/repos/twbs/bootstrap/subscribers
			getSubscribers();
			
			// 7. Commits call = #commits
			getCommits();			
			
	}
	
	/** 
	 * Retrieve the number of tags for this repository
	 */
	public void getNumberOfTags() {
		try {
			int numberOfTags = runTagsQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/tags");
			repo.setTags(numberOfTags);
			Thread.sleep(1000);// give the API a break!		
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	/** 
	 * Retrieve the number of contributors for this repository
	 */
	public void getContributors() {
		try {
			int numberOfContributors = runCollaboratorsQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/contributors");
			repo.setContributorsTotal(numberOfContributors);
			Thread.sleep(1000);// give the API a break!		
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	/** 
	 * Retrieve the number of pulls for this repository
	 */
	public void getPulls() {
		try {
			int openPulls = runPageCountQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/pulls?state=open&per_page=1");
			repo.setOpenPulls(openPulls);
			Thread.sleep(1000);// give the API a break!		
			int closedPulls = runPageCountQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/pulls?state=closed&per_page=1");
			repo.setClosedPulls(closedPulls);
			Thread.sleep(1000);// give the API a break!		
						
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
	}
	
	/** 
	 * Retrieve the number of issues & pulls for this repository
	 * // NOTE: ISSUES contains ISSUES AND PULLS!!!
	 */
	public void getIssues() {
		try {
			int openIssues = runPageCountQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/issues?state=open&per_page=1");
			openIssues = openIssues-repo.getOpenPulls();
			if(openIssues<0)
				openIssues = 0;
				
			repo.setOpenIssues(openIssues);
			Thread.sleep(1000);// give the API a break!		
			int closedIssues = runPageCountQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/issues?state=closed&per_page=1");
			closedIssues = closedIssues-repo.getClosedPulls();
			if(closedIssues<0)
				closedIssues = 0;
			
			repo.setClosedIssues(closedIssues);
			Thread.sleep(1000);// give the API a break!		
						
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
	}
	/** 
	 * Retrieve the number of subscribers (Watchers) for this repository
	 */
	public void getSubscribers() {
		try {
			watchersCount = 0;
			runWatchersCountQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/subscribers");
			repo.setWatchers(watchersCount);
			Thread.sleep(1000);// give the API a break!		
						
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
	}
	
	/** 
	 * Retrieve the number of commits for this repository
	 */
	public void getCommits() {
		try {
			commitsCount = 0;
			runCommitsCountQuery(GITHUB_REPOS_API+"/"+repo.getFullName()+"/commits?per_page=100");
			repo.setCommits(commitsCount);
			Thread.sleep(1000);// give the API a break!		
						
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
	}
	
	/** 
	 * Parse the basic repository information from the JSON response. 
	 * This includes details such as the name, URL, forks, stars and creation date.
	 */
	public void getBasicRepoInfo() {
		try {

			HttpURLConnection conn = getConnection(GITHUB_REPOS_API+"/"+repo.getFullName());

			
			if (conn.getResponseCode() == 404){
				// PROBLEM for this project
				logger.info("getBasicRepoInfo: 404 "+repo.getFullName());
				return;
			}
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			final StringBuffer buffer = new StringBuffer();
		
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}

			conn.disconnect();
			
			// Extract JSON from the JSONP envelope
			String json = buffer.toString();
			
			logger.debug(json);
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
			 
			JSONObject repoJSON = (JSONObject) obj;
			
			repo.setId(((Long)repoJSON.get("id")).intValue());
			repo.setName((String)repoJSON.get("name"));
			repo.setFullName((String)repoJSON.get("full_name"));

			// NOTE: watchers are actually stars in this call...
			// GitHub renamed the feature to stars
			// Watchers is a different concept and cannot to retrived in this call
			repo.setStars(((Long) repoJSON.get("watchers_count")).intValue()); 
			repo.setForks(((Long) repoJSON.get("forks_count")).intValue());
			repo.setUrl((String)repoJSON.get("html_url"));
			
			repo.setCreatedAt((String)repoJSON.get("created_at"));
			repo.setUpdatedAt((String)repoJSON.get("updated_at"));			
			
			JSONObject repoOWNER = (JSONObject) repoJSON.get("owner");
			repo.setOwnerLogin((String) repoOWNER.get("login"));
			repo.setOwnerId(((Long) repoOWNER.get("id")).intValue());
			
			repo.setDescription((String)repoJSON.get("description"));
			repo.setLanguage((String)repoJSON.get("language"));
			
			Long size = (Long) repoJSON.get("size");
			if(size!=null)
				repo.setSize((size).intValue());
			
			
		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Execute the query to retrieve the tags from the GitHub API 
	 * 
	 * @param queryURL
	 * @return
	 * @throws ParseException
	 */
	public int runTagsQuery(String queryURL) throws ParseException {

		HttpURLConnection conn = null;
		
		try {			
			conn = getConnection(queryURL);
			if (conn.getResponseCode() == 404){
				// issues are disabled for this project
				return Integer.parseInt("-1");
			}
			
			if (conn.getResponseCode() == 410){
				// issues are disabled for this project
				return Integer.parseInt("-1");
			}
			
			if (conn.getResponseCode() != 200) {			
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
		
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			final StringBuffer buffer = new StringBuffer();
			
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
			
			String json = buffer.toString();
							
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
				 
			JSONArray jsonArray = (JSONArray) obj;
			return jsonArray.size();
			
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		  finally{
			  if(conn!=null)
				  conn.disconnect();
		}
		
		return 0;
	}
	
	/** 
	 * Execute the query to retrieve the number of collaborators for this repository.
	 * @param queryURL
	 * @return
	 * @throws ParseException
	 */
	public int runCollaboratorsQuery(String queryURL) throws ParseException {

		logger.info("runCollaboratorsQuery: queryURL");
		HttpURLConnection conn = null;
		
		try {			
			conn = getConnection(queryURL);
			
			if (conn.getResponseCode() == 410){
				// issues are disabled for this project
				logger.info("runCollaboratorsQuery: 410 return -1");
				return Integer.parseInt("-1");
			}
			
			if (conn.getResponseCode() == 404){
				// PROBLEM for this project
				logger.info("runCollaboratorsQuery: 404 return -1");
				return Integer.parseInt("-1");
			}
			
			if (conn.getResponseCode() != 200) {			
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
					
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			final StringBuffer buffer = new StringBuffer();
			
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
			
			String json = buffer.toString();
							
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
				 
			JSONArray jsonArray = (JSONArray) obj;
			
			//findCollaboratorUsers(jsonArray);
			
			return jsonArray.size();
			
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		  finally{
			  if(conn!=null)
				  conn.disconnect();
		}
		
		return 0;
	}
	/**
	 * Count the number of pages returned for a given query. 
	 * 
	 * @param queryURL
	 * @return
	 * @throws ParseException
	 */
	public int runPageCountQuery(String queryURL) throws ParseException {

		HttpURLConnection conn = null;
		
		try {
			
			conn = getConnection(queryURL);
			
			
			if (conn.getResponseCode() == 410){
				// issues are disabled for this project
				return Integer.parseInt("-1");
			}
			
			if (conn.getResponseCode() == 404){
				// issues are disabled for this project
				return Integer.parseInt("-1");
			}
			
			if (conn.getResponseCode() != 200) {			
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			String link = conn.getHeaderField("Link");
			if(link!=null){
				//paging
				String lastLink = link.split(",")[1];
				String lastCount = lastLink.split(";")[0];
				lastCount = lastCount.replace(">", "");
				String count = lastLink.substring(lastCount.lastIndexOf("=")+1, lastCount.length());
				return Integer.parseInt(count);
			}
			else{
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				final StringBuffer buffer = new StringBuffer();
			
				while ((output = br.readLine()) != null) {
					buffer.append(output);
				}
					
				// Extract JSON from the JSONP envelope

				String json = buffer.toString();
								
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(json);
					 
				JSONArray jsonArray = (JSONArray) obj;
				return jsonArray.size();
			}				
			
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		  finally{
			  if(conn!=null)
				  conn.disconnect();
		}
		
		return 0;
	}
	

	/** 
	 * Execute the query to retrieve the number of watchers (subscribers) 
	 * 
	 * @param queryURL
	 * @throws ParseException
	 */
	public void runWatchersCountQuery(String queryURL) throws ParseException {

		HttpURLConnection conn = null;
		
		try {
			
			conn = getConnection(queryURL);

			if (conn.getResponseCode() == 404){
				// issues are disabled for this project
				return;
			}
			
			if (conn.getResponseCode() == 410){
				// issues are disabled for this project				
			}
			
			if (conn.getResponseCode() != 200) {			
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			StringBuffer buffer = new StringBuffer();
		
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
				
			// Extract JSON from the JSONP envelope

			String json = buffer.toString();
			buffer =null;				
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
				 
			JSONArray jsonArray = (JSONArray) obj;
			
			watchersCount+=jsonArray.size();
						
			// Check if it's paged
			String link = conn.getHeaderField("Link");
			if(link!=null && link.contains("rel=\"next\"")){
				// GET rel="next" link
				link = link.replaceFirst("<", "");
				link = link.substring(0, link.indexOf(">"));
				runWatchersCountQuery(link);
			}
			
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		  finally{
			  if(conn!=null)
				  conn.disconnect();
		}		
	}
	/**
	 * Execute the query to retrieve the number of commits for this repository 
	 * 
	 * @param queryURL
	 * @throws ParseException
	 */
	public void runCommitsCountQuery(String queryURL) throws ParseException {

		logger.info("queryURL");	
		HttpURLConnection conn = null;
		
		try {
			
			conn = getConnection(queryURL);
			
			if (conn.getResponseCode() == 404){
				// issues are disabled for this project
				return;
			}
			
			if (conn.getResponseCode() == 410){
				// issues are disabled for this project	
				return;			
			}

			if (conn.getResponseCode() == 409){
				logger.error("409 error");	
				return;
			}
			
			if (conn.getResponseCode() != 200) {			
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}			
			
			// Check if it's paged
			String link = conn.getHeaderField("Link");
			if(link!=null && link.contains("rel=\"next\"")){
				commitsCount+=100;
			}
			else
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				StringBuffer buffer = new StringBuffer();
			
				while ((output = br.readLine()) != null) {
					buffer.append(output);
				}
					
				// Extract JSON from the JSONP envelope

				String json = buffer.toString();
				buffer = null;
								
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(json);
					 
				JSONArray jsonArray = (JSONArray) obj;
				
				commitsCount+=jsonArray.size();
			}
			
			// Check if it's paged
			if(link!=null && link.contains("rel=\"next\"")){
				// GET rel="next" link
				link = link.replaceFirst("<", "");
				link = link.substring(0, link.indexOf(">"));
				runCommitsCountQuery(link);
			}
			
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		  finally{
			  if(conn!=null)
				  conn.disconnect();
		}		
	}
	
	/**
	 * Build a connection to the GitHub API with the required credentials. 
	 * 
	 * @param queryURL
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private static HttpURLConnection getConnection(String queryURL)
			throws MalformedURLException, IOException, ProtocolException {
		System.out.println("\t Run Query: "+queryURL);
		
		URL url = new URL(queryURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.setRequestProperty("Authorization", "Basic " + credentials);
		conn.setRequestProperty("Accept", "application/vnd.github.preview");
		return conn;
	}


	/** 
	 * Get the repository that these queries have been run for 
	 * 
	 * @return
	 */
	public Repository getRepo() {
		return repo;
	}


	/** 
	 * Set the repository to run the queries for 
	 * 
	 * @param repo
	 */
	public void setRepo(Repository repo) {
		this.repo = repo;
	}
	
	
}
