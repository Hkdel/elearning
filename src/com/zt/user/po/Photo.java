package com.zt.user.po;

import java.util.Date;

public class Photo {
	private int id;//ͼƬid
	private String url;//ͼƬ·��
	private User user;//������
	private Date createTime;//����ʱ��
	private int place;//ͼƬλ�� 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	
	
}
