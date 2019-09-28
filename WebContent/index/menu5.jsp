<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link type="text/css" rel="stylesheet" href="info/CSS/myInfo.css" />
	</head>

	<body>
		<div id="all">

			<div id="fangkuai">
				<div id="fk1"></div>
			</div>
			<c:if test="${not empty loginUser }">
			<div id="left">
				<div id="title1">我的信息</div>
				<div id="photo">
					<img src="${loginUser.photo }" id="left_img" >
				</div>
				<div class="letf_font">姓名 : ${loginUser.name }</div>
				<div class="letf_font">发帖积分 : ${loginUser.bbsScore}</div>
				<div class="letf_font">考试积分 : ${loginUser.examScore }</div>
				<div class="letf_font">发帖数 : ${loginUser.postCount }</div>
				<div class="letf_font">注册时间 : ${loginUser.createTime }</div>
				<div>
					<a class="btn_radius btn3" href="info/infoUpdate.jsp" >修改信息</a>

				</div>
			</div>
			</c:if>
			<c:if test="${not empty loginUser }">
			<div id="right">

				<div id="examInfo">
					<div id="fk2"></div>
					<div id="title2">考试信息详情</div>
					<a href="#" id="more">更多 >></a>
				</div>

				<div id="score">
					<div id="three">
						<span id="number">序号</span>
						<span id="examPage">试卷</span>
						<span id="fenshu">分数</span>
					</div>
					<c:forEach items="${recordList}" var="record" varStatus="i" >
						<div class="course">
						<span class="right_font1">${i.count}</span>
						<span class="right_font2"><a href="exam/frontExam?method=showDetail&id=${record.id}" title="查看详情" >${record.rule.name}</a></span>
						<span class="right_font3">${record.score}</span>
					</div>
					</c:forEach>
				</div>
			</div>
			</c:if>
		</div>

	</body>

</html>