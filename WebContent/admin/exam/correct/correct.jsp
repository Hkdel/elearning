<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>试卷批改</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../../js/common.js"></script>
	</head>
<body>
	<div class="page_title">试卷管理&nbsp; &gt; 添加试卷</div>
	<div class="button_bar">
		<button class="common_button" onclick="back();">返回</button>
		<button class="common_button" onclick="save('correctList.html');">保存</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>java</td>
			<th>试卷名称</th>
			<td>面向对象基础</td>
		</tr>
		<tr>
			<th>学生姓名</th>
			<td>林允</td>
			<th>题目总分值</th>
			<td>5分</td>
		</tr>
		<tr>
			<th>题目</th>
			<td colspan="3">栈内存与堆内存的理解？</td>
		</tr>
		<tr>
			<th>学员答案</th>
			<td colspan="3">
				栈内存与堆内存是JVM中的内存结构
				栈内存：
				1、基本数据类型
				2、引用类型的指针
				堆内存：
				引用类型的内容
			</td>
		</tr>
		<tr>
			<th>题目答案</th>
			<td colspan="3">
				栈内存与堆内存是JVM中的内存结构
				栈内存：
				1、基本数据类型
				2、引用类型的指针
				堆内存：
				引用类型的内容
			</td>
		</tr>
		<tr>
			<th>实际得分</th>
			<td colspan="3"><input type="text"><span class="red_star">*</span></td>
		</tr>
	</table>
</body>
</html>