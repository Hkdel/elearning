<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>版块管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
	<div class="page_title">版块管理</div>
	<div class="button_bar">
		<button class="common_button" onclick="to('admin/bbs/plate/plateAdd.jsp');">新建</button>
		<button class="common_button" onclick="subFrom(1);">查询</button>
	</div>
	<form action="admin/bbs/plate/sysPlate?method=findByPage" method="post" id="modelForm">
		<table class="query_form_table">
			<tr>
				<th>版块名称</th>
				<td><input type="text" name="bName" value="${filter['bName']}"></td>
				<th>状态</th>
				<td>
					<select name="status">
						<option value="-1">请选择</option>
						<option value="1">正常</option>
						<option value="0">禁用</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>版主</th>
				<td><input type="text" name="uName" value="${filter['uName']}"></td>
				<th>时间</th>
				<td>
					<input type="date" name="beginTime" value="${filter['begin']}">
					 - 
					<input type="date" name="endTime" value="${filter['end']}">
				</td>
			</tr>
		</table>

		<br />
		<table class="data_list_table">
			<tr>
				<th>编号</th>
				<th>版块图片</th>
				<th>版块名称</th>
				<th>版块简介</th>
				<th>版主</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>

			<c:forEach items="${plateList}" var="plate" varStatus="i">
				<tr>
					<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
					<td class="list_data_text">
						<img alt="error" src="bbs/${plate.photo}" style="width: 50px;height: 50px">
					</td>
					<td class="list_data_text">${plate.name}</td>
					<td class="list_data_text">${plate.introduction}</td>
					<td class="list_data_text">${plate.createUser.name}</td>
					<td class="list_data_text">
						<c:if test="${plate.status eq '1 '}">正常</c:if>
						<c:if test="${plate.status eq '0 '}">被禁用</c:if>
					</td>
					<td class="list_data_text">${plate.createTime}</td>
					<td class="list_data_text">
					<c:if test="${loginSysUser.role.id == 1 || loginSysUser.id == plate.createUser.id}">
						<a href="admin/bbs/plate/sysPlate?method=edit&id=${plate.id}">编辑</a>
						<c:choose>
							<c:when test="${plate.status eq '1 '}">
								<a href="admin/bbs/plate/sysPlate?method=updateStatus
								&id=${plate.id}&status=${plate.status}">禁用</a>
							</c:when>
							<c:otherwise>
								<a href="admin/bbs/plate/sysPlate?method=updateStatus
								&id=${plate.id}&status=${plate.status}">启用</a>
							</c:otherwise>
						</c:choose> 
						<c:choose>
							<c:when test="${plate.status eq '0 '}">
								<a href="admin/bbs/plate/sysPlate?method=del&id=${plate.id}">删除</a>
							</c:when>
							<c:otherwise>删除</c:otherwise>
						</c:choose>
					</c:if>
					</td>
				</tr>
			</c:forEach>

			<tr>
				<th colspan="10">
					<div class="pager">
						<div class="pager_left">
							共${pageUtils.totalSize}条记录 每页5条
							第${pageUtils.currPage}页/共${pageUtils.totalPage}页 
							转到<input  size="1" type="number" min="1" max="${pageUtils.totalPage}" value="${pageUtils.currPage}" name="page" id="page"/>页 <input
								type="submit" value="GO">
						</div>
						<div class="pager_right">
							<a class="common_button" onclick="subFrom(1)">首页</a>
							<c:if test="${pageUtils.currPage>1 }">
								<a class="common_button" onclick="subFrom(${pageUtils.currPage-1 })">上一页</a>
							</c:if>
							<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
								<a class="common_button" onclick="subFrom(${pageUtils.currPage+1 })">下一页</a>
							</c:if>
							<a class="common_button" onclick="subFrom(${pageUtils.totalPage })">尾页</a>
						</div>
					</div>
				</th>
			</tr>
		</table>
	</form>
</body>
</html>