<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑角色</title>
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
	<form action="admin/system/role?method=update" method="post" id="submitForm">
	<div class="page_title">角色管理&nbsp; &gt; 编辑角色</div>
	<div class="button_bar">
		<a class="common_button" onclick="back();">返回</a>
		<a class="common_button" onclick="submitForm()">保存</a>
	</div>
	<table class="query_form_table">
		<tr>
			<th>角色名称</th>
			<td><input type="text" name="roleName" value="${role.name }" /><span class="red_star">*</span></td>
			<th>创建人</th>
			<td><input type="text" name="createName" value="${loginSysUser.name }" readonly />
				<input type="hidden" name="roleId" value="${role.id }">
			</td>
		</tr>
		<tr>
			<th>创建时间</th>
			<td><input type="text" name="createTime" value="${role.createTime }" readonly /></td>
			<th></th>
			<td></td>
		</tr>
	</table>
	</form>
</body>
</html>