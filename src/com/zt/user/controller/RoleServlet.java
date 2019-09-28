package com.zt.user.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.zt.user.dao.AuthDao;
import com.zt.user.dao.RoleAuthDao;
import com.zt.user.dao.RoleDao;
import com.zt.user.dao.impl.AuthDaoImpl;
import com.zt.user.dao.impl.RoleAuthDaoImpl;
import com.zt.user.dao.impl.RoleDaoImpl;
import com.zt.user.po.Auth;
import com.zt.user.po.Role;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

/**
 * Servlet implementation class RoleServlet
 */
@WebServlet("/admin/system/role")
public class RoleServlet extends HttpServlet {
	private RoleDao roleDao;
	private AuthDao authDao;
	private RoleAuthDao roleAuthDao;

	public void init(ServletConfig config) throws ServletException {
		roleDao = new RoleDaoImpl();
		authDao = new AuthDaoImpl();
		roleAuthDao = new RoleAuthDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("list".equals(method)) {
			list(request, response);
		}
		if ("grant".equals(method)) {
			grant(request, response);
		}
		if ("saveRoleAuth".equals(method)) {
			saveRoleAuth(request, response);
		}
		if("edit".equals(method)){
			edit(request,response);
		}
		if("update".equals(method)){
			update(request,response);
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
	}

	// ����
	protected void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map	filter = new HashMap();
		String name = request.getParameter("name");
		System.out.println(name);
		String status = request.getParameter("status");
		if (name != null && !"".equals(name)) {
			filter.put("name", name);
		}
		if (status != null && !"-1".equals(status)) {
			filter.put("status", status);
		}
		int totalSize = roleDao.getTotalSizeByFilter(filter);
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
		List<Role> roles = roleDao.findAllRole(filter, pageUtils);
		HttpSession session = request.getSession();
		session.setAttribute("pageUtils", pageUtils);
		session.setAttribute("filter", filter);
		request.setAttribute("roles", roles);
		request.getRequestDispatcher("role/roleList.jsp").forward(request,
				response);
	}

	// ��Ȩ
	protected void grant(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String roleId = request.getParameter("roleId");
		int id = 0;
		if (roleId != null && !"".equals(roleId)) {
			id = Integer.parseInt(roleId);
		}
		Role role = roleDao.getRoleById(id);
		List<Auth> authList = authDao.findAll();// ��ѯ������ ����û�и����Ĺ���
		Set<Auth> parentList = new HashSet<Auth>();// set����ȥ��
		for (Auth a : authList) {
			if (a.getParent().getId() == 0) {// ���==0���Ǹ�������
				parentList.add(a);
			}
		}
		request.setAttribute("parentList", parentList);
		request.setAttribute("authList", authList);
		request.setAttribute("role", role);
		// ��ѯ��ɫ��ǰ�Ѿ���Ȩ�˵�Ȩ���б�
		List<Auth> roleAuthList = roleDao.findRoleAuth(id);
		//System.out.println(roleAuthList);
		request.setAttribute("roleAuthList", roleAuthList);
		request.getRequestDispatcher("role/roleGrant.jsp").forward(request,
				response);
	}

	// ����Ȩ���޸Ĳ���
	protected void saveRoleAuth(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String roleId = request.getParameter("roleId");
		String authIds[] = request.getParameterValues("authId");
		int id = 0;
		if (roleId != null) {
			id = Integer.parseInt(roleId);
		}
		if(authIds == null){
			request.setAttribute("granterror", "赋权失败");
			request.getRequestDispatcher("role?method=grant&roleId="+id).forward(request, response);
		}else{
			int aids[] = new int[authIds.length];
			for (int i = 0; i < authIds.length; i++) {
				aids[i] = Integer.parseInt(authIds[i]);
			}
			// ����id��ɾ�����ڵ�ģ���ٽ�����ӱ�֤���ݲ�����
			boolean f = roleAuthDao.delRoleAuth(id);
			if (f) {
				boolean f1 = roleAuthDao.addRoleAuth(id, aids);
				if (f1) {
					HttpSession session = request.getSession();
					PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
					int page = pageUtils.getCurrPage();
					Role role = roleDao.getRoleById(id);
					String name = role.getName();
					request.setAttribute("role", role);
					request.setAttribute("grantSuccess", "赋权成功");
					request.getRequestDispatcher("role?method=list&name="+name).forward(request, response);
				} else {
					response.sendRedirect("error.jsp");
				}
			} else {
				response.sendRedirect("error.jsp");
			}
		}
		
	}

