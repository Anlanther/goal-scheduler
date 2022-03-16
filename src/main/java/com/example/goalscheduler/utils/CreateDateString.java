package com.example.goalscheduler.utils;

public class CreateDateString {
	private String year, month, day, hour, minute;

	public CreateDateString(String year, String month, String day, String hour, String minute) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	public CreateDateString() {
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}
	
	public String createDate() {
		return getYear() + "-" + getMonth() + "-" + getDay() + "at" + getHour() + ":" +getMinute();
	}

}
