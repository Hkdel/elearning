<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"  %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>左边菜单</title>
<link href="css/style-1.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>

</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>在线学习管理系统</div>
     <dl class="leftmenu"> 
		<dd>
			<div class="title">
				<span><img src="images/leftico01.png" /></span>考试管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="admin/exam/sysExam?method=subList" target="rightFrame">科目管理</a></li>
				<li><cite></cite><a href="admin/exam/sysExam?method=typeList" target="rightFrame">题型管理</a></li>
				<li><cite></cite><a href="admin/exam/sysExam?method=quesList" target="rightFrame">题目管理</a></li>
				<li><cite></cite><a href="admin/exam/sysExam?method=paperList" target="rightFrame">试卷管理</a></li>
				<li><cite></cite><a href="admin/exam/sysExam?method=correctList" target="rightFrame">试卷批改</a></li>
				<li><cite></cite><a href="admin/exam/sysExam?method=recordList" target="rightFrame">考试记录管理</a></li>
			</ul>    
		</dd>
		<dd>
			<div class="title">
				<span><img src="images/leftico01.png" /></span>论坛管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="bbs/edition/editionList.html" target="rightFrame">版块管理</a></li>
				<li><cite></cite><a href="bbs/post/postList.html" target="rightFrame">帖子管理</a></li>
			</ul>    
		</dd>
		<dd>
			<div class="title">
				<span><img src="images/leftico01.png" /></span>资源管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="study/sourceType/sourceTypeList.html" target="rightFrame">类型管理</a></li>
				<li><cite></cite><a href="study/source/sourceList.html" target="rightFrame">资源审核</a></li>
			</ul>    
		</dd>
		<dd>
			<div class="title">
				<span><img src="images/leftico01.png"/></span>系统管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="system/user/userList.html" target="rightFrame">用户管理</a></li>
				<li><cite></cite><a href="system/role/roleList.html" target="rightFrame">角色管理</a></li>
				<li><cite></cite><a href="system/model/modelList.html" target="rightFrame">模块管理</a></li>
				<li><cite></cite><a href="system/img/imgList.html" target="rightFrame">首页图片管理</a></li>
			</ul>    
		</dd>
    </dl>
</body>
</html>
