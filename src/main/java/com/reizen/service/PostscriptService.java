package com.reizen.service;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Post;
import com.reizen.domain.ScheduleScrap;

public interface PostscriptService {
  public List<Object> postscript(int scheduleNo);
  public List<Object> userPost(int scheduleNo);
  public int addPost(Post post);
  public int updatePicture(Map<String, Object> parmas);
  public int addPicture(Map<String, Object> parmas);
  public List<Object> selectPicture(int routeNo);
  public Post postSelect(int scheduleNo);
  public int checkPostscript(int scheduleNo);
  public int updateCount(Map<String, Object> parmas);
  public int deletePicts(int pictureNo);
  public void deleteRecm(int scheduleNo);
  public void deleteScrap(int scheduleNo);
  public Map<String, Object> checkRecm(int scheduleNo, int userNo);
  public void sscrs(int userNo, int scheduleNo);
  public void srecm(int userNo, int scheduleNo);
}
