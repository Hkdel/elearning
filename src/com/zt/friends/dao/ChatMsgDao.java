package com.zt.friends.dao;

import java.util.List;

import com.zt.friends.po.ChatMsg;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

public interface ChatMsgDao {
	public boolean sendMsg(ChatMsg chatmsg);
	
	/*��ѯ˫��������Ϣ*/
	public List<ChatMsg> findMsg(int loginId,int toId);
	
	//��ѯ���ҷ���Ϣ����
	public int getToMyTotalSize(User loginUser);
	public List<ChatMsg> findToMyUserByPage(PageUtils pageUtils,User loginUser);
	
	//������Ϣ
	public boolean ignoreMsg(int loginId,int toId);
	
	//���ݺ���Id��ѯ��ǰ�û���δ����Ϣ����
	public int getCountByFriendId(int loginId);
}
