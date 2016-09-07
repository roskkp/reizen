package com.reizen.service;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Post;

public interface PostscriptService {
  public List<Object> postscript(int scheduleNo);
  public List<Object> userPost(int scheduleNo);
  public int addPost(Post post);
  public int updatePicture(Map<String, Object> parmas);
  public int addPicture(Map<String, Object> parmas);
  public List<Object> selectPicture(int routeNo);
  public Post postSelect(int scheduleNo);
  public int checkPostscript(int scheduleNo);
  public int deletePicts(int pictureNo);
  public int sscrs(int userNo,int scheduleNo);
}
