<%@page import="com.zt.user.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学习_共享资源</title>
<link rel="stylesheet" href="study/css/share.css"/>
<link rel="stylesheet" href="study/css/study.css" />
	<style type="text/css">
.aa{
	margin-top:10px;
	color: #333333;
	font-size: 18px;
	margin-top: 20px;
	margin-left: 10px;
}
.radius_box{
	border-radius: 2px;
	height: 24px;
	border: 1px solid #65a2fd;
	margin-top: 20px;
}
.cc{
	width: 68px;
	height: 28px;
	background-color: #65a2fd;
	color: white;
	font-size: 16px;
	
	border-radius: 5px;
}
.five{
	width: 600px;
	height: 30px;
	font-size: 12px;
	color: #808080;
}
.five2{
	width: 600px;
	height: 30px;
	font-size: 14px;
	color: #333333;
}
.w1{
	width: 50px;
}
.w2{
	width: 100px;
}
.w3{
	width: 50px;
}
.w4{
	width: 180px;
}
.w5{
	width: 100px;
}
.w6{
	width: 120px;
}
.col{
	color: #3ce650;
}
.s{
	width: 570px;
	height: 0.1px;
	border: 1px solid #eeeeee;
	background-color:#eeeeee;
}
.second{
	width:600px;
	height:476px;
	border: 2px solid #65a2fd;
	
	border-radius: 6px;
}
.second_seven{
	width: 600px;
	height: 60px;
	position: absolute;
	top: 560px;
}

.second_va{
	position: absolute;
	left: 165px;
	top: 22px;
	font-size: 12px;
	color: #333333;
}

.second_vb{
	position: absolute;
	left: 390px;
	top: 22px;
	font-size: 12px;
	color: #65a2fd;
	cursor: pointer;
}

.second_vc{
	position: absolute;
	left: 445px;
	top: 22px;
	font-size: 12px;
	color: #65a2fd;
	cursor: pointer;
}

.second_vd{
	position: absolute;
	left: 490px;
	top: 22px;
	font-size: 12px;
	color: #65a2fd;
	cursor: pointer;
}

.second_ve{
	position: absolute;
	left: 545px;
	top: 22px;
	font-size: 12px;
	color: #65a2fd;
	cursor: pointer;
}

/*隐藏  */
.hidden{
	width: 400px;
	height: 280px;
	/* border: 1px solid #ff0000; */
	z-index: 999;
	position: absolute;
	left: 18%;
	top: 5%;
	background: #ffffff;
	box-shadow: -2px 20px 100px -20px #aaaabb;
	
}
.border{
	border-radius: 8px;
}
.close{
	position: absolute;
	left: 375px;
	top: 15px;
}
.file{
	position: absolute;
	left: 65px;
	top: 218px;
	z-index: 10;
}
.input{
	position: absolute;
	left: 65px;
	top: 218px;
	z-index: 20;
}

a{
color: #65a2fd;
}
a:HOVER {
	color: #65a2fd;
}
	</style>
<script type="text/javascript">
	function submitForm(page){
	document.getElementById("page").value=page;
	document.getElementById("resForm").submit();
}
	function openShare() {
		document.getElementById("hidden").style.display="block";
	}
	function closeShare() {
		document.getElementById("hidden").style.display="none";
	}
