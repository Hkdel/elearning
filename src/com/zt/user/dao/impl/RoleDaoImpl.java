package com.zt.user.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.user.dao.RoleDao;
import com.zt.user.po.Auth;
import com.zt.user.po.Role;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class RoleDaoImpl implements RoleDao {

	@Override
	public int getTotalSizeByFilter(Map filter) {
		String sql = "select count(*) totalSize from t_sysRole where 1 = 1";
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
		//System.out.println(sql);
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
	public List<Role> findAllRole(Map filter, PageUtils pageUtils) {
		String sql = "select e.*,rownum r from (select r.*,u.name createName "
				+ "from t_sysRole r left join t_sysUser u "
				+ "on r.createId = u.id order by r.id) e where 1 = 1";
		if (filter.get("name") != null) {
			sql += " and name like '%" + filter.get("name") + "%'";
		}
		if (filter.get("status") != null) {
			sql += " and status like '%" + filter.get("status") + "%'";
		}
		String newSql = "select * from(" + sql
				+ " and rownum <= ?) where r > ?";
		List<Role> roles = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//System.out.println(newSql);
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(newSql);
			pstmt.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			pstmt.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setStatus(rs.getString("status"));
				role.setCreateTime(rs.getDate("createTime"));
				User user = new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("createName"));
				role.setUser(user);
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return roles;
	}
	public List<Role> findAllRole(){
		List<Role> roles = new ArrayList();
		String sql = "select * from t_sysRole order by id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setStatus(rs.getString("status"));
				role.setCreateTime(rs.getDate("createTime"));
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return roles;
	}
	@Override
	public Role getRoleById(int id) {
		String sql = "select * from t_sysRole where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Role role = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setStatus(rs.getString("status"));
				role.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return role;
	}

	@Override
	public List<Auth> findRoleAuth(int id) {
		List<Auth> authList = new ArrayList();
		String sql = "select * from t_sysAuth where id in( "
				+ " select authId from t_sysRoleAuth where roleId=?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Auth auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				auth.setUrl(rs.getString("url"));
				auth.setStatus(rs.getString("status"));
				authList.add(auth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return authList;
	}
	//注销角色
	@Override
	public boolean cancelRole(int roleid) {
		String sql = "update t_sysRole set status = '0' where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try{
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleid);
			int num = pstmt.executeUpdate();
			//System.out.println(sql);
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
	//恢复角色
	@Override
	public boolean restoreRole(int roleId) {
		String sql = "update t_sysRole set status = '1' where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try{
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
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

	@Override
	public boolean updateRole(Role role) {
		String sql = "update t_sysRole set name = ? where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try{
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, role.getName());
			pstmt.setInt(2, role.getId());
			int num = pstmt.executeUpdate();
			//System.out.println(sql);
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
	//添加角色
	@Override
	public boolean addRole(Role role) {
		String sql = "insert into t_sysRole(id,name,status,createId,createTime) "
				+ "values(seq_sysRole.nextval,?,'1',?,?)";
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, role.getName());
			pstmt.setInt(2, role.getUser().getId());
			pstmt.setDate(3,new java.sql.Date(role.getCreateTime().getTime()) );
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
