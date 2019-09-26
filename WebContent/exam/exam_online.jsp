<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"  %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>考试试卷-正在考试</title>
	<link rel="stylesheet" href="exam/CSS/exam_online.css">
	<script type="text/javascript">
	var time_cd = setInterval("countdown()",1000);
	function $$(a){
		return document.getElementById(a);
	}
	var cd = ${rule.time}*60;

	//考试计时
	function countdown(){
		cd = cd - 1;
		tamp = parseInt(cd/60);
		second = cd%60 ;
		$$("exam_time").innerHTML = tamp +'分'+second+'秒';
		if(cd == 0){
			$$("exam_paper").submit();
		}
	} 

	//选择答案（单选singoselect）ss
	function click_answer(n){

		for(var i=0;i<=3;i++){
			if(i != n ){
				this.parentNode.children[i].style.color = "#888888";
			}else{
				this.parentNode.children[i].style.color = "#65a2fd";
			}
		}
	}
	</script>
</head>
<body>
    <!-- 考试中的中间部分 -->
    <div class="examing">
        <p class="exam_position">当前位置：考试 &gt; ${rule.name}</p>
        <div class="exam_title">
            <span class="exam_title1">${rule.name}</span>
            <span class="exam_title2">（剩余考试时间：</span>
            <span id="exam_time" class="minutes">${rule.time}分钟</span>
            <span class="exam_title2">）</span>
        </div>
		<form id="exam_paper" action="exam/frontExam?method=examSubmit&id=${recordId}" method="post">
		<input type="hidden" name="credit" value="${rule.credit}" />
		<input type="hidden" name="score" value="${rule.score}" />
	        <ul class="exam_problems">
	        	<c:forEach items="${rds}" var="rd" >
	        		<c:if test="${!empty danxuan && rd.type.name == '单选题'}">
	        		<li>
	                <span class="es1">${rd.type.name}</span>
	                <span class="es2">（每题${rd.score}分，共${rd.nums}题）</span>
	                <input type="hidden" name="danxuanScore" value="${rd.score}" />
	                <ul>
	        			<c:forEach items="${danxuan}" var="dan" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${dan.question.title}
	                        	</span>
	                        	<ul id="ss1" class="radio_1_ul">
	                        		<c:forEach items="${dan.options}" var="opt" varStatus="seq" >
	                        			<li>
	                            			<input type="radio" id="dan${i.count}${seq.count}" name="${dan.question.id}" 
	                            				<c:choose>
	                        						<c:when test="${seq.count == 1}">value="A"</c:when>
	                        						<c:when test="${seq.count == 2}">value="B"</c:when>
	                        						<c:when test="${seq.count == 3}">value="C"</c:when>
	                        						<c:when test="${seq.count == 4}">value="D"</c:when>
	                        						<c:when test="${seq.count == 5}">value="E"</c:when>
	                        					</c:choose> />
	                            			<label for="dan${i.count}${seq.count}" >
	                            				<c:choose>
	                        						<c:when test="${seq.count == 1}">A、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 2}">B、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 3}">C、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 4}">D、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 5}">E、${opt.content}</c:when>
	                        					</c:choose>
	                            			</label>
	                            		</li>
	                        		</c:forEach>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        		<c:if test="${!empty duoxuan && rd.type.name == '多选题'}">
	        		<li>
	                <span class="es1">${rd.type.name}</span>
	                <span class="es2">（每题${rd.score}分，共${rd.nums}题）</span>
	                <input type="hidden" name="duoxuanScore" value="${rd.score}" />
	                <ul>
	        			<c:forEach items="${duoxuan}" var="duo" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${duo.question.title}
	                        	</span>
	                        	<ul id="ss1" class="radio_1_ul">
	                        		<c:forEach items="${duo.options}" var="opt" varStatus="seq" >
	                        			<li>
	                            			<input type="checkbox" id="duo${i.count}${seq.count}" name="${duo.question.id}" 
	                            				<c:choose>
	                        						<c:when test="${seq.count == 1}">value="A"</c:when>
	                        						<c:when test="${seq.count == 2}">value="B"</c:when>
	                        						<c:when test="${seq.count == 3}">value="C"</c:when>
	                        						<c:when test="${seq.count == 4}">value="D"</c:when>
	                        						<c:when test="${seq.count == 5}">value="E"</c:when>
	                        					</c:choose> />
	                            			<label for="duo${i.count}${seq.count}" >
	                            				<c:choose>
	                        						<c:when test="${seq.count == 1}">A、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 2}">B、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 3}">C、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 4}">D、${opt.content}</c:when>
	                        						<c:when test="${seq.count == 5}">E、${opt.content}</c:when>
	                        					</c:choose>
	                            			</label>
	                            		</li>
	                        		</c:forEach>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        		<c:if test="${!empty panduan && rd.type.name == '判断题'}">
	        		<li>
	                <span class="es1">${rd.type.name}</span>
	                <span class="es2">（每题${rd.score}分，共${rd.nums}题）</span>
	                <input type="hidden" name="panduanScore" value="${rd.score}" />
	                <ul>
	        			<c:forEach items="${panduan}" var="pan" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${pan.question.title}
	                        	</span>
	                        	<ul id="ss1" class="radio_1_ul">
	                        		<li>
	                            		<input id="panTrue${i.count}" type="radio" name="${pan.question.id}" value="对" />
	                            		<label for="panTrue${i.count}" >对</label>
	                            	</li>
	                            	<li>
	                            		<input id="panFalse${i.count}" type="radio" name="${pan.question.id}" value="错" />
	                            		<label for="panFalse${i.count}" >错</label>
	                            	</li>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        		<c:if test="${!empty tiankong && rd.type.name == '填空题'}">
	        		<li>
	                <span class="es1">${rd.type.name}</span>
	                <span class="es2">（每题${rd.score}分，共${rd.nums}题）</span>
	                <ul>
	        			<c:forEach items="${tiankong}" var="tian" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${tian.question.title}
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${tian.question.id}" rows="4" cols="100"></textarea>
	                            	</li>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        		<c:if test="${!empty jianda && rd.type.name == '简答题'}">
	        		<li>
	                <span class="es1">${rd.type.name}</span>
	                <span class="es2">（每题${rd.score}分，共${rd.nums}题）</span>
	                <ul>
	        			<c:forEach items="${jianda}" var="jian" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${jian.question.title}
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${jian.question.id}" rows="4" cols="100"></textarea>
	                            	</li>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        		<c:if test="${!empty jisuan && rd.type.name == '计算题'}">
	        		<li>
	                <span class="es1">${rd.type.name}</span>
	                <span class="es2">（每题${rd.score}分，共${rd.nums}题）</span>
	                <ul>
	        			<c:forEach items="${jisuan}" var="ji" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${ji.question.title}
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${ji.question.id}" rows="4" cols="100"></textarea>
	                            	</li>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        		<c:if test="${!empty lunshu && rd.type.name == '论述题'}">
	        		<li>
	                <span class="es1">${rd.type.name}</span>
	                <span class="es2">（每题${rd.score}分，共${rd.nums}题）</span>
	                <ul>
	        			<c:forEach items="${lunshu}" var="lun" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${lun.question.title}
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${lun.question.id}" rows="4" cols="100"></textarea>
	                            	</li>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        	</c:forEach>
	        	<div class="exam_button">
	        	<!--提交到服务器需更改form的action值以及启用以下代码-->
	        	<input type="submit" class="exam_button1" value="提交试卷" >
	        	<input type="button" class="exam_button2" onclick="location.href='exam/frontExam?method=examClose&id=${recordId}'" value="关闭试卷" />
	        </div>
	        </ul>
	    </form>
    </div>
</body>
</html>