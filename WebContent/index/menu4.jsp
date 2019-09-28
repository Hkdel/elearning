<%@page import="com.zt.friends.dao.ChatMsgDao"%>
<%@page import="com.zt.friends.dao.impl.ChatMsgDaoImpl"%>
<%@page import="com.zt.user.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<%
	User loginUser=(User)session.getAttribute("loginUser");
	if(loginUser!=null){
		int loginId=loginUser.getId();
		ChatMsgDao chatDao=new ChatMsgDaoImpl();
		int count=chatDao.getCountByFriendId(loginId);
		pageContext.setAttribute("count", count);
	}
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>交友</title>
		<link rel="stylesheet" type="text/css" href="friend/css/friend.css" />
		<style type="text/css">
		
        .red-point{
          position: relative;
        }

        .red-point::before{
          content: "";
          border: 3px solid red;/*设置红色*/
          border-radius:3px;/*设置圆角*/
          position: absolute;
          z-index: 1000;
          right: 0;
          margin-right: -8px;
          font-size: 2px;
          
        }
.circle{
	width: 20px;
	height: 20px;
	border: 1px solid red;
	border-radius:50%;
	background-color: #ff0000;
}
		</style>
		<script type="text/javascript" src="friend/js/friend.js"></script>
		<script type="text/javascript">
		int c=document.getElementById('circle').innerHTML;
		if (c==0) {
			document.getElementById('circle').style.backgroundColor = "none";
			document.getElementById('circle').innerHTML="";
		} else {
			document.getElementById('circle').style.backgroundColor = "#FF0000";
		}
		
		</script>
	</head>
	<body>
		<div class="div-father">
			<div class="div-left">
				<ul>
					<li style="border: 1px solid #65a2fd; ">
						<a href="javascript:void(0)" onclick="showdiv(0)"
						style="color:#65a2fd;  ">未读消息
						<c:if test="${count!=0}">
						<span style="color: #ffffff;" class="circle">${count}</span>
						</c:if> 
						</a>
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