	// ע����ɫ
	protected void cancel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String roleId = request.getParameter("roleId");
		int id = 0;
		if (roleId != null && !"".equals(roleId)) {
			id = Integer.parseInt(roleId);
		}
		boolean f = roleDao.cancelRole(id);
		if (f) {
			HttpSession session = request.getSession();
			PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
			int page = pageUtils.getCurrPage();
			Role role = roleDao.getRoleById(id);
			String name = role.getName();
			request.setAttribute("role", role);
			request.setAttribute("cancelSuccess", "注销成功");
			request.getRequestDispatcher("role?method=list&name="+name).forward(request,
					response);
		} else {
			HttpSession session = request.getSession();
			PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
			int page = pageUtils.getCurrPage();
			Role role = roleDao.getRoleById(id);
			String name = role.getName();
			request.setAttribute("cancelerror", "注销失败");
			request.getRequestDispatcher("role?method=list&name="+name).forward(request,
					response);
		}
	}

	// �ָ���ɫ
	protected void restore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String roleId = request.getParameter("roleId");
		int id = 0;
		if (roleId != null && !"".equals(roleId)) {
			id = Integer.parseInt(roleId);
		}
		boolean f = roleDao.restoreRole(id);
		if (f) {
			HttpSession session = request.getSession();
			PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
			int page = pageUtils.getCurrPage();
			Role role = roleDao.getRoleById(id);
			String name = role.getName();
			request.setAttribute("role", role);
			request.setAttribute("restoreSuccess", "恢复成功");
			request.getRequestDispatcher("role?method=list&name="+name).forward(request,
					response);
		} else {
			HttpSession session = request.getSession();
			PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
			int page = pageUtils.getCurrPage();
			Role role = roleDao.getRoleById(id);
			String name = role.getName();
			request.setAttribute("restoreerror", "恢复失败");
			request.getRequestDispatcher("role?method=list&name="+name).forward(request,
					response);
		}
	}
	//�༭��ɫ
	protected void edit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String roleId = request.getParameter("roleId");
		int id = 0;
		if (roleId != null && !"".equals(roleId)) {
			id = Integer.parseInt(roleId);
		}
		//����id��ȡ��ɫ��������Ϣ
		Role role = roleDao.getRoleById(id);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginSysUser");
		role.setUser(user);
		request.setAttribute("role", role);
		request.getRequestDispatcher("role/roleUpdate.jsp").forward(request, response);
	}
	//�޸Ľ�ɫ
	protected void update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String roleName = request.getParameter("roleName");
		String roleId = request.getParameter("roleId");
		int id = 0;
		if (roleId != null && !"".equals(roleId)) {
			id = Integer.parseInt(roleId);
		}
		Role role = roleDao.getRoleById(id);
		role.setName(roleName);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginSysUser");
		role.setUser(user);
		boolean f = roleDao.updateRole(role);
		if (f) {
			PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
			int page = pageUtils.getCurrPage();
			request.setAttribute("updateSuccess", "修改成功");
			//request.removeAttribute("name");
			Role newRole = roleDao.getRoleById(id);
			String name = newRole.getName();
			request.getRequestDispatcher("role?method=list&name="+name).forward(request,
					response);
		} else {
			PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
			int page = pageUtils.getCurrPage();
			request.setAttribute("updateerror", "修改失败");
			request.getRequestDispatcher("role?method=list&page="+page).forward(request,
					response);
		}
	}
	//�½���ɫ
	protected void add(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String roleName = request.getParameter("roleName");
		if(roleName == null || "".equals(roleName)){
			request.setAttribute("error", "添加失败");
			request.getRequestDispatcher("role/roleAdd.jsp").forward(request, response);
		}else{
			String createName = request.getParameter("createName");
			String nowTime = request.getParameter("nowTime");
			Role role = new Role();
			role.setName(roleName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date createTime = null;
			try {
				createTime = sdf.parse(nowTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			role.setCreateTime(createTime);
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginSysUser");
			role.setUser(user);
			boolean f = roleDao.addRole(role);
			if (f) {
				PageUtils pageUtils = (PageUtils) session.getAttribute("pageUtils");
				Map filter = new HashMap();
				String name = request.getParameter("name");
				String status = request.getParameter("status");
				if (name != null && !"".equals(name)) {
					filter.put("name", name);
				}
				if (status != null && !"-1".equals(status)) {
					filter.put("status", status);
				}
				int totalSize = roleDao.getTotalSizeByFilter(filter);
				pageUtils.setTotalSize(totalSize);
				pageUtils.setTotalPage(totalSize);
				int page = pageUtils.getTotalPage();
				request.setAttribute("addSuccess", "添加成功");
				request.getRequestDispatcher("role?method=list&page="+page).forward(request,
						response);
			} else {
				request.setAttribute("adderror", "添加失败");
				request.getRequestDispatcher("role?method=list").forward(request,
						response);
			}
		}
		
	}
}
