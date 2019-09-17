<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看题目</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">题目管理&nbsp; &gt; 查看题目</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>
				<select>
					<option>请选择</option>
					<option selected>java</option>
					<option>html</option>
				</select>
				<span class="red_star">*</span>
			</td>
			<th>题型</th>
			<td>
				<select>
					<option>请选择</option>
					<option value="1">单选题</option>
					<option value="2" selected>多选题</option>
					<option value="3">判断题</option>
					<option value="4">填空题</option>
					<option value="5">简答题</option>
				</select>
				<span class="red_star">*</span>
			</td>
		</tr>
		<tr>
			<th>题目</th>
			<td colspan="3"><input type="text" value="面向对象的特征？" size="80"><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>答案</th>
			<td colspan="3"><textarea rows="6" cols="50">ABCD</textarea><span class="red_star">*</span></td>
		</tr>
		<tr class="optionTr_show" id="optionTr">
			<th>选项</th>
			<td colspan="3">
				<ol type="A" id="options">
					<li><input type="text" value="封装"></li>
					<li><input type="text" value="继承"></li>
					<li><input type="text" value="多态"></li>
					<li><input type="text" value="抽象"></li>
				</ol>
			</td>
		</tr>
	</table>
</body>
</html>