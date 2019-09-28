package com.zt.user.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import com.zt.user.dao.ImgDao;
import com.zt.user.dao.impl.ImgDaoImpl;
import com.zt.user.po.Photo;
import com.zt.user.po.Role;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

/**
 * Servlet implementation class ImgServlet
 */
@WebServlet("/admin/system/img")
public class ImgServlet extends HttpServlet {
	private ImgDao imgDao;

	public void init(ServletConfig config) throws ServletException {
		imgDao = new ImgDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("list".equals(method)) {
			list(request, response);
		}
		if ("add".equals(method)) {
			add(request, response);
		}
		if ("delete".equals(method)) {
			delete(request, response);
		}
		if ("edit".equals(method)) {
			edit(request, response);
		}
		if ("update".equals(method)) {
			update(request, response);
		}
	}

	protected void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int totalSize = imgDao.getTotalSize();
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;
		pageUtils.setCurrPage(currPage);
		String page = request.getParameter("page");
		if (page != null && !"".equals(page)) {
			pageUtils.setCurrPage(Integer.parseInt(page));
		}
		pageUtils.setPageSize(3);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Photo> photos = imgDao.findAllImg(pageUtils);
		HttpSession session = request.getSession();
		request.setAttribute("photos", photos);
		session.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("img/imgList.jsp").forward(request,
				response);
	}

	protected void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1������һ�����̹�����
		DiskFileItemFactory df = new DiskFileItemFactory();
		// 2:����һ���ļ��ϴ������
		ServletFileUpload upload = new ServletFileUpload(df);
		upload.setHeaderEncoding("utf-8");
		try {
			List<FileItem> fileItems = upload.parseRequest(request);// ����
			String place = null;
			String createTime = null;
			String createName = null;
			String urlRealName = null;
			String urlType = null;
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// ����Ǳ���
					String name = item.getFieldName();// ������
					String value = item.getString("utf-8");// ֵ
					if ("place".equals(name)) {
						place = value;
					}
					if ("createTime".equals(name)) {
						createTime = value;
					}
					if ("createName".equals(name)) {
						createName = value;
					}
				} else { // �ļ���
					String url = item.getName();
					String path = request.getRealPath("/photo");// ��ȡ�ļ���Ҫ�ϴ�����·��
					urlRealName = UUID.randomUUID().toString();// UUID.randomUUID().toString()��javaJDK�ṩ��һ���Զ����������ķ�����
					urlType = url.substring(url.lastIndexOf("."));
					File trueUrl = new File(path, urlRealName + urlType);
					try {
						item.write(trueUrl);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			Photo photo = new Photo();
			photo.setUrl("photo/" + urlRealName + urlType);
			photo.setPlace(Integer.parseInt(place));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date cdt = null;
			try {
				cdt = sdf.parse(createTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			photo.setCreateTime(cdt);
			HttpSession session = request.getSession();
			User user1 = (User) session.getAttribute("loginSysUser");
			photo.setUser(user1);
			boolean f = imgDao.addPhoto(photo);
			if (f) {
				int totalSize = imgDao.getTotalSize();
				PageUtils pageUtils = (PageUtils) session
						.getAttribute("pageUtils");
				pageUtils.setTotalSize(totalSize);
				pageUtils.setTotalPage(totalSize);
				int page = pageUtils.getTotalPage();
				request.setAttribute("addSuccess", "添加成功");
				request.getRequestDispatcher("img?method=list&page=" + page)
						.forward(request, response);
			} else {
				request.setAttribute("adderror", "添加失败");
				request.getRequestDispatcher("img?method=list").forward(
						request, response);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	protected void delete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String imgId = request.getParameter("imgId");
		int id = 0;
		if (imgId != null && !"".equals(imgId)) {
			id = Integer.parseInt(imgId);
		}
		boolean f = imgDao.deletePhoto(id);
		if (f) {
			request.setAttribute("deleteSuccess", "删除成功");
			request.getRequestDispatcher("img?method=list").forward(request,
					response);
		} else {
			request.setAttribute("deleteerror", "删除失败");
			request.getRequestDispatcher("img?method=list").forward(request,
					response);
		}
	}

	protected void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String imgId = request.getParameter("imgId");
		int id = 0;
		if (imgId != null && !"".equals(imgId)) {
			id = Integer.parseInt(imgId);
		}
		Photo photo = imgDao.findPhotoById(id);
		request.setAttribute("photo", photo);
		request.getRequestDispatcher("img/imgUpdate.jsp").forward(request,
				response);
	}

	protected void update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 1������һ�����̹�����
		DiskFileItemFactory df = new DiskFileItemFactory();
		// 2:����һ���ļ��ϴ������
		ServletFileUpload upload = new ServletFileUpload(df);
		upload.setHeaderEncoding("utf-8");
		try {
			List<FileItem> fileItems = upload.parseRequest(request);// ����
			String imgId = null;
			String place = null;
			String createTime = null;
			String createName = null;
			String url = null;
			String newUrl = null;
			String newUrlRealName = null;
			String newUrlType = null;
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// ����Ǳ���
					String name = item.getFieldName();// ������
					String value = item.getString("utf-8");// ֵ
					if ("imgId".equals(name)) {
						imgId = value;
					}
					if ("place".equals(name)) {
						place = value;
					}
					if ("url".equals(name)) {
						url = value;
					}
					if ("createTime".equals(name)) {
						createTime = value;
					}
					if ("createName".equals(name)) {
						createName = value;
					}
				} else { // �ļ���
					newUrl = item.getName();
					if (newUrl != null && !"".equals(newUrl)) {
						String path = request.getRealPath("/photo");// ��ȡ�ļ���Ҫ�ϴ�����·��
						newUrlRealName = UUID.randomUUID().toString();// UUID.randomUUID().toString()��javaJDK�ṩ��һ���Զ����������ķ�����
						newUrlType = newUrl.substring(newUrl.lastIndexOf("."));
						File trueUrl = new File(path, newUrlRealName
								+ newUrlType);
						try {
							item.write(trueUrl);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			Photo photo = imgDao.findPhotoById(Integer.parseInt(imgId));
			if (newUrl == null || "".equals(newUrl)) {
				photo.setUrl(url);
			} else {
				photo.setUrl("photo/" + newUrlRealName + newUrlType);
			}
			photo.setPlace(Integer.parseInt(place));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date cdt = null;
			try {
				cdt = sdf.parse(createTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			photo.setCreateTime(cdt);
			HttpSession session = request.getSession();
			User user1 = (User) session.getAttribute("loginSysUser");
			photo.setUser(user1);
			boolean f = imgDao.updatePhoto(photo);
			if (f) {
				PageUtils pageUtils = (PageUtils) session
						.getAttribute("pageUtils");
				int page = pageUtils.getCurrPage();
				request.setAttribute("updateSuccess", "修改成功");
				request.getRequestDispatcher("img?method=list&page=" + page)
						.forward(request, response);
			} else {
				request.setAttribute("updateerror", "修改失败");
				request.getRequestDispatcher("img?method=list").forward(
						request, response);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

}
