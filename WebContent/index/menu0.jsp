<%@page import="com.zt.bbs.po.BbsPost"%>
<%@page import="com.zt.bbs.dao.impl.BbsPostDaoImpl"%>
<%@page import="com.zt.bbs.dao.BbsPostDao"%>
<%@page import="com.zt.bbs.dao.impl.BbsPlateDaoImpl"%>
<%@page import="com.zt.bbs.dao.BbsPlateDao"%>
<%@page import="com.zt.bbs.po.BbsPlate"%>
<%@page import="com.zt.user.po.User"%>
<%@page import="com.zt.user.dao.impl.UserDaoImpl"%>
<%@page import="com.zt.user.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<%@ page import="com.zt.user.dao.ImgDao,com.zt.user.dao.impl.ImgDaoImpl,com.zt.user.po.Photo,java.util.*" %>
<%
	ImgDao imgDao = new ImgDaoImpl();
	List<Photo> photos =  imgDao.findFontPageImg();
	UserDao userDao = new UserDaoImpl();
	List<User> users = userDao.getScores();
	pageContext.setAttribute("photos", photos);
	pageContext.setAttribute("users", users);
	
	BbsPlateDao bbsPlateDao = new BbsPlateDaoImpl();
	List<BbsPlate> bbsPlateList = bbsPlateDao.findAll();
	pageContext.setAttribute("bbsPlateList", bbsPlateList);
	
	BbsPostDao bbsPostDao = new BbsPostDaoImpl();
	List<BbsPost> postHeatList = bbsPostDao.findHeat();
	pageContext.setAttribute("postHeatList", postHeatList);
	List<BbsPost> postGoodList = bbsPostDao.findGood();
	pageContext.setAttribute("postGoodList", postGoodList);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="show/css/show.css" />
<script>
	var index = 0;
	function showPic(a){
		clearInterval(time);
		var arr = document.getElementsByName("pic");
		var arr1 = document.getElementsByName("dian");
		for(var i = 0; i < arr1.length; i++){
			if(i == a){
				arr1[i].style.backgroundColor="#0d8fdd";
				arr[i].style.display="block";
			}else{
				arr[i].style.display = "none";
				arr1[i].style.backgroundColor = "";
			}
		}
		time = setInterval("picShow()", 1500);
	}
		
	
	function picShow(){
		var arr = document.getElementsByName("pic");
		var arr1 = document.getElementsByName("dian");
		for(var i = 0;i < arr1.length;i++){
			if(index == i){
				arr1[i].style.backgroundColor = "#0d8fdd";
				arr[i].style.display = "block"
			}else{
				arr1[i].style.backgroundColor = "";
				arr[i].style.display = "none"
			}
		}
		index++;
		if(index == 4){
			index = 0;	
		}
	}
	var time = setInterval("picShow()", 1500);
	

</script>

