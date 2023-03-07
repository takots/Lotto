package com.common;

import java.util.Locale;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date {
	private final SimpleDateFormat simpleDateFormat_ToDay = new SimpleDateFormat("yyyy/MM/dd");
	private final SimpleDateFormat SimpleDateFormat_NOW = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
	private final SimpleDateFormat SimpleDateFormat_NOWTime = new SimpleDateFormat("HH:mm:ss", Locale.US);
	private static final SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);

	public boolean WhatTime(String time) throws ParseException {
		boolean timeup = false;
		if (SimpleDateFormat_NOWTime.format(new java.util.Date()).compareTo(time) > 0) {
			timeup = true;
		}
		return timeup;
	}

	public String ToDay() {
		return this.simpleDateFormat_ToDay.format(new java.util.Date()).toString();
	}

	public String Now() {
		return this.SimpleDateFormat_NOW.format(new java.util.Date()).toString();
	}

	public String NowTime() {
		return this.SimpleDateFormat_NOWTime.format(new java.util.Date()).toString();
	}

	public static String timestampToString(Timestamp timestamp) {
		return SimpleDateFormat.format(new java.util.Date(timestamp.getTime())).toString();
	}
}
