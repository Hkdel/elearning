package com.zt.bbs.po;

import java.util.Date;

import com.zt.user.po.User;

public class BbsPost {
	private int id;
	private String name;
	private BbsPlate bbsPlate;
	private User createUser;
	private Date createTime;
	private String checkStatus;
	private User checkUser;
	private Date checkTime;
	private String isGood;
	private String isTop;
	private int heat;
	private int integral;
	private String opinion;
	private String content;
	private int replySum;

	public int getReplySum() {
		return replySum;
	}

	public void setReplySum(int replySum) {
		this.replySum = replySum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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

	public BbsPlate getBbsPlate() {
		return bbsPlate;
	}

	public void setBbsPlate(BbsPlate bbsPlate) {
		this.bbsPlate = bbsPlate;
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

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public User getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getIsGood() {
		return isGood;
	}

	public void setIsGood(String isGood) {
		this.isGood = isGood;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	@Override
	public String toString() {
		return "BbsPost [id=" + id + ", name=" + name + ", bbsPlate="
				+ bbsPlate + ", createUser=" + createUser + ", createTime="
				+ createTime + ", checkStatus=" + checkStatus + ", checkUser="
				+ checkUser + ", checkTime=" + checkTime + ", isGood=" + isGood
				+ ", isTop=" + isTop + ", heat=" + heat + ", integral="
				+ integral + ", opinion=" + opinion + ", content=" + content
				+ "]";
	}

}
