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
	<script type="text/javascript">
		function subForm(page){
	//		if(page != 0 ){
				document.getElementById("page").value = page;
	//		}
			document.getElementById("resForm").submit();
		}
		function rePage() {
			document.getElementById("page").value = 1;
		}
	</script>
<body>
	<div class="page_title">资源审核</div>
	<form action="admin/study/sysStudy?method=sourceList" method="post" id="resForm">
	<!-- <input type="hidden" name="page" id="page"/> -->
	<div class="button_bar">
		<!-- <button class="common_button" onclick="reload()">查询</button> -->
		<input type="submit" value="查询"  class="common_button" onclick="rePape()"/>
	</div>
	<table class="query_form_table">
		<tr>
			<th>资源名称</th>
			<td><input type="text" name="name" value="${filter['name']}"/></td>
			<th>审核状态</th>
			<td>
				<select name="checkStatus">
					<option value="3" <c:if test="${filter['checkStatus']==3 }">selected="selected"</c:if>>请选择</option>
					<option value="0" <c:if test="${filter['checkStatus']==0 }">selected="selected"</c:if>>未审核</option>
					<option value="1" <c:if test="${filter['checkStatus']==1 }">selected="selected"</c:if>>通过</option>
					<option value="2" <c:if test="${filter['checkStatus']==2 }">selected="selected"</c:if>>驳回</option>
					
				</select>
			</td>
		</tr>
		<tr>
			<th>资源类别</th>
			<td>
				<select name="typeId">
					<option value="0">全部</option>
					<c:forEach items="${typeList }" var="type">
						<option value="${type.id }" <c:if test="${type.id==filter['typeId']}">selected="selected"</c:if>>${type.typeName }</option>
					</c:forEach>
				</select>
			</td>
			<th>上传时间</th>
			<td><input type="date"  name="beginTime" value="${filter['begin'] }">-
			<input type="date"  name="endTime" value="${filter['end'] }"></td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>资源名称</th>
			<th>资源类别</th>
			<th>描述</th>
			<th>上传人</th>
			<th>上传时间</th>
			<th>审核状态</th>
			<th>审核时间</th>
			<th>审核人</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${resList }" var="res" varStatus="i">
		<tr>
			<td class="list_data_number">${i.count }</td>
			<td class="list_data_text">${res.name }</td>
			<td class="list_data_text">${res.type.typeName }</td>
			<td class="list_data_text">${res.describe }</td>
			<td class="list_data_text">${res.uploadUser.name }</td>
			<td class="list_data_text">${res.uploadTime }</td>
			<td class="list_data_text">
				<c:if test="${res.checkStatus==0 }">未审核</c:if>
				<c:if test="${res.checkStatus==1 }">审核通过</c:if>
				<c:if test="${res.checkStatus==2 }">驳回</c:if>
			</td>
			<td class="list_data_text">${res.checkTime }</td>
			<td class="list_data_text">${res.checkUser.name }</td>
			<td class="list_data_text">
			<c:if test="${res.checkStatus==0 }">
				<a href="admin/study/sysStudy?method=AuditResource&id=${res.id }">审核</a>
			</c:if>
			<c:if test="${res.checkStatus==1 }">
				<a href="admin/study/sysStudy?method=AuditResource&id=${res.id }">重审</a>
			</c:if>
				<a href="${res.url}">下载</a>
			
			</td>
		</tr>
		</c:forEach>
		<tr>
			<th colspan="11">
				<div class="pager">
					<div class="pager_left">
						共${pageUtils.totalSize }条记录 每页${pageUtils.pageSize }条
						第${pageUtils.currPage}页/共${pageUtils.totalPage }页
						转到<input type="number" value="${pageUtils.currPage }"
						 id="page" name="page"  size="1" min="1"
						  max="${pageUtils.totalPage }" />页
						<input type="submit" value="Go" style="width: 40px;">
						
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