package com.zt.study.po;

import java.util.Date;

import com.zt.user.po.User;
/*
 * ѧϰ�ּ�  ��Ӧt_studyNote��
 * */
public class StudyNote {
	private int id;
	private String name;		//�ּ���
	private String title;		//�ּǱ���
	private String content;		//�ּ�����
	private User user;			//������,֪��������Id�Ϳ���
	private Date createTime;	//����ʱ��
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
