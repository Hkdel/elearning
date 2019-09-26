<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>交友</title>
		<link rel="stylesheet" type="text/css" href="friend/css/friend.css" />
		<script type="text/javascript" src="friend/js/friend.js"></script>
	</head>
	<body>
		<div class="div-father">
			<div class="div-left">
				<ul>
					<li style="border: 1px solid #65a2fd; ">
						<a href="javascript:void(0)" onclick="showdiv(0)" style="color:#65a2fd; ">未读消息</a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="showdiv(1)">好友申请</a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="showdiv(2)">我的好友</a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="showdiv(3)">黑名单</a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="showdiv(4)">添加好友</a>
					</li>
				</ul>
			</div>
			<div class="div-right">
				<iframe name="ifr" id="ifr" style="border: none;" onload="this.height=ifr.document.body.scrollHeight" width="100%"  scrolling="no" src="friend/frontFriend?method=unReadList"></iframe>
			</div>
		</div>
	</body>

</html>