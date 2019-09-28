package com.zt.exam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.zt.exam.dao.TypeDao;
import com.zt.exam.dao.impl.QuestionDaoImpl;
import com.zt.exam.dao.impl.RecordDaoImpl;
import com.zt.exam.dao.impl.RuleDaoImpl;
import com.zt.exam.dao.impl.SubjectDaoImpl;
import com.zt.exam.dao.impl.TypeDaoImpl;
import com.zt.exam.po.Question;
import com.zt.exam.po.Record;
import com.zt.exam.po.RecordDetail;
import com.zt.exam.po.Rule;
import com.zt.exam.po.RuleDetail;
import com.zt.exam.po.Subject;
import com.zt.exam.po.Type;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/admin/exam/sysExam")
public class SysExamServlet extends HttpServlet {
	private SubjectDao subDao;
	private TypeDao typeDao;
	private QuestionDao queDao;
	private RuleDao ruleDao;
	private RecordDao recordDao;

	public void init(ServletConfig config) throws ServletException {
		subDao = new SubjectDaoImpl();
		typeDao = new TypeDaoImpl();
		queDao = new QuestionDaoImpl();
		ruleDao = new RuleDaoImpl();
		recordDao = new RecordDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("subList")) {
			subList(request, response);
		}
		if (method.equals("subAdd")) {
			subAdd(request, response);
		}
		if (method.equals("subUpdate")) {
			subUpdate(request, response);
		}
		if (method.equals("subDelete")) {
			subDelete(request, response);
		}
		if (method.equals("typeList")) {
			typeList(request, response);
		}
		if (method.equals("quesList")) {
			quesList(request, response);
		}
		if (method.equals("quesAdd")) {
			quesAdd(request, response);
		}
		if (method.equals("quesUpdate")) {
			quesUpdate(request, response);
		}
		if (method.equals("quesDelete")) {
			quesDelete(request, response);
		}
		if (method.equals("quesChange")) {
			quesChange(request, response);
		}
		if (method.equals("paperList")) {
			paperList(request, response);
		}
		if (method.equals("paperAdd")) {
			paperAdd(request, response);
		}
		if (method.equals("paperUpdate")) {
			paperUpdate(request, response);
		}
		if (method.equals("paperDelete")) {
			paperDelete(request, response);
		}
		if (method.equals("paperChange")) {
			paperChange(request, response);
		}
		if (method.equals("recordList")) {
			recordList(request, response);
		}
		if (method.equals("correctList")) {
			correctList(request, response);
		}
		if (method.equals("correctUpdate")) {
			correctUpdate(request, response);
		}
	}

	protected void correctUpdate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String recordIdStr = request.getParameter("recordId");
		int recordId = 0;
		if (recordIdStr != null && !"".equals(recordIdStr)) {
			recordId = Integer.parseInt(recordIdStr);
		}
		String[] score = request.getParameterValues("score");
		String[] idStr = request.getParameterValues("id");
		Record record = recordDao.getRecordById(recordId);
		List<RecordDetail> rds = new ArrayList<RecordDetail>();
		for (int i = 0; i < idStr.length; i++) {
			if (!score[i].trim().equals("") && !idStr[i].trim().equals("")) {
				RecordDetail rd = new RecordDetail();
				rd.setId(Integer.parseInt(idStr[i]));
				rd.setScore(Double.parseDouble(score[i]));
				rds.add(rd);
			}
		}
		boolean f = recordDao.update(record, rds);
		if (f) {
			response.sendRedirect("sysExam?method=correctList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void correctList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> filter = new HashMap<String, String>();
		String paperName = request.getParameter("paperName");
		String stuName = request.getParameter("stuName");
		if (paperName != null && !"".equals(paperName)) {
			filter.put("paperName", paperName);
		}
		if (stuName != null && !"".equals(stuName)) {
			filter.put("stuName", stuName);
		}
		filter.put("status","1");
		int totalSize = recordDao.getTotalSize(filter);
		String page = request.getParameter("page");
		int currPage = 1;
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);
		}
		PageUtils pageUtils = new PageUtils();
		pageUtils.setCurrPage(currPage);
		pageUtils.setPageSize(5);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Record> recordList = recordDao.findAll(filter, pageUtils);
		request.setAttribute("filter", filter);
		request.setAttribute("recordList", recordList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("correct/correctList.jsp").forward(
				request, response);
	}

	protected void recordList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> filter = new HashMap<String, String>();
		String paperName = request.getParameter("paperName");
		String stuName = request.getParameter("stuName");
		String subIdStr = request.getParameter("subId");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		if (paperName != null && !"".equals(paperName)) {
			filter.put("paperName", paperName);
		}
		if (stuName != null && !"".equals(stuName)) {
			filter.put("stuName", stuName);
		}
		if (begin != null && !"".equals(begin)) {
			filter.put("begin", begin);
		}
		if (end != null && !"".equals(end)) {
			filter.put("end", end);
		}
		if (subIdStr != null && !"0".equals(subIdStr)) {
			filter.put("subId", subIdStr);
		}
		filter.put("status","2");
		int totalSize = recordDao.getTotalSize(filter);
		String page = request.getParameter("page");
		int currPage = 1;
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);
		}
		PageUtils pageUtils = new PageUtils();
		pageUtils.setCurrPage(currPage);
		pageUtils.setPageSize(5);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Record> recordList = recordDao.findAll(filter, pageUtils);
		List<Subject> subList = subDao.findAll();
		request.setAttribute("filter", filter);
		request.setAttribute("subList", subList);
		request.setAttribute("recordList", recordList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("examHistory/examHistoryList.jsp").forward(
				request, response);
	}

	protected void paperChange(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		boolean f = ruleDao.changeStatus(id);
		if (f) {
			response.sendRedirect("sysExam?method=paperList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void paperDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		boolean f = ruleDao.delete(id);
		if (f) {
			response.sendRedirect("sysExam?method=paperList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void paperUpdate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		String timeStr = request.getParameter("time");
		int time = 0;
		if (timeStr != null && !"".equals(timeStr)) {
			time = Integer.parseInt(timeStr);
		}
		String scoreStr = request.getParameter("score");
		int score = 0;
		if (scoreStr != null && !"".equals(scoreStr)) {
			score = Integer.parseInt(scoreStr);
		}
		String creditStr = request.getParameter("credit");
		int credit = 0;
		if (creditStr != null && !"".equals(creditStr)) {
			credit = Integer.parseInt(creditStr);
		}
		Rule rule = new Rule(null, null, time, null, score, null, credit, null);
		rule.setId(id);
		String[] typeIdStr = request.getParameterValues("typeId");
		String[] numsStr = request.getParameterValues("nums");
		String[] rdScoreStr = request.getParameterValues("rdScore");
		List<RuleDetail> ruleDetails = new ArrayList<RuleDetail>();
		for (int i = 0; i < typeIdStr.length; i++) {
			if (!typeIdStr[i].trim().equals("")
					&& !numsStr[i].trim().equals("")
					&& !rdScoreStr[i].trim().equals("")) {
				Type type = new Type();
				type.setId(Integer.parseInt(typeIdStr[i]));
				int nums = Integer.parseInt(numsStr[i]);
				int rdScore = Integer.parseInt(rdScoreStr[i]);
				RuleDetail rd = new RuleDetail(rule, type, nums, rdScore);
				ruleDetails.add(rd);
			}
		}
		boolean f = ruleDao.update(rule, ruleDetails);
		if (f) {
			response.sendRedirect("sysExam?method=paperList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void paperAdd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String subIdStr = request.getParameter("subId");
		int subId = 0;
		if (subIdStr != null && !"".equals(subIdStr)) {
			subId = Integer.parseInt(subIdStr);
		}
		String name = request.getParameter("paperName");
		String timeStr = request.getParameter("time");
		int time = 0;
		if (timeStr != null && !"".equals(timeStr)) {
			time = Integer.parseInt(timeStr);
		}
		String scoreStr = request.getParameter("score");
		int score = 0;
		if (scoreStr != null && !"".equals(scoreStr)) {
			score = Integer.parseInt(scoreStr);
		}
		String creditStr = request.getParameter("credit");
		int credit = 0;
		if (creditStr != null && !"".equals(creditStr)) {
			credit = Integer.parseInt(creditStr);
		}
		HttpSession session = request.getSession();
		User loginSysUser = (User) session.getAttribute("loginSysUser");
		Rule rule = new Rule(name, subDao.getSubjectById(subId), time, null,
				score, null, credit, loginSysUser);
		String[] typeIdStr = request.getParameterValues("typeId");
		String[] numsStr = request.getParameterValues("nums");
		String[] rdScoreStr = request.getParameterValues("rdScore");
		List<RuleDetail> ruleDetails = new ArrayList<RuleDetail>();
		for (int i = 0; i < typeIdStr.length; i++) {
			if (!typeIdStr[i].trim().equals("") && !numsStr[i].trim().equals("")
					&& !rdScoreStr[i].trim().equals("")) {
				Type type = new Type();
				type.setId(Integer.parseInt(typeIdStr[i]));
				int nums = Integer.parseInt(numsStr[i]);
				int rdScore = Integer.parseInt(rdScoreStr[i]);
				RuleDetail rd = new RuleDetail(null, type, nums, rdScore);
				ruleDetails.add(rd);
			}
		}
		boolean f = ruleDao.add(rule, ruleDetails);
		if (f) {
			response.sendRedirect("sysExam?method=paperList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void paperList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> filter = new HashMap<String, String>();
		String name = request.getParameter("paperName");
		String subIdStr = request.getParameter("subId");
		if (name != null && !"".equals(name)) {
			filter.put("name", name);
		}
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
		pageUtils.setPageSize(5);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Rule> ruleList = ruleDao.findAll(filter, pageUtils);
		List<Subject> subList = subDao.findAll();
		List<RuleDetail> rdList = ruleDao.findAllRuleDetail();
		request.setAttribute("filter", filter);
		request.setAttribute("subList", subList);
		request.setAttribute("rdList", rdList);
		request.setAttribute("ruleList", ruleList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("testPaper/testPaperList.jsp").forward(
				request, response);
	}

	protected void quesChange(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		boolean f = queDao.changeStatus(id);
		if (f) {
			response.sendRedirect("sysExam?method=quesList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void quesDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		boolean f = queDao.delete(id);
		if (f) {
			response.sendRedirect("sysExam?method=quesList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void quesUpdate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		String title = request.getParameter("title");
		String answer = request.getParameter("answer");
		String[] contents = request.getParameterValues("content");
		Question question = new Question(title, null, null, answer, null, null,
				null);
		question.setId(id);
		boolean f = queDao.update(question, contents);
		if (f) {
			response.sendRedirect("sysExam?method=quesList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void quesAdd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String subIdStr = request.getParameter("subId");
		int subId = 0;
		if (subIdStr != null && !"".equals(subIdStr)) {
			subId = Integer.parseInt(subIdStr);
		}
		String typeIdStr = request.getParameter("typeId");
		int typeId = 0;
		if (typeIdStr != null && !"".equals(typeIdStr)) {
			typeId = Integer.parseInt(typeIdStr);
		}
		String title = request.getParameter("title");
		String answer = request.getParameter("answer");
		HttpSession session = request.getSession();
		User loginSysUser = (User) session.getAttribute("loginSysUser");
		String[] contents = request.getParameterValues("content");
		Question question = new Question(title, subDao.getSubjectById(subId),
				typeDao.getTypeById(typeId), answer, null, loginSysUser, null);
		boolean f = queDao.add(question, contents);
		if (f) {
			response.sendRedirect("sysExam?method=quesList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void quesList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> filter = new HashMap<String, String>();
		String title = request.getParameter("title");
		String subIdStr = request.getParameter("subId");
		String typeIdStr = request.getParameter("typeId");
		if (title != null && !"".equals(title)) {
			filter.put("title", title);
		}
		if (subIdStr != null && !"0".equals(subIdStr)) {
			filter.put("subId", subIdStr);
		}
		if (typeIdStr != null && !"0".equals(typeIdStr)) {
			filter.put("typeId", typeIdStr);
		}
		int totalSize = queDao.getTotalSize(filter);
		String page = request.getParameter("page");
		int currPage = 1;
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);
		}
		PageUtils pageUtils = new PageUtils();
		pageUtils.setCurrPage(currPage);
		pageUtils.setPageSize(5);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Question> quesList = queDao.findAll(filter, pageUtils);
		List<Type> typeList = typeDao.findAll();
		List<Subject> subList = subDao.findAll();
		request.setAttribute("filter", filter);
		request.setAttribute("typeList", typeList);
		request.setAttribute("subList", subList);
		request.setAttribute("quesList", quesList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("question/questionList.jsp").forward(
				request, response);
	}

	protected void typeList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int totalSize = typeDao.getTotalSize();
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
		List<Type> typeList = typeDao.findAll(pageUtils);
		request.setAttribute("typeList", typeList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("questionType/questionTypeList.jsp")
				.forward(request, response);
	}

	protected void subDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		boolean f = subDao.delete(id);
		if (f) {
			response.sendRedirect("sysExam?method=subList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

	protected void subUpdate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null && !"".equals(idStr)) {
			id = Integer.parseInt(idStr);
		}
		String name = request.getParameter("subName");
		HttpSession session = request.getSession();
		User loginSysUser = (User) session.getAttribute("loginSysUser");
		Subject sub = new Subject();
		sub.setId(id);
		sub.setName(name);
		sub.setCreateUser(loginSysUser);
		boolean f = subDao.update(sub);
		if (f) {
			response.sendRedirect("sysExam?method=subList");
		} else {
			response.sendRedirect("/error.jsp");
		}

	}

	protected void subList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> filter = new HashMap<String, String>();
		String name = request.getParameter("subName");
		if (name != null && !"".equals(name)) {
			filter.put("name", name);
		}
		int totalSize = subDao.getTotalSize(filter);
		String page = request.getParameter("page");
		int currPage = 1;
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);
		}
		PageUtils pageUtils = new PageUtils();
		pageUtils.setCurrPage(currPage);
		pageUtils.setPageSize(5);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Subject> subList = subDao.findAll(filter, pageUtils);
		request.setAttribute("filter", filter);
		request.setAttribute("subList", subList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("subject/subjectList.jsp").forward(
				request, response);
	}

	protected void subAdd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("subName");
		HttpSession session = request.getSession();
		User loginSysUser = (User) session.getAttribute("loginSysUser");
		Subject sub = new Subject();
		sub.setName(name);
		sub.setCreateUser(loginSysUser);
		boolean f = subDao.add(sub);
		if (f) {
			response.sendRedirect("sysExam?method=subList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

}
