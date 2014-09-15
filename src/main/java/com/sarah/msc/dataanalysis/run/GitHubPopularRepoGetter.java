package com.sarah.msc.dataanalysis.run;

/** 
 * The execution of this class will request a choice to be entered by the user to indicate at what 
 * stage in the process they would like to begin. 
 * 
 * Typically, a user will select option 1. However, as the complete execution of this class takes some time, 
 * the user may wish to resume at another point.
 * 
 * The process is broken up into 15 parts which are easily readable within the source code of this class.
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sarah.msc.dataanalysis.model.CommitUsersTodo;
import com.sarah.msc.dataanalysis.model.CrowstonViewCommitsTodo;
import com.sarah.msc.dataanalysis.model.CrowstonViewIssuesTodo;
import com.sarah.msc.dataanalysis.model.CrowstonViewWeeklyTodo;
import com.sarah.msc.dataanalysis.model.IssueUsersTodo;
import com.sarah.msc.dataanalysis.model.Repository;
import com.sarah.msc.dataanalysis.model.User;
import com.sarah.msc.dataanalysis.utils.HibernateUtil;
import com.sarah.msc.dataanalysis.utils.Utils;

public class GitHubPopularRepoGetter {

	private static final String STARS = "stars";

	private static Logger logger = Logger
			.getLogger(GitHubPopularRepoGetter.class);

	// 1000 PROJECTS = 10 X 100 (100 IS THE MAXIMUM PER PAGE)
	private static final int START_PAGE = 1;
	private static final int NUMBER_PAGES = 9;

	private static final String PER_PAGE_100 = "&per_page=100";

	// Limit the amount records that comes back
	private static final String Q_STARS = "?q=stars:>100";
	private static final String UPDATED = "+pushed:>2013-08";

	// Deviations for bad projects
	private static final String UPDATED_BAD = "+pushed:<2013-02";

	private static final String ORDER_ASC = "&order=asc";
	private static final String SORT_STARS = "&sort=stars";
	private static final String CREATED = "+created:<2012-01";
	private static final String LANGUAGE = "+language:javascript";

	private static final String GITHUB_REPO_SEARCH_API = "https://api.github.com/search/repositories";

	// q=stars:>500+created:<2012-01+pushed:>2013-08+language:javascript&sort=stars&order=desc&per_page=100
	private static final String TOP_1000_STARS = GITHUB_REPO_SEARCH_API
			+ Q_STARS + LANGUAGE + CREATED + UPDATED + SORT_STARS + ORDER_ASC
			+ PER_PAGE_100;

	private static final String BAD_PROJECT_QUERY = GITHUB_REPO_SEARCH_API
			+ Q_STARS + LANGUAGE + CREATED + UPDATED_BAD + SORT_STARS
			+ ORDER_ASC + PER_PAGE_100;

	private static String credentials = "";
	private static HashMap<Integer, Repository> repos = new HashMap<Integer, Repository>();

	private static GitHubRepoInfoGetter detailedInfo = null;
	private static GitHubCommitsGetter commitsInfo = null;
	private static GitHubIssuesGetter issuesInfo = null;
	private static GetUsers userInfo = null;
	private static GetMonthlyActivity weeklyInfo = null;

	/**
	 * This just gets the choice of where to start from the command line.
	 * 
	 * @return
	 */
	public static int getChoice() {
		System.out.println("Pick Your Start Point");
		System.out.println("1: Get Good Projects");
		System.out.println("2: Get Bad Projects");
		System.out.println("3: Get Basic Info");
		System.out.println("4: Get Contributors Count");
		System.out.println("5: Get Pulls Count");
		System.out.println("6: Get Issues Count");
		System.out.println("7: Get Subscribers Count");
		System.out.println("8: Get Commit Count");
		System.out.println("9: Get Tags Count");
		System.out.println("10: Get Commit Details - 18mths");
		System.out.println("11: Get Issue Details - 18mths");
		System.out.println("12: Get User Details for commits");
		System.out.println("13: Get User Details for issues");
		System.out.println("14: Get Weekly Activity");
		System.out.println("15: Get Project Total Activity");

		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			Integer i = Integer.parseInt(s);
			return i;

		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * This runs the next part of the program.
	 * 
	 * @param part
	 */
	public static void runPart(int part) {
		Utils utils = new Utils();
		credentials = utils.encryptCredentials(utils.GITHUB_NAME,
				utils.GITHUB_PASSWORD);
		detailedInfo = new GitHubRepoInfoGetter(credentials);
		try {

			switch (part) {

			case 1:
				logger.info("Start getPopularProjects");
				getPopularProjects(TOP_1000_STARS, STARS);
				break;

			case 2:
				logger.info("Get Bad Projects");
				getPopularProjects(BAD_PROJECT_QUERY, STARS);
				break;

			case 3:
				logger.info("Get Basic Info");
				getProjectInfo(BASIC);
				break;

			case 4:
				logger.info("Get Contribitors Count");
				getProjectInfo(CONTRIBUTORS_COUNT);
				break;

			case 5:
				logger.info("Get Pulls Count");
				getProjectInfo(PULLS_COUNT);
				break;

			case 6:
				logger.info("Get Issues Count");
				getProjectInfo(ISSUES_COUNT);
				break;

			case 7:
				logger.info("Get Subscribers Count");
				getProjectInfo(SUBSCRIBERS_COUNT);
				break;

			case 8:
				logger.info("Get Commits Count");
				getProjectInfo(COMMITS_COUNT);
				break;

			case 9:
				logger.info("Get Tags Count");
				getProjectInfo(TAGS_COUNT);
				break;

			case 10:
				logger.info("Get Commit Details - 18mths");
				getCrowstonInfo(COMMITS, utils);
				break;

			case 11:
				logger.info("Get Issue Details - 18mths");
				getCrowstonInfo(ISSUES, utils);
				break;

			case 12:
				logger.info("Get Users Details for Commits - 18mths");
				getUsers(COMMITS_USERS, utils);
				break;

			case 13:
				logger.info("Get Users Details for Issues - 18mths");
				getUsers(ISSUES_USERS, utils);
				break;

			case 14:
				logger.info("Get Monthly Activity - 18mths");
				getMonthlyActivity(MONTHLY_ACTIVITY, utils);
				break;

			case 15:
				logger.info("Get Project Total Activity - 18mths");
				getMonthlyActivity(TOTAL_PROJECT_ACTIVTIY, utils);
				break;

			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			logger.error(ie);
		}
	}

	private static final int BASIC = 3;
	private static final int CONTRIBUTORS_COUNT = 4;
	private static final int PULLS_COUNT = 5;
	private static final int ISSUES_COUNT = 6;
	private static final int SUBSCRIBERS_COUNT = 7;
	private static final int COMMITS_COUNT = 8;
	private static final int TAGS_COUNT = 9;
	private static final int COMMITS = 10;
	private static final int ISSUES = 11;
	private static final int COMMITS_USERS = 12;
	private static final int ISSUES_USERS = 13;
	private static final int MONTHLY_ACTIVITY = 14;
	private static final int TOTAL_PROJECT_ACTIVTIY = 15;

	/**
	 * Decorates each repository saved inthe database with the additional detail
	 * specified as a parameter.
	 * 
	 * @param detail
	 */
	private static void getProjectInfo(int detail) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Repository> repolist = session.createQuery("from Repository")
				.list();

		for (Repository savedRepo : repolist) {

			detailedInfo.setRepo(savedRepo);

			logger.info(detailedInfo.getRepo().getFullName());

			if (detail == BASIC) {
				// check if necessary. If we have owner id we can skip this
				// repo, right?
				if (savedRepo.getUrl() == null) {
					// GET THE REPO DATA
					logger.info("Getting basic info for " + savedRepo.getName());
					// detailedInfo.getRepoData();
					detailedInfo.getBasicRepoInfo();
				} else {
					logger.info("Already got basic info for "
							+ savedRepo.getName());

				}
			}

			if (detail == CONTRIBUTORS_COUNT) {
				// check if necessary. If we have owner id we can skip this
				// repo, right?
				if ((savedRepo.getContributorsTotal() == null)
						|| (savedRepo.getContributorsTotal() == 0)) {
					// GET THE REPO DATA
					logger.info("Getting contributor info for "
							+ savedRepo.getName());
					// detailedInfo.getRepoData();
					detailedInfo.getContributors();
				} else {
					logger.info("Already got contributors for "
							+ savedRepo.getName());

				}
				// getContributors
			}
			if (detail == PULLS_COUNT) {
				if ((savedRepo.getOpenPulls() == null)
						|| (savedRepo.getOpenPulls() == 0)) {
					// GET THE REPO DATA
					logger.info("Getting Pull info for " + savedRepo.getName());
					detailedInfo.getPulls();
				} else {
					logger.info("Already got Pulls for " + savedRepo.getName());

				}
			}

			if (detail == TAGS_COUNT) {
				// 2. Tags Call (#tags)
				if ((savedRepo.getTags() == null) || (savedRepo.getTags() == 0)) {
					// GET THE REPO DATA
					logger.info("Getting tag info for " + savedRepo.getName());
					// detailedInfo.getRepoData();
					detailedInfo.getNumberOfTags();
				} else {
					logger.info("Already got tag info for "
							+ savedRepo.getName());

				}
			}

			if (detail == ISSUES_COUNT) {
				if ((savedRepo.getOpenIssues() == null)
						|| (savedRepo.getOpenIssues() == 0)) {
					// GET THE REPO DATA
					logger.info("Getting issues info for "
							+ savedRepo.getName());
					// detailedInfo.getRepoData();
					detailedInfo.getIssues();
				} else {
					logger.info("Already got issues for " + savedRepo.getName());

				}
				// getIssues
			}
			if (detail == SUBSCRIBERS_COUNT) {
				// getSubscribers
				if ((savedRepo.getWatchers() == null)
						|| (savedRepo.getWatchers() == 0)) {
					// GET THE REPO DATA
					logger.info("Getting SUBSCRIBERS info for "
							+ savedRepo.getName());
					// detailedInfo.getRepoData();
					detailedInfo.getSubscribers();
				} else {
					logger.info("Already got SUBSCRIBERS for "
							+ savedRepo.getName());

				}
			}

			if (detail == COMMITS_COUNT) {
				// getCommits
				if ((savedRepo.getCommits() == null)
						|| (savedRepo.getCommits() == 0)) {
					// GET THE REPO DATA
					logger.info("Getting COMMIT info for "
							+ savedRepo.getName());
					// detailedInfo.getRepoData();
					detailedInfo.getCommits();
				} else {
					logger.info("Already got COMMITs for "
							+ savedRepo.getName());

				}
			}

			try {
				session.beginTransaction();
				session.save(detailedInfo.getRepo());
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/** 
	 * Get the commit details that align with the Crowston information
	 * @param detail
	 * @param utils
	 */
	private static void getCrowstonInfo(int detail, Utils utils) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		commitsInfo = new GitHubCommitsGetter(credentials);
		issuesInfo = new GitHubIssuesGetter(credentials);

		if (detail == COMMITS) {

			// return projects which are not already in the commits table
			List<CrowstonViewCommitsTodo> repolist = session.createQuery(
					"from CrowstonViewCommitsTodo").list();

			logger.info("CrowstonViewCommitsToDo: count: " + repolist.size());

			int count = 0;
			for (CrowstonViewCommitsTodo savedRepo : repolist) {

				count++;

				logger.info("CrowstonViewCommitsToDo: count: " + count + " / "
						+ repolist.size());
				logger.info("Getting COMMIT DETAILS for "
						+ savedRepo.getFullName() + "/ " + savedRepo.getId());
				commitsInfo.getCommits(savedRepo, utils);

			}
		}

		if (detail == ISSUES) {
			// return projects which are not already in the issues table
			List<CrowstonViewIssuesTodo> repolist = session.createQuery(
					"from CrowstonViewIssuesTodo").list();

			logger.info("CrowstonViewIssuesToDo: count: " + repolist.size());

			int count = 0;
			for (CrowstonViewIssuesTodo savedRepo : repolist) {

				count++;
				logger.info("-----------------------------------------------------");
				logger.info("CrowstonViewIssuesToDo: count: " + count + " / "
						+ repolist.size());
				logger.info("Getting ISSUE DETAILS for "
						+ savedRepo.getFullName() + "/ " + savedRepo.getId());
				logger.info("-----------------------------------------------------");
				issuesInfo.getIssues(savedRepo, utils);
			}

		}
	}

	/** 
	 * Collect the detail on all users in each repository.
	 * Users are those involved in commits or issues. 
	 * 
	 * @param detail
	 * @param utils
	 */
	private static void getUsers(int detail, Utils utils) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		userInfo = new GetUsers(credentials);

		if (detail == COMMITS_USERS) {
			// return commit users which are not already in the users table
			List<CommitUsersTodo> userslist = session.createQuery(
					"from CommitUsersTodo c").list(); // where c.userId not in
														// (select id from User)
														// group by userId

			int count = 0;
			for (CommitUsersTodo user : userslist) {

				count++;
				logger.info("NUMBER " + count + " / " + userslist.size());
				if (user.getUserId() != -1) {

					logger.info("Commits - Getting USER Data for "
							+ user.getUserLogin() + " / " + user.getUserId());

					User user1 = new User();
					user1.setId(user.getUserId());
					user1.setLogin(user.getUserLogin());
					userInfo.getUser(user1);
				} else
					logger.info("COMMITS - SKIP USER - INACTIVE!!! "
							+ user.getUserLogin() + " / " + user.getUserId());
			}
		}

		if (detail == ISSUES_USERS) {
			// return issue users which are not already in the users table
			List<IssueUsersTodo> userslist = session.createQuery(
					"from IssueUsersTodo c").list(); // where c.userId not in
														// (select id from User)
														// group by userId

			int count = 0;
			for (IssueUsersTodo user : userslist) {

				count++;
				logger.info("NUMBER " + count + " / " + userslist.size());
				if (user.getUserId() != -1) {

					logger.info("Commits - Getting USER Data for "
							+ user.getUserLogin() + " / " + user.getUserId());

					User user1 = new User();
					user1.setId(user.getUserId());
					user1.setLogin(user.getUserLogin());
					userInfo.getUser(user1);
				} else
					logger.info("COMMITS - SKIP USER - INACTIVE!!! "
							+ user.getUserLogin() + " / " + user.getUserId());
			}
		}
	}
	
	/**
	 * Assemble the monthly activity for each repository.
	 * @param detail
	 * @param utils
	 */
	private static void getMonthlyActivity(int detail, Utils utils) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		if (detail == MONTHLY_ACTIVITY) {

			List<CrowstonViewWeeklyTodo> repolist = session.createQuery(
					"from CrowstonViewWeeklyTodo ").list();
			weeklyInfo = new GetMonthlyActivity();

			HashMap<Integer, User> usersMap = new HashMap<Integer, User>();

			int count = 0;
			for (CrowstonViewWeeklyTodo savedRepo : repolist) {

				count++;
				logger.info("NUMBER " + count + " / " + repolist.size());

				logger.info("WEEKLY ACTIVITY - " + savedRepo.getFullName()
						+ " / " + savedRepo.getId());
				usersMap = weeklyInfo.getRepo(savedRepo, usersMap);
			}
		}

		
		session.close();
	}

	/**
	 * Main entry point for data retrieval
	 * IMPORTANT: if you don't want to run to the last step, add an end point here.
	 **/
	public static void main(String[] args) {
		logger.info("Starting the process");
		System.out.println("----Welcome to GitHub Data RetrievalRunner----------");
		int choice = getChoice();
		// ensure we have a valid choice first
		while (choice < 1 || choice > 15) {
			System.out.println("Pick something between 1 and 15");
			choice = getChoice();
		}

		// so we can run all parts from <choice> loop through til we get to the last step
		while (choice < 15) {
			runPart(choice);
			choice++;
		}
		logger.info("It is done");

	}
	
	/**
	 * Executes a query to get a list of the popular projects from GitHub
	 * @param queryURL
	 * @param type
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	private static void getPopularProjects(String queryURL, String type)
			throws ParseException, InterruptedException {
		logger.info("queryURL: " + queryURL);
		logger.info("Type: " + type);

		List<JSONArray> repoCol = new ArrayList<JSONArray>();
		// Loop the number of PAGES i.e. 10 X 100 = 1000 - NUMBER_PAGES X
		// PER_PAGE = Number of projects
		for (int i = START_PAGE; i <= NUMBER_PAGES; i++) {

			String page = "&page=" + i;
			String pagedURL = queryURL + page;
			logger.info(pagedURL);
			System.out.println(pagedURL);

			JSONArray jarray = runPopularQuery(pagedURL, type);
			repoCol.add(jarray);

			Thread.sleep(1000);
		}

		saveRepoJSONListToDatabase(repoCol, type);
	}
	
	/** 
	 * Executes the popular query are reads the results.
	 * @param queryURL
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	private static JSONArray runPopularQuery(String queryURL, String type)
			throws ParseException {
		try {

			HttpURLConnection conn = getConnection(queryURL);

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

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray repoArray = (JSONArray) jsonObject.get("items");
			return repoArray;

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return new JSONArray();
	}
	
	/**
	 * Save a list of repositories to the MySQL database.
	 * @param repoArrays
	 * @param type
	 */
	private static void saveRepoJSONListToDatabase(List<JSONArray> repoArrays,
			String type) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		logger.info("repoArrays SIZE " + repoArrays.size());

		int count = 1;
		for (JSONArray jsonArray : repoArrays) {

			logger.info("PAGE COUNT: " + count);

			count++;

			Iterator<JSONObject> repoLoop = jsonArray.iterator();
			logger.info("REPO ARRAY SIZE " + jsonArray.size());
			int i = 0;
			while (repoLoop.hasNext()) {

				i++;

				JSONObject repoJSON = repoLoop.next();

				// logger.info("repoJSON: "+repoJSON);

				Repository repo = new Repository();
				repo.setId(((Long) repoJSON.get("id")).intValue());
				repo.setName((String) repoJSON.get("name"));
				repo.setFullName((String) repoJSON.get("full_name"));
				repo.setStars(((Long) repoJSON.get("watchers_count"))
						.intValue());
				repo.setCreatedAt((String) repoJSON.get("created_at"));
				repo.setPushedAt((String) repoJSON.get("pushed_at"));
				repo.setUpdatedAt((String) repoJSON.get("updated_at"));

				// save
				logger.info("SAVING: " + repo);
				try {
					session.save(repo);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("ALREADY SAVE ?? SAVING ERROR WITH : " + repo);
					logger.error("EXCEPTION: " + e.getMessage());
				}

			}
		}

		try {
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EXCEPTION: " + e.getMessage());
		}
	}
	
	/** 
	 * Opens a connection to the GitHub API with the required credentials
	 * @param queryURL
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private static HttpURLConnection getConnection(String queryURL)
			throws MalformedURLException, IOException, ProtocolException {
		logger.info("\t Run Query: " + queryURL);
		System.out.println("\t Run Query: " + queryURL);

		URL url = new URL(queryURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Basic " + credentials);
		conn.setRequestProperty("Accept", "application/vnd.github.preview");
		return conn;
	}
}
