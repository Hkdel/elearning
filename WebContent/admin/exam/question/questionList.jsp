<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>题目管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/common.js"></script>
	</head>
<body>
	<div class="page_title">题目管理</div>
	<div class="button_bar">
		<button class="common_button" onclick="to('questionAdd.html');">新建</button>
		<button class="common_button" onclick="reload()">查询</button>
	</div>
	<table class="query_form_table">
		<tr>
			<th>科目名称</th>
			<td>
				<select>
					<option>请选择</option>
					<option>java</option>
					<option>html</option>
				</select>
			</td>
			<th>题型</th>
			<td>
				<select>
					<option>请选择</option>
					<option>单选题</option>
					<option>多选题</option>
					<option>判断题</option>
					<option>填空题</option>
					<option>简答题</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>题目</th>
			<td><input type="text"></td>
			<th></th>
			<td></td>
		</tr>
	</table>
	<br />
	<table class="data_list_table">
		<tr>
			<th>编号</th>
			<th>科目名称</th>
			<th>题型</th>
			<th>题目</th>
			<th>题目状态</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<tr>
			<td class="list_data_number">1</td>
			<td class="list_data_text">java</td>
			<td class="list_data_text">单选题</td>
			<td class="list_data_text">面向对象的特征？</td>
			<td class="list_data_text">启用</td>
			<td class="list_data_text">汪涵</td>
			<td class="list_data_text">2017-12-06</td>
			<td class="list_data_text">
				<a href="questionUpdate.html">编辑</a>
				<a href="#">停用</a>
				<a href="javascript:del('题目')">删除</a>
				<a href="questionInfo.html">查看详情</a>
			</td>
		</tr>
		<tr>
			<td class="list_data_number">2</td>
			<td class="list_data_text">html</td>
			<td class="list_data_text">单选题</td>
			<td class="list_data_text">以下哪些是块级标签？</td>
			<td class="list_data_text">停用</td>
			<td class="list_data_text">汪涵</td>
			<td class="list_data_text">2017-12-06</td>
			<td class="list_data_text">
				<a href="questionUpdate.html">编辑</a>
				<a href="#">启用</a>
				<a href="javascript:del('题目')">删除</a>
				<a href="questionInfo.html">查看详情</a>
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