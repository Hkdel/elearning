<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的好友</title>
<link rel="stylesheet" type="text/css" href="friend/css/friend.css" />
<script type="text/javascript">
function submitForm(page){
	document.getElementById("page").value=page;
	document.getElementById("friendForm").submit();
}
function subForm(){
	document.getElementById("addBlackFrom").submit();
	document.getElementById("delFriendFrom").submit();
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
			<td class="b80" id="td3">操作</td>
			<td class="b80"></td>
		</tr>
		<c:forEach items="${myFriendList }" var="myFriend">
			<tr class="tr1">
				<td class="b33"><img src="${myFriend.friendUser.photo }" style="width: 28px;height: 28px;"/></td>
				<td class="b33">${myFriend.friendUser.name }</td>
				<td class="b33">${myFriend.friendUser.sex }</td>
				<td class="b33">${myFriend.friendUser.accountName }</td>
				<td class="b33">
					<a href="friend/frontFriend?method=addBlack&toId=${myFriend.friendUser.id }" class="b65" style="color: #ffb463;">添加黑名单</a>
					<span class="span18"><a href="friend/frontFriend?method=delFriend&toId=${myFriend.friendUser.id }" class="b80 col2">删除好友</a></span>
				</td>
				<td>
					<form action="friend/frontFriend?method=findMsg" method="post">
						<input type="hidden" name="toId" value="${myFriend.friendUser.id }" />
						<input type="submit" value="发起聊天" class="add-submit" />
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<!-- <form action="friend/frontFriend?method=sendContent" method="post">
		<input type="hidden" name="friendId" id="friendId" />
		<div id="hidden" class="hidden">
			<div style="width: 479px;height: 20px;background-color: #65a2fd;border:1px solid #65a2fd;border-radius: 10px;">
				<a href="javascript:closeChat()" style="position: absolute;top: 5px;left: 460px;" ><img src="study/img/close.png"/></a>
				<span style="color: #ffffff;font-size: 14px;">&nbsp;&nbsp;聊天中</span>
			</div>
			聊天内容
			<div class="chat" style="width: 479px;height: 190px;overflow-x:hidden;overflow-y:auto;">
				<div  style="width: 479px;height: 1200px;" id="div">
					

				</div>
			</div>
			发送内容 
			<div class="sendContent" style="width: 479px;height: 88px;background: #ffffbb;border-radius: 10px;">
				<div class="content" style="width: 479px;height: 66px;">
					<textarea style="width:479px;height:66px;background: #ffffbb;border: 0;outline: none;" placeholder="请输入..." name="content"></textarea>
				</div>
				<div class="send" style="width: 479px;height: 20px;">
					<input type="submit" value="发送" class="add-submit" style="width: 48px;height: 16px;margin-left: 420px;font-size: 10px;"/>
				</div>
			</div>
		</div>
	</form> -->
<form action="friend/frontFriend?method=myFriendList" method="post" id="friendForm">
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