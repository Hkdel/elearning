<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp" %>
<%@ page import="com.zt.user.po.User,com.zt.user.dao.UserDao,com.zt.user.dao.impl.UserDaoImpl" %>
<c:if test="${empty loginSysUser}">
	<%response.sendRedirect("../login.jsp");
	//request.getRequestDispatcher("../../login.jsp").forward(request, response);
	%>
</c:if>
<%
	UserDao userDao = new UserDaoImpl();
	String userId = request.getParameter("userId");
	int id = 0;
	if(userId != null && !"".equals(userId)){
		id = Integer.parseInt(userId);
	}
	User user = userDao.findUserById(id);
	pageContext.setAttribute("user", user);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>登录用户修改密码</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			function submitForm(){
				//document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
		<c:if test="${not empty reseterror}">
		<script type="text/javascript">
		alert('${reseterror}');
		</script>
		</c:if>
		
	</head>
<body>
	<form action="admin/system/user?method=resetPass" method="post" id="submitForm">
	<div class="page_title">用户管理&nbsp; &gt; 重置密码</div>
	<div class="button_bar">
		<a class="common_button" onclick="back();">返回</a>
		<a class="common_button" onclick="submitForm()">保存</a>
	</div>
	<table class="query_form_table">
		<tr>
			<th>姓名</th>
			<td><input type="text" name="name" value="${user.name }" readonly="readonly"/><span class="red_star">*</span>
				<input type="hidden" name="userId" value="${user.id }">
			</td>
			<th>账号</th>
			<td><input type="text" name="accountName" value="${user.accountName }" readonly="readonly" /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>新密码</th>
			<td><input type="password" name="newPass"/><span class="red_star">*</span></td>
			<th>确认密码</th>
			<td><input type="password"  name="reNewPass"/><span class="red_star">*</span>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>