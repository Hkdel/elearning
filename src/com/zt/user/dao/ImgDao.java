package com.zt.user.dao;

import java.util.List;
import java.util.Map;

import com.zt.user.po.Photo;
import com.zt.utils.PageUtils;

public interface ImgDao {
	//��ѯ����ͼƬ������
	public int getTotalSize();
	//��ҳ��ѯͼƬ��¼
	public List<Photo> findAllImg(PageUtils pageUtils);
	//���ͼƬ
	public boolean addPhoto(Photo photo);
	//ɾ��ͼƬ
	public boolean deletePhoto(int imgId);
	//�༭ͼƬ
	public boolean updatePhoto(Photo photo);
	//����id����ͼƬ
	public Photo findPhotoById(int id);
	//��ѯǰ��ҳ��ͼƬ
	public List<Photo> findFontPageImg();
}
