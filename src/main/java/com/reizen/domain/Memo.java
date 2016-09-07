package com.reizen.domain;

import java.io.Serializable;
import java.sql.Date;

public class Memo implements Serializable {

  // Field
  private static final long serialVersionUID = 1L;
  private int memoNo;
  private User user;
  private int contentId;
  private String content;
  private Date regDate;
  private String dateAgo;
  
  public int getMemoNo() {
    return memoNo;
  }
  public void setMemoNo(int memoNo) {
    this.memoNo = memoNo;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public int getContentId() {
    return contentId;
  }
  public void setContentId(int contentId) {
    this.contentId = contentId;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getRegDate() {
    return regDate;
  }
  public void setRegDate(Date regDate) {
    this.regDate = regDate;
  }
  public String getDateAgo() {
	return dateAgo;
}
public void setDateAgo(String dateAgo) {
	this.dateAgo = dateAgo;
}
@Override
public String toString() {
	return "Memo [memoNo=" + memoNo + ", user=" + user + ", contentId=" + contentId + ", content=" + content
			+ ", regDate=" + regDate + ", dateAgo=" + dateAgo + "]";
}

}
