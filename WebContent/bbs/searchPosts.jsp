<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="bbs/css/bbs_content_details.css" />
</head>
<body>
	<div id="content_details_main">
		<div class="main_adrress">
			<ul>
				<li style="margin-left: 0;">当前位置</li>
				<li>:</li>
				<li>论坛</li>
				<li>&gt;</li>
				<li>内容模糊查询帖子</li>
			</ul>
		</div>
		<div class="content_update" style="margin-bottom: 20px;">
			<!--第一块-->
			<!--这是一个帖子内容详情板块-->
			<c:forEach items="${postList }" var="post">
				<a href="FrontBbsServlet?method=postReplyList&id=${post.id}" title="查看帖子详情"
				style="color: black;">
					<div class="content_box" style="padding-bottom: 20px;">
						<div class="content_box_details">
							<div class="details_title">
								<div class="details_title_icon"></div>
								<div class="details_title_info">
									<div class="info_name">${post.createUser.name}</div>
									<div class="info_grade">
										<ul>
											<li>等级&nbsp;:&nbsp;</li>
											<li>
											<c:choose>
												<c:when test="${post.createUser.bbsScore<=20}">
													<li>英勇青铜</li>
												</c:when>
												<c:when test="${post.createUser.bbsScore<=40}">
													<li>不屈白银</li>
												</c:when>
												<c:when test="${post.createUser.bbsScore<=60}">
													<li>荣耀黄金</li>
												</c:when>
												<c:when test="${post.createUser.bbsScore<=80}">
													<li>华贵铂金</li>
												</c:when>
												<c:when test="${post.createUser.bbsScore<=100}">
													<li>璀璨钻石</li>
												</c:when>
												<c:when test="${post.createUser.bbsScore<=120}">
													<li>最强王者</li>
												</c:when>
											</c:choose>
											</li>
											<li style="float: right;">${post.createUser.bbsScore}</li>
											<li style="float: right;">积分&nbsp;:&nbsp;</li>
										</ul>
									</div>
								</div>
								<div class="details_title_idetity">
									<div class="dots"></div>
									<div class="owner">楼主</div>
								</div>
							</div>
							<div class="details_article">
								<p align="center" style="margin-bottom: 20px;" >${post.name}</p>
								&nbsp;&nbsp;&nbsp;&nbsp;${post.content}
							</div>
						</div>
					</div>
				</a>
			</c:forEach>
		</div>
	</div>
	
</body>
</html>