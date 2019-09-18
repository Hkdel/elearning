package com.zt.exam.dao;

import java.util.List;
import java.util.Map;

import com.zt.exam.po.Question;
import com.zt.utils.PageUtils;

public interface QuestionDao {
	public boolean add(Question question, String[] contents);

	public boolean update(Question question);

	public boolean delete(int id);

	public boolean changeStatus(int id);

	public Question getQuestionById(int id);

	public int getTotalSize(Map<String, String> filter);

	public List<Question> findAll(Map<String, String> filter,
			PageUtils pageUtils);
}
