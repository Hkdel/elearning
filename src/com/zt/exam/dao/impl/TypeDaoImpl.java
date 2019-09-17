package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.zt.exam.dao.TypeDao;
import com.zt.exam.po.Type;
import com.zt.utils.DBUtils;

public class TypeDaoImpl implements TypeDao {

	@Override
	public List<Type> findAll() {
		String sql = "select * from t_examType ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Type> types = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Type type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				types.add(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return types;
	}

}
