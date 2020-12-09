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


<br/>1.上传文件接口
<br/>http://localhost:8090/upload/pad/upload.do
 
<form  action="pad/upload.do" method="post" enctype="multipart/form-data" target="_blank">
<input type="file" name="files">
<input type="file" name="files">
type:<input type="text" name="type">
<input type="submit" value="upload"/>
</form>
<br/>参数:type  >>1逸夫学生端 2逸夫教师端 3逸夫评分端 4逸夫绑定端 5盛大学生端 6盛大教师端
<br/>
返回结果:state:0.失败 1.成功 2.请求出现未知异常 3没有上传文件
<br/>
<br/>

<!-- <br/>2.上传学生分数
<br/>http://localhost:8090/upload/pad/saveScore.do
 
<form  action="pad/saveScore.do" method="post" target="_blank">
score:<input type="text" name="score">
<input type="submit" value="submit"/>
</form>
<br/>参数:score  >>{"comment1":"备注1","comment2":"备注2","content":"内容","lqid":"1-1","pad_name":"pad名","qid":"1","right_answer":"正确答案","score":5,"sid":"1000001","stu_answer":"学生答案","type":1,"uuid":"uuid","wd1":"维度1","wd2":"维度2","wd3":"维度3"}
<br/>其中:sid 考生ID;qid 题号;lqid 小题号;score 分数;type 题型;
<br/>
返回结果:state: 1.成功 2.请求出现未知异常 3没有数据
<br/>
<br/> -->


<br/>3.获取当前进度信息
<br/>http://localhost:8090/upload/pad/getInfo.do
 
<form  action="pad/getInfo.do" method="post" target="_blank">
<input type="submit" value="submit"/>
</form>
<br/>
返回结果:info:当前进度信息
<br/>
<br/>
<br/>
</body>
</html>