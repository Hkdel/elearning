package com.zt.user.po;

import java.util.Date;

import com.zt.user.po.Auth;

public class Auth {
	/*
	 * id number primary key, name varchar2(50), url varchar2(100), parentId
	 * number, status char(1), createId number, createTime date,
	 */
	private int id; // 主键
	private String name; // 名称
	private String url; // 访问路径
	private String status; // 状态
	private Auth parent; // 父级功能
	private User user;// 创建人
	private Date createTime;// 创建时间

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Auth getParent() {
		return parent;
	}

	public void setParent(Auth parent) {
		this.parent = parent;
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

	@Override
	public int hashCode() {
		return this.id + this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Auth) {
			Auth a = (Auth) obj;
			if (a.id == this.id && a.name.equals(this.name)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
