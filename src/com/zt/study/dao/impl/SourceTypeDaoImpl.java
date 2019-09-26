package com.zt.study.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.study.dao.SourceTypeDao;
import com.zt.study.po.SourceType;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class SourceTypeDaoImpl implements SourceTypeDao {

	@Override
	public boolean addSourceType(SourceType type) {
		String sql="insert into t_studyType(id,typeName,status,createId,createTime)"
				+ " values(seq_studyType.nextval,?,?,?,sysdate)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, type.getTypeName());
			pstmt.setString(2, type.getStatus());
			pstmt.setInt(3, type.getUser().getId());
			int num=pstmt.executeUpdate();
			if(num==1){
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public boolean updateSourceType(SourceType type) {
		String sql="update t_studyType set typeName=?,status=? where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, type.getTypeName());
			pstmt.setString(2, type.getStatus());
			pstmt.setInt(3, type.getId());
			int num=pstmt.executeUpdate();
			if(num==1){
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public boolean delSourceType(int typeId) {
		String sql="delete from t_studyType where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			int num=pstmt.executeUpdate();
			if(num==1){
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public SourceType getSourceTypeById(int typeId) {
		String sql="select t.*,u.name uname from t_studyType t,t_sysUser u where t.createid=u.id and t.id=?";
		SourceType type=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			rs=pstmt.executeQuery();
			if(rs.next()){
				type=new SourceType();
				type.setId(rs.getInt("id"));
				type.setTypeName(rs.getString("typeName"));
				type.setStatus(rs.getString("status"));
				type.setCreateTime(rs.getDate("createTime"));
				User user=new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("uname"));
				type.setUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return type;
	}

	@Override
	public int getTotalSize(Map<Object, Object> filter) {
		String sql="select count(*) from t_studyType where 1=1 ";
		if(filter.get("typeName")!=null){
			sql+=" and typeName like '%"+filter.get("typeName")+"%'";
		}
		if(filter.get("status")!=null){
			sql+=" and status ="+filter.get("status");
		}
		int totalSize=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				totalSize=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return totalSize;
	}

	@Override
	public List<SourceType> searchTypeByPage(Map<Object, Object> filter,
			PageUtils pageUtils) {
		String sql="select p.*,rownum n from "
				+ "(select t.*,u.name uname from t_studyType t,t_sysUser u "
				+ "where t.createid=u.id) p where 1=1";
		if(filter.get("typeName")!=null){
			sql+=" and typeName like '%"+filter.get("typeName")+"%'";
		}
		if(filter.get("status")!=null){
			sql+=" and status like '%"+filter.get("status")+"%'";
		}
		sql+=" and rownum<=?";
		String newsql="select * from ("+sql+")  where n>?";
		List<SourceType> types=new ArrayList<SourceType>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(newsql);
			pstmt.setInt(1, pageUtils.getCurrPage()*pageUtils.getPageSize());
			pstmt.setInt(2, (pageUtils.getCurrPage()-1)*pageUtils.getPageSize());
			rs=pstmt.executeQuery();
			while(rs.next()){
				SourceType type=new SourceType();
				type.setId(rs.getInt("id"));
				type.setTypeName(rs.getString("typeName"));
				type.setStatus(rs.getString("status"));
				type.setCreateTime(rs.getDate("createTime"));
				User user=new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("uname"));
				type.setUser(user);
				types.add(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return types;
	}

	@Override
	public boolean updateTypeStatus(SourceType type) {
		String sql="update t_studyType set  status=? where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, type.getStatus());
			pstmt.setInt(2, type.getId());
			int num=pstmt.executeUpdate();
			if(num==1){
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public List<SourceType> findAll() {
		List<SourceType> types=new ArrayList<SourceType>();
		String sql="select * from t_studyType";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				SourceType type=new SourceType();
				type.setId(rs.getInt("id"));
				type.setTypeName(rs.getString("typeName"));
				type.setStatus(rs.getString("status"));
				type.setCreateTime(rs.getDate("createTime"));
				User user=new User();
				user.setId(rs.getInt("createId"));
				type.setUser(user);
				types.add(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return types;
	}

	@Override
	public List<SourceType> findAllType() {
		List<SourceType> types=new ArrayList<SourceType>();
		String sql="select * from t_studyType where status='1'";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				SourceType type=new SourceType();
				type.setId(rs.getInt("id"));
				type.setTypeName(rs.getString("typeName"));
				type.setStatus(rs.getString("status"));
				type.setCreateTime(rs.getDate("createTime"));
				User user=new User();
				user.setId(rs.getInt("createId"));
				type.setUser(user);
				types.add(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return types;
	}

}
