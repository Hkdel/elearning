<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>bbs_post_message</title>
<link rel="stylesheet" href="css/post.css" />
<script type="text/javascript" src="../js/common.js"></script>
</head>

<body>
	<ul class="post_iframe">
    	<c:forEach items="${postList }" var="post">
	    	<li class="post_iframe_li">
				<a target="_parent" href="FrontBbsServlet?method=postReplyList&id=${post.id}">
	        		<span class="post_span1">${post.name}</span>
				</a>
	            <span class="post_span2">${post.createUser.name}</span>
	            <span class="post_span3">${post.replySum }</span>
	            <span class="post_span4">${post.createTime}</span>
	        </li>
    	</c:forEach>
    	<form action="FrontBbsServlet?method=listPlatePage" method="post" id="modelForm">
	        <div id="iframe_bottom">
	    		当前第 ${pageUtils.currPage}页, 每页${pageUtils.pageSize}条数据&nbsp;&nbsp;&nbsp;共${pageUtils.totalPage}页
	    		<a href="javascript:void(0)}" class="common_button" onclick="subFrom(1)" style="margin-left:45px;">首页</a>
            	<a href="javascript:void(0)}" class="common_button" onclick="subFrom(${pageUtils.totalPage })">尾页</a>
	            <c:if test="${pageUtils.currPage>1 }">
					<a href="javascript:void(0)}" class="common_button" onclick="subFrom(${pageUtils.currPage-1 })">上一页</a>
				</c:if>
				<c:if test="${pageUtils.currPage<pageUtils.totalPage }">
					<a href="javascript:void(0)}" class="common_button" onclick="subFrom(${pageUtils.currPage+1 })">下一页</a>
				</c:if>
				<input type="hidden" value="${pageUtils.currPage}" id="page" name="page" />
				<input type="hidden" value="${id}" name="id" />
	    	</div>
		</form>
    </ul>
    
</body>
</html>
