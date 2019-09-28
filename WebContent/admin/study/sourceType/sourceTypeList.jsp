<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>资源类型管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		
	</head>
	<script type="text/javascript">
		function subForm(page){
	//		if(page != 0 ){
				document.getElementById("page").value = page;
	//		}
			document.getElementById("typeForm").submit();
		}
		function rePage() {
			document.getElementById("page").value = 1;
		}
	</script>
<body>
	<div class="page_title">资源类型管理</div>
	<form action="admin/study/sysStudy?method=typeList" method="post" id="typeForm">
	<!-- <input type="hidden" name="page" id="page"/> -->
	<div class="button_bar">
		<input type="button" value="新建" onclick="to('admin/study/sourceType/sourceTypeAdd.jsp');" class="common_button"/>
		<!-- <button class="common_button" onclick="to('sourceTypeAdd.jsp');">新建</button> -->
		<!-- <button class="common_button" onclick="subForm(1)">查询</button> -->
		<input type="submit" value="查询"  class="common_button" onclick="rePape()"/>
	</div>
	<table class="query_form_table">
		<tr>
			<th>资源类型名称</th>
			<td><input type="text" name="typeName" value="${filter['typeName']}"/></td>
			<th>状态</th>
			<td>
				<select name="status">
					<option value="2" <c:if test="${filter['status']==2 }">selected="selected"</c:if>>全部</option>
					<option value="1" <c:if test="${filter['status']==1 }">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${filter['status']==0 }">selected="selected"</c:if>>停用</option>
					
				</select>
				
			</td>
		</tr>
	</table>
	
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>资源类型名称</th>
			<th>状态</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${typeList }" var="type" varStatus="i">
		<tr>
			<td class="list_data_number">${i.count }</td>
			<td class="list_data_text">${type.typeName }</td>
			<td class="list_data_text">
				<c:if test="${type.status==1 }">启用</c:if>
				<c:if test="${type.status==0 }">停用</c:if>
			</td>
			
			<td class="list_data_text">${type.user.name }</td>
			<td class="list_data_text">${type.createTime }</td>
			<td class="list_data_text">
				<a href="admin/study/sysStudy?method=editType&id=${type.id }">编辑</a>
				<a href="admin/study/sysStudy?method=updateStatus&id=${type.id }&status=${type.status}">
					<c:if test="${type.status==0 }">启用</c:if>
					<c:if test="${type.status==1 }">停用</c:if>
				</a>
				
				<a href="admin/study/sysStudy?method=delType&id=${type.id }"  onclick="return confirm('确定删除吗？')">删除</a>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="7">
				<div class="pager">
					<div class="pager_left">
						共${pageUtils.totalSize }条记录 每页${pageUtils.pageSize }条
						第${pageUtils.currPage}页/共${pageUtils.totalPage }页
						转到<input type="number" id="page" name="page"
						 value="${pageUtils.currPage }" size="1" max="${pageUtils.totalPage}" min="1" />页
						
						<input type="submit" value="Go" style="width: 40px;"/>
						
					</div>
					<div class="pager_right">
						<button class="common_button" onclick="subForm(1)">首页</button>
						<c:if test="${pageUtils.currPage>1 }">
							<button class="common_button" onclick="subForm(${pageUtils.currPage-1 })">上一页</button>
						</c:if>
						<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
							<input type="button" value="下一页" class="common_button" onclick="subForm(${pageUtils.currPage+1 })"/>
							<%-- <button class="common_button" onclick="subForm(${pageUtils.currPage+1 })">下一页</button> --%>
						</c:if>
						<button class="common_button" onclick="subForm(${pageUtils.totalPage })">尾页</button>
					</div>



				</div>
			</th>
		</tr>
	</table>
	</form>

</body>
</html>