package com.sarah.msc.dataanalysis.run;
/**
 * Allows the retrieval of information about a particular user to be retrieved from the 
 * GitHub API 
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

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sarah.msc.dataanalysis.model.User;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;
 
public class GetUsers {  
	
	private static Logger logger = Logger.getLogger(GitHubIssuesGetter.class);
	private String credentials = "";	

	private static String GITHUB_USERS_API = "https://api.github.com/users";
	
	public GetUsers(String credentials){
		this.credentials = credentials;
	}
	
	/** 
	 * Retrieves detailed information about a given user from the GitHub API 
	 * 
	 * @param user
	 */
	public void getUser(User user)
	{
		logger.info("Start getUser");
											
		if(user.getLogin()!=null && !user.getLogin().isEmpty() && user.getId().intValue()!=-1)
			getUserQuery(user);		
				
		logger.info("End getUser");		
	}

	/** 
	 * Creates a user query for the given user
	 * @param user
	 */
	private void getUserQuery(User user) {
		
		try {
			runQuery(GITHUB_USERS_API+"/"+user.getLogin());
			Thread.sleep(500);// give the API a break!		
			
		} catch (Exception e) {

			logger.error("Failed: USER ERROR! - rerun??" + GITHUB_USERS_API+"/"+user.getLogin());
			logger.error("Failed: USER ERROR! - rerun??" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}								
	}
	/**
	 * Executes the query for the user against the GitHub API, saving the 
	 * details in the MySQL database. 
	 * 
	 * @param queryURL
	 * @throws Exception
	 */
	private void runQuery(String queryURL) throws Exception {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {

			HttpURLConnection conn = getConnection(queryURL);
						
			if (conn.getResponseCode() != 200) {			
				throw new RuntimeException(" ERROR - Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			// GET JSON and create RepoTags[]
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			final StringBuffer buffer = new StringBuffer();
		
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
				
			// Extract JSON from the JSONP envelope
			String json = buffer.toString();
			
			try{
				User user = getUserFromJSON(json);
				session.beginTransaction();			
				session.save(user);
				session.getTransaction().commit();
			}
			catch(Exception e)			{

				logger.error("Failed: USER ERROR! - rerun??" + e.getMessage());
				logger.error("Problem saving User: "+ queryURL);	
				e.printStackTrace();
			}
		
			conn.disconnect();
			
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }		
	}

	/** 
	 * Parses the response from the GitHub API and populates the user object.
	 * @param json
	 * @return
	 */
	private User getUserFromJSON(String json) {

		User userDB = new User();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(json);			 			
			
			JSONObject user = (JSONObject) obj;

			Integer id = ((Long)user.get("id")).intValue();
			String login = (String) user.get("login");
			Integer followers = ((Long)user.get("followers")).intValue();
			Integer following = ((Long)user.get("following")).intValue();
			Integer publicRepos = ((Long)user.get("public_repos")).intValue();
			Integer publicGists = ((Long)user.get("public_gists")).intValue();
			String createdAt = (String) user.get("created_at");
			
			userDB.setCreatedAt(createdAt);	
			userDB.setFollowers(followers);
			userDB.setFollowing(following);
			userDB.setId(id);
			userDB.setLogin(login);
			userDB.setPublicGists(publicGists);
			userDB.setPublicRepos(publicRepos);
						
		} catch (Exception e) {
			// IGNORE - move on

			logger.error("Failed: USER ERROR! - rerun??");
			logger.error("Failed: USER ERROR! - rerun??" + e.getMessage());
			e.printStackTrace();
		}
		return userDB;
	}
	
	/** 
	 * Builds a connection to the GitHub API with the required credentials.
	 * 
	 * @param queryURL
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private  HttpURLConnection getConnection(String queryURL)
			throws MalformedURLException, IOException, ProtocolException {
		System.out.println("\t Run Query: "+queryURL);
		
		URL url = new URL(queryURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.setRequestProperty("Authorization", "Basic " + credentials);
		conn.setRequestProperty("Accept", "application/vnd.github.preview");
		return conn;
	}
}
