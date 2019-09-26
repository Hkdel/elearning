<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>试卷管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<form action="admin/exam/sysExam?method=paperList" method="post" id="modelForm" >
	<div class="page_title">试卷管理</div>
	<div class="button_bar">
		<input type="button" class="common_button" value="新建" 
			onclick="to('admin/exam/testPaper/testPaperAdd.jsp');" /> 
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
			<th>试卷名称</th>
			<td><input type="text" name="paperName" value="${filter.name}" ></td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>科目名称</th>
			<th>试卷名称</th>
			<th>试卷所含题型</th>
			<th>试卷状态</th>
			<th>学分</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${ruleList}" var="rule" varStatus="i" >
		<tr>
			<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
			<td class="list_data_text">${rule.subject.name}</td>
			<td class="list_data_text">${rule.name}</td>
			<td class="list_data_text">
				<c:forEach items="${rdList}" var="rd" >
					<c:if test="${rd.rule.id == rule.id}">
						题型：${rd.type.name}&nbsp;数量：${rd.nums}&nbsp;每题分值：${rd.score}<br/>
					</c:if>
				</c:forEach>
			</td>
			<td class="list_data_text">
				<c:choose>
					<c:when test="${rule.status eq '0'}">禁用</c:when>
					<c:when test="${rule.status eq '1'}">启用</c:when>
				</c:choose>
			</td>
			<td class="list_data_number">${rule.credit}</td>
			<td class="list_data_text">${rule.createUser.name}</td>
			<td class="list_data_text">${rule.createTime}</td>
			<td class="list_data_text">
				<a href="admin/exam/testPaper/testPaperUpdate.jsp?id=${rule.id}">编辑</a>
				<a href="admin/exam/sysExam?method=paperChange&id=${rule.id}">
					<%-- <input type="hidden" name="changeId" value="${ques.id}" /> --%>
				<c:choose>
					<c:when test="${rule.status eq '0'}">启用</c:when>
					<c:when test="${rule.status eq '1'}">禁用</c:when>
				</c:choose>
				</a>
				<a href="admin/exam/sysExam?method=paperDelete&id=${rule.id}"
					onclick="return confirm('删除后无法恢复！是否确定删除？')" >删除</a>
				<a href="admin/exam/testPaper/testPaperInfo.jsp?id=${rule.id}">查看详情</a>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="10">
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
	</form>
</body>
</html>