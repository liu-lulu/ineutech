<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<link rel="shortcut icon" href="../image/favicon.ico">
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
 
</head>
       
<body class="home">


<br/>--备份--
 
<form  action="backup/upload.do" method="post" enctype="multipart/form-data" target="_blank">
<input type="file" name="file"><br>
<input type="submit" value="upload"/>
</form>

<br/>
返回结果:state:0.失败 1.成功 2.请求出现未知异常 3没有上传文件
<br/>
<br/>


</body>
</html>