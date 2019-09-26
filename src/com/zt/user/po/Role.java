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
	private int id;//����
	private String name;//��ɫ����
	private String status;//��ɫ״̬
	private User user;//������
	private Date createTime;//����ʱ��
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
