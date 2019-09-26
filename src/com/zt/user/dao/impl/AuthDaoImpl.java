package com.zt.user.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPSize;

import com.zt.user.dao.AuthDao;
import com.zt.user.po.Auth;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class AuthDaoImpl implements AuthDao {

	@Override
	// 添加模块
	public boolean addAuth(Auth auth) {
		String sql = "insert into t_sysAuth"
				+ "(id,name,url,parentid,status,createid,createtime) "
				+ " values(seq_sysAuth.nextval,?,?,?,'1',?,?)";
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, auth.getName());
			pstmt.setString(2, auth.getUrl());
			if (auth.getParent() != null) {
				pstmt.setInt(3, auth.getParent().getId());
			} else {
				pstmt.setObject(3, null);
			}
			pstmt.setInt(4, auth.getUser().getId());
			// new java.sql.Date(role.getCreateTime().getTime())
			pstmt.setDate(5, new java.sql.Date(auth.getCreateTime().getTime()));
			int num = pstmt.executeUpdate();
			if (num == 1) {
				result = true;
				// System.out.println(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	// 修改模块
	public boolean updateAuth(Auth auth) {
		String sql = "update t_sysAuth set name = ?,url = ?,parentId = ? where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, auth.getName());
			pstmt.setString(2, auth.getUrl());
			if (auth.getParent() == null) {
				pstmt.setObject(3, null);
			} else {
				pstmt.setInt(3, auth.getParent().getId());
			}
			pstmt.setInt(4, auth.getId());
			int num = pstmt.executeUpdate();
			System.out.println(sql);
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public boolean cancelAuth(int authId) {
		String sql = "update t_sysAuth set status = '0' where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, authId);
			int num = pstmt.executeUpdate();
			// System.out.println(sql);
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public boolean restoreAuth(int authId) {
		String sql = "update t_sysAuth set status = '1' where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, authId);
			int num = pstmt.executeUpdate();
			if (num == 1) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public Auth getAuthById(int authId) {
		String sql = "select * from "
				+ "(select a.*,b.name createName "
				+ "from (select s.*,p.name pname "
				+ "from t_sysAuth s left join t_sysAuth p on s.parentid=p.id) a,t_Sysuser b "
				+ " where a.createId = b.id order by a.id) where id = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Auth auth = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, authId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				auth.setUrl(rs.getString("url"));
				auth.setStatus(rs.getString("status"));
				Auth parent = new Auth();
				parent.setId(rs.getInt("parentId"));
				parent.setName(rs.getString("pname"));
				auth.setParent(parent);
				User user = new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("createName"));
				auth.setUser(user);
				auth.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return auth;
	}

	public List<Auth> findAll() {
		List<Auth> authList = new ArrayList();
		String sql = "select * from "
				+ "(select a.*,b.name createName "
				+ "from (select s.*,p.name pname "
				+ "from t_sysAuth s left join t_sysAuth p on s.parentid=p.id) a,t_Sysuser b "
				+ " where a.createId = b.id order by a.id) ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(sql);
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Auth auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				auth.setUrl(rs.getString("url"));
				auth.setStatus(rs.getString("status"));
				Auth parent = new Auth();
				parent.setId(rs.getInt("parentId"));
				parent.setName(rs.getString("pname"));
				auth.setParent(parent);
				User user = new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("createName"));
				auth.setUser(user);
				auth.setCreateTime(rs.getDate("createTime"));
				authList.add(auth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return authList;
	}

	@Override
	public int getTotalSizeByFilter(Map filter) {
		String sql = "select count(*) totalSize from(select a.*,b.name createName "
				+ "from (select s.*,p.name pname "
				+ "from t_sysAuth s left join t_sysAuth p "
				+ "on s.parentid=p.id ) a,t_Sysuser b "
				+ "where a.createId = b.id order by a.id) where 1 = 1 ";
		if (filter.get("name") != null) {
			sql += " and name like '%" + filter.get("name") + "%'";
		}
		if (filter.get("status") != null) {
			sql += " and status like '%" + filter.get("status") + "%'";
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num = 0;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt("totalSize");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return num;
	}

	@Override
	public List<Auth> findAll(Map filter, PageUtils pageUtils) {
		String sql = "select e.*,rownum r from(select * from(select a.*,b.name createName "
				+ "from (select s.*,p.name pname "
				+ "from t_sysAuth s left join t_sysAuth p "
				+ "on s.parentid=p.id ) a,t_Sysuser b "
				+ "where a.createId = b.id order by a.id) ) e where 1 = 1 ";
		if (filter.get("name") != null) {
			sql += " and name like '%" + filter.get("name") + "%'";
		}
		if (filter.get("status") != null) {
			sql += " and status like '%" + filter.get("status") + "%'";
		}
		String newSql = "select * from(" + sql
				+ " and rownum <= ?) where r > ?";
		List<Auth> authList = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// System.out.println(newSql);
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(newSql);
			pstmt.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			pstmt.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Auth auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				auth.setUrl(rs.getString("url"));
				auth.setStatus(rs.getString("status"));
				Auth parent = new Auth();
				parent.setId(rs.getInt("parentId"));
				parent.setName(rs.getString("pname"));
				auth.setParent(parent);
				User user = new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("createName"));
				auth.setUser(user);
				auth.setCreateTime(rs.getDate("createTime"));
				authList.add(auth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return authList;
	}

	@Override
	public Auth getAuthParentById(int authId) {
		Auth auth = null;
		String sql = "select a.*,b.name createName "
				+ "from(select * from t_sysAuth where id in "
				+ "(select parentId from t_sysAuth where id = ?)) a, "
				+ "t_Sysuser b " + "where a.createId = b.id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, authId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				auth.setUrl(rs.getString("url"));
				auth.setStatus(rs.getString("status"));
				auth.setParent(null);
				User user = new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("createName"));
				auth.setUser(user);
				auth.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return auth;
	}

	@Override
	public List<Auth> findParentAuth() {
		String sql = "select * from t_sysAuth where parentId is null";
		List<Auth> parentList = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Auth auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				auth.setUrl(rs.getString("url"));
				auth.setStatus(rs.getString("status"));
				auth.setParent(null);
				auth.setCreateTime(rs.getDate("createTime"));
				parentList.add(auth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return parentList;
	}

}
