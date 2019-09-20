<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zt.exam.po.Type"%>
<%@page import="com.zt.exam.dao.impl.TypeDaoImpl"%>
<%@page import="com.zt.exam.dao.TypeDao"%>
<%@page import="com.zt.exam.po.Subject"%>
<%@page import="java.util.List"%>
<%@page import="com.zt.exam.dao.impl.SubjectDaoImpl"%>
<%@page import="com.zt.exam.dao.SubjectDao"%>
<%@ include file="../../../tag.jsp"  %>
<%
	TypeDao typeDao = new TypeDaoImpl();
	SubjectDao subDao = new SubjectDaoImpl();
	List<Type> typeList = typeDao.findAll();
	List<Subject> subList = subDao.findAll();
	pageContext.setAttribute("typeList", typeList);
	pageContext.setAttribute("subList", subList);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加试卷</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">试卷管理&nbsp; &gt; 添加试卷</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="submit();">保存</button>
	</div>
	<form action="admin/exam/sysExam?method=paperAdd" method="post" id="Form" >
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>
				<select name="subId">
					<option value="" style="display:none;" disabled selected>请选择</option>
					<c:forEach items="${subList}" var="sub">
						<option value="${sub.id}">${sub.name}</option>
					</c:forEach>
				</select>
				<span class="red_star">*</span>
			</td>
			<th>试卷名称</th>
			<td>
				<input name="paperName" type="text"><span class="red_star">*</span>
			</td>
		</tr>
		<c:forEach items="${typeList}" var="type">
			<tr>
				<th>${type.name}</th>
				<input type="hidden" name="typeId" value="${type.id}" />
				<td colspan="3">
					数量：<input name="nums" type="text" />
					分值：<input name="rdScore" type="text" />
				</td>
			</tr>
		</c:forEach>
		<tr>
			<th>考试总时长</th>
			<td><input name="time" type="text" />分钟<span class="red_star">*</span></td>
			<th>考试总分</th>
			<td><input name="score" type="text" /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>学分</th>
			<td colspan="3">
				<input name="credit" type="text" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>