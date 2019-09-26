<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>科目管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<form action="admin/exam/sysExam?method=subList" method="post" id="modelForm" >
	<div class="page_title">科目管理</div>
	<div class="button_bar">
		<!-- <a class="common_button" href="admin/exam/subject/subjectAdd.jsp">新建</a> -->
		<input type="button" class="common_button" value="新建" onclick="to('admin/exam/subject/subjectAdd.jsp');" />
		<!-- <button class="common_button" onclick="to('admin/exam/subject/subjectAdd.jsp');">新建</button> -->
		<!-- <a class="common_button" onclick="subFrom(1)">查询</a> -->
		<input type="button" value="查询" class="common_button" onclick="subFrom(1)">
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td><input type="text" name="subName" value="${filter.name}" /></td>
			<th></th>
			<td></td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>科目名称</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${subList}" var="sub" varStatus="i">
		<tr>
			<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
			<td class="list_data_text">${sub.name}</td>
			<td class="list_data_text">${sub.createUser.name}</td>
			<td class="list_data_text">${sub.createTime}</td>
			<td class="list_data_text">
				<a href="admin/exam/subject/subjectUpdate.jsp?id=${sub.id}">编辑</a>
				<a href="admin/exam/sysExam?method=subDelete&id=${sub.id}" 
					onclick="return confirm('删除后不能回复！是否确定删除？')" >删除</a>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="7">
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