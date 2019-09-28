<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>在线学习管理系统后台登录</title>
<link rel="stylesheet" type="text/css" href="css/login-style.css" />
<link rel="stylesheet" type="text/css" href="css/login-body.css"/> 
<c:if test="${not empty reLogin }">
<script type="text/javascript">
			confirm('${reLogin}');
</script>
</c:if>
<script type="text/javascript">
	     function refresh(){
	       var img=document.getElementById("imgObj");
	       img.src="verifyCode?id="+Math.random();
	     }
	 	</script>
</head>
<body>
<div class="container">
	<section id="content">
		<form action="admin/userLogin?method=login" method="post">
			<h1>后台登录</h1>
			<div>
				<input type="text" placeholder="账号" id="username" name="accountName" />
			</div>
			<div>
				<input type="password" placeholder="密码" id="password" name="pass"/>
			</div>
			<div>
				<input type="text" placeholder="验证码" id="checkcode"  name="code"/>
				<img  id="imgObj" src="verifyCode" onclick="refresh()" title="看不清换一张"/>
			</div>
			<div>
				<input type="submit" value="登录" class="btn btn-primary" id="js-btn-login"/>
				<input type="button" value="返回" class="btn btn-primary" id="js-btn-login"/>
			</div>
		</form>
		<p><span style="color:#ff0000">${codeerror }${error }</span></p>
		 <div class="button">
			<h3>在线学习管理系统后台登录</h3>	
		</div> 
	</section>
</div>
</body>
</html>