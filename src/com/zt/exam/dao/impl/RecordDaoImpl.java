package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.exam.dao.RecordDao;
import com.zt.exam.po.Question;
import com.zt.exam.po.Record;
import com.zt.exam.po.RecordDetail;
import com.zt.exam.po.Rule;
import com.zt.exam.po.Subject;
import com.zt.exam.po.Type;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class RecordDaoImpl implements RecordDao {

	@Override
	public int getTotalSize(Map<String, String> filter) {
		String sql = "SELECT count(*) count "
				+ " FROM t_examRecord re,t_sysUser u,"
				+ " (SELECT ru.ID ruleId,ru.NAME ,s.NAME subName FROM t_examRule ru,t_examSubject s WHERE ru.subjectId = s.ID) t "
				+ " WHERE re.userId = u.ID AND re.ruleId = t.ruleId and 1=1 ";
		sql += " and re.status = '" + filter.get("status") + "' ";
		if (filter.get("stuName") != null) {
			sql += " and stuName like '%" + filter.get("stuName") + "%' ";
		}
		if (filter.get("paperName") != null) {
			sql += " and paperName like '%" + filter.get("paperName") + "%' ";
		}
		if (filter.get("subId") != null) {
			sql += " and subId = " + Integer.parseInt(filter.get("subId"));
		}
		String begin = (String) filter.get("begin");
		String end = (String) filter.get("end");
		if (begin != null) {
			sql += " and startTime >= to_date('" + begin + "','yyyy-MM-dd')";
		}
		if (end != null) {
			sql += " and startTime <= to_date('" + end + "','yyyy-MM-dd')";
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
	public List<Record> findAll(Map<String, String> filter, PageUtils pageUtils) {
		String sql = "SELECT re.*,u.NAME stuName, t.paperName paperName,t.subId,t.subName,rownum rn "
				+ " FROM t_examRecord re,t_sysUser u,"
				+ " (SELECT ru.ID ruleId,ru.name paperName ,s.id subId,s.NAME subName FROM t_examRule ru,t_examSubject s WHERE ru.subjectId = s.ID) t "
				+ " WHERE re.userId = u.ID AND re.ruleId = t.ruleId and 1=1 ";
		sql += " and re.status = '" + filter.get("status") + "' ";
		if (filter.get("stuName") != null) {
			sql += " and u.name like '%" + filter.get("stuName") + "%' ";
		}
		if (filter.get("paperName") != null) {
			sql += " and paperName like '%" + filter.get("paperName") + "%' ";
		}
		if (filter.get("subId") != null) {
			sql += " and subId = " + Integer.parseInt(filter.get("subId"));
		}
		String begin = (String) filter.get("begin");
		String end = (String) filter.get("end");
		if (begin != null) {
			sql += " and startTime >= to_date('" + begin + "','yyyy-MM-dd')";
		}
		if (end != null) {
			sql += " and startTime <= to_date('" + end + "','yyyy-MM-dd')";
		}
		String newSql = "select * from ( " + sql
				+ " and rownum <= ? ) where rn > ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<Record> records = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(newSql);
			psm.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			psm.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = psm.executeQuery();
			while (rs.next()) {
				Record record = new Record();
				record.setId(rs.getInt("id"));
				record.setSubjective(rs.getDouble("subjective"));
				record.setObjective(rs.getDouble("objective"));
				Rule rule = new Rule();
				rule.setId(rs.getInt("ruleId"));
				rule.setName(rs.getString("paperName"));
				Subject subject = new Subject();
				subject.setId(rs.getInt("subId"));
				subject.setName(rs.getString("subName"));
				rule.setSubject(subject);
				record.setRule(rule);
				record.setStatus(rs.getString("status"));
				record.setScore(rs.getDouble("score"));
				record.setCredit(rs.getDouble("credit"));
				User user = new User();
				user.setId(rs.getInt("userId"));
				user.setName(rs.getString("stuName"));
				record.setUser(user);
				record.setStartTime(rs.getTimestamp("startTime"));
				record.setEndTime(rs.getTimestamp("endTime"));
				records.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return records;
	}

	@Override
	public List<RecordDetail> findCorrect(int id) {
		String sql = "SELECT d.*,q.title,t.score tscore FROM t_examDetail d, "
				+ " (SELECT * FROM t_examQuestion  WHERE typeId IN "
				+ " 	(SELECT ID FROM t_examType WHERE NAME NOT IN ('单选题','多选题','判断题'))) q, "
				+ " (SELECT re.ID reId,rd.score,rd.typeId  FROM t_examRecord re,t_examRuleDetail rd "
				+ " 	WHERE re.ruleId = rd.ruleId and re.status = '1' AND rd.typeId IN "
				+ " 		(SELECT ID FROM t_examType WHERE NAME NOT IN ('单选题','多选题','判断题')) ) t "
				+ " WHERE d.questionId = q.ID AND q.typeId = t.typeId AND d.recordId = t.reId  AND d.recordId = ?";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<RecordDetail> rds = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			rs = psm.executeQuery();
			while (rs.next()) {
				RecordDetail rd = new RecordDetail();
				rd.setId(rs.getInt("id"));
				Question question = new Question();
				question.setId(rs.getInt("questionId"));
				question.setTitle(rs.getString("title"));
				rd.setQuestion(question);
				rd.setQuestionAnswer(rs.getString("questionAnswer"));
				rd.setAnswer(rs.getString("answer"));
				rd.setScore(rs.getDouble("tscore"));
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
	public List<RecordDetail> findByRecordId(int id) {
		String sql = "select d.*,q.typeId,q.title from t_examDetail d,t_examQuestion q where d.questionId = q.id and d.recordId = ?";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<RecordDetail> rds = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			rs = psm.executeQuery();
			while (rs.next()) {
				RecordDetail rd = new RecordDetail();
				rd.setId(rs.getInt("id"));
				Question question = new Question();
				Type type = new Type();
				type.setId(rs.getInt("typeId"));
				question.setId(rs.getInt("questionId"));
				question.setType(type);
				question.setTitle(rs.getString("title"));
				rd.setQuestion(question);
				rd.setQuestionAnswer(rs.getString("questionAnswer"));
				rd.setScore(rs.getDouble("score"));
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
	public Record getRecordById(int id) {
		String sql = "SELECT re.*,u.NAME stuName, t.paperName paperName,t.subId,t.subName,rownum rn "
				+ " FROM t_examRecord re,t_sysUser u,"
				+ " (SELECT ru.ID ruleId,ru.name paperName ,s.id subId,s.NAME subName FROM t_examRule ru,t_examSubject s WHERE ru.subjectId = s.ID) t "
				+ " WHERE re.userId = u.ID AND re.ruleId = t.ruleId and re.id = ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		Record record = new Record();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			rs = psm.executeQuery();
			while (rs.next()) {
				record = new Record();
				record.setId(rs.getInt("id"));
				record.setSubjective(rs.getDouble("subjective"));
				record.setObjective(rs.getDouble("objective"));
				Rule rule = new Rule();
				rule.setId(rs.getInt("ruleId"));
				rule.setName(rs.getString("paperName"));
				Subject subject = new Subject();
				subject.setId(rs.getInt("subId"));
				subject.setName(rs.getString("subName"));
				rule.setSubject(subject);
				record.setRule(rule);
				record.setStatus(rs.getString("status"));
				record.setScore(rs.getDouble("score"));
				record.setCredit(rs.getDouble("credit"));
				User user = new User();
				user.setId(rs.getInt("userId"));
				user.setName(rs.getString("stuName"));
				record.setUser(user);
				record.setStartTime(rs.getTimestamp("startTime"));
				record.setEndTime(rs.getTimestamp("endTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return record;
	}

	@Override
	public int add(Record record, List<Question> questions) {
		String sql = "insert into t_examRecord values(seq_examRecord.nextval,?,null,null,?,sysdate,null,'0',null,null) ";
		String sql2 = "insert into t_examDetail values(seq_examDetail.nextval,seq_examRecord.currval,?,?,null,null) ";
		String sql3 = "select seq_examRecord.currval id from dual";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		int id = 0;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			psm = conn.prepareStatement(sql);
			psm.setInt(1, record.getUser().getId());
			psm.setInt(2, record.getRule().getId());
			psm.executeUpdate();
			if (questions != null && questions.size() > 0) {
				psm = conn.prepareStatement(sql2);
				for (Question que : questions) {
					psm.setInt(1, que.getId());
					psm.setString(2, que.getAnswer());
					psm.addBatch();
				}
				psm.executeBatch();
			}
			psm = conn.prepareStatement(sql3);
			rs = psm.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			}
			conn.commit();
		} catch (Exception e) {
			id = 0;
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
			DBUtils.close(rs, psm, conn);
		}
		return id;
	}

	@Override
	public boolean update(Record record, List<RecordDetail> rds) {
		String sql = "update t_examDetail set score = ? where id = ? ";
		String sql2 = "SELECT t.objective,r.subjective+t.objective stuScore, ru.score,ru.credit FROM t_examRecord r, "
				+ " (SELECT recordId,SUM(score) objective FROM t_examDetail "
				+ " WHERE questionId IN "
				+ " (SELECT ID FROM t_examQuestion WHERE typeId IN "
				+ " (SELECT ID FROM t_examType WHERE NAME NOT IN ('单选题','多选题','判断题'))) "
				+ " GROUP BY recordId) t, t_examRule ru "
				+ " WHERE r.ID = t.recordId AND r.ruleId = ru.ID AND r.ID = ? ";
		String sql3 = "update t_examRecord set status = '2' ,"
				+ " objective = ? ,score = ? ,credit = ? where id = ? ";
		//修改用户表
		String sql4 = "SELECT sum(MAX(credit)) examScore FROM t_examRecord WHERE  status='2' AND userId = ? GROUP BY ruleId  ";
		String sql5 = "update t_sysUser set examScore = ? where id = ?";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			if (rds != null && rds.size() > 0) {
				psm = conn.prepareStatement(sql);
				for (RecordDetail rd : rds) {
					psm.setDouble(1, rd.getScore());
					psm.setInt(2, rd.getId());
					psm.addBatch();
				}
				psm.executeBatch();
				psm = conn.prepareStatement(sql2);
				psm.setInt(1, record.getId());
				rs = psm.executeQuery();
				double objective = 0;
				double stuScore = 0;
				double stuCredit = 0;
				double score = 0;
				double credit = 0;
				if (rs.next()) {
					objective = rs.getDouble("objective");
					stuScore = rs.getDouble("stuScore");
					score = rs.getDouble("score");
					credit = rs.getDouble("credit");
				}
				if(score !=0 && credit != 0 ){
					psm = conn.prepareStatement(sql3);
					psm.setDouble(1, objective);
					psm.setDouble(2, stuScore);
					if (stuScore >= 0.6 * score) {
						stuCredit = (2 * stuScore * credit - score * credit) / score;
					}
					psm.setDouble(3, stuCredit);
					psm.setInt(4, record.getId());
					psm.executeUpdate();
					psm = conn.prepareStatement(sql4);
					psm.setInt(1,record.getUser().getId());
					rs = psm.executeQuery();
					double examScore = 0;
					if (rs.next()) {
						examScore = rs.getDouble("examScore");
					}
					psm = conn.prepareStatement(sql5);
					psm.setDouble(1, examScore);
					psm.setInt(2, record.getUser().getId());
					psm.executeUpdate();
					conn.commit();
				} else {
					conn.rollback();
				}
			} 
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
			DBUtils.close(rs, psm, conn);
		}
		return result;
	}

	@Override
	public int getSubjective(int id) {
		String sql = "SELECT ruleId, SUM(nums * score) subjective FROM t_examRuleDetail "
				+ " WHERE typeId IN "
				+ " 	(SELECT ID FROM t_examType WHERE NAME NOT IN ('单选题','多选题','判断题')) "
				+ " AND ruleId = (SELECT ruleId FROM t_examRecord WHERE ID = ?) "
				+ " GROUP BY ruleId";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		int subjective = 0;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			rs = psm.executeQuery();
			if (rs.next()) {
				subjective = rs.getInt("subjective");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return subjective;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from t_examDetail where recordId = ? ";
		String sql2 = "delete from t_examRecord where id = ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, id);
			psm.executeUpdate();
			psm = conn.prepareStatement(sql2);
			psm.setInt(1, id);
			psm.executeUpdate();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {
			DBUtils.close(null, psm, conn);
		}
		return result;
	}

	@Override
	public boolean correct(Record record, List<RecordDetail> rds) {
		String sql5 = "select max(credit) max from t_examRecord ";
		String sql = "update t_examRecord set subjective = ?,objective = ?,endTime = sysdate , status = ?,score = ?,credit = ? where id = ? ";
		String sql2 = "update t_examDetail set answer = ? ,score = ? where id = ? ";
		String sql3 = "SELECT sum(MAX(credit)) examScore FROM t_examRecord WHERE  status='2' AND userId = ? GROUP BY ruleId  ";
		String sql4 = "update t_sysUser set examScore = ? where id = ?";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			psm = conn.prepareStatement(sql);
			psm.setDouble(1,record.getSubjective());
			psm.setObject(2,record.getObjective());
			psm.setString(3,record.getStatus());
			psm.setObject(4,record.getScore());
			psm.setObject(5,record.getCredit());
			psm.setInt(6,record.getId());
			psm.executeUpdate();
			if (rds != null && rds.size() > 0) {
				psm = conn.prepareStatement(sql2);
				for (RecordDetail rd : rds) {
					psm.setString(1, rd.getAnswer());
					psm.setObject(2, rd.getScore());
					psm.setInt(3, rd.getId());
					psm.addBatch();
				}
				psm.executeBatch();
				psm = conn.prepareStatement(sql3);
				psm.setInt(1,record.getUser().getId());
				rs = psm.executeQuery();
				double examScore = 0;
				if (rs.next()) {
					examScore = rs.getDouble("examScore");
				}
				psm = conn.prepareStatement(sql4);
				psm.setDouble(1, examScore);
				psm.setInt(2, record.getUser().getId());
				psm.executeUpdate();
				conn.commit();
			} 
			
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
			DBUtils.close(rs, psm, conn);
		}
		return result;
	}

	@Override
	public List<Record> getRecordsById(int id) {
		String sql = "SELECT r.NAME,t.score FROM "
				+ " (SELECT ruleId,MAX(score) score FROM t_examRecord "
				+ " WHERE status = '2' AND userId = ? GROUP BY ruleId ) t, "
				+ " t_examRule r WHERE t.ruleId = r.ID ";
		List<Record> res = new ArrayList<Record>();
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1,id);
			rs = psm.executeQuery();
			while (rs.next()) {
				Record record = new Record();
				Rule rule = new Rule();
				rule.setName(rs.getString("name"));
				record.setRule(rule);
				record.setScore(rs.getDouble("score"));
				res.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return res;
	}

}
