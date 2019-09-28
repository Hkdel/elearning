package com.zt.bbs.dao;

import java.util.List;
import java.util.Map;

import com.zt.bbs.po.BbsPlate;
import com.zt.bbs.po.BbsPost;
import com.zt.utils.PageUtils;

public interface FrontBbsDao {
	public int getTotalSizeFront();//����ģ����
	
	public int countPlateFront(int id);//����ģ��ID��ѯ����������

	public List<BbsPost> findByPageFront(int id,PageUtils page);
	
	public boolean addBbsPost(BbsPost post);
	
	public List<BbsPlate> findPlateGroup();//��̳��ҳģ���ѯ
	
	public List<BbsPlate> findPlateByPage(PageUtils page);//��̳��ҳ�ײ�ģ���ҳ
	
	public List<BbsPost> findByName(String name);
	
	public List<BbsPost> findByPageFrontIsGood(int id,PageUtils page);
	
	public List<BbsPost> findByPageFrontByTime(int id,PageUtils page);
	
	public List<BbsPost> findByPageFrontByHeat(int id,PageUtils page);
}
