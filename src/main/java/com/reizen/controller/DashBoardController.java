package com.reizen.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reizen.service.DashBoardService;

@Controller
@RequestMapping("/dashboard/")
public class DashBoardController {
	
	@Autowired
	DashBoardService dashBoardService;
	
	@RequestMapping(path = "getDash", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getDash(int boardNo) {
		HashMap<String, Object> result = new HashMap<>();
		try {
			result.put("user", dashBoardService.getDashBoard(boardNo));
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "planlist", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String planList(int boardNo) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("list",dashBoardService.getPlanList(boardNo));
			result.put("dataType", "plan");
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "failure");
			e.printStackTrace();
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "scplanlist", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String scPlanList(int boardNo) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("list", dashBoardService.getScrapPlanList(boardNo));
			result.put("dataType", "scrPlan");
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "failure");
			e.printStackTrace();
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "sclocationlist", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String scLocationList(int boardNo) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result.put("list", dashBoardService.getScrapLocationList(boardNo));
			result.put("dataType", "scrLocation");
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "failure");
			e.printStackTrace();
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "removeplan", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String removePlan(int scdNo) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		if(scdNo == 0){
			result.put("status", "success");
			return new Gson().toJson(result);
		}
		try {
			dashBoardService.removePlan(scdNo);
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "failure");
			e.printStackTrace();
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "removescp", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String removeScp(int scdNo) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			dashBoardService.removeScp(scdNo);
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "failure");
			e.printStackTrace();
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "removescl", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String removeScl(int contentId) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			dashBoardService.removeScl(contentId);
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "failure");
			e.printStackTrace();
		}
		return new Gson().toJson(result);
	}
	
}
