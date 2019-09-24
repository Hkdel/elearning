<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zt.exam.dao.impl.SubjectDaoImpl"%>
<%@page import="com.zt.exam.dao.impl.TypeDaoImpl"%>
<%@page import="com.zt.exam.dao.impl.QuestionDaoImpl"%>
<%@page import="com.zt.exam.dao.SubjectDao"%>
<%@page import="com.zt.exam.dao.TypeDao"%>
<%@page import="com.zt.exam.dao.QuestionDao"%>
<%@page import="com.zt.exam.po.Subject"%>
<%@page import="com.zt.exam.po.Type"%>
<%@page import="com.zt.exam.po.Option"%>
<%@page import="com.zt.exam.po.Question"%>
<%@page import="java.util.List"%>
<%@ include file="../../../tag.jsp"  %>  
<%
	TypeDao typeDao = new TypeDaoImpl();
	SubjectDao subDao = new SubjectDaoImpl();
	List<Type> typeList = typeDao.findAll();
	List<Subject> subList = subDao.findAll();
	String idStr = request.getParameter("id");
	int id = 0;
	if (idStr != null && !"".equals(idStr)) {
		id = Integer.parseInt(idStr);
	}
	QuestionDao queDao = new QuestionDaoImpl();
	Question qusetion = queDao.getQuestionById(id);
	List<Option> options = queDao.getOptionsByQuestionId(id);
	pageContext.setAttribute("typeList", typeList);
	pageContext.setAttribute("subList", subList);
	pageContext.setAttribute("ques", qusetion);
	pageContext.setAttribute("options", options);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑题目</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
			//新增选项
			function addOption(){
				var options = document.getElementById("options");
				options.innerHTML += "<li><input type='text' name='content'></li>";
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
	<div class="page_title">题目管理&nbsp; &gt; 编辑题目</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="submit();">保存</button>
	</div>
	<form action="admin/exam/sysExam?method=quesUpdate" method="post" id="Form" >
	<input type="hidden" name="id" value="${ques.id}" />
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>
				<select name="subId" onfocus="this.defaultIndex=this.selectedIndex;" 
						onchange="this.selectedIndex=this.defaultIndex;" >
					<c:forEach items="${subList}" var="sub">
						<option value="${sub.id}" 
							<c:if test="${sub.id == ques.subject.id}">selected="selected"</c:if> >
							${sub.name}
						</option>
					</c:forEach>
				</select>
				<span class="red_star">*</span>
			</td>
			<th>题型</th>
			<td>
				<select name="typeId" onfocus="this.defaultIndex=this.selectedIndex;" 
						onchange="this.selectedIndex=this.defaultIndex;" >
					<c:forEach items="${typeList}" var="type">
						<option value="${type.id}" 
							<c:if test="${type.id == ques.type.id}">selected="selected"</c:if> >
							${type.name}
						</option>
					</c:forEach>
				</select>
				<span class="red_star">*</span>
			</td>
		</tr>
		<tr>
			<th>题目</th>
			<td colspan="3"><input type="text" name="title" value="${ques.title}" size="80"><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>答案</th>
			<td colspan="3">
				<textarea name="answer" rows="6" cols="50" />${ques.answer}</textarea>
				<span class="red_star">*</span></td>
		</tr>
		<c:if test="${fn:length(options) > 1 }">
			<tr class="optionTr_show" id="optionTr">
			<th>选项</th>
			<td colspan="3">
				<input type="button" value="新增选项" class="common_button" onclick="addOption()" />
				<input type="button" value="删除选项" class="common_button" onclick="delOption()" />
				<ol type="A" id="options">
					<c:forEach items="${options}" var="op" >
						<li><input name="content" type="text" value="${op.content}" /></li>
					</c:forEach>
				</ol>
			</td>
			</tr>
		</c:if>
	</table>
	</form>
</body>
</html>