package com.zt.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.zt.exam.dao.OptionDao;
import com.zt.exam.dao.QuestionDao;
import com.zt.exam.po.Option;
import com.zt.exam.po.Question;
import com.zt.utils.DBUtils;

public class OptionDaoImpl implements OptionDao {
	private QuestionDao queDao = new QuestionDaoImpl();

	@Override
	public boolean add(Option option) {
		String sql = "insert into t_examOption values(seq_examOption.nextval,?,?) ";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, option.getQuestion().getId());
			psm.setString(2, option.getContent());
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
	public boolean update(Option option) {
		String sql = "update t_examOption set questionId = ? , content = ? where id = ?";
		Connection conn = null;
		PreparedStatement psm = null;
		boolean result = true;
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			psm.setInt(1, option.getQuestion().getId());
			psm.setString(2, option.getContent());
			psm.setInt(3, option.getId());
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
		String sql = "delete from t_examOption where id = ? ";
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
	public List<Option> findAll() {
		String sql = "select * from t_examOption where questionId = ? ";
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		List<Option> options = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			psm = conn.prepareStatement(sql);
			rs = psm.executeQuery();
			while (rs.next()) {
				Option option = new Option();
				option.setId(rs.getInt("id"));
				option.setContent(rs.getString("content"));
				Question question = queDao.getQuestionById(rs.getInt("questionId"));
				option.setQuestion(question);
				options.add(option);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs, psm, conn);
		}
		return options;
	}

}
