package com.zt.exam.dao;

import java.util.List;

import com.zt.exam.po.Option;

public interface OptionDao {
	public boolean add(Option option);
	
	public boolean update(Option option);
	
	public boolean delete(int id);
	
	public List<Option> findAll();
}
