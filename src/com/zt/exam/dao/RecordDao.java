package com.zt.exam.dao;

import java.util.List;
import java.util.Map;

import com.zt.exam.po.Record;
import com.zt.exam.po.RecordDetail;
import com.zt.utils.PageUtils;

public interface RecordDao {
	public List<RecordDetail> findCorrect();
	
	public List<RecordDetail> findAll();
	
	public int getTotalSize(Map<String, String> filter);

	public List<Record> findAll(Map<String, String> filter,
			PageUtils pageUtils);
}
