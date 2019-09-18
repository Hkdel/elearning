<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zt.exam.dao.impl.SubjectDaoImpl"%>
<%@page import="com.zt.exam.dao.SubjectDao"%>
<%@page import="com.zt.exam.po.Subject"%>
<%@ include file="../../../tag.jsp"  %>  
<%
	String idStr = request.getParameter("id");
	int id = 0;
	if (idStr != null && !"".equals(idStr)) {
		id = Integer.parseInt(idStr);
	}
	SubjectDao subDao = new SubjectDaoImpl();
	Subject sub = subDao.getSubjectById(id);
	pageContext.setAttribute("sub", sub);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑科目</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">科目管理&nbsp; &gt; 编辑科目</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="submit();">保存</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<form action="admin/exam/sysExam?method=subUpdate" method="post" id="Form" >
			<input type="hidden" name="id" value="${sub.id}" />
			<td><input type="text" name="subName" value="${sub.name}" /><span class="red_star">*</span></td>
			<th>创建人</th>
			<td><input type="text" value="${loginUser.name}" readonly /></td>
			</form>
		</tr>
	</table>
</body>