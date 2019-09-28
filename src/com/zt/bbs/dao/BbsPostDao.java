package com.zt.bbs.dao;

import java.util.List;
import java.util.Map;

import com.zt.bbs.po.BbsPost;
import com.zt.utils.PageUtils;

public interface BbsPostDao {
	public List<BbsPost> findAll();
	
	public boolean addHeat(BbsPost post);

	public boolean updateBbsPost(BbsPost post);

	public boolean delBbsPost(int id);

	public BbsPost getBbsPostById(int id);

	public int count(Map filter);

	public List<BbsPost> findByPage(Map filter, PageUtils page);
	
	public boolean auditBbsPost(BbsPost post);//���
	
	public boolean auditAddIntegral(int id,int integral);//���ӼӼ�����
	
	public boolean postIsGood(int id,String isGood);//��Ϊ������
	
	public boolean postIsTop(int id,String isTop);//�Ƿ��ö�
	
	public List<BbsPost> findHeat();
	
	public List<BbsPost> findGood();
	
	//public List<BbsPost> findByPageFront(PageUtils page);
}
