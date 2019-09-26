<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑资源类型</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">资源类型管理&nbsp; &gt; 编辑资源类型</div>
	<form action="admin/study/sysStudy?method=updateType" method="post">
	<input type="hidden" name="id" value="${type.id }"/>
	
	<div class="button_bar">
		<!-- <button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="save('sourceTypeList.jsp');">保存</button> -->
		<input type="button" value="返回" onclick="back();" class="common_button"/>
		<input type="submit" value="保存" class="common_button" onclick="alert('保存成功！');"/>
	</div>
	<table class="query_form_table">
		<tr>
			<th>资源名称</th>
			<td><input type="text" value="${type.typeName }" name="typeName" /><span class="red_star">*</span></td>
			<th>创建人</th>
			<td><input type="text" value="${type.user.name}" readonly /></td>
		</tr>
		<tr>
			<th>状态</th>
			<td>
				<select name="status">
					<option value="1" <c:if test="${type.status==1 }">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${type.status==0 }">selected="selected"</c:if>>停用</option>
					
				</select>
				<span class="red_star">*</span></td>
			<th>创建时间</th>
			<td><input type="text" value="${type.createTime }" readonly /></td>
		</tr>
	</table>
	</form>
</body>

</html>