package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.zt.exam.dao.TypeDao;
import com.zt.exam.po.Type;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class TypeDaoImpl implements TypeDao {

	@Override
	public List<Type> findAll(PageUtils pageUtils) {
		String sql = "select * from (select t.*,rownum rn from "
				+ " (select * from t_examType order by id) t where rownum <= ?) "
				+ " where rn > ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<Type> types = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			psm.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = psm.executeQuery();
			while (rs.next()) {
				Type type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				types.add(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return types;
	}

	@Override
	public int getTotalSize() {
		String sql = "select count(*) count from t_examType ";
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
	public Type getTypeById(int id) {
		String sql="select * from t_examType where id = ?";
		Connection conn=null; 
		PreparedStatement pstmt=null;
		ResultSet rs=null; 
		Type type=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				type=new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return type;
	}

	@Override
	public List<Type> findAll() {
		String sql = "select * from t_examType order by id ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<Type> types = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			rs = psm.executeQuery();
			while (rs.next()) {
				Type type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				types.add(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return types;
	}

}
