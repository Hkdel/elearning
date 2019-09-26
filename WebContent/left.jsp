<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>左边菜单</title>
<link href="css/style-1.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
	$(function() {
		//导航切换
		$(".menuson li").click(function() {
			$(".menuson li.active").removeClass("active")
			$(this).addClass("active");
		});

		$('.title').click(function() {
			var $ul = $(this).next('ul');
			$('dd').find('ul').slideUp();
			if ($ul.is(':visible')) {
				$(this).next('ul').slideUp();
			} else {
				$(this).next('ul').slideDown();
			}
		});
	})
</script>


</head>

<body style="background: #f0f9fd;">
	<div class="lefttop">
		<span></span>在线学习管理系统
	</div>
	<dl class="leftmenu">
		<c:forEach items="${parentList}" var="parent">
			<c:if test="${parent.status.equals('1') }">
				<dd>
					<div class="title">
						<span><img src="images/leftico01.png" /></span>${parent.name}
					</div>
					<ul class="menuson">
						<c:forEach items="${authList}" var="son">
							<c:if test="${parent.id==son.parent.id}">
								<c:if test="${son.status.equals('1') }">
									<li><cite></cite><a href="${son.url}" target="rightFrame">${son.name}</a></li>
								</c:if>
							</c:if>
						</c:forEach>
					</ul>
				</dd>
			</c:if>
		</c:forEach>


	</dl>
</body>
</html>
