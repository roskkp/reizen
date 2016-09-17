package com.reizen.dao;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Post;

public interface PostscriptDao {

	public List<Object> postscript(int routeNo);

	public List<Object> userPost(int scheduleNo);
  
  public int addPost(Post post);
  
  public int updatePicture(Map<String, Object> params);
  
  public List<Object> selectPicture(int routeNo);
  
  public  int checkPostscript(int scheduleNo);
  
  public int sscrs(int userNo,int scheduleNo);
  
  public int deletePicts(int pictureNo);
  
  public Integer postNoSelect(int routeNo);
  
  public int addPicture(Map<String, Object> parms);

}
