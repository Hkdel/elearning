package com.zt.study.controller;

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

import com.zt.study.dao.SourceTypeDao;
import com.zt.study.dao.StudyNoteDao;
import com.zt.study.dao.StudyResourceDao;
import com.zt.study.dao.impl.SourceTypeDaoImpl;
import com.zt.study.dao.impl.StudyNoteDaoImpl;
import com.zt.study.dao.impl.StudyResourceDaoImpl;
import com.zt.study.po.SourceType;
import com.zt.study.po.StudyNote;
import com.zt.study.po.StudyResource;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/study/frontStudy")
public class FrontStudyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudyNoteDao noteDao;
	private StudyResourceDao sourceDao;
	private SourceTypeDao typeDao;
	private UserDao userDao;

	public void init(ServletConfig config) throws ServletException {
		noteDao = new StudyNoteDaoImpl();
		sourceDao = new StudyResourceDaoImpl();
		typeDao = new SourceTypeDaoImpl();
		userDao = new UserDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if ("addNote".equals(method)) {
			addNote(request, response);
		}
		if ("updateNote".equals(method)) {
			updateNote(request, response);
		}
		if ("editNote".equals(method)) {
			editNote(request, response);
		}
		if ("noteList".equals(method)) {
			noteList(request, response);
		}
		if ("delNote".equals(method)) {
			delNote(request, response);
		}
		if ("showNote".equals(method)) {
			showNote(request, response);
		}
		if ("addRes".equals(method)) {
			addRes(request, response);
		}
		if ("resList".equals(method)) {
			resList(request, response);
		}
		if ("delRes".equals(method)) {
			delRes(request, response);
		}
		if ("showRes".equals(method)) {
			showRes(request, response);
		}
		if ("editRes".equals(method)) {
			editRes(request, response);
		}
		if ("updateRes".equals(method)) {
			updateRes(request, response);
		}
	}

	protected void addNote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user != null) {
			String name = request.getParameter("name");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			StudyNote note = new StudyNote();
			// User user = new User();
			// user.setId(2);
			note.setName(name);
			note.setTitle(title);
			note.setContent(content);
			note.setUser(user);
			boolean f = noteDao.addStudyNote(note);
			if (f) {
				response.sendRedirect("frontStudy?method=noteList");
			} else {
				response.sendRedirect("error.jsp");
			}
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void noteList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user == null) {
			response.sendRedirect("../login/fontLogin.jsp");
		} else {
			String name = request.getParameter("name");
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			Map<Object, Object> filter = new HashMap<Object, Object>();
			if (name != null && !"".equals(name)) {
				filter.put("name", name);
			}
			if (beginTime != null && !"".equals(beginTime)) {
				filter.put("begin", beginTime);
			}
			if (endTime != null && !"".equals(endTime)) {
				filter.put("end", endTime);
			}
			// 2��������������Map filter ��ѯ���������ļ�¼����

			int loginId = user.getId();
			int totalSize = noteDao.getTotalSize(filter, loginId);
			// 3:��ѯ��ǰҳ�ļ�¼����
			PageUtils pageUtils = new PageUtils();
			int currPage = 1;// Ĭ���ǵ�һҳ
			String page = request.getParameter("page");// Ҳ���������뵱ǰҳ
			if (page != null && !"".equals(page)) {
				currPage = Integer.parseInt(page);// ������˵�ǰҳ Ĭ�Ͼ��Ǵ��ĵ�ǰҳ
			}
			pageUtils.setCurrPage(currPage);// ���õ�ǰҳ�ǵڼ�ҳ
			pageUtils.setPageSize(9); // ����ÿҳ������Ĭ����2
			pageUtils.setTotalSize(totalSize); // �����ܼ�¼��
			pageUtils.setTotalPage(totalSize); // ������ҳ��
			// 4:���ݵ�ǰҳ��Ϣ��ѯ��ǰҳ��¼
			List<StudyNote> notes = noteDao.searchNotesByPage(filter,
					pageUtils, loginId);
			request.setAttribute("filter", filter);
			request.setAttribute("noteList", notes);
			request.setAttribute("pageUtils", pageUtils);
			request.getRequestDispatcher("../index/menu1.jsp").forward(request,
					response);
		}
	}

	protected void editNote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int noteId = 0;
		if (idStr != null) {
			noteId = Integer.parseInt(idStr);
		}
		StudyNote note = noteDao.getStudyNoteById(noteId);
		HttpSession session = request.getSession();
		session.setAttribute("note", note);
		request.getRequestDispatcher("updateNote.jsp").forward(request,
				response);
	}

	protected void updateNote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int noteId = 0;
		if (idStr != null) {
			noteId = Integer.parseInt(idStr);
		}
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		StudyNote note = noteDao.getStudyNoteById(noteId);
		note.setName(name);
		note.setTitle(title);
		note.setContent(content);
		boolean f = noteDao.updateStudyNote(note);
		if (f) {
			response.sendRedirect("frontStudy?method=noteList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void delNote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int noteId = 0;
		if (idStr != null) {
			noteId = Integer.parseInt(idStr);
		}
		boolean f = noteDao.delStudyNote(noteId);
		if (f) {
			request.getRequestDispatcher("frontStudy?method=noteList").forward(
					request, response);
			// response.sendRedirect("frontStudy?method=noteList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void showNote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int noteId = 0;
		if (idStr != null) {
			noteId = Integer.parseInt(idStr);
		}
		StudyNote note = noteDao.getStudyNoteById(noteId);
		request.setAttribute("note", note);
		request.getRequestDispatcher("showNote.jsp").forward(request, response);
	}

	protected void addRes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DiskFileItemFactory df = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(df);
		StudyResource res = new StudyResource();

		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			for (FileItem item : fileItems) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");

					if ("typeId".equals(name)) {
						SourceType type = new SourceType();
						type.setId(Integer.parseInt(value));
						res.setType(type);
					}
					if ("name".equals(name)) {
						res.setName(value);
					}
					if ("describe".equals(name)) {
						res.setDescribe(value);
					}

				} else {// �ļ���
					String fileName = item.getName();
					String path = request.getRealPath("/uploadFile");
					String fileRealName = UUID.randomUUID().toString();
					String fileType = fileName.substring(fileName
							.lastIndexOf("."));
					File file = new File(path, fileRealName + fileType);
					res.setUrl("uploadFile/" + fileRealName + fileType);
					try {
						item.write(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					HttpSession session = request.getSession();
					User uploadUser = (User) session.getAttribute("loginUser");
					res.setUploadUser(uploadUser);
				}
			}
			boolean f = sourceDao.addResource(res);
			if (f) {
				response.sendRedirect("frontStudy?method=resList");
			} else {
				response.sendRedirect("error.jsp");
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void resList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String typeId = request.getParameter("typeId");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String uploadId = request.getParameter("uploadId");
		Map<Object, Object> filter = new HashMap<Object, Object>();
		if (name != null && !"".equals(name)) {
			filter.put("name", name);
		}
		if (uploadId != null && !"0".equals(uploadId)) {
			filter.put("uploadId", Integer.parseInt(uploadId));
		}
		if (typeId != null && !"0".equals(typeId)) {
			filter.put("typeId", Integer.parseInt(typeId));
		}
		if (beginTime != null && !"".equals(beginTime)) {
			filter.put("begin", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			filter.put("end", endTime);
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		int loginId = user.getId();
		int totalSize = sourceDao.getTotalSize2(filter, loginId);
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;
		String page = request.getParameter("page");// Ҳ���������뵱ǰҳ
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);// ������˵�ǰҳ Ĭ�Ͼ��Ǵ��ĵ�ǰҳ
		}
		pageUtils.setCurrPage(currPage);// ���õ�ǰҳ�ǵڼ�ҳ
		pageUtils.setPageSize(8); // ����ÿҳ����
		pageUtils.setTotalSize(totalSize); // �����ܼ�¼��
		pageUtils.setTotalPage(totalSize); // ������ҳ��
		List<StudyResource> resList = sourceDao.searchSourceByPage2(filter,
				pageUtils, loginId);
		request.setAttribute("filter", filter);
		request.setAttribute("resList", resList);
		request.setAttribute("pageUtils", pageUtils);

		List<SourceType> types = typeDao.findAllType();
		// HttpSession session=request.getSession();
		session.setAttribute("typeList", types);

		List<User> users = userDao.findAllUser();
		session.setAttribute("userList", users);

		User loginUser = (User) session.getAttribute("loginUser");
		request.getRequestDispatcher("share.jsp").forward(request, response);

	}

	protected void delRes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int resId = 0;
		if (idStr != null) {
			resId = Integer.parseInt(idStr);
		}
		boolean f = sourceDao.delResource(resId);
		if (f) {
			response.sendRedirect("frontStudy?method=resList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void showRes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int resId = 0;
		if (idStr != null) {
			resId = Integer.parseInt(idStr);
		}
		StudyResource res = sourceDao.getResourceById(resId);
		request.setAttribute("res", res);
		request.getRequestDispatcher("showRes.jsp").forward(request, response);
	}

	protected void editRes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int resId = 0;
		if (idStr != null) {
			resId = Integer.parseInt(idStr);
		}
		StudyResource res = sourceDao.getResourceById(resId);
		HttpSession session = request.getSession();
		session.setAttribute("res", res);
		List<SourceType> types = typeDao.findAllType();
		session.setAttribute("typeList", types);
		request.getRequestDispatcher("reShareRes.jsp").forward(request,
				response);
	}

	protected void updateRes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory df = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(df);
		StudyResource res = null;
		System.out.println(res + "res");
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			for (FileItem item : fileItems) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if ("id".equals(name)) {
						int resId = 0;
						if (value != null) {
							resId = Integer.parseInt(value);
							res = sourceDao.getResourceById(resId);
						}
					}
					if ("typeId".equals(name)) {
						SourceType type = new SourceType();
						if (value != null && !"".equals(value)) {
							type.setId(Integer.parseInt(value));
						}
						res.setType(type);
					}
					if ("name".equals(name)) {
						res.setName(value);
					}
					if ("describe".equals(name)) {
						res.setDescribe(value);
					}

				} else {// �ļ���
					String fileName = item.getName();
					String path = request.getRealPath("/uploadFile");
					String fileRealName = UUID.randomUUID().toString();
					String fileType = fileName.substring(fileName
							.lastIndexOf("."));
					File file = new File(path, fileRealName + fileType);
					res.setUrl("uploadFile/" + fileRealName + fileType);
					try {
						item.write(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			boolean f = sourceDao.updateResource(res);
			if (f) {
				response.sendRedirect("frontStudy?method=resList");
			} else {
				response.sendRedirect("error.jsp");
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
