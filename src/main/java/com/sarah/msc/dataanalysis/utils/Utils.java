package com.sarah.msc.dataanalysis.utils;
/** 
 * Simple utilities to help with the connection to the GitHub API 
 * along with data manipulation and conversion methods.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;


public class Utils {

	public Utils()
	{
		this.getProperties();
	}
	
	public static final int MONTHS_12 = 12;
	public String GITHUB_NAME;
	public String GITHUB_PASSWORD;
	public String PROJECT_DIR;
	/**
	 * @param args
	 */
	private void getProperties() {
		Properties prop = new Properties();
		 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("C:\\config.properties"));
 
               //get the property value and print it out
    		this.GITHUB_NAME = prop.getProperty("github.name");
    		this.GITHUB_PASSWORD = prop.getProperty("github.password");
    		this.PROJECT_DIR = prop.getProperty("project.dir");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    	
	}
	
	  /**
     * encryptCredentials for this instance of username & password
     */
    public String encryptCredentials(String username, String password) {
    	String userPass = username + ":" + password;
		String basicCrdential = null;
		
		if(username.trim().length() != 0 && 
				password.trim().length() != 0){
			byte [] userPassBase64 = Base64.encodeBase64(userPass.getBytes());
			basicCrdential = new String(userPassBase64);
		}
		return basicCrdential;

    }

    public Date addFourWeeksToDate(Date date) {
		Calendar newDate = Calendar.getInstance();  
		newDate.setTime(date);  
		Format f = new SimpleDateFormat("yyyy-MM-dd");  
		newDate.add(Calendar.WEEK_OF_YEAR,4);
		return newDate.getTime();
	}
    

    public Date addOneDayToDate(Date date) {
		Calendar newDate = Calendar.getInstance();  
		newDate.setTime(date);  
		Format f = new SimpleDateFormat("yyyy-MM-dd");  
		newDate.add(Calendar.DAY_OF_YEAR,1);
		return newDate.getTime();
	}
    
    
	public String getStringFromDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		return formatter.format(date);
	}

	public Date getDateFromDBString(String dateInString) {
		//dateInString = dateInString.replace("T", " ");
		//dateInString = dateInString.replace("Z", "");
		                                                 //2011-07-29 21:19:00
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	 
		try {
	 
			return formatter.parse(dateInString);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
