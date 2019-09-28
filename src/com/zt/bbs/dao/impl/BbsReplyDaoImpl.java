package com.zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.zt.bbs.dao.BbsReplyDao;
import com.zt.bbs.po.BbsPlate;
import com.zt.bbs.po.BbsPost;
import com.zt.bbs.po.BbsReply;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class BbsReplyDaoImpl implements BbsReplyDao {

	@Override
	public boolean addReply(BbsReply reply) {
		boolean result = false;
		String sql ="INSERT INTO t_bbsReply(ID,Postid,Loginid,Acceptid,Content,Createtime,Parentid)"
				+ "VALUES(seq_t_bbsReply.NEXTVAL,?,?,null,?,SYSDATE,null)";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, reply.getBbspost().getId());
			pst.setInt(2, reply.getLoginUser().getId());
			pst.setString(3, reply.getContent());
			int num = pst.executeUpdate();
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pst, conn);
		}
		return result;
	}
	
//	@Override
//	public boolean delReply(int id){
//		boolean result = false;
//		String sql = "";
//		Connection conn = null;
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		try {
//			conn = DBUtils.getConnection();
//			pst = conn.prepareStatement(sql);
//			int num = pst.executeUpdate();
//			if (num == 1) {
//				result = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DBUtils.close(rs, pst, conn);
//		}
//		return result;
//	}
	
	@Override
	public boolean delReply2(int id) {
		boolean result = false;
		String sql = "DELETE FROM t_bbsReply WHERE ID = "+id;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			int num = pst.executeUpdate();
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pst, conn);
		}
		return result;
	}

	@Override
	public List<BbsReply> findByPage(int id, PageUtils page) {
		List<BbsReply> postReplyList = new ArrayList<BbsReply>();
		String sql = "SELECT t.*,b.s s FROM(SELECT * FROM(SELECT t.*,u.NAME uName,ROWNUM n "
				+ "FROM t_bbsReply t,t_sysUser u WHERE t.loginid=u.ID AND t.postid="+id+" AND t.parentid IS NULL "
				+ "AND ROWNUM<=?)WHERE n>?) t,(SELECT createId,SUM(integral) s "
				+ "FROM t_bbsPost GROUP BY createId) b WHERE t.loginId=b.createid(+)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page.getCurrPage() * page.getPageSize());
			pstmt.setInt(2, (page.getCurrPage() - 1) * page.getPageSize());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BbsReply postReply = new BbsReply();
				postReply.setId(rs.getInt("id"));
				BbsPost bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("postId"));
				postReply.setBbspost(bbsPost);
				User loginUser = new User();
				loginUser.setId(rs.getInt("loginId"));
				loginUser.setName(rs.getString("uName"));
				loginUser.setBbsScore(rs.getInt("s"));
				postReply.setLoginUser(loginUser);
				postReply.setContent(rs.getString("content"));
				postReply.setCreateTime(rs.getDate("createTime"));
				postReplyList.add(postReply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return postReplyList;
	}

	@Override
	public int count(int id) {
		String sql = "SELECT COUNT(*) a FROM t_bbsReply WHERE postid=" + id;
		int totalSize = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalSize = rs.getInt("a");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return totalSize;
	}

	@Override
	public List<BbsReply> findReply2(int id) {
		List<BbsReply> reply2List = new ArrayList<BbsReply>();
		String sql ="SELECT t.*,l.NAME loginName,a.NAME accName FROM t_bbsReply t,t_sysUser l,t_sysUser a "
				+ "WHERE t.loginId=l.ID AND t.acceptid=a.ID START WITH t.parentid="+id+" "
				+ "CONNECT BY NOCYCLE PRIOR t.ID=t.parentid";
		//System.out.println(sql);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BbsReply reply2 = new BbsReply();
				reply2.setId(rs.getInt("id"));
				User loginUser = new User();
				loginUser.setId(rs.getInt("loginId"));
				loginUser.setName(rs.getString("loginName"));
				reply2.setLoginUser(loginUser);
				User acceptUser = new User();
				acceptUser.setId(rs.getInt("acceptid"));
				acceptUser.setName(rs.getString("accName"));
				reply2.setAcceptUser(acceptUser);
				reply2.setContent(rs.getString("content"));
				reply2.setCreateTime(rs.getDate("createTime"));
				BbsReply bbsReply = new BbsReply();
				bbsReply.setId(rs.getInt("parentid"));
				reply2.setBbsReply(bbsReply);
				reply2List.add(reply2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return reply2List;
	}

	@Override
	public boolean addReply2(BbsReply reply) {
		boolean result = false;
		String sql ="INSERT INTO t_bbsReply(ID,Postid,Loginid,Acceptid,Content,Createtime,Parentid)"
				+ "VALUES(seq_t_bbsReply.NEXTVAL,?,?,?,?,SYSDATE,?)";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, reply.getBbspost().getId());
			pst.setInt(2, reply.getLoginUser().getId());
			pst.setInt(3, reply.getAcceptUser().getId());
			pst.setString(4, reply.getContent());
			pst.setInt(5, reply.getBbsReply().getId());
			int num = pst.executeUpdate();
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pst, conn);
		}
		return result;
	}

//	@Override
//	public BbsReply findByid(int id) {
//		BbsReply bbsReply = new BbsReply();
//		String sql ="select * from t_bbsReply where id="+id;
//		Connection conn = null;
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		try {
//			conn = DBUtils.getConnection();
//			pst = conn.prepareStatement(sql);
//			rs=pst.executeQuery();
//			while(rs.next()){
//				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DBUtils.close(rs, pst, conn);
//		}
//		return bbsReply;
//	}

}
