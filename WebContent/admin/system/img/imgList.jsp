<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>首页图片管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
			function submitForm(page){
				document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
<c:if test="${not empty deleteSuccess}">
	<script type="text/javascript">
			confirm('${deleteSuccess}');
		</script>
</c:if>
<c:if test="${not empty updateSuccess}">
	<script type="text/javascript">
			confirm('${updateSuccess}');
		</script>
</c:if>
<c:if test="${not empty addSuccess}">
	<script type="text/javascript">
			confirm('${addSuccess}');
		</script>
</c:if>
</head>
<body>
	<form action="admin/system/img?method=list" method="post"
		id="submitForm">
		<div class="page_title">首页图片管理</div>
		<div class="button_bar">
			<a class="common_button" href="admin/system/img/imgAdd.jsp">新建</a>
			<!-- <a class="common_button" onclick="submitForm(1)">查询</a> -->
		</div>
		<br />
		<table class="data_list_table">
			<tr>
				<th>编号</th>
				<th>图片</th>
				<th>位置</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${photos }" var="img" varStatus="i">
				<tr>
					<td class="list_data_number">${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
					<td class="list_data_text">
					<img alt="error" src="${img.url }" style="width: 50px;height: 50px">
					</td>
					<td class="list_data_text">${img.place }</td>
					<td class="list_data_text">${img.user.name }</td>
					<td class="list_data_text">${img.createTime }</td>
					<td class="list_data_text"><a
						href="admin/system/img?method=edit&imgId=${img.id }">编辑</a> <a
						href="admin/system/img?method=delete&imgId=${img.id }">删除</a></td>
				</tr>
			</c:forEach>
			<tr>
				<th colspan="7">
					<div class="pager">
						<div class="pager_left">
							<span style="color: red">${updateSuccess }${updateerror }${deleteerror }${addSuccess }${adderror }</span>共
							${pageUtils.totalSize}条记录 每页 ${pageUtils.pageSize}条
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