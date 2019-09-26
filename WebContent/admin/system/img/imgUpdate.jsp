<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑首页图片</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	function submitForm() {
		document.getElementById("submitForm").submit();
	}
</script>
</head>
<body>
	<form action="admin/system/img?method=update" method="post"
		id="submitForm" enctype="multipart/form-data">
		<div class="page_title">首页图片管理&nbsp; &gt; 编辑首页图片</div>
		<div class="button_bar">
			<a class="common_button" onclick="back();">返回</a> <a
				class="common_button" onclick="submitForm()">保存</a>
		</div>
		<table class="query_form_table">
			<tr>
				<th>图片</th>
				<td><input type="file" name="newUrl" /><span class="red_star">*<br></span>
					<input type="text" name="url" value="${photo.url }" size="40px"
					readonly="readonly"> <input type="hidden" name="imgId"
					value="${photo.id }">
					<img alt="error" src="${photo.url }" style="width: 50px;height: 50px">
				</td>
				<th>位置</th>
				<td><input type="text" name="place" value="${photo.place }" /><span
					class="red_star">*</span></td>
			</tr>
			<tr>
				<th>创建人</th>
				<td><input type="text" name="createName"
					value="${loginSysUser.name } " readonly /></td>
				<th>创建时间</th>
				<td><input type="text" name="createTime"
					value="${photo.createTime }" readonly /></td>

			</tr>
		</table>
	</form>
</body>
</html>