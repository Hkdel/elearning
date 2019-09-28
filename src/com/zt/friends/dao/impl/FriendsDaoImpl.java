package com.zt.friends.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zt.friends.dao.FriendsDao;
import com.zt.friends.po.Friends;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class FriendsDaoImpl implements FriendsDao {

	@Override
	public boolean addFriend(Friends friend) {
		String sql2="select * from t_friends where loginId=? and friendId=? and status='0'";
		String sql3="update t_friends set withMsg=? where loginId=? and friendId=? and status='0'";
		String sql="insert into t_friends(loginId,friendId,status,withMsg)"
				+ " values(?,?,'0',?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean result=true;
		try {
			conn=DBUtils.getConnection();
			//1：jdbc事务的默认自动提交true 改为 false
			conn.setAutoCommit(false);
			pstmt=conn.prepareStatement(sql2);
			pstmt.setInt(1, friend.getLoginUser().getId());
			pstmt.setInt(2, friend.getFriendUser().getId());
			rs=pstmt.executeQuery();
			if(!rs.next()){
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, friend.getLoginUser().getId());
				pstmt.setInt(2, friend.getFriendUser().getId());
				pstmt.setString(3, friend.getWithMsg());
				pstmt.executeUpdate();
			}else{
				pstmt=conn.prepareStatement(sql3);
				pstmt.setString(1, friend.getWithMsg());
				pstmt.setInt(2, friend.getLoginUser().getId());
				pstmt.setInt(3, friend.getFriendUser().getId());
				pstmt.executeUpdate();
			}
			
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setInt(1, friend.getLoginUser().getId());
//			pstmt.setInt(2, friend.getFriendUser().getId());
//			pstmt.setString(3, friend.getWithMsg());
//			int num=pstmt.executeUpdate();
//			if(num==1){
//				result=true;
//			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtils.close(rs, pstmt, conn);
		}
		return result;
	}

	@Override
	public int getTotalSize(User loginUser) {
		String sql="select count(*) from t_sysUser where roleId=5 and id not in"
				+ "(select friendId from t_friends  where "
				+ "status in(1,2) and loginId=?) and id!=?";
		int totalSize=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			pstmt.setInt(2, loginUser.getId());
			rs=pstmt.executeQuery();
			if(rs.next()){
				totalSize=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return totalSize;
	}

	@Override
	public List<User> searchFriendByPage(PageUtils pageUtils, User loginUser) {
		String sql="select u.*,rownum n from t_sysUser u where roleId=5 and id not in"
				+ "(select friendId from t_friends  where "
				+ "status in(1,2) and loginId=?) and id!=? and rownum<=?";
		String newsql="select * from ("+sql+")  where n>?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<User> notFriends=new ArrayList<User>();
		
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(newsql);
			pstmt.setInt(1, loginUser.getId());
			pstmt.setInt(2, loginUser.getId());
			pstmt.setInt(3, pageUtils.getCurrPage()*pageUtils.getPageSize());
			pstmt.setInt(4, (pageUtils.getCurrPage()-1)*pageUtils.getPageSize());
			rs=pstmt.executeQuery();
			while(rs.next()){
				User notFriend=new User();
				notFriend.setId(rs.getInt("id"));
				notFriend.setName(rs.getString("name"));
				notFriend.setSex(rs.getString("sex"));
				notFriend.setAccountName(rs.getString("accountName"));
				notFriend.setPhoto(rs.getString("photo"));
				notFriends.add(notFriend);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return notFriends;
	}

	@Override
	public User getFriendById(int toId) {
		String sql="select * from t_sysUser where id=?";
		User friendUser=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,toId );
			rs=pstmt.executeQuery();
			if(rs.next()){
				friendUser=new User();
				friendUser.setId(rs.getInt("id"));
				friendUser.setAccountName(rs.getString("accountName"));
				friendUser.setName(rs.getString("name"));
				friendUser.setSex(rs.getString("sex"));
				friendUser.setPhoto(rs.getString("photo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return friendUser;
	}

	
	@Override
	public int getApplySize(User loginUser) {
		String sql="select count(*) from (select f.*,u.name uname,"
				+ "u.accountname uaname,u.sex usex ,u.photo uphoto "
				+ "from t_friends f,t_sysUser u where f.loginid=u.id"
				+ " and f.status='0' and f.friendId=?)";
		int totalSize=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			rs=pstmt.executeQuery();
			if(rs.next()){
				totalSize=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return totalSize;
	}

	@Override
	public List<Friends> searchUserByPage(PageUtils pageUtils, User loginUser) {
		String sql="select * from (select f.*,u.name uname,"
				+ "u.accountname uaname,u.sex usex ,u.photo uphoto,"
				+ "rownum n from t_friends f,t_sysUser u where"
				+ " f.loginid=u.id and f.status='0' and f.friendId=? "
				+ "and rownum<=?) where n>?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Friends> list=new ArrayList<Friends>();
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			pstmt.setInt(2, pageUtils.getCurrPage()*pageUtils.getPageSize());
			pstmt.setInt(3, (pageUtils.getCurrPage()-1)*pageUtils.getPageSize());
			rs=pstmt.executeQuery();
			while(rs.next()){
				Friends friend=new Friends();
				friend.setWithMsg(rs.getString("withMsg"));
				User fromUser=new User();
				fromUser.setId(rs.getInt("loginId"));
				fromUser.setName(rs.getString("uname"));
				fromUser.setAccountName(rs.getString("uaname"));
				fromUser.setSex(rs.getString("usex"));
				fromUser.setPhoto(rs.getString("uphoto"));
				User toUser=new User();
				toUser.setId(rs.getInt("friendId"));
				friend.setLoginUser(fromUser);
				friend.setFriendUser(toUser);
//				friend.setStatus(rs.getString("status"));
				list.add(friend);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public boolean applyAccept(int fromId, User loginUser) {
		String sql="update t_friends set status=1 where "
				+ "status=0 and loginId=? and friendId=?";
		String sql2="insert into t_friends(loginId,friendid,"
				+ "status) values(?,?,'1')";
		String sql3="select * from t_friends where loginId=? and friendId=?";
		String sql4="update t_friends set status=1 where status=0 and"
				+ " loginId=? and friendId=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean result=true;
		try {
			conn=DBUtils.getConnection();
			//1：jdbc事务的默认自动提交true 改为 false
			conn.setAutoCommit(false);
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, fromId);
			pstmt.setInt(2, loginUser.getId());
			pstmt.executeUpdate();
			
			pstmt=conn.prepareStatement(sql3);
			pstmt.setInt(1, loginUser.getId());
			pstmt.setInt(2, fromId);
			rs=pstmt.executeQuery();
			if(!rs.next()){
				pstmt=conn.prepareStatement(sql2);
				pstmt.setInt(1, loginUser.getId());
				pstmt.setInt(2, fromId);
				pstmt.executeUpdate();
			}else{
				pstmt=conn.prepareStatement(sql4);
				pstmt.setInt(1, loginUser.getId());
				pstmt.setInt(2, fromId);
				pstmt.executeUpdate();
			}
			
//			pstmt=conn.prepareStatement(sql2);
//			pstmt.setInt(1, loginUser.getId());
//			pstmt.setInt(2, fromId);
//			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtils.close(rs, pstmt, conn);
		}
		
		return result;
	}

	@Override
	public boolean applyRefuse(int fromId, User loginUser) {
		String sql="delete from t_friends where loginId=? and friendId=? and status='0'";
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, fromId);
			pstmt.setInt(2, loginUser.getId());
			int num=pstmt.executeUpdate();
			if(num==1){
				result=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public int getMyFriendSize(User loginUser) {
		String sql="select count(*) from (select f.*,u.photo uphoto,"
				+ "u.name uname,u.accountname uaname,u.sex usex"
				+ " from t_friends f,t_sysUser u where f.friendId=u.id and f.loginId=? and f.status='1')";
		int totalSize=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			rs=pstmt.executeQuery();
			if(rs.next()){
				totalSize=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return totalSize;
	}

	@Override
	public List<Friends> searchMyFriendByPage(PageUtils pageUtils,
			User loginUser) {
		String sql="select * from (select f.*,u.photo uphoto,u.name uname,"
				+ "u.accountname uaname,u.sex usex,rownum n from t_friends f,"
				+ "t_sysUser u where f.friendId=u.id and f.loginId=? and f.status='1'"
				+ " and rownum<=?) where n>?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Friends> list=new ArrayList<Friends>();
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			pstmt.setInt(2, pageUtils.getCurrPage()*pageUtils.getPageSize());
			pstmt.setInt(3, (pageUtils.getCurrPage()-1)*pageUtils.getPageSize());
			rs=pstmt.executeQuery();
			while(rs.next()){
				Friends friend=new Friends();
				friend.setWithMsg(rs.getString("withMsg"));
				
				User friendUser=new User();
				friendUser.setId(rs.getInt("friendId"));
				friendUser.setName(rs.getString("uname"));
				friendUser.setAccountName(rs.getString("uaname"));
				friendUser.setSex(rs.getString("usex"));
				friendUser.setPhoto(rs.getString("uphoto"));
				User myUser=new User();
				myUser.setId(rs.getInt("loginId"));
				friend.setLoginUser(myUser);
				friend.setFriendUser(friendUser);
//				friend.setStatus(rs.getString("status"));
				list.add(friend);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return list;
	}
	@Override
	public boolean addBlack(int loginId, int toId) {
		String sql="update t_friends set status='2' where loginId=? and friendId=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginId);
			pstmt.setInt(2, toId);
			int num=pstmt.executeUpdate();
			if(num==1){
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public boolean delFriend(int loginId, int toId) {
		String sql="delete from t_friends where loginId in(?,?) and friendId in(?,?)";
		String sql2="delete from t_chatMsg where fromId in(?,?) and toId in(?,?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=true;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginId);
			pstmt.setInt(2, toId);
			pstmt.setInt(3, toId);
			pstmt.setInt(4, loginId);
			int num=pstmt.executeUpdate();
			
			pstmt=conn.prepareStatement(sql2);
			pstmt.setInt(1, loginId);
			pstmt.setInt(2, toId);
			pstmt.setInt(3, toId);
			pstmt.setInt(4, loginId);
			int num2=pstmt.executeUpdate();
			
			/*if(num==1&&num2==1){
				result=true;
			}*/
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public int getBlackSize(User loginUser) {
		String sql="select count(*) from(select f.*,u.name fname,"
				+ "u.accountname faname,u.photo fphoto from t_friends f,"
				+ "t_sysUser u where f.friendid=u.id and  "
				+ "f.status='2' and f.loginId=?)";
		int totalSize=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			rs=pstmt.executeQuery();
			if(rs.next()){
				totalSize=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return totalSize;
	}

	@Override
	public List<Friends> searchBlackByPage(PageUtils pageUtils, User loginUser) {
		String sql="select * from (select f.*,u.name fname,u.accountname faname,u.sex fsex,"
				+ "u.photo fphoto,rownum n from t_friends f,t_sysUser u where"
				+ " f.friendid=u.id and  f.status='2' and f.loginId=?"
				+ " and rownum<=?) where n>?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Friends> list=new ArrayList<Friends>();
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			pstmt.setInt(2, pageUtils.getCurrPage()*pageUtils.getPageSize());
			pstmt.setInt(3, (pageUtils.getCurrPage()-1)*pageUtils.getPageSize());
			rs=pstmt.executeQuery();
			while(rs.next()){
				Friends friend=new Friends();
				friend.setWithMsg(rs.getString("withMsg"));
				
				User friendUser=new User();
				friendUser.setId(rs.getInt("friendId"));
				friendUser.setName(rs.getString("fname"));
				friendUser.setAccountName(rs.getString("faname"));
				friendUser.setSex(rs.getString("fsex"));
				friendUser.setPhoto(rs.getString("fphoto"));
				User myUser=new User();
				myUser.setId(rs.getInt("loginId"));
				friend.setLoginUser(myUser);
				friend.setFriendUser(friendUser);
//				friend.setStatus(rs.getString("status"));
				list.add(friend);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public boolean delBlack(int loginId, int friendId) {
		String sql="update t_friends set status='1' where loginId=? and friendId=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginId);
			pstmt.setInt(2, friendId);
			int num=pstmt.executeUpdate();
			if(num==1){
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

}
