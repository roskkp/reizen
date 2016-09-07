package com.reizen.dao;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Location;

public interface LocationDao {
  
	public void insertLocation(Location location);

	public List<Location> selectLocations(Map<String, Object> params);

	public List<Location> selectAround(Map<String, Object> params);

  public List<Location> selectAroundList(Map<String, Object> params);
  
  public int countAroundList(Map<String, Object> params);

	public Location detailSelect(int contentId);

	public void updateLocation(Location location);

	public void insertCateL(Map<String, Object> params);

	public List<String> selectCateL();

	public List<String> selectArea(String value);

	public List<String> selectCity(String value);

	public void insertLcdd(Map<String, Object> params);

	public int scrapCheck(Map<String, Object> params);

	public int recmCheck(Map<String, Object> params);

	public void insertRecm(Map<String, Object> params);

	public void deleteRecm(Map<String, Object> params);

	public void insertScrap(Map<String, Object> params);

	public void deleteScrap(Map<String, Object> params);

	public void updateCount(Map<String, Object> params);
	
	public List<Location> selectScrapList(int userNo);
	
	public List<String> autoKeyword(Map<String, Object> params);
}
