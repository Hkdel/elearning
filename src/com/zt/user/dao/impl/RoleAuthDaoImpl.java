package com.zt.user.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.zt.user.dao.RoleAuthDao;
import com.zt.utils.DBUtils;

public class RoleAuthDaoImpl implements RoleAuthDao {

	@Override
	public boolean addRoleAuth(int roleId, int[] authIds) {
		String sql="insert into t_sysRoleAuth(roleId,authId) values(?,?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=true;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			for(int authId:authIds){
				pstmt.setInt(1, roleId);
				pstmt.setInt(2, authId);
				pstmt.addBatch();
			 }
			 pstmt.executeBatch();
		   }catch(Exception e) {
			   result=false;
		    e.printStackTrace();
	      }finally{
		     DBUtils.close(null, pstmt, conn);
          }
		return result;
	}
	public boolean delRoleAuth(int roleId){
		boolean result=true;
		String sql="delete from  t_sysRoleAuth where roleId=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			pstmt.executeUpdate();
		   }catch(Exception e) {
			   result=false;
		    e.printStackTrace();
	      }finally{
		     DBUtils.close(null, pstmt, conn);
          }
		return result;
	}

}