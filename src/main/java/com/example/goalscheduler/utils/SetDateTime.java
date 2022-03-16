package com.example.goalscheduler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetDateTime {
	private String from;
	private String to;
	private static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	
	public SetDateTime(String from, String to) {
		//"Format as such: yyyy-MM-dd'T'HH:mm"
		this.from = from;
		this.to = to;
	}
	
	public static Date parse(String dateString) {
		Date d = null;
		try {
		    d = f.parse(dateString);
		    return d;
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return d;
	
	}
	
	public Date getFrom() {
		Date d = null;
		try {
		    d = f.parse(from);
		    return d;
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return d;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getTo() {
		Date d = null;
		try {
		    d = f.parse(to);
		    return d;
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return d;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
}
