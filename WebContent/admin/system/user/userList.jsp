<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp" %>
<c:if test="${empty loginSysUser}">
	<%response.sendRedirect("../login.jsp");
	//request.getRequestDispatcher("../../login.jsp").forward(request, response);
	%>
</c:if>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			function submitForm(page){
				document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
			
		</script>
		
		<c:if test="${not empty addSuccess}">
		<script type="text/javascript">
		alert('用户：'+'${user.name}'+'${addSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty cancelSuccess}">
		<script type="text/javascript">
		alert('用户：'+'${user.name}'+'${cancelSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty restoreSuccess}">
		<script type="text/javascript">
		alert('用户：'+'${user.name}'+'${restoreSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty updateSuccess}">
		<script type="text/javascript">
		alert('用户：'+'${user.name}'+'${updateSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty resetSuccess}">
		<script type="text/javascript">
		alert('用户：'+'${user.name}'+'${resetSuccess}');
		</script>
		</c:if>
	</head>
<body>
	<form action="admin/system/user?method=list" method="post" id="submitForm">
	<div class="page_title">用户管理</div>
	<div class="button_bar">
		<a class="common_button" href="admin/system/user/userAdd.jsp">新建</a>
		<a class="common_button" onclick="submitForm(1)">查询</a>
	</div>
	<table class="query_form_table">
		<tr>
			<th>姓名</th>
			<td><input type="text" name="userName" value="${filter['userName'] }"/></td>
			<th>角色</th>
			<td>
				<select name="roleId">
					<option value="0">请选择</option>
					<c:forEach items="${roles }" var="role">
					<c:if test="${role.status.equals('1') }">
					<option value="${role.id }" <c:if test="${role.id == filter['roleId'] }">selected="selected"</c:if>>${role.name}</option>
					</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>账号</th>
			<td><input type="text" name="accountName" value="${filter['accountName'] }"/></td>
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
			<th>头像</th>
			<th>姓名</th>
			<th>账号</th>
			<th>角色</th>
			<th>性别</th>
			<th>出生日期</th>
			<th>论坛积分</th>
			<th>考试学分</th>
			<th>状态</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${users }" var="user" varStatus="i">
		<tr>
			<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
			<td class="list_data_text">
				<img alt="error" src="${user.photo }" style="width: 50px;height: 50px">
			</td>
			<td class="list_data_text">${user.name }</td>
			<td class="list_data_text">${user.accountName }</td>
			<td class="list_data_text">${user.role.name }</td>
			<td class="list_data_text">${user.sex}</td>
			<td class="list_data_text">${user.birthday}</td>
			<td class="list_data_text">${user.bbsScore }</td>
			<td class="list_data_text">${user.examScore }</td>
			<td class="list_data_text">
			<c:choose>
				<c:when test="${user.status.equals('1') }">正常</c:when>
				<c:otherwise>注销</c:otherwise>
				</c:choose>
			</td>
			<td class="list_data_text">${user.user.name }</td>
			<td class="list_data_text">${user.createTime }</td>
			<td class="list_data_text">
				<a href="admin/system/user?method=edit&userId=${user.id}">编辑</a>
				<c:if test="${user.status.equals('0') }">
				<a href="admin/system/user?method=restore&userId=${user.id}">恢复</a>
				</c:if>
				<c:if test="${user.status.equals('1') }">
				<c:if test="${user.name != 'scott' }">
				<a href="admin/system/user?method=cancel&userId=${user.id}">注销</a>
				</c:if>
				</c:if>
				<c:if test="${user.id != loginSysUser.id }">
				<a href="admin/system/user/userResetPass.jsp?userId=${user.id}">重置密码</a>
				</c:if>
			</td>	
		</tr>
		</c:forEach>
		<tr>
			<th colspan="13">
							<div class="pager">
					<div class="pager_left">
	<span style="color: red">${updateSuccess }${updateerror }${cancelSuccess }${cancelerror }${restoreSuccess }${restoreerror }${addSuccess }${adderror}</span>共 ${pageUtils.totalSize}条记录 每页 ${pageUtils.pageSize}条 
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