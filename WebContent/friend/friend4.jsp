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
function openFriend(photo,id,name,accountName,sex) {
	document.getElementById("hidden").style.display="block";
	document.getElementById("p2").innerHTML=name;
	document.getElementById("p3").innerHTML=accountName;
	document.getElementById("p4").innerHTML=sex;
	document.getElementById("friendId").value=id;
	document.getElementById("img").src=photo;
}
function closeFriend() {
	document.getElementById("hidden").style.display="none";
}
</script>
</head>
<body>
<div style="height: 400px;width: 990px;">
	<table class="table1">
		<tr>
			<td class="b80" id="td1">头像</td>
			<td class="b80" id="td1">姓名</td>
			<td class="b80" id="td2">性别</td>
			<td class="b80" id="td3">账号</td>
			<td class="b80">处理</td>
		</tr>
		<c:forEach items="${notFriendList }" var="notFriend">
			<tr class="tr1">
				<td class="b33"><img src="${notFriend.photo }" style="width: 28px;height: 28px;"/></td>
				<td class="b33">${notFriend.name }</td>
				<td class="b33">${notFriend.sex }</td>
				<td class="b33">${notFriend.accountName }</td>
				<td>
					<a href="javascript:openFriend('${notFriend.photo }','${notFriend.id }','${notFriend.name }','${notFriend.accountName }','${notFriend.sex }')">
						<input type="button" value="添加好友" class="add-submit"/>
					</a>
					
				</td>
			</tr>
		</c:forEach>
	</table>
	<!-- 隐藏 -->
	<form action="friend/frontFriend?method=addFriend" method="post">
		<div id="hidden" style="display: none;width: 360px;height: 200px;position: absolute;top: 100px;left: 160px;border:1px solid #65a2fd;border-radius: 10px;background: #ffffff">
			<div style="width: 359px;height: 20px;background-color: #65a2fd;border:1px solid #65a2fd;border-radius: 10px;">
				<a href="javascript:closeFriend()" style="position: absolute;top: 5px;left: 340px;" ><img src="study/img/close.png"/></a>
				<span style="color: #ffffff;font-size: 14px;">&nbsp;&nbsp;添加好友</span>
			</div>
			<div style="width: 360px;height: 180px;">
				<div style="float: left;width: 100px;height: 180px;text-align: center;">
				<input type="hidden" name="friendId" id="friendId" />
					<p><img alt="头像" src="" style="width: 60px;height: 60px;" id="img"/></p>
					<div id="p2" style="color: #333333;"></div>
					<div id="p3" style="color: #333333;"></div>
					<div style="color: #333333;">性别：<span id="p4"></span></div>
				</div>
				<div style="float: right;width: 260px;height: 180px;">
					请输入附加信息：
					<textarea style="width:240px;height:100px; "placeholder="请输入..." name="withMsg"></textarea>
					<input type="submit" value="添加好友" class="add-submit" style="margin-left: 160px;margin-top: 20px;" />
				</div>
			</div>
		</div>
	</form>
<form action="friend/frontFriend?method=notFriendList" method="post" id="addFriendForm">
<input type="hidden" name="page" id="page">
	<div class="lastRow">
		<span>当前第${pageUtils.currPage}页&nbsp;&nbsp;每页${pageUtils.pageSize }条数据
		&nbsp;&nbsp;共${pageUtils.totalPage }页</span>
		<a href="javascript:void(0)" class="b65" onclick="submitForm(1)">首页</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="b65" onclick="submitForm(${pageUtils.totalPage })">尾页</a>&nbsp;&nbsp;
		<c:if test="${pageUtils.currPage>1 }">
			<a href="javascript:void(0)" class="b65" onclick="submitForm(${pageUtils.currPage-1})">上一页</a>&nbsp;&nbsp;
		</c:if>
		<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
			<a href="javascript:void(0)" class="b65" onclick="submitForm(${pageUtils.currPage+1})">下一页</a>
		</c:if>
	</div>
</form>
</div>
</body>
</html>