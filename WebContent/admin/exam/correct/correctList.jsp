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
	<div class="page_title">试卷批改</div>
	<div class="button_bar">
		<button class="common_button" onclick="reload()">查询</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>学生姓名</th>
			<td>
				<input type="text">
			</td>
			<th>试卷名称</th>
			<td><input type="text"></td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>学生姓名</th>
			<th>科目名称</th>
			<th>试卷名称</th>
			<th>考试时间</th>
			<th>操作</th>
		</tr>
		<tr>
			<td class="list_data_number">1</td>
			<td class="list_data_text">林允</td>
			<td class="list_data_text">java</td>
			<td class="list_data_text">面向对象基础</td>
			<td class="list_data_text">2017-12-06</td>
			<td class="list_data_text">
				<a href="correct.html">批改</a>
			</td>
		</tr>
		<tr>
			<td class="list_data_number">2</td>
			<td class="list_data_text">欧阳娜娜</td>
			<td class="list_data_text">html</td>
			<td class="list_data_text">html标签应用</td>
			<td class="list_data_text">2017-12-06</td>
			<td class="list_data_text">
				<a href="correct.html">批改</a>
			</td>
		</tr>
		<tr>
			<th colspan="8">
				<div class="pager">
					<div class="pager_left">
						共2条记录 每页10条
						第1页/共5页
						转到<input value="1" size="1" />页
						<button width="20" onclick="reload();">GO</button>
					</div>
					<div class="pager_right">
						<button class="common_button" onclick="">首页</button>
						<button class="common_button" onclick="">上一页</button>
						<button class="common_button" onclick="">下一页</button>
						<button class="common_button" onclick="">尾页</button>
					</div>
				</div>
			</th>
		</tr>
	</table>
</body>
</html>