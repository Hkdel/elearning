package com.zt.bbs.po;

import java.util.Date;

import com.zt.user.po.User;

public class BbsPlate {
	private int id;
	private String photo;
	private String name;
	private String introduction;
	private User createUser;
	private String status;
	private Date createTime;
	private int postSum=0;

	public int getPostSum() {
		return postSum;
	}

	public void setPostSum(int postSum) {
		this.postSum = postSum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
