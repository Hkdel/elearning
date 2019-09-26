package com.zt.exam.dao;

import java.util.List;
import java.util.Map;

import com.zt.exam.po.Question;
import com.zt.exam.po.Record;
import com.zt.exam.po.RecordDetail;
import com.zt.utils.PageUtils;

public interface RecordDao {
	
	public int add(Record record,List<Question> rds);
	
	public boolean update(Record record,List<RecordDetail> rds);
	
	public boolean delete(int recordId);
	
	public boolean correct(Record record,List<RecordDetail> rds);
	
	public List<RecordDetail> findCorrect(int id);
	
	public List<RecordDetail> findByRecordId(int id);
	
	public Record getRecordById(int id);
	
	public int getTotalSize(Map<String, String> filter);

	public List<Record> findAll(Map<String, String> filter,
			PageUtils pageUtils);
	
	public int getSubjective(int id);
	
	public List<Record> getRecordsById(int id);
	
}
