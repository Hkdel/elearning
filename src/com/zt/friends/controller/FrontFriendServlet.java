package com.zt.friends.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zt.friends.dao.ChatMsgDao;
import com.zt.friends.dao.FriendsDao;
import com.zt.friends.dao.impl.ChatMsgDaoImpl;
import com.zt.friends.dao.impl.FriendsDaoImpl;
import com.zt.friends.po.ChatMsg;
import com.zt.friends.po.Friends;
import com.zt.user.po.User;
import com.zt.utils.PageUtils;

@WebServlet("/friend/frontFriend")
public class FrontFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FriendsDao friendDao;
	private ChatMsgDao chatDao;

	public void init(ServletConfig config) throws ServletException {
		friendDao = new FriendsDaoImpl();
		chatDao = new ChatMsgDaoImpl();
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("addFriend".equals(method)) {
			addFriend(request, response);
		}
		if ("notFriendList".equals(method)) {
			notFriendList(request, response);
		}
		if ("friendApplyList".equals(method)) {
			friendApplyList(request, response);
		}
		if ("applyAccept".equals(method)) {
			applyAccept(request, response);
		}
		if ("applyRefuse".equals(method)) {
			applyRefuse(request, response);
		}
		if ("myFriendList".equals(method)) {
			myFriendList(request, response);
		}
		if ("findMsg".equals(method)) {
			findMsg(request, response);
		}
		if ("sendContent".equals(method)) {
			sendContent(request, response);
		}
		if ("addBlack".equals(method)) {
			addBlack(request, response);
		}
		if ("delFriend".equals(method)) {
			delFriend(request, response);
		}
		if ("blackList".equals(method)) {
			blackList(request, response);
		}
		if ("delBlack".equals(method)) {
			delBlack(request, response);
		}
		if ("unReadList".equals(method)) {
			unReadList(request, response);
		}
		if ("temp".equals(method)) {
			temp(request, response);
		}
		if ("findMsg2".equals(method)) {
			findMsg2(request, response);
		}
		if ("ignoreMsg".equals(method)) {
			ignoreMsg(request, response);
		}
	}

	protected void findMsg2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("toId");
		int toId = 0;
		if (idStr != null && !"".equals(idStr)) {
			toId = Integer.parseInt(idStr);
		}
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int loginId = loginUser.getId();
		List<ChatMsg> list = chatDao.findMsg(loginId, toId);
		request.setAttribute("toId", toId);
		request.setAttribute("chatMsgList", list);
		request.getRequestDispatcher("chat2.jsp").forward(request, response);
	}

	protected void ignoreMsg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int loginId = loginUser.getId();
		String idStr = request.getParameter("toId");
		int toId = 0;
		if (idStr != null && !"".equals(idStr)) {
			toId = Integer.parseInt(idStr);
		}
		boolean f = chatDao.ignoreMsg(loginId, toId);
		if (f) {
			response.sendRedirect("frontFriend?method=unReadList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void temp(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			response.sendRedirect("../login/fontLogin.jsp");
		} else {
			response.sendRedirect("../index/menu4.jsp");
		}
	}

	protected void addFriend(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String withMsg = request.getParameter("withMsg");
		String idStr = request.getParameter("friendId");
		int friendId = 0;
		if (idStr != null) {
			friendId = Integer.parseInt(idStr);
		}
		// System.out.println(friendId);
		User friendUser = new User();
		friendUser.setId(friendId);
		Friends friend = new Friends();
		friend.setFriendUser(friendUser);

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		friend.setLoginUser(loginUser);

		friend.setWithMsg(withMsg);
		boolean f = friendDao.addFriend(friend);
		if (f) {
			response.sendRedirect("frontFriend?method=notFriendList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void notFriendList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int totalSize = friendDao.getTotalSize(loginUser);
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
		List<User> notFriendList = friendDao.searchFriendByPage(pageUtils,
				loginUser);
		request.setAttribute("notFriendList", notFriendList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("friend4.jsp").forward(request, response);
	}

	protected void friendApplyList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int totalSize = friendDao.getApplySize(loginUser);
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
		List<Friends> applyList = friendDao.searchUserByPage(pageUtils,
				loginUser);
		request.setAttribute("applyList", applyList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("friend1.jsp").forward(request, response);
	}

	protected void applyAccept(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("fromId");
		int fromId = 0;
		if (idStr != null) {
			fromId = Integer.parseInt(idStr);
		}
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		boolean f = friendDao.applyAccept(fromId, loginUser);
		if (f) {
			response.sendRedirect("frontFriend?method=friendApplyList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void applyRefuse(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("fromId");
		int fromId = 0;
		if (idStr != null) {
			fromId = Integer.parseInt(idStr);
		}
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		boolean f = friendDao.applyRefuse(fromId, loginUser);
		if (f) {
			response.sendRedirect("frontFriend?method=friendApplyList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void myFriendList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int totalSize = friendDao.getMyFriendSize(loginUser);
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
		List<Friends> myFriendList = friendDao.searchMyFriendByPage(pageUtils,
				loginUser);
		request.setAttribute("myFriendList", myFriendList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("friend2.jsp").forward(request, response);
	}

	protected void findMsg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String idStr = request.getParameter("toId");
		int toId = 0;
		if (idStr != null && !"".equals(idStr)) {
			toId = Integer.parseInt(idStr);
		}
		// System.out.println(toId);

		HttpSession session = request.getSession();

		User loginUser = (User) session.getAttribute("loginUser");
		int loginId = loginUser.getId();
		// System.out.println(loginId);
		List<ChatMsg> list = chatDao.findMsg(loginId, toId);
		request.setAttribute("toId", toId);
		request.setAttribute("chatMsgList", list);
		request.getRequestDispatcher("chat.jsp").forward(request, response);
	}

	protected void sendContent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("toId");
		int toId = 0;
		if (idStr != null && !"".equals(idStr)) {
			toId = Integer.parseInt(idStr);
		} else {
			toId = (int) request.getAttribute("toId");
		}
		String content = request.getParameter("content");

		HttpSession session = request.getSession();
		User fromUser = (User) session.getAttribute("loginUser");

		User toUser = new User();
		toUser.setId(toId);

		ChatMsg chatMsg = new ChatMsg();
		chatMsg.setContent(content);
		chatMsg.setFromUser(fromUser);
		chatMsg.setToUser(toUser);
		boolean f = chatDao.sendMsg(chatMsg);
		if (f) {
			request.setAttribute("toId", toId);
			// response.sendRedirect("frontFriend?method=findMsg");
			request.getRequestDispatcher("frontFriend?method=findMsg").forward(
					request, response);
		} else {
			response.sendRedirect("error.jsp");
		}

	}

	protected void addBlack(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("toId");
		int toId = 0;
		if (idStr != null && !"".equals(idStr)) {
			toId = Integer.parseInt(idStr);
		}
		// System.out.println(toId);
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int loginId = loginUser.getId();
		// System.out.println(loginId);
		boolean f = friendDao.addBlack(loginId, toId);
		if (f) {
			response.sendRedirect("frontFriend?method=myFriendList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void delFriend(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("toId");
		int toId = 0;
		if (idStr != null && !"".equals(idStr)) {
			toId = Integer.parseInt(idStr);
		}
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int loginId = loginUser.getId();
		boolean f = friendDao.delFriend(loginId, toId);
		if (f) {
			response.sendRedirect("frontFriend?method=myFriendList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void blackList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int totalSize = friendDao.getMyFriendSize(loginUser);
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
		List<Friends> blackList = friendDao.searchBlackByPage(pageUtils,
				loginUser);
		request.setAttribute("blackList", blackList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("friend3.jsp").forward(request, response);
	}

	protected void delBlack(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("friendId");
		int friendId = 0;
		if (idStr != null && !"".equals(idStr)) {
			friendId = Integer.parseInt(idStr);
		}
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int loginId = loginUser.getId();
		boolean f = friendDao.delBlack(loginId, friendId);
		if (f) {
			response.sendRedirect("frontFriend?method=blackList");
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	protected void unReadList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		int totalSize = chatDao.getToMyTotalSize(loginUser);
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
		List<ChatMsg> unReadList = chatDao.findToMyUserByPage(pageUtils,
				loginUser);
		request.setAttribute("unReadList", unReadList);
		request.setAttribute("pageUtils", pageUtils);
		request.getRequestDispatcher("friend0.jsp").forward(request, response);
	}

}
