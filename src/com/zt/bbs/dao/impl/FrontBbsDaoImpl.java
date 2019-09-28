package com.zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.bbs.dao.FrontBbsDao;
import com.zt.bbs.po.BbsPlate;
import com.zt.bbs.po.BbsPost;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class FrontBbsDaoImpl implements FrontBbsDao {
	@Override
	public int countPlateFront(int id) {
		String sql = "SELECT COUNT(*) a FROM t_bbsPost WHERE checkstatus = '1' AND plateId = "+id;
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
	
	public int getTotalSizeFront() {
		String sql = "SELECT COUNT(*) a FROM t_bbsPlate WHERE status = '1'";
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
	public List<BbsPost> findByPageFront(int id,PageUtils page) {
		String sql = "SELECT * FROM (SELECT t.*,ROWNUM n,b.a FROM "
				+ "(SELECT b.*,cr.name createName,ch.name checkName,p.name pname FROM t_bbsPost b,t_sysUser cr,"
				+ "t_sysUser ch,t_bbsPlate p where b. plateId=p.ID AND b.createId=cr.ID AND b.checkId=ch.ID "
				+ "AND checkStatus = '1') t,(SELECT postId,COUNT(*) a FROM t_bbsReply GROUP BY postId) b "
				+ "WHERE t.ID=b.postId(+) AND t.plateId="+id+" AND ROWNUM<=? ORDER BY istop DESC) WHERE n>?";
		List<BbsPost> postList = new ArrayList<BbsPost>();
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
				BbsPost bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("id"));
				bbsPost.setName(rs.getString("name"));
				// bbsPlate
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("plateId"));
				bbsPlate.setName(rs.getString("pname"));
				bbsPost.setBbsPlate(bbsPlate);
				// createUser
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("createName"));
				bbsPost.setCreateUser(createUser);
				bbsPost.setCreateTime(rs.getDate("createTime"));
				bbsPost.setCheckStatus(rs.getString("checkStatus"));
				// checkUser
				User checkUser = new User();
				checkUser.setId(rs.getInt("checkId"));
				checkUser.setName(rs.getString("checkName"));
				bbsPost.setCheckUser(checkUser);
				bbsPost.setCheckTime(rs.getDate("checkTime"));
				bbsPost.setIsGood(rs.getString("isGood"));
				bbsPost.setIsTop(rs.getString("isTop"));
				bbsPost.setHeat(rs.getInt("heat"));
				bbsPost.setIntegral(rs.getInt("integral"));
				bbsPost.setOpinion(rs.getString("opinion"));
				bbsPost.setContent(rs.getString("content"));
				bbsPost.setReplySum(rs.getInt("a"));
				postList.add(bbsPost);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return postList;
	}

	@Override
	public boolean addBbsPost(BbsPost post) {
		boolean result = false;
		String sql = "INSERT INTO t_bbsPost(ID, Name,Plateid,Createid,Createtime,Checkstatus,"
				+ "Checkid,Checktime,Isgood,Istop,heat,Integral,Opinion,content)"
				+ "VALUES(seq_t_bbsPost.Nextval,?,?,?,SYSDATE,'0','0',NULL,"
				+ "'0','0','0','0',NULL,?)";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, post.getName());
			pst.setInt(2, post.getBbsPlate().getId());
			pst.setInt(3, post.getCreateUser().getId());
			pst.setString(4, post.getContent());
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
	public List<BbsPlate> findPlateGroup() {
		List<BbsPlate> plateList = new ArrayList<BbsPlate>();
		String sql = "SELECT be.*,c.n FROM t_bbsPlate be,"
				+ "(SELECT plateid,COUNT(*) n FROM t_bbsPost GROUP BY plateId) c "
				+ "WHERE be.ID=c.plateid(+)";

		return plateList;
	}

	@Override
	public List<BbsPlate> findPlateByPage(PageUtils page) {
		List<BbsPlate> plateList = new ArrayList<BbsPlate>();
		String sql="select * from (SELECT b.*,rownum n,u.name uName "
				+ "FROM t_bbsPlate b,t_sysUser u WHERE b.createId=u.ID AND b.status='1' and rownum<=?  )"
				+ "where n>? ";
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
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("id"));
				bbsPlate.setPhoto(rs.getString("photo"));
				bbsPlate.setName(rs.getString("name"));
				bbsPlate.setIntroduction(rs.getString("introduction"));
				bbsPlate.setStatus(rs.getString("status"));
				bbsPlate.setCreateTime(rs.getDate("createTime"));
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("uName"));
				bbsPlate.setCreateUser(createUser);
				plateList.add(bbsPlate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return plateList;
	}

	@Override
	public List<BbsPost> findByName(String name) {
		List<BbsPost> postList = new ArrayList<BbsPost>();
		String sql = "SELECT * FROM (SELECT t.NAME,t.content,u.NAME uName,u.ID userid "
				+ "FROM t_bbsPost t,t_sysUser u WHERE t.createid=u.ID AND t.checkstatus='1' and t.NAME LIKE '%"+name+"%') m,"
				+ "(SELECT createId,SUM(integral) s FROM t_bbsPost GROUP BY createId) n WHERE m.userid=n.createid";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()){
				BbsPost post = new BbsPost();
				User createUser = new User();
				createUser.setId(rs.getInt("userid"));
				createUser.setName(rs.getString("uName"));
				createUser.setBbsScore(rs.getInt("s"));
				post.setCreateUser(createUser);
				post.setName(rs.getString("name"));
				post.setContent(rs.getString("content"));
				postList.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return postList;
	}

	@Override
	public List<BbsPost> findByPageFrontIsGood(int id, PageUtils page) {
		String sql = "SELECT * FROM (SELECT t.*,ROWNUM n,b.a FROM "
				+ "(SELECT b.*,cr.name createName,ch.name checkName,p.name pname FROM t_bbsPost b,t_sysUser cr,"
				+ "t_sysUser ch,t_bbsPlate p where b. plateId=p.ID AND b.createId=cr.ID AND b.checkId=ch.ID "
				+ "AND checkStatus = '1') t,(SELECT postId,COUNT(*) a FROM t_bbsReply GROUP BY postId) b "
				+ "WHERE t.ID=b.postId(+) AND t.plateId="+id+" AND ROWNUM<=? ORDER BY istop DESC) WHERE n>?"
						+ " and isGood='1'";
		List<BbsPost> postList = new ArrayList<BbsPost>();
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
				BbsPost bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("id"));
				bbsPost.setName(rs.getString("name"));
				// bbsPlate
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("plateId"));
				bbsPlate.setName(rs.getString("pname"));
				bbsPost.setBbsPlate(bbsPlate);
				// createUser
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("createName"));
				bbsPost.setCreateUser(createUser);
				bbsPost.setCreateTime(rs.getDate("createTime"));
				bbsPost.setCheckStatus(rs.getString("checkStatus"));
				// checkUser
				User checkUser = new User();
				checkUser.setId(rs.getInt("checkId"));
				checkUser.setName(rs.getString("checkName"));
				bbsPost.setCheckUser(checkUser);
				bbsPost.setCheckTime(rs.getDate("checkTime"));
				bbsPost.setIsGood(rs.getString("isGood"));
				bbsPost.setIsTop(rs.getString("isTop"));
				bbsPost.setHeat(rs.getInt("heat"));
				bbsPost.setIntegral(rs.getInt("integral"));
				bbsPost.setOpinion(rs.getString("opinion"));
				bbsPost.setContent(rs.getString("content"));
				bbsPost.setReplySum(rs.getInt("a"));
				postList.add(bbsPost);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return postList;
	}

	@Override
	public List<BbsPost> findByPageFrontByTime(int id, PageUtils page) {
		String sql = "SELECT * FROM (SELECT t.*,ROWNUM n,b.a FROM "
				+ "(SELECT b.*,cr.name createName,ch.name checkName,p.name pname FROM t_bbsPost b,t_sysUser cr,"
				+ "t_sysUser ch,t_bbsPlate p where b. plateId=p.ID AND b.createId=cr.ID AND b.checkId=ch.ID "
				+ "AND checkStatus = '1') t,(SELECT postId,COUNT(*) a FROM t_bbsReply GROUP BY postId) b "
				+ "WHERE t.ID=b.postId(+) AND t.plateId="+id+" AND ROWNUM<=? ORDER BY istop DESC) WHERE n>?"
						+ " order by createTime desc";
		List<BbsPost> postList = new ArrayList<BbsPost>();
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
				BbsPost bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("id"));
				bbsPost.setName(rs.getString("name"));
				// bbsPlate
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("plateId"));
				bbsPlate.setName(rs.getString("pname"));
				bbsPost.setBbsPlate(bbsPlate);
				// createUser
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("createName"));
				bbsPost.setCreateUser(createUser);
				bbsPost.setCreateTime(rs.getDate("createTime"));
				bbsPost.setCheckStatus(rs.getString("checkStatus"));
				// checkUser
				User checkUser = new User();
				checkUser.setId(rs.getInt("checkId"));
				checkUser.setName(rs.getString("checkName"));
				bbsPost.setCheckUser(checkUser);
				bbsPost.setCheckTime(rs.getDate("checkTime"));
				bbsPost.setIsGood(rs.getString("isGood"));
				bbsPost.setIsTop(rs.getString("isTop"));
				bbsPost.setHeat(rs.getInt("heat"));
				bbsPost.setIntegral(rs.getInt("integral"));
				bbsPost.setOpinion(rs.getString("opinion"));
				bbsPost.setContent(rs.getString("content"));
				bbsPost.setReplySum(rs.getInt("a"));
				postList.add(bbsPost);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return postList;
	}

	@Override
	public List<BbsPost> findByPageFrontByHeat(int id, PageUtils page) {
		String sql = "SELECT * FROM (SELECT t.*,ROWNUM n,b.a FROM "
				+ "(SELECT b.*,cr.name createName,ch.name checkName,p.name pname FROM t_bbsPost b,t_sysUser cr,"
				+ "t_sysUser ch,t_bbsPlate p where b. plateId=p.ID AND b.createId=cr.ID AND b.checkId=ch.ID "
				+ "AND checkStatus = '1') t,(SELECT postId,COUNT(*) a FROM t_bbsReply GROUP BY postId) b "
				+ "WHERE t.ID=b.postId(+) AND t.plateId="+id+" AND ROWNUM<=? ORDER BY istop DESC) WHERE n>?"
						+ " order by heat desc";
		List<BbsPost> postList = new ArrayList<BbsPost>();
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
				BbsPost bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("id"));
				bbsPost.setName(rs.getString("name"));
				// bbsPlate
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("plateId"));
				bbsPlate.setName(rs.getString("pname"));
				bbsPost.setBbsPlate(bbsPlate);
				// createUser
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("createName"));
				bbsPost.setCreateUser(createUser);
				bbsPost.setCreateTime(rs.getDate("createTime"));
				bbsPost.setCheckStatus(rs.getString("checkStatus"));
				// checkUser
				User checkUser = new User();
				checkUser.setId(rs.getInt("checkId"));
				checkUser.setName(rs.getString("checkName"));
				bbsPost.setCheckUser(checkUser);
				bbsPost.setCheckTime(rs.getDate("checkTime"));
				bbsPost.setIsGood(rs.getString("isGood"));
				bbsPost.setIsTop(rs.getString("isTop"));
				bbsPost.setHeat(rs.getInt("heat"));
				bbsPost.setIntegral(rs.getInt("integral"));
				bbsPost.setOpinion(rs.getString("opinion"));
				bbsPost.setContent(rs.getString("content"));
				bbsPost.setReplySum(rs.getInt("a"));
				postList.add(bbsPost);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return postList;
	}
	
}
