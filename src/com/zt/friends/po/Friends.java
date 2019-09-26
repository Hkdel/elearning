package com.zt.friends.po;

import com.zt.user.po.User;

public class Friends {
	private User loginUser;
	private User friendUser;
	private String status;
	private String withMsg;
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public User getFriendUser() {
		return friendUser;
	}
	public void setFriendUser(User friendUser) {
		this.friendUser = friendUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWithMsg() {
		return withMsg;
	}
	public void setWithMsg(String withMsg) {
		this.withMsg = withMsg;
	}
	
}
