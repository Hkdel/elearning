function $$(s){
    return document.getElementById(s);
}

//实现考试科目的切换效果
function bkChange(num) {
	var examList = $$("examChange").getElementsByTagName("li");
	for(var i = 0; i < examList.length; i++) {
		if(i == num) {
			examList[i].style.backgroundColor="#65a2fd";
		} else {
			examList[i].style.backgroundColor = "";
		}
	}
}

//搜索框内容的显示隐藏
function disappear(){
    var values=$$("values");
    values.value="";
}
function visiabled(){
    var values=$$("values");
    values.value="请输入试卷名称";
}
//确认考试开始
function click_confirm(){
	var flag = confirm("你确认开始考试吗？（注意：一旦开始即开始计时，不能暂停考试）");
	/*if(flag){
		location.href="exam/exam_online.jsp";
	}*/
}
