package com.zt.bbs.po;

import java.util.Date;
import java.util.List;

import com.zt.user.po.User;

public class BbsReply {

	private int id;
	private BbsPost bbspost;
	private User loginUser;
	private User acceptUser;
	private String content;
	private Date createTime;
	private BbsReply bbsReply;
	private List<BbsReply> reply2;  

	public List<BbsReply> getReply2() {
		return reply2;
	}

	public void setReply2(List<BbsReply> reply2) {
		this.reply2 = reply2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BbsPost getBbspost() {
		return bbspost;
	}

	public void setBbspost(BbsPost bbspost) {
		this.bbspost = bbspost;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public User getAcceptUser() {
		return acceptUser;
	}

	public void setAcceptUser(User acceptUser) {
		this.acceptUser = acceptUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BbsReply getBbsReply() {
		return bbsReply;
	}

	public void setBbsReply(BbsReply bbsReply) {
		this.bbsReply = bbsReply;
	}

	@Override
	public String toString() {
		return "BbsReply [reply2=" + reply2 + "]";
	}

	
}
