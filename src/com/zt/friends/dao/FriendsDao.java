package com.zt.friends.dao;

import java.util.List;
import com.zt.friends.po.Friends;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

public interface FriendsDao {
	//��Ӻ���
	public boolean addFriend(Friends friend);
	/*��ҳ+ģ����ѯ*/
	public int getTotalSize(User loginUser);
	//��ѯ������Ӻ��ѵ�
	public List<User> searchFriendByPage(PageUtils pageUtils,User loginUser);
	
	//ͨ��id���û�
	public User getFriendById(int toId);
	
	//��ѯ������Һ��ѵ���
	public int getApplySize(User loginUser);
	public List<Friends> searchUserByPage(PageUtils pageUtils,User loginUser);
	
	//���ܺ�������
	public boolean applyAccept(int fromId,User loginUser);
	
	//�ܾ���������
	public boolean applyRefuse(int fromId,User loginUser);
	
	//��ѯ�ҵĺ���
	public int getMyFriendSize(User loginUser);
	public List<Friends> searchMyFriendByPage(PageUtils pageUtils,User loginUser);
	
	//���������
	public boolean addBlack(int loginId,int toId);
	
	//ɾ������
	public boolean delFriend(int loginId,int toId);
	
	//��ѯ�������б�
	public int getBlackSize(User loginUser);
	public List<Friends> searchBlackByPage(PageUtils pageUtils,User loginUser);
	
	//���������
	public boolean delBlack(int loginId,int friendId);
	
	
}
