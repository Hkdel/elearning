package com.zt.exam.po;

public class RuleDetail {
	private int id;
	private Rule rule;
	private Type type;
	private int nums;
	private int score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public RuleDetail() {

	}

	public RuleDetail(Rule rule, Type type, int nums, int score) {
		this.rule = rule;
		this.type = type;
		this.nums = nums;
		this.score = score;
	}

}
