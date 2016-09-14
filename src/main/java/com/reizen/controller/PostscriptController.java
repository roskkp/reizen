package com.reizen.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.reizen.domain.Post;
import com.reizen.domain.User;
import com.reizen.service.PostscriptService;
import com.reizen.util.CommonUtils;

@Controller
@RequestMapping("/postscript/")
public class PostscriptController {

  @Autowired
  PostscriptService postscriptService;

  @RequestMapping(path = "postscript", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String getDataset(int scheduleNo) {
    Map<String, Object> data = new HashMap<String, Object>();
    try {
      System.out.println("scheduleNo::"+scheduleNo);
      data.put("list", postscriptService.postscript(scheduleNo));
      data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    System.out.println(new Gson().toJson(data));
    return new Gson().toJson(data);
  }

  
  @RequestMapping(path = "checkPostscript", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String check(@RequestParam int scheduleNo, HttpSession session) {
    Map<String, Object> data = new HashMap<String, Object>();
    int userNo = ((User) session.getAttribute("user")).getUserNo();
    try {
      
      int resultUserNo = postscriptService.checkPostscript(scheduleNo);
      if(resultUserNo == userNo){
        data.put("pass","right");
      }else{
        System.out.println("회원 불일치");
        data.put("pass", "false");
      }
      data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    System.out.println(new Gson().toJson(data));
    return new Gson().toJson(data);
  }

  @RequestMapping(path="sscrs",  produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String sscrs(int scheduleNo,HttpSession session){
    Map<String, Object> data = new HashMap<String, Object>();
    try {
      int userNo = ((User) session.getAttribute("user")).getUserNo();
      postscriptService.sscrs(userNo,scheduleNo);
      data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    return new Gson().toJson(data);
  }
  
  @RequestMapping(path = "checkRecm", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String checkRecm(int scheduleNo,HttpSession session) {
    Map<String, Object> data = new HashMap<String, Object>();
   
    int userNo = ((User) session.getAttribute("user")).getUserNo();
    System.out.println("userNO::::"+userNo);
    System.out.println();
  
    try {
      data = postscriptService.checkRecm(userNo,scheduleNo);
      data.put("data",  postscriptService.checkRecm(userNo,scheduleNo));
      data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    System.out.println("select::::::"+new Gson().toJson(data));
    return new Gson().toJson(data);
  }

  
  
  @RequestMapping(path="srecm",  produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String srecm(int scheduleNo,HttpSession session){
    Map<String, Object> data = new HashMap<String, Object>();
    try {
    System.out.println("scheduleNo :::::"+scheduleNo);
      int userNo = ((User) session.getAttribute("user")).getUserNo();
  
      postscriptService.srecm(userNo,scheduleNo);
       data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    return new Gson().toJson(data);
  }
  

  @RequestMapping(path = "deleteRecm", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String deleteRecm(int scheduleNo,HttpSession session) {
    Map<String, Object> data = new HashMap<String, Object>();

    try {
      postscriptService.deleteRecm(scheduleNo);
      data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    return new Gson().toJson(data);
  }
  @RequestMapping(path = "deleteScrap", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String deleteScrap(int scheduleNo) {
    Map<String, Object> data = new HashMap<String, Object>();
 
    try {
      System.out.println("delete NO"+scheduleNo);
      
      postscriptService.deleteScrap(scheduleNo);
      data.put("status", "success");
      System.out.println("data:::::::"+data);
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    return new Gson().toJson(data);
  }
  

  
  
  
  @RequestMapping(path = "deletePicts", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String delRecm(int pictureNo) {
    Map<String, Object> data = new HashMap<String, Object>();
    try {
      System.out.println("delete NO"+pictureNo);
      postscriptService.deletePicts(pictureNo);
      data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    return new Gson().toJson(data);
  }
  
  @RequestMapping(path = "userPost", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String userPost(int scheduleNo) {
    Map<String, Object> data = new HashMap<String, Object>();
    try {
      data.put("data", postscriptService.userPost(scheduleNo));
      data.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    return new Gson().toJson(data);
  }

  @RequestMapping(path="selectPict", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String selectPict(int routeNo){
    Map<String, Object> data = new HashMap<String, Object>();
      System.out.println("루트넘버"+routeNo);
    try{
      data.put("data", postscriptService.selectPicture(routeNo));
      data.put("status", "success");
      System.out.println(postscriptService.selectPicture(routeNo));
    }catch(Exception e){
      e.printStackTrace();
      data.put("status", "failure");
      
    }
    return new  Gson().toJson(data);
  }
  
  
  
  
  
  @RequestMapping(path = "updatePost", method = RequestMethod.POST)
  @ResponseBody
  public String updatePost(int routeNo,String content, String transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request) {
    System.out.println( "라우트 넘버" + routeNo + "콘텐츠 " + content + "이동 수단 " + transportation + "가격 " + price);

    int pric = Integer.parseInt(price);
    Map<String, Object> data = new HashMap<String, Object>();
    Map<String, Object> parms = new HashMap<String, Object>();

    try {
      parms.put("routeNo", routeNo);
      parms.put("content", content);
      parms.put("transportation", transportation);
      parms.put("price", pric);
        if (!images.isEmpty()) {
          MultipartFile file = images.get(0);
          String originalFileName = file.getOriginalFilename();
          System.out.println("filePath" + file.getOriginalFilename());

          if (originalFileName.contains(".")) {
            originalFileName = CommonUtils.getRandomString()
                + originalFileName.substring(originalFileName.lastIndexOf("."));
            FileCopyUtils.copy(file.getBytes(),
                new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                    + "/resources/images/viewSchedule/" + originalFileName));
          }
          parms.put("picturePath", originalFileName);
        }else{
          parms.put("picturePath", "");
        }
        data.put("parms", postscriptService.updatePicture(parms));
        data.put("status", "success");
        System.out.println(parms);
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    System.out.println(new Gson().toJson(data));
    return new Gson().toJson(data);
  }

  
  
  
  
  
  @RequestMapping(path = "addPost", method = RequestMethod.POST)
  @ResponseBody
  public String addPost(Post post, int scheduleNo, int routeNo, String content, int transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request) {

    System.out.println(
        "스케줄 넘버 " + scheduleNo + "라우트 넘버" + routeNo + "콘텐츠 " + content + "이동 수단 " + transportation + "가격 " + price);

    int pric = Integer.parseInt(price);
    Map<String, Object> data = new HashMap<String, Object>();
    Map<String, Object> parmas = new HashMap<String, Object>();

    try {

      if (postscriptService.postSelect(scheduleNo)==null) {

        System.out.println(postscriptService.postSelect(scheduleNo));
        data.put("data", postscriptService.addPost(post));
        postscriptService.postSelect(scheduleNo);
        parmas.put("postNo", postscriptService.postSelect(scheduleNo).getPostNo());
        parmas.put("scheduleNo", scheduleNo);
        if (!images.isEmpty()) {
          MultipartFile file = images.get(0);
          String originalFileName = file.getOriginalFilename();
          System.out.println("filePath" + file.getOriginalFilename());

          if (originalFileName.contains(".")) {
            originalFileName = CommonUtils.getRandomString()
                + originalFileName.substring(originalFileName.lastIndexOf("."));
            System.out.println("사진경로 : "+request.getSession().getServletContext().getRealPath("/"));
            FileCopyUtils.copy(file.getBytes(),
                new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                    + "/resources/images/viewSchedule/" + originalFileName));
            
          }
          System.out.println("파일이름"+originalFileName);
          parmas.put("picturePath", originalFileName);
        } else {
          parmas.put("picturePath", "");
        }
        parmas.put("content", content);
        parmas.put("routeNo", routeNo);
        parmas.put("transportation", transportation);
        parmas.put("price", pric);

        data.put("parms", postscriptService.addPicture(parmas));
        data.put("status", "success");
      } else {
        System.out.println("여기여기");

        postscriptService.postSelect(scheduleNo);
        parmas.put("postNo", postscriptService.postSelect(scheduleNo).getPostNo());
        parmas.put("scheduleNo", scheduleNo);
        if (!images.isEmpty()) {
          MultipartFile file = images.get(0);
          String originalFileName = file.getOriginalFilename();
          System.out.println("filePath" + file.getOriginalFilename());
          if (originalFileName.contains(".")) {
            System.out.println("여기들어왓나여?");
            originalFileName = CommonUtils.getRandomString()
                + originalFileName.substring(originalFileName.lastIndexOf("."));
            System.out.println("멀까ㅕㅇ:" + originalFileName);
            FileCopyUtils.copy(file.getBytes(),
                new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                    + "/resources/images/viewSchedule/" + originalFileName));
          }
          System.out.println("파일이름"+originalFileName);
          parmas.put("picturePath", originalFileName);
        } else {
          parmas.put("picturePath", "");
        }
        parmas.put("content", content);
        parmas.put("routeNo", routeNo);
        parmas.put("transportation", transportation);
        parmas.put("price", pric);

        data.put("parms", postscriptService.addPicture(parmas));
        data.put("status", "success");
      }

    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    System.out.println("addPost"+new Gson().toJson(data));
    return new Gson().toJson(data);
  }

  @RequestMapping(path="addFile")
  @ResponseBody
  public String addFile(@RequestPart(required=false) MultipartFile file, HttpServletRequest request, @RequestParam("type") String type,
      @RequestParam("scheduleNo") int scheduleNo, @RequestParam("routeNo") int routeNo,
      @RequestParam("content") String content, @RequestParam("transportation") int transportation, 
      @RequestParam("price") int price, Post post) throws Exception{      
      
      System.out.println(
          "스케줄 넘버 " + scheduleNo + "라우트 넘버" + routeNo + "콘텐츠 " + content + "이동 수단 " + transportation + "가격 " + price);

      Map<String, Object> data = new HashMap<String, Object>();
      Map<String, Object> parmas = new HashMap<String, Object>();

      try {
        
        if (postscriptService.postSelect(scheduleNo) == null) {

          System.out.println(postscriptService.postSelect(scheduleNo));
          data.put("data", postscriptService.addPost(post));
          postscriptService.postSelect(scheduleNo);
          parmas.put("postNo", postscriptService.postSelect(scheduleNo).getPostNo());
          parmas.put("scheduleNo", scheduleNo);
          if (!file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            originalFileName = CommonUtils.getRandomString()
                +"."+ request.getParameter("type");
            FileCopyUtils.copy(file.getBytes(),
                new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                    + "/resources/images/viewSchedule/" + originalFileName));
          
            System.out.println("파일 이름 : "+originalFileName);
            parmas.put("picturePath", originalFileName);
          } else {
            parmas.put("picturePath", "");
          }
          parmas.put("content", content);
          parmas.put("routeNo", routeNo);
          parmas.put("transportation", transportation);
          parmas.put("price", price);

          data.put("parms", postscriptService.addPicture(parmas));
          data.put("status", "success");
          
        } else {

          postscriptService.postSelect(scheduleNo);
          parmas.put("postNo", postscriptService.postSelect(scheduleNo).getPostNo());
          parmas.put("scheduleNo", scheduleNo);
          if (!file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            originalFileName = CommonUtils.getRandomString()
                +"."+ request.getParameter("type");
            FileCopyUtils.copy(file.getBytes(),
                new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                    + "/resources/images/viewSchedule/" + originalFileName));
          
            System.out.println("파일 이름 : "+originalFileName);
            parmas.put("picturePath", originalFileName);
          } else {
            parmas.put("picturePath", "");
          }
          parmas.put("content", content);
          parmas.put("routeNo", routeNo);
          parmas.put("transportation", transportation);
          parmas.put("price", price);

          data.put("parms", postscriptService.addPicture(parmas));
          data.put("status", "success");
        }

      } catch (Exception e) {
        e.printStackTrace();
        data.put("status", "failure");
      }
      System.out.println("addPost"+new Gson().toJson(data));
      return new Gson().toJson(data);
  }

  @RequestMapping(path = "updateFile", method = RequestMethod.POST)
  @ResponseBody
  public String updateFile(@RequestPart(required=false) MultipartFile file, HttpServletRequest request, @RequestParam("type") String type,
      @RequestParam("scheduleNo") int scheduleNo, @RequestParam("routeNo") int routeNo,
      @RequestParam("content") String content, @RequestParam("transportation") int transportation, 
      @RequestParam("price") int price) throws Exception{    
    
    System.out.println( "라우트 넘버" + routeNo + "콘텐츠 " + content + "이동 수단 " + transportation + "가격 " + price);

    Map<String, Object> data = new HashMap<String, Object>();
    Map<String, Object> parms = new HashMap<String, Object>();

    try {
      parms.put("routeNo", routeNo);
      parms.put("content", content);
      parms.put("transportation", transportation);
      parms.put("price", price);
      if (!file.isEmpty()) {
        String originalFileName = file.getOriginalFilename();
        originalFileName = CommonUtils.getRandomString()
            +"."+ request.getParameter("type");
        FileCopyUtils.copy(file.getBytes(),
            new FileOutputStream(request.getSession().getServletContext().getRealPath("/")
                + "/resources/images/viewSchedule/" + originalFileName));
      
        System.out.println("파일 이름 : "+originalFileName);
        parms.put("picturePath", originalFileName);
      } else {
        parms.put("picturePath", "");
      }
        data.put("parms", postscriptService.updatePicture(parms));
        data.put("status", "success");
        System.out.println(parms);
    } catch (Exception e) {
      e.printStackTrace();
      data.put("status", "failure");
    }
    System.out.println(new Gson().toJson(data));
    return new Gson().toJson(data);
  }


} // controller
