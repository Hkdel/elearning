package com.zt.study.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.study.dao.StudyResourceDao;
import com.zt.study.po.SourceType;
import com.zt.study.po.StudyResource;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class StudyResourceDaoImpl implements StudyResourceDao {

	@Override
	public boolean addResource(StudyResource resource) {
		String sql="insert into t_studyResource(id,name,typeId,"
				+ "describe,uploadId,uploadTime,checkStatus,url)"
				+ " values(seq_studyResource.nextval,?,?,?,?,sysdate,'0',?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, resource.getName());
			pstmt.setInt(2, resource.getType().getId());
			pstmt.setString(3, resource.getDescribe());
			pstmt.setInt(4, resource.getUploadUser().getId());
			pstmt.setString(5, resource.getUrl());
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
	public boolean updateResource(StudyResource resource) {
		String sql="update t_studyResource set name=?,typeId=?,"
				+ "describe=?,url=?,uploadTime=sysdate,checkStatus='0' where checkStatus='2' and id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, resource.getName());
			pstmt.setInt(2, resource.getType().getId());
			pstmt.setString(3, resource.getDescribe());
			pstmt.setString(4, resource.getUrl());
			pstmt.setInt(5, resource.getId());
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
	public boolean checkResource(StudyResource resource) {
		String sql="update t_studyResource set checkStatus=?,checkId=?,"
				+ "checkTime=sysdate,checkOpinion=? where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, resource.getCheckStatus());
			pstmt.setInt(2, resource.getCheckUser().getId());
			pstmt.setString(3, resource.getCheckOpinion());
			pstmt.setInt(4, resource.getId());
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
	public StudyResource getResourceById(int resId) {
		String sql="select p.*,e.name ename from"
				+ "(select r.*,t.typeName tname,u.name uname "
				+ "from  t_studyResource r,t_studyType t,t_sysUser u  "
				+ "where r.typeid=t.id and r.uploadId=u.id) p left join t_sysUser e on p.checkid=e.id  where p.id=?";
		StudyResource source=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, resId);
			rs=pstmt.executeQuery();
			if(rs.next()){
				source=new StudyResource();
				source.setId(rs.getInt("id"));
				source.setName(rs.getString("name"));
				source.setDescribe(rs.getString("describe"));
				source.setUploadTime(rs.getDate("uploadTime"));
				source.setCheckStatus(rs.getString("checkStatus"));
				source.setUrl(rs.getString("url"));
				source.setCheckTime(rs.getDate("checkTime"));
				source.setCheckOpinion(rs.getString("checkOpinion"));
				
				SourceType type=new SourceType();
				type.setId(rs.getInt("typeId"));
				type.setTypeName(rs.getString("tname"));
				source.setType(type);
				User user=new User();
				user.setId(rs.getInt("uploadId"));
				user.setName(rs.getString("uname"));
				source.setUploadUser(user);
				User user2=new User();
				user2.setId(rs.getInt("checkId"));
				user2.setName(rs.getString("ename"));
				source.setCheckUser(user2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return source;
	}
	
	@Override
	public boolean delResource(int resId) {
		String sql="delete from t_studyResource where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, resId);
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
	public int getTotalSize(Map<Object, Object> filter) {
		String sql="select count(*) from t_studyResource where 1=1 ";
		if(filter.get("name")!=null){
			sql+=" and name like '%"+filter.get("name")+"%'";
		}
		if(filter.get("typeId")!=null){
			sql+=" and typeId ="+filter.get("name");
		}
		if(filter.get("checkStatus")!=null){
			sql+=" and checkStatus ="+filter.get("checkStatus");
		}
		if(filter.get("uploadId")!=null){
			sql+=" and uploadId ="+filter.get("uploadId");
		}
		
		String begin=(String)filter.get("begin");
		String end=(String)filter.get("end");
		if(begin!=null){
			sql+=" and uploadTime >= to_date('"+begin+"','yyyy-MM-dd')";
		}
		if(end!=null){
			sql+=" and uploadTime <= to_date('"+end+"','yyyy-MM-dd')";
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
	public List<StudyResource> searchSourceByPage(Map<Object, Object> filter,
			PageUtils pageUtils) {
		String sql="select a.*,rownum n from (select p.*,e.name checkName from "
				+ "(select r.*,t.typeName tname,u.name uname from t_studyResource r,"
				+ "t_studyType t,t_sysUser u where r.typeid=t.id and r.uploadId=u.id order by r.uploadTime desc) p"
				+ " left join t_sysUser e on p.checkid=e.id) a where 1=1";
		if(filter.get("name")!=null){
			sql+=" and a.name like '%"+filter.get("name")+"%'";
		}
		if(filter.get("typeId")!=null){
			sql+=" and a.typeId ="+filter.get("typeId");
		}
		if(filter.get("checkStatus")!=null){
			sql+=" and a.checkStatus ="+filter.get("checkStatus");
		}
		if(filter.get("uploadId")!=null){
			sql+=" and a.uploadId ="+filter.get("uploadId");
		}
		
		String begin=(String)filter.get("begin");
		String end=(String)filter.get("end");
		if(begin!=null){
			sql+=" and a.uploadTime >= to_date('"+begin+"','yyyy-MM-dd')";
		}
		if(end!=null){
			sql+=" and a.uploadTime <= to_date('"+end+"','yyyy-MM-dd')";
		}
		sql+=" and rownum<=?";
		String newsql="select * from ("+sql+")  where n>?";
		List<StudyResource> resList=new ArrayList<StudyResource>();
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
				StudyResource source=new StudyResource();
				source.setId(rs.getInt("id"));
				source.setName(rs.getString("name"));
				source.setDescribe(rs.getString("describe"));
				source.setUploadTime(rs.getDate("uploadTime"));
				source.setCheckStatus(rs.getString("checkStatus"));
				source.setCheckTime(rs.getDate("checkTime"));
				source.setCheckOpinion(rs.getString("checkOpinion"));
				source.setUrl(rs.getString("url"));
				SourceType type=new SourceType();
				type.setId(rs.getInt("typeId"));
				type.setTypeName(rs.getString("tname"));
				source.setType(type);
				User user=new User();
				user.setId(rs.getInt("uploadId"));
				user.setName(rs.getString("uname"));
				source.setUploadUser(user);
				User user2=new User();
				user2.setId(rs.getInt("checkId"));
				user2.setName(rs.getString("checkName"));
				source.setCheckUser(user2);
				resList.add(source);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return resList;
	}
	@Override
	public int getTotalSize2(Map<Object, Object> filter,int loginId) {
		String sql=" select count(*) from ("
				+ "(select * from t_studyResource r,t_studyType t where r.typeid=t.id and t.status='1' and r.checkstatus='1')"
				+ "union(select * from t_studyResource r,t_studyType t where"
				+ " r.typeid=t.id and t.status='1' and r.uploadId=? and r.checkstatus in(0,2))) where 1=1";
		if(filter.get("name")!=null){
			sql+=" and name like '%"+filter.get("name")+"%'";
		}
		if(filter.get("typeId")!=null){
			sql+=" and typeId ="+filter.get("name");
		}
		if(filter.get("checkStatus")!=null){
			sql+=" and checkStatus ="+filter.get("checkStatus");
		}
		if(filter.get("uploadId")!=null){
			sql+=" and uploadId ="+filter.get("uploadId");
		}
		
		String begin=(String)filter.get("begin");
		String end=(String)filter.get("end");
		if(begin!=null){
			sql+=" and uploadTime >= to_date('"+begin+"','yyyy-MM-dd')";
		}
		if(end!=null){
			sql+=" and uploadTime <= to_date('"+end+"','yyyy-MM-dd')";
		}
		int totalSize=0;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, loginId);
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
	public List<StudyResource> searchSourceByPage2(Map<Object, Object> filter,
			PageUtils pageUtils,int loginId) {
		String sql="select m.*,rownum n from"
				+ "((select p.*,e.name checkName from (select r.*,t.typeName tname,u.name uname from "
				+ " t_studyResource r,t_studyType t,t_sysUser u "
				+ " where r.typeid=t.id and r.uploadId=u.id and t.status='1' and r.checkstatus='1') p "
				+ " left join t_sysUser e on p.checkid=e.id) union "
				+ "(select p.*,e.name checkName from (select r.*,t.typeName tname,u.name uname from "
				+ " t_studyResource r,t_studyType t,t_sysUser u "
				+ " where r.typeid=t.id and r.uploadId=u.id and t.status='1'and r.uploadId=? and r.checkstatus in(0,2)) p "
				+ " left join t_sysUser e on p.checkid=e.id)) m where 1=1";
		if(filter.get("name")!=null){
			sql+=" and m.name like '%"+filter.get("name")+"%'";
		}
		if(filter.get("typeId")!=null){
			sql+=" and m.typeId ="+filter.get("typeId");
		}
		if(filter.get("checkStatus")!=null){
			sql+=" and m.checkStatus ="+filter.get("checkStatus");
		}
		if(filter.get("uploadId")!=null){
			sql+=" and m.uploadId ="+filter.get("uploadId");
		}
		
		String begin=(String)filter.get("begin");
		String end=(String)filter.get("end");
		if(begin!=null){
			sql+=" and a.uploadTime >= to_date('"+begin+"','yyyy-MM-dd')";
		}
		if(end!=null){
			sql+=" and a.uploadTime <= to_date('"+end+"','yyyy-MM-dd')";
		}
		sql+=" and rownum<=?";
		String newsql="select * from ("+sql+")  where n>?";
		List<StudyResource> resList=new ArrayList<StudyResource>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(newsql);
			pstmt.setInt(1, loginId);
			pstmt.setInt(2, pageUtils.getCurrPage()*pageUtils.getPageSize());
			pstmt.setInt(3, (pageUtils.getCurrPage()-1)*pageUtils.getPageSize());
			rs=pstmt.executeQuery();
			while(rs.next()){
				StudyResource source=new StudyResource();
				source.setId(rs.getInt("id"));
				source.setName(rs.getString("name"));
				source.setDescribe(rs.getString("describe"));
				source.setUploadTime(rs.getDate("uploadTime"));
				source.setCheckStatus(rs.getString("checkStatus"));
				source.setCheckTime(rs.getDate("checkTime"));
				source.setCheckOpinion(rs.getString("checkOpinion"));
				source.setUrl(rs.getString("url"));
				SourceType type=new SourceType();
				type.setId(rs.getInt("typeId"));
				type.setTypeName(rs.getString("tname"));
				source.setType(type);
				User user=new User();
				user.setId(rs.getInt("uploadId"));
				user.setName(rs.getString("uname"));
				source.setUploadUser(user);
				User user2=new User();
				user2.setId(rs.getInt("checkId"));
				user2.setName(rs.getString("checkName"));
				source.setCheckUser(user2);
				resList.add(source);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return resList;
	}


}
