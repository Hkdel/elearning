package com.zt.bbs.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zt.bbs.dao.BbsPlateDao;
import com.zt.bbs.dao.BbsPostDao;
import com.zt.bbs.dao.BbsReplyDao;
import com.zt.bbs.dao.FrontBbsDao;
import com.zt.bbs.dao.impl.BbsPlateDaoImpl;
import com.zt.bbs.dao.impl.BbsPostDaoImpl;
import com.zt.bbs.dao.impl.BbsReplyDaoImpl;
import com.zt.bbs.dao.impl.FrontBbsDaoImpl;
import com.zt.bbs.po.BbsPlate;
import com.zt.bbs.po.BbsPost;
import com.zt.bbs.po.BbsReply;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/bbs/FrontBbsServlet")
public class FrontBbsServlet extends HttpServlet {
	private FrontBbsDao frontBbsDao;
	private BbsPlateDao bbsPlateDao;
	private BbsPostDao bbsPostDao;
	private BbsReplyDao bbsReplyDao;
	private UserDao userDao;

	public void init(ServletConfig config) throws ServletException {
		frontBbsDao = new FrontBbsDaoImpl();
		bbsPlateDao = new BbsPlateDaoImpl();
		bbsPostDao = new BbsPostDaoImpl();
		bbsReplyDao = new BbsReplyDaoImpl();
		userDao = new UserDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if ("add".equals(method)) {
			add(request, response);
		}
		if ("listPlate".equals(method)) {
			listPlate(request, response);
		}
		if ("listPlatePage".equals(method)) {
			listPlatePage(request, response);
		}
		if ("platePost".equals(method)) {
			platePost(request, response);
		}
		if ("postReplyList".equals(method)) {
			postReplyList(request, response);
		}
		if ("addPostReply".equals(method)) {
			addPostReply(request, response);
		}
		if ("addReply2".equals(method)) {
			addReply2(request, response);
		}
		if ("delReply2".equals(method)) {
			delReply2(request, response);
		}
		if ("findByName".equals(method)) {
			findByName(request, response);
		}
		if ("addPlate".equals(method)) {
			addPlate(request, response);
		}
		if ("temp".equals(method)) {
			temp(request, response);
		}
		if ("temp2".equals(method)) {
			temp2(request, response);
		}
		if ("temp3".equals(method)) {
			temp3(request, response);
		}
		if ("temp4".equals(method)) {
			temp4(request, response);
		}
	}
	protected void temp4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user != null) {
			addPostReply(request, response);
		} else {
			request.setAttribute("msg", "请先登录！");
			request.getRequestDispatcher("../login/fontLogin.jsp").forward(request, response);
		}
	}
	protected void temp3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user != null) {
			addReply2(request, response);
		} else {
			request.setAttribute("msg", "请先登录！");
			request.getRequestDispatcher("../login/fontLogin.jsp").forward(request, response);
		}
	}
	
	protected void temp2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user != null) {
			response.sendRedirect("plateAdd.jsp");
		} else {
			request.setAttribute("msg", "请先登录！");
			request.getRequestDispatcher("../login/fontLogin.jsp").forward(request, response);
		}
	}

	protected void temp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user != null) {
			add(request, response);
		} else {
			request.setAttribute("msg", "请先登录！");
			request.getRequestDispatcher("../login/fontLogin.jsp").forward(request, response);
		}
	}

	// ��������
	protected void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BbsPost post = new BbsPost();
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		HttpSession session = request.getSession();
		User createUser = (User) session.getAttribute("loginUser");
		post.setName(name);
		post.setContent(content);
		post.setCreateUser(createUser);
		BbsPlate plate = bbsPlateDao.getById(id);
		post.setBbsPlate(plate);
		boolean f = frontBbsDao.addBbsPost(post);
		if (f) {
			request.setAttribute("bbsPlate", plate);
			request.getRequestDispatcher("bbs_post.jsp").forward(request,
					response);
		} else {
			response.sendRedirect("error.jsp");
		}
	}
	
	
	// ����Ź���
	protected void listPlate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<BbsPlate> bbsPlateList = bbsPlateDao.findAll();
		request.setAttribute("bbsPlateList", bbsPlateList);

		List<BbsPost> postList = bbsPostDao.findAll();
		request.setAttribute("postList", postList);

		int totalSize = frontBbsDao.getTotalSizeFront();
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;// Ĭ�ϵ�һҳ
		String page = request.getParameter("page");// Ҳ��������ת�뵱ǰҳ
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);// ������˵�ǰҳ��Ĭ�Ͼ��ǵ�ǰҳ
		}
		pageUtils.setCurrPage(currPage);// ���õ�ǰҳ
		pageUtils.setPageSize(3);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<BbsPlate> plateListPage = frontBbsDao.findPlateByPage(pageUtils);
		request.setAttribute("plateListPage", plateListPage);
		request.setAttribute("pageUtils", pageUtils);

		request.getRequestDispatcher("../index/menu3.jsp").forward(request,
				response);
	}

	// ���������б�
	protected void platePost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("plateId");
		String conditionStr = request.getParameter("condition");
		int condition = 0;
		if (conditionStr != null) {
			condition = Integer.parseInt(conditionStr);
		}
		request.setAttribute("condition", condition);
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		BbsPlate bbsPlate = bbsPlateDao.getById(id);
		request.setAttribute("bbsPlate", bbsPlate);
		request.getRequestDispatcher("bbs_post.jsp").forward(request, response);
	}

	// �����������б�
	protected void listPlatePage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		String conditionStr = request.getParameter("condition");
		int condition = 0;
		if (conditionStr != null && !"".equals(conditionStr) ) {
			condition = Integer.parseInt(conditionStr);
		}
		int totalSize = frontBbsDao.countPlateFront(id);
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;// Ĭ�ϵ�һҳ
		String page = request.getParameter("page");// Ҳ��������ת�뵱ǰҳ
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);// ������˵�ǰҳ��Ĭ�Ͼ��ǵ�ǰҳ
		}
		pageUtils.setCurrPage(currPage);// ���õ�ǰҳ
		pageUtils.setPageSize(10);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		if (condition == 0) {
			List<BbsPost> postList = frontBbsDao.findByPageFront(id, pageUtils);
			request.setAttribute("id", id);
			request.setAttribute("postList", postList);
			request.setAttribute("pageUtils", pageUtils);
			request.getRequestDispatcher("bbs_post_message.jsp").forward(
					request, response);
		} else if (condition == 1) {
			List<BbsPost> postList = frontBbsDao.findByPageFrontIsGood(id,
					pageUtils);
			request.setAttribute("id", id);
			request.setAttribute("postList", postList);
			request.setAttribute("pageUtils", pageUtils);
			request.getRequestDispatcher("bbs_post_message.jsp").forward(
					request, response);
		} else if (condition == 2) {
			List<BbsPost> postList = frontBbsDao.findByPageFrontByTime(id,
					pageUtils);
			request.setAttribute("id", id);
			request.setAttribute("postList", postList);
			request.setAttribute("pageUtils", pageUtils);
			request.getRequestDispatcher("bbs_post_message.jsp").forward(
					request, response);
		} else if (condition == 3) {
			List<BbsPost> postList = frontBbsDao.findByPageFrontByHeat(id,
					pageUtils);
			request.setAttribute("id", id);
			request.setAttribute("postList", postList);
			request.setAttribute("pageUtils", pageUtils);
			request.getRequestDispatcher("bbs_post_message.jsp").forward(
					request, response);
		}

	}

	// �������ݺ�����
	protected void postReplyList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		BbsPost post = bbsPostDao.getBbsPostById(id);
		boolean f = bbsPostDao.addHeat(post);
		if (f) {
			request.setAttribute("post", post);
		} else {
			response.sendRedirect("error.jsp");
		}

		int totalSize = bbsReplyDao.count(id);
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;// Ĭ�ϵ�һҳ
		String page = request.getParameter("page");// Ҳ��������ת�뵱ǰҳ
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);// ������˵�ǰҳ��Ĭ�Ͼ��ǵ�ǰҳ
		}
		pageUtils.setCurrPage(currPage);// ���õ�ǰҳ
		pageUtils.setPageSize(3);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<BbsReply> postReplyList = bbsReplyDao.findByPage(id, pageUtils);
		for (BbsReply r : postReplyList) {
			List<BbsReply> reply2 = bbsReplyDao.findReply2(r.getId());
			r.setReply2(reply2);
		}
		request.setAttribute("id", id);
		request.setAttribute("postReplyList", postReplyList);
		request.setAttribute("pageUtils", pageUtils);

		request.getRequestDispatcher("bbs_content_details.jsp").forward(
				request, response);
	}

	// ������ӻظ�
	protected void addPostReply(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BbsReply postReply = new BbsReply();
		String content = request.getParameter("content");
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		BbsPost post = bbsPostDao.getBbsPostById(id);
		postReply.setBbspost(post);
		postReply.setContent(content);
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		postReply.setLoginUser(loginUser);
		boolean f = bbsReplyDao.addReply(postReply);
		if (f) {
			response.sendRedirect("FrontBbsServlet?method=postReplyList&id="
					+ id);
		} else {
			response.sendRedirect("error.jsp");
		}

	}

	// ������ۻ�ظ��Ļظ�
	protected void addReply2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BbsReply replyReply = null;
		String postIdStr = request.getParameter("postId");
		int postId = 0;
		if (postIdStr != null) {
			postId = Integer.parseInt(postIdStr);
		}
		String accId = request.getParameter("accId");
		String accContentId = request.getParameter("accContentId");

		if (accId == null || "".equals(accId)) {
			// �����û���ID
			replyReply = new BbsReply();
			String postReplyUserIdStr = request.getParameter("postReplyUserId");
			int postReplyUserId = 0;
			if (postReplyUserIdStr != null) {
				postReplyUserId = Integer.parseInt(postReplyUserIdStr);
			}
			// ������������id
			String postReplyIdStr = request.getParameter("postReplyId");
			int postReplyId = 0;
			if (postReplyIdStr != null) {
				postReplyId = Integer.parseInt(postReplyIdStr);
			}
			BbsReply postReply = new BbsReply();
			postReply.setId(postReplyId);
			replyReply.setBbsReply(postReply);
			User postReplyUser = userDao.findUserById(postReplyUserId);
			replyReply.setAcceptUser(postReplyUser);
		} else {
			replyReply = new BbsReply();
			String postReplyUserIdStr = request.getParameter("accId");
			int postReplyUserId = 0;
			if (postReplyUserIdStr != null) {
				postReplyUserId = Integer.parseInt(postReplyUserIdStr);
			}
			String postReplyIdStr = request.getParameter("accContentId");
			int postReplyId = 0;
			if (postReplyIdStr != null) {
				postReplyId = Integer.parseInt(postReplyIdStr);
			}
			BbsReply postReply = new BbsReply();
			postReply.setId(postReplyId);
			replyReply.setBbsReply(postReply);
			User postReplyUser = userDao.findUserById(postReplyUserId);
			replyReply.setAcceptUser(postReplyUser);
		}

		String content = request.getParameter("content");
		content = new String(content.getBytes("iso8859-1"), "UTF-8");
		// content = new String(content.getBytes("iso8859-1"),"UTF-8");
		BbsPost post = bbsPostDao.getBbsPostById(postId);
		replyReply.setBbspost(post);
		replyReply.setContent(content);
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		replyReply.setLoginUser(loginUser);
		boolean f = bbsReplyDao.addReply2(replyReply);
		if (f) {
			response.sendRedirect("FrontBbsServlet?method=postReplyList&id="
					+ postId);
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void delReply2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String childIdStr = request.getParameter("childId");
		int childId = 0;
		if (childIdStr != null) {
			childId = Integer.parseInt(childIdStr);
		}
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		boolean f = bbsReplyDao.delReply2(childId);
		if (f) {
			response.sendRedirect("FrontBbsServlet?method=postReplyList&id="
					+ id);
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void findByName(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		List<BbsPost> postList = frontBbsDao.findByName(name);
		request.setAttribute("postList", postList);
		request.getRequestDispatcher("searchPosts.jsp").forward(request,
				response);
	}

	protected void addPlate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BbsPlate bbsPlate = new BbsPlate();
		DiskFileItemFactory df = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(df);
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// ����Ǳ���
					String name = item.getFieldName();
					String value = item.getString();
					if ("name".equals(name)) {
						value = new String(item.getString().getBytes(
								"iso-8859-1"), "utf-8");
						bbsPlate.setName(value);
					}
					if ("introduction".equals(name)) {
						value = new String(item.getString().getBytes(
								"iso-8859-1"), "utf-8");
						bbsPlate.setIntroduction(value);
					}
				} else { // �ļ���
					String fileName = item.getName();
					String path = request.getRealPath("/bbs/bbsPlatePhoto");
					String fileRealName = UUID.randomUUID().toString();
					String fileType = null;
					if (fileName != null && !"".equals(fileName)) {
						fileType = fileName
								.substring(fileName.lastIndexOf("."));
						File file = new File(path, fileRealName + fileType);
						bbsPlate.setPhoto("bbsPlatePhoto/" + fileRealName
								+ fileType);
						try {
							item.write(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						bbsPlate.setPhoto("/bbs/bbsPlatePhoto/mo.jpg");
					}
				}
			}
			HttpSession session = request.getSession();
			User createUser = (User) session.getAttribute("loginUser");
			bbsPlate.setCreateUser(createUser);
			boolean f = bbsPlateDao.addBbsPlate(bbsPlate);
			if (f) {
				response.sendRedirect("FrontBbsServlet?method=listPlate");
			} else {
				response.sendRedirect("error.jsp");
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
}
