<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加科目</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<!-- <input type="submit" class="common_button" value="保存" /> -->
	</head>
<body>
	<div class="page_title">科目管理&nbsp; &gt; 添加科目</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="submit();">保存</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<form action="admin/exam/sysExam?method=subAdd" method="post" id="Form" >
			<td><input type="text" name="subName" /><span class="red_star">*</span></td>
			<th>创建人</th>
			<td><input type="text" value="scott" readonly /></td>
			</form>
		</tr>
	</table>
</body>
</html>