package com.zt.user.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.zt.user.dao.UserDao;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User findUserById(int id) {
		User user = null;
		String sql = "select * from t_sysUser where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPhoto(rs.getString("photo"));
				user.setName(rs.getString("name"));
				user.setPass(rs.getString("pass"));
				user.setAccountName(rs.getString("accountName"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return user;
	}

}
