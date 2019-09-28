function reload(){
    window.location.reload();
}
function help(msg){
    alert('欢迎使用'+msg);
}

function to(url){
    window.location.href=url;
}
function back(){
    history.go(-1);
}
function subFrom(page){
	if(page != 0 ){
		document.getElementById("page").value = page;
	}
	document.getElementById("modelForm").submit();
}
function subFrom1(page){
	if(page != 0 ){
		document.getElementById("page").value = page;
	}
	document.getElementById("modelForm1").submit();
}
function submit(){
    document.getElementById("Form").submit();
}
function add(url){
    alert('新建成功！');
    to(url);
}
function del(msg){
    if (window.confirm("确认删除"+msg+"？")){
        reload();
    }
}