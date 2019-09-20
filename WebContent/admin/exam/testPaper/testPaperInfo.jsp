<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.zt.exam.po.RuleDetail"%>
<%@page import="com.zt.exam.dao.impl.RuleDaoImpl"%>
<%@page import="com.zt.exam.dao.RuleDao"%>
<%@page import="com.zt.exam.po.Rule"%>
<%@ include file="../../../tag.jsp"  %> 
<%
	RuleDao ruleDao = new RuleDaoImpl();
	String idStr = request.getParameter("id");
	int id = 0;
	if (idStr != null && !"".equals(idStr)) {
		id = Integer.parseInt(idStr);
	}
	Rule rule = ruleDao.getRuleById(id);
	List<RuleDetail> rds = ruleDao.getRuleDetailsByRuleId(id);
	pageContext.setAttribute("rule", rule);
	pageContext.setAttribute("rds", rds);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看试卷</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">试卷管理&nbsp; &gt; 查看试卷</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>
				<select>
					<option selected>${rule.subject.name}</option>
				</select>
				<span class="red_star">*</span>
			</td>
			<th>试卷名称</th>
			<td>
				<input type="text" value="${rule.name}" readonly /><span class="red_star">*</span>
			</td>
		</tr>
		<c:forEach items="${rds}" var="rd" >
			<tr>
				<th>${rd.type.name}</th>
				<td colspan="3">
					数量：<input type="text" value="${rd.nums}" readonly />
					分值：<input type="text" value="${rd.score}" readonly />
				</td>
			</tr>
		</c:forEach>
		<tr>
			<th>考试总时长</th>
			<td><input type="text" value="${rule.time}" readonly />分钟<span class="red_star">*</span></td>
			<th>考试总分</th>
			<td><input type="text" value="${rule.score}" readonly /><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>学分</th>
			<td colspan="3">
				<input type="text" value="${rule.credit}" readonly />
			</td>
		</tr>
	</table>
</body>
</html>