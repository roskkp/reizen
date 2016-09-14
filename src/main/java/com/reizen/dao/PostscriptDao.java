package com.reizen.dao;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Picture;
import com.reizen.domain.Post;
import com.reizen.domain.ScheduleScrap;

public interface PostscriptDao {

	public List<Object> postscript(int routeNo);

	public List<Object> userPost(int scheduleNo);
  
  public int addPost(Post post);
  
  public int updatePicture(Map<String, Object> params);
  
  public List<Object> selectPicture(int routeNo);
  
  public  int checkPostscript(int scheduleNo);
  
  public void sscrs(Map<String, Object> parmas);
  
  public void srecm(Map<String, Object> parmas);
  
  public void deleteRecm(Map<String, Object> params);
  
  public void deleteScrap(Map<String, Object> params);
  
  public int updateCount(Map<String, Object> parmas);
  
  public int deletePicts(int pictureNo);
  
  public Post postSelect(int routeNo);
  
  public int checkRecm(Map<String, Object> map);
  
  public int checkScrap(Map<String, Object> map);
  
  public int addPicture(Map<String, Object> parms);

}
