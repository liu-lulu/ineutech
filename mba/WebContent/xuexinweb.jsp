<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="image/logo.png" type="image/x-icon">
<title></title>
<style type="text/css">
	html,body{
		width:100%;
    	height: 100%;
    }
	body{
		padding: 0;
        margin: 0;
		font-family: "华文细黑";
		font-size: 15px;
		color: black;
		background:url("<%=basePath %>image/back.jpg") no-repeat;
		background-size: 100% 100%;
		position: absolute;
	}
	
	.content{
		width:60%;
		height:50%;
		margin:25% 20%;
		text-align:center;
		position:fixed;
		/* border:1px solid #000; */
	}
</style>
</head>
<body>
	<div class="content">
		<form action="<%=basePath %>result/xuexin.do" method="post">
			<span style="color:red;">${msg }</span><br>
			验证码：<input type="text" name="code" id="code" value="${code}"><br><br>
			<input type="submit">
		</form>
	</div>
</body>
</html>