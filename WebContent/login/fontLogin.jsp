<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"%>
<!DOCTYPE html>

<html>

	<head>
		<meta charset="utf-8" />
		<title>登录页面</title>
		<link type="text/css" href="login/css/login.css" rel="stylesheet" />
		<script type="text/javascript" src="login/js/createCode.js"></script>
		<script type="text/javascript">
			function submitForm(){
				document.getElementById("submitForm").submit();
			}
		</script>
		<c:if test="${not empty regSuccess}">
		<script type="text/javascript">
			confirm('${regSuccess}');
		</script>
		</c:if>
		<c:if test="${not empty updateSuccess}">
		<script type="text/javascript">
			confirm('${updateSuccess}');
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
		<div class="dv">
			<div class="dv_login">
			<form action="fontUser?method=fontLogin" target="_top" method="post" id="submitForm">
				<div class="login_top">登录<span style="color:red;font-size: 14px" >${msg}</span></div>
				<div class="login_other">
					<div class="inp">
						<div class="inp_text">账号</div>
						<div class="inp_in">
							<input type="text" name="accountName" />
						</div>
						${usererror }
					</div>
					<div class="inp marTop30">
						<div class="inp_text">密码</div>
						<div class="inp_in">
							<input type="password" name="pass"/>
						</div>
						${passerror }
					</div>
					<div class="code marTop30">
						<div class="code_left">
							<div class="code_text">验证码</div>
							<div class="code_in">
								<input type="text" name="code" />
							</div>
						</div>
						<div >
							<img  id="imgObj" src="verifyCode" onclick="refresh()" title="看不清换一张"/>
							<p><span style="color:#ff0000">${codeerror }</span></p>
						</div>

					</div>
					<div class="bottom" onclick="submitForm()" style="margin-top: 20px">立即登录</div>
					<div style="margin-top: 15px;text-align: center;"><a href="login/fontReg.jsp" style="color: blue">立即注册</a></div>
					<div class="back_index">
						<a href="index.jsp">返回首页</a>
					</div>
				</div>
			</form>
			</div>

		</div>

	</body>

</html>