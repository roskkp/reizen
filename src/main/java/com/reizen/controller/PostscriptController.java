package com.reizen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

@Controller
@RequestMapping("/postscript/")
public class PostscriptController {

  @Autowired
  PostscriptService postscriptService;

  @RequestMapping(path = "postscript", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String getDataset(int scheduleNo) {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      result.put("list", postscriptService.postscript(scheduleNo));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "checkPostscript", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String check(@RequestParam int scheduleNo, HttpSession session) {
    Map<String, Object> result = new HashMap<String, Object>();
    int userNo = ((User) session.getAttribute("user")).getUserNo();
    try {
      int resultUserNo = postscriptService.checkPostscript(scheduleNo);
      if(resultUserNo == userNo){
        result.put("pass","right");
      }else{
        result.put("pass", "false");
      }
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="sscrs",  produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String sscrs(int scheduleNo,HttpSession session){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      int userNo = ((User) session.getAttribute("user")).getUserNo();
      postscriptService.sscrs(userNo,scheduleNo);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "checkRecm", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String checkRecm(int scheduleNo,HttpSession session) {
    Map<String, Object> result = new HashMap<String, Object>();

    int userNo = ((User) session.getAttribute("user")).getUserNo();


    try {
      result = postscriptService.checkRecm(userNo,scheduleNo);
      result.put("data",  postscriptService.checkRecm(userNo,scheduleNo));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }



  @RequestMapping(path="srecm",  produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String srecm(int scheduleNo,HttpSession session){
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      int userNo = ((User) session.getAttribute("user")).getUserNo();

      postscriptService.srecm(userNo,scheduleNo);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }


  @RequestMapping(path = "deleteRecm", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String deleteRecm(int scheduleNo,HttpSession session) {
    Map<String, Object> result = new HashMap<String, Object>();

    try {
      postscriptService.deleteRecm(scheduleNo);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "deleteScrap", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String deleteScrap(int scheduleNo) {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      postscriptService.deleteScrap(scheduleNo);
      result.put("status", "success");
      result.put("status", "success");
    } catch (Exception e) {
      result.put("status", "failure");
      e.printStackTrace();
    }
    return new Gson().toJson(result);
  }



  @RequestMapping(path = "deletePicts", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String delRecm(int pictureNo) {
    System.out.println("pictureNo : "+pictureNo);
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      postscriptService.deletePicts(pictureNo);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "userPost", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String userPost(int scheduleNo) {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      result.put("data", postscriptService.userPost(scheduleNo));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="selectPict", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String selectPict(int routeNo){
    Map<String, Object> result = new HashMap<String, Object>();
    try{
      result.put("data", postscriptService.selectPicture(routeNo));
      result.put("status", "success");
    }catch(Exception e){
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new  Gson().toJson(result);
  }

  @RequestMapping(path = "updatePost", method = RequestMethod.POST)
  @ResponseBody
  public String updatePost(int routeNo,String content, String transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request) {
    return new Gson().toJson(postscriptService.updatePicture(routeNo, content, transportation, price, images, request));
  }

  @RequestMapping(path = "addPost", method = RequestMethod.POST)
  @ResponseBody
  public String addPost(Post post, int scheduleNo, int routeNo, String content, int transportation, String price,
      @RequestParam(value = "files") List<MultipartFile> images, HttpServletRequest request) {
    return new Gson().toJson(postscriptService.addPost(post, scheduleNo, routeNo, content, transportation, price, images, request));
  }

  @RequestMapping(path="addFile")
  @ResponseBody
  public String addFile(@RequestPart(required=false) MultipartFile file, HttpServletRequest request, @RequestParam("type") String type,
      @RequestParam("scheduleNo") int scheduleNo, @RequestParam("routeNo") int routeNo,
      @RequestParam("content") String content, @RequestParam("transportation") int transportation, 
      @RequestParam("price") int price, Post post) throws Exception{
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      postscriptService.addFile(file, request, type, scheduleNo, routeNo, content, transportation, price, post);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "updateFile", method = RequestMethod.POST)
  @ResponseBody
  public String updateFile(@RequestPart(required=false) MultipartFile file, HttpServletRequest request, @RequestParam("type") String type,
      @RequestParam("scheduleNo") int scheduleNo, @RequestParam("routeNo") int routeNo,
      @RequestParam("content") String content, @RequestParam("transportation") int transportation, 
      @RequestParam("price") int price) throws Exception{    
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      postscriptService.updateFile(file, request, type, scheduleNo, routeNo, content, transportation, price);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

} // controller
