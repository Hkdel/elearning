package com.zt.exam.po;

import java.util.Date;

import com.zt.user.po.User;

public class Question {
	private int id;
	private String title;
	private Subject subject;
	private Type type;
	private String answer;
	private String status;
	private User createUser;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public Question() {

	}

	public Question(String title, Subject subject, Type type, String answer,
			String status, User createUser, Date createTime) {
		this.title = title;
		this.subject = subject;
		this.type = type;
		this.answer = answer;
		this.status = status;
		this.createUser = createUser;
		this.createTime = createTime;
	}

}
