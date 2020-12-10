<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="">

    <title>登录</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="<%=request.getContextPath() %>/css/font-awesome.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/css/style-responsive.css" rel="stylesheet" />

    <script  src="<%=request.getContextPath() %>/js/jquery.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath() %>/js/jquery.validate.min.js" ></script>
    <script src="<%=request.getContextPath() %>/js/jquery.validate.metadata.js" ></script>
    <script src="<%=request.getContextPath() %>/js/kkbc/user/login.js" ></script>
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    #loginform label.error{
	color: red;
	display: block;
	position: relative;
    margin: 0 auto;
    text-align: left;
}

.login-box p input:hover{ border:1px #0065ff solid;}
</style>
</head>
  <body class="login-body">
  
    <div class="container">
      <form class="form-signin" id="loginform" action="../user/login.do" method="post" role="form">
        <h2 class="form-signin-heading">登录</h2>
        <div class="login-wrap">
            <input id="loginName" name="loginName" type="text" class="form-control" placeholder="请输入用户名" autofocus>
            <input id="password" name="password" type="password" class="form-control" placeholder="请输入密码">
            <label class="checkbox">
                <input type="checkbox" value="1" name="remember"> 记住我
            </label>
            <button class="btn btn-lg btn-login btn-block" type="submit">登录</button>
        </div>
      </form>
    </div>
	
  </body>
</html>
