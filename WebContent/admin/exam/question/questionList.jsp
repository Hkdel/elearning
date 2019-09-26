<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
<!DOCTYPE HTML >
<html>
	<head>
		<title>题目管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<form action="admin/exam/sysExam?method=quesList" method="post" id="modelForm" >
	<div class="page_title">题目管理</div>
	<div class="button_bar">
		<input type="button" class="common_button" value="新建" 
			onclick="to('admin/exam/question/questionAdd.jsp');" /> 
		<input type="button" class="common_button" value="查询" onclick="subFrom(1)" />	
	</div>
	<table class="query_form_table">
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
			<th>题型</th>
			<td>
				<select name="typeId">
					<option value="0">所有</option>
					<c:forEach items="${typeList}" var="type">
						<option value="${type.id}" 
							<c:if test="${filter.typeId == type.id}">selected="selected"</c:if> >
							${type.name}
						</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>题目</th>
			<td><input type="text" name="title" value="${filter.title}"></td>
			<th></th>
			<td></td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>科目名称</th>
			<th>题型</th>
			<th>题目</th>
			<th>题目状态</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${quesList}" var="ques" varStatus="i" >
		<tr>
			<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
			<td class="list_data_text">${ques.subject.name}</td>
			<td class="list_data_text">${ques.type.name}</td>
			<td class="list_data_text">${ques.title}</td>
			<td class="list_data_text">
				<c:choose>
					<c:when test="${ques.status eq '0'}">禁用</c:when>
					<c:when test="${ques.status eq '1'}">启用</c:when>
				</c:choose>
			</td>
			<td class="list_data_text">${ques.createUser.name}</td>
			<td class="list_data_text">${ques.createTime}</td>
			<td class="list_data_text">
				<a href="admin/exam/question/questionUpdate.jsp?id=${ques.id}">编辑</a>
				<a href="admin/exam/sysExam?method=quesChange&id=${ques.id}">
					<%-- <input type="hidden" name="changeId" value="${ques.id}" /> --%>
				<c:choose>
					<c:when test="${ques.status eq '0'}">启用</c:when>
					<c:when test="${ques.status eq '1'}">禁用</c:when>
				</c:choose>
				</a>
				<a href="admin/exam/sysExam?method=quesDelete&id=${ques.id}"
					onclick="return confirm('删除后无法恢复！是否确定删除？')" >删除</a>
				<a href="admin/exam/question/questionInfo.jsp?id=${ques.id}">查看详情</a>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="8">
				<div class="pager">
					<div class="pager_left">
						共${pageUtils.totalSize}条记录 每页${pageUtils.pageSize}条
						第${pageUtils.currPage}页/共${pageUtils.totalPage}页
						转到<input type="number" value="${pageUtils.currPage}" min="1" max="${pageUtils.totalPage}" name="page" id="page" size="1" />页
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