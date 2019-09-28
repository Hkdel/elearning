package com.zt.bbs.controller;

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

import com.zt.bbs.dao.BbsPlateDao;
import com.zt.bbs.dao.BbsPostDao;
import com.zt.bbs.dao.impl.BbsPostDaoImpl;
import com.zt.bbs.po.BbsPlate;
import com.zt.bbs.po.BbsPost;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/admin/bbs/post/sysPost")
public class BbsPostServlet extends HttpServlet {
	private BbsPostDao bbsPostDao;

	public void init(ServletConfig config) throws ServletException {
		bbsPostDao = new BbsPostDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if ("findByPage".equals(method)) {
			findByPage(request, response);
		}
		if ("audit".equals(method)) {
			audit(request, response);
		}
		if ("isGood".equals(method)) {
			isGood(request, response);
		}
		if ("isTop".equals(method)) {
			isTop(request, response);
		}
		
	}

	protected void findByPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String checkStatus = request.getParameter("checkStatus");
		String name = request.getParameter("name");
		String plateName = request.getParameter("plateName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		Map filter = new HashMap();
		if (beginTime != null && !"".equals(beginTime)) {
			filter.put("begin", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			filter.put("end", endTime);
		}
		if (name != null && !"".equals(name)) {
			filter.put("name", name);
		}
		if (plateName != null && !"".equals(plateName)) {
			filter.put("plateName", plateName);
		}
		if (checkStatus != null && !"2".equals(checkStatus)) {
			filter.put("checkStatus", checkStatus);
		}
		int totalSize = bbsPostDao.count(filter);
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;
		String page = request.getParameter("page");
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);
		}
		pageUtils.setCurrPage(currPage);
		pageUtils.setPageSize(10);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<BbsPost> postList = bbsPostDao.findByPage(filter, pageUtils);
		request.setAttribute("filter", filter);
		request.setAttribute("pageUtils", pageUtils);
		request.setAttribute("postList", postList);
		request.getRequestDispatcher("postList.jsp").forward(request, response);
	}
	
	protected void audit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String checkStatus = request.getParameter("checkStatus");
		String opinion = request.getParameter("opinion");
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		BbsPost bbsPost = bbsPostDao.getBbsPostById(id);
		bbsPost.setCheckStatus(checkStatus);
		bbsPost.setOpinion(opinion);
		HttpSession session=request.getSession();
		User checkUser = (User) session.getAttribute("loginSysUser");
		bbsPost.setCheckUser(checkUser);
		if(bbsPost.getCheckStatus()!=null){
			int status = Integer.parseInt(checkStatus);
			if(status==1){
				bbsPostDao.auditAddIntegral(id, 1);
			}else if(status==-1){
				bbsPostDao.auditAddIntegral(id, 0);
			}
		}
		boolean f = bbsPostDao.auditBbsPost(bbsPost);
		if(f){
			response.sendRedirect("sysPost?method=findByPage");
		}else{
			response.sendRedirect("error.jsp");
		}
	}

	protected void isGood(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		String isGood = request.getParameter("isGood");
		if ("0".equals(isGood)) {
			isGood="1";
			bbsPostDao.auditAddIntegral(id, 3);
		}else if("1".equals(isGood)){
			isGood="0";
			bbsPostDao.auditAddIntegral(id, -3);
		}
		//System.out.println(isGood);
		boolean f = bbsPostDao.postIsGood(id, isGood);
		if (f) {
			response.sendRedirect("sysPost?method=findByPage");
		} else {
			response.sendRedirect("error.jsp");
		}
	}
	
	protected void isTop(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		String isTop = request.getParameter("isTop");
		if ("0".equals(isTop)) {
			isTop="1";
		}else if("1".equals(isTop)){
			isTop="0";
		}
		boolean f = bbsPostDao.postIsTop(id, isTop);
		if (f) {
			response.sendRedirect("sysPost?method=findByPage");
		} else {
			response.sendRedirect("error.jsp");
		}
	}
}
