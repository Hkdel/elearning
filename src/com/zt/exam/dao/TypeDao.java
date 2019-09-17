package com.zt.exam.dao;

import java.util.List;
import java.util.Map;

import com.zt.exam.po.Type;
import com.zt.utils.PageUtils;

public interface TypeDao {
	public int getTotalSize();

	public List<Type> findAll(PageUtils pageUtils);
}
