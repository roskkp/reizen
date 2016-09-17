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
      List<MultipartFile> images, HttpServletRequest request) {
    int pric = Integer.parseInt(price);
    Map<String, Object> result = new HashMap<String, Object>();
    Map<String, Object> params = new HashMap<String, Object>();
    try {
      Integer postNo = postscriptDao.postNoSelect(scheduleNo);
      System.out.println("postNo check :: "+postNo);
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
  public void sscrs(int userNo,int scheduleNo) {
    Map<String, Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("scheduleNo",scheduleNo);
    postscriptDao.sscrs(params);
    params.put("type", "addScrap");
    postscriptDao.updateCount(params);
  }

  @Override
  public void srecm(int userNo,int scheduleNo) {
    Map<String, Object> params = new HashMap<>();
    params.put("userNo", userNo);
    params.put("scheduleNo",scheduleNo);
    postscriptDao.srecm(params);
    params.put("type", "addRecm");
    postscriptDao.updateCount(params);
  }

  @Override
  public int updateCount(Map<String, Object> parmas) {
    return postscriptDao.updateCount(parmas);
  }

  @Override
  public void deleteRecm(int scheduleNo) {
    Map<String, Object> params = new HashMap<>();
    params.put("scheduleNo",scheduleNo);
    postscriptDao.deleteRecm(params);
    params.put("type", "delRecm");
    postscriptDao.updateCount(params);
  }

  @Override
  public Map<String, Object> checkRecm(int userNo, int scheduleNo) {
    Map<String, Object> params = new HashMap<>();
    params.put("scheduleNo", scheduleNo);
    params.put("userNo", userNo);
    Map<String, Object> result = new HashMap<>();
    if(postscriptDao.checkScrap(params)>=1){
      result.put("scrap", "checked");
    }else{
      result.put("scrap", "unChecked");
    }
    if(postscriptDao.checkRecm(params)>=1){
      result.put("recm", "checked");
    }else{
      result.put("recm", "unChecked");
    }
    return result;
  }

  @Override
  public void deleteScrap(int scheduleNo) {
    Map<String, Object> params = new HashMap<>();
    params.put("scheduleNo",scheduleNo);
    postscriptDao.deleteScrap(params);
    params.put("type", "delScrap");
    postscriptDao.updateCount(params);
  }

  @Override
  public void addFile(MultipartFile file, HttpServletRequest request, String type, int scheduleNo,
      int routeNo, String content, int transportation, int price, Post post) {
    Map<String, Object> parmas = new HashMap<String, Object>();
    Integer postNo = postscriptDao.postNoSelect(scheduleNo);
    if (postNo == null) {
      postscriptDao.addPost(post);
      postNo = post.getPostNo();
      parmas.put("postNo", postNo);
      parmas.put("scheduleNo", scheduleNo);
      try {
        if (!file.isEmpty()) {
          String originalFileName = file.getOriginalFilename();
          originalFileName = CommonUtils.getRandomString()
              +"."+ request.getParameter("type");
          FileCopyUtils.copy(file.getBytes(),
              new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                  + "/resources/images/viewSchedule/" + originalFileName));
          parmas.put("picturePath", originalFileName);
        } else {
          parmas.put("picturePath", "");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      parmas.put("content", content);
      parmas.put("routeNo", routeNo);
      parmas.put("transportation", transportation);
      parmas.put("price", price);
      postscriptDao.addPicture(parmas);
    } else {
      parmas.put("postNo", postNo);
      parmas.put("scheduleNo", scheduleNo);
      try {
        if (!file.isEmpty()) {
          String originalFileName = file.getOriginalFilename();
          originalFileName = CommonUtils.getRandomString()
              +"."+ request.getParameter("type");
          FileCopyUtils.copy(file.getBytes(),
              new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                  + "/resources/images/viewSchedule/" + originalFileName));
          parmas.put("picturePath", originalFileName);
        } else {
          parmas.put("picturePath", "");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      parmas.put("content", content);
      parmas.put("routeNo", routeNo);
      parmas.put("transportation", transportation);
      parmas.put("price", price);
      postscriptDao.addPicture(parmas);
    }
  }

  @Override
  public void updateFile(MultipartFile file, HttpServletRequest request, String type, int scheduleNo, int routeNo,
      String content, int transportation, int price) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("routeNo", routeNo);
    params.put("content", content);
    params.put("transportation", transportation);
    params.put("price", price);
    try {
      if (!file.isEmpty()) {
        String originalFileName = file.getOriginalFilename();
        originalFileName = CommonUtils.getRandomString()
            +"."+ request.getParameter("type");
        FileCopyUtils.copy(file.getBytes(),
            new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                + "/resources/images/viewSchedule/" + originalFileName));
        params.put("picturePath", originalFileName);
      } else {
        params.put("picturePath", "");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    }
    postscriptDao.updatePicture(params);
  }
  
  
}
