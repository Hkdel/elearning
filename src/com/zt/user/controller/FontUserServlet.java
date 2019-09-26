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

import com.zt.exam.dao.RecordDao;
import com.zt.exam.dao.impl.RecordDaoImpl;
import com.zt.exam.po.Record;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;

/**
 * Servlet implementation class FontUserServlet
 */
@WebServlet("/fontUser")
public class FontUserServlet extends HttpServlet {
	private UserDao userDao;
	private RecordDao recordDao;

	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDaoImpl();
		recordDao = new RecordDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("fontLogin".equals(method)) {
			fontLogin(request, response);
		}
		if ("fontOut".equals(method)) {
			fontOut(request, response);
		}
		if ("fontReg".equals(method)) {
			fontReg(request, response);
		}
		if ("fontReInfo".equals(method)) {
			fontReInfo(request, response);
		}
		if ("myMsg".equals(method)) {
			myMsg(request, response);
		}
	}

	protected void myMsg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			response.sendRedirect("login/fontLogin.jsp");
		} else {
			RecordDao recordDao = new RecordDaoImpl();
			List<Record> recordList = recordDao.getRecordsById(loginUser.getId());
			request.setAttribute("recordList", recordList);
			request.getRequestDispatcher("index/menu5.jsp").forward(request,
					response);
		}
	}

	protected void fontLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		String validateCode = (String) session.getAttribute("validateCode");
		if (code.equals(validateCode)) {
			String accountName = request.getParameter("accountName");
			String pass = request.getParameter("pass");
			User user = userDao.fontLogin(accountName);
			if (user != null) {
				if (user.getPass().equals(pass)) {
					session.setAttribute("loginUser", user);
					response.sendRedirect("index.jsp");
				} else {
					request.setAttribute("passerror", "密码输入错误");
					request.getRequestDispatcher("login/fontLogin.jsp")
							.forward(request, response);
				}
			} else {
				request.setAttribute("usererror", "用户名不存在");
				request.getRequestDispatcher("login/fontLogin.jsp").forward(
						request, response);
			}
			// 登录
		} else {
			request.setAttribute("codeerror", "验证码输入错误！");
			request.getRequestDispatcher("login/fontLogin.jsp").forward(
					request, response);

		}

	}

	protected void fontReg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 1：创建一个磁盘工厂类
		DiskFileItemFactory df = new DiskFileItemFactory();
		// 2:创建一个文件上传组件类
		ServletFileUpload upload = new ServletFileUpload(df);
		upload.setHeaderEncoding("utf-8");
		// 3:解析表单里面的所有项
		User user = new User();
		try {
			List<FileItem> fileItems = upload.parseRequest(request);// 解析
			String userName = null;
			String accountName = null;
			String pass = null;
			String repass = null;
			String sex = null;
			String birthday = null;
			String createTime = null;
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// 这个是表单项
					String name = item.getFieldName();// 变量名
					String value = item.getString("utf-8");// 值
					if ("userName".equals(name)) {
						userName = value;
					}
					if ("accountName".equals(name)) {
						accountName = value;
					}
					if ("pass".equals(name)) {
						pass = value;
					}
					if ("repass".equals(name)) {
						repass = value;
					}
					if ("sex".equals(name)) {
						sex = value;
					}
					if ("birthday".equals(name)) {
						birthday = value;

					}
					if ("createTime".equals(name)) {
						createTime = value;
					}
				} else { // 文件域
					String photo = item.getName();
					String path = request.getRealPath("/photo");// 获取文件需要上传到的路径
					String photoRealName = UUID.randomUUID().toString();// UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。
					String photoType = photo.substring(photo.lastIndexOf("."));
					File headPhoto = new File(path, photoRealName + photoType);
					user.setPhoto("photo/" + photoRealName + photoType);
					try {
						item.write(headPhoto);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			if (pass.equals(repass)) {
				user.setName(userName);
				user.setAccountName(accountName);
				user.setPass(pass);
				user.setSex(sex);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date bdt = null;
				try {
					bdt = sdf.parse(birthday);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user.setBirthday(bdt);
				Date cdt = null;
				try {
					cdt = sdf.parse(createTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user.setCreateTime(cdt);
				boolean f = userDao.regUser(user);
				if (f) {
					request.setAttribute("regSuccess", "注册用户成功");
					request.getRequestDispatcher("login/fontLogin.jsp")
							.forward(request, response);
				} else {
					request.setAttribute("regerror", "注册用户失败！");
					request.getRequestDispatcher("login/fontReg.jsp").forward(
							request, response);
				}
			} else {
				request.setAttribute("passerror", "密码不一致，请重新操作！");
				request.getRequestDispatcher("login/fontReg.jsp").forward(
						request, response);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	protected void fontOut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("login/fontLogin.jsp");
	}

	protected void fontReInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 1：创建一个磁盘工厂类
		DiskFileItemFactory df = new DiskFileItemFactory();
		// 2:创建一个文件上传组件类
		ServletFileUpload upload = new ServletFileUpload(df);
		upload.setHeaderEncoding("utf-8");
		// 3:解析表单里面的所有项
		User user = new User();
		try {
			List<FileItem> fileItems = upload.parseRequest(request);// 解析
			String userName = null;
			String pass = null;
			String repass = null;
			String userId = null;
			String photoRealName = null;
			String photoType = null;
			String photo = null;
			String img = null;
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// 这个是表单项
					String name = item.getFieldName();// 变量名
					String value = item.getString("utf-8");// 值
					if ("userName".equals(name)) {
						userName = value;
					}
					if ("pass".equals(name)) {
						pass = value;
					}
					if ("repass".equals(name)) {
						repass = value;
					}
					if ("userId".equals(name)) {
						userId = value;
					}
					if ("photo".equals(name)) {
						photo = value;
					}
				} else { // 文件域
					img = item.getName();
					if (img != null && !"".equals(img)) {
						String path = request.getRealPath("/photo");// 获取文件需要上传到的路径
						photoRealName = UUID.randomUUID().toString();// UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。
						photoType = img.substring(img.lastIndexOf("."));
						File headPhoto = new File(path, photoRealName
								+ photoType);
						try {
							item.write(headPhoto);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			if (pass.equals(repass)) {
				HttpSession session = request.getSession();
				User oldUser = (User) session.getAttribute("loginUser");
				user = userDao.findUserById(Integer.parseInt(userId));
				user.setName(userName);
				user.setPass(pass);
				if (img != null && !"".equals(img)) {
					user.setPhoto("photo/" + photoRealName + photoType);
				} else {
					user.setPhoto(photo);
				}
				boolean f = userDao.updateFontUser(user);
				if (f) {
					if (oldUser.getPass().equals(user.getPass())) {
						user = userDao.findUserById(Integer.parseInt(userId));
						session.setAttribute("loginUser", user);
						request.getRequestDispatcher("index.jsp").forward(
								request, response);
					} else {
						request.setAttribute("updateSuccess", "用户密码已修改,请重新登录!");
						request.getRequestDispatcher("login/fontLogin.jsp")
								.forward(request, response);
					}
				} else {
					request.setAttribute("updateerror", "用户修改失败！");
					request.getRequestDispatcher("info/infoUpdate.jsp")
							.forward(request, response);
				}
			} else {
				request.setAttribute("passerror", "密码不一致，请重新操作！");
				request.getRequestDispatcher("info/infoUpdate.jsp").forward(
						request, response);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

}
