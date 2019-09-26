package com.zt.user.po;

import java.util.Date;

public class Role {
/*
 *  id number primary key,
       name varchar2(50),
       status char(1),
       createId number,
       createTime date       
 */
	private int id;//主键
	private String name;//角色名称
	private String status;//角色状态
	private User user;//创建人
	private Date createTime;//创建时间
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
	
}
