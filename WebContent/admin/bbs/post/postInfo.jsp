<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"%>
<%@ page import="com.zt.bbs.po.*,com.zt.bbs.dao.*,com.zt.bbs.dao.impl.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String idStr = request.getParameter("id");
	int id = 0;
	if (idStr != null) {
		id = Integer.parseInt(idStr);
	}
	BbsPostDao bbsPostDao = new BbsPostDaoImpl();
	BbsPost bbsPost = bbsPostDao.getBbsPostById(id);
	pageContext.setAttribute("bbsPost", bbsPost);
%>
<html>
	<head>
		<title>帖子审核</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">帖子管理&nbsp; &gt; 帖子审核</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>帖子名称</th>
			<td><input type="text" value="${bbsPost.name}" readonly /></td>
			<th>版块名称</th>
			<td><input type="text" value="${bbsPost.bbsPlate.name}" readonly /></td>
		</tr>
		<tr>
			<th>帖子内容</th>
			<td colspan="3">
				<textarea rows="6" cols="60" readonly>
				${bbsPost.content}
				</textarea>
			</td>
		</tr>
		<tr>
			<th>发帖人</th>
			<td><input type="text" value="${bbsPost.createUser.name }" readonly /></td>
			<th>发布时间</th>
			<td><input type="text" value="${bbsPost.createTime}" readonly /></td>
		</tr>
		<tr>
			<th>审核状态</th>
			<td>
				<c:choose>
					<c:when test="${bbsPost.checkStatus eq '0 '}">
						<input type="text" value="未审核" readonly />
					</c:when>
					<c:when test="${bbsPost.checkStatus eq '1 '}">
						<input type="text" value="通过" readonly />
					</c:when>
					<c:when test="${bbsPost.checkStatus eq '-1'}">
						<input type="text" value="驳回" readonly />
					</c:when>
				</c:choose>
			</td>
			<th>审核意见</th>
			<td><input type="text" value="${bbsPost.opinion }" readonly /></td>
		</tr>
		<tr>
			<th>审核人</th>
			<td><input type="text" value="${bbsPost.checkUser.name }" readonly /></td>
			<th>审核时间</th>
			<td><input type="text" value="${bbsPost.checkTime }" readonly /></td>
		</tr>

	</table>
</body>
</html>