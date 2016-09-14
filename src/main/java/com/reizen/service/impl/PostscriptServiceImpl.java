package com.reizen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reizen.dao.PostscriptDao;
import com.reizen.domain.Post;
import com.reizen.service.PostscriptService;


@Service
public class PostscriptServiceImpl implements PostscriptService{

  @Autowired PostscriptDao dao;
  
  @Override
  public List<Object> postscript(int scheduleNo) {
    return dao.postscript(scheduleNo);
  }

  @Override
  public List<Object> userPost(int scheduleNo) {
    return dao.userPost(scheduleNo);
  }

  @Override
  public int addPost(Post post) {
    // TODO Auto-generated method stub
    System.out.println("service"+post);
    return dao.addPost(post);
  }

  @Override
  public int addPicture(Map<String, Object> params) {
    // TODO Auto-generated method stub
    System.out.println("parmas");
    return dao.addPicture(params);
  }

  @Override
  public Post postSelect(int scheduleNo) {
    // TODO Auto-generated method stub
    System.out.println("dsd"+dao.postSelect(scheduleNo));
    return dao.postSelect(scheduleNo);
  }

  @Override
  public int updatePicture(Map<String, Object> params) {
    // TODO Auto-generated method stub
    return dao.updatePicture(params);
  }

  @Override
  public List<Object> selectPicture(int routeNo) {
    // TODO Auto-generated method stub
    
    System.out.println("들어왔나 루트넘버?:::"+routeNo);
    return dao.selectPicture(routeNo);
  }

  @Override
  public int checkPostscript(int scheduleNo) {
    // TODO Auto-generated method stub
    int checkNo = dao.checkPostscript(scheduleNo);
    if( checkNo == 0){
      return 0;
    }
    return checkNo;
  }

  @Override
  public int deletePicts(int pictureNo) {
    // TODO Auto-generated method stub
    return dao.deletePicts(pictureNo);
  }

  @Override
  public void sscrs(int userNo,int scheduleNo) {
    // TODO Auto-generated method stub
    Map<String, Object> map = new HashMap<>();
    map.put("userNo", userNo);
    map.put("scheduleNo",scheduleNo);
    dao.sscrs(map);
    map.put("type", "addScrap");
    dao.updateCount(map);
  }

  @Override
  public void srecm(int userNo,int scheduleNo) {
    Map<String, Object> map = new HashMap<>();
    map.put("userNo", userNo);
    map.put("scheduleNo",scheduleNo);
    dao.srecm(map);
    map.put("type", "addRecm");
    dao.updateCount(map);
  }

  @Override
  public int updateCount(Map<String, Object> parmas) {
    // TODO Auto-generated method stub
    return dao.updateCount(parmas);
  }

  @Override
  public void deleteRecm(int scheduleNo) {
    Map<String, Object> map = new HashMap<>();
    map.put("scheduleNo",scheduleNo);
    dao.deleteRecm(map);
    map.put("type", "delRecm");
    dao.updateCount(map);
  }

  @Override
  public Map<String, Object> checkRecm(int userNo, int scheduleNo) {
    // TODO Auto-generated method stub
    Map<String, Object> map = new HashMap<>();
    System.out.println("userNo:::::"+userNo+"scheduleNo::::"+scheduleNo);
    map.put("scheduleNo", scheduleNo);
    map.put("userNo", userNo);
    System.out.println("check::::"+dao.checkRecm(map));
    System.out.println("scrap::::::::"+dao.checkScrap(map));
    Map<String, Object> rcMap = new HashMap<>();
    if(dao.checkScrap(map)>=1){
      rcMap.put("scrap", "checked");
    }else{
      rcMap.put("scrap", "unChecked");
    }
    if(dao.checkRecm(map)>=1){
      rcMap.put("recm", "checked");
    }else{
      rcMap.put("recm", "unChecked");
    }
    System.out.println("머지?"+ rcMap);
    return rcMap;
  }

  @Override
  public void deleteScrap(int scheduleNo) {
    Map<String, Object> map = new HashMap<>();
    map.put("scheduleNo",scheduleNo);
    dao.deleteScrap(map);
    map.put("type", "delScrap");
   dao.updateCount(map);
  }

}
