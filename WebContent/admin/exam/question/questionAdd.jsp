<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../tag.jsp"  %>  
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
				options.innerHTML += "<li><input type='text'></li>";
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
		<button class="common_button" onclick="save('questionList.html');">保存</button>
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
				<span class="red_star">*</span>
			</td>
			<th>题型</th>
			<td>
				<select onchange="selectQuestion(this.value)">
					<option>请选择</option>
					<option value="1">单选题</option>
					<option value="2">多选题</option>
					<option value="3">判断题</option>
					<option value="4">填空题</option>
					<option value="5">简答题</option>
				</select>
				<span class="red_star">*</span>
			</td>
		</tr>
		<tr>
			<th>题目</th>
			<td colspan="3"><input type="text" size="80"><span class="red_star">*</span></td>
		</tr>
		<tr>
			<th>答案</th>
			<td colspan="3"><textarea rows="6" cols="50"></textarea><span class="red_star">*</span></td>
		</tr>
		<tr class="optionTr_hidden" id="optionTr">
			<th>选项</th>
			<td colspan="3">
				<button class="common_button" onclick="addOption();">新增选项</button>
				<button class="common_button" onclick="delOption();">删除选项</button>
				<ol type="A" id="options">
					<li><input type="text"></li>
				</ol>
			</td>
		</tr>
	</table>
</body>
</html>