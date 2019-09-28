package com.zt.friends.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zt.friends.dao.ChatMsgDao;
import com.zt.friends.po.ChatMsg;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class ChatMsgDaoImpl implements ChatMsgDao {

	@Override
	public boolean sendMsg(ChatMsg chatmsg) {
		String sql2="select * from t_friends where status='2' and loginId in(?,?) and friendId in(?,?)";
		String sql="insert into t_chatMsg(id,fromId,toId,content,sendTime,status)"
				+ " values(seq_chatMsg.nextval,?,?,?,sysdate,'0')";
		boolean result=true;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			//1：jdbc事务的默认自动提交true 改为 false
			conn.setAutoCommit(false);
			pstmt=conn.prepareStatement(sql2);
			pstmt.setInt(1, chatmsg.getFromUser().getId());
			pstmt.setInt(2, chatmsg.getToUser().getId());
			pstmt.setInt(3, chatmsg.getToUser().getId());
			pstmt.setInt(4, chatmsg.getFromUser().getId());
			rs=pstmt.executeQuery();
			if(!rs.next()){
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, chatmsg.getFromUser().getId());
				pstmt.setInt(2, chatmsg.getToUser().getId());
				pstmt.setString(3, chatmsg.getContent());
				pstmt.executeUpdate();
			}
			
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
	public List<ChatMsg> findMsg(int loginId, int toId) {
		String sql="select c.*,u.name fname,u.photo fphoto,u2.photo myPhoto from t_Chatmsg c,t_sysUser u,t_sysUser u2 "
				+ "where c.fromid=u.id and c.toId=u2.id  and c.fromId in (?,?)"
				+ " and c.toId in(?,?) order by sendTime asc";
		String sql2="update t_chatmsg set status='1' where toId=? and fromId=?";
		List<ChatMsg> list=new ArrayList<ChatMsg>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginId);
			pstmt.setInt(2, toId);
			pstmt.setInt(3, toId);
			pstmt.setInt(4, loginId);
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				ChatMsg msg=new ChatMsg();
				msg.setContent(rs.getString("content"));
				msg.setSendTime(rs.getTimestamp("sendTime"));
				msg.setId(rs.getInt("id"));
				msg.setStatus(rs.getString("status"));
				
				User fromUser=new User();
				fromUser.setId(rs.getInt("fromId"));
				fromUser.setName(rs.getString("fname"));
				fromUser.setPhoto(rs.getString("fphoto"));
				
				User toUser=new User();
				toUser.setId(rs.getInt("toId"));
				toUser.setPhoto(rs.getString("myPhoto"));
				
				msg.setFromUser(fromUser);
				msg.setToUser(toUser);
				list.add(msg);
				
			}
			pstmt=conn.prepareStatement(sql2);
			pstmt.setInt(1, loginId);
			pstmt.setInt(2, toId);
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		
		return list;
	}

	@Override
	public List<ChatMsg> findToMyUserByPage(PageUtils pageUtils,User loginUser) {
		String sql="select * from (select m.*,rownum n from "
				+ "(select distinct c.fromId, f.photo fphoto,"
				+ "f.name fname,f.accountname faname from t_chatMsg c,"
				+ "t_sysUser f where c.fromid=f.id and c.status='0' "
				+ "and c.toId=?) m where rownum<=?) where n>?";
		List<ChatMsg> list=new ArrayList<ChatMsg>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginUser.getId());
			pstmt.setInt(2, pageUtils.getCurrPage()*pageUtils.getPageSize());
			pstmt.setInt(3, (pageUtils.getCurrPage()-1)*pageUtils.getPageSize());
			rs=pstmt.executeQuery();
			while(rs.next()){
				ChatMsg c=new ChatMsg();
				User fromUser=new User();
				fromUser.setPhoto(rs.getString("fphoto"));
				fromUser.setAccountName(rs.getString("faname"));
				fromUser.setName(rs.getString("fname"));
				fromUser.setId(rs.getInt("fromId"));
				c.setFromUser(fromUser);
				list.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public int getToMyTotalSize(User loginUser) {
		String sql="select count(*) from(select distinct c.fromId,"
				+ "f.photo fphoto,f.name fname,f.accountname faname"
				+ " from t_chatMsg c,t_sysUser f where c.fromid=f.id"
				+ " and c.status='0' and c.toId=?)";
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
	public boolean ignoreMsg(int loginId, int toId) {
		String sql="update t_Chatmsg set status='1' where fromId=? and toId=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=true;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, toId);
			pstmt.setInt(2, loginId);
			int num=pstmt.executeUpdate();
			/*if(num==1){
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
	public int getCountByFriendId(int loginId) {
		String sql="select count(*) from t_chatMsg where"
				+ " status='0' and toId=?";
		int count=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginId);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return count;
	}

	

}
