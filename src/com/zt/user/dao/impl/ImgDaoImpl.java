package com.zt.user.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.PDLOverrideSupported;

import com.zt.user.dao.ImgDao;
import com.zt.user.po.Photo;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class ImgDaoImpl implements ImgDao {
	// 查询所有图片的总数
	@Override
	public int getTotalSize() {
		String sql = "select count(*) totalSize from t_sysPhoto";
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

	// 分页查询图片记录
	@Override
	public List<Photo> findAllImg(PageUtils pageUtils) {
		String sql = "select e.*,rownum r from (select p.*,u.name createName from t_sysphoto p left join t_sysUser u on p.createId = u.id) e ";
		String newSql = "select * from(" + sql
				+ " where rownum <= ? order by e.id) where r > ? order by id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Photo> photos = new ArrayList<Photo>();
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(newSql);
			pstmt.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			pstmt.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Photo photo = new Photo();
				photo.setId(rs.getInt("id"));
				photo.setUrl(rs.getString("url"));
				User user = new User();
				user.setId(rs.getInt("createId"));
				user.setName(rs.getString("createName"));
				photo.setUser(user);
				photo.setCreateTime(rs.getDate("createTime"));
				photo.setPlace(rs.getInt("place"));
				photos.add(photo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return photos;
	}

	// 添加图片
	@Override
	public boolean addPhoto(Photo photo) {
		String sql = "insert into t_sysPhoto(id,url,createId,createTime,place) values(seq_sysPhoto.nextval,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, photo.getUrl());
			pstmt.setInt(2, photo.getUser().getId());
			pstmt.setDate(3, new java.sql.Date(photo.getCreateTime().getTime()));
			pstmt.setInt(4, photo.getPlace());
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

	// 删除图片
	@Override
	public boolean deletePhoto(int imgId) {
		String sql = "delete from t_sysPhoto where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, imgId);
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

	// 编辑图片
	@Override
	public boolean updatePhoto(Photo photo) {
		String sql = "update t_sysPhoto set url = ?,place = ? where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, photo.getUrl());
			pstmt.setInt(2, photo.getPlace());
			pstmt.setInt(3, photo.getId());
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

	// 根据id查找图片
	@Override
	public Photo findPhotoById(int id) {
		String sql = "select * from t_sysPhoto where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Photo photo = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				photo = new Photo();
				photo.setId(rs.getInt("id"));
				photo.setUrl(rs.getString("url"));
				photo.setPlace(rs.getInt("place"));
				photo.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return photo;
	}

	// 查询前端页面图片（取数据库place前4张）
	@Override
	public List<Photo> findFontPageImg() {
		String sql = "select * from t_sysPhoto where place <=4";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Photo> photos = new ArrayList<Photo>();
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Photo photo = new Photo();
				photo.setId(rs.getInt("id"));
				photo.setUrl(rs.getString("url"));
				User user = new User();
				user.setId(rs.getInt("createId"));
				// user.setName(rs.getString("createName"));
				photo.setUser(user);
				photo.setCreateTime(rs.getDate("createTime"));
				photo.setPlace(rs.getInt("place"));
				photos.add(photo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return photos;
	}

}
