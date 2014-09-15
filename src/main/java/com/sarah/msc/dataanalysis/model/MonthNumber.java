package com.sarah.msc.dataanalysis.model;

import java.util.Date;

public class MonthNumber {

	private int monthNumber;
	private Date startDate;
	private Date endDate;
	
	
	public MonthNumber(int monthNumber, Date startDate, Date endDate) {
		super();
		this.monthNumber = monthNumber;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public int getMonthNumber() {
		return monthNumber;
	}
	public void setMonthNumber(int monthNumber) {
		this.monthNumber = monthNumber;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
