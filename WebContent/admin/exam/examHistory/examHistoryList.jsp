<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>考试记录管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<form action="admin/exam/sysExam?method=recordList" method="post" id="modelForm" >
	<div class="page_title">考试记录管理</div>
	<div class="button_bar">
		<input type="button" class="common_button" value="查询" onclick="subFrom(1)" />	
	</div>
	<table class="query_form_table">
		<tr>
			<th>学生姓名</th>
			<td>
				<input name="stuName" value="${filter.stuName}" type="text">
			</td>
			<th>试卷名称</th>
			<td><input name="paperName" value="${filter.paperName}" type="text"></td>
		</tr>
		<tr>
			<th>科目名称</th>
			<td>
				<select name="subId">
					<option value="0">所有</option>
					<c:forEach items="${subList}" var="sub">
						<option value="${sub.id}" 
							<c:if test="${filter.subId == sub.id}">selected="selected"</c:if> >
							${sub.name}
						</option>
					</c:forEach>
				</select>
			</td>
			<th>考试时间</th>
			<td>
				<input type="date" name="begin" value="${filter.begin}" >
				-<input type="date" name="end" value="${filter.end}" ></td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>学生姓名</th>
			<th>科目名称</th>
			<th>试卷名称</th>
			<th>客观题分数</th>
			<th>主观题分数</th>
			<th>总分数</th>
			<th>获得学分</th>
			<th>考试时间</th>
		</tr>
		<c:forEach items="${recordList}" var="record" varStatus="i" >
		<tr>
			<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
			<td class="list_data_text">${record.user.name}</td>
			<td class="list_data_text">${record.rule.subject.name}</td>
			<td class="list_data_text">${record.rule.name}</td>
			<td class="list_data_text">${record.subjective}</td>
			<td class="list_data_text">${record.objective}</td>
			<td class="list_data_text">${record.score}</td>
			<td class="list_data_text">${record.credit}</td>
			<td class="list_data_text">${record.startTime}至${record.endTime}</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="10">
				<div class="pager">
					<div class="pager_left">
						共${pageUtils.totalSize}条记录 每页${pageUtils.pageSize}条
						第${pageUtils.currPage}页/共${pageUtils.totalPage}页
						转到<input type="number" min="1" max="${pageUtils.totalPage}" value="${pageUtils.currPage}" name="page" id="page" size="1" />页
						<button width="20" onclick="subFrom(0)">GO</button>
					</div>
					<div class="pager_right">
						<button class="common_button" onclick="subFrom(1)">首页</button>
						<c:if test="${pageUtils.currPage > 1}">
							<button class="common_button" onclick="subFrom(${pageUtils.currPage-1})">上一页</button>
						</c:if>
						<c:if test="${pageUtils.currPage < pageUtils.totalPage}">
							<button class="common_button" onclick="subFrom(${pageUtils.currPage+1})">下一页</button>
						</c:if>
						<button class="common_button" onclick="subFrom(${pageUtils.totalPage})">尾页</button>
					</div>
				</div>
			</th>
		</tr>
	</table>
</body>
</html>