package com.zt.user.dao;

import java.util.List;
import java.util.Map;

import com.zt.user.po.Photo;
import com.zt.utils.PageUtils;

public interface ImgDao {
	//查询所有图片的总数
	public int getTotalSize();
	//分页查询图片记录
	public List<Photo> findAllImg(PageUtils pageUtils);
	//添加图片
	public boolean addPhoto(Photo photo);
	//删除图片
	public boolean deletePhoto(int imgId);
	//编辑图片
	public boolean updatePhoto(Photo photo);
	//根据id查找图片
	public Photo findPhotoById(int id);
	//查询前端页面图片
	public List<Photo> findFontPageImg();
}
