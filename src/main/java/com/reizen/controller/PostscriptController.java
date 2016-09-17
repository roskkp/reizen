package com.reizen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        System.out.println("회원 불일치");
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
      result.put("status", postscriptService.sscrs(userNo, scheduleNo));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path = "deletePicts", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String delRecm(int pictureNo) {
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
}
