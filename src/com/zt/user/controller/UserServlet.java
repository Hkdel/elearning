package com.zt.user.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.zt.user.po.Auth;
import com.zt.user.po.Role;
import com.zt.user.dao.AuthDao;
import com.zt.user.dao.RoleDao;
import com.zt.user.dao.RoleAuthDao;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.AuthDaoImpl;
import com.zt.user.dao.impl.RoleDaoImpl;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/user")
public class UserServlet extends HttpServlet {
	private UserDao userDao;
	private RoleDao roleDao;
	private AuthDao authDao;

	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDaoImpl();
		roleDao = new RoleDaoImpl();
		authDao = new AuthDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("login".equals(method)) {
			login(request, response);
		}
		if ("out".equals(method)) {
			out(request, response);
		}
		if ("list".equals(method)) {
			list(request, response);
		}
		if ("edit".equals(method)) {
			edit(request, response);
		}
		if ("update".equals(method)) {
			update(request, response);
		}
		if ("cancel".equals(method)) {
			cancel(request, response);
		}
		if ("restore".equals(method)) {
			restore(request, response);
		}
		if ("add".equals(method)) {
			add(request, response);
		}
		if ("resetPass".equals(method)) {
			resetPass(request, response);
		}
	}

	protected void login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		String validateCode = (String) session.getAttribute("validateCode");
		if (code.equals(validateCode)) {
			String accountName = request.getParameter("accountName");
			String pass = request.getParameter("pass");
			User user = userDao.login(accountName);
			if (user != null) {
				if (user.getPass().equals(pass)) {
					session.setAttribute("loginSysUser", user);
					// ���ݵ�¼������ʾ��Ӧstatus��Ϊ0��ģ�飨��ģ�鸸ģ�鶼���Ҳ�Ϊ0��
					List<Auth> authList = roleDao.findRoleAuth(user.getRole()
							.getId());
					Set<Auth> parentList = new HashSet<Auth>();
					for (Auth a : authList) {
						Auth parent = authDao.getAuthParentById(a.getId());
						a.setParent(parent);
						parentList.add(parent);
					}
					session.setAttribute("authList", authList);
					session.setAttribute("parentList", parentList);
					response.sendRedirect("../main.jsp");
				} else {
					request.setAttribute("error", "�����������");
					request.getRequestDispatcher("../login.jsp").forward(
							request, response);
				}
			} else {
				request.setAttribute("error", "�û���������");
				request.getRequestDispatcher("../login.jsp").forward(request,
						response);
			}
			// ��¼
		} else {
			request.setAttribute("codeerror", "��֤���������");
			request.getRequestDispatcher("../login.jsp").forward(request,
					response);

		}

	}

	protected void out(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("login.jsp");
	}

	protected void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map filter = new HashMap();
		String userName = request.getParameter("userName");
		String accountName = request.getParameter("accountName");
		String roleId = request.getParameter("roleId");
		/*
		 * int id = 0; if(roleId != null && !"0".equals(roleId)){ id =
		 * Integer.parseInt(roleId); }
		 */
		String status = request.getParameter("status");
		if (userName != null && !"".equals(userName)) {
			filter.put("userName", userName);
		}
		if (accountName != null && !"0".equals(accountName)) {
			filter.put("accountName", accountName);
		}
		if (roleId != null && !"0".equals(roleId)) {
			filter.put("roleId", roleId);
		}
		if (status != null && !"-1".equals(status)) {
			filter.put("status", status);
		}
		int totalSize = userDao.getTotalSizeByFilter(filter);
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;
		pageUtils.setCurrPage(currPage);
		String page = request.getParameter("page");
		if (page != null && !"".equals(page)) {
			pageUtils.setCurrPage(Integer.parseInt(page));
		}
		pageUtils.setPageSize(2);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<User> users = userDao.findAllUser(filter, pageUtils);
		List<Role> roles = roleDao.findAllRole();
		List<Role> roles1 = roleDao.findAllRole();
		HttpSession session = request.getSession();
		session.setAttribute("pageUtils", pageUtils);
		request.setAttribute("filter", filter);
		request.setAttribute("roles", roles);
		request.setAttribute("roles1", roles1);
		request.setAttribute("users", users);
		request.getRequestDispatcher("system/user/userList.jsp").forward(
				request, response);
	}

	protected void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		int id = 0;
		if (userId != null && !"".equals(userId)) {
			id = Integer.parseInt(userId);
		}
		User user = userDao.findUserById(id);
		request.setAttribute("user", user);
		request.getRequestDispatcher("system/user/userUpdate.jsp").forward(
				request, response);
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
			String userName = null;
			String userId = null;
			String accountName = null;
			String sex = null;
			String birthday = null;
			String roleId = null;
			String bbsScore = null;
			String examScore = null;
			String createTime = null;
			String createName = null;
			String photo = null;
			String newPhoto = null;
			String photoRealName = null;
			String photoType = null;
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// ����Ǳ���
					String name = item.getFieldName();// ������
					String value = item.getString("utf-8");// ֵ
					if ("photo".equals(name)) {
						photo = value;
					}
					if ("userName".equals(name)) {
						userName = value;
					}
					if ("userId".equals(name)) {
						userId = value;
					}
					if ("accountName".equals(name)) {
						accountName = value;
						// user.setAccountName(value);
					}
					if ("sex".equals(name)) {
						sex = value;

					}
					if ("birthday".equals(name)) {
						birthday = value;
					}
					if ("roleId".equals(name)) {
						roleId = value;
					}
					if ("bbsScore".equals(name)) {
						bbsScore = value;
					}
					if ("examScore".equals(name)) {
						examScore = value;
					}
					if ("createTime".equals(name)) {
						createTime = value;
					}
					if ("createName".equals(name)) {
						createName = value;
					}
				} else { // �ļ���
					newPhoto = item.getName();
					if (newPhoto != null && !"".equals(newPhoto)) {
						String path = request.getRealPath("/photo");// ��ȡ�ļ���Ҫ�ϴ�����·��
						// System.out.println(path);
						photoRealName = UUID.randomUUID().toString();// UUID.randomUUID().toString()��javaJDK�ṩ��һ���Զ����������ķ�����
						photoType = newPhoto.substring(newPhoto
								.lastIndexOf("."));
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
			User user = userDao.findUserById(Integer.parseInt(userId));
			if (newPhoto == null || "".equals(newPhoto)) {
				user.setPhoto(photo);
			} else {
				user.setPhoto("photo/" + photoRealName + photoType);
			}
			user.setName(userName);
			user.setAccountName(accountName);
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
			int rid = 0;
			if (roleId != null && !"0".equals(roleId)) {
				rid = Integer.parseInt(roleId);
			}
			Role role = roleDao.getRoleById(rid);
			user.setRole(role);
			user.setBbsScore(Integer.parseInt(bbsScore));
			user.setExamScore(Double.parseDouble(examScore));
			Date cdt = null;
			try {
				cdt = sdf.parse(createTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setCreateTime(cdt);
			HttpSession session = request.getSession();
			User user1 = (User) session.getAttribute("loginSysUser");
			user.setUser(user1);
			// user.setRank(null);
			boolean f = userDao.updateUser(user);
			if (f) {
				User user2 = userDao.findUserById(Integer.parseInt(userId));
				String accountName1 = user.getAccountName();
				request.setAttribute("user", user2);
				request.setAttribute("updateSuccess", "�޸ĳɹ�");
				request.getRequestDispatcher(
						"user?method=list&accountName=" + accountName1)
						.forward(request, response);
			} else {
				User user2 = userDao.findUserById(Integer.parseInt(userId));
				String accountName1 = user.getAccountName();
				request.setAttribute("user", user2);
				request.setAttribute("updateerror", "�޸�ʧ�ܣ�");
				request.getRequestDispatcher(
						"user?method=list&accountName=" + accountName1)
						.forward(request, response);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

	}

	protected void cancel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		int id = 0;
		if (userId != null && !"".equals(userId)) {
			id = Integer.parseInt(userId);
		}
		boolean f = userDao.cancelUser(id);
		if (f) {
			User user = userDao.findUserById(id);
			String accountName = user.getAccountName();
			request.setAttribute("user", user);
			request.setAttribute("cancelSuccess", "ע���ɹ�");
			request.getRequestDispatcher(
					"user?method=list&accountName=" + accountName).forward(
					request, response);
		} else {
			User user = userDao.findUserById(id);
			String accountName = user.getAccountName();
			request.setAttribute("user", user);
			request.setAttribute("cancelerror", "ע��ʧ��");
			request.getRequestDispatcher(
					"user?method=list&accountName=" + accountName).forward(
					request, response);
		}
	}

	protected void restore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		int id = 0;
		if (userId != null && !"".equals(userId)) {
			id = Integer.parseInt(userId);
		}
		boolean f = userDao.restoreUser(id);
		if (f) {
			User user = userDao.findUserById(id);
			String accountName = user.getAccountName();
			request.setAttribute("user", user);
			request.setAttribute("restoreSuccess", "�ָ��ɹ�");
			request.getRequestDispatcher(
					"user?method=list&accountName=" + accountName).forward(
					request, response);
		} else {
			User user = userDao.findUserById(id);
			String accountName = user.getAccountName();
			request.setAttribute("user", user);
			request.setAttribute("restoreerror", "�ָ�ʧ��");
			request.getRequestDispatcher(
					"user?method=list&accountName=" + accountName).forward(
					request, response);
		}
	}

	protected void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 1������һ�����̹�����
		DiskFileItemFactory df = new DiskFileItemFactory();
		// 2:����һ���ļ��ϴ������
		ServletFileUpload upload = new ServletFileUpload(df);
		upload.setHeaderEncoding("utf-8");
		// 3:�����������������
		User user = new User();
		try {
			List<FileItem> fileItems = upload.parseRequest(request);// ����
			String userName = null;
			String accountName = null;
			String pass = null;
			String repass = null;
			String sex = null;
			String birthday = null;
			String roleId = null;
			String bbsScore = null;
			String examScore = null;
			String createTime = null;
			String createName = null;
			for (FileItem item : fileItems) {
				if (item.isFormField()) {// ����Ǳ���

					String name = item.getFieldName();// ������
					String value = item.getString("utf-8");// ֵ
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
					if ("roleId".equals(name)) {
						roleId = value;
					}
					if ("bbsScore".equals(name)) {
						bbsScore = value;
					}
					if ("examScore".equals(name)) {
						examScore = value;
					}
					if ("createTime".equals(name)) {
						createTime = value;
					}
					if ("createName".equals(name)) {
						createName = value;
					}
				} else { // �ļ���
					String photo = item.getName();
					String path = request.getRealPath("/photo");// ��ȡ�ļ���Ҫ�ϴ�����·��
					String photoRealName = UUID.randomUUID().toString();// UUID.randomUUID().toString()��javaJDK�ṩ��һ���Զ����������ķ�����
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
				int rid = 0;
				if (roleId != null && !"0".equals(roleId)) {
					rid = Integer.parseInt(roleId);
				}
				Role role = roleDao.getRoleById(rid);
				user.setRole(role);
				user.setBbsScore(Integer.parseInt(bbsScore));
				user.setExamScore(Double.parseDouble(examScore));
				Date cdt = null;
				try {
					cdt = sdf.parse(createTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user.setCreateTime(cdt);
				HttpSession session = request.getSession();
				User user1 = (User) session.getAttribute("loginSysUser");
				user.setUser(user1);
				user.setRank(null);
				boolean f = userDao.addUser(user);
				if (f) {
					PageUtils pageUtils = (PageUtils) session
							.getAttribute("pageUtils");
					Map filter = new HashMap();
					String userName1 = request.getParameter("userName");
					String accountName1 = request.getParameter("accountName");
					String roleId1 = request.getParameter("roleId");
					String status = request.getParameter("status");
					if (userName1 != null && !"".equals(userName1)) {
						filter.put("userName", userName1);
					}
					if (accountName1 != null && !"0".equals(accountName1)) {
						filter.put("accountName", accountName1);
					}
					if (roleId1 != null && !"0".equals(roleId1)) {
						filter.put("roleId", roleId1);
					}
					if (status != null && !"-1".equals(status)) {
						filter.put("status", status);
					}
					int totalSize = userDao.getTotalSizeByFilter(filter);
					pageUtils.setTotalSize(totalSize);
					pageUtils.setTotalPage(totalSize);
					int page = pageUtils.getTotalPage();
					request.setAttribute("user", user);
					request.setAttribute("addSuccess", "��ӳɹ�");
					request.getRequestDispatcher(
							"user?method=list&page=" + page).forward(request,
							response);
				} else {
					request.setAttribute("adderror", "���ʧ�ܣ�");
					request.getRequestDispatcher("user?method=list").forward(
							request, response);
				}
			} else {
				request.setAttribute("passerror", "���벻һ�£������²�����");
				request.getRequestDispatcher("system/user/userAdd.jsp")
						.forward(request, response);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	protected void resetPass(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		User user = userDao.findUserById(Integer.parseInt(userId));
		String newPass = request.getParameter("newPass");
		String reNewPass = request.getParameter("reNewPass");
		if (newPass.equals(reNewPass)) {
			user.setPass(newPass);
			boolean f = userDao.resetPass(user);
			if (f) {
				User user1 = userDao.findUserById(Integer.parseInt(userId));
				String accountName = user.getAccountName();
				request.setAttribute("user", user1);
				request.setAttribute("resetSuccess", "�������óɹ�");
				request.setAttribute("user", user);
				HttpSession session = request.getSession();
				User loginUser = (User) session.getAttribute("loginSysUser");
				if (loginUser.getId() == Integer.parseInt(userId)) {
					request.setAttribute("reLogin", "��ǰ�û��������޸ģ������µ�¼��");
					request.getRequestDispatcher("../login.jsp").forward(
							request, response);
				} else {
					request.getRequestDispatcher(
							"user?method=list&accountName=" + accountName)
							.forward(request, response);
				}
			} else {
				request.setAttribute("reseterror", "��������ʧ�ܣ���������");
				request.getRequestDispatcher("system/user/userResetPass.jsp")
						.forward(request, response);
			}
		} else {
			request.setAttribute("reseterror", "�����޸�ʧ�ܣ�ȷ�������������벻һ�£������²�����");
			request.getRequestDispatcher("system/user/userResetPass.jsp")
					.forward(request, response);
		}
	}
}
