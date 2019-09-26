<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../tag.jsp" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.*,com.zt.user.po.Role,com.zt.user.dao.RoleDao,com.zt.user.dao.impl.RoleDaoImpl"%>
<%
java.util.Date date = new Date(); //获取当前时间
//out.print(date);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化时间 
String createTime = sdf.format(date);
pageContext.setAttribute("createTime", createTime);

RoleDao roleDao = new RoleDaoImpl();
List<Role> roles = roleDao.findAllRole();
pageContext.setAttribute("roles", roles);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>注册用户</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			function submitForm(){
				//document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
		<c:if test="${not empty regerror}">
		<script type="text/javascript">
			confirm('${regerror}');
		</script>
		</c:if>
	</head>
<body>
	<form action="fontUser?method=fontReg" method="post" id="submitForm" enctype="multipart/form-data">
	<div class="page_title">在线学习系统&nbsp; &gt;注册用户</div>
	<table class="query_form_table">
		<tr>
			<th>姓名</th>
			<td><input type="text" name="userName"  /><span class="red_star">*</span></td>
			<th>账号</th>
			<td><input type="text" name="accountName" /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>密码</th>
			<td><input type="password" name="pass"/><span class="red_star">*</span></td>
			<th>确认密码</th>
			<td><input type="password" name="repass"/><span class="red_star">*</span>
			<span style="color: red">${passerror }</span>
			</td>
		</tr>
		<tr>
			<th>性别</th>
			<td>
				<select name="sex">
					<option value="-1">请选择</option>
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
				<span class="red_star">*</span>
			</td>
			<th>出生日期</th>
			<td><input type="text" name="birthday"/><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>头像</th>
			<td><input type="file" name="photo" /><span class="red_star">*</span></td>
			<th>角色</th>
			<td>
				<input type="text" value="学生" name="roleName" readonly />
				<span class="red_star">*系统默认</span>
			</td>
		</tr>
		<tr>
			<th>论坛积分</th>
			<td><input type="text" value="0" name="bbsScore" readonly /><span class="red_star">*系统默认</span></td>
			<th>考试学分</th>
			<td><input type="text" value="0" name="examScore" readonly /><span class="red_star">*系统默认</span></td>
		</tr>
		<tr>
			<th>创建时间</th>
			<td><input type="text" name="createTime" value="${createTime }" readonly />
				<span class="red_star">*系统默认</span>
			</td>
			
		</tr>
	</table>
	<div class="button_bar">
		<a class="common_button" onclick="back();">返回</a>
		<a class="common_button" onclick="submitForm()">注册</a>
	</div>
	</form>
	
</body>
</html>