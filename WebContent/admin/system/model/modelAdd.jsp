<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.zt.user.dao.AuthDao,com.zt.user.dao.impl.AuthDaoImpl,
com.zt.user.po.Auth,java.util.*"%>
<%@include file="../../../tag.jsp"%>
<%
	AuthDao authDao = new AuthDaoImpl();
	List<Auth> parentList = authDao.findParentAuth();
	pageContext.setAttribute("parentList", parentList);
	session.getAttribute("loginSysUser");
	java.util.Date date = new Date(); //获取当前时间
	//out.print(date);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化时间 
	String createTime = sdf.format(date);
	pageContext.setAttribute("createTime", createTime);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加模块</title>
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
	<div class="page_title">模块管理&nbsp; &gt; 添加模块</div>
	<form action="admin/system/auth?method=add" method="post"
		id="submitForm">
		<div class="button_bar">
			<a class="common_button" onclick="back();">返回</a> <a
				class="common_button" onclick="submitForm()">保存</a>
		</div>
		<table class="query_form_table">
			<tr>
				<th>模块名称</th>
				<td><input type="text" name="authName" /><span
					class="red_star">*</span>${error }</td>
				<th>访问URL</th>
				<td><input type="text" name="url" />${urlerror } <input
					type="hidden" name="page" value="${page }"></td>
			</tr>
			<tr>
				<th>父模块</th>
				<td><select name="parentId">
						<option value="0">无</option>
						<c:forEach items="${parentList }" var="p">
							<c:if test="${p.status.equals('1') }">
								<option value="${p.id }">${p.name }</option>
							</c:if>
						</c:forEach>
				</select></td>
				<th>创建人</th>
				<td><input type="text" name="createName"
					value="${loginSysUser.name }" readonly /></td>
			</tr>
			<tr>
				<th>创建时间</th>
				<td><input type="text" name="createTime" value="${createTime }"
					readonly /></td>
				<th></th>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>