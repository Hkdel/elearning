package com.zt.study.po;

import java.util.Date;

import com.zt.user.po.User;
/*
 * 学习手记  对应t_studyNote表
 * */
public class StudyNote {
	private int id;
	private String name;		//手记名
	private String title;		//手记标题
	private String content;		//手记内容
	private User user;			//创建人,知道创建人Id就可以
	private Date createTime;	//创建时间
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
