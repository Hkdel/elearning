package com.zt.study.po;

import java.util.Date;

import com.zt.user.po.User;
/*
 * ��Դ����
 * ��Ӧ t_studyType ��Դ���ͱ�
 * */
public class SourceType {
	private int id;
	private String typeName;
	private String status;//1����  0ͣ��
	private User user;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
