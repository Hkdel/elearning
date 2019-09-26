<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tag.jsp"%>
<!DOCTYPE html>
<html>
	<!--页面的头部与尾部页面-->

	<head>
		<meta charset="utf-8" />
		<title>南昌朝腾科技在线考试系统</title>
		<link rel="shortcut icon" href="index/img/logo_browser.ico" />
		<link rel="stylesheet" href="index/css/index.css" />
		<script type="text/javascript" src="index/js/index.js"></script>
		<script type="text/javascript" src="index/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="index/layui/layui.all.js"></script>
		<script>
			function login() {
				layer.open({
					type: 2,
					title: false,
					area: ['1280px', '800px'],
					/* content: 'login/fontLogin.jsp'  *///这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
					content: ['login/fontLogin.jsp', 'no']
				});
			}
		</script>

	</head>

	<body>
		<div id="top">
			<!--logo图片部分-->
			<div class="logo"><img src="index/img/logo .png" /></div>
			<!--菜单标题选项卡部分-->
			<div class="menu">
				<ul>
					<li style="margin-left: none; border-bottom: 4px solid #0d8fdd;">
						<a href="javaScript:void(0)" onclick="switchMenu(0)">首页</a>
					</li>
					<li>
						<a href="javaScript:void(0)" onclick="switchMenu(1)">学习</a>
					</li>
					<li>
						<a href="javaScript:void(0)" onclick="switchMenu(2)">考试</a>
					</li>
					<li>
						<a href="javaScript:void(0)" onclick="switchMenu(3)">论坛</a>
					</li>
					<li>
						<a href="javaScript:void(0)" onclick="switchMenu(4)">交友</a>
					</li>
					<li>
						<a href="javaScript:void(0)" onclick="switchMenu(5)">我的信息</a>
					</li>
				</ul>
			</div>
			<!--登陆信息部分-->
			<div class="login">
				<div class="login_icon">
					<c:if test="${empty loginUser }">
					<a  href="login/fontLogin.jsp" >
					<img src="index/img/login_icon.png" title="点我登录" alt="点击头像可切换登陆">
					</a>
					</c:if>
					<c:if test="${not empty loginUser }">
					<img src="${loginUser.photo }" style="width: 35px;height: 35px" alt="点击头像可切换登陆">
					</c:if>
					
				</div>
				<c:if test="${not empty loginUser }">
				<div class="login_info">
					<div class="info_name">${loginUser.name }</div>
					<div class="info_identity">${loginUser.role.name }</div>
				</div>
				</c:if>
				<c:if test="${not empty loginUser }">
				<div class="loginOut">
					<a href="fontUser?method=fontOut"><img src="index/img/loginout.png" /></a>
				</div>
				</c:if>
			</div>

		</div>

		<div id="main">
			<!--iframe部分，在此处插入相应的网页，宽度为1366px-->
			<!--多iframe页面命名使用menu+num命名 例如menu1.html-->
			<!--onload="this.height=ifr.document.body.scrollHeight"-->
			<!--onload=iframeLoad()-->
			<iframe name="ifr" id="ifr" style="border: none;" onload="this.height=ifr.document.body.scrollHeight" width="100%"  scrolling="no" src="index/menu0.jsp"></iframe>
			
		</div>
		
		
		<!--网站页面底部公司信息-->
		<div id="fotter" style="background-color: #65a2fd;">
			<div class="company_info">
				<div class="company_name">南昌朝腾科技有限公司</div>
				<div class="company_tel">
					<ul>
						<li>关于朝腾</li>
						<li class="line"></li>
						<li>加入朝腾</li>
						<li class="line"></li>
						<li>联系我们</li>
						<li>咨询服务热线</li>
						<li>0791-88357179</li>
					</ul>
				</div>
				<div class="company_copy">赣ICP备 14001991号 CopyRight&copy;2010-2018 南昌朝腾科技有限公司</div>
			</div>
		</div>
	</body>

</html>