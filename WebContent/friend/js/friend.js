
function $$(s) {
	return document.getElementById(s);
}
//实现菜单栏的切换效果
function showdiv(num) {
	var menuList = document.getElementsByTagName("li");
	var aList = document.getElementsByTagName("a");
	var frameSrc = document
	for(var i = 0; i < menuList.length; i++) {
		if(i == num) {
			menuList[i].style.border = "1px solid #65a2fd";
			aList[i].style.color="#65a2fd";

			switch (num) {
			case 0:
				$$("ifr").src = "friend/frontFriend?method=unReadList";
				break;
			case 1:
				$$("ifr").src = "friend/frontFriend?method=friendApplyList";
				break;
			case 2:
				$$("ifr").src = "friend/frontFriend?method=myFriendList";
				break;
			case 3:
				$$("ifr").src = "friend/frontFriend?method=blackList";
				break;
			case 4:
				$$("ifr").src = "friend/frontFriend?method=notFriendList";
				break;
			default:
				break;
			}
		} else {
			menuList[i].style.border = "none";
			aList[i].style.color="#575757";
		}
	}
}

function iframeLoad() {
	document.getElementById("ifr").height = document.getElementById("ifr").contentWindow.document.body.scrollHeight;
}