package com.zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.bbs.dao.BbsPostDao;
import com.zt.bbs.po.BbsPlate;
import com.zt.bbs.po.BbsPost;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class BbsPostDaoImpl implements BbsPostDao {

	@Override
	public boolean addHeat(BbsPost post) {
		boolean result = false;
		String sql = "update t_bbsPost set heat=heat+1 where id=?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, post.getId());
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
	public boolean updateBbsPost(BbsPost post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delBbsPost(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BbsPost getBbsPostById(int id) {
		String sql = "SELECT t.*,b.s FROM(SELECT t.*,u.NAME uName,p.name pName,ch.name checkName "
				+ "FROM t_bbsPost t,t_sysUser u,t_bbsPlate p,t_sysUser ch WHERE t.createId=u.ID "
				+ "AND t.plateId=p.ID and ch.id=t.checkId and t.id=?) t,"
				+ "(SELECT createId,SUM(integral) s FROM t_bbsPost GROUP BY createId) b "
				+ "WHERE t.createId=b.createid";
		// System.out.println(sql);
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		BbsPost bbsPost = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("id"));
				bbsPost.setName(rs.getString("name"));
				// bbsPlate
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("plateId"));
				bbsPlate.setName(rs.getString("pName"));
				bbsPost.setBbsPlate(bbsPlate);
				// createUser
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("uName"));
				createUser.setBbsScore(rs.getInt("s"));
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pst, conn);
		}
		return bbsPost;
	}

	@Override
	public int count(Map filter) {
		String sql = "SELECT COUNT(*) FROM t_bbsPost t ,t_bbsPlate p WHERE t.plateId = p.ID ";
		if (filter.get("name") != null) {
			sql += " and name like '%" + filter.get("name") + "%'";
		}
		String begin = (String) filter.get("begin");
		String end = (String) filter.get("end");
		if (begin != null) {
			sql += " and createTime >= to_date('" + begin + "','yyyy-MM-dd')";
		}
		if (end != null) {
			sql += " and createTime <= to_date('" + end + "','yyyy-MM-dd')";
		}
		if (filter.get("checkStatus") != null) {
			sql += " AND checkStatus = '" + filter.get("checkStatus") + "'";
		}
		if (filter.get("plateName") != null) {
			sql += " and p.name like '%" + filter.get("plateName") + "%'";
		}
		int totalSize = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalSize = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return totalSize;
	}

	@Override
	public List<BbsPost> findByPage(Map filter, PageUtils page) {
		String sql = "SELECT b.*,ROWNUM n,cr.name createName,ch.name checkName,p.name pname "
				+ "FROM t_bbsPost b,t_sysUser cr,t_sysUser ch,t_bbsPlate p "
				+ "where b. plateId=p.ID AND b.createId=cr.ID AND b.checkId=ch.ID";
		if (filter.get("name") != null) {
			sql += " and b.name like '%" + filter.get("name") + "%'";
		}
		String begin = (String) filter.get("begin");
		String end = (String) filter.get("end");
		if (begin != null) {
			sql += " and b.createTime >= to_date('" + begin + "','yyyy-MM-dd')";
		}
		if (end != null) {
			sql += " and b.createTime <= to_date('" + end + "','yyyy-MM-dd')";
		}
		if (filter.get("checkStatus") != null) {
			sql += " AND b.checkStatus = '" + filter.get("checkStatus") + "'";
		}
		if (filter.get("plateName") != null) {
			sql += " and p.name like '%" + filter.get("plateName") + "%'";
		}
		sql += " and rownum<=? ";
		String newsql = " select * from (" + sql + ") where n>? order by createTime desc";
		List<BbsPost> postList = new ArrayList<BbsPost>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(newsql);
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
	public boolean auditBbsPost(BbsPost post) {
		boolean result = false;
		String sql = "update t_bbsPost set checkStatus=?,opinion=?,checkTime=sysdate,checkId=? where id=?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, post.getCheckStatus());
			pst.setString(2, post.getOpinion());
			pst.setInt(3, post.getCheckUser().getId());
			//System.out.println(post.getCheckUser().getId());
			pst.setInt(4, post.getId());
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
	public boolean auditAddIntegral(int id, int integral) {
		boolean result = false;
		String sql = " UPDATE t_bbsPost SET integral=integral+? WHERE ID =?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, integral);
			pst.setInt(2, id);
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
	public boolean postIsGood(int id, String isGood) {
		boolean result = false;
		String sql = "update t_bbsPost set isGood=? where id=?";
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, isGood);
			pst.setInt(2, id);
			int num = pst.executeUpdate();
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pst, conn);
		}
		return result;
	}

	@Override
	public boolean postIsTop(int id, String isTop) {
		boolean result = false;
		String sql = "update t_bbsPost set isTop=? where id=?";
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, isTop);
			pst.setInt(2, id);
			int num = pst.executeUpdate();
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pst, conn);
		}
		return result;
	}

	@Override
	public List<BbsPost> findAll() {
		List<BbsPost> postList = new ArrayList<BbsPost>();
		String sql = "SELECT t.*,u.NAME uName FROM t_bbsPost t,t_sysUser u WHERE t.createId=u.ID "
				+ "AND checkstatus='1' ORDER BY t.createTime DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BbsPost bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("id"));
				bbsPost.setName(rs.getString("name"));
				// bbsPlate
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("plateId"));
				bbsPost.setBbsPlate(bbsPlate);
				// createUser
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("uName"));
				bbsPost.setCreateUser(createUser);
				bbsPost.setCreateTime(rs.getDate("createTime"));
				bbsPost.setCheckStatus(rs.getString("checkStatus"));
				// checkUser
				User checkUser = new User();
				checkUser.setId(rs.getInt("checkId"));
				bbsPost.setCheckUser(checkUser);
				bbsPost.setCheckTime(rs.getDate("checkTime"));
				bbsPost.setIsGood(rs.getString("isGood"));
				bbsPost.setIsTop(rs.getString("isTop"));
				bbsPost.setHeat(rs.getInt("heat"));
				bbsPost.setIntegral(rs.getInt("integral"));
				bbsPost.setOpinion(rs.getString("opinion"));
				bbsPost.setContent(rs.getString("content"));
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
	public List<BbsPost> findHeat() {
		List<BbsPost> postList = new ArrayList<BbsPost>();
		String sql = "SELECT t.*,u.NAME uName FROM t_bbsPost t,t_sysUser u WHERE t.createId=u.ID "
				+ "AND checkstatus='1' ORDER BY t.heat DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BbsPost bbsPost = new BbsPost();
				bbsPost.setId(rs.getInt("id"));
				bbsPost.setName(rs.getString("name"));
				// bbsPlate
				BbsPlate bbsPlate = new BbsPlate();
				bbsPlate.setId(rs.getInt("plateId"));
				bbsPost.setBbsPlate(bbsPlate);
				// createUser
				User createUser = new User();
				createUser.setId(rs.getInt("createId"));
				createUser.setName(rs.getString("uName"));
				bbsPost.setCreateUser(createUser);
				bbsPost.setCreateTime(rs.getDate("createTime"));
				bbsPost.setCheckStatus(rs.getString("checkStatus"));
				// checkUser
				User checkUser = new User();
				checkUser.setId(rs.getInt("checkId"));
				bbsPost.setCheckUser(checkUser);
				bbsPost.setCheckTime(rs.getDate("checkTime"));
				bbsPost.setIsGood(rs.getString("isGood"));
				bbsPost.setIsTop(rs.getString("isTop"));
				bbsPost.setHeat(rs.getInt("heat"));
				bbsPost.setIntegral(rs.getInt("integral"));
				bbsPost.setOpinion(rs.getString("opinion"));
				bbsPost.setContent(rs.getString("content"));
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
	public List<BbsPost> findGood() {
		List<BbsPost> postList = new ArrayList<BbsPost>();
		String sql = "SELECT t.*,u.NAME uName,pl.name pname FROM t_bbsPost t,t_sysUser u,t_bbsPlate pl WHERE t.createId=u.ID "
				+ "AND checkstatus='1' and isGood='1' ORDER BY t.createTime DESC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
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
				createUser.setName(rs.getString("uName"));
				bbsPost.setCreateUser(createUser);
				bbsPost.setCreateTime(rs.getDate("createTime"));
				bbsPost.setCheckStatus(rs.getString("checkStatus"));
				// checkUser
				User checkUser = new User();
				checkUser.setId(rs.getInt("checkId"));
				bbsPost.setCheckUser(checkUser);
				bbsPost.setCheckTime(rs.getDate("checkTime"));
				bbsPost.setIsGood(rs.getString("isGood"));
				bbsPost.setIsTop(rs.getString("isTop"));
				bbsPost.setHeat(rs.getInt("heat"));
				bbsPost.setIntegral(rs.getInt("integral"));
				bbsPost.setOpinion(rs.getString("opinion"));
				bbsPost.setContent(rs.getString("content"));
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
