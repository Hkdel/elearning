<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../bbs/css/bbs_index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/common.js"></script>
<title>无标题文档</title>
</head>

<body>
	<!--宽度1280px 总高度 1570px-->
	<!--菜单栏 -->
	<div>
		<!--菜单部分-->


	</div>
	<!--用户名 查询框-->
	<div class="dv2">
		<!--用户显示-->
		<div class="dv2_text">欢迎你，${loginUser.name }</div>
		<!--搜索框-->
		<form action="FrontBbsServlet?method=findByName" method="post" id="modelForm">
		<div class="dv2_inp">
			<div class="dv2_inp_left">
				<input name="name" type="text" />
			</div>
			<button style="margin-top: 10px;background: green;width:64px;height:28px;" onclick="subFrom(1);">
				查询
			</button>
		</div>
		</form>
	</div>
	<!--快速入口 -->
	<div class="dv3">
		<!--标题-->
		<div class="dv3_title">
			<div class="dv3_title_line"></div>
			<div class="dv3_title_text">
				<a href="#">快速入口 </a>
			</div>
		</div>
		<div class="dv3_main">
			<!--模块-->
			<c:forEach items="${bbsPlateList }" var="plate" varStatus="i">
			<c:if test="${i.count<=9 }">
				<div class="dv3_main_module">
					<div class="dv3_main_module_1">
						
							<img src="${plate.photo }" style="width: 60px;height: 60px"/>
						
						<div class="dv3_main_module_1_text">
							<div class="dv3_main_module_1_text_top">
								<a class="a2" href="../bbs/FrontBbsServlet?
								method=platePost&plateId=${plate.id}">${plate.name}</a>
							</div>
							<div class="dv3_main_module_1_text_bottom">
								<div class="dv3_main_module_1_text_bottom_1">帖子数 : ${plate.postSum}</div>
								<div class="dv3_main_module_1_text_bottom_2">作者：${plate.createUser.name}</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			</c:forEach>
			
		</div>
	</div>
	<!--论坛热帖 -->
	<div class="dv4">
		<!--标题-->
		<div class="dv3_title">
			<div class="dv3_title_line"></div>
			<div class="dv3_title_text">
				<a href="#">论坛热帖 </a>
			</div>
		</div>
		<!--内容-->
		<c:forEach items="${plateListPage}" var="platePage">
			<div class="dv4_main">
				<div class="dv4_main_left">
					<div class="dv4_main_left_img">
					<a href="../bbs/FrontBbsServlet?
								method=platePost&plateId=${platePage.id}">
						<img class="dv4_img" src="${platePage.photo }" />
					</a>
					</div>
					<div class="dv4_main_left_text">
						<span class="sp1 a2">${platePage.name}</span> 
						<span class="sp2">简介:${platePage.introduction}</span>
					</div>
				</div>
				<div class="dv4_main_right">
					<!--信息-->
					<c:set var="count" value="0"></c:set>
					<c:forEach items="${postList}" var="post" varStatus="i">
						<c:if test="${post.bbsPlate.id==platePage.id}">
						<c:set var="count" value="${count+1}"></c:set>
							<c:if test="${count<=5}">
							<div class="dv4_main_right_text">
								<span class="sp3">${post.createUser.name}:</span> 
								<span class="sp4">
									<a href="../bbs/FrontBbsServlet?method=postReplyList
									&id=${post.id}">${post.name}</a>
								</span> 
								<span class="sp5">${post.checkTime}</span>
							</div>
							</c:if>
						</c:if>
					</c:forEach>
				</div>
				<div class="line"></div>
			</div>
		</c:forEach>
		<!--换页-->
		<form action="FrontBbsServlet?method=listPlate" method="post" id="modelForm1">
		<div class="dv5">
			<div class="dv5_paging">
				<a href="javascript:void(0)}" class="common_button" onclick="subFrom1(1)" style="margin-left:45px;">首页</a>
            	<a href="javascript:void(0)}" class="common_button" onclick="subFrom1(${pageUtils.totalPage })">尾页</a>
            	<c:if test="${pageUtils.currPage>1 }">
					<a href="javascript:void(0)}" class="common_button" onclick="subFrom1(${pageUtils.currPage-1 })">上一页</a>
				</c:if>
				<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
					<a href="javascript:void(0)}" class="common_button" onclick="subFrom1(${pageUtils.currPage+1 })">下一页</a>
				</c:if>
			</div>
			<div class="dv5_pageInfo">
				当前第 ${pageUtils.currPage}页,每页${pageUtils.pageSize}条数据<span>共${pageUtils.totalPage}页</span>
			</div>
			<input type="hidden" value="${pageUtils.currPage}" id="page" name="page" />
		</div>
		</form>
	</div>
</body>
</html>
