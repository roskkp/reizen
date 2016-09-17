package com.reizen.service;

import java.util.Map;

import org.json.JSONArray;

import com.reizen.domain.Route;

public interface RouteService {

	public void addRoute(Route route, int day, int no, String time);
	
	public void updateRoute(Route route, int day, int no, String time);

	public Map<String, Object> getCurrentList(int no);

	public Map<String, Object> getList(int day, int no);
	
	public int getCount(int no);

	public void deleteSchedule(int routeNo, int day, int no, String currentDate);

	public void updateSchedule(String route);

	public void updateDay(Route route, String condition);

	public void deleteDay(Route route);
	
	public int removeRoute(int no);
	
	public int copySchedule(int scheduleNo, int copyScheduleNo);
	
	public Map<String, Object> checkRoute(org.json.JSONArray data);

  public void bestRoute(JSONArray data);
}
