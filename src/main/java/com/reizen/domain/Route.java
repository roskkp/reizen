package com.reizen.domain;

import java.io.Serializable;

public class Route implements Serializable {

  // Field
  private static final long serialVersionUID = 1L;
  
  private int routeNo;
  private int scheduleNo;
  private int day;
  private int travelSequence;
  private String time;
  private int transferCode;
  private int price;
  private String flag;

  private Location location;
  private Schedule schedule;
  private Memo memo;

  public Schedule getSchedule() {
    return schedule;
  }

  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }

  public int getRouteNo() {
    return routeNo;
  }

  public void setRouteNo(int routeNo) {
    this.routeNo = routeNo;
  }

  public int getScheduleNo() {
    return scheduleNo;
  }

  public void setScheduleNo(int scheduleNo) {
    this.scheduleNo = scheduleNo;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int getTravelSequence() {
    return travelSequence;
  }

  public void setTravelSequence(int travelSequence) {
    this.travelSequence = travelSequence;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public int getTransferCode() {
    return transferCode;
  }

  public void setTransferCode(int transferCode) {
    this.transferCode = transferCode;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Memo getMemo() {
    return memo;
  }

  public void setMemo(Memo memo) {
    this.memo = memo;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  @Override
  public String toString() {
    return "Route [routeNo=" + routeNo + ", scheduleNo=" + scheduleNo + ", day=" + day + ", travelSequence="
        + travelSequence + ", time=" + time + ", transferCode=" + transferCode + ", price=" + price + ", flag=" + flag
        + ", location=" + location + ", schedule=" + schedule + ", memo=" + memo + "]";
  }

}
