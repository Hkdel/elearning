<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.*,com.zt.user.po.Role,com.zt.user.dao.RoleDao,com.zt.user.dao.impl.RoleDaoImpl"%>
<%
java.util.Date date = new Date(); //获取当前时间
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化时间 
String createTime = sdf.format(date);
pageContext.setAttribute("createTime", createTime);
%>
<!DOCTYPE html>

<html>

	<head>
		<meta charset="utf-8" />
		<title>注册页面</title>
		<link type="text/css" href="login/css/login.css" rel="stylesheet" />
		<script type="text/javascript" src="login/js/createCode.js"></script>
		<script type="text/javascript">
			function submitForm(){
				document.getElementById("submitForm").submit();
			}
		</script>
		<c:if test="${not empty regerror}">
		<script type="text/javascript">
			confirm('${regerror}');
		</script>
		</c:if>
	</head>

	<body>
		<div class="dv">
			<div class="dv_login">
			<form action="fontUser?method=fontReg" method="post" id="submitForm" enctype="multipart/form-data">
				<div class="login_top">注册</div>
				<div class="login_other" >
					<div class="smallinp" >
						<div class="smallinp_text">
						姓名
						</div>
						<div class="smallinp_in">
						<input type="text" name="userName" />
						<span class="red_star">*</span>
						</div>
					</div>
					<div class="smallinp">
						<div class="smallinp_text">
						账号
						</div>
						<div class="smallinp_in">
						<input type="text" name="accountName" />
						<span class="red_star">*</span>
						</div>
						
					</div>
					<div class="smallinp">
						<div class="smallinp_text">
						密码
						</div>
						<div class="smallinp_in">
						<input type="password" name="pass"/>
						<span class="red_star">*</span>
						</div>
					</div>
					<div class="smallinp" >
						<div class="smallinp_text" style="letter-spacing: 0px;">
						确认密码
						</div>
						<div class="smallinp_in">
						<input type="password" name="repass"/>
						<span class="red_star">*</span>
						<span style="color: red;">${passerror }</span>
						</div>
					</div>
					<div class="smallinp">
						<div class="smallinp_text">
						性别
						</div>
						<div class="smallinp_in">
						<select name="sex">
								<option value="-1">请选择</option>
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
							<span class="red_star">*</span>
						</div>
					</div>
					
					<div class="smallinp">
						<div class="smallinp_text">
						生日
						</div>
						<div class="smallinp_in">
						<input type="text" name="birthday"/>
							<span class="red_star">*</span>
						</div>
					</div>
					
					
					<div class="smallinp">
						<div class="smallinp_text">
						头像
						</div>
						<div class="smallinp_in">
						<input type="file" name="photo" />
							
						</div>
					
						<input type="hidden" value="学生" name="roleName" readonly />
						<input type="hidden" value="0" name="bbsScore" readonly />
						<input type="hidden" value="0" name="examScore" readonly />
						<input type="hidden" name="createTime" value="${createTime }" readonly />
					</div>
					<div class="bottom" onclick="submitForm()" style="margin-top: 20px">立即注册</div>
					<div class="back_index">
						<a href="index.jsp">返回首页</a>
					</div>
				</div>
			</form>
			</div>

		</div>

	</body>

</html>