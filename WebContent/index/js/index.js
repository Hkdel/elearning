function $$(s) {
	return document.getElementById(s);
}
//实现菜单栏的切换效果
function switchMenu(num) {
	var menuList = document.getElementsByTagName("li");
	var frameSrc = document
	for(var i = 0; i < menuList.length; i++) {
		if(i == num) {
			menuList[i].style.borderBottom = "4px solid #0d8fdd";
			$$("ifr").src = "exam/frontExam?method=paperList";
		} else {
			menuList[i].style.border = "none";
		}
	}
}

function iframeLoad() {
	document.getElementById("ifr").height = document.getElementById("ifr").contentWindow.document.body.scrollHeight;
}

