<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
<link rel="shortcut icon" href="img/icon.jpg">
<title>登录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<style type="text/css">
html,body{
	width:100%;
	height:100%
}
.form-group .form-item input{
	border-bottom: 1px solid white;
	border-top: 0;
	border-left: 0;
	border-right: 0;
}
.form-group .form-item{
	background-color: transparent;
}

.button-group .login-btn {
    color: #39406c;
}
</style>
<script type="text/javascript">

window.onload =function()
{
	if(!isPC()){
		$(".login-bg").css("background","url('images/login-bg2.jpg') no-repeat fixed").css("background-size","100% 100%");
	}
}

function isPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}
</script>
</head>
<body class="login-bg" >
	<div class="login-contain" style="width:100%;">
    <!-- <div class="login-header">
        <p>登录</p>
    </div> -->
    <div class="container">  
    <div class="form"> 
    <form class="form-horizontal col-xs-offset-1 col-sm-offset-2 col-md-offset-3" id="loginform" action="loginuser.do" onsubmit="return check();" method="post">
    <div class="col-xs-11 col-sm-10 col-md-9" style="background: rgb(0,87,122,0.2);">
    <div class="form-group ">
    	<div style="text-align:center;">
    		<span id="msg" style="color:red;">${msg }</span><br>
    	</div>
        <div class="form-item">
            <label for="username" style="margin-bottom: 15px;">
                <img src="img/user.png" style="margin-top:0;">
            </label>
            <input id="username" name="username" type="text" placeholder="请输入账号" style="width:85%;">
        </div>
        <div class="form-item">
            <label for="password" style="margin-bottom: 15px;">
                <img src="img/password.png" style="margin-top:0;">
            </label>
            <input id="password" name="password" type="password" placeholder="请输入密码" style="width:85%;">
        </div>
    </div>
    <div class="button-group">
        <button class="login-btn" id="doLogin">登录</button>
    </div>
    </div>
	</form>
	</div>
	</div>
	
	</div>
<%-- <script src="${pageContext.request.contextPath}/js/jquery.js"></script> --%>
<script>
     $(function(){
        $(window).resize();
    });
    //js设置居中
    $(window).resize(function(){
        $(".login-contain").css({
            position: "absolute",
           //left: ($(window).width() - $(".login-contain").outerWidth())/2,
            top: ($(window).height() - $(".login-contain").outerHeight())/2
        });
    }); 
    
    function check() {
    	var username = $("#username").val();
    	var password = $("#password").val();
    	
    	if(username.length <= 0){
    		$("#msg").text("用户名不能为空！");
    		return false;
    	}
    	if(password.length <= 0){
    		$("#msg").text("密码不能为空！");
    		return false;
    	}
    	
    	$("#loginform").submit();
    }
    

</script>
</body>
</html>