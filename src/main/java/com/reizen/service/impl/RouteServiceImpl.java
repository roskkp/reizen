package com.reizen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.reizen.dao.RouteDao;
import com.reizen.domain.Route;
import com.reizen.service.RouteService;
import com.reizen.util.JsonArraySort;
import com.reizen.util.JsonChange;

@Service
public class RouteServiceImpl implements RouteService {

  @Autowired
  RouteDao dao;

  @Override
  public Map<String, Object> getCurrentList(int no) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("total", dao.totalCount(no));
    result.put("list", dao.selectCurrentList(no));
    return result;
  }

  @Override
  public Map<String, Object> getList(int day, int no) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("day", day);
    params.put("scheduleNo", no);
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("list", dao.selectList(params));
    return result;
  }

  @Override
  public void deleteSchedule(int routeNo, int day, int no, String currentDate) {
    dao.deleteRoute(routeNo);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("day", day);
    params.put("scheduleNo", no);
    Map<String, Object> list = new HashMap<String, Object>();
    list.put("list", dao.selectList(params));
    JSONObject jsonObject = new JSONObject(new Gson().toJson(list));
    List<JSONObject> jsonArray = JsonArraySort.updateJsonArray(jsonObject.get("list").toString());

    Route updateRoute = new Route();
    for (int i = 0; i < jsonArray.size(); i++) {
      updateRoute.setRouteNo(jsonArray.get(i).getInt("routeNo"));
      updateRoute.setTime(currentDate+" "+(String)(jsonArray.get(i).get("time")));
      updateRoute.setTravelSequence(i+1);
      dao.updateIndex(updateRoute);
    }
  }

  @Override
  public void updateSchedule(String jsonData, String time) {
    List<JSONObject> jsonArray = JsonArraySort.updateJsonArray(jsonData);

    Route updateRoute = new Route();
    for (int i = 0; i < jsonArray.size(); i++) {
      updateRoute.setRouteNo(jsonArray.get(i).getInt("routeNo"));
      updateRoute.setTime(time+" "+(String)(jsonArray.get(i).get("time")));
      updateRoute.setTravelSequence(i+1);
      dao.updateIndex(updateRoute);
    }
  }

  @Override
  public void deleteDay(Route route) {
    dao.deleteDay(route);
  }

 

  @Override
  public void updateDay(Route route, String condition) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("condition", condition);
    params.put("route", route);
    dao.updateDay(params);
  }

  @Override
  public int removeRoute(int no) {
    return dao.deleteRoute(no);
  }

  @Override
  public int copySchedule(int scheduleNo, int copyScheduleNo) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("scheduleNo", scheduleNo);
    params.put("copyScheduleNo", copyScheduleNo);
    return dao.copySchedule(params);
  }

  @Override
  public void bestRoute(JSONArray data) {
    Map<String, Object> params = null;
    try {
      for (int i = 0; i < data.length(); i++) {
        JSONObject jsonObject = (JSONObject)((JSONObject)data.get(i)).get("map");
        params = JsonChange.jsonToMap(jsonObject);
        System.out.println("params : "+params);
        dao.bestRoute(params);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public int getCount(int no) {
    return dao.totalCount(no);
  }

	@Override
	public void addRoute(Route route, int day, int no, String time) {
		dao.insertRoute(route);
		routeSort(day, no, time);
	}

	@Override
	public void updateRoute(Route route, int day, int no, String time) {
		dao.updateRoute(route);
		routeSort(day, no, time);
	}

	public void routeSort(int day, int no, String time) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("day", day);
		params.put("scheduleNo", no);
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("list", dao.selectList(params));
		JSONObject jsonObject = new JSONObject(new Gson().toJson(list));
		List<JSONObject> jsonArray = JsonArraySort.updateJsonArray(jsonObject.get("list").toString());

		Route updateRoute = new Route();
		for (int i = 0; i < jsonArray.size(); i++) {
			updateRoute.setRouteNo(jsonArray.get(i).getInt("routeNo"));
			updateRoute.setTime(time + " " + (String) (jsonArray.get(i).get("time")));
			updateRoute.setTravelSequence(i + 1);
			dao.updateIndex(updateRoute);
		}
	}

}
