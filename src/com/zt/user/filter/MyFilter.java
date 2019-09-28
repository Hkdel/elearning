package com.zt.user.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
/*
 * 权限过滤器
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zt.user.dao.RoleDao;
import com.zt.user.dao.impl.RoleDaoImpl;
import com.zt.user.po.Auth;
import com.zt.user.po.User;

@WebFilter("/admin/*")
public class MyFilter implements Filter {
	private String[] chainPaths = { "admin/login.jsp", "admin/userLogin",
			"admin/system/noAuth.jsp", "admin/main.jsp", "admin/top.jsp",
			"admin/footer.jsp", "admin/left.jsp",
			 };
		//"admin/system/user/userResetPass.jsp",
	//"admin/system/loginUserRePass.jsp"
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// 获取请求的链接
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String accUrl = request.getRequestURI();
		// /20190910sysAuth/admin/system/role/role?method=list
		String accRealUrl = accUrl.substring(accUrl.indexOf("/",
				accUrl.indexOf("/") + 1) + 1);
		// admin/system/role/role?method=list
		System.out.println("accRealUrl=" + accRealUrl);
		HttpSession session = request.getSession();
		boolean isChain = false;
		for (String s : chainPaths) {
			if (accRealUrl.indexOf(s) != -1) {// 在允许放行的界面
				isChain = true;
				break;
			}
		}
		if (!isChain) {
			if (session.getAttribute("loginSysUser") == null) {
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + path + "/";
				response.sendRedirect(basePath
						+ "login.jsp");
				//response.sendRedirect("login.jsp");
			} else {
				// 已经登录 判断是否有权限
					User user = (User) session.getAttribute("loginSysUser");
					RoleDao roleDao = new RoleDaoImpl();
					List<Auth> authList = roleDao.findRoleAuth(user.getRole()
							.getId());
					boolean isAuth = false;
					for (Auth a : authList) {
						String realUrl = "";
						if (accRealUrl.indexOf("jsp") != -1) {
							String temp = accRealUrl.substring(0,accRealUrl.lastIndexOf("/"));
							realUrl = temp.substring(0,temp.lastIndexOf("/"));
						} else {
							realUrl = accRealUrl.substring(0,accRealUrl.lastIndexOf("/"));
						}
						if (a.getUrl().indexOf(realUrl) != -1) {// 在允许放行的界面
							isAuth = true;
							break;
						}
					}
					if (isAuth) {
						chain.doFilter(request, response);
					} else {
						String path = request.getContextPath();
						String basePath = request.getScheme() + "://"
								+ request.getServerName() + ":"
								+ request.getServerPort() + path + "/";
						response.sendRedirect(basePath
								+ "admin/system/noAuth.jsp");
					}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {

	}
}
