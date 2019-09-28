package com.zt.bbs.dao;

import java.util.List;
import java.util.Map;

import com.zt.bbs.po.BbsPlate;
import com.zt.utils.PageUtils;

public interface BbsPlateDao {
	public boolean addBbsPlate(BbsPlate bbsPlate);

	public boolean delBbsPlate(int id);

	public boolean updateBbsPlate(BbsPlate bbsPlate);

	public boolean updateStatus(int id, String status);

	public BbsPlate getById(int id);

	public List<BbsPlate> findAll();

	public int getTotalSizeByFilter(Map filter);

	public List<BbsPlate> findByPageFilter(Map filter, PageUtils page);
	

}
