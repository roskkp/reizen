package com.reizen.domain;

import java.io.Serializable;
import java.sql.Date;

public class Post implements Serializable {

  // Field
  private static final long serialVersionUID = 1L;
  private int postNo;
  private int scheduleNo;
  private Date regDate;  

  public int getPostNo() {
    return postNo;
  }
  public void setPostNo(int postNo) {
    this.postNo = postNo;
  }
  public int getScheduleNo() {
    return scheduleNo;
  }
  public void setScheduleNo(int scheduleNo) {
    this.scheduleNo = scheduleNo;
  }
  public Date getRegDate() {
    return regDate;
  }
  public void setRegDate(Date regDate) {
    this.regDate = regDate;
  }
  @Override
  public String toString() {
    return "Post [postNo=" + postNo + ", scheduleNo=" + scheduleNo + ", regDate=" + regDate + "]";
  }

  
}
