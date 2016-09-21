package com.reizen.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.reizen.domain.Location;

public interface LocationService {
  
	public void insertLocation(Location location);

	public List<Location> selectLocations(String keyword, String areaCode, String localCode, String category, String date, int page, int size, @RequestParam(value="cateS", defaultValue = "") List<Object> cateS, @RequestParam(value="cateL", defaultValue = "") List<Object> cateL);

	public void updateLocation(Location location);

	public Map<String, Object> detailSelect(String path, int contentId);
	
	public List<Location> around(String lat, String lon, String tid);
  
  public List<Location> selectAroundList(String lat, String lon, String tid, int size, int page, int cid);
  
  public int countAroundList(String lat, String lon, String tid);

	public void insertCateL(Map<String, Object> params);

	public List<String> selectCateL();

	public List<String> selectAreaCity(String value);
	
	public void insertLcdd(Map<String, Object> params);

	public Map<String, Object> statusCheck(String nick, int cid);

	public void addRecm(String nick, int cid);

	public void removeRecm(int cid);

	public void addScrap(String nick, int cid);

	public void removeScrap(int cid);
	
	public List<Location> selectSpotScraps(int userNo);

  public List<String> autoKeyword(String keyword, String areaCode, String localCode, @RequestParam(value="cateS", defaultValue = "") List<Object> cateS);
  
}
