<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tag.jsp"  %>   
<html>
<head>
<title>在线学习管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	body{
		border-bottom:solid 1px #666;
	}
	a{
		text-decoration: none;
		color: #000000;
	}
</style>
</head>

<body>

<table style="width:100%;">
<tr >
	<td ><img src="images/logo.gif" height="60px"></td>
	<td style="font-family:黑体;font-size:33px;font-weight:bold;">在线学习管理系统</td>	
	<td width="25%" align="right" style="font-size:12px;" valign="bottom">当前用户：<a href="userInfo.html" target="rightFrame">${loginSysUser.name }（${loginSysUser.role.name }）</a>&nbsp;&nbsp; <a href="admin/system/loginUserRePass.jsp?userId=${loginSysUser.id}" target="_parent">修改密码</a>&nbsp;&nbsp;&nbsp;<a href="admin/userLogin?method=out" target="_top">退出系统</a> </td>
</tr>
</table>
</body>
</html>