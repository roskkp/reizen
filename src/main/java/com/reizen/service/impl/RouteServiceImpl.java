package com.reizen.service.impl;

import java.util.ArrayList;
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
import com.reizen.util.BestRoute;
import com.reizen.util.DistanceCalc;
import com.reizen.util.JsonArraySort;
import com.reizen.util.JsonChange;

@Service
public class RouteServiceImpl implements RouteService {

  @Autowired
  RouteDao dao;

  @Override
  public Map<String, Object> getCurrentList(int no) {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      result.put("total", dao.totalCount(no));
      result.put("list", dao.selectCurrentList(no));
      result.put("status", "success");
    } catch (Exception e) {
      result.put("stauts", "failure");
    }
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
  public void updateSchedule(String route) {
    JSONObject jsonObject = new JSONObject(route);
    String jsonData = jsonObject.get("data").toString();
    String time = ((String)jsonObject.get("currentDate")).substring(0, 10);
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
  public int copySchedule(int scheduleNo, int copyScheduleNo, String date) {
    Map<String, Object> params = new HashMap<String, Object>();
    System.out.println("schedultNo : "+scheduleNo+" / copyScheduleNo : "+copyScheduleNo+" / date : "+date);
    params.put("scheduleNo", scheduleNo);
    params.put("copyScheduleNo", copyScheduleNo);
    return dao.copySchedule(params);
  }
  
  @Override
  public Map<String, Object> checkRoute(JSONArray data) {
    Map<String, Object> result = new HashMap<String, Object>();
    List<String> dataSet = new ArrayList<>();
    Map<String,Map<String, Double>> list = new HashMap<>();
    Map<String, Double> start = new HashMap<>();
    Map<String, Double> root;
    for (int i = 0; i < data.length()-1; i++) {
      dataSet.add("t"+(i+1));
      root = new HashMap<>();
      for (int j = 1; j < data.length(); j++) {
        double lat1 = Double.parseDouble(((JSONObject)data.get(i)).getString("mapY"));
        double lon1 = Double.parseDouble(((JSONObject)data.get(i)).getString("mapX"));
        double lat2 = Double.parseDouble(((JSONObject)data.get(j)).getString("mapY"));
        double lon2 = Double.parseDouble(((JSONObject)data.get(j)).getString("mapX"));
        if (i == 0) {
          start.put("t"+j, DistanceCalc.distance(lat1,lon1,lat2,lon2)); 
        } else {
          if (i != j) {
            root.put("t"+j, DistanceCalc.distance(lat1,lon1,lat2,lon2));
          }
        }
      }
      if( i != 0) list.put("t"+i,root);
    }
    String targets = "";
    for (String tn : dataSet) {
      targets += tn;
    }
    String path = BestRoute.routeOptimum(list, start, targets).get("path").toString();
    String[] paths = path.split("t");
    List<JSONObject> resultList = new ArrayList<JSONObject>();
    resultList.add(((JSONObject)data.get(0)));
    for (String string : paths) {
     if (!string.equals("")) {
        resultList.add(((JSONObject)data.get(Integer.parseInt(string))));
      }
    }
    try {
      result.put("data", resultList);
      result.put("seq",path);
      result.remove("list");
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return result;
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
