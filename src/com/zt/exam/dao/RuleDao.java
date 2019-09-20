package com.zt.exam.dao;

import java.util.List;
import java.util.Map;

import com.zt.exam.po.Rule;
import com.zt.exam.po.RuleDetail;
import com.zt.utils.PageUtils;

public interface RuleDao {
	public boolean add(Rule rule, List<RuleDetail> ruleDetails);

	public boolean update(Rule rule, List<RuleDetail> ruleDetails);

	public boolean delete(int id);

	public boolean changeStatus(int id);

	public Rule getRuleById(int id);

	public int getTotalSize(Map<String, String> filter);

	public List<Rule> findAll(Map<String, String> filter, PageUtils pageUtils);

	public List<RuleDetail> getRuleDetailsByRuleId(int id);
	
	public List<RuleDetail> findAllRuleDetail();

}
