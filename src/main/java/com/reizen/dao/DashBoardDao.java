package com.reizen.dao;

import java.util.List;

import com.reizen.domain.Location;
import com.reizen.domain.Schedule;

public interface DashBoardDao {
	public int insertDashBoard(int no);

	public int selectDashBoard(int no);

	public List<Schedule> selectPlanList(int no);

	public List<Schedule> selectScrapPlanList(int no);

	public List<Location> selectScrapLocationList(int no);

	public int deleteRoute(int no);

	public int deletePlan(int no);

	public int deleteScPlan(int no);

	public int deleteScLocation(int no);
}
