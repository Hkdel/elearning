package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.exam.dao.SubjectDao;
import com.zt.exam.po.Subject;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class SubjectDaoImpl implements SubjectDao {
	private UserDao userDao = new UserDaoImpl();

	@Override
	public boolean add(Subject sub) {
		String sql = "insert into t_examSubject values(seq_examSubject.nextval,?,'1',?,sysdate)";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setString(1, sub.getName());
			psm.setInt(2, sub.getCreateUser().getId());
			psm.executeUpdate();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {
			DBUtils.close(null, psm, conn);
		}
		return result;
	}

	@Override
	public boolean update(Subject sub) {
		String sql = "update t_examSubject set name = ? , createId = ? where id = ?";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setString(1, sub.getName());
			psm.setInt(2, sub.getCreateUser().getId());
			psm.setInt(3, sub.getId());
			psm.executeUpdate();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {
			DBUtils.close(null, psm, conn);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from t_examSubject where id = ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			psm.executeUpdate();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {
			DBUtils.close(null, psm, conn);
		}
		return result;
	}

	@Override
	public List<Subject> findAll(Map<String, String> filter, PageUtils pageUtils) {
		String sql = "select * from (select s.* , rownum rn FROM (select * from t_examSubject order by createTime desc ) s "
				+ "WHERE 1=1 ";
		if (filter.get("name") != null) {
			sql += " and s.name like '%" + filter.get("name") + "%' ";
		}
		String newSql = sql + " and rownum <= ? ) where rn > ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<Subject> subs = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(newSql);
			psm.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			psm.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = psm.executeQuery();
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				sub.setStatus(rs.getString("status"));
				User createUser = userDao.findUserById(rs.getInt("createId"));
				sub.setCreateUser(createUser);
				sub.setCreateTime(rs.getDate("createTime"));
				subs.add(sub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return subs;
	}

	@Override
	public int getTotalSize(Map<String, String> filter) {
		String sql = "select count(*) count from t_examSubject where 1=1 ";
		if (filter.get("name") != null) {
			sql += " and name like '%" + filter.get("name") + "%' ";
		}
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			rs = psm.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return count;
	}

	@Override
	public Subject getSubjectById(int id) {
		String sql = "select * from t_examSubject where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Subject sub = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				sub.setStatus(rs.getString("status"));
				User createUser = userDao.findUserById(rs.getInt("createId"));
				sub.setCreateUser(createUser);
				sub.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return sub;
	}

	@Override
	public List<Subject> findAll() {
		String sql = "select * from t_examSubject where status = '1' ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Subject> subs = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				sub.setStatus(rs.getString("status"));
				User createUser = userDao.findUserById(rs.getInt("createId"));
				sub.setCreateUser(createUser);
				sub.setCreateTime(rs.getDate("createTime"));
				subs.add(sub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return subs;
	}

}
