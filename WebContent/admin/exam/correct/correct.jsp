<%@page import="com.zt.exam.po.RecordDetail"%>
<%@page import="java.util.List"%>
<%@page import="com.zt.exam.po.Record"%>
<%@page import="com.zt.exam.dao.impl.RecordDaoImpl"%>
<%@page import="com.zt.exam.dao.RecordDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>
<%
	String idStr = request.getParameter("id");
	int id = 0;
	if (idStr != null && !"".equals(idStr)) {
		id = Integer.parseInt(idStr);
	}	
	RecordDao recordDao = new RecordDaoImpl();
	Record record = recordDao.getRecordById(id);
	int subjective = recordDao.getSubjective(id);
	List<RecordDetail> rds = recordDao.findCorrect(id);
	pageContext.setAttribute("record", record);
	pageContext.setAttribute("rds", rds);
	pageContext.setAttribute("subjective", subjective);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>试卷批改</title>
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
	<form action="admin/exam/sysExam?method=correctUpdate" method="post" id="Form" >
	<input type="hidden" name="recordId" value="${record.id}" />
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>${record.rule.subject.name}</td>
			<th>试卷名称</th>
			<td>${record.rule.name}</td>
		</tr>
		<tr>
			<th>学生姓名</th>
			<td>${record.user.name}</td>
			<th>客观题总分值</th>
			<td>${subjective}分</td>
		</tr>
		<c:forEach items="${rds}" var="rd" >
			<tr>
			<th>题目</th>
			<td colspan="3">${rd.question.title}</td>
		</tr>
		<tr>
			<th>学员答案</th>
			<td colspan="3">
				${rd.answer}
			</td>
		</tr>
		<tr>
			<th>题目答案</th>
			<td colspan="3">
				${rd.questionAnswer}
			</td>
		</tr>
		<tr>
			<th>实际得分</th>
			<input type="hidden" name="id" value="${rd.id}" />
			<td ><input name="score" type="text"><span class="red_star">*</span></td>
			<th>当前题目分值</th>
			<td>${rd.score}分</td>
		</tr>
		</c:forEach>
	</table>
	</form>
</body>
</html>