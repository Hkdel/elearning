package com.zt.user.controller;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.zt.user.dao.AuthDao;
import com.zt.user.dao.impl.AuthDaoImpl;
import com.zt.user.po.Auth;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/admin/system/auth")
public class AuthServlet extends HttpServlet {
	private AuthDao authDao;

	public void init(ServletConfig config) throws ServletException {
		authDao = new AuthDaoImpl();
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
		if ("cancel".equals(method)) {
			cancel(request, response);
		}
		if ("restore".equals(method)) {
			restore(request, response);
		}
		if ("edit".equals(method)) {
			edit(request, response);
		}
		if ("update".equals(method)) {
			update(request, response);
		}

	}

	// ����ģ��
	protected void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map filter = new HashMap();
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		if (name != null && !"".equals(name)) {
			filter.put("name", name);
		}
		if (status != null && !"-1".equals(status)) {
			filter.put("status", status);
		}
		int totalSize = authDao.getTotalSizeByFilter(filter);
		PageUtils pageUtils = new PageUtils();
		int currPage = 1;
		pageUtils.setCurrPage(currPage);
		String page = request.getParameter("page");
		if (page != null && !"".equals(page)) {
			pageUtils.setCurrPage(Integer.parseInt(page));
		}
		pageUtils.setPageSize(5);
		pageUtils.setTotalSize(totalSize);
		pageUtils.setTotalPage(totalSize);
		List<Auth> auths = authDao.findAll(filter, pageUtils);// ��ѯ�����з��������Ĺ���ģ��
		/*
		 * Set<Auth> parentAuths = new HashSet<Auth>();//ȥ�� for(Auth a:auths){
		 * if(a.getParent().getId()==0){//���==0���Ǹ������� parentAuths.add(a); } }
		 */
		HttpSession session = request.getSession();
		session.setAttribute("pageUtils", pageUtils);
		request.setAttribute("filter", filter);
		request.setAttribute("auths", auths);
		request.getRequestDispatcher("model/modelList.jsp").forward(request,
				response);
	}

	// ���ģ��
	protected void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String authName = request.getParameter("authName");// ģ����
		String url = request.getParameter("url");
		if (authName == null || "".equals(authName)) {
			request.setAttribute("error", "������ģ�����ƣ�");
			request.getRequestDispatcher("model/modelAdd.jsp").forward(request,
					response);
		} else {
			String parentId = request.getParameter("parentId");
			String nowTime = request.getParameter("createTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date createTime = null;
			try {
				createTime = sdf.parse(nowTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginSysUser");
			Auth auth = new Auth();
			auth.setName(authName);
			if (url == null || "".equals(url)) {
				auth.setUrl(null);
			} else {
				auth.setUrl(url);
			}
			auth.setUser(user);
			auth.setCreateTime(createTime);
			if ("0".equals(parentId)) {
				auth.setParent(null);
			} else {
				auth.setParent(authDao.getAuthById(Integer.parseInt(parentId)));
			}
			boolean f = authDao.addAuth(auth);
			if (f) {
				PageUtils pageUtils = (PageUtils) session
						.getAttribute("pageUtils");
				Map filter = new HashMap();
				String name = request.getParameter("name");
				String status = request.getParameter("status");
				if (name != null && !"".equals(name)) {
					filter.put("name", name);
				}
				if (status != null && !"-1".equals(status)) {
					filter.put("status", status);
				}
				int totalSize = authDao.getTotalSizeByFilter(filter);
				pageUtils.setTotalSize(totalSize);
				pageUtils.setTotalPage(totalSize);
				int page = pageUtils.getTotalPage();
				request.setAttribute("auth", auth);
				request.setAttribute("addSuccess", "ģ����ӳɹ�");
				request.getRequestDispatcher("auth?method=list&page=" + page)
						.forward(request, response);
			} else {
				request.setAttribute("adderror", "ģ�����ʧ��");
				request.getRequestDispatcher("auth?method=list").forward(
						request, response);
			}
		}

	}

	// ע��ģ��
	protected void cancel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authId = request.getParameter("authId");
		int id = 0;
		if (authId != null && !"".equals(authId)) {
			id = Integer.parseInt(authId);
		}
		System.out.println(id);
		boolean f = authDao.cancelAuth(id);
		if (f) {
			/*
			 * HttpSession session = request.getSession(); PageUtils pageUtils =
			 * (PageUtils) session.getAttribute("pageUtils"); int page =
			 * pageUtils.getCurrPage();
			 */
			Auth auth = authDao.getAuthById(id);
			String name = auth.getName();
			request.setAttribute("auth", auth);
			request.setAttribute("cancelSuccess", "ģ��ע���ɹ�");
			request.getRequestDispatcher("auth?method=list&name=" + name)
					.forward(request, response);
		} else {
			Auth auth = authDao.getAuthById(id);
			String name = auth.getName();
			request.setAttribute("cancelerror", "ģ��ע��ʧ��");
			request.getRequestDispatcher("auth?method=list&name=" + name)
					.forward(request, response);
		}
	}

	// �ָ�ģ��
	protected void restore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authId = request.getParameter("authId");
		int id = 0;
		if (authId != null && !"".equals(authId)) {
			id = Integer.parseInt(authId);
		}
		// System.out.println(id);
		boolean f = authDao.restoreAuth(id);
		if (f) {
			/*
			 * HttpSession session = request.getSession(); PageUtils pageUtils =
			 * (PageUtils) session.getAttribute("pageUtils"); Map filter = (Map)
			 * session.getAttribute("filter"); int page =
			 * pageUtils.getCurrPage();
			 */
			Auth auth = authDao.getAuthById(id);
			String name = auth.getName();
			request.setAttribute("auth", auth);
			request.setAttribute("restoreSuccess", "恢复成功");
			request.getRequestDispatcher("auth?method=list&name=" + name)
					.forward(request, response);
		} else {
			Auth auth = authDao.getAuthById(id);
			String name = auth.getName();
			request.setAttribute("restoreerror", "恢复失败");
			request.getRequestDispatcher("auth?method=list&name=" + name)
					.forward(request, response);
		}
	}

	// �༭ģ��
	protected void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String authId = request.getParameter("authId");
		int id = 0;
		if (authId != null && !"".equals(authId)) {
			id = Integer.parseInt(authId);
		}
		// ����id��ȡ��Ȩ�޵�������Ϣ
		Auth auth = authDao.getAuthById(id);
		// ��ѯ���и���Ȩ��
		List<Auth> parentList = authDao.findParentAuth();
		// ��ȡ��ǰҳ��
		/*
		 * HttpSession session = request.getSession(); PageUtils pageUtils =
		 * (PageUtils) session.getAttribute("pageUtils"); int page =
		 * pageUtils.getCurrPage(); request.setAttribute("page", page);
		 */
		request.setAttribute("parentList", parentList);
		request.setAttribute("auth", auth);
		request.getRequestDispatcher("model/modelUpdate.jsp").forward(request,
				response);
	}

	// �޸�ģ��
	protected void update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String parentId = request.getParameter("parentId");
		String authId = request.getParameter("authId");
		int id = 0;
		if (authId != null && !"".equals(authId)) {
			id = Integer.parseInt(authId);
		}
		Auth auth = authDao.getAuthById(id);
		auth.setName(name);
		auth.setUrl(url);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginSysUser");
		auth.setUser(user);
		if ("0".equals(parentId)) {
			auth.setParent(null);
		} else {
			auth.setParent(authDao.getAuthById(Integer.parseInt(parentId)));
		}
		boolean f = authDao.updateAuth(auth);
		if (f) {
			Auth newAuth = authDao.getAuthById(id);
			String name1 = newAuth.getName();
			request.setAttribute("updateSuccess", "修改成功");
			request.getRequestDispatcher("auth?method=list&name=" + name1)
					.forward(request, response);
		} else {
			request.setAttribute("updateerror", "修改失败");
			request.getRequestDispatcher("auth?method=list").forward(request,
					response);
		}
	}
}
