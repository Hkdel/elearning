package com.zt.friends.dao;

import java.util.List;

import com.zt.friends.po.ChatMsg;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

public interface ChatMsgDao {
	public boolean sendMsg(ChatMsg chatmsg);
	
	/*查询双方发的消息*/
	public List<ChatMsg> findMsg(int loginId,int toId);
	
	//查询给我发消息的人
	public int getToMyTotalSize(User loginUser);
	public List<ChatMsg> findToMyUserByPage(PageUtils pageUtils,User loginUser);
}
