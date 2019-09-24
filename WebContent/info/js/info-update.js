function $$(s){
	return document.getElementById(s);
}
function bc(m){
	var str = $$("imp2").value;
	if (str==0) {
		alert("密码不能为空");
	}else if (str.length<6) {
		alert("密码长度少于6位")
	}else{
		alert("密码符合要求")
	}
}

function dis(){
	$$("none").style.visibility="visible";
}
function dis1(){
	$$("none").style.visibility="hidden";
}



