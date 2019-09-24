package com.zt.exam.po;

import java.util.Date;

import com.zt.user.po.User;

public class Record {
	private int id;
	private User user;
	private Double subjective;
	private Double objective;
	private Rule rule;
	private Date startTime;
	private Date endTime;
	private String status;
	private Double score;
	private Double credit;

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

	public Double getSubjective() {
		return subjective;
	}

	public void setSubjective(Double subjective) {
		this.subjective = subjective;
	}

	public Double getObjective() {
		return objective;
	}

	public void setObjective(Double objective) {
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Record() {

	}

	public Record(User user, Double subjective, Double objective, Rule rule,
			Date startTime, Date endTime, String status, Double score, Double credit) {
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
