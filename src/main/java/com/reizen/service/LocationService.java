package com.reizen.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.reizen.domain.Location;

public interface LocationService {
  
	public void insertLocation(Location location);

	public Map<String, Object> selectLocations(Map<String, Object> params);

	public void updateLocation(Location location);

	public Map<String, Object> detailSelect(String path, int contentId);
	
	public Map<String, Object> around(String lat, String lon, String tid);
  
  public List<Location> selectAroundList(String lat, String lon, String tid, int size, int page);
  
  public int countAroundList(String lat, String lon, String tid);

	public void insertCateL(Map<String, Object> params);

	public List<String> selectCateL();

	public List<String> selectArea(String value);

	public List<String> selectCity(String value);

	public void insertLcdd(Map<String, Object> params);

	public Map<String, Object> statusCheck(String nick, int cid);

	public void addRecm(String nick, int cid);

	public void removeRecm(int cid);

	public void addScrap(String nick, int cid);

	public void removeScrap(int cid);
	
	public List<Location> selectSpotScraps(int userNo);

  public List<String> autoKeyword(String keyword, String areaCode, String localCode, @RequestParam(value="cateS", defaultValue = "") List<Object> cateS);
  
  public Location getLocationByMap(String mapY,String mapX);

}
