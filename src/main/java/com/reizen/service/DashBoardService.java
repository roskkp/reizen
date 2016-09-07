package com.reizen.service;

import java.util.List;

import com.reizen.domain.Location;
import com.reizen.domain.Schedule;
import com.reizen.domain.User;

public interface DashBoardService {
	public User getDashBoard(int no);

	public List<Schedule> getPlanList(int no);

	public List<Schedule> getScrapPlanList(int no);

	public List<Location> getScrapLocationList(int no);

	public int removePlan(int no);

	public int removeScp(int no);

	public int removeScl(int no);
}
