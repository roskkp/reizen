package com.reizen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reizen.dao.DashBoardDao;
import com.reizen.dao.UserDao;
import com.reizen.domain.User;
import com.reizen.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	DashBoardDao dashBoardDao;

	@Override
	public User checkUser(User user) throws Exception {
	  System.out.println(user);
		User dbUser = userDao.checkUser(user);
		System.out.println("[DB로부터 : ]" + dbUser);
		if (dbUser == null) {
			throw new Exception();
		}
		return dbUser;
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
