<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"  %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>我的信息-查看试卷详情</title>
	<link rel="stylesheet" href="exam/CSS/exam_online.css">
	<link rel="stylesheet" href="exam/CSS/exam_paper_confirm.css">
	<script type="text/javascript" src="index/js/index.js"></script>
	<script src="exam/layui/layui.all.js"></script>
	<script src="exam/js/exam_paper_confirm.js"></script>
</head>
<body>

    <!-- 考试中的中间部分 -->
    <div class="examing">
       <p class="exam_position">当前位置：我的信息 &gt; 查看试卷</p>
        <div class="exam_title">
            <span class="exam_title1"></span>
        </div>

        <ul class="exam_problems">
	        	<c:forEach items="${ques}" var="que" >
	        		<c:if test="${que.type.id == 1}">
	        		<li>
	                <span class="es1">单选</span>
	                <span class="es2"></span>
	                <ul>
	        			<c:forEach items="${danxuan}" var="dan" varStatus="i" >
	        				<li>
	                        	<span class="radio_1">
	                            	${i.count}、${dan.question.title} 
	                            			<c:forEach items="${recordDetails}" var="red" >
	                            			<c:if test="${red.question.id == dan.question.id}">
	                            				<c:choose>
	                            					<c:when test="${red.questionAnswer != dan.question.answer}"><p>错误  参考答案是：${dan.question.answer}</p></c:when>
	                            					<c:otherwise><span style="color:green;font-weight: bolder;" >正确</span></c:otherwise>
	                            				</c:choose>
	                        				</c:if>	
	                        				</c:forEach>
	                        	</span>
	                        	<ul id="ss1" class="radio_1_ul">
	                        		<c:forEach items="${dan.options}" var="opt" varStatus="seq" >
	                        			<li>
	                            			<input type="radio" <c:forEach items="${recordDetails}" var="red" >
	                        					<c:if test="${red.question.id == dan.question.id && red.answer == seq.count}">checked="checked"</c:if>
	                        				</c:forEach>  disabled="disabled" id="dan${i.count}${seq.count}" name="${dan.question.id}" 
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
	                            	<c:forEach items="${recordDetails}" var="red" >
	                            			<c:if test="${red.question.id == duo.question.id}">
	                            				<c:choose>
	                            					<c:when test="${red.questionAnswer != duo.question.answer}"><p>错误  参考答案是：${duo.question.answer}</p></c:when>
	                            					<c:otherwise><span style="color:green;font-weight: bolder;" >正确</span></c:otherwise>
	                            				</c:choose>
	                        				</c:if>	
	                        		</c:forEach>
	                        	</span>
	                        	<ul id="ss1" class="radio_1_ul">
	                        		<c:forEach items="${duo.options}" var="opt" varStatus="seq" >
	                        			<li>
	                            			<input type="checkbox" <c:forEach items="${recordDetails}" var="red" >
	                        					<c:if test="${red.question.id == duo.question.id && fn:contains(red.answer,seq.count)}">checked="checked"</c:if>
	                        				</c:forEach> disabled="disabled" id="duo${i.count}${seq.count}" name="${duo.question.id}" 
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
	                            	<c:forEach items="${recordDetails}" var="red" >
	                            			<c:if test="${red.question.id == pan.question.id}">
	                            				<c:choose>
	                            					<c:when test="${red.questionAnswer != pan.question.answer}"><p>错误  参考答案是：${pan.question.answer}</p></c:when>
	                            					<c:otherwise><span style="color:green;font-weight: bolder;" >正确</span></c:otherwise>
	                            				</c:choose>
	                        				</c:if>	
	                        		</c:forEach>
	                        	</span>
	                        	<ul id="ss1" class="radio_1_ul">
	                        		<li>
	                            		<input <c:forEach items="${recordDetails}" var="red" >
	                        					<c:if test="${red.question.id == pan.question.id && red.answer == '对'}">checked="checked"</c:if>
	                        				</c:forEach> disabled="disabled" id="panTrue${i.count}" type="radio" name="${pan.question.id}" value="对" />
	                            		<label for="panTrue${i.count}" >对</label>
	                            	</li>
	                            	<li>
	                            		<input <c:forEach items="${recordDetails}" var="red" >
	                        					<c:if test="${red.question.id == pan.question.id && red.answer == '错'}">checked="checked"</c:if>
	                        				</c:forEach> disabled="disabled" id="panFalse${i.count}" type="radio" name="${pan.question.id}" value="错" />
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
	                            	${i.count}、${tian.question.title}  <p>待批改！参考答案是：${tian.question.answer} </p>
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${tian.question.id}" rows="4" cols="100"><c:forEach items="${recordDetails}" var="red" ><c:if test="${tian.question.id == red.question.id}">${red.answer}</c:if></c:forEach></textarea>
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
	                            	${i.count}、${jian.question.title} <p>待批改！参考答案是：${jian.question.answer} </p>
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${jian.question.id}" rows="4" cols="20"></textarea>
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
	                            	${i.count}、${ji.question.title} <p>待批改！参考答案是：${ji.question.answer} </p>
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${ji.question.id}" rows="4" cols="20"></textarea>
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
	                            	${i.count}、${lun.question.title} <p>待批改！参考答案是：${lun.question.answer} </p>
	                        	</span>
	                        	<ul id="ss1" >
	                        		<li>
	                            		<label>答案</label>
	                            		<textarea name="${lun.question.id}" rows="4" cols="20"></textarea>
	                            	</li>
	                        	</ul>
	                   		</li>
	        			</c:forEach>
	        		</ul>
	            	</li>
	            	<br>
	        		</c:if>
	        		
	        	</c:forEach>
	        	<div style="bottom:10px" class="exam_button">
            		<a class="exam_button1" href="fontUser?method=myMsg"  >返回</a>
        		</div>
	        </ul>

        
    </div>
</body>
</html>