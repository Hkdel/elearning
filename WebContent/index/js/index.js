function $$(s) {
	return document.getElementById(s);
}
// 实现菜单栏的切换效果
function switchMenu(num) {
	var menuList = document.getElementsByTagName("li");
	var frameSrc = document
	for (var i = 0; i < menuList.length; i++) {
		if (i == num) {
			menuList[i].style.borderBottom = "4px solid #0d8fdd";
			if (num == 2) {
				$$("ifr").src = "exam/frontExam?method=paperList";
			} else if (num == 1) {
				$$("ifr").src = "study/frontStudy?method=noteList";
			}else if (num == 5) {
				$$("ifr").src = "fontUser?method=myMsg";
			}
			else if (num == 4) {
				$$("ifr").src = "friend/frontFriend?method=temp";
			}
			else if (num == 3) {
				$$("ifr").src = "bbs/FrontBbsServlet?method=listPlate";
			}
			else {
				$$("ifr").src = "index/menu" + num + ".jsp";
			}
		} else {
			menuList[i].style.border = "none";
		}
	}
}

function iframeLoad() {
	document.getElementById("ifr").height = document.getElementById("ifr").contentWindow.document.body.scrollHeight;
}
