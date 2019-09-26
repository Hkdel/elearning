<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat,java.util.*"%>
<%@include file="../../../tag.jsp"%>
<%
	java.util.Date date = new Date(); //获取当前时间
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化时间 
	String createTime = sdf.format(date);
	pageContext.setAttribute("createTime", createTime);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加首页图片</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	function submitForm() {
		document.getElementById("submitForm").submit();
	}
</script>
</head>
<body>
	<form action="admin/system/img?method=add" method="post"
		id="submitForm" enctype="multipart/form-data">
		<div class="page_title">首页图片管理&nbsp; &gt; 添加首页图片</div>
		<div class="button_bar">
			<a class="common_button" onclick="back();">返回</a> <a
				class="common_button" onclick="submitForm()">保存</a>
		</div>
		<table class="query_form_table">
			<tr>
				<th>图片</th>
				<td><input type="file" name="url" /><span class="red_star">*</span></td>
				<th>位置</th>
				<td><input type="text" name="place" /><span class="red_star">*</span></td>
			</tr>
			<tr>
				<th>创建人</th>
				<td><input type="text" name="createName"
					value="${loginSysUser.name  }" readonly /></td>
				<th>创建时间</th>
				<td><input type="text" name="createTime" value="${createTime }"
					readonly /></td>
			</tr>
		</table>
	</form>
</body>
</html>