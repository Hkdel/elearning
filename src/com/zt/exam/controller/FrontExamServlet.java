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
import javax.servlet.http.HttpSession;

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
import com.zt.exam.po.Type;
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
		if (method.equals("temp")) {
			temp(request, response);
		}
		if (method.equals("showDetail")) {
			showDetail(request, response);
		}
	}

	protected void showDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		List<RecordDetail> rds = recordDao.findByRecordId(id);
//		Rule rule = ruleDao.getRuleById(id)
		List<ExamQuestion> eqs = new ArrayList<ExamQuestion>();
		List<Question> ques = new ArrayList<Question>();
//		List<Option> ops = new ArrayList<Option>();
		for (RecordDetail rd : rds) {
			/*if(rd.getQuestion().getId() == 1 || rd.getQuestion().getId() == 2 ||rd.getQuestion().getId() == 3){
				List<Option> ops = quesDao.get
				eqs
			}*/
			Question q = new Question();
			q.setId(rd.getQuestion().getId());
			ques.add(q);
		}
		List<Option> ops = quesDao.getOptionsByQuestionId(ques);
		
		request.setAttribute("rds", rds);
		request.setAttribute("ques", ques);
		request.setAttribute("ops", ops);
		request.getRequestDispatcher("paper_detail.jsp").forward(request, response);
		
		
	}

	protected void temp(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user == null) {
			request.setAttribute("msg", "请先登录");
			request.getRequestDispatcher("../login/fontLogin.jsp").forward(
					request, response);
		} else {
			examList(request, response);
		}
	}

	protected void examSubmit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
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
				Type type = new Type();
				type.setId(rds.get(i).getQuestion().getType().getId());
				q.setType(type);
				rd.setQuestion(q);
				rd.setQuestionAnswer(rds.get(i).getQuestionAnswer());
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
				Type type = new Type();
				type.setId(rds.get(i).getQuestion().getType().getId());
				q.setType(type);
				rd.setQuestion(q);
				rd.setQuestionAnswer(rds.get(i).getQuestionAnswer());
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
				Type type = new Type();
				type.setId(rds.get(i).getQuestion().getType().getId());
				q.setType(type);
				rd.setQuestion(q);
				rd.setQuestionAnswer(rds.get(i).getQuestionAnswer());
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
				Type type = new Type();
				type.setId(rds.get(i).getQuestion().getType().getId());
				q.setType(type);
				rd.setQuestion(q);
				rd.setQuestionAnswer(rds.get(i).getQuestionAnswer());
				rd.setAnswer(answer);
				rds2.add(rd);
			}
		}
		Record record = new Record();
		record.setId(id);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		record.setUser(user);
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
			for (RecordDetail rd : rds2) {
				rd.setQuestionAnswer(rd.getAnswer());
				if (rd.getQuestion().getType().getId() == 1
						|| rd.getQuestion().getType().getId() == 2) {
					rd.setAnswer(rd.getAnswer().replace("A", "1")
							.replace("B", "2").replace("C", "3")
							.replace("D", "4").replace("E", "5"));
				}
			}
			request.setAttribute("recordDetails", rds2);
			request.getRequestDispatcher("exam_paper_confirm.jsp").forward(
					request, response);
		} else {
			response.sendRedirect("/error.jsp");
		}

	}

	protected void examClose(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		boolean f = recordDao.delete(id);
		if (f) {
			HttpSession session = request.getSession();
			session.removeAttribute("danxuan");
			session.removeAttribute("duoxuan");
			session.removeAttribute("panduan");
			session.removeAttribute("tiankong");
			session.removeAttribute("jianda");
			session.removeAttribute("jisuan");
			session.removeAttribute("lunshu");
			session.removeAttribute("rds");
			session.removeAttribute("rule");
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
		HttpSession session = request.getSession();
		if (danxuan != null && danxuan.size() > 0) {
			session.setAttribute("danxuan", danxuan);
		}
		if (duoxuan != null && duoxuan.size() > 0) {
			session.setAttribute("duoxuan", duoxuan);
		}
		if (panduan != null && panduan.size() > 0) {
			session.setAttribute("panduan", panduan);
		}
		if (tiankong != null && tiankong.size() > 0) {
			session.setAttribute("tiankong", tiankong);
		}
		if (jianda != null && jianda.size() > 0) {
			session.setAttribute("jianda", jianda);
		}
		if (jisuan != null && jisuan.size() > 0) {
			session.setAttribute("jisuan", jisuan);
		}
		if (lunshu != null && lunshu.size() > 0) {
			session.setAttribute("lunshu", lunshu);
		}
		Rule rule = ruleDao.getRuleById(ruleId);
		Record record = new Record();
		User user = (User) session.getAttribute("loginUser");
		record.setUser(user);
		record.setRule(rule);
		int id = recordDao.add(record, quesList);
		request.setAttribute("recordId", id);
		session.setAttribute("rds", rds);
		session.setAttribute("rule", rule);
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
