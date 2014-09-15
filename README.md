CloudMSc-TrackingOpenSource
===========================

Tracking the success of open source projects

This code is part of the thesis, Tracking the Success of Open Source Projects, as submitted by Sarah O'Connell BSc, MSc.

The code here allows for the retrieval and organisation of data from GitHub repositories into a MySQL database. 
Details of the database schema can be found in the database directory in this CD.

The code has a number of modes: 
1) Retrieval of data from GitHub
2) Creating the timeline matrix for the historical data 
3) Calculating the bug fixing speed for a repository 
4) Proving the ranking algorithm by running against some known GitHub projects

The execution of each of this modes is detailed in this readme document.

Note: To run GitHub API queries - GitHub account credentials are required in \Source Code\src\main\resources\config.properties
also modify the path in \Source Code\src\main\java\com\sarah\msc\dataanalysis\utils\Utils.java to point 
to the config.properties file on your local system.


1) Retrieval of data from GitHub 
===========================
Main Class: com.sarah.msc.dataanalysis.run.GitHubPopularRepoGetter

Usage: 
The execution of this class will request a choice to be entered by the user to indicate at what 
stage in the process they would like to begin. 

Typically, a user will select option 1. However, as the complete execution of this class takes some time, 
the user may wish to resume at another point.

The process is broken up into 15 parts which are easily readable within the source code of this class.


2) Creating the timeline matrix for the historical data 
===========================
Main Class: com.sarah.msc.dataanalysis.run.CreateTimelineMatrix

Usage: 
Following the execution of the data retrieval process, this is one of the data preparation steps necessary before
using the data in RapidMiner 

This class aggregates data that has been collected in monthly chunks into more manageable quarterly sections.

This will only need to be run once following data retrieval. 

3) Calculating the bug fixing speed for a repository 
===========================
Main Class: com.sarah.msc.dataanalysis.run.MonthlyBugFixSpeed

Usage: 
As GitHub does not explicitly record the bug fixing speed as an attribute, this class iterates through each 
issue in the dataset and calculates the lifespan of that issue in days.

4) Proving the ranking algorithm by running against some known GitHub projects
===========================
Main Class: com.sarah.msc.dataanalysis.run.WeightingCalculator

Usage: 
A simple test that pre-populates the data for a number of projects and calculates the ranking 
of each project using the attribute weights yielded through SVM.
