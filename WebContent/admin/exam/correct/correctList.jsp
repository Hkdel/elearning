<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>试卷批改</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<form action="admin/exam/sysExam?method=correctList" method="post" id="modelForm" >
	<div class="page_title">试卷批改</div>
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
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>学生姓名</th>
			<th>科目名称</th>
			<th>试卷名称</th>
			<th>考试时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${recordList}" var="record" varStatus="i" >
		<tr>
			<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
			<td class="list_data_text">${record.user.name}</td>
			<td class="list_data_text">${record.rule.subject.name}</td>
			<td class="list_data_text">${record.rule.name}</td>
			<td class="list_data_text">${record.startTime}</td>
			<td class="list_data_text">
				<a href="admin/exam/correct/correct.jsp?id=${record.id}">批改</a>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="8">
				<div class="pager">
					<div class="pager_left">
						共${pageUtils.totalSize}条记录 每页${pageUtils.pageSize}条
						第${pageUtils.currPage}页/共${pageUtils.totalPage}页
						转到<input type="number" min="1" max="${pageUtils.totalPage}" value="${pageUtils.currPage}" name="page" id="page" size="1" />页
						<input width="20" type="submit" value="GO" />
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
	</form>
</body>
</html>