<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="study/css/study.css" />
<script type="text/javascript" src="study/js/vue.min.js"></script>
</head>
<body>
	<div class="all">
        <div class="left" id="a">
        	<div class="left-top" >
            	<div class="left-top-xue" v-bind:class="{'display':flag1}" style="width: 600px;"><p style="margin-top:16px;color: #ffffff">学习手记<p></div>
            </div>
				<form action="study/frontStudy?method=updateNote" method="post">
				<input type="hidden" name="id" value="${note.id }"/>
            		<input class="inp radius_box" type="text" style="color:#aaa;width: 180px;margin-left: 200px;" placeholder="           手记名" name="name"  value="${note.name}" />
            		<input class="inp radius_box" type="text" style="color:#aaa;margin-top: 30px;" placeholder="请输入手记标题" name="title" value="${note.title }"/>
            		<textarea class="text radius_box" style="color:#aaaaaa;resize: none;" placeholder="请输入手记正文" name="content">${note.content }</textarea>
            		<input class="radius_box" type="submit" value="修改手记"
            		  style="color:#FFF;margin-left: 300px;width:100px;height:30px;background-color:#65a2fd;margin-top: 20px;margin-left: 370px;"/>
            		<a href="study/frontStudy?method=noteList">
						<input type="button" value="返&nbsp;&nbsp;回" class="radius_box" 
						 style="color:#FFF;width:100px;height:30px;background-color:#65a2fd;margin-left: 20px;margin-top: 20px;"/>
					</a>
            	</form>
            	
			
        </div>
    </div>

</body>


</html>
