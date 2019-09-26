<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="study/css/study.css" />
<script type="text/javascript" src="study/js/vue.min.js">


</script>
<script type="text/javascript">
	function submitForm(page){
	document.getElementById("page").value=page;
	document.getElementById("searchForm").submit();
}
</script>
</head>

<body>
	<div class="all">
        <div class="left" id="a">
        	<div class="left-top" >
            	<div class="left-top-xue" v-bind:class="{'display':flag1}"><p style="margin-top:16px;color: #ffffff">学习手记<p></div>
				
                <div class="left-top-gong" onclick="location.href='study/frontStudy?method=resList'"><p style="margin-top:16px;color: #64a3fd;" >共享资源<p></div>
				
            </div>
			<div v-bind:class="{'display':flag1}">
				<form action="study/frontStudy?method=addNote" method="post">
					<input class="inp radius_box" type="text" style="color:#aaa;width: 180px;margin-left: 200px;" placeholder="           手记名" name="name"/>
            		<input class="inp radius_box" type="text" style="color:#aaa;margin-top: 30px;" placeholder="请输入手记标题" name="title"/>
            		<textarea class="text radius_box" style="color:#aaaaaa;resize: none;" placeholder="请输入手记正文" name="content"></textarea>
            		<input class="inp1 radius_box" type="submit" value="创建手记"  style="color:#FFF"/>
            	</form>
			</div>
			
        </div>
        <div class="right">
        	<form action="study/frontStudy?method=noteList" method="post"  id="searchForm">
        		<input type="hidden" name="page" value="${currPage }" id="page"/>
        	   手 记名:&nbsp;&nbsp;<input class="inp2 radius_box" name="name" value="${filter['name'] }"/><br/>
           	   开始时间:&nbsp;&nbsp;<input type="date" class="inp3 radius_box" name="beginTime" value="${filter['begin'] }"/><br/>
                                   结束时间:&nbsp;&nbsp;<input type="date" class="inp3 radius_box" name="endTime" value="${filter['end'] }"/>
             <input class="inp4 radius_box" type="submit" value="查询" />
            </form>
            
            <div class="right-text">
              <c:forEach items="${noteList }" var="note" varStatus="i">
            	<div class="right-text${i.count%2+1}">
                	<div class="si">
                	<span>
                		<a href="study/frontStudy?method=showNote&id=${note.id}">
                			<c:if test="${fn:length(note.title)>=1 }">
                				${fn:substring(note.title,0,3)}
                			</c:if>
                			<%-- ${note.title } --%>
                		</a>
                	</span></div>
                    <div class="time"><span class="sp">${note.createTime }</span></div>
                    <div class="modify"><a class="a1" href="study/frontStudy?method=editNote&id=${note.id}">修改</a></div>
                    <div class="delete"><a class="a" href="study/frontStudy?method=delNote&id=${note.id}"  onclick="return confirm('确定删除吗？')">删除</a></div>
                </div>
              </c:forEach>
                
            </div>
        </div>
        <div class="bottom">
        	<div class="bottom1">当前第${pageUtils.currPage}页,共${pageUtils.totalPage }页</div>
            <div class="bottom2"><a class="a2" href="javaScript:void(0)"  onclick="submitForm(1)">首页</a></div>
            <div class="bottom2"><a class="a2" href="javaScript:void(0)" onclick="submitForm(${pageUtils.totalPage })">尾页</a></div>
            <c:if test="${pageUtils.currPage>1 }">
            	<div class="bottom3"><a class="a2" href="javascript:void(0)" onclick="submitForm(${pageUtils.currPage-1 })">上一页</a></div>
            </c:if>
            <c:if test="${pageUtils.currPage<pageUtils.totalPage }">
            	<div class="bottom3"><a class="a2" href="javascript:void(0)" onclick="submitForm(${pageUtils.currPage+1 })">下一页</a></div>
            </c:if>
        </div>
    </div>

</body>


</html>
