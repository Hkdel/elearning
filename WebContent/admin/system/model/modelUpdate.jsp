<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑模块</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			function submitForm(){
				//document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
	</head>
<body>
	<form action="admin/system/auth?method=update" method="post" id="submitForm">
	<div class="page_title">模块管理&nbsp; &gt; 编辑模块</div>
	<div class="button_bar">
		<a class="common_button" onclick="back();">返回</a>
		<a class="common_button" onclick="submitForm()">保存</a>
	</div>
	<table class="query_form_table">
		<tr>
			<th>模块名称</th>
			<td><input type="text" name="name" value="${auth.name }" /><span class="red_star">*</span></td>
			<th>访问URL</th>
			<td><input type="text" name="url" value="${auth.url }" />
				<input type="hidden" name="authId" value="${auth.id }">
				<%-- <input type="hidden" name="page" value="${page }"> --%>
			</td>
		</tr>
		<tr>
			<th>父模块</th>
			<td>
				<select name="parentId">
					<option value="0">无</option>
					<c:forEach items="${parentList }" var="p">
					<c:if test="${p.status.equals('1') }">
					<option value="${p.id }" 
						<c:if test="${p.id == auth.parent.id}">selected="selected"</c:if>
					>
					${p.name }</option>
					</c:if>
					</c:forEach>
				</select>
			</td>
			<th>创建人</th>
			<td><input type="text" name="createName" value="${auth.user.name }" readonly /></td>
		</tr>
		<tr>
			<th>创建时间</th>
			<td><input type="text" name="createTime" value="${auth.createTime }" readonly /></td>
			<th></th>
			<td></td>
		</tr>
	</table>
	</form>
</body>
</html>