<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>在线学习管理系统后台登录用户密码修改</title>
<link rel="stylesheet" type="text/css" href="css/login-style.css" />
<link rel="stylesheet" type="text/css" href="css/login-body.css"/> 
<c:if test="${not empty reLogin }">
<script type="text/javascript">
			confirm('${reLogin}');
</script>
</c:if>
</head>
<body>
<div class="container">
	<section id="content">
		<form action="admin/user?method=loginUserRePass" method="post">
			<h1>后台登录用户密码修改</h1>
			<div>
				<input type="text" readonly="readonly" placeholder="姓名" id="username" name="name" value="${loginSysUser.name }" />
				<input type="hidden" name="userId" value="${loginSysUser.id }"/>
			</div>
			<div>
				<input type="text" readonly="readonly" placeholder="账号" id="username" name="accountName" value="${loginSysUser.accountName }"/>
			</div>
			<div>
				<input type="password" placeholder="密码" id="password"  name="newPass" value="${loginSysUser.pass }"/>
			</div>
			<div>
				<input type="password" placeholder="确认密码" id="password" name="reNewPass" value="${loginSysUser.pass }"/>
			</div>
			<div>
				<input type="submit" value="修改" class="btn btn-primary" id="js-btn-login" style="margin-left: 120px"/>
				<!-- <input type="button" value="返回" class="btn btn-primary" id="js-btn-login" /> -->
				<a href="main.jsp" style="float: left;color: blue;font-size: 15px">返回</a> 
			</div>
			<div style="text-align: center;margin: 0 auto;"><span style="color: red;font-size: 13px;margin: 0 auto;float: left;margin-left: 30px">${reseterror }</span></div>
		</form>
		 <div class="button">
			<h3>在线学习管理系统后台登录用户密码修改</h3>	
		</div> 
	</section>
</div>
</body>
</html>