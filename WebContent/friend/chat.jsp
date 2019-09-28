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
.gif{
	display:none;
	width: 470px;
	height: 226px;
	position: absolute;
	left: 2px;
	top: 200px;
	/* border: 1px solid #ff0000; */
	background: #ffffee;
	box-shadow: 15px 5px 30px  #aaaaaa;
}
.img{
	width: 24px;
	height: 24px;
	margin: 5px;
}

</style>
<script type="text/javascript">
function getMouse(){
	document.getElementById("gif").style.display="block";
	
}
function missMouse(){
	document.getElementById("gif").style.display="none";
}

</script>
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
			<div id="chat" class="chat" style="width: 680px;height: 300px;background-color: #ffffff;overflow-x:hidden;overflow-y:auto;">
				<div  style="width: 680px;height: auto;">
					<c:forEach items="${chatMsgList }" var="chatMsg">
						<c:if test="${chatMsg.fromUser.id!=loginUser.id }">
							<div style="width: 380px;height: auto;max-width: 380px;float: left;">
								<p style="color:#009bdb;font-size: 14px; ">
								${chatMsg.fromUser.name }
								${chatMsg.sendTime }
								</p>
								<div class="chatContent">
									<div style="width: 30px;float: left;">
										<img src="${chatMsg.fromUser.photo } " style="width: 27px; height: 27px;"/>
									</div>
									${chatMsg.content }
								</div>
							</div>
						</c:if>
						<c:if test="${chatMsg.fromUser.id==loginUser.id }">
							<div style="width: 380px;height: auto;max-width: 380px;float: right;margin-right: 20px;">
								<p style="color:#48ea6e;font-size: 14px;width: 380px;text-align: right; ">
								我
								${chatMsg.sendTime }
								</p>
								<div class="chatContent" style="width: 380px;text-align: right;">
									<div style="width: 350px;float: left;">
									${chatMsg.content }
									</div>
									
									<div style="width: 30px;float: right;">
										<img src="${chatMsg.fromUser.photo } " style="width: 27px; height: 27px;"/>
									</div>
									
								</div>
							</div>
						</c:if>
						
						<%-- <div style="width: auto;height: auto;max-width: 380px;">
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
								<div class="chatContent">
									<img src="${chatMsg.fromUser.photo } " style="width: 27px; height: 27px;"/>
									${chatMsg.content }
								</div>
							</c:if>
						</div> --%>
					</c:forEach>
					<br><br>
				</div>
			</div>
			<!--发送内容  -->
			<div class="sendContent" style="width: 680px;height: 118px;background: #ffffbb;border-radius: 10px;">
				<div class="content" style="width: 680px;height: 88px;">
					<textarea style="width:680px;height:80px;background: #ffffbb;border: 0;outline: none;" placeholder="请输入..." name="content"></textarea>
				</div>
				<div class="send" style="width: 680px;height: 30px;">
					<div style="width: 500px;height: 25px;float: left;">
						<div id="gif" class="gif"  onmouseover="getMouse()" onmouseout="missMouse()">
							<img class="img" src="index/layui/images/face/1.gif">
							<img class="img" src="index/layui/images/face/2.gif">
							<img class="img" src="index/layui/images/face/3.gif">
							<img class="img" src="index/layui/images/face/4.gif">
							<img class="img" src="index/layui/images/face/5.gif">
							<img class="img" src="index/layui/images/face/6.gif">
							<img class="img" src="index/layui/images/face/7.gif">
							<img class="img" src="index/layui/images/face/8.gif">
							<img class="img" src="index/layui/images/face/9.gif">
							<img class="img" src="index/layui/images/face/10.gif">
							<img class="img" src="index/layui/images/face/11.gif">
							<img class="img" src="index/layui/images/face/12.gif">
							<img class="img" src="index/layui/images/face/13.gif">
							<img class="img" src="index/layui/images/face/14.gif">
							<img class="img" src="index/layui/images/face/15.gif">
							<img class="img" src="index/layui/images/face/16.gif">
							<img class="img" src="index/layui/images/face/17.gif">
							<img class="img" src="index/layui/images/face/18.gif">
							<img class="img" src="index/layui/images/face/19.gif">
							<img class="img" src="index/layui/images/face/20.gif">
							<img class="img" src="index/layui/images/face/21.gif">
							<img class="img" src="index/layui/images/face/22.gif">
							<img class="img" src="index/layui/images/face/23.gif">
							<img class="img" src="index/layui/images/face/24.gif">
							<img class="img" src="index/layui/images/face/25.gif">
							<img class="img" src="index/layui/images/face/26.gif">
							<img class="img" src="index/layui/images/face/27.gif">
							<img class="img" src="index/layui/images/face/28.gif">
							<img class="img" src="index/layui/images/face/29.gif">
							<img class="img" src="index/layui/images/face/30.gif">
							<img class="img" src="index/layui/images/face/31.gif">
							<img class="img" src="index/layui/images/face/32.gif">
							<img class="img" src="index/layui/images/face/33.gif">
							<img class="img" src="index/layui/images/face/34.gif">
							<img class="img" src="index/layui/images/face/35.gif">
							<img class="img" src="index/layui/images/face/36.gif">
							<img class="img" src="index/layui/images/face/37.gif">
							<img class="img" src="index/layui/images/face/38.gif">
							<img class="img" src="index/layui/images/face/39.gif">
							<img class="img" src="index/layui/images/face/40.gif">
							<img class="img" src="index/layui/images/face/41.gif">
							<img class="img" src="index/layui/images/face/42.gif">
							<img class="img" src="index/layui/images/face/43.gif">
							<img class="img" src="index/layui/images/face/44.gif">
							<img class="img" src="index/layui/images/face/45.gif">
							<img class="img" src="index/layui/images/face/46.gif">
							<img class="img" src="index/layui/images/face/47.gif">
							<img class="img" src="index/layui/images/face/48.gif">
							<img class="img" src="index/layui/images/face/49.gif">
							<img class="img" src="index/layui/images/face/50.gif">
							<img class="img" src="index/layui/images/face/51.gif">
							<img class="img" src="index/layui/images/face/52.gif">
							<img class="img" src="index/layui/images/face/53.gif">
							<img class="img" src="index/layui/images/face/54.gif">
							<img class="img" src="index/layui/images/face/55.gif">
							<img class="img" src="index/layui/images/face/56.gif">
							<img class="img" src="index/layui/images/face/57.gif">
							<img class="img" src="index/layui/images/face/58.gif">
							<img class="img" src="index/layui/images/face/59.gif">
							<img class="img" src="index/layui/images/face/60.gif">
							<img class="img" src="index/layui/images/face/61.gif">
							<img class="img" src="index/layui/images/face/62.gif">
							<img class="img" src="index/layui/images/face/63.gif">
							<img class="img" src="index/layui/images/face/64.gif">
							<img class="img" src="index/layui/images/face/65.gif">
							<img class="img" src="index/layui/images/face/66.gif">
							<img class="img" src="index/layui/images/face/67.gif">
							<img class="img" src="index/layui/images/face/68.gif">
							<img class="img" src="index/layui/images/face/69.gif">
							<img class="img" src="index/layui/images/face/70.gif">
							<img class="img" src="index/layui/images/face/71.gif">
							
							
							
							
						</div>
						<img  onmouseover="getMouse()" onmouseout="missMouse()" title="选择表情" alt="选择图片" src="index/layui/images/face/1.gif" style="width: 24px;height: 24px;margin-left: 2px;">
						<img title="文件上传" alt="选择图片" src="index/layui/images/upload.png" style="width: 24px;height: 24px;margin-left: 5px;">
					</div>
					<div style="width: 100px;height: 25px;float: right;">
						<input type="submit" value="发送" class="add-submit" style="width: 60px;height: 24px;font-size: 10px;"/>
					</div>
					
				</div>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
document.getElementById('chat').scrollTop=document.getElementById('chat').scrollHeight;
</script>
</html>