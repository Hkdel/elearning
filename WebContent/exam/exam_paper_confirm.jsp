<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tag.jsp"  %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>考试试卷-已提交试卷</title>
	<link rel="stylesheet" href="exam/CSS/exam_online.css">
	<link rel="stylesheet" href="exam/CSS/exam_paper_confirm.css">
	<script src="exam/layui/layui.all.js"></script>
	<script src="exam/js/exam_paper_confirm.js"></script>
</head>
<body>

    <!-- 考试中的中间部分 -->
    <div class="examing">
        <p class="exam_position">当前位置：考试 &gt; JS第一阶段</p>
        <div class="exam_title">
            <span class="exam_title1">JS第一阶段</span>
            <span class="exam_title2">（剩余考试时间：</span>
            <span class="minutes">10</span>
            <span class="exam_title2">分钟）</span>
        </div>

        <ul class="exam_problems">
            <li>
                <span class="es1">单选题</span>
                <span class="es2">（每题10分，共5题）</span>
                <ul>
                    <li>
                        <span class="radio_1">
                            1、简述ajax中js脚本缓存问题该如何解决？
                            (<input type="text" value="A" name="aa">)
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：A </p>
                        </span>
                        <ul class="radio_1_ul">
                            <li>A、111</li>
                            <li>B、222</li>
                            <li>C、333</li>
                            <li>D、444</li>
                        </ul>
                    </li>
                    <li>
                        <span class="radio_1">
                            2、ajax的优点？
                            (<input type="text" value="A" name="aa">)
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：A </p>
                        </span>
                        <ul class="radio_1_ul">
                            <li>A、111</li>
                            <li>B、222</li>
                            <li>C、333</li>
                            <li>D、444</li>
                        </ul>
                    </li>
                    <li>
                        <span class="radio_1">
                            3、谈谈你的JS的理解？
                            (<input type="text" value="A" name="aa">)
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：A </p>
                        </span>
                        <ul class="radio_1_ul">
                            <li>A、111</li>
                            <li>B、222</li>
                            <li>C、333</li>
                            <li>D、444</li>
                        </ul>
                    </li>
                    <li>
                        <span class="radio_1">
                            4、ajax的优点？
                            (<input type="text" value="A" name="aa">)
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：D </p>
                        </span>
                        <ul class="radio_1_ul">
                            <li>A、111</li>
                            <li>B、222</li>
                            <li>C、333</li>
                            <li>D、444</li>
                        </ul>
                    </li>
                    <li>
                        <span class="radio_1">
                            5、谈谈你的JS的理解？
                            (<input type="text" value="A" name="aa">)
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：D </p>
                        </span>
                        <ul class="radio_1_ul">
                            <li>A、111</li>
                            <li>B、222</li>
                            <li>C、333</li>
                            <li>D、444</li>
                        </ul>
                    </li>
                </ul>
            </li>
            <br>
            <li>
                <span class="es1">多选题</span>
                <span class="es2">（每题20分，共2题）</span>
                <ul>
                    <li>
                        <span class="radio_1">
                            6、简述ajax中js脚本缓存问题该如何解决？
                            (<input type="text" value="AB" name="bb">)
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：AB </p>
                        </span>
                        <ul class="radio_1_ul">
                            <li>A、111</li>
                            <li>B、222</li>
                            <li>C、333</li>
                            <li>D、444</li>
                        </ul>
                    </li>
                    <li>
                        <span class="radio_1">
                            7、ajax的优点？
                            (<input type="text" value="AB" name="bb">)
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：CD </p>
                        </span>
                        <ul class="radio_1_ul">
                            <li>A、111</li>
                            <li>B、222</li>
                            <li>C、333</li>
                            <li>D、444</li>
                        </ul>
                    </li>
                </ul>
            </li>
            <br>
            <li>
                <span class="es1">判断题</span>
                <span class="es2">（每题10分，共2题）</span>
                <ul class="choose">
                    <li>
                        <span class="radio_1">
                            8、简述ajax中js脚本缓存问题该如何解决？
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：√ </p>
                        </span><br>
                        <input type="radio" id="male1" name="pd1" checked="checked" /> <label for="male1">对</label>
					    <input type="radio" id="male2" name="pd1" /> <label for="male2">错</label>
                    </li>
                    <li>
                        <span class="radio_1">
                            9、ajax的优点？
                            &nbsp;&nbsp;&nbsp;
                            <p>参考答案是：× </p>
                        </span><br>
                        <input type="radio" id="male3" name="pd2" checked="checked" /> <label for="male3">对</label>
					    <input type="radio" id="male4" name="pd2" /> <label for="male4">错</label>
                    </li>
                </ul>
            </li>
        </ul>

        <div class="exam_button">
            <input class="exam_button1" type="submit" value="查看分数" onclick="examBox();">
        </div>
    </div>
</body>
</html>