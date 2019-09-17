package com.zt.user.dao;

import com.zt.user.po.User;

public interface UserDao {
	//根据id查询用户
	public User findUserById(int id);
}
