package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.exam.dao.RecordDao;
import com.zt.exam.po.Record;
import com.zt.exam.po.RecordDetail;
import com.zt.exam.po.Rule;
import com.zt.exam.po.Subject;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class RecordDaoImpl implements RecordDao {

	@Override
	public int getTotalSize(Map<String, String> filter) {
		String sql = "SELECT re.*,u.NAME stuName,t.NAME paperName "
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
				record.setSubjective(rs.getInt("subjective"));
				record.setObjective(rs.getInt("objective"));
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
	public List<RecordDetail> findCorrect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecordDetail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
