<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>网页</title>
	<link rel="stylesheet" type="text/css" href="info/CSS/info_update.css">
	<script type="text/javascript" src="info/js/info-update.js" ></script>
	<script type="text/javascript">
		function updateImg() {
			document.getElementById("img").click();
		}
		/* function getSrc() {
			var src=document.getElementById("img").value;	
		} */
	</script>
	<c:if test="${not empty passerror }">
	<script type="text/javascript">
		confirm('${passerror }');
	</script>
	</c:if>
	<c:if test="${not empty updateerror }">
	<script type="text/javascript">
		confirm('${updateerror }');
	</script>
	</c:if>
</head>
<body>
<div class="body">
	<div class="body_font">
		<span>当前位置&nbsp;:&nbsp;我的信息&nbsp;>&nbsp;修改信息</span>
	</div>
	<form action="fontUser?method=fontReInfo" method="post" enctype="multipart/form-data" target="_top">
	<div class="body_middle">
		<div class="pic"  onmouseover="dis();" onmouseout="dis1();">
				<img src="${loginUser.photo}" style="width: 124px;height: 124px">
				<div class="change" id="none" style="visibility: hidden">
					<li><a href="javaScript:void(0)" onclick="updateImg()">更&nbsp;改&nbsp;头&nbsp;像</a></li>
					<input type="file" id="img" name="img" style="display: none;">
					<input type="hidden" name="userId" value="${loginUser.id }">
				</div>
		</div>
		<div class="fom">
			
				<div class="namep">
					<div class="name"><li>姓名</li>	</div>
					<input class="inp" type="text" name="userName" value="${loginUser.name }"></input>
					<input type="hidden" name="photo" value="${loginUser.photo }">
				</div>
				<div class="namep1">
					<div class="name"><li>密码</li>	</div>
					<input class="inp" type="password" name="pass" value="${loginUser.pass }" id="imp2"></input>
				</div>
				<div class="namep1">
					<div class="name"><li>确认密码</li>	</div>
					<input class="inp" type="password" name="repass" value="${loginUser.pass }" id="imp2"></input>
				</div>
				<div class="smt" style="margin-top: 50px">
					<input type="submit" value="保存"></input>
				</div>
				
			
		</div>
	</div>
	</form>	

</div>

	
</body>
</html>