package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zt.exam.dao.RuleDao;
import com.zt.exam.dao.SubjectDao;
import com.zt.exam.po.Rule;
import com.zt.exam.po.RuleDetail;
import com.zt.exam.po.Subject;
import com.zt.exam.po.Type;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class RuleDaoImpl implements RuleDao {
	private SubjectDao subDao = new SubjectDaoImpl();
	private UserDao userDao = new UserDaoImpl();

	@Override
	public boolean add(Rule rule, List<RuleDetail> ruleDetails) {
		String sql = "insert into t_examRule "
				+ " values(seq_examRule.nextval,?,?,?,'1',?,?,sysdate,?)";
		String sql2 = "insert into t_examRuleDetail "
				+ " values(seq_examRuleDetail.nextval,seq_examRule.currval,?,?,?) ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			psm = conn.prepareStatement(sql);
			psm.setString(1, rule.getName());
			psm.setInt(2, rule.getSubject().getId());
			psm.setInt(3, rule.getTime());
			psm.setInt(4, rule.getScore());
			psm.setInt(5, rule.getCredit());
			psm.setInt(6, rule.getCreateUser().getId());
			psm.executeUpdate();
			if (ruleDetails != null && ruleDetails.size() != 0) {
				psm = conn.prepareStatement(sql2);
				for (RuleDetail rd : ruleDetails) {
					psm.setInt(1, rd.getType().getId());
					psm.setInt(2, rd.getNums());
					psm.setInt(3, rd.getScore());
					psm.addBatch();
				}
				psm.executeBatch();
			} else {
				result = false;
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			result = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtils.close(null, psm, conn);
		}
		return result;
	}

	@Override
	public boolean update(Rule rule, List<RuleDetail> ruleDetails) {
		String sql = "update t_examRule set time = ? , score = ? , credit = ? where id = ?";
		String sql2 = "delete from t_examRuleDetail where ruleId = ? ";
		String sql3 = "insert into t_examRuleDetail "
				+ "values(seq_examRuleDetail.nextval,?,?,?,?) ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			psm = conn.prepareStatement(sql);
			psm.setInt(1, rule.getTime());
			psm.setInt(2, rule.getScore());
			psm.setInt(3, rule.getCredit());
			psm.setInt(4, rule.getId());
			psm.executeUpdate();
			if (ruleDetails != null && ruleDetails.size() > 0) {
				psm = conn.prepareStatement(sql2);
				psm.setInt(1, rule.getId());
				psm.executeUpdate();
				psm = conn.prepareStatement(sql3);
				for (RuleDetail rd : ruleDetails) {
					psm.setInt(1, rule.getId());
					psm.setInt(2, rd.getType().getId());
					psm.setInt(3, rd.getNums());
					psm.setInt(4, rd.getScore());
					psm.addBatch();
				}
				psm.executeBatch();
			}
			conn.commit();
		} catch (Exception e) {
			result = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtils.close(null, psm, conn);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from t_examRuleDetail where ruleId = ? ";
		String sql2 = "delete from t_examRule where id = ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			psm.executeUpdate();
			psm = conn.prepareStatement(sql2);
			psm.setInt(1, id);
			psm.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			result = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBUtils.close(null, psm, conn);
		}
		return result;
	}

	@Override
	public boolean changeStatus(int id) {
		String sql = "update t_examRule set status = ? where id = ?";
		Rule rule = getRuleById(id);
		String status = rule.getStatus().equals("1") ? "0" : "1";
		Connection conn = null;
		boolean result = true;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {
			DBUtils.close(null, pstmt, conn);
		}
		return result;
	}

	@Override
	public Rule getRuleById(int id) {
		String sql = "select * from t_examRule where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Rule rule = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				rule = new Rule();
				rule.setId(rs.getInt("id"));
				rule.setName(rs.getString("name"));
				Subject subject = subDao.getSubjectById(rs.getInt("subjectId"));
				rule.setSubject(subject);
				rule.setTime(rs.getInt("time"));
				rule.setStatus(rs.getString("status"));
				rule.setScore(rs.getInt("score"));
				rule.setCredit(rs.getInt("credit"));
				User createUser = userDao.findUserById(rs.getInt("createId"));
				rule.setCreateUser(createUser);
				rule.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return rule;
	}

	@Override
	public int getTotalSize(Map<String, String> filter) {
		String sql = "select count(*) count from t_examRule where 1=1 ";
		if (filter.get("name") != null) {
			sql += " and name like '%" + filter.get("name") + "%' ";
		}
		if (filter.get("subId") != null) {
			sql += " and subjectId = " + Integer.parseInt(filter.get("subId"));
		}
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			rs = psm.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return count;
	}

	@Override
	public List<Rule> findAll(Map<String, String> filter, PageUtils pageUtils) {
		String sql = "select * from (select r.* , rownum rn FROM (select * from t_examRule order by createTime desc ) r "
				+ "WHERE 1=1 ";
		if (filter.get("name") != null) {
			sql += " and name like '%" + filter.get("name") + "%' ";
		}
		if (filter.get("subId") != null) {
			sql += " and subjectId = " + Integer.parseInt(filter.get("subId"));
		}
		String newSql = sql + " and rownum <= ? ) where rn > ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<Rule> rules = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(newSql);
			psm.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			psm.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = psm.executeQuery();
			while (rs.next()) {
				Rule rule = new Rule();
				rule.setId(rs.getInt("id"));
				rule.setName(rs.getString("name"));
				Subject subject = subDao.getSubjectById(rs.getInt("subjectId"));
				rule.setSubject(subject);
				rule.setTime(rs.getInt("time"));
				rule.setStatus(rs.getString("status"));
				rule.setScore(rs.getInt("score"));
				rule.setCreateTime(rs.getDate("createTime"));
				rule.setCredit(rs.getInt("credit"));
				User createUser = userDao.findUserById(rs.getInt("createId"));
				rule.setCreateUser(createUser);
				rules.add(rule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return rules;
	}

	@Override
	public List<RuleDetail> getRuleDetailsByRuleId(int id) {
		String sql = "SELECT rd.* , t.NAME tName FROM t_examRuleDetail rd,t_examType t WHERE rd.typeid = t.ID and ruleId = ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<RuleDetail> rds = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			rs = psm.executeQuery();
			while (rs.next()) {
				RuleDetail rd = new RuleDetail();
				rd.setId(rs.getInt("id"));
				Type type = new Type();
				type.setId(rs.getInt("typeId"));
				type.setName(rs.getString("tName"));
				rd.setType(type);
				rd.setNums(rs.getInt("nums"));
				rd.setScore(rs.getInt("score"));
				rds.add(rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return rds;
	}

	@Override
	public List<RuleDetail> findAllRuleDetail() {
		String sql = "SELECT rd.* , t.NAME tName FROM t_examRuleDetail rd,t_examType t WHERE rd.typeid = t.ID ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<RuleDetail> rdList = new ArrayList<RuleDetail>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			rs = psm.executeQuery();
			while (rs.next()) {
				RuleDetail rd = new RuleDetail();
				rd.setId(rs.getInt("id"));
				Rule rule = new Rule();
				rule.setId(rs.getInt("ruleId"));
				rd.setRule(rule);
				Type type = new Type();
				type.setId(rs.getInt("typeId"));
				type.setName(rs.getString("tName"));
				rd.setType(type);
				rd.setNums(rs.getInt("nums"));
				rd.setScore(rs.getInt("score"));
				rdList.add(rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return rdList;
	}

	public Map<String, List<RuleDetail>> findAllRuleDetail2() {
		String sql = "SELECT rd.* , t.NAME tName FROM t_examRuleDetail rd,t_examType t WHERE rd.typeid = t.ID ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		Map<String, List<RuleDetail>> rdMap = new HashMap<String, List<RuleDetail>>();
		List<RuleDetail> rdList = new ArrayList<RuleDetail>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			rs = psm.executeQuery();
			int tempId = 0;
			while (rs.next()) {
				RuleDetail rd = new RuleDetail();
				rd.setId(rs.getInt("id"));
				Rule rule = new Rule();
				rule.setId(rs.getInt("ruleId"));
				rd.setRule(rule);
				Type type = new Type();
				type.setId(rs.getInt("typeId"));
				type.setName(rs.getString("tName"));
				rd.setType(type);
				rd.setNums(rs.getInt("nums"));
				rd.setScore(rs.getInt("score"));
				rdList.add(rd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return rdMap;
	}

}
