function $$(s) {
	return document.getElementById(s);
}
//不关闭定时器，用于动态获取元素内容的填充高度
setInterval("getHideenHeight(),getDetailsHeight()", 0);

//获取元素内容的高度,动态增加外层content_box高度
function getDetailsHeight() {
	var detailsArr = document.getElementsByClassName("content_box_details");
	var boxArr = document.getElementsByClassName("content_box");
	for(var i = 0; i < detailsArr.length; i++) {
		boxArr[i].style.height = detailsArr[i].offsetHeight + 25 + "px";
	}

}
//显示隐藏评论


function showComments(parent) {
	var parent = parent;
	var staText = parent.children[0];
	var staTextShow = staText.innerHTML;
	var imgShow = parent.children[0].nextElementSibling;
	var hidBox = parent.children[0].nextElementSibling.nextElementSibling;
	console.log(imgShow);
	console.log(staTextShow);
	if(staTextShow == "隐藏评论") {
		staText.innerHTML = "显示评论";
		imgShow.src = "img/hidden_arrow.png";
		hidBox.style.display = "none";
	} else {
		staText.innerHTML = "隐藏评论";
		imgShow.src = "img/show_arrow.png";
		hidBox.style.display = "block";
	}
}

//获取元素内容的高度,动态增加外层comments_reply高度
//test
function getHideenHeight() {
	var boxArr = document.getElementsByClassName("content_box");
	var len = boxArr.length;
	var replyInfoHeight;
	var commentsDetHeight;
	var commentsRepHeight;

	for(var i = 1; i < len; i++) {
		replyInfoHeight = boxArr[i].getElementsByClassName("reply_info");
		commentsDetHeight = boxArr[i].getElementsByClassName("comments_details");
		commentsRepHeight = boxArr[i].getElementsByClassName("comments_reply");
		var infoH = 0;
		var comH = 0;
		for(var j = 0; j < replyInfoHeight.length; j++) {
			infoH += replyInfoHeight[j].offsetHeight;
			comH += commentsDetHeight[j].offsetHeight;
		}
		for(var k = 0; k < commentsRepHeight.length; k++) {
			commentsRepHeight[k].style.height = infoH + comH - 15 + "px";
		}
	}

}
//回复信息的校验
function replyCheck() {
	var replyCon = $$("replyContent").value;
	if(replyCon.length < 1 || (replyCon == " ")) {
		alert("请输入您要回复的信息");
	} else {
		location = "#";
	}

}


// 
function openMessage(parent) {
	var parent = parent;
	var target = parent.children[0].children[0].innerHTML;
	var inp = parent.parentNode.parentNode.parentNode.parentNode.lastElementChild.firstElementChild.firstElementChild;
	
	
	var hidden1 = parent.children[0].lastElementChild.value;
	var hidden2 = parent.parentNode.parentNode.lastElementChild.lastElementChild.value;
	
	var accId = parent.parentNode.parentNode.parentNode.parentNode.lastElementChild.children[0].children[1];
	var accContentId = parent.parentNode.parentNode.parentNode.parentNode.lastElementChild.children[0].children[2];
	inp.value = "@" + target;
	accId.value = hidden1;
	accContentId.value = hidden2 ;
	
}