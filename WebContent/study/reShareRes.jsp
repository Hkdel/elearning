<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源详情</title>
<style type="text/css">
.inp2{
	width:260px;
	height:30px;
	border:#65a2fd 1px solid;
	}
.inp4{
	width:100px;
	background-color:#65a2fd;
	color: white;
	border-radius: 3px;
	margin-left: 300px;
}
.inp5{
	width:100px;
	border:#65a2fd 1px solid;
	background-color:#65a2fd;
	color: white;
	border-radius: 3px;
	margin-top: 15px;
}
.div2{
	width:400px;
	height:48px;
	font-size:16px;
	background-color:#65a2fd;
	text-align:center;
	border-radius: 5px;
	line-height:48px;color: #ffffff;
	}
.div1{
width:400px;
height:500px;
border:#65a2fd 1px solid;
border-radius: 5px;
}
a{
color: #65a2fd;
text-decoration: none;
}
a:HOVER {
	color: #65a2fd;
}
.file{
	position: absolute;
	left: 105px;
	top: 232px;
	z-index: 10;
}
.input{
	position: absolute;
	left: 105px;
	top: 232px;
	z-index: 20;
}
</style>
</head>
<body>
<div class="div1">
<div class="div2">资源详情</div>
		<form action="study/frontStudy?method=updateRes" method="post"  enctype="multipart/form-data">
				<input type="hidden" name="id" value="${res.id }"/>
				<table style="margin-left: 10px;">
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333;height: 20px;">类型：</td>
						<td style="height: 20px;">
							<select name="typeId"  class="inp2 select" style="color:#aaa;border-radius: 3px;">
								<option value="0" style="display: none;" disabled selected>请选择</option>
								<c:forEach items="${typeList }" var="type">
									<option value="${type.id }" <c:if test="${type.typeName==res.type.typeName }">selected="selected"</c:if>>${type.typeName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333">名称：</td>
						<td><input type="text" value="${res.name }" class="inp2"  placeholder="请输入名称" style="color:#aaa;border-radius: 3px;" name="name" /></td>
					</tr>
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333">描述：</td>
						<td><textarea class="inp2" style="color:#aaaaaa;resize: none;height: 68px;border-radius: 3px;"  placeholder="请输入描述" name="describe">${res.describe }</textarea></td>
					</tr>
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333">资源：</td>
						<td>
						<a href="javaScript(0)" class="file">选择文件</a>
						<input type="file" name="url"  style="width: 70px;opacity: 0;" class="input"/>
						<a href="${res.url }" style="margin-left: 80px;">查看资源</a>
						</td>
					</tr>
					<tr>
						<td style="color: #333333">上传人：</td>
						<td><input type="text" value="${res.uploadUser.name }" class="inp2" style="color:#aaa;border-radius: 3px;" readonly /></td>
					</tr>
					<tr>
						<td style="color: #333333">上传时间：</td>
						<td><input type="text" value="${res.uploadTime }" class="inp2" style="color:#aaa;border-radius: 3px;" readonly /></td>
					</tr>
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333">审核人：</td>
						<td><input type="text" value="${res.checkUser.name }" class="inp2" style="color:#aaa;border-radius: 3px;" readonly /></td>
					</tr>
					<tr>
						<td style="color: #333333">审核时间：</td>
						<td><input type="text" value="${res.checkTime }" class="inp2" style="color:#aaa;border-radius: 3px;" readonly /></td>
					</tr>
					<tr>
						<td style="color: #333333">审核意见：</td>
						<td><textarea class="inp2" style="color:#aaaaaa;height: 68px;border-radius: 3px;" readonly>${res.checkOpinion }</textarea>
						</td>
					</tr>
					<tr style="height: 10px;text-align: center;">
						<td colspan="2">
						<input type="submit" value="重新分享" class="inp5"/>
						<a href="study/frontStudy?method=resList">
						<input type="button" value="返&nbsp;&nbsp;回" class="inp5" style="margin-left: 20px;"/>
						</a></td>
					</tr>
					
				</table>
			</form>
</div>
</body>
</html>