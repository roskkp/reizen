package com.reizen.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.reizen.domain.Schedule;
import com.reizen.domain.User;
import com.reizen.service.ScheduleService;
import com.reizen.service.UserService;
import com.reizen.util.CommonUtils;

@Controller
@RequestMapping("/user/")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  ScheduleService scheduleService;

  Map<String, Object> result = new HashMap<String, Object>();

  @RequestMapping(value = "signup", method = RequestMethod.POST)
  @ResponseBody
  public String addUser(@ModelAttribute User user,
      @RequestParam("files") List<MultipartFile> images,HttpServletRequest request) {
    
    try {
      String fileName= "empty.png";
      if(!images.isEmpty()){ // 이미지가 있다면 
        MultipartFile mpf= images.get(0);
        String originalFileName = mpf.getOriginalFilename();
        if( originalFileName.contains(".") ){
          fileName = CommonUtils.getRandomString() + originalFileName.substring(originalFileName.lastIndexOf("."));
          FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
              request.getSession().getServletContext().getRealPath("/") + "/resources/images/thumbnail/" + fileName));
        }
      }
      
      user.setThumbNail(fileName);
      userService.addUser(user);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }

    return new Gson().toJson(result);
  }

  @RequestMapping(path = "checkMail", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String checkMail(String email) {
    try {
      if (userService.checkMail(email) == null) {
        result.put("status", "success");
      } else {
        result.put("status", "duplicated");
      }
    } catch (Exception e) {
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }



  @RequestMapping(path = "login", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String checkUser(User user, HttpSession httpSession) {
    try {
      User resultUser = userService.checkUser(user);
      result.put("user", resultUser);
      result.put("activeScheduleNo", scheduleService.activeSchedule(resultUser.getUserNo()));
      httpSession.setAttribute("user", resultUser);
      int totalRecommand = 0;
      int totalScrap = 0;
      List<Schedule> list = scheduleService.totalCount(resultUser.getUserNo());
      for (Schedule sd : list) {
        totalRecommand += sd.getRecommandCount();
        totalScrap += sd.getScrapCount();
      }
      result.put("totalRecommand", totalRecommand);
      result.put("totalScrap", totalScrap);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="updateUserForm", produces="application/json;charset=UTF-8")
  @ResponseBody
  public String getUser(HttpSession httpSession){
    try {
      int no = ((User)httpSession.getAttribute("user")).getUserNo();
      result.put("user", userService.getUser(no));
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="updateUser", produces="application/json; charset=UTF-8", method=RequestMethod.POST)
  @ResponseBody
  public String updateUser(User user, HttpSession httpSession,
      @RequestParam("files") List<MultipartFile> images, HttpServletRequest request) {
    try {
        String fileName= "empty.png";
        if(!images.isEmpty()){ // 이미지가 있다면 
          MultipartFile mpf= images.get(0);
          String originalFileName = mpf.getOriginalFilename();
          if( originalFileName.contains(".") ){
            fileName = CommonUtils.getRandomString() + originalFileName.substring(originalFileName.lastIndexOf("."));
            FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
                request.getSession().getServletContext().getRealPath("/") + "/resources/images/thumbnail/" + fileName));
            user.setThumbNail(fileName);
          }
        }
      
      user.setUserNo(((User)httpSession.getAttribute("user")).getUserNo());
      System.out.println("[회원 정보 수정 debug] : "+userService.updateUser(user));
      User returnUser = userService.getUser(user.getUserNo());
      httpSession.setAttribute("user", returnUser);
      result.put("user", returnUser);
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

  @RequestMapping(path="leaveUser")
  @ResponseBody
  public String leave(HttpSession session, HttpServletRequest request){
    try{
      User user = (User)session.getAttribute("user");
      File image = new File(request.getSession().getServletContext().getRealPath("/")+"resources/images/thumbnail/"+user.getThumbNail());
      if(image.exists()){
        image.delete();
      }
      System.out.println("[회원 탈퇴 debug] : "+userService.deleteUser(user.getUserNo()));
      session.invalidate();
      result.put("status", "success");
    } catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="googleLogin", produces="application/json; charset=UTF-8")
  @ResponseBody
  public String googleLogin(User user, HttpSession session){
    try{
      user.setPassword("apiGuest");
      user.setThumbNail("empty.png");
      User checkUser = userService.googleCheck(user);
      if( checkUser == null ){
        System.out.println("[구글 로그인 가입 debug] : "+userService.addUser(user));
      }else{
        user = checkUser;
      }
      result.put("activeScheduleNo", scheduleService.activeSchedule(user.getUserNo()));
      System.out.println("[구글 로그인 확인 debug] : "+userService.getUser(user.getUserNo()));
      result.put("user", userService.getUser(user.getUserNo()));
      session.setAttribute("user", user);
      int totalRecommand = 0;
      int totalScrap = 0;
      List<Schedule> list = scheduleService.totalCount(user.getUserNo());
      for (Schedule sd : list) {
        totalRecommand += sd.getRecommandCount();
        totalScrap += sd.getScrapCount();
      }
      result.put("totalRecommand", totalRecommand);
      result.put("totalScrap", totalScrap);
      result.put("status", "success");
    }catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="logout")
  @ResponseBody
  public String logout(HttpSession session){
    try{
      session.invalidate();
      result.put("status", "success");
    }catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }
  
  @RequestMapping(path="checkUser") // mobile 로그인 확인 interceptor 적용
  @ResponseBody
  public String checkUser(HttpSession session){
    Map<String, Object> result = new HashMap<String, Object>();
    try{
      User user = (User)session.getAttribute("user");
      result.put("nickName",  user.getNickName());
      result.put("dashNo", user.getDashNo());
      result.put("status", "success");
    }catch (Exception e) {
      e.printStackTrace();
      result.put("status", "failure");
    }
    return new Gson().toJson(result);
  }

}
