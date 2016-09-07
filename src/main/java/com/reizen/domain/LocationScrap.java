package com.reizen.domain;

import java.io.Serializable;

public class LocationScrap implements Serializable {

  // Field
  private static final long serialVersionUID = 1L;
  private int userNo;
  private int contentId;
  public int getUserNo() {
    return userNo;
  }
  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
  public int getContentId() {
    return contentId;
  }
  public void setContentId(int contentId) {
    this.contentId = contentId;
  }
  @Override
  public String toString() {
    return "LocationScrap [userNo=" + userNo + ", contentId=" + contentId + "]";
  }

}
