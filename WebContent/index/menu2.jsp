<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"  %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="exam/CSS/exam_index.css">
	<link rel="stylesheet" href="exam/layui/css/layui.css">
	<style type="text/css">
		/* .title_choice>ul>li{width: 100px} */
	</style>
	<script type="text/javascript" src="exam/js/exam_index.js"></script>
	<script type="text/javascript">
		function submitForm(page,subId){
			document.getElementById("page").value = page;
			if (subId != null) {
				document.getElementById("subId").value = subId;
			} else {
				document.getElementById("subId").value = 0;
			}
			document.getElementById("searchForm").submit();
		}
	</script>
    <title>考试</title>
</head>
<body>
    <!-- 中部内容 -->
    <form action="exam/frontExam?method=paperList" id="searchForm" method="post" >
    	<input type="hidden" name="page" id="page" />
    	<input type="hidden" name="subId" id="subId" />
    <div id="middle">
        <div class="sercherBox">
            <input type="text" name="paperName" value="${filter.name}" placeholder="请输入试卷名称" id="values" >
            <a href="javaScript:void(0)" onclick="submitForm(1,0)" ><img src="exam/img/glass.png"></a>
        </div>
        <div class="title_choice" id="examChange">
            <ul>
                <li onclick="bkChange(0)" 
                	<c:if test="${empty filter.subId}">style="background-color: #65a2fd;"</c:if> >
                	<a onclick="submitForm(1,0)" >全部</a>
                </li>
                <c:forEach items="${subList}" var="sub" varStatus="i">
                	<li onclick="bkChange(${i.count})" 
                		<c:if test="${filter.subId == sub.id}">style="background-color: #65a2fd;"</c:if> >
                		<a onclick="submitForm(1,${sub.id})" >${sub.name}</a>
                	</li>
                </c:forEach>
            </ul>
        </div>
        <div class="exam">
            <table class="layui-table">
                <tr class="exam_tr1" style="background-color: #f2f2f2;">
                    <td>序号</td>
                    <td>科目</td>
                    <td>试卷名称</td>
                    <td >操作</td>
                </tr>
                <c:forEach items="${ruleList}" var="rule" varStatus="i" >
                	<tr class="exam_tr2">
                    <td>${(pageUtils.currPage-1)*pageUtils.pageSize+i.count}</td>
                    <td>${rule.subject.name}</td>
                    <td>${rule.name}</td>
                    <td onclick='click_confirm()'>
                    	<a href="exam/frontExam?method=temp&ruleId=${rule.id}&subId=${rule.subject.id}" class="exam_color">考试</a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            <div class="page_choice">
                <font>当前第${pageUtils.currPage}页, 共${pageUtils.totalPage}页</font> 
                <a href="javaScript:void(0)" onclick="submitForm(1,${filter.subId})" >首页</a>
                <a 
                	<c:if test="${pageUtils.currPage == 1}">style="display:none;"</c:if>
					href="javaScript:void(0)" onclick="submitForm(${pageUtils.currPage-1},${filter.subId})" >上一页
				</a> 
				<a 
					<c:if test="${pageUtils.currPage == pageUtils.totalPage}">style="display:none;"</c:if>
					href="javaScript:void(0)" onclick="submitForm(${pageUtils.currPage+1},${filter.subId})" >下一页
				</a> 
                <a href="javaScript:void(0)" onclick="submitForm(${pageUtils.totalPage},${filter.subId})" >尾页</a>
            </div>
        </div>
    </div>
    </form>
</body>
</html>