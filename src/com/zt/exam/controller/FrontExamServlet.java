package com.zt.exam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zt.exam.dao.QuestionDao;
import com.zt.exam.dao.RecordDao;
import com.zt.exam.dao.RuleDao;
import com.zt.exam.dao.SubjectDao;
import com.zt.exam.dao.impl.QuestionDaoImpl;
import com.zt.exam.dao.impl.RecordDaoImpl;
import com.zt.exam.dao.impl.RuleDaoImpl;
import com.zt.exam.dao.impl.SubjectDaoImpl;
import com.zt.exam.po.ExamQuestion;
import com.zt.exam.po.Option;
import com.zt.exam.po.Question;
import com.zt.exam.po.Record;
import com.zt.exam.po.RecordDetail;
import com.zt.exam.po.Rule;
import com.zt.exam.po.RuleDetail;
import com.zt.exam.po.Subject;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/exam/frontExam")
public class FrontExamServlet extends HttpServlet {
	private RuleDao ruleDao;
	private SubjectDao subDao;
	private QuestionDao quesDao;
	private RecordDao recordDao;

	public void init(ServletConfig config) throws ServletException {
		ruleDao = new RuleDaoImpl();
		subDao = new SubjectDaoImpl();
		quesDao = new QuestionDaoImpl();
		recordDao = new RecordDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("paperList")) {
			paperList(request, response);
		}
		if (method.equals("examList")) {
			examList(request, response);
		}
		if (method.equals("examClose")) {
			examClose(request, response);
		}
		if (method.equals("examSubmit")) {
			examSubmit(request, response);
		}
	}

	protected void examSubmit(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String creditStr = request.getParameter("credit");
		int credit = 0;
		if (creditStr != null && !"".equals(creditStr)) {
			credit = Integer.parseInt(creditStr);
		}
		String scoreStr = request.getParameter("score");
		int score = 0;
		if (scoreStr != null && !"".equals(scoreStr)) {
			score = Integer.parseInt(scoreStr);
		}
		String danxuanScoreStr = request.getParameter("danxuanScore");
		double danxuanScore = 0;
		if (danxuanScoreStr != null && !"".equals(danxuanScoreStr)) {
			danxuanScore = Double.parseDouble(danxuanScoreStr);
		}
		
		String duoxuanScoreStr = request.getParameter("duoxuanScore");
		double duoxuanScore = 0;
		if (duoxuanScoreStr != null && !"".equals(duoxuanScoreStr)) {
			duoxuanScore = Double.parseDouble(duoxuanScoreStr);
		}
		String panduanScoreStr = request.getParameter("panduanScore");
		double panduanScore = 0;
		if (panduanScoreStr != null && !"".equals(panduanScoreStr)) {
			panduanScore = Double.parseDouble(panduanScoreStr);
		}
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		List<RecordDetail> rds = recordDao.findByRecordId(id);
		List<RecordDetail> rds2 = new ArrayList<RecordDetail>();
		double stuScore = 0;
		boolean flag = false;
		for (int i = 0; i < rds.size(); i++) {
			if (rds.get(i).getQuestion().getType().getId() == 1) {
				RecordDetail rd = new RecordDetail();
				rd.setId(rds.get(i).getId());
				String name = rds.get(i).getQuestion().getId() + "";
				String answer = request.getParameter(name);
				Question q = new Question();
				q.setId(rds.get(i).getQuestion().getId());
				rd.setQuestion(q);
				rd.setAnswer(answer);
				if (rds.get(i).getQuestionAnswer().equals(answer)) {
					rd.setScore(danxuanScore);
				} else {
					rd.setScore((double) 0);
				}
				stuScore += rd.getScore();
				rds2.add(rd);
			} else if (rds.get(i).getQuestion().getType().getId() == 3) {
				RecordDetail rd = new RecordDetail();
				rd.setId(rds.get(i).getId());
				String name = rds.get(i).getQuestion().getId() + "";
				String answer = request.getParameter(name);
				Question q = new Question();
				q.setId(rds.get(i).getQuestion().getId());
				rd.setQuestion(q);
				rd.setAnswer(answer);
				if (rds.get(i).getQuestionAnswer().equals(answer)) {
					rd.setScore(panduanScore);
				} else {
					rd.setScore((double) 0);
				}
				stuScore += rd.getScore();
				rds2.add(rd);
			} else if (rds.get(i).getQuestion().getType().getId() == 2) {
				RecordDetail rd = new RecordDetail();
				rd.setId(rds.get(i).getId());
				String name = rds.get(i).getQuestion().getId() + "";
				String[] duoxuan = request.getParameterValues(name);
				StringBuffer sb = new StringBuffer();
				for (String s : duoxuan) {
					sb.append(s);
				}
				String answer = sb.toString();
				Question q = new Question();
				q.setId(rds.get(i).getQuestion().getId());
				rd.setQuestion(q);
				rd.setAnswer(answer);
				if (rds.get(i).getQuestionAnswer().equals(answer)) {
					rd.setScore(duoxuanScore);
				} else {
					rd.setScore((double) 0);
				}
				stuScore += rd.getScore();
				rds2.add(rd);
			} else {
				flag = true;
				RecordDetail rd = new RecordDetail();
				rd.setId(rds.get(i).getId());
				String name = rds.get(i).getQuestion().getId() + "";
				String answer = request.getParameter(name);
				Question q = new Question();
				q.setId(rds.get(i).getQuestion().getId());
				rd.setQuestion(q);
				rd.setAnswer(answer);
				rds2.add(rd);
			}
		}
		Record record = new Record();
		record.setId(id);
		if (!flag) {// 题型全部为客观题(选择填空判断)
			record.setStatus("2");
			record.setScore(stuScore);
			record.setSubjective(stuScore);
			record.setObjective(null);
			double stuCredit = 0;
			if (stuScore >= 0.6 * score) {
				stuCredit = (2 * stuScore * credit - score * credit) / score;
			}
			record.setCredit(stuCredit);
		} else {
			record.setStatus("1");
			record.setSubjective(stuScore);
			record.setObjective(null);
			record.setScore(null);
			record.setCredit(null);
		}
		boolean f = recordDao.correct(record, rds2);
		if (f) {
			System.out.println("成功");
		} else {
			response.sendRedirect("/error.jsp");
		}

	}

	protected void examClose(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		System.out.println(idStr);
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		boolean f = recordDao.delete(id);
		System.out.println(f);
		if (f) {
			response.sendRedirect("frontExam?method=paperList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void examList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ruleIdStr = request.getParameter("ruleId");
		int ruleId = 0;
		if (ruleIdStr != null && !"".equals(ruleIdStr)) {
			ruleId = Integer.parseInt(ruleIdStr);
		}
		String subIdStr = request.getParameter("subId");
		int subId = 0;
		if (subIdStr != null && !"".equals(subIdStr)) {
			subId = Integer.parseInt(subIdStr);
		}
		List<RuleDetail> rds = ruleDao.getRuleDetailsByRuleId(ruleId);
		List<Question> quesList = quesDao.getExamPaper(subId, rds);
		List<ExamQuestion> danxuan = new ArrayList<ExamQuestion>();
		List<ExamQuestion> duoxuan = new ArrayList<ExamQuestion>();
		List<ExamQuestion> panduan = new ArrayList<ExamQuestion>();
		List<ExamQuestion> tiankong = new ArrayList<ExamQuestion>();
		List<ExamQuestion> jianda = new ArrayList<ExamQuestion>();
		List<ExamQuestion> jisuan = new ArrayList<ExamQuestion>();
		List<ExamQuestion> lunshu = new ArrayList<ExamQuestion>();
		for (int i = 0; i < quesList.size(); i++) {
			ExamQuestion eq = new ExamQuestion();
			switch (quesList.get(i).getType().getId()) {
			case 1:
				List<Option> op1 = quesDao.getOptionsByQuestionId(quesList.get(
						i).getId());
				eq.setQuestion(quesList.get(i));
				eq.setOptions(op1);
				danxuan.add(eq);
				break;
			case 2:
				List<Option> op2 = quesDao.getOptionsByQuestionId(quesList.get(
						i).getId());
				eq.setQuestion(quesList.get(i));
				eq.setOptions(op2);
				duoxuan.add(eq);
				break;
			case 3:
				eq.setQuestion(quesList.get(i));
				panduan.add(eq);
				break;
			case 4:
				eq.setQuestion(quesList.get(i));
				tiankong.add(eq);
				break;
			case 5:
				eq.setQuestion(quesList.get(i));
				jianda.add(eq);
				break;
			case 6:
				eq.setQuestion(quesList.get(i));
				jisuan.add(eq);
				break;
			case 7:
				eq.setQuestion(quesList.get(i));
				lunshu.add(eq);
				break;
			}
		}
		request.setAttribute("danxuan", danxuan);
		request.setAttribute("duoxuan", duoxuan);
		request.setAttribute("panduan", panduan);
		request.setAttribute("tiankong", tiankong);
		request.setAttribute("jianda", jianda);
		request.setAttribute("jisuan", jisuan);
		request.setAttribute("lunshu", lunshu);
		Rule rule = ruleDao.getRuleById(ruleId);
		Record record = new Record();
		User user = new User();
		user.setId(21);
		record.setUser(user);
		record.setRule(rule);
		int id = recordDao.add(record, quesList);
		request.setAttribute("recordId", id);
		request.setAttribute("rds", rds);
		request.setAttribute("rule", rule);
		request.getRequestDispatcher("exam_online.jsp").forward(request,
				response);
	}

	protected void paperList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> filter = new HashMap<String, String>();
		String name = request.getParameter("paperName");
		if (name != null && !"".equals(name)) {
			filter.put("name", name);
		}
		String subIdStr = request.getParameter("subId");
		if (subIdStr != null && !"0".equals(subIdStr)) {
			filter.put("subId", subIdStr);
		}
		int totalSize = ruleDao.getTotalSize(filter);
		String page = request.getParameter("page");
		int currPage = 1;
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);
		}
		PageUtils pageUtils = new PageUtils();
		pageUtils.setCurrPage(currPage);
		pageUtils.setPageSize(2);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Rule> ruleList = ruleDao.findAll(filter, pageUtils);
		List<Subject> subList = subDao.findAll();
		request.setAttribute("filter", filter);
		request.setAttribute("subList", subList);
		request.setAttribute("ruleList", ruleList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("../index/menu2.jsp").forward(request,
				response);
	}

}
