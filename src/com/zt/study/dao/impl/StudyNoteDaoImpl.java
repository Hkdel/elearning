package com.zt.study.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.study.dao.StudyNoteDao;
import com.zt.study.po.StudyNote;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class StudyNoteDaoImpl implements StudyNoteDao {

	@Override
	public boolean addStudyNote(StudyNote note) {
		String sql="insert into t_studyNote(id,name,title,content,createId,createTime)"
				+ " values(seq_studyNote.nextval,?,?,?,?,sysdate)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, note.getName());
			pstmt.setString(2, note.getTitle());
			pstmt.setString(3, note.getContent());
			pstmt.setInt(4, note.getUser().getId());
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
	public boolean updateStudyNote(StudyNote note) {
		String sql="update t_studyNote set name=?,title=?,content=?,createTime=sysdate where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, note.getName());
			pstmt.setString(2, note.getTitle());
			pstmt.setString(3, note.getContent());
			pstmt.setInt(4, note.getId());
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
	public boolean delStudyNote(int noteId) {
		String sql="delete from t_studyNote where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		boolean result=false;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noteId);
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
	public StudyNote getStudyNoteById(int noteId) {
		String sql="select * from t_studyNote where id=?";
		StudyNote note=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noteId);
			rs=pstmt.executeQuery();
			if(rs.next()){
				note=new StudyNote();
				note.setId(rs.getInt("id"));
				note.setName(rs.getString("name"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setCreateTime(rs.getDate("createTime"));
				User user=new User();
				user.setId(rs.getInt("createId"));
				note.setUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return note;
	}

	@Override
	public int getTotalSize(Map<Object, Object> filter,int loginId) {
		String sql="select count(*) from (select * from t_studyNote where createId=?) where 1=1";
		if(filter.get("name")!=null){
			sql+=" and name like '%"+filter.get("name")+"%'";
		}

		String begin=(String)filter.get("begin");
		String end=(String)filter.get("end");
		if(begin!=null){
			sql+=" and createTime >= to_date('"+begin+"','yyyy-MM-dd')";
		}
		if(end!=null){
			sql+=" and createTime <= to_date('"+end+"','yyyy-MM-dd')";
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
	public List<StudyNote> searchNotesByPage(Map<Object, Object> filter,
			PageUtils pageUtils,int loginId) {
		List<StudyNote> list=new ArrayList<StudyNote>();
		
		String sql="select t.*,rownum n from (select * from t_studyNote where createId=? order by createTime desc) t  where 1=1 ";
		if(filter.get("name")!=null){
			sql+=" and name like '%"+filter.get("name")+"%'";
		}

		String begin=(String)filter.get("begin");
		String end=(String)filter.get("end");
		if(begin!=null){
			sql+=" and createTime >= to_date('"+begin+"','yyyy-MM-dd')";
		}
		if(end!=null){
			sql+=" and createTime <= to_date('"+end+"','yyyy-MM-dd')";
		}
		sql+=" and rownum<=?";
		String newsql="select * from ("+sql+")  where n>?";

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
				StudyNote note=new StudyNote();
				note.setId(rs.getInt("id"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setCreateTime(rs.getDate("createTime"));
				User user=new User();
				user.setId(rs.getInt("createId"));
				note.setUser(user);
				list.add(note);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(rs, pstmt, conn);
		}
		return list;
	}

}
