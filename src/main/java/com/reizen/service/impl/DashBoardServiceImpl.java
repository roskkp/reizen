package com.reizen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reizen.dao.DashBoardDao;
import com.reizen.dao.ScheduleDao;
import com.reizen.dao.UserDao;
import com.reizen.domain.Location;
import com.reizen.domain.Schedule;
import com.reizen.domain.User;
import com.reizen.service.DashBoardService;

@Service
public class DashBoardServiceImpl implements DashBoardService {

	@Autowired
	DashBoardDao dashBoardDao;
    @Autowired
    ScheduleDao scheduleDao;
	@Autowired
	UserDao userDao;
	
	public DashBoardServiceImpl() {
	}

	
	@Override
	public User getDashBoard(int no) {
		return userDao.selectUser(dashBoardDao.selectDashBoard(no));
	}

	@Override
	public List<Schedule> getPlanList(int no) {
		return dashBoardDao.selectPlanList(dashBoardDao.selectDashBoard(no));
	}

	@Override
	public List<Schedule> getScrapPlanList(int no) {
		return dashBoardDao.selectScrapPlanList(dashBoardDao.selectDashBoard(no));
	}
	
	@Override
	public List<Location> getScrapLocationList(int no) {
		return dashBoardDao.selectScrapLocationList(dashBoardDao.selectDashBoard(no));
	}
	
	@Override
	public int removePlan(int no) {
		dashBoardDao.deleteRoute(no);
		return dashBoardDao.deletePlan(no);
	}
	
	@Override
	public int removeScp(int no) {
		return dashBoardDao.deleteScPlan(no);
	}

	@Override
	public int removeScl(int no) {
		return dashBoardDao.deleteScLocation(no);
	}
}