</head>
<body>
<div id="body">	
	<div id="nr">
		<!-- 模块选项+轮播 -->
		<div class="part1">
			<!-- 左边 -->
			<div class="part1_left">
				<div class="bg_img1">
                	<a href="#"><li>在线考试>></li></a>
				</div>
				<div class="bg_img2">
                	<a href="#"><li>成绩查询>></li></a>
				</div>
				<div class="bg_img3">
                	<a href="#"><li>在线校友>></li></a>
				</div>
				<div class="bg_img4">
                	<a href="#"><li>我的论坛>></li></a>
				</div>
				<div class="bg_img5">
                	<a href="#"><li>资源上传>></li></a>
				</div>
			</div>			
			<!-- 右边 -->
			<div class="part1_right">
				<!-- 轮播图片当背景插进DIV,链接写在DIV里面 -->
				<c:forEach items="${photos }" var="photo" varStatus="i">
				<c:if test="${photo.place == 1 }">
				<div name="pic"><img src="${photo.url }" style="width: 900px" height="300px"></img></div>
				</c:if>
				</c:forEach>
				<c:forEach items="${photos }" var="photo" varStatus="i">
				<c:if test="${photo.place == 2 }">
				<div name="pic" style="display:none"><img src="${photo.url }" style="width: 900px" height="300px"></img></div>
				</c:if>
				</c:forEach>
				<c:forEach items="${photos }" var="photo" varStatus="i">
				<c:if test="${photo.place == 3 }">
				<div name="pic" style="display:none"><img src="${photo.url }" style="width: 900px" height="300px"></img></div>
				</c:if>
				</c:forEach>
				<c:forEach items="${photos }" var="photo" varStatus="i">
				<c:if test="${photo.place == 4 }">
				<div name="pic" style="display:none"><img src="${photo.url }" style="width: 900px" height="300px"></img></div>
				</c:if>
				</c:forEach>
				<div class="cc" style="height:90px;width:15px; position:absolute; bottom:24px; right:19px;">
					<ul>
						<a href="javaScript:void(0)"><li name="dian" onclick = "showPic(0);" style="background-color:#0d8fdd;"></li></a>
						<a href="javaScript:void(0)"><li name="dian" onclick = "showPic(1);"></li></a>
						<a href="javaScript:void(0)"><li name="dian" onclick = "showPic(2);"></li></a>
						<a href="javaScript:void(0)"><li name="dian" onclick = "showPic(3);"></li></a>
					</ul>
        </div>

			</div>
		</div>	
		<!-- 排行榜+论坛 -->
		<div class="part2">
			<div class="part2_top">
				<div class="part2_top1">
					<span>学分排行榜</span>
				</div>
				<div class="part2_top2">
					<span>朝腾科技论坛</span>
				</div>
			</div>		
			<!-- 下面内容 -->
			<div class="part2_down">
				<div class="part2_down1">
					<div class="part2_down1_l">
						<li class="mt3">姓名</li>
						<li class="mt16">学分</li>
						<li class="mt46">姓名</li>
						<li class="mt16">学分</li>
					</div>
					<c:set var="index" value="1" ></c:set>
					<c:forEach items="${users}" var="user" varStatus="i">
						<c:set var="index" value="${index+1}"></c:set>
						<c:if test="${index < 12}">
						<div class="part2_down1_r">
							<li class="ml17"><a href="javaScript:void(0)">${user.name}</a></li>
							<li class="mt15ml32 ce33">${user.examScore}</li>
						</div>
						</c:if>
					</c:forEach>
				</div>
				<div class="part2_down2">
					<c:set var="count" value="0"></c:set>
					<c:forEach items="${bbsPlateList }" var="plate">
					<c:set var="count" value="${count+1}"></c:set>
					<c:if test="${count<=6}">
					<div class="part2_down2a">
						<div></div>
						<li class="part2_down2a_l1">
							<a href="bbs/FrontBbsServlet?method=platePost&plateId=${plate.id}">
								${plate.name }
							</a>
						</li>
						<li class="part2_down2a_l2 mt3">帖子数：${plate.postSum}</li>
					</div>
					</c:if>
					</c:forEach>
			</div>
			</div>
		</div>
		<!-- 热门话题 -->
		<div class="part3">
			<span>热门话题</span>
		</div>
		<div class="part3_top">
			<span class="ml27">帖子标题</span>
			<span class="ml337">热度</span>
			<span class="ml225">作者</span>
			<span class="ml279">时间</span>
		</div>
		<table width="1202" class="table1" >
		<c:set var="index1" value="1" ></c:set>
		<c:forEach items="${postHeatList }" var="heatPost">
		<c:set var="index1" value="${index1+1}"></c:set>
			<c:if test="${index1<=8 }">
			<tr>
				<td width="398" class="pl27">
					<a href="bbs/FrontBbsServlet?method=postReplyList&id=${heatPost.id}">
						${heatPost.name }
					</a>
				</td>
				<td width="258"><a href="#">${heatPost.heat }</a></td>
				<td width="310"><a href="#">${heatPost.createUser.name }</a></td>
				<td>${heatPost.createTime }</td>
			</tr>
			</c:if>
		</c:forEach>
			</table>
		<!-- 加精好贴 -->
		<div class="part4">
			<span>加精好贴</span>
		</div>
		<div class="part4_top">
			<span class="ml27">帖子标题</span>
			<span class="ml337">板块</span>
			<span class="ml225">作者</span>
			<span class="ml279">时间</span>
		</div>
		<table width="1200" class="table2" >
			<c:set var="index2" value="1" ></c:set>
			<c:forEach items="${postGoodList }" var="goodPost">
			<c:set var="index2" value="${index2+1}"></c:set>
			<c:if test="${index2<=8 }">
			<tr>
				<td width="398" class="pl27">
					<a href="bbs/FrontBbsServlet?method=postReplyList&id=${goodPost.id}">
						${goodPost.name }
					</a>
				</td>
				<td width="258"><a href="#">${goodPost.bbsPlate.name }</a></td>
				<td width="310"><a href="#">${goodPost.createUser.name }</a></td>
				<td>${goodPost.createTime }</td>
			</tr>
			</c:if>
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>


















