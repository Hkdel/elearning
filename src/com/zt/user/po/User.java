package com.zt.user.po;

import java.util.Date;

public class User {
	/*
	 * id number primary key,--主键 photo varchar2(100),--头像 name
	 * varchar2(100),--姓名 pass varchar2(100),--密码 accountName
	 * varchar2(100),--账号名 roleId number,--角色id sex char(2),--性别 birthday
	 * date,--生日 bbsScore number,--论坛积分 examScore number,--考试学分 status
	 * char(1),--状态 createId number,--创建人 createTime date,--创建时间 rank
	 * varchar2(50),--等级 foreign key(createId) references t_sysUser(id), foreign
	 * key(roleId) references t_sysRole(id)
	 */
	private int id;
	private String photo;// 头像
	private String name;
	private String pass;
	private String accountName;
	private Role role;// 角色
	private String sex;
	private Date birthday;
	private int bbsScore;
	private double examScore;
	private String status;
	private User user;// 创建人
	private Date createTime;// 创建时间
	private String rank;

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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getBbsScore() {
		return bbsScore;
	}

	public void setBbsScore(int bbsScore) {
		this.bbsScore = bbsScore;
	}

	public double getExamScore() {
		return examScore;
	}

	public void setExamScore(double examScore) {
		this.examScore = examScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public User(String photo, String name, String pass, String accountName,
			Role role, String sex, Date birthday, int bbsScore, int examScore,
			String status, User user, Date createTime, String rank) {
		this.photo = photo;
		this.name = name;
		this.pass = pass;
		this.accountName = accountName;
		this.role = role;
		this.sex = sex;
		this.birthday = birthday;
		this.bbsScore = bbsScore;
		this.examScore = examScore;
		this.status = status;
		this.user = user;
		this.createTime = createTime;
		this.rank = rank;
	}

	public User() {

	}
}
