package com.reizen.dao;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.reizen.domain.Route;

public interface RouteDao {

	public void insertRoute(Route route);
	
	public void updateRoute(Route route);

	public List<Route> selectCurrentList(int no);

	public List<Route> selectList(Map<String, Object> params);

	public int totalCount(int scheduleNo);

	public int deleteRoute(int routeNo);

	public void updateIndex(Route route);

	public void deleteDay(Route route);

	public void updateDay(Map<String, Object> params);
	
	public int copySchedule(Map<String, Object> params);
	
  public void bestRoute(Map<String, Object> params);
}
