package com.reizen.domain;

import java.io.Serializable;

public class Picture implements Serializable {

  // Field
  private static final long serialVersionUID = 1L;
  private int pictureNo;
  private Post post;
  private Route route;
  private String picturePath;
  private String content;
  private int scheduleNo;
  private int postNo;
  private int price;
  private int transportation;
  
  public int getScheduleNo() {
    return scheduleNo;
  }
  public void setScheduleNo(int scheduleNo) {
    this.scheduleNo = scheduleNo;
  }
  public int getPostNo() {
    return postNo;
  }
  public void setPostNo(int postNo) {
    this.postNo = postNo;
  }
  public int getPrice() {
    return price;
  }
  public void setPrice(int price) {
    this.price = price;
  }
  public int getTransportation() {
    return transportation;
  }
  public void setTransportation(int transportation) {
    this.transportation = transportation;
  }
  public Route getRoute() {
    return route;
  }
  public void setRoute(Route route) {
    this.route = route;
  }
  
  public int getPictureNo() {
    return pictureNo;
  }
  public void setPictureNo(int pictureNo) {
    this.pictureNo = pictureNo;
  }
  public Post getPost() {
    return post;
  }
  public void setPost(Post post) {
    this.post = post;
  }
  public String getPicturePath() {
    return picturePath;
  }
  public void setPicturePath(String picturePath) {
    this.picturePath = picturePath;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  
  @Override
  public String toString() {
    return "Picture [pictureNo=" + pictureNo + ", post=" + post + ", route=" + route + ", picturePath=" + picturePath
        + ", content=" + content + ", scheduleNo=" + scheduleNo + ", postNo=" + postNo + ", price=" + price
        + ", transportation=" + transportation + "]";
  }
  

}
