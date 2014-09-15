package com.sarah.msc.dataanalysis.run;

/** 
 * This class uses the attribute weights calculated using SVM to derive 
 * ranking results for a number of repositories 
 * 
 */
import java.util.ArrayList;

public class WeightingCalculator {

	/** 
	 * Attribute weighting by SVM yielded: 
	 * 
	 * tags		0.04101072907549392
	 * forks	0.1281036567024759
	 * openIssues	0.13889798318760208
	 * closedIssues	0.24753591597490435
	 * watchers	0.6625813716541344
	 * contributorsTotal	1.0
	 * @param args
	 */
	
	private static final double TAG_WEIGHT 			= 	0.04101;
	private static final double FORK_WEIGHT 		= 	0.12810;
	private static final double OPEN_ISSUE_WEIGHT	=	0.13889;
	private static final double CLOSED_ISSUE_WEIGHT	= 	0.24753;
	private static final double WATCHER_WEIGHT		= 	0.66258;
	private static final double CONTRIBUTOR_WEIGHT	= 	1;
	
	
	
	
	private ArrayList<Project> projects = new ArrayList<Project>(); 
	
	public WeightingCalculator(){
		prepareData();
	}
	
	
	
	/**
	 * Prepare a list of known projects 
	 * 
	 * Note: this could be done with the projects from the database. 
	 */
	private void prepareData(){
		Project bootstrap = new Project("Bootstrap"); 
		bootstrap.setWatchers(58352);
		bootstrap.setForks(20070);
		bootstrap.setTags(22);
		bootstrap.setContributors(416);
		bootstrap.setOpenIssues(133);
		bootstrap.setClosedIssues(7575);
		
		projects.add(bootstrap);
		Project node  = new Project("Node",24603, 4746,217,447,491,3447);
		projects.add(node);
		Project backbone = new Project("Backbone", 15692,3212,18,192,18,1551); 
		projects.add(backbone);
		 
		Project three = new Project("three.js",	12756,	2844,	53,	203,	323,	2800);
		projects.add(three);
		
		Project express = new Project("express",	10691,	1747,	121,	113,	43,	1392);
		projects.add(express);
		
		Project moment = new Project("moment",	10337,	1009,	29,	135,	43,	679);
		projects.add(moment);
		
		Project modern = new Project("Modernizr",	10227,	1175,	11,	114,	109,	595);
		projects.add(modern);
		
		Project jasmine = new Project("jasmine",	5502,	693,	27,	49,	27,	227);
		projects.add(jasmine);
		
		Project handlebars = new Project("handlebars.js",	5116,	697,	17,	69,	37,	404);
		projects.add(handlebars);
		
		Project easel = new Project("EaselJS",	2779,	659,	9,	15,	35,	241);
		projects.add(easel);
		Project audiolib = new Project("audiolib.js",	432,	45,	0,	5,	2,	50);
		projects.add(audiolib);
		
		Project safari = new Project("safari-json-formatter",	305,	36,	4,	3,	11,	9);
		projects.add(safari);
		Project joshfire = new Project("joshfire-framework",	230,	28,	0,	19,	3,37);
		projects.add(joshfire);
		Project doodle = new Project("doodle-js",	229,	18,	0,	1,	1,	2);
		projects.add(doodle);
		
		
		Project form = new Project("formwizard",	101,	31,	4,	6,	3,	25);
		projects.add(form);
		
		Project oauth = new Project("oauth2_server_node",	101,	13,	1,	2,	0,	0);
		projects.add(oauth);
		
		Project jquerySerialize = new Project("jquery-serialize-object",	101,	25,	2,	2,	2,	4);
		projects.add(jquerySerialize);
	}
	
	/** 
	 * For each project, calculate the ranking score using Weight by SVM
	 */
	private void run(){
		System.out.println("----------------WEIGHT BY SVM----------------------");
		for (Project project :projects) {
			
			double score = 	(project.getTags()* TAG_WEIGHT) + 
						    (project.getWatchers() * WATCHER_WEIGHT) + 
						    (project.getForks() * FORK_WEIGHT) + 
						    (project.getContributors() * CONTRIBUTOR_WEIGHT) + 
						    (project.getOpenIssues() * OPEN_ISSUE_WEIGHT)  +
						    (project.getClosedIssues() * CLOSED_ISSUE_WEIGHT);
			
			System.out.println(project.getName() + " scored " + score);
		}
		
	
	}
	
	
	public static void main(String[] args) {
		
		WeightingCalculator calc = new WeightingCalculator(); 
		
		calc.run();
			
	}
	
	/** 
	 * A very simple representation of a project for this test. 
	 * 
	 * @author Sarah O'Connell
	 *
	 */
	class Project{
		
		private int tags, forks, openIssues, closedIssues, watchers, contributors;
		private String name; 
		
		public Project(String name){
			this.name = name;
		}
		
		public Project(String name, int watchers, int forks, int tags, int contributors, int openIssues, int closedIssues){
			this.name = name;
			this.watchers = watchers; 
			this.forks = forks;
			this.tags = tags;
			this.contributors = contributors; 
			this.openIssues = openIssues; 
			this.closedIssues = closedIssues;
		}
		
		public String getName(){
			return this.name;
		}
		public int getTags() {
			return tags;
		}

		public void setTags(int tags) {
			this.tags = tags;
		}

		public int getForks() {
			return forks;
		}

		public void setForks(int forks) {
			this.forks = forks;
		}

		public int getOpenIssues() {
			return openIssues;
		}

		public void setOpenIssues(int openIssues) {
			this.openIssues = openIssues;
		}

		public int getClosedIssues() {
			return closedIssues;
		}

		public void setClosedIssues(int closedIssues) {
			this.closedIssues = closedIssues;
		}

		public int getWatchers() {
			return watchers;
		}

		public void setWatchers(int watchers) {
			this.watchers = watchers;
		}

		public int getContributors() {
			return contributors;
		}

		public void setContributors(int contributors) {
			this.contributors = contributors;
		} 
		
		
		
	}

}
