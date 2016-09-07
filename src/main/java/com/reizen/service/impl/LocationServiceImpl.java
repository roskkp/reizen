package com.reizen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.reizen.dao.LocationDao;
import com.reizen.domain.Location;
import com.reizen.service.LocationService;
import com.reizen.util.HttpSend;

@Service
public class LocationServiceImpl implements LocationService {

  @Autowired
  LocationDao locationDao;
  
  Map<String, Object> result = new HashMap<>();
  
  @Override
  public void insertLocation(Location location) {
    locationDao.insertLocation(location);
  }
  
  @Override
  public Map<String, Object> selectLocations(Map<String, Object> params){
    Map<String, Object> result = new HashMap<>();
    try {
      result.put("data", locationDao.selectLocations(params));
    } catch (Exception e) {
      result.put("data", "noData");
      e.printStackTrace();
    }
    return result;
  }
  
  @Override
  public Map<String, Object> detailSelect(String path, int contentId) {
    try {
      result.put("data", HttpSend.getSend(path));
      Location location = locationDao.detailSelect(contentId);
      result.put("subData", location);
      Map<String, Object> params = new HashMap<>();
      params.put("lat", location.getMapY());
      params.put("lon", location.getMapX());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  
  @Override
  public Map<String, Object> around(String lat, String lon, String tid) {
    Map<String, Object> params = new HashMap<>();
    params.put("lat", lat);
    params.put("lon", lon);
    params.put("tid", tid);
    try {
      result.put("data", locationDao.selectAround(params));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void insertCateL(Map<String, Object> params) {
    locationDao.insertCateL(params);
  }

  @Override
  public List<String> selectCateL() {
    return locationDao.selectCateL();
  }

  @Override
  public void updateLocation(Location location) {
    locationDao.updateLocation(location);
  }

  @Override
  public List<String> selectArea(String value) {
    return locationDao.selectArea(value);
  }

  @Override
  public List<String> selectCity(String value) {
    return locationDao.selectCity(value);
  }

  @Override
  public void insertLcdd(Map<String, Object> params) {
    locationDao.insertLcdd(params);
  }

	@Override
	public Map<String, Object> statusCheck(String nick, int cid) {
		Map<String, Object> map = new HashMap<>();
		map.put("nick", nick);
		map.put("cid",cid);
		Map<String, Object> rsMap = new HashMap<>();
		if(locationDao.scrapCheck(map)>=1){
			rsMap.put("scrap", "checked");
		}else{
			rsMap.put("scrap", "unChecked");
		}
		if(locationDao.recmCheck(map)>=1){
			rsMap.put("recm", "checked");
		}else{
			rsMap.put("recm", "unChecked");
		}
		return rsMap;
	}

	@Override
	public void addRecm(String nick, int cid) {
		Map<String, Object> map = new HashMap<>();
		map.put("nick", nick);
		map.put("cid",cid);
		locationDao.insertRecm(map);
		map.put("type", "addRecm");
		locationDao.updateCount(map);
	}

	@Override
	public void removeRecm(int cid) {
		Map<String, Object> map = new HashMap<>();
		map.put("cid",cid);
		locationDao.deleteRecm(map);
		map.put("type", "delRecm");
		locationDao.updateCount(map);
	}

	@Override
	public void addScrap(String nick, int cid) {
		Map<String, Object> map = new HashMap<>();
		map.put("nick", nick);
		map.put("cid",cid);
		locationDao.insertScrap(map);
		map.put("type", "addScrap");
		locationDao.updateCount(map);
	}

	@Override
	public void removeScrap(int cid) {
		Map<String, Object> map = new HashMap<>();
		map.put("cid",cid);
		locationDao.deleteScrap(map);
		map.put("type", "delScrap");
		locationDao.updateCount(map);
	}

  @Override
  public List<Location> selectSpotScraps(int userNo) {
    return locationDao.selectScrapList(userNo);
  }
  @Override
  public List<String> autoKeyword(String keyword, String areaCode, String localCode, @RequestParam(value="cateS", defaultValue = "") List<Object> cateS) {
    Map<String, Object> params = new HashMap<>();
    params.put("keyword", keyword);
    params.put("areaCode", areaCode);
    params.put("localCode", localCode);
    params.put("cateS", cateS);
    params.put("cateSSize", cateS.size());
    return locationDao.autoKeyword(params);
  }

  @Override
  public List<Location> selectAroundList(String lat, String lon, String tid, int size, int page) {
    Map<String, Object> params = new HashMap<>();
    params.put("mapX", lon);
    params.put("mapY", lat);
    params.put("tid", tid);
    params.put("size", size);
    params.put("page", (page-1)*size);
    return locationDao.selectAroundList(params);
  }

  @Override
  public int countAroundList(String lat, String lon, String tid) {
    Map<String, Object> params = new HashMap<>();
    params.put("mapX", lon);
    params.put("mapY", lat);
    params.put("tid", tid);
    return locationDao.countAroundList(params);
  }
	
}
