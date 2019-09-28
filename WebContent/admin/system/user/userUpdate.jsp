<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp" %>
<%@ page import="java.util.*,com.zt.user.po.Role,com.zt.user.dao.RoleDao,com.zt.user.dao.impl.RoleDaoImpl"%>
<c:if test="${empty loginSysUser}">
	<%response.sendRedirect("../login.jsp");
	//request.getRequestDispatcher("../../login.jsp").forward(request, response);
	%>
</c:if>
<% 
RoleDao roleDao = new RoleDaoImpl();
List<Role> roles = roleDao.findAllRole();
pageContext.setAttribute("roles", roles);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑用户</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			function submitForm(){
				//document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
	</head>
<body>
	<form action="admin/system/user?method=update" method="post" id="submitForm" enctype="multipart/form-data">
	<div class="page_title">用户管理&nbsp; &gt; 编辑用户</div>
	<div class="button_bar">
		<a class="common_button" onclick="back();">返回</a>
		<a class="common_button" onclick="submitForm()">保存</a>
	</div>
	<table class="query_form_table">
		<tr>
			<th>姓名</th>
			<td><input type="text" name="userName" value="${user.name }" /><span class="red_star">*</span>
				<input type="hidden" name="userId" value="${user.id }">
			</td>
			<th>账号</th>
			<td><input type="text" name="accountName" value="${user.accountName }"  /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>性别</th>
			<td>
				<select name="sex" value="${user.sex }">
					<option value="-1">请选择</option><!-- 数据库sex char6个字符串 -->
					<option <c:if test="${user.sex =='男    ' }">selected="selected"</c:if>>男</option>
					<option <c:if test="${user.sex =='女    ' }">selected="selected"</c:if>>女</option>
				</select> 
				<span class="red_star">*</span>
			</td>
			<th>出生日期</th>
			<td><input type="text" name="birthday" value="${user.birthday }" /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>头像</th>
			<td><input type="file" name="newPhoto" /><span class="red_star">*<br></span>
				<input type="text" name="photo" value="${user.photo }" size="40px">
				<img alt="error" src="${user.photo }" style="width: 50px;height: 50px">
			</td>
			<th>角色</th>
			<td>
				<select name="roleId">
					<option value="0">请选择</option>
					<c:forEach items="${roles }" var="r">
					<c:if test="${r.status.equals('1') }">
					<option value="${r.id }" <c:if test="${r.id == user.role.id }">selected="selected"</c:if>>${r.name }</option>
					</c:if>
					</c:forEach>
				</select>
				<span class="red_star">*</span>
			</td>
		</tr>
		<tr>
			<th>论坛积分</th>
			<td><input type="text" name="bbsScore" value="${user.bbsScore }" readonly /><span class="red_star">*</span></td>
			<th>考试学分</th>
			<td><input type="text" name="examScore" value="${user.examScore }" readonly /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>创建时间</th>
			<td><input type="text" name="createTime" value="${user.createTime }" readonly /></td>
			<th>创建人</th>
			<td><input type="text" name="createName" value="${loginSysUser.name }" readonly /></td>
		</tr>
	</table>
	</form>
</body>
</html>