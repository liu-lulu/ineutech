<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>

<link rel="stylesheet" type="text/css" href="reset-min.css">

<style type="text/css">
body{
	height:400px;
	background:#ddd;
	/*background: #50a3a2;
	background: -webkit-linear-gradient(top left, #50a3a2 0%, #53e3a6 100%);
	background: linear-gradient(to bottom right, #50a3a2 0%, #53e3a6 100%);
	width:100%;*/
}
.wrapper {
	margin:0 auto;
	width: 884px;
	padding-top:300px;
}
.loginBox {
	background-color: #FEFEFE;
	border: 1px solid #BfD6E1;
	border-radius: 5px;
	color: #444;
	font: 14px 'Microsoft YaHei','微软雅黑';
	margin: 0 auto;
	width: 388px;
}
.loginBox .loginBoxCenter {
	border-bottom: 1px solid #DDE0E8;
	padding: 24px;
}
.loginBox .loginBoxCenter p {
	margin-bottom: 10px
}
.loginBox .loginBoxButtons {
	background-color: #F0F4F6;
	border-top: 1px solid #FFF;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	line-height: 28px;
	overflow: hidden;
	padding: 20px 24px;
	vertical-align: center;
}
.loginBox .loginInput {
	border: 1px solid #D2D9dC;
	border-radius: 2px;
	color: #444;
	font: 12px 'Microsoft YaHei','微软雅黑';
	padding: 8px 14px;
	margin-bottom: 8px;
	width: 310px;
}
.loginBox .loginInput:FOCUS {
	border: 1px solid #B7D4EA;
	box-shadow: 0 0 8px #B7D4EA;
}
.loginBox .loginBtn {
	background: #2f96b4;
	border: 1px solid #98CCE7;
	border-radius: 20px;
	box-shadow:inset rgba(255,255,255,0.6) 0 1px 1px, rgba(0,0,0,0.1) 0 1px 1px;
	color: #FFF;
	cursor: pointer;
	float: right;
	font: bold 13px Arial;
	padding: 5px 14px;
}
.loginBox .loginBtn:HOVER {
	background-image: -moz-linear-gradient(to top, #B5DEF2, #85CFEE);
}
.loginBox a.forgetLink {
	color: #ABABAB;
	cursor: pointer;
	float: right;
	font: 11px/20px Arial;
	text-decoration: none;
	vertical-align: middle;
}
.loginBox a.forgetLink:HOVER {
	text-decoration: underline;
}
.loginBox input#remember {
	vertical-align: middle;
}
.loginBox label[for="remember"] {
	font: 11px Arial;
}
</style>

</head>
<body>

<div class="wrapper">
	<form id="loginform" action="loginanswer.action" method="post" >
		<div class="loginBox">
			<div class="loginBoxCenter">
				<p><label for="username">学号：</label></p>
				<p><input id="str_num" name="str_num" class="loginInput" autofocus="autofocus" required="required" autocomplete="off" placeholder="请输入学号" value="" /></p>
			</div>
			<div class="loginBoxButtons">
				<button class="loginBtn" onclick="sub();">登录</button>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
function sub(){
	var str_num = $.trim($("#str_num").val());
	if(str_num.length==0){
		alert("请输入学号！");
	}
	$("#loginform").submit();
}
</script>
	
</body>
</html>