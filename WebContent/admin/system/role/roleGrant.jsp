<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>角色赋权</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<style type="text/css">
			ul {
				list-style: none;
			}
			#treeMenu ul{
				margin-left: 50px;
			}
			.hidden{
				display: none;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$("#treeMenu").children("li").children(":checkbox").click(function(){
					var flag = $(this).attr("checked");
					$(this).siblings("ul").show();
					$(this).siblings("ul").children("li").children(":checkbox").attr("checked", flag);
				}).siblings("a").click(function(){
					$(this).siblings("ul").toggle();
				});
			});
		</script>
		<script type="text/javascript">
			function submitForm(){
				//document.getElementById("page").value = page;
				document.getElementById("submitForm").submit();
			}
		</script>
	</head>
<body>
	<form action="admin/system/role?method=saveRoleAuth" method="post" id="submitForm">
	<div class="page_title">角色管理&nbsp; &gt; 角色赋权</div>
	<div class="button_bar">
		<a class="common_button" onclick="back();">返回</a>
		<a class="common_button" onclick="submitForm()">保存</a>
	</div>
	<table class="query_form_table">
		<tr>
			<th>角色名称</th>
			<td>
			<input type="text" value="${role.name}" readonly />
			<input type="hidden" name="roleId" value="${role.id}" readonly />
			<span style="color: red;">${granterror }</span>
			</td>
			<th></th>
			<td></td>
		</tr>

		<tr>
			<th>赋权</th>
			<td colspan="3">
				<ul id="treeMenu" class="forminfo">
				<c:forEach items="${parentList}" var="p">
					<c:if test="${p.status.equals('1') }">
					<li>
						<input type="checkbox"/>
						<a href="javascript:void(0);">${p.name }</a>

						<ul class="hidden">
						<c:forEach items="${authList}" var="au">
						<c:if test="${au.parent.id == p.id }">
						<c:if test="${au.status.equals('1') }">
							<li>
								<input type="checkbox" name="authId" value="${au.id}" 
								<c:forEach items="${roleAuthList}" var="ra">
								    <c:if test="${au.id==ra.id}">checked="checked"</c:if>
								</c:forEach>
								/>
								<a href="javascript:void(0);">${au.name }</a>
							</li>
							</c:if>
						</c:if>
						</c:forEach>
						</ul>
					</li>
					</c:if>
				</c:forEach>
				</ul>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>