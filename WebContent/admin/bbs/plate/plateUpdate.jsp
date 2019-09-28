<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑版块</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
	<div class="page_title">版块管理&nbsp; &gt; 编辑版块</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="submit();">保存</button>
	</div>

	<form action="admin/bbs/plate/sysPlate?method=update&photo=${photo}&id=${id}" 
	method="post" id="Form" enctype="multipart/form-data">
		<table class="query_form_table">
			<tr>
				<th>版块名称</th>
				<td><input type="hidden" value="${id}" name="id" />
					<input type="text" value="${name}" name="name" /><span class="red_star">*</span></td>
				<th>版块简介</th>
				<td><input type="text" value="${introduction}" name="introduction" /></td>
			</tr>
			<tr>
				<th>版块图片</th>
				<td>
					<input type="file" value="${photo}" name="photo" /><span class="red_star">*</span>
				</td>
				<th>版主</th>
				<td><input type="text" value="${loginSysUser.name}" readonly /></td>
			</tr>
		</table>
	</form>
</body>
</html>