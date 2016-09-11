package com.reizen.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reizen.domain.Memo;
import com.reizen.domain.Route;
import com.reizen.domain.Schedule;
import com.reizen.domain.User;
import com.reizen.service.MemoService;
import com.reizen.service.RouteService;
import com.reizen.service.ScheduleService;
import com.reizen.util.BestRoute;
import com.reizen.util.CalculateTime;
import com.reizen.util.DistanceCalc;

@Controller
@RequestMapping("/scheduler/")
public class SchedulerController {

  Map<String, Object> result = new HashMap<String, Object>();

  @Autowired
  RouteService routeService;

  @Autowired
  MemoService memoService;

  @Autowired
  ScheduleService scheduleService;

  @RequestMapping(path = "proceeding", produces = "application/json;charset=utf-8")
  @ResponseBody
  public String proceeding(@RequestParam(defaultValue = "0") int day, int scheduleNo) {
    if (day == 0) {
      return new Gson().toJson(routeService.getCurrentList(scheduleNo));
    } else {
      return new Gson().toJson(routeService.getList(day, scheduleNo));
    }
  }


  /*      scheduler 페이지에서 -> schedule day 하나씩 삭제      */
  @RequestMapping(path="updateDay")
  @ResponseBody
  public String updateDay(Route route, @RequestParam String condition){
    try{
      routeService.updateDay(route, condition);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }


  /*      scheduler 페이지에서 -> schedule day 하나씩 삭제      */
  @RequestMapping(path="deleteDay", produces="application/json; charset=UTF-8")
  @ResponseBody
  public String deleteDay(Route route){
    try{
      routeService.deleteDay(route);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }


  /*      scheduler 페이지에서 -> schedule 리스트 day count      */
  @RequestMapping(path="scheduleDayCount", produces="application/json; charset=UTF-8")
  @ResponseBody
  public String scheduleDayCount(@RequestParam int scheduleNo){
    try{
      result.put("total", routeService.getCount(scheduleNo));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  /*      scheduler 페이지에서 -> schedule 리스트      */
  @RequestMapping(path="scheduleList", produces="application/json; charset=UTF-8")
  @ResponseBody
  public String list(@RequestParam int day, int scheduleNo){
    try{
      result.put("list", routeService.getList(day, scheduleNo).get("list"));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    System.out.println(result);
    return new Gson().toJson(result);
  }

  /*      scheduler 페이지에서 -> schedule 생성      */
  @RequestMapping(path="addSchedule", produces="application/json; charset=UTF-8")
  @ResponseBody
  public String add(Schedule sc, HttpSession session){
    try{
      sc.setUser((User)session.getAttribute("user"));
      scheduleService.addSchedule(sc);
      result.put("scheduleNo", sc.getScheduleNo());
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  /*      scheduler 페이지에서 -> route 삭제      */
  @RequestMapping(path="removeRoute")
  @ResponseBody
  public String removeRoute(@RequestParam int routeNo){
    try{
    routeService.removeRoute(routeNo);
    result.put("status", "success");
  } catch (Exception e) {
    e.printStackTrace();
    result.put("status", "failure");
  }
  return new Gson().toJson(result);
  }


  /******  이 장소가 포함된 일정  ******/
  @RequestMapping(path="searchSchedule", produces="application/json; charset=UTF-8")
  @ResponseBody
  public String searchSchedule(int cId){
    Map<String, Object> params = new HashMap<>();
    params.put("contentId", cId);
    try {
      result = scheduleService.listSchedule(params);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="addRoute")
  @ResponseBody
  public String addRoute(Route route, int scheduleNo, int day, String currentDate){
    try {
      result.clear();
      routeService.addRoute(route, day, scheduleNo, currentDate);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="updateRoute")
  @ResponseBody
  public String updateRoute(Route route, int scheduleNo, int day, String currentDate){
	  System.out.println(route);
    try {
      result.clear();
      routeService.updateRoute(route, day,scheduleNo, currentDate);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  /* 회원=스케줄넘버 검사 */
  @RequestMapping(path = "scCheck")
  @ResponseBody
  public String check(@RequestParam int sc, HttpSession session) {
    try {
      int uno = ((User)session.getAttribute("user")).getUserNo();
      int resultUserNo = scheduleService.checkProceeding(sc);
      if (resultUserNo == uno) {
        System.out.println("접근 회원 : " + uno + "    이 스케줄의 회원 번호 : " + resultUserNo + "    일치");
        result.put("pass", "right");
      } else {
        System.out.println("회원 불일치 : proceeding.html 접근 제어");
        result.put("pass", "false");
      }
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "routeDelete", produces = "application/json;charset=utf-8")
  @ResponseBody
  public String routeDelete(@RequestParam int routeNo, int scheduleNo,int day, String currentDate){
    try {
      routeService.deleteSchedule(routeNo, scheduleNo, day, currentDate);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="arrayUpdate", produces="application/json; charset=utf-8", method=RequestMethod.POST)
  @ResponseBody
  public String array(@RequestBody String route) { 
    try {
      JSONObject jsonObject = new JSONObject(route);
      String jsonData = jsonObject.get("data").toString();
      String time = ((String)jsonObject.get("currentDate")).substring(0, 10);
      routeService.updateSchedule(jsonData, time);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "memo", produces = "application/json;charset=utf-8")
  @ResponseBody
  public String memo(@RequestParam int contentId) {
    try {
      List<Memo> list = memoService.getListFour(contentId);
      int i = 0;
      for (Memo memo : list) {
        memo.setDateAgo(CalculateTime.calc(memo.getRegDate()));
        list.set(i, memo);
        i++;
      }
      result.put("data", list);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="searchkeyword", method=RequestMethod.POST , produces="application/json;charset=utf-8")
  @ResponseBody
  public String searchKeyword(String keyword, String areaCode, String localCode, String category, String date, int page, String nop, String month, String term){
    Map<String, Object> params = new HashMap<>();
    params.put("keyword", keyword);
    params.put("areaCode", areaCode);
    params.put("localCode", localCode);
    params.put("date", date);
    params.put("startNo", (page-1)*6);
    params.put("size", 6);
    if (nop != null && !nop.equals("0")) params.put("nop", nop);
    if (month != null && !month.equals("0")) params.put("month", month);
    if (term != null &&!term.equals("0")) params.put("term", term);
    try {
      result = scheduleService.listSchedule(params);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  } 
  
  @RequestMapping(path="copySchedule")
  @ResponseBody
  public String copySchedule(@RequestParam int scheduleNo, int copyScheduleNo){
    System.out.println(scheduleNo+"<-----copy   "+copyScheduleNo);
    try {
      System.out.println(routeService.copySchedule(scheduleNo, copyScheduleNo));
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="bestRoute")
  @ResponseBody
  public String bestRoute(@RequestParam("data") org.json.JSONArray data ){
    List<String> dataSet = new ArrayList<>();
    List<Map<String, Double>> list = new ArrayList<>();
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
          start.put("t"+(i+1), DistanceCalc.distance(lat1,lon1,lat2,lon2)); 
        } else {
          if (i != j) {
            root.put("t"+j, DistanceCalc.distance(lat1,lon1,lat2,lon2));
          }
        }
      }
      if( i != 0) list.add(root);
    }
    
    System.out.println("start : "+start);
    int i = 0;
    for (Map<String, Double> map : list) {
      System.out.println(dataSet.get(i));
      System.out.println(map);
      i++;
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
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="bestRouteUpdate")
  @ResponseBody
  public String bestRouteUpdate(@RequestParam("data") org.json.JSONArray data ){
    try {
      routeService.bestRoute(data);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
   return new Gson().toJson(result); 
  }
} // SchedulerController
