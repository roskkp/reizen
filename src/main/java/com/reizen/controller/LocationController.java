package com.reizen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reizen.domain.Location;
import com.reizen.domain.Memo;
import com.reizen.domain.User;
import com.reizen.service.LocationService;
import com.reizen.service.MemoService;
import com.reizen.service.ScheduleService;
import com.reizen.util.CalculateTime;

@Controller
@RequestMapping("/location/")
public class LocationController {

  @Autowired
  LocationService locationService;
  @Autowired
  MemoService memoService;
  @Autowired
  ScheduleService scheduleService;

  @RequestMapping(path="insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
  @ResponseBody
  public String insert(@RequestBody Location location){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      locationService.insertLocation(location);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="searchkeyword", method=RequestMethod.POST , produces="application/json;charset=utf-8")
  @ResponseBody
  public String searchKeyword(String keyword, String areaCode, String localCode, String category, String date, int page, int size, @RequestParam(value="cateS", defaultValue = "") List<Object> cateS, @RequestParam(value="cateL", defaultValue = "") List<Object> cateL){
    Map<String, Object> params = new HashMap<>();
    params.put("keyword", keyword);
    params.put("areaCode", areaCode);
    params.put("localCode", localCode);
    params.put("category", category);
    params.put("cateS", cateS);
    params.put("cateL", cateL);
    params.put("cateSSize", cateS.size());
    params.put("cateLSize", cateL.size());
    params.put("date", date);
    params.put("startNo", (page-1)*size);
    params.put("size", size);
    System.out.println(params);
    Map<String, Object> result = new HashMap<>();
    try {
      result = locationService.selectLocations(params);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  } 
  
  @RequestMapping(path="searchareacode", method=RequestMethod.POST , produces="application/json;charset=utf-8")
  @ResponseBody
  public String searchAreaCode(String value){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      List<String> data = locationService.selectArea(value);
      List<String> areaData = locationService.selectCity(value);
      for (int j = 0; j < areaData.size(); j++) {
        for (int i = 0; i < data.size(); i++) {
          if (data.get(i).split("/")[0].equals(areaData.get(j).split("/")[0])) {
            data.add(i, areaData.get(j));
            break;
          }
        } 
      }
      result.put("data", data);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  } 
  
  @RequestMapping(path="searchKeywordAuto", method=RequestMethod.POST , produces="application/json;charset=utf-8")
  @ResponseBody
  public String searchKeywordAuto(String keyword, String areaCode, String localCode, @RequestParam(value="cateS", defaultValue = "") List<Object> cateS){
    System.out.println("keyword "+keyword+" / areaCode "+areaCode+" / localCode "+localCode+" / cateS "+cateS);
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      List<String> data = locationService.autoKeyword(keyword,areaCode,localCode,cateS);
      result.put("data", data);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  } 

  @RequestMapping(path="update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
  @ResponseBody
  public String update(@RequestBody Location location){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      locationService.updateLocation(location);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="searchDetail", produces="application/json;charset=utf-8")
  @ResponseBody
  public String searchDetail(String path, int contentId){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      result = locationService.detailSelect(path, contentId);
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="statusCheck", produces="application/json;charset=utf-8")
  @ResponseBody
  public String statusCheck(String nick, int cid){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
    	result=locationService.statusCheck(nick, cid);
    	result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }
  
	@RequestMapping(path = "addRecm", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addRecm(String nick, int cid) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			locationService.addRecm(nick, cid);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "delRecm", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String delRecm(int cid) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			locationService.removeRecm(cid);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "addScrap", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addScrap(String nick, int cid) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			locationService.addScrap(nick, cid);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "delScrap", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String delScrap(int cid) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			locationService.removeScrap(cid);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "getMemo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getMemo(int cid) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Memo> list = memoService.getMemoList(cid);
			int i = 0;
			for (Memo memo : list) {
				memo.setDateAgo(CalculateTime.calc(memo.getRegDate()));
				list.set(i, memo);
				i++;
			}
			result.put("data",list);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "writeMemo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String writeMemo(int cid,int dsno,String content) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			memoService.writeMemo(cid, dsno, content);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "removeMemo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeMemo(int mno) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			memoService.removeMemo(mno);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "updateMemo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateMemo(int mno,String content) {
    Map<String, Object> result = new HashMap<String, Object>();
		try {
			memoService.updateMemo(mno,content);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "failure");
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(path = "around", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String around(String mapX, String mapY, String tid) {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      result = locationService.around(mapY, mapX, tid);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
	}
  
  @RequestMapping(path="scrapSpotList", produces="application/json; charset=UTF-8")
  @ResponseBody
  public String scrapSpotList(HttpSession session){
    Map<String,Object> result = new HashMap<String,Object>();
    try {
      int userNo = ((User)session.getAttribute("user")).getUserNo();
      result.put("data", locationService.selectSpotScraps(userNo));
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path = "aroundList", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String aroundList(String mapX, String mapY, String tid, int size, int page) {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      result.put("data", locationService.selectAroundList(mapY, mapX, tid, size, page));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="countAround")
  @ResponseBody
  public String countAround(String mapX, String mapY, String tid){
    System.out.println(tid);
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      result.put("count", locationService.countAroundList(mapY, mapX, tid));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="updateMemoAlarm")
  @ResponseBody
  public String updateMemoAlarm(int cid){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      memoService.insertMemoAlarm(cid);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="checkAlarm")
  @ResponseBody
  public String checkAlarm(@RequestParam("data") String routeNumbers){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      if (!routeNumbers.equals("[]")) {
        result.put("data", memoService.checkAlarm(routeNumbers.replace("[", " ").replace("]", " ")));  
      }
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="clearMemoAlarm")
  @ResponseBody
  public String clearMemoAlarm(HttpSession session, int routeNo){
    Map<String, Object> result = new HashMap<String, Object>();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("rno", routeNo);
    params.put("uno", ((User)session.getAttribute("user")).getUserNo());
    try {
      memoService.deleteMemoAlarm(params);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
}