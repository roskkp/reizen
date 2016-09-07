package com.reizen.domain;

import java.io.Serializable;

public class Schedule implements Serializable {

	// Field
	private static final long serialVersionUID = 1L;

	private int scheduleNo;
	private User user;
	private String title;
	private int recommandCount;
	private int scrapCount;
	private int peopleCount;
	private int month;
	private int totalDays;
	private String thumbImg;
	
	public int getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRecommandCount() {
		return recommandCount;
	}

	public void setRecommandCount(int recommandCount) {
		this.recommandCount = recommandCount;
	}

	public int getScrapCount() {
		return scrapCount;
	}

	public void setScrapCount(int scrapCount) {
		this.scrapCount = scrapCount;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public String getThumbImg() {
		return thumbImg;
	}

	public void setThumbImg(String thumbImg) {
		this.thumbImg = thumbImg;
	}

	@Override
	public String toString() {
		return "Schedule [scheduleNo=" + scheduleNo + ", user=" + user + ", title=" + title + ", recommandCount="
				+ recommandCount + ", scrapCount=" + scrapCount + ", peopleCount=" + peopleCount + ", month=" + month
				+ ", totalDays=" + totalDays + ", thumbImg=" + thumbImg + "]";
	}

	

}
