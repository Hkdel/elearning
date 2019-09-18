package com.zt.exam.dao;

import java.util.List;

import com.zt.exam.po.Type;
import com.zt.utils.PageUtils;

public interface TypeDao {
	public Type getTypeById(int id);
	
	public int getTotalSize();
	
	public List<Type> findAll();

	public List<Type> findAll(PageUtils pageUtils);
}
