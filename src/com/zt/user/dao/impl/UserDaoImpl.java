package com.zt.user.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.user.dao.UserDao;
import com.zt.user.po.Role;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User findUserById(int id) {
		User user = null;
		String sql = "select * from ( select a.*,b.name roleName from (select u.id,u.photo,u.name userName,u.pass,u.accountName,u.roleId,u.sex,u.birthday,u.bbsScore,u.examScore,u.status,u.createId,u.createTime,u.rank,v.name createName from t_sysUser u left join t_sysUser v on u.createId = v.id) a left join t_sysRole b on a.roleId = b.id order by a.id ) where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPhoto(rs.getString("photo"));
				user.setName(rs.getString("userName"));
				user.setPass(rs.getString("pass"));
				user.setAccountName(rs.getString("accountName"));
				user.setStatus(rs.getString("status"));
				user.setSex(rs.getString("sex"));
				user.setBirthday(rs.getDate("birthday"));
				Role role = new Role();
				role.setId(rs.getInt("roleId"));
				role.setName(rs.getString("roleName"));
				user.setRole(role);
				user.setBbsScore(rs.getInt("bbsScore"));
				user.setExamScore(rs.getInt("examScore"));
				user.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return user;
	}

	@Override
	public User login(String accountName) {
		User user = null;
		String sql = "select u.*,r.name rname from t_sysUser u left join t_sysRole r"
				+ " on u.roleId=r.id where roleId != 5 and u.accountName=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, accountName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPhoto(rs.getString("photo"));
				user.setName(rs.getString("name"));
				user.setPass(rs.getString("pass"));
				user.setAccountName(rs.getString("accountName"));
				Role role = new Role();
				role.setId(rs.getInt("roleId"));
				role.setName(rs.getString("rname"));
				user.setBbsScore(rs.getInt("bbsScore"));
				user.setExamScore(rs.getDouble("ExamScore"));
				user.setRole(role);
				user.setStatus(rs.getString("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return user;
	}

	@Override
	public int getTotalSizeByFilter(Map filter) {
		String sql = "select count(*) totalSize from "
				+ "(select u.id,u.photo,u.name userName,u.pass,u.accountName,u.roleId,u.sex,u.birthday,u.bbsScore,u.examScore,u.status,u.createId,u.createTime,u.rank,v.name createName "
				+ "from t_sysUser u " + "left join t_sysUser v "
				+ "on u.createId = v.id) a " + "left join t_sysRole b "
				+ "on a.roleId = b.id where 1 = 1 ";
		if (filter.get("userName") != null) {
			sql += " and a.userName like '%" + filter.get("userName") + "%'";
		}
		if (filter.get("accountName") != null) {
			sql += " and a.accountName like '%" + filter.get("accountName")
					+ "%'";
		}
		if (filter.get("status") != null) {
			sql += " and a.status = '" + filter.get("status") + "'";
		}
		if (filter.get("roleId") != null) {
			sql += " and a.roleId in '" + filter.get("roleId") + "'";
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
	public List<User> findAllUser(Map filter, PageUtils pageUtils) {
		String sql = "select h.*,rownum r from (select a.*,b.name roleName from "
				+ "(select u.id,u.photo,u.name userName,u.pass,u.accountName,u.roleId,u.sex,u.birthday,u.bbsScore,u.examScore,u.status,u.createId,u.createTime,u.rank,v.name createName "
				+ "from t_sysUser u "
				+ "left join t_sysUser v "
				+ "on u.createId = v.id) a "
				+ "left join t_sysRole b "
				+ "on a.roleId = b.id order by a.id) h where 1 = 1 and h.id != 0 ";
		if (filter.get("userName") != null) {
			sql += " and userName like '%" + filter.get("userName") + "%'";
		}
		if (filter.get("accountName") != null) {
			sql += " and accountName like '%" + filter.get("accountName")
					+ "%'";
		}
		if (filter.get("status") != null) {
			sql += " and status = '" + filter.get("status") + "'";
		}
		if (filter.get("roleId") != null) {
			sql += " and roleId in '" + filter.get("roleId") + "'";
		}
		String newSql = "select * from (" + sql
				+ " and rownum <= ?) where r > ?";
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(newSql);
			pstmt.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			pstmt.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setPhoto(rs.getString("photo"));
				user.setName(rs.getString("userName"));
				user.setPass(rs.getString("pass"));
				user.setAccountName(rs.getString("accountName"));
				Role role = new Role();
				role.setId(rs.getInt("roleId"));
				role.setName(rs.getString("roleName"));
				user.setRole(role);
				user.setSex(rs.getString("sex"));
				user.setBirthday(rs.getDate("birthday"));
				user.setBbsScore(rs.getInt("bbsScore"));
				user.setExamScore(rs.getInt("examScore"));
				user.setStatus(rs.getString("status"));
				User user1 = new User();
				user1.setId(rs.getInt("createId"));
				user1.setName(rs.getString("createName"));
				user.setUser(user1);
				user.setCreateTime(rs.getDate("createTime"));
				user.setRank(rs.getString("rank"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return users;
	}

	@Override
	public boolean cancelUser(int userId) {
		String sql = "update t_sysUser set status = '0' where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
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
	public boolean restoreUser(int userId) {
		String sql = "update t_sysUser set status = '1' where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
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
	public boolean addUser(User user) {
		String sql = "insert into t_sysUser(id,photo,name,pass,accountName,Roleid"
				+ ",sex,Birthday,Bbsscore,Examscore,"
				+ "status,createId,Createtime,rank)"
				+ "values(seq_sysUser.nextval,?,?,?,?,?,?,?,?,?,'1',?,?,?)";
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPhoto());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPass());
			pstmt.setString(4, user.getAccountName());
			pstmt.setInt(5, user.getRole().getId());
			pstmt.setString(6, user.getSex());
			pstmt.setDate(7, new java.sql.Date(user.getBirthday().getTime()));
			pstmt.setInt(8, user.getBbsScore());
			pstmt.setInt(9, (int) user.getExamScore());
			// pstmt.setString(10, user.getStatus());
			pstmt.setInt(10, user.getUser().getId());
			pstmt.setDate(11, new java.sql.Date(user.getCreateTime().getTime()));
			pstmt.setString(12, user.getRank());
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
	public boolean updateUser(User user) {
		String sql = "update t_sysUser set photo = ?,name = ?,accountName = ?,roleId = ?"
				+ ",sex = ?,Birthday = ?,Bbsscore = ?,Examscore = ?,"
				+ "createId = ?,Createtime = ? where id = ?";
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPhoto());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getAccountName());
			pstmt.setInt(4, user.getRole().getId());
			pstmt.setString(5, user.getSex());
			pstmt.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
			pstmt.setInt(7, user.getBbsScore());
			pstmt.setInt(8, (int) user.getExamScore());
			// pstmt.setString(10, user.getStatus());
			pstmt.setInt(9, user.getUser().getId());
			pstmt.setDate(10, new java.sql.Date(user.getCreateTime().getTime()));
			pstmt.setInt(11, user.getId());
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
	public boolean resetPass(User user) {
		String sql = "update t_sysUser set pass = ? where id = ?";
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPass());
			pstmt.setInt(2, user.getId());
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
	public User fontLogin(String accountName) {
		User user = null;
		String sql = " select u.*,r.name rname "
				+ " from t_sysUser u left join t_sysRole r "
				+ " on u.roleId=r.id where u.roleId = 5 and u.accountName= ?  ";
		String sql2 = "select COUNT(*) postCount FROM t_bbsPost WHERE createId = "
				+ " (SELECT ID FROM t_sysUser WHERE accountName= ? ) GROUP BY createId ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, accountName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPhoto(rs.getString("photo"));
				user.setName(rs.getString("name"));
				user.setPass(rs.getString("pass"));
				user.setAccountName(rs.getString("accountName"));
				Role role = new Role();
				role.setId(rs.getInt("roleId"));
				role.setName(rs.getString("rname"));
				user.setRole(role);
				user.setBbsScore(rs.getInt("bbsScore"));
				user.setExamScore(rs.getDouble("ExamScore"));
				user.setStatus(rs.getString("status"));
				user.setCreateTime(rs.getDate("createTime"));
			}
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, accountName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setPostCount(rs.getInt("PostCount"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return user;
	}

	@Override
	public boolean regUser(User user) {
		String sql = "insert into t_sysUser(id,photo,name,pass,accountName,Roleid"
				+ ",sex,Birthday,Bbsscore,Examscore,"
				+ "status,createId,Createtime,rank)"
				+ "values(seq_sysUser.nextval,?,?,?,?,5,?,?,0,0,'1',seq_sysUser.nextval,?,null)";
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPhoto());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPass());
			pstmt.setString(4, user.getAccountName());
			pstmt.setString(5, user.getSex());
			pstmt.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
			pstmt.setDate(7, new java.sql.Date(user.getCreateTime().getTime()));
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
	public List<User> getScores() {
		String sql = "select * from t_sysUser where roleId = 5 order by examScore desc ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setExamScore(rs.getDouble("ExamScore"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return users;
	}

	@Override
	public List<User> findAllUser() {
		List<User> users = new ArrayList<User>();
		String sql = "select * from t_sysUser where roleId=5";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setPhoto(rs.getString("photo"));
				user.setName(rs.getString("name"));
				user.setPass(rs.getString("pass"));
				user.setAccountName(rs.getString("accountName"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return users;
	}

	@Override
	public boolean updateFontUser(User user) {
		String sql = "update t_sysUser set photo = ?,name = ?,pass = ? where id = ?";	
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPhoto());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPass());
			pstmt.setInt(4, user.getId());
			int num = pstmt.executeUpdate();
			if(num == 1){
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}
	
}
