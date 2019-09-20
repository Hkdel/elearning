package com.zt.exam.po;

import java.util.Date;

import com.zt.user.po.User;

public class Rule {
	private int id;
	private String name;
	private Subject subject;
	private int time;
	private String status;
	private int score;
	private Date createTime;
	private int credit;
	private User createUser;

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Rule() {

	}

	public Rule(String name, Subject subject, int time, String status,
			int score, Date createTime, int credit, User createUser) {
		this.name = name;
		this.subject = subject;
		this.time = time;
		this.status = status;
		this.score = score;
		this.createTime = createTime;
		this.credit = credit;
		this.createUser = createUser;
	}

}
