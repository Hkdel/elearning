package com.zt.bbs.dao;

import java.util.List;
import java.util.Map;

import com.zt.bbs.po.BbsPlate;
import com.zt.bbs.po.BbsPost;
import com.zt.utils.PageUtils;

public interface FrontBbsDao {
	public int getTotalSizeFront();//可用模块数
	
	public int countPlateFront(int id);//根据模块ID查询可用帖子数

	public List<BbsPost> findByPageFront(int id,PageUtils page);
	
	public boolean addBbsPost(BbsPost post);
	
	public List<BbsPlate> findPlateGroup();//论坛首页模块查询
	
	public List<BbsPlate> findPlateByPage(PageUtils page);//论坛首页底部模块分页
	
	public List<BbsPost> findByName(String name);
	
	public List<BbsPost> findByPageFrontIsGood(int id,PageUtils page);
	
	public List<BbsPost> findByPageFrontByTime(int id,PageUtils page);
	
	public List<BbsPost> findByPageFrontByHeat(int id,PageUtils page);
}
