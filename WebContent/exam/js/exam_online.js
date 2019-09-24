var time_cd = setInterval("countdown()",1000);
function $$(a){
	return document.getElementById(a);
}
var cd = parseInt('60')*60;
console.log($$("exam_time").innerHTML);

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

