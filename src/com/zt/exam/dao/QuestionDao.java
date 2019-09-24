package com.zt.exam.dao;

import java.util.List;
import java.util.Map;

import com.zt.exam.po.ExamQuestion;
import com.zt.exam.po.Option;
import com.zt.exam.po.Question;
import com.zt.exam.po.RuleDetail;
import com.zt.utils.PageUtils;

public interface QuestionDao {
	public boolean add(Question question, String[] contents);

	public boolean update(Question question, String[] contents);

	public boolean delete(int id);

	public boolean changeStatus(int id);

	public Question getQuestionById(int id);

	public int getTotalSize(Map<String, String> filter);

	public List<Question> findAll(Map<String, String> filter,
			PageUtils pageUtils);

	public List<Option> getOptionsByQuestionId(int id);
	
	public List<Option> getOptionsByQuestionId(List<Question> question);
	
	public List<Question> getExamPaper(int subId,List<RuleDetail> rds);
	
	public List<ExamQuestion> getExamPaper2(int subId,List<RuleDetail> rds);
}
