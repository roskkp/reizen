package com.reizen.service.impl;

import java.util.ArrayList;
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
  
  @Override
  public void insertLocation(Location location) {
    locationDao.insertLocation(location);
  }
  
  @Override
  public List<Location> selectLocations(String keyword, String areaCode, String localCode, String category, String date, int page, int size, @RequestParam(value="cateS", defaultValue = "") List<Object> cateS, @RequestParam(value="cateL", defaultValue = "") List<Object> cateL){
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
    return locationDao.selectLocations(params);
  }
  
  @Override
  public Map<String, Object> detailSelect(String path, int contentId) {
    Map<String, Object> result = new HashMap<>();
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
  public List<Location> around(String lat, String lon, String tid) {
    Map<String, Object> params = new HashMap<>();
    params.put("lat", lat);
    params.put("lon", lon);
    params.put("tid", tid);
    return locationDao.selectAround(params);
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
  public List<String> selectAreaCity(String value) {
    List<String> data = locationDao.selectArea(value);
    List<String> areaData = locationDao.selectCity(value);
    for (int j = 0; j < areaData.size(); j++) {
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).split("/")[0].equals(areaData.get(j).split("/")[0])) {
          data.add(i, areaData.get(j));
          break;
        }
      } 
    }
    return data;
  }

  @Override
  public void insertLcdd(Map<String, Object> params) {
    locationDao.insertLcdd(params);
  }

	@Override
	public Map<String, Object> statusCheck(String nick, int cid) {
		Map<String, Object> params = new HashMap<>();
		params.put("nick", nick);
		params.put("cid",cid);
		Map<String, Object> result = new HashMap<>();
		if(locationDao.scrapCheck(params)>=1){
			result.put("scrap", "checked");
		}else{
			result.put("scrap", "unChecked");
		}
		if(locationDao.recmCheck(params)>=1){
			result.put("recm", "checked");
		}else{
			result.put("recm", "unChecked");
		}
		return result;
	}

	@Override
	public void addRecm(String nick, int cid) {
		Map<String, Object> params = new HashMap<>();
		params.put("nick", nick);
		params.put("cid",cid);
		locationDao.insertRecm(params);
		params.put("type", "addRecm");
		locationDao.updateCount(params);
	}

	@Override
	public void removeRecm(int cid) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid",cid);
		locationDao.deleteRecm(params);
		params.put("type", "delRecm");
		locationDao.updateCount(params);
	}

	@Override
	public void addScrap(String nick, int cid) {
		Map<String, Object> params = new HashMap<>();
		params.put("nick", nick);
		params.put("cid",cid);
		locationDao.insertScrap(params);
		params.put("type", "addScrap");
		locationDao.updateCount(params);
	}

	@Override
	public void removeScrap(int cid) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid",cid);
		locationDao.deleteScrap(params);
		params.put("type", "delScrap");
		locationDao.updateCount(params);
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
  public List<Location> selectAroundList(String mapX, String mapY, String tid, int size, int page) {
    List<Location> list = new ArrayList<>();
    Map<String, Object> params = new HashMap<>();
    if(mapX.length()!=14){
      mapX=mapX.concat("0");
    }
    if(mapY.length() != 13){
      mapY=mapY.concat("0");
    }
    params.put("mapX", mapX);
    params.put("mapY", mapY);
    list.add(locationDao.selectLocationByMap(params));
    params.put("tid", tid);
    params.put("size", size);
    params.put("page", (page-1)*size);
    list.addAll(locationDao.selectAroundList(params));
    return list;
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
