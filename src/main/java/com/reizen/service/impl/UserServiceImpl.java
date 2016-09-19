package com.reizen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reizen.dao.DashBoardDao;
import com.reizen.dao.ScheduleDao;
import com.reizen.dao.UserDao;
import com.reizen.domain.Schedule;
import com.reizen.domain.User;
import com.reizen.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	DashBoardDao dashBoardDao;
	
  @Autowired
  ScheduleDao scheduleDao;

	@Override
	public Map<String, Object> checkUser(User user, HttpSession httpSession) throws Exception {
    Map<String, Object> result = new HashMap<String, Object>();
	  User resultUser = userDao.checkUser(user);
    result.put("user", resultUser);
    result.put("activeScheduleNo", scheduleDao.activeSelect(resultUser.getUserNo()));
    httpSession.setAttribute("user", resultUser);
    int totalRecommand = 0;
    int totalScrap = 0;
    List<Schedule> list = scheduleDao.totalCount(resultUser.getUserNo());
    for (Schedule sd : list) {
      totalRecommand += sd.getRecommandCount();
      totalScrap += sd.getScrapCount();
    }
    result.put("totalRecommand", totalRecommand);
    result.put("totalScrap", totalScrap);
		User dbUser = userDao.checkUser(user);
		if (dbUser == null) {
			throw new Exception();
		}
		return result;
	}

	@Override
	public User getUser(int no) {
		return userDao.selectUser(no);
	}

	@Override
	public int addUser(User user) {
	  userDao.insertUser(user);
		return dashBoardDao.insertDashBoard(user.getUserNo());
	}

	@Override
	public String checkMail(String email) throws Exception {
		return userDao.selectMail(email);
	}

  @Override
  public int updateUser(User user) {
    return userDao.updateUser(user);
  }

  @Override
  public int deleteUser(int no) {
    return userDao.deleteUser(no);
  }

  @Override
  public User googleCheck(User user) {
    return userDao.checkUser(user);
  }

}
