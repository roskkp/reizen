package com.reizen.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.reizen.domain.User;

public interface UserService {

  public Map<String, Object> checkUser(User user, HttpSession httpSession) throws Exception;
  public User getUser(int no);
  public int addUser(User user);
  public String checkMail(String email) throws Exception;
  public int updateUser(User user);
  public int deleteUser(int no);
  public User googleCheck(User user);
}