</script>
</head>

	
<body style="background-color:#FFF;">
	<div id="main">
    	<div id="main_main">
    		<!--隐藏  -->
            <div id="hidden" class="hidden border" style="display: none;">
            	<a href="javascript:closeShare()" class="close"><img src="study/img/close.png"/></a>
            	<div class="left-top-xue" v-bind:class="{'display':flag1}" style="width: 400px;"><p style="margin-top:16px;color: #ffffff;text-align: left;margin-left: 5px;">分享资源<p></div>
            	
				<form action="study/frontStudy?method=addRes" method="post"  enctype="multipart/form-data">
				
				<table style="margin-left: 10px;">
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333;height: 20px;">类型：</td>
						<td style="height: 20px;">
							<select name="typeId"  class="inp2 select" style="color:#aaa;border-radius: 3px;">
								<option value="0" style="display: none;" disabled selected>请选择</option>
								<c:forEach items="${typeList }" var="type">
									<option value="${type.id }" <c:if test="${type.id==filter['typeId']}">selected="selected"</c:if>>${type.typeName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333">名称：</td>
						<td><input class="inp2" type="text" style="color:#aaa;border-radius: 3px;width: 260px;" placeholder="请输入名称" name="name"/></td>
					</tr>
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333">描述：</td>
						<td><textarea class="inp2" style="color:#aaaaaa;resize: none;height: 68px;border-radius: 3px;" placeholder="请输入描述" name="describe"></textarea></td>
					</tr>
					<tr style="height: 6px;"></tr>
					<tr>
						<td style="color: #333333">资源：</td>
						<td><a href="javaScript(0)" class="file">选择文件</a><input type="file" name="url"  style="width: 70px;opacity: 0;" class="input"/></td>
					</tr>
					<tr style="height: 10px;">
						<td></td>
						<td><input class="inp4 radius_box" type="submit" value="确认" style="margin-top: 5px;border-radius: 5px;margin-left: 275px;"/></td>
					</tr>
				</table>
            	</form>
            </div>
    	
        	<div id="main_left">
            	<div id="main_left_top">
                	<div class="xuexishouji" onclick="location.href='study/frontStudy?method=noteList'"><!-- ../index/menu1.jsp -->
                    	<p>学习手记</p>
                    </div>
                    <div class="xuexishouji" style="background-color:#64a3fd">
                    	<p style="color:white">共享资源</p>
                    </div>
                </div>
                <div id="main_left_middle">
                	<div id="main_left_middle_left">
                    	资源共享,互帮互助!
                    </div>
                    <div id="main_left_middle_right">
                    	<a href="javascript:openShare()"><p>分享资料</p></a>
                    	
                    </div>
                </div>
                <div id="main_left_bottom">
                	<!--<div id="main_left_bottom_1">
                    	<div class="shouji">
                        	
                        </div>
                    </div>-->
                    <!-- <iframe width="604px" height="480px" frameborder="no" src="study/res.jsp"></iframe> -->
                    <div class="second">
	<form action="study/frontStudy?method=resList" method="post"  id="resForm">
        <input type="hidden" name="page" id="page"/>
		
		<span class="aa">文件名 ：</span>
		<input class="radius_box" type="text" name="name" value="${filter['name'] }"/>
		
		<span class="aa">上传人 ：</span>
			<select name="uploadId"  class="radius_box" >
					<option value="0">全部</option>
					<c:forEach items="${userList }" var="user">
						<option value="${user.id }" <c:if test="${user.id==filter['uploadId']}">selected="selected"</c:if>>${user.name }</option>
					</c:forEach>
			</select>
		<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->	 
		<span class="aa">资源类型：</span>
			<select name="typeId"  class="radius_box" >
					<option value="0">全部</option>
					<c:forEach items="${typeList }" var="type">
						<option value="${type.id }" <c:if test="${type.id==filter['typeId']}">selected="selected"</c:if>>${type.typeName }</option>
					</c:forEach>
			</select>
		<span class="aa">时&nbsp;&nbsp;&nbsp;&nbsp;间 ：</span>
			<input class="radius_box" type="date" name="beginTime" value="${filter['begin'] }">
			-
			<input class="radius_box" type="date" name="endTime" value="${filter['end'] }"/>
			<input class="cc" type="reset" value="重置" style="margin-left: 24px;"/>
			<input class="cc" type="submit" value="查询"  style="margin-left: 20px;"/>
		</form>
		<table style="margin-left: 10px;margin-top: 20px;">
			<tr class="five">
				<td class="w1">序号</td>
				<td class="w2">文件名</td>
				<td class="w3">类别</td>
				<td class="w4">描述</td>
				<td class="w5">上传日期</td>
				<td class="w6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作</td>
			</tr>
			<c:forEach items="${resList }" var="res" varStatus="i">
			
			<tr class="five2">
				<td class="w1">&nbsp;&nbsp;${i.count }</td>
				<td class="w2">
				<c:choose>
					<c:when test="${fn:length(res.name)>6 }">
					${fn:substring(res.name,0,6)}...
					</c:when>
					<c:otherwise>
					${res.name }
					</c:otherwise>
				</c:choose>
				</td>
				<td class="w3">${res.type.typeName }</td>
				<td class="w4">
				<c:choose>
					<c:when test="${fn:length(res.describe)>9 }">
					${fn:substring(res.describe,0,9)}...
					</c:when>
					<c:otherwise>
					${res.describe }
					</c:otherwise>
				</c:choose>
				
				
				</td>
				<td class="w5">${res.uploadTime}</td>
				<td class="w6 col">
					<a href="${res.url }" class="col">下载</a>
					<c:if test="${res.uploadUser.id==loginUser.id }">
						<a class="a" href="study/frontStudy?method=delRes&id=${res.id}"  onclick="return confirm('确定删除吗？')">删除</a>
					</c:if>
					<c:if test="${res.checkStatus==1}">
						<a href="study/frontStudy?method=showRes&id=${res.id}" style="color: #808080">详情</a>
					</c:if>
					<c:if test="${res.checkStatus==0 }">
						<span class="a1">待审</span>
					</c:if>
					<c:if test="${res.checkStatus==2 }">
						<a style="color: blue" href="study/frontStudy?method=editRes&id=${res.id}">驳回</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="6" class="s"></td>
			</tr>
			</c:forEach>
			
			
		</table>
		<div class="second_seven">
			<span class="second_va">当前第${pageUtils.currPage}页，每页${pageUtils.pageSize }条数据&nbsp;&nbsp;&nbsp;&nbsp;共${pageUtils.totalPage }页</span>
			
			<span class="second_vb" onclick="submitForm(1)">首页</span>
			<span class="second_vc" onclick="submitForm(${pageUtils.totalPage })">尾页</span>
			
			<c:if test="${pageUtils.currPage>1 }">
				<span class="second_vd" onclick="submitForm(${pageUtils.currPage-1})">上一页</span>
			</c:if>
			<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
				<span class="second_ve" onclick="submitForm(${pageUtils.currPage+1})">下一页</span>
			</c:if>
		</div>
	</div>
                </div>
            </div>
            <div id="main_right">
            	<img src="study/img/sj.png" width="360" height="600"/>
            </div>
            
            
        </div>
    </div>
</body>
</html>
