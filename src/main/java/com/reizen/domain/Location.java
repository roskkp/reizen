package com.reizen.domain;

import java.io.Serializable;

public class Location implements Serializable {

	// Field
	private static final long serialVersionUID = 1L;
	private int contentId;
	private int typeId;
	private String locateName;
	private String address1;
	private String address2;
	private String mapX;
	private String mapY;
	private int areaCode;
	private int localCode;
	private String category01;
	private String category02;
	private String category03;
	private String tel;
	private String firstImage;
	private String firstImage2;
	private String eventStartDate;
	private String eventEndDate;
	private int recommandCount;
	private int scrapCount;

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getLocateName() {
		return locateName;
	}

	public void setLocateName(String locateName) {
		this.locateName = locateName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getMapX() {
		return mapX;
	}

	public void setMapX(String mapX) {
		this.mapX = mapX;
	}

	public String getMapY() {
		return mapY;
	}

	public void setMapY(String mapY) {
		this.mapY = mapY;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public int getLocalCode() {
		return localCode;
	}

	public void setLocalCode(int localCode) {
		this.localCode = localCode;
	}

	public String getCategory01() {
		return category01;
	}

	public void setCategory01(String category01) {
		this.category01 = category01;
	}

	public String getCategory02() {
		return category02;
	}

	public void setCategory02(String category02) {
		this.category02 = category02;
	}

	public String getCategory03() {
		return category03;
	}

	public void setCategory03(String category03) {
		this.category03 = category03;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public String getFirstImage2() {
		return firstImage2;
	}

	public void setFirstImage2(String firstImage2) {
		this.firstImage2 = firstImage2;
	}

	public String getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public String getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public int getRecommandCount() {
		return recommandCount;
	}

	public void setRecommandCount(int recommandCount) {
		this.recommandCount = recommandCount;
	}

	public int getScrapCount() {
		return scrapCount;
	}

	public void setScrapCount(int scrapCount) {
		this.scrapCount = scrapCount;
	}

	@Override
	public String toString() {
		return "Location [contentId=" + contentId + ", typeId=" + typeId + ", locateName=" + locateName + ", address1="
				+ address1 + ", address2=" + address2 + ", mapX=" + mapX + ", mapY=" + mapY + ", areaCode=" + areaCode
				+ ", localCode=" + localCode + ", category01=" + category01 + ", category02=" + category02
				+ ", category03=" + category03 + ", tel=" + tel + ", firstImage=" + firstImage + ", firstImage2="
				+ firstImage2 + ", eventStartDate=" + eventStartDate + ", eventEndDate=" + eventEndDate
				+ ", recommandCount=" + recommandCount + ", scrapCount=" + scrapCount + ", totalDays="+ "]";
	}

}
