<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="friend/css/friend.css" />
<script type="text/javascript">
function submitForm(page){
	document.getElementById("page").value=page;
	document.getElementById("addFriendForm").submit();
}
</script>
</head>
<body>
<div style="height: 400px;width: 990px;">
	<table class="table1">
		<tr>
			<td class="b80" id="td4">头像</td>
			<td class="b80" id="td4">姓名</td>
			<td class="b80" id="td4">性别</td>
			<td class="b80" id="td1">账号</td>
			<td class="b80" id="td3">附加信息</td>
			<td class="b80">操作</td>
		</tr>
		<c:forEach items="${applyList }" var="applyFriend">
			<tr class="tr1">
				<td class="b33"><img src="${applyFriend.loginUser.photo }" style="width: 28px;height: 28px;"/></td>
				<td class="b33">${applyFriend.loginUser.name }</td>
				<td class="b33">${applyFriend.loginUser.sex }</td>
				<td class="b33">${applyFriend.loginUser.accountName }</td>
				<td class="b33">${applyFriend.withMsg }</td>
				<td>
					<a href="friend/frontFriend?method=applyAccept&fromId=${applyFriend.loginUser.id }" class="b65 col1" style="color: #00ff00">接受</a>
					<span class="span18"><a href="friend/frontFriend?method=applyRefuse&fromId=${applyFriend.loginUser.id }" class="b80 col2">拒绝</a></span>
				</td>
			</tr>
		</c:forEach>
	</table>
	
<form action="friend/frontFriend?method=friendApplyList" method="post" id="addFriendForm">
<input type="hidden" name="page" id="page">
	<div class="lastRow">
		<span>当前第${pageUtils.currPage}页&nbsp;&nbsp;每页${pageUtils.pageSize }条数据
		&nbsp;&nbsp;共${pageUtils.totalPage }页</span>
		<a href="javascript:void(0)" class="b65" onclick="submitForm(1)">首页</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="b65" onclick="submitForm(${pageUtils.totalPage })">尾页</a>&nbsp;&nbsp;
		<c:if test="${pageUtils.currPage>1 }">
			<a href="javascript:void(0)" class="b65" onclick="submitForm(${pageUtils.currPage-1})"></a>&nbsp;&nbsp;
		</c:if>
		<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
			<a href="javascript:void(0)" class="b65" onclick="submitForm(${pageUtils.currPage+1})">下一页</a>
		</c:if>
	</div>
</form>
</div>
</body>
</html>