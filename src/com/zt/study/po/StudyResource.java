package com.zt.study.po;

import java.util.Date;

import com.zt.user.po.User;

/*
 * ѧϰ��Դ
 * ��Ӧ t_studyResource ѧϰ��Դ��
 * */
public class StudyResource {
	private int id;
	private String name;		//��Դ����
	private SourceType type;	//��Դ���
	private String describe;	//����
	private User uploadUser;		//�ϴ���
	private Date uploadTime;	//�ϴ�ʱ��
	private String checkStatus;		//���״̬ δ���0  ���ͨ��1  ����2��Ĭ����δ���
	private Date checkTime;			//���ʱ��
	private User checkUser;			//�����
	private String url;				//��Դ·��
	private String checkOpinion;	//������
	public String getCheckOpinion() {
		return checkOpinion;
	}
	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
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
	
	public SourceType getType() {
		return type;
	}
	public void setType(SourceType type) {
		this.type = type;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public User getUploadUser() {
		return uploadUser;
	}
	
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public void setUploadUser(User uploadUser) {
		this.uploadUser = uploadUser;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public User getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
