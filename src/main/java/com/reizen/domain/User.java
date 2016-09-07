package com.reizen.domain;

import java.io.Serializable;

public class User implements Serializable {

	// Field
	private static final long serialVersionUID = 1L;

	private int userNo;
	private String email;
	private String password;
	private String nickName;
	private String thumbNail;
	private String role;
	private int dashNo;

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getThumbNail() {
		return thumbNail;
	}

	public void setThumbNail(String thumbNail) {
		this.thumbNail = thumbNail;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getDashNo() {
		return dashNo;
	}

	public void setDashNo(int dashNo) {
		this.dashNo = dashNo;
	}

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", email=" + email + ", password=" + password + ", nickName=" + nickName
				+ ", thumbNail=" + thumbNail + ", role=" + role + ", dashNo=" + dashNo + "]";
	}

}
