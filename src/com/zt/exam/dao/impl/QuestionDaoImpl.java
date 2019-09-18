package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zt.exam.dao.QuestionDao;
import com.zt.exam.dao.SubjectDao;
import com.zt.exam.dao.TypeDao;
import com.zt.exam.po.Option;
import com.zt.exam.po.Question;
import com.zt.exam.po.Subject;
import com.zt.exam.po.Type;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;
import com.zt.utils.DBUtils;
import com.zt.utils.PageUtils;

public class QuestionDaoImpl implements QuestionDao {
	private SubjectDao subDao = new SubjectDaoImpl();
	private TypeDao typeDao = new TypeDaoImpl();
	private UserDao userDao = new UserDaoImpl();

	@Override
	public boolean add(Question question, String[] contents) {
		String sql = "insert into t_examQuestion "
				+ "values(seq_examQuestion.nextval,?,?,?,?,'1',?,sysdate)";
		String sql2 = "insert into t_examOption "
				+ "values(seq_examOption.nextval,seq_examQuestion.currval,?) ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			psm = conn.prepareStatement(sql);
			psm.setString(1, question.getTitle());
			psm.setInt(2, question.getSubject().getId());
			psm.setInt(3, question.getType().getId());
			psm.setString(4, question.getAnswer());
			psm.setInt(5, question.getCreateUser().getId());
			psm.executeUpdate();
			if (contents != null && contents.length != 0) {
				psm = conn.prepareStatement(sql2);
				for (String s : contents) {
					psm.setString(1, s);
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
	public boolean update(Question question) {
		String sql = "update t_examQuestion set title = ? , subjectId = ? , typeId = ? , answer = ? where id = ?";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setString(1, question.getTitle());
			psm.setInt(2, question.getSubject().getId());
			psm.setInt(3, question.getType().getId());
			psm.setString(4, question.getAnswer());
			psm.setInt(5, question.getId());
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
	public boolean delete(int id) {
		String sql = "delete from t_examQuestion where id = ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
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
	public boolean changeStatus(int id) {
		String sql = "update t_examQuestion set status = ? where id = ?";
		Question question = getQuestionById(id);
		String status = question.getStatus().equals("1") ? "0" : "1";
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
	public Question getQuestionById(int id) {
		String sql = "select * from t_examQuestion where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Question question = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				question = new Question();
				question.setId(rs.getInt("id"));
				question.setTitle(rs.getString("title"));
				Subject subject = subDao.getSubjectById(rs.getInt("subjectId"));
				question.setSubject(subject);
				Type type = typeDao.getTypeById(rs.getInt("typeId"));
				question.setType(type);
				question.setStatus(rs.getString("status"));
				question.setAnswer(rs.getString("answer"));
				User createUser = userDao.findUserById(rs.getInt("createId"));
				question.setCreateUser(createUser);
				question.setCreateTime(rs.getDate("createTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, pstmt, conn);
		}
		return question;
	}

	@Override
	public int getTotalSize(Map<String, String> filter) {
		String sql = "select count(*) count from t_examQuestion where 1=1 ";
		if (filter.get("title") != null) {
			sql += " and title like '%" + filter.get("title") + "%' ";
		}
		if (filter.get("subjectId") != null) {
			sql += " and subjectId = "
					+ Integer.parseInt(filter.get("subjectId"));
		}
		if (filter.get("typeId") != null) {
			sql += " and typeId = " + Integer.parseInt(filter.get("typeId"));
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
	public List<Question> findAll(Map<String, String> filter,
			PageUtils pageUtils) {
		String sql = "select * from (select s.* , rownum rn FROM (select * from t_examQuestion order by createTime desc ) s "
				+ "WHERE 1=1 ";
		if (filter.get("title") != null) {
			sql += " and title like '%" + filter.get("title") + "%' ";
		}
		if (filter.get("subId") != null) {
			sql += " and subjectId = "
					+ Integer.parseInt(filter.get("subId"));
		}
		if (filter.get("typeId") != null) {
			sql += " and typeId = " + Integer.parseInt(filter.get("typeId"));
		}
		String newSql = sql + " and rownum <= ? ) where rn > ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<Question> questions = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(newSql);
			psm.setInt(1, pageUtils.getCurrPage() * pageUtils.getPageSize());
			psm.setInt(2,
					(pageUtils.getCurrPage() - 1) * pageUtils.getPageSize());
			rs = psm.executeQuery();
			while (rs.next()) {
				Question question = new Question();
				question.setId(rs.getInt("id"));
				question.setTitle(rs.getString("title"));
				Subject subject = subDao.getSubjectById(rs.getInt("subjectId"));
				question.setSubject(subject);
				Type type = typeDao.getTypeById(rs.getInt("typeId"));
				question.setType(type);
				question.setStatus(rs.getString("status"));
				question.setAnswer(rs.getString("answer"));
				User createUser = userDao.findUserById(rs.getInt("createId"));
				question.setCreateUser(createUser);
				question.setCreateTime(rs.getDate("createTime"));
				questions.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return questions;
	}

}
