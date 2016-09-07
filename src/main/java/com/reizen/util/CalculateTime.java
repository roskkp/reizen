package com.reizen.util;

import java.sql.Date;

public class CalculateTime {

	public static final int SEC = 60;
	public static final int MIN = 60;
	public static final int HOUR = 24;
	public static final int DAY = 30;
	public static final int MONTH = 12;

	public CalculateTime() {
		// TODO Auto-generated constructor stub
	}

	public static String calc(Date date) {
		long curTime = System.currentTimeMillis();
		long regTime = date.getTime();
		long diffTime = (curTime - regTime) / 1000;

		String msg = null;

		if (diffTime < SEC) {
			// sec
			msg = diffTime + " seconds ago";
		} else if ((diffTime /= SEC) < MIN) {
			// min
			msg = diffTime + " minutes ago";
		} else if ((diffTime /= MIN) < HOUR) {
			// hour
			msg = (diffTime) + " hours ago";
		} else if ((diffTime /= HOUR) < DAY) {
			// day
			msg = (diffTime) + " days ago";
		} else if ((diffTime /= DAY) < MONTH) {
			// day
			msg = (diffTime) + " months ago";
		} else {
			msg = (diffTime) + " years ago";
		}

		return msg;
	}
}
