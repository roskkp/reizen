package com.reizen.dao;

import com.reizen.domain.User;

public interface UserDao {

	public int insertUser(User user);

	public User selectUser(int no);

	public User checkUser(User user);

	public int updateUser(User user);

	public int deleteUser(int no);

}
