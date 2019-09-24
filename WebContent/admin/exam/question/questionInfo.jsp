<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zt.exam.po.Option"%>
<%@page import="java.util.List"%>
<%@page import="com.zt.exam.dao.impl.QuestionDaoImpl"%>
<%@page import="com.zt.exam.dao.QuestionDao"%>
<%@page import="com.zt.exam.po.Question"%>
<%@ include file="../../../tag.jsp"  %> 
<%
	QuestionDao queDao = new QuestionDaoImpl();
	String idStr = request.getParameter("id");
	int id = 0;
	if (idStr != null && !"".equals(idStr)) {
		id = Integer.parseInt(idStr);
	}
	Question qusetion = queDao.getQuestionById(id);
	List<Option> options = queDao.getOptionsByQuestionId(id);
	pageContext.setAttribute("ques", qusetion);
	pageContext.setAttribute("options", options);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看题目</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">题目管理&nbsp; &gt; 查看题目</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>
				<select>
					<option selected>${ques.subject.name}</option>
				</select>
				<span class="red_star">*</span>
			</td>
			<th>题型</th>
			<td>
				<select>
					<option selected>${ques.type.name}</option>
				</select>
				<span class="red_star">*</span>
			</td>
		</tr>
		<tr>
			<th>题目</th>
			<td colspan="3"><input type="text" value="${ques.title}" size="80" readonly /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>答案</th>
			<td colspan="3">
				<textarea rows="6" cols="50" readonly />${ques.answer}</textarea>
				<span class="red_star">*</span>
			</td>
		</tr>
		<c:if test="${fn:length(options) > 1 }">
			<tr class="optionTr_show" id="optionTr">
			<th>选项</th>
			<td colspan="3">
				<ol type="A" id="options">
					<c:forEach items="${options}" var="op" >
						<li><input type="text" value="${op.content}" readonly /></li>
					</c:forEach>
				</ol>
			</td>
			</tr>
		</c:if>
	</table>
</body>
</html>