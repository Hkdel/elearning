package com.zt.exam.po;

import java.util.Date;

import com.zt.user.po.User;

public class Subject {
	private int id;
	private String name;
	private String status;
	private User createUser;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Subject() {

	}

	public Subject(String name, String status, User createUser, Date createTime) {
		this.name = name;
		this.status = status;
		this.createUser = createUser;
		this.createTime = createTime;
	}

}
