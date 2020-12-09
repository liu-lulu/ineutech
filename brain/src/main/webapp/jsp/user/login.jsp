<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0" />
    <title>脑电</title>
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script src="../js/jquery.validate.min.js"></script>
    <script src="../js/jquery.validate.metadata.js"></script>
	<script src="../js/hardware/user/login.js"></script>
<style type="text/css">
    *{
        margin: 0px;
        padding: 0px;
    }
    body{font-size:12px;color:#666; font-family:Arial, Helvetica, sans-serif,"宋体";  line-height:24px; } 
    .wrapper{
        width:1024px;
        margin: 0 auto;
        position: relative;
    }
    .login-box{
        width: 300px;
        height: 196px;
        left: 52%;
        bottom: 220px;
        position: absolute;
    }
    .login-box  p {
        width: 286px;
        float: left;
        margin-top: 20px;
    }
    .login-box  span {
        width: 80px;
        text-align: right;
        float: left;
    }
    .login-box input {
        padding: 2px 5px;
        border: 1px solid #CAE0F8;
        _____float: left;
    }
   .login-box p input {
        width: 180px;
        height: 24px;
    }
   .login_btn{
       padding-left: 70px; 
       margin-top: 40px;
   }
    .login-btn{ width:76px; height:27px; background:url(../image/login/login-btn.png) no-repeat; border:none; __float:right;margin-left:80px; display:inline;}
    #loginform label.error{
	color: red;;
	display: block;
	margin-left: 80px;
}

.login-box p input:hover{ border:1px #0065ff solid;}
</style>
</head>
<body>
 <div class="wrapper">
     <img src="../image/login/login-bg.jpg" width="1024" height="900">
     <form id="loginform" action="../user/login.htm" method="post">
     <div class="login-box">
       <p>
          <span>登录名：</span>
           <input id="username" name="username" type="text" value="" placeholder="请输入用户名"></input>
       </p>
       <p>
           <span>密码：</span>
           <input type="password" id="password" name="password" class="text1" placeholder="请输入密码"></input>
       </p>
       <div style="clear:both;"></div>
         <div class="login_btn">
         <input id="rm" value="1" name="remember" type="checkbox" style="float:left"/>
         <label style="float:left;margin-top: -5px;"
							for="rm">记住我</label>
              <button type="submit" class="login-btn" title="登录"></button>
         </div>
     </div>
     </form>
 </div>
</body>
</html>