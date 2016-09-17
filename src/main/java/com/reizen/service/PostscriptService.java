package com.reizen.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.reizen.domain.Post;
import com.reizen.domain.ScheduleScrap;

public interface PostscriptService {
  public List<Object> postscript(int scheduleNo);
  public List<Object> userPost(int scheduleNo);
  public Map<String, Object> addPost(Post post, int scheduleNo, int routeNo, String content, int transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request);
  public Map<String, Object> updatePicture(int routeNo,String content, String transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request);
  public List<Object> selectPicture(int routeNo);
  public int checkPostscript(int scheduleNo);
  public int updateCount(Map<String, Object> parmas);
  public int deletePicts(int pictureNo);
  public void deleteRecm(int scheduleNo);
  public void deleteScrap(int scheduleNo);
  public Map<String, Object> checkRecm(int scheduleNo, int userNo);
  public void sscrs(int userNo, int scheduleNo);
  public void srecm(int userNo, int scheduleNo);
  public void addFile(MultipartFile file, HttpServletRequest request, String type,
      int scheduleNo,int routeNo, String content, int transportation, int price, Post post);
  public void updateFile(MultipartFile file, HttpServletRequest request, String type,
      int scheduleNo, int routeNo, String content, int transportation, int price);
}
