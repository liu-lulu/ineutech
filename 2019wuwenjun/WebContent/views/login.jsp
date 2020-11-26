<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
<link rel="shortcut icon" href="img/icon.png">
<title>登录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
</head>
<body class="login-bg">
	<div class="login-contain" style="position: absolute; left: 28.125px; top: 117.672px;">
    <div class="login-header">
        <!-- <p>2019吴文俊科学技术奖颁奖礼会务接待</p> -->
    </div>
    <form id="loginform" action="loginuser.do" onsubmit="return check();" method="post">
    <div class="form-group">
    	<div style="text-align:center;">
    		<span id="msg" style="color:red;">${msg }</span><br>
    	</div>
        <div class="form-item">
            <label for="username">
                <img src="img/user.png">
            </label>
            <input id="username" name="username" type="text" placeholder="请输入账号" value="${username}" style="width:90%;">
        </div>
        <div class="form-item">
            <label for="password">
                <img src="img/password.png" alt="">
            </label>
            <input id="password" name="password" type="password" placeholder="请输入密码" value="${password}" style="width:90%;">
        </div>
    </div>
    <div class="button-group">
        <button class="login-btn" id="doLogin">登录</button>
    </div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script>
    $(function(){
        $(window).resize();
    });
    //js设置居中
    $(window).resize(function(){
        $(".login-contain").css({
            position: "absolute",
            left: ($(window).width() - $(".login-contain").outerWidth())/2,
            top: ($(window).height() - $(".login-contain").outerHeight())/2
        });
    });
    
    function check() {
    	var username = $("#username").val();
    	var password = $("#password").val();
    	
    	if(username.length <= 0){
    		$("#msg").text("用户名不能为空！");
    		/* alert("用户名不能为空！"); */
    		/* layer.alert("用户名不能为空！"); */
    		return false;
    	}
    	if(password.length <= 0){
    		$("#msg").text("密码不能为空！");
    		/* alert("密码不能为空！"); */
    		/* layer.alert("密码不能为空"); */
    		return false;
    	}
    	
    	$("#loginform").submit();
    }
    

</script>
</body>
</html>