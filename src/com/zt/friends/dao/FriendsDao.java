package com.zt.friends.dao;

import java.util.List;
import com.zt.friends.po.Friends;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

public interface FriendsDao {
	//添加好友
	public boolean addFriend(Friends friend);
	/*分页+模糊查询*/
	public int getTotalSize(User loginUser);
	//查询可以添加好友的
	public List<User> searchFriendByPage(PageUtils pageUtils,User loginUser);
	
	//通过id查用户
	public User getFriendById(int toId);
	
	//查询申请加我好友的人
	public int getApplySize(User loginUser);
	public List<Friends> searchUserByPage(PageUtils pageUtils,User loginUser);
	
	//接受好友申请
	public boolean applyAccept(int fromId,User loginUser);
	
	//拒绝好友申请
	public boolean applyRefuse(int fromId,User loginUser);
	
	//查询我的好友
	public int getMyFriendSize(User loginUser);
	public List<Friends> searchMyFriendByPage(PageUtils pageUtils,User loginUser);
	
	//加入黑名单
	public boolean addBlack(int loginId,int toId);
	
	//删除好友
	public boolean delFriend(int loginId,int toId);
	
	//查询黑名单列表
	public int getBlackSize(User loginUser);
	public List<Friends> searchBlackByPage(PageUtils pageUtils,User loginUser);
	
	//解除黑名单
	public boolean delBlack(int loginId,int friendId);
	
	
}
