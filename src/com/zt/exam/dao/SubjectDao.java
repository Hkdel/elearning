package com.zt.exam.dao;

import java.util.List;
import java.util.Map;

import com.zt.exam.po.Subject;
import com.zt.utils.PageUtils;

public interface SubjectDao {
	public boolean add(Subject sub);

	public boolean update(Subject sub);

	public boolean delete(int id);
	
	public Subject getSubjectById(int id);
	
	public int getTotalSize(Map<String,String> filter);

	public List<Subject> findAll(Map<String, String> filter, PageUtils pageUtils);
	
	public List<Subject> findAll();
}
