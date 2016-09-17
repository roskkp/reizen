package com.reizen.service.impl;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.reizen.dao.PostscriptDao;
import com.reizen.domain.Post;
import com.reizen.service.PostscriptService;
import com.reizen.util.CommonUtils;


@Service
public class PostscriptServiceImpl implements PostscriptService{

  @Autowired PostscriptDao postscriptDao;
  
  @Override
  public List<Object> postscript(int scheduleNo) {
    return postscriptDao.postscript(scheduleNo);
  }

  @Override
  public List<Object> userPost(int scheduleNo) {
    return postscriptDao.userPost(scheduleNo);
  }

  @Override
  public Map<String, Object> addPost(Post post, int scheduleNo, int routeNo, String content, int transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request) {
    int pric = Integer.parseInt(price);
    Map<String, Object> result = new HashMap<String, Object>();
    Map<String, Object> params = new HashMap<String, Object>();
    try {
      Integer postNo = postscriptDao.postNoSelect(routeNo);
      if (postNo == null) {
        postscriptDao.addPost(post);
        postNo = post.getPostNo();
        params.put("postNo", postNo);
        params.put("scheduleNo", scheduleNo);
        if (!images.isEmpty()) {
          MultipartFile file = images.get(0);
          String originalFileName = file.getOriginalFilename();
          if (originalFileName.contains(".")) {
            originalFileName = CommonUtils.getRandomString()
                + originalFileName.substring(originalFileName.lastIndexOf("."));
            FileCopyUtils.copy(file.getBytes(),
                new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                    + "/resources/images/viewSchedule/" + originalFileName));
          }
          params.put("picturePath", originalFileName);
        } else {
          params.put("picturePath", "");
        }
        params.put("content", content);
        params.put("routeNo", routeNo);
        params.put("transportation", transportation);
        params.put("price", pric);
        postscriptDao.addPicture(params);
        result.put("status", "success");
      } else {
        params.put("postNo", postNo);
        params.put("scheduleNo", scheduleNo);
        if (!images.isEmpty()) {
          MultipartFile file = images.get(0);
          String originalFileName = file.getOriginalFilename();
          if (originalFileName.contains(".")) {
            originalFileName = CommonUtils.getRandomString()
                + originalFileName.substring(originalFileName.lastIndexOf("."));
            FileCopyUtils.copy(file.getBytes(),
                new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                    + "/resources/images/viewSchedule/" + originalFileName));
          }
          params.put("picturePath", originalFileName);
        } else {
          params.put("picturePath", "");
        }
        params.put("content", content);
        params.put("routeNo", routeNo);
        params.put("transportation", transportation);
        params.put("price", pric);
        postscriptDao.addPicture(params);
        result.put("status", "success");
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return result;
  }

  @Override
  public Map<String, Object> updatePicture(int routeNo,String content, String transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request) {
    Map<String, Object> params = new HashMap<String, Object>();
    Map<String, Object> result = new HashMap<String, Object>();
    int pric = Integer.parseInt(price);
    params.put("routeNo", routeNo);
    params.put("content", content);
    params.put("transportation", transportation);
    params.put("price", pric);
    try {
      if (!images.isEmpty()) {
        MultipartFile file = images.get(0);
        String originalFileName = file.getOriginalFilename();
        if (originalFileName.contains(".")) {
          originalFileName = CommonUtils.getRandomString()
              + originalFileName.substring(originalFileName.lastIndexOf("."));
          FileCopyUtils.copy(file.getBytes(),
              new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                  + "/resources/images/viewSchedule/" + originalFileName));
        }
        params.put("picturePath", originalFileName);
      }else{
        params.put("picturePath", "");
      }
      result.put("parms",postscriptDao.updatePicture(params));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return result;
  }

  @Override
  public List<Object> selectPicture(int routeNo) {
    return postscriptDao.selectPicture(routeNo);
  }

  @Override
  public int checkPostscript(int scheduleNo) {
    int checkNo = postscriptDao.checkPostscript(scheduleNo);
    return checkNo;
  }

  @Override
  public int deletePicts(int pictureNo) {
    return postscriptDao.deletePicts(pictureNo);
  }

  @Override
  public int sscrs(int userNo, int scheduleNo) {
    return postscriptDao.sscrs(userNo, scheduleNo);
  }

}
