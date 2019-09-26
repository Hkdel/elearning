<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>模块管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
			function submitForm(page){
				document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
<c:if test="${not empty addSuccess }">
	<script type="text/javascript">
			confirm('${auth.name}'+'${addSuccess}');
		</script>
</c:if>
<c:if test="${not empty cancelSuccess }">
	<script type="text/javascript">
			confirm('${auth.name}'+'${cancelSuccess}');
		</script>
</c:if>
<c:if test="${not empty restoreSuccess }">
	<script type="text/javascript">
			confirm('${auth.name}'+'${restoreSuccess}');
		</script>
</c:if>
<c:if test="${not empty updateSuccess }">
	<script type="text/javascript">
			confirm('${updateSuccess}');
		</script>
</c:if>
</head>
<body>
	<form action="admin/system/auth?method=list" method="post"
		id="submitForm">
		<div class="page_title">模块管理</div>
		<div class="button_bar">
			<a class="common_button" href="admin/system/model/modelAdd.jsp">新建</a>
			<a class="common_button" onclick="submitForm(1)">查询</a>
		</div>
		<table class="query_form_table">
			<tr>
				<th>模块名称</th>
				<td><input type="text" name="name" value="${filter['name']  }" /></td>
				<th>模块状态</th>
				<td><select name="status">
						<option value="-1">请选择</option>
						<option value="1"
							<c:if test="${filter['status'].equals('1') }">selected="selected"</c:if>>正常</option>
						<option value="0"
							<c:if test="${filter['status'].equals('0') }">selected="selected"</c:if>>注销</option>
				</select></td>
			</tr>
		</table>
		<br />
		<table class="data_list_table">
			<tr>
				<th>序号</th>
				<th>模块名称</th>
				<th>访问URL</th>
				<th>父模块</th>
				<th>状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${auths}" var="auth" varStatus="i">
				<tr>
					<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
					<td class="list_data_text">${auth.name }</td>
					<td class="list_data_text">${auth.url}</td>
					<td class="list_data_text">${auth.parent.name}</td>
					<td class="list_data_text"><c:choose>
							<c:when test="${auth.status == 1 }">正常</c:when>
							<c:otherwise>注销</c:otherwise>
						</c:choose></td>
					<td class="list_data_text">${auth.user.name }</td>
					<td class="list_data_text">${auth.createTime}</td>
					<td class="list_data_text"><a
						href="admin/system/auth?method=edit&authId=${auth.id}">编辑</a> <c:if
							test="${auth.status.equals('0') }">
							<a href="admin/system/auth?method=restore&authId=${auth.id}">恢复</a>
						</c:if> <c:if test="${auth.status.equals('1') }">
							<a href="admin/system/auth?method=cancel&authId=${auth.id}">注销</a>
						</c:if></td>
				</tr>

			</c:forEach>
			<tr>
				<th colspan="8">
					<div class="pager">
						<div class="pager_left">
							<span style="color: red">${addSuccess }${adderror }${cancelSuccess }${cancelerror }${restoreSuccess }${restoreerror }${updateSuccess }${updateerror }</span>
							共 ${pageUtils.totalSize}条记录 每页 ${pageUtils.pageSize}条
							第${pageUtils.currPage}页/共${pageUtils.totalPage}页 转到
							<input  size="1" type="number" min="1" max="${pageUtils.totalPage}" value="${pageUtils.currPage}" name="page" id="page"/>页 <input
								type="submit" value="GO">
						</div>
						<div class="pager_right">
							<button class="common_button" onclick="submitForm(1)">首页</button>
							<c:if test="${pageUtils.currPage > 1 }">
								<button class="common_button"
									onclick="submitForm(${pageUtils.currPage-1})">上一页</button>
							</c:if>
							<c:if test="${pageUtils.currPage < pageUtils.totalPage }">
								<button class="common_button"
									onclick="submitForm(${pageUtils.currPage+1})">下一页</button>
							</c:if>
							<button class="common_button"
								onclick="submitForm(${pageUtils.totalPage})">尾页</button>
						</div>
					</div>
				</th>
			</tr>
		</table>
	</form>
</body>
</html>