<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zt.exam.dao.impl.TypeDaoImpl"%>
<%@page import="com.zt.exam.dao.TypeDao"%>
<%@page import="com.zt.exam.po.Type"%>
<%@page import="java.util.List"%>
<%@page import="com.zt.exam.po.RuleDetail"%>
<%@page import="com.zt.exam.dao.impl.RuleDaoImpl"%>
<%@page import="com.zt.exam.dao.RuleDao"%>
<%@page import="com.zt.exam.po.Rule"%>
<%@ include file="../../../tag.jsp"  %> 
<%
	RuleDao ruleDao = new RuleDaoImpl();
	TypeDao typeDao = new TypeDaoImpl();
	String idStr = request.getParameter("id");
	int id = 0;
	if (idStr != null && !"".equals(idStr)) {
		id = Integer.parseInt(idStr);
	}
	List<Type> typeList = typeDao.findAll();
	Rule rule = ruleDao.getRuleById(id);
	List<RuleDetail> rds = ruleDao.getRuleDetailsByRuleId(id);
	pageContext.setAttribute("rule", rule);
	pageContext.setAttribute("typeList", typeList);
	pageContext.setAttribute("rds", rds);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑试卷</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">试卷管理&nbsp; &gt; 编辑试卷</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="submit();">保存</button>
	</div>
	<form action="admin/exam/sysExam?method=paperUpdate" method="post" id="Form" >
		<input type="hidden" name="id" value="${rule.id}" />
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
				<input type="text" value="${rule.name}"><span class="red_star">*</span>
			</td>
		</tr>
		<c:forEach items="${typeList}" var="type" >
			<tr>
				<th>${type.name}</th>
				<input type="hidden" name="typeId" value="${type.id}" />
				<td colspan="3">
					数量：<input type="text" name="nums" 
						<c:forEach items="${rds}" var="rd" >
							<c:if test="${type.id == rd.type.id}">value="${rd.nums}"</c:if>
						</c:forEach> />
					分值：<input type="text" name="rdScore" 
						<c:forEach items="${rds}" var="rd" >
							<c:if test="${type.id == rd.type.id}">value="${rd.score}"</c:if>
						</c:forEach> />
				</td>
			</tr>
		</c:forEach>
		<tr>
			<th>考试总时长</th>
			<td><input type="text" name="time" value="${rule.time}">分钟<span class="red_star">*</span></td>
			<th>考试总分</th>
			<td><input type="text" name="score" value="${rule.score}"><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>学分</th>
			<td colspan="3">
				<input type="text" name="credit" value="${rule.credit}">
			</td>
		</tr>
	</table>
	</form>
</body>
</html>