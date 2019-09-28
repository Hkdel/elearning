package com.zt.user.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zt.user.dao.AuthDao;
import com.zt.user.dao.RoleDao;
import com.zt.user.dao.UserDao;
import com.zt.user.dao.impl.AuthDaoImpl;
import com.zt.user.dao.impl.RoleDaoImpl;
import com.zt.user.dao.impl.UserDaoImpl;
import com.zt.user.po.Auth;
import com.zt.user.po.User;

@WebServlet("/admin/userLogin")
public class UserLoginServlet extends HttpServlet {
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
					// 根据登录对象显示相应status不为0的模块（子模块父模块都在且不为0）
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
					request.setAttribute("error", "密码输入错误");
					request.getRequestDispatcher("../login.jsp").forward(
							request, response);
				}
			} else {
				request.setAttribute("error", "用户名不存在");
				request.getRequestDispatcher("../login.jsp").forward(request,
						response);
			}
			// 登录
		} else {
			request.setAttribute("codeerror", "验证码输入错误！");
			request.getRequestDispatcher("../login.jsp").forward(request,
					response);

		}

	}

	protected void out(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("../login.jsp");
	}

}
