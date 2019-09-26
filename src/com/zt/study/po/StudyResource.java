package com.zt.study.po;

import java.util.Date;

import com.zt.user.po.User;

/*
 * 学习资源
 * 对应 t_studyResource 学习资源表
 * */
public class StudyResource {
	private int id;
	private String name;		//资源名称
	private SourceType type;	//资源类别
	private String describe;	//描述
	private User uploadUser;		//上传人
	private Date uploadTime;	//上传时间
	private String checkStatus;		//审核状态 未审核0  审核通过1  驳回2，默认是未审核
	private Date checkTime;			//审核时间
	private User checkUser;			//审核人
	private String url;				//资源路径
	private String checkOpinion;	//审核意见
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
