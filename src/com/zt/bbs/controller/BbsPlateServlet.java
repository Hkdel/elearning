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
import com.zt.bbs.dao.impl.BbsPlateDaoImpl;
import com.zt.bbs.po.BbsPlate;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/admin/bbs/plate/sysPlate")
public class BbsPlateServlet extends HttpServlet {
	private BbsPlateDao bbsPlateDao;

	public void init(ServletConfig config) throws ServletException {
		bbsPlateDao = new BbsPlateDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if ("list".equals(method)) {
			list(request, response);
		}
		if ("add".equals(method)) {
			add(request, response);
		}
		if ("del".equals(method)) {
			del(request, response);
		}
		if ("edit".equals(method)) {
			edit(request, response);
		}
		if ("update".equals(method)) {
			update(request, response);
		}
		if ("updateStatus".equals(method)) {
			updateStatus(request, response);
		}
		if("findByPage".equals(method)){
			findByPage(request, response);
		}
	}

	protected void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<BbsPlate> bbsPlateList = bbsPlateDao.findAll();
		request.setAttribute("bbsPlateList", bbsPlateList);
		request.getRequestDispatcher("plateList.jsp").forward(request, response);
	}

	protected void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BbsPlate bbsPlate = new BbsPlate();
		DiskFileItemFactory df = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(df);
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// 这个是表单项
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
				} else { // 文件域
					String fileName = item.getName();
					String path = request.getRealPath("/bbs/bbsPlatePhoto");
					String fileRealName = UUID.randomUUID().toString();
					String fileType = null;
					if(fileName!=null && !"".equals(fileName)){
						fileType = fileName.substring(fileName.lastIndexOf("."));
						File file = new File(path, fileRealName + fileType);
						bbsPlate.setPhoto("bbsPlatePhoto/" + fileRealName + fileType);
						try {
							item.write(file);
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}else{
						bbsPlate.setPhoto("/bbs/bbsPlatePhoto/mo.jpg");
					}
				}
			}
			HttpSession session=request.getSession();
			User createUser = (User) session.getAttribute("loginSysUser");
			bbsPlate.setCreateUser(createUser);
			boolean f = bbsPlateDao.addBbsPlate(bbsPlate);
			if (f) {
				response.sendRedirect("sysPlate?method=findByPage");
			} else {
				response.sendRedirect("error.jsp");
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	protected void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		boolean f = bbsPlateDao.delBbsPlate(id);
		if (f) {
			response.sendRedirect("sysPlate?method=findByPage");
		} else {
			response.sendRedirect("error.jsp");
		}
	}
	
	protected void edit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		BbsPlate bbsPlate = bbsPlateDao.getById(id);
		String name = bbsPlate.getName();
		String introduction = bbsPlate.getIntroduction();
		String photo = bbsPlate.getPhoto();
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("introduction", introduction);
		request.setAttribute("photo", photo);
		request.getRequestDispatcher("plateUpdate.jsp")
				.forward(request, response);
	}

	protected void update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BbsPlate bbsPlate = new BbsPlate();
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		bbsPlate.setId(id);
		String photo = request.getParameter("photo");
		DiskFileItemFactory df = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(df);
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// 这个是表单项
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
				} else { // 文件域
					String fileName = item.getName();
					String path = request.getRealPath("/bbs/bbsPlatePhoto");
					String fileRealName = UUID.randomUUID().toString();
					String fileType = null;
					if(fileName!=null && !"".equals(fileName)){
						fileType = fileName.substring(fileName.lastIndexOf("."));
						File file = new File(path, fileRealName + fileType);
						bbsPlate.setPhoto("bbsPlatePhoto/" + fileRealName + fileType);
						try {
							item.write(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						bbsPlate.setPhoto(photo);
					}
				}
			}
			HttpSession session=request.getSession();
			User createUser = (User) session.getAttribute("loginSysUser");
			bbsPlate.setCreateUser(createUser);
			boolean f = bbsPlateDao.updateBbsPlate(bbsPlate);
			if (f) {
				response.sendRedirect("sysPlate?method=findByPage");
			} else {
				response.sendRedirect("error.jsp");
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	protected void updateStatus(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
		}
		String status = request.getParameter("status");
		if (status.equals("1")) {
			status = "0";
		} else if (status.equals("0")) {
			status = "1";
		}
		boolean f = bbsPlateDao.updateStatus(id, status);
		if (f) {
			response.sendRedirect("sysPlate?method=findByPage");
		} else {
			response.sendRedirect("error.jsp");
		}
	}
	protected void findByPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bName = request.getParameter("bName");
		String uName = request.getParameter("uName");
		String status = request.getParameter("status");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		Map filter = new HashMap();
		if (beginTime != null && !"".equals(beginTime)) {
			filter.put("begin", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			filter.put("end", endTime);
		}
		if(bName!=null && !"".equals(bName)){
			filter.put("bName", bName);
		}
		if(uName!=null && !"".equals(uName)){
			filter.put("uName", uName);
		}
		if(status!=null && !"-1".equals(status)){
			filter.put("status", status);
		}
		int totalSize = bbsPlateDao.getTotalSizeByFilter(filter);
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;
		String page = request.getParameter("page");
		if (page != null && !"".equals(page)) {
			currPage = Integer.parseInt(page);
		}
		pageUtils.setCurrPage(currPage);
		pageUtils.setPageSize(5);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<BbsPlate> plateList = bbsPlateDao.findByPageFilter(filter, pageUtils);
		request.setAttribute("filter", filter);
		request.setAttribute("pageUtils", pageUtils);
		request.setAttribute("plateList", plateList);
		request.getRequestDispatcher("plateList.jsp").forward(request, response);
	}
}
