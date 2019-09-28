package com.zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.bbs.dao.BbsPlateDao;
import com.zt.bbs.po.BbsPlate;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class BbsPlateDaoImpl implements BbsPlateDao {

	@Override
	public boolean addBbsPlate(BbsPlate bbsPlate) {
		boolean result = false;
		String sql = "INSERT INTO t_bbsPlate(ID,Photo,Name,introduction,Createid,Status,Createtime) "
				+ "VALUES(seq_t_bbsPlate.Nextval,?,?,?,?,'1',SYSDATE)";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, bbsPlate.getPhoto());
			pst.setString(2, bbsPlate.getName());
			pst.setString(3, bbsPlate.getIntroduction());
			pst.setInt(4, bbsPlate.getCreateUser().getId());
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
	public boolean delBbsPlate(int id) {
		boolean result = false;
		String sql = "DELETE FROM t_bbsPlate WHERE ID=?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
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
	public boolean updateBbsPlate(BbsPlate bbsPlate) {
		boolean result = false;
		String sql = "update t_bbsPlate set name=?,photo=?,introduction=? where id=?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, bbsPlate.getName());
			pst.setString(2, bbsPlate.getPhoto());//
			pst.setString(3, bbsPlate.getIntroduction());
			pst.setInt(4, bbsPlate.getId());
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
	public boolean updateStatus(int id, String status) {
		boolean result = false;
		String sql = "update t_bbsPlate set status=? where id=?";
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, status);
			pst.setInt(2, id);
			int num = pst.executeUpdate();
			System.out.println(num);
			if (num == 1) {
				result = true;
//				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pst, conn);
		}
		return result;
	}

	@Override
	public BbsPlate getById(int id) {
		String sql = "SELECT b.*,u.NAME uName FROM t_bbsPlate b,t_sysUser u WHERE b.createId=u.ID AND b.ID=?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		BbsPlate bbsPlate = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				bbsPlate = new BbsPlate();
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pst, conn);
		}
		return bbsPlate;
	}

	@Override
	public List<BbsPlate> findAll() {
		List<BbsPlate> plateList = new ArrayList<BbsPlate>();
		String sql = "SELECT b.*,u.NAME uName,n.s num FROM t_bbsPlate b,t_sysUser u ,"
				+ "(SELECT plateId,COUNT(*) s FROM t_bbsPost WHERE checkStatus='1' GROUP BY plateId) n "
				+ "WHERE b.createId=u.ID AND n.plateId(+)=b.ID and b.status='1' ORDER BY b.ID ASC";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
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
				bbsPlate.setPostSum(rs.getInt("num"));
				plateList.add(bbsPlate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pst, conn);
		}
		return plateList;
	}

	@Override
	public int getTotalSizeByFilter(Map filter) {
		// List<BbsPlate> plateList = new ArrayList<BbsPlate>();
		String sql = "SELECT COUNT(*) FROM t_bbsPlate b,t_sysUser u WHERE b.createId=u.ID";
		if (filter.get("bName") != null) {
			sql += " and b.name like '%" + filter.get("bName") + "%'";
		}
		String begin = (String) filter.get("begin");
		String end = (String) filter.get("end");
		if (begin != null) {
			sql += " and b.createTime >= to_date('" + begin + "','yyyy-MM-dd')";
		}
		if (end != null) {
			sql += " and b.createTime <= to_date('" + end + "','yyyy-MM-dd')";
		}
		if (filter.get("status") != null) {
			sql += " AND b.status='" + filter.get("status") + "'";
		}
		if(filter.get("uName") != null) {
			sql += " and u.name like '%" + filter.get("uName") + "%'";
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
	public List<BbsPlate> findByPageFilter(Map filter, PageUtils page) {
		String sql = "SELECT b.*,rownum n,u.name uName FROM t_bbsPlate b,t_sysUser u WHERE b.createId=u.ID";
		if (filter.get("bName") != null) {
			sql += " and b.name like '%" + filter.get("bName") + "%'";
		}
		String begin = (String) filter.get("begin");
		String end = (String) filter.get("end");
		if (begin != null) {
			sql += " and b.createTime >= to_date('" + begin + "','yyyy-MM-dd')";
		}
		if (end != null) {
			sql += " and b.createTime <= to_date('" + end + "','yyyy-MM-dd')";
		}
		if (filter.get("status") != null) {
			sql += " AND b.status='" + filter.get("status") + "'";
		}
		if(filter.get("uName") != null) {
			sql += " and u.name like '%" + filter.get("uName") + "%'";
		}
		sql += " and rownum<=? ";
		String newsql = "select * from (" + sql + ") where n>?";
		List<BbsPlate> plateList = new ArrayList<BbsPlate>();
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
	
}
