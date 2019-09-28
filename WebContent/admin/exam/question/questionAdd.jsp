<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zt.exam.dao.impl.SubjectDaoImpl"%>
<%@page import="com.zt.exam.dao.SubjectDao"%>
<%@page import="com.zt.exam.po.Subject"%>
<%@page import="com.zt.exam.dao.impl.TypeDaoImpl"%>
<%@page import="com.zt.exam.dao.TypeDao"%>
<%@page import="com.zt.exam.po.Type"%>
<%@page import="java.util.List"%>
<%@ include file="../../../tag.jsp"  %>
<%
	TypeDao typeDao = new TypeDaoImpl();
	SubjectDao subDao = new SubjectDaoImpl();
	List<Type> typeList = typeDao.findAll();
	List<Subject> subList = subDao.findAll();
	pageContext.setAttribute("typeList", typeList);
	pageContext.setAttribute("subList", subList);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加题目</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			//新增选项
			function addOption(){
				var options = document.getElementById("options");
				options.innerHTML += "<li><input name='content' type='text'></li>";
				//var va = document.getElementsByName("content")[0].value ;
				//console.log(va);
				//console.log(options.innerHTML);
				//document.getElementsByName("content")[0].value = va;
			}
			//删除选项
			function delOption() {
				var options = document.getElementById("options");
				var lis = options.getElementsByTagName("li");
				lis[lis.length-1].remove();
			}
			//选择题
			function selectQuestion(value) {
				var optionTr = document.getElementById("optionTr");
				if(value == 1 || value == 2) {
					optionTr.className = "optionTr_show"
				}else {
					optionTr.className = "optionTr_hidden"
				}
			}
		</script>
	</head>
<body>
	<div class="page_title">题目管理&nbsp; &gt; 添加题目</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="submit();">保存</button>
	</div>
	<form action="admin/exam/sysExam?method=quesAdd" method="post" id="Form" >
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>
				<select name="subId" >
					<option value="" style="display:none;" disabled selected>请选择</option>
					<c:forEach items="${subList}" var="sub">
						<option value="${sub.id}">${sub.name}</option>
					</c:forEach>
				</select>
				<span class="red_star">*</span>
			</td>
			<th>题型</th>
			<td>
				<select name="typeId" required="true" onchange="selectQuestion(this.value)">
					<option value="" style="display:none;" disabled selected>请选择</option>
					<c:forEach items="${typeList}" var="type">
						<option value="${type.id}" >
							${type.name}
						</option>
					</c:forEach>
				</select>
				<span class="red_star">*</span>
			</td>
		</tr>
		<tr>
			<th>题目</th>
			<td colspan="3"><input type="text" name="title" size="80"><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>答案</th>
			<td colspan="3"><textarea name="answer" rows="6" cols="50"></textarea><span class="red_star">*</span></td>
		</tr>
		<tr class="optionTr_hidden" id="optionTr">
			<th>选项</th>
			<td colspan="3">
				<input type="button" value="新增选项" class="common_button" onclick="addOption()" />
				<input type="button" value="删除选项" class="common_button" onclick="delOption()" />
				<ol type="A" id="options">
					<li><input name="content" type="text"></li>
				</ol>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>