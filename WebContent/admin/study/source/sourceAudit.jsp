<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>资源审核</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">资源审核&nbsp; &gt; 资源审核</div>
	<form action="admin/study/sysStudy?method=updateResource" method="post">
	<input type="hidden" name="id" value="${res.id }"/>
	
	<div class="button_bar">
		<!-- <button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="save('sourceList.jsp');">保存</button> -->
		<input type="button" value="返回" onclick="back();" class="common_button"/>
		<input type="submit" value="保存" class="common_button" onclick="alert('保存成功！');"/>
	</div>
	<table class="query_form_table">
		<tr>
			<th>资源名称</th>
			<td><input type="text" value="${res.name }" readonly /></td>
			<th>资源类别</th>
			<td><input type="text" value="${res.type.typeName }" readonly /></td>
		</tr>
		<tr>
			<th>描述</th>
			<td colspan="3">
				<textarea rows="6" cols="60" readonly>
				${res.describe }
				</textarea>
			</td>
		</tr>
		<tr>
			<th>上传人</th>
			<td><input type="text" value="${res.uploadUser.name }" readonly /></td>
			<th>上传时间</th>
			<td><input type="text" value="${res.uploadTime }" readonly /></td>
		</tr>
		<tr>
			<th>审核状态</th>
			<td>
				<select name="checkStatus">
					<option value="1" <c:if test="${res.checkStatus==0 }">selected="selected"</c:if>>请选择</option><!--不选择默认就是通过  -->
					<option value="1" <c:if test="${res.checkStatus==1 }">selected="selected"</c:if>>通过</option>
					
					<option value="2" <c:if test="${res.checkStatus=='2' }">selected="selected"</c:if>>驳回</option>
				</select>
				<span class="red_star">*</span></td>
			<th>审核意见</th>
			<td><input type="text" name="checkOpinion" value="${res.checkOpinion }" /><span class="red_star">*</span></td>
		</tr>

	</table>
	</form>
</body>
</html>