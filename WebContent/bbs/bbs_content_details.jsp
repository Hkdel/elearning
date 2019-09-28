<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="css/bbs_content_details.css" />
		<script type="text/javascript" src="js/bbs.js"></script>
		<script type="text/javascript" src="../js/common.js"></script>
		<script type="text/javascript">
			function replySubmit(parent,url){
				
				var parent = parent;
				var content = parent.children[0].children[0].value;
				
				var accId = parent.children[0].children[1].value;//被回复人ID(非层主)
				var accContentId = parent.children[0].children[2].value;//被回复的回复ID(非层主)
				window.location.href=url+encodeURI(content)+"&accId="+accId+"&accContentId="+accContentId;
				
			}
		</script>
	</head>

	<body>
		<!--帖子详细内容展示开始-->
		<div id="content_details_main">
			<div class="main_adrress">
				<ul>
					<li style="margin-left: 0;">当前位置</li>
					<li>:</li>
					<li>论坛</li>
					<li>&gt;</li>
					<li>${post.bbsPlate.name }</li>
					<li>&gt;</li>
					<li>${post.name}</li>
				</ul>
			</div>
			<div class="content_update">
				<!--第一块-->
				<!--这是一个帖子内容详情板块-->
				<div class="content_box" style="margin: 0;">
					<div class="content_box_details">
						<div class="details_title">
							<div class="details_title_icon"> </div>
							<div class="details_title_info">
								<div class="info_name">${post.createUser.name}</div>
								<div class="info_grade">
									<ul>
										<li>等级&nbsp;:&nbsp;</li>
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
										<li style="float: right;">${post.createUser.bbsScore }</li>
										<li style="float: right;">积分&nbsp;:&nbsp;</li>
									</ul>
								</div>
							</div>
							<div class="details_title_idetity">
								<div class="dots"></div>
								<div class="owner">楼主</div>
							</div>
						</div>
						<div class="details_article">${post.content}</div>
					</div>
				</div>
				<!--内容详情板块结束-->
				<!--第二块-->
				<!--这是一个帖子评论详情板块-->
				<c:forEach items="${postReplyList }" var="postReply" varStatus="i">
				<div class="content_box">
					<div class="content_box_details">
						<div class="details_title">
							<div class="details_title_icon"> </div>
							<div class="details_title_info">
								<div class="info_name">${postReply.loginUser.name }</div>
								<div class="info_grade">
									<ul>
										<li>等级&nbsp;:&nbsp;</li>
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
										<li style="float: right;">${postReply.loginUser.bbsScore }</li>
										<li style="float: right;">积分&nbsp;:&nbsp;</li>
									</ul>
								</div>
							</div>
							<div class="details_title_idetity">
								<div class="dots"></div>
								<c:choose>
									<c:when test="${i.index==0}">
										<div class="owner">沙发</div>
									</c:when>
									<c:when test="${i.index>=1}">
										<div class="owner">${i.index+1}楼</div>
									</c:when>
								</c:choose>
								<c:if test="${postReply.loginUser.id==loginUser.id }">
									<div class="owner">
										<a href="FrontBbsServlet?method=delReply2&childId=${postReply.id}&id=${post.id}">删除</a>
									</div>
								</c:if>
							</div>
						</div>
						<div class="details_article">${postReply.content}</div>
						<div class="comments">
							<div class="comments_status">
								<span id="status_text" class="status_text">隐藏评论</span>
								<img id="status_img" class="shImg" src="img/show_arrow.png" onclick="showComments(this.parentNode)" />
								<!--评论可隐藏区域-->
								<!--隐藏域1-->
								<div id="com1" class="hidden_comments">
									<!--评论1-->
									<c:forEach items="${postReply.reply2}" var="childReply">
									<div style="height:57px;">
										<div class="preIcon"></div>
										<div class="reply_info">
											<ul>
												<li class="link_style">
													<a href="#">${childReply.loginUser.name }</a>
													<input type="hidden" value="${childReply.loginUser.id }">
												</li>
												<li>回复</li>
												<li class="link_style">
													<a href="#">${childReply.acceptUser.name }</a>
												</li>
												<li class="link_style reply" style="float: right;" onclick="openMessage(this.parentNode)">
													<a href="javaScript:void(0)">回复</a>
													<c:if test="${childReply.loginUser.id==loginUser.id }">
														<a href="FrontBbsServlet?method=delReply2&childId=${childReply.id}&id=${post.id}">删除</a>
													</c:if>
												</li>
											</ul>
										</div>
										<div id="comments_details" class="comments_details">
											${childReply.content}
											<input type="hidden" value="${childReply.id }">
										</div>
									</div>
									</c:forEach>
									
								</div>
								<!--回复层-->
								
								<div class="reply_online">
									<div class="online_content">
										<input name="content" id="replyContent" type="text" placeholder="说说想回复层主什么？" class="online_content_inp" />
										<input type="hidden"/>
										<input type="hidden"/>
									</div>
									<div class="online_replyBtn" onclick="return replySubmit(this.parentNode,'FrontBbsServlet?method=temp3&postId=${post.id}&postReplyUserId=${postReply.loginUser.id }&postReplyId=${postReply.id }&content=')">
										<span>回&nbsp;复</span>
									</div>
								</div>
								
							</div>

						</div>
					</div>
				</div>
				</c:forEach>
				<!--内容详情板块结束-->
				<!--page页部分-->
				<form action="FrontBbsServlet?method=postReplyList" method="post" id="modelForm">
				<div class="page">
					<ul class="page_left">
						<li>当前第 ${pageUtils.currPage}页,</li>
						<li>${pageUtils.totalSize}条数据</li>
						<li>共${pageUtils.totalPage}页</li>
					</ul>
					<ul class="page_right">
						<li>
							<c:if test="${pageUtils.currPage>1 }">
								<a href="javascript:void(0)}" class="common_button" onclick="subFrom(${pageUtils.currPage-1 })">上一页</a>
							</c:if>
						</li>
						<li>
							<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
								<a href="javascript:void(0)}" class="common_button" onclick="subFrom(${pageUtils.currPage+1 })">下一页</a>
							</c:if>
						</li>
						<li>
							<a href="javascript:void(0)}" class="common_button" onclick="subFrom(1)" style="margin-left:45px;">首页</a>
							<input type="hidden" value="${pageUtils.currPage}" id="page" name="page" />
							<input type="hidden" value="${id}" name="id" />
						</li>
						<li style="margin: 0;">
							<a href="javascript:void(0)}" class="common_button" onclick="subFrom(${pageUtils.totalPage })">尾页</a>
						</li>
					</ul>
				</div>
				</form>
				
				<form action="FrontBbsServlet?method=temp4" method="post" id="Form">
					<div class="quick_reply">
						<div class="quick_reply_name">
							<div class="blue_line"></div>
							<div class="quick_reply_name_text">快速回复帖子</div>
						</div>
						<div class="quick_reply_content">
							<textarea name="content" placeholder="请输入您要回复的内容"></textarea>
							<input type="hidden" value="${post.id }" name="id" />
						</div>
						<div class="online_replyBtn" onclick="submit();" style="margin-top: 20px;">回&nbsp;复</div>
					</div>
				</form>
			</div>

		</div>

	</body>

</html>