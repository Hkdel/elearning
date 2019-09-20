package com.zt.exam.po;

import java.util.Date;

import com.zt.user.po.User;

public class Record {
	private int id;
	private User user;
	private int subjective;
	private int objective;
	private Rule rule;
	private Date startTime;
	private Date endTime;
	private String status;
	private double score;
	private double credit;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getSubjective() {
		return subjective;
	}

	public void setSubjective(int subjective) {
		this.subjective = subjective;
	}

	public int getObjective() {
		return objective;
	}

	public void setObjective(int objective) {
		this.objective = objective;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public Record() {

	}

	public Record(User user, int subjective, int objective, Rule rule,
			Date startTime, Date endTime, String status, double score, double credit) {
		this.user = user;
		this.subjective = subjective;
		this.objective = objective;
		this.rule = rule;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.score = score;
		this.credit = credit;
	}

}
