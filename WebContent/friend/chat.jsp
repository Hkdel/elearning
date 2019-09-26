<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天</title>
<link rel="stylesheet" type="text/css" href="friend/css/friend.css" />
<style type="text/css">
.hidden{
	/* display: none; */
	width: 680px;
	height: 460px;
	position: absolute;
	top: 20px;
	left: 60px;
	border:1px solid #65a2fd;
	border-radius: 10px;
	background: #ffffff;
	background-color: #65a2fd;
}
/* 设置滚动条的高宽 */

.chat::-webkit-scrollbar{
	width:10px;
	height: 10px;
}
/* 滚动条里面的滑块 */
.chat::-webkit-scrollbar-thumb{
	border-radius: 20px;/*圆角滑块*/
	-webkit-box-shadow:insert 0 0 5px rgba(0,0,0,0.2);
	background: rgba(0,0,0,0.2);
}

/* 滚动条里面的轨道 */
.chat::-webkit-scrollbar-track{
	border-radius: 10px;/*圆角滑块*/
	-webkit-box-shadow:insert 0 0 5px rgba(0,0,0,0.2);
	background: rgba(0,0,0,0.1);
}
.chatContent{
	color:#333333;
	font-size: 14px;
	margin-left: 5px;
	word-wrap:break-word;
}
</style>
</head>
<body>
<!-- 隐藏 -->
	<form action="friend/frontFriend?method=sendContent" method="post">
		<input type="hidden" name="toId" value="${toId }" />
		<div id="hidden" class="hidden">
			<div style="width: 680px;height: 40px;border:1px solid #65a2fd;border-radius: 10px;">
				<a href="friend/frontFriend?method=myFriendList" style="position: absolute;top: 8px;left: 640px;font-size: 14px;color: #ffffff" >返回</a>
				<span style="color: #ffffff;font-size: 16px;">&nbsp;&nbsp;聊天中</span>
			</div>
			<!-- 聊天内容 -->
			<div id="chatDiv" class="chat" style="width: 680px;height: 300px;background-color: #ffffff;overflow-x:hidden;overflow-y:auto;">
				<div  style="width: 680px;height: auto;">
					<c:forEach items="${chatMsgList }" var="chatMsg">
						<div style="width: auto;height: auto;max-width: 280px;">
							<c:if test="${chatMsg.fromUser.id!=loginUser.id }">
								<p style="color:#009bdb;font-size: 14px; ">
								${chatMsg.fromUser.name }
								${chatMsg.sendTime }
								</p>
								<div class="chatContent">
								<img src="${chatMsg.fromUser.photo } " style="width: 27px; height: 27px;"/>
								${chatMsg.content }
								</div>
							</c:if>
							<c:if test="${chatMsg.fromUser.id==loginUser.id }">
								<p style="color:#48ea6e;font-size: 14px; ">
								我
								${chatMsg.sendTime }
								</p>
								<div class="chatContent">${chatMsg.content }</div>
							</c:if>
						</div>
					</c:forEach>
					<br>
				</div>
			</div>
			<!--发送内容  -->
			<div class="sendContent" style="width: 680px;height: 118px;background: #ffffbb;border-radius: 10px;">
				<div class="content" style="width: 680px;height: 88px;">
					<textarea style="width:680px;height:80px;background: #ffffbb;border: 0;outline: none;" placeholder="请输入..." name="content"></textarea>
				</div>
				<div class="send" style="width: 680px;height: 30px;">
					<input type="submit" value="发送" class="add-submit" style="width: 60px;height: 24px;margin-left: 600px;font-size: 10px;"/>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		document.getElementById("chatDiv").scrollTop = document.getElementById("chatDiv").scrollHeight;
	</script>
</body>
</html>