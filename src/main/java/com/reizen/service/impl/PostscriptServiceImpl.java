package com.reizen.service.impl;

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
  public int sscrs(int userNo, int scheduleNo) {
    // TODO Auto-generated method stub
    return dao.sscrs(userNo, scheduleNo);
  }

}
