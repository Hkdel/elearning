<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>帖子管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">帖子管理</div>
	<div class="button_bar">
		<button class="common_button" onclick="subFrom(1);">查询</button>
	</div>
	<form action="admin/bbs/post/sysPost?method=findByPage" method="post" id="modelForm">
	<table class="query_form_table">
		<tr>
			<th>帖子名称</th>
			<td><input type="text" name="name" value="${filter['name']}"></td>
			<th>审核状态</th>
			<td>
				<select name="checkStatus">
					<option value="2">请选择</option>
					<option value="0">未审核</option>
					<option value="1">通过</option>
					<option value="-1">驳回</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>版块</th>
			<td><input type="text" name="plateName" value="${filter['plateName']}"></td>
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
			<th>帖子名称</th>
			<th>版块名称</th>
			<th>发帖人</th>
			<th>发布时间</th>
			<th>审核状态</th>
			<th>审核时间</th>
			<th>审核人</th>
			<th>是否加精</th>
			<th>是否置顶</th>
			<th>操作</th>
		</tr>
		
		<c:forEach items="${postList}" var="post" varStatus="i">
			<tr>
				<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
				<td class="list_data_text">${post.name}</td>
				<td class="list_data_text">${post.bbsPlate.name}</td>
				<td class="list_data_text">${post.createUser.name}</td>
				<td class="list_data_text">${post.createTime}</td>
				<td class="list_data_text">
					<c:choose>
						<c:when test="${post.checkStatus eq '0 '}">未审核</c:when>
						<c:when test="${post.checkStatus eq '1 '}">通过</c:when>
						<c:when test="${post.checkStatus eq '-1'}">驳回</c:when>
					</c:choose>
				</td>
				<td class="list_data_text">${post.checkTime}</td>
				<td class="list_data_text">${post.checkUser.name}</td>
				<td class="list_data_text">
					<c:choose>
						<c:when test="${post.isGood eq '0 '}">非精品</c:when>
						<c:when test="${post.isGood eq '1 '}">精品</c:when>
					</c:choose>
				</td>
				<td class="list_data_text">
					<c:choose>
						<c:when test="${post.isTop eq '0 '}">未置顶</c:when>
						<c:when test="${post.isTop eq '1 '}">已置顶</c:when>
					</c:choose>
				</td>
				
				<td class="list_data_text">
				<c:choose>
					<c:when test="${post.checkStatus eq '-1'}">
						<a href="admin/bbs/post/postInfo.jsp?id=${post.id}">查看详情</a>
					</c:when>
					<c:otherwise>
						<c:if test="${post.checkStatus eq '0 '}">
							<a href="admin/bbs/post/postAudit.jsp?id=${post.id}">审核</a>
						</c:if>
						<c:if test="${post.checkStatus eq '1 '}">
							<c:choose>
								<c:when test="${post.isGood eq '0 '}">
									<a href="admin/bbs/post/sysPost?method=isGood
									&id=${post.id}&isGood=${post.isGood}">加精</a>
								</c:when>
								<c:when test="${post.isGood eq '1 '}">
									<a href="admin/bbs/post/sysPost?method=isGood
									&id=${post.id}&isGood=${post.isGood}">取消加精</a>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${post.isTop eq '0 '}">
									<a href="admin/bbs/post/sysPost?method=isTop
									&id=${post.id}&isTop=${post.isTop}">置顶</a>
								</c:when>
								<c:when test="${post.isTop eq '1 '}">
									<a href="admin/bbs/post/sysPost?method=isTop
									&id=${post.id}&isTop=${post.isTop}">取消置顶</a>
								</c:when>
							</c:choose>
						</c:if>
						<a href="admin/bbs/post/postInfo.jsp?id=${post.id}">查看详情</a>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</c:forEach>
		
		<tr>
			<th colspan="11">
				<div class="pager">
					<div class="pager_left">
						共${pageUtils.totalSize}条记录 每页10条
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