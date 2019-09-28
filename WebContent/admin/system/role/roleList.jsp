<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>角色管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			function submitForm(page){
				document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
		<c:if test="${not empty grantSuccess }">
		<script type="text/javascript">
			confirm('${role.name}'+'${grantSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty cancelSuccess }">
		<script type="text/javascript">
			confirm('${role.name}'+'${cancelSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty restoreSuccess }">
		<script type="text/javascript">
			confirm('${role.name}'+'${restoreSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty updateSuccess }">
		<script type="text/javascript">
			confirm('${updateSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty addSuccess }">
		<script type="text/javascript">
			confirm('${addSuccess}');
		</script>
		</c:if>
	</head>
<body>
	<form action="admin/system/role?method=list" method="post" id="submitForm">
	<div class="page_title">角色管理</div>
	<div class="button_bar">
		<a class="common_button" href="admin/system/role/roleAdd.jsp">新建</a>
		<a class="common_button" onclick="submitForm(1)">查询</a>
	</div>
	<table class="query_form_table">
		<tr>
			<th>角色名称</th>
			<td><input type="text" name="name" value="${filter['name'] }"/></td>
			<th>状态</th>
			<td>
				<select name="status">
					<option value="-1">请选择</option>
					<option value="1" <c:if test="${filter['status'].equals('1') }">selected="selected"</c:if>>正常</option>
					<option value="0" <c:if test="${filter['status'].equals('0') }">selected="selected"</c:if>>注销</option>
				</select>
			</td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>角色名称</th>
			<th>状态</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${roles}" var="r" varStatus="i">
		<tr>
			<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
			<td class="list_data_text">${r.name }</td>
			<td class="list_data_text">
				<c:choose>
				<c:when test="${r.status.equals('1') }">正常</c:when>
				<c:otherwise>注销</c:otherwise>
				</c:choose>
			</td>
			<td class="list_data_text">${r.user.name }</td>
			<td class="list_data_text">${r.createTime }</td>
					<td class="list_data_text">
						<a href="admin/system/role?method=edit&roleId=${r.id}">编辑</a>
				<c:if test="${r.status.equals('0') }">
				<a href="admin/system/role?method=restore&roleId=${r.id}">恢复</a>
				</c:if>
				<c:if test="${r.status.equals('1') }">
				<a href="admin/system/role?method=cancel&roleId=${r.id}">注销</a>
				</c:if>
				<c:if test="${r.id != 5}">
						<a href="admin/system/role?method=grant&roleId=${r.id}">赋权</a>
				</c:if>
				</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="6">
				<div class="pager">
					<div class="pager_left">
	<span style="color: red">${updateSuccess }${updateerror }${cancelSuccess }${cancelerror }${restoreSuccess }${restoreerror }${addSuccess }${adderror }</span>共 ${pageUtils.totalSize}条记录 每页 ${pageUtils.pageSize}条 
						第${pageUtils.currPage}页/共${pageUtils.totalPage}页
					转到<input  size="1" type="number" min="1" max="${pageUtils.totalPage}" value="${pageUtils.currPage}" name="page" id="page"/>页
                        <input type="submit" value="GO">
					</div>
					<div class="pager_right">
						<button class="common_button" onclick="submitForm(1)">首页</button>
						<c:if test="${pageUtils.currPage > 1 }">
						<button class="common_button" onclick="submitForm(${pageUtils.currPage-1})">上一页</button>
						</c:if>
						<c:if test="${pageUtils.currPage < pageUtils.totalPage }">
						<button class="common_button" onclick="submitForm(${pageUtils.currPage+1})">下一页</button>
						</c:if>
						<button class="common_button" onclick="submitForm(${pageUtils.totalPage})">尾页</button>
					</div>
				</div>
			</th>
		</tr>
	</table>
	</form>
</body>
</html>