package com.reizen.domain;

import java.io.Serializable;

public class ScheduleScrap implements Serializable {

  // Field
  private static final long serialVersionUID = 1L;
  private int userNo;
  private int scheduleNo;
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public int getScheduleNo() {
    return scheduleNo;
  }
  public void setScheduleNo(int scheduleNo) {
    this.scheduleNo = scheduleNo;
  }
  @Override
  public String toString() {
    return "ScheduleScrap [userNo=" + userNo + ", scheduleNo=" + scheduleNo + "]";
  }
  
}
