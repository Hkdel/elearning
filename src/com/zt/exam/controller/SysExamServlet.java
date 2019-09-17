package com.zt.exam.controller;

import java.io.IOException;
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

import com.zt.exam.dao.SubjectDao;
import com.zt.exam.dao.TypeDao;
import com.zt.exam.dao.impl.SubjectDaoImpl;
import com.zt.exam.dao.impl.TypeDaoImpl;
import com.zt.exam.po.Subject;
import com.zt.exam.po.Type;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/admin/exam/sysExam")
public class SysExamServlet extends HttpServlet {
	private SubjectDao subDao;
	private TypeDao typeDao;

	public void init(ServletConfig config) throws ServletException {
		subDao = new SubjectDaoImpl();
		typeDao = new TypeDaoImpl();
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
		request.getRequestDispatcher("questionType/questionTypeList.jsp").forward(
				request, response);
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
		User loginUser = (User) session.getAttribute("loginUser");
		Subject sub = new Subject();
		sub.setId(id);
		sub.setName(name);
		sub.setCreateUser(loginUser);
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
		User loginUser = (User) session.getAttribute("loginUser");
		Subject sub = new Subject();
		sub.setName(name);
		sub.setCreateUser(loginUser);
		boolean f = subDao.add(sub);
		if (f) {
			response.sendRedirect("sysExam?method=subList");
		} else {
			response.sendRedirect("/error.jsp");
		}
	}

}
