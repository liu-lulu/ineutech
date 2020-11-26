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
接口:http://localhost:8090/gaokaopai/result/score.do
<br>
<br>
<form action="pad/score.do" target="_blank" method="post">
schoolName:<input type="text" name="schoolName" id="schoolName" value="清华大学"><br/>
schoolUrl:<input type="text" name="schoolUrl" id="schoolUrl" value="http://www.gaokaopai.com/daxue-luquxian-2216.html"><br/>
type:<input type="text" name="type" id="type" value="1">(1:分数线 2:专业分数线 不传则下载所有)<br/>
<br/>
<input type="submit"  value="确认"/>
</form>

<br><br><br>
接口:http://localhost:8090/gaokaopai/result/degreeRank.do
<br>
<br>
<form action="pad/degreeRank.do" target="_blank" method="post">
degree:<input type="text" name="degree" id="degree" value="本科">(学历:大专 本科 硕士 博士)<br/>
<br/>

本科学历(第一学历):<br/>
schoolName:<input type="text" name="schoolName" id="schoolName" value="清华大学"><br/>
major:<input type="text" name="major" id="major" value=""><br/>
highSchoolLocation:<input type="text" name="highSchoolLocation" id="highSchoolLocation" value="">(高中所在地)<br/>
<br/>

硕士学历:<br/>
masterSchoolName:<input type="text" name="masterSchoolName" id="masterSchoolName" value="清华大学"><br/>
masterMajor:<input type="text" name="masterMajor" id="masterMajor" value=""><br/>
<br/>

博士学历:<br/>
doctorSchoolName:<input type="text" name="doctorSchoolName" id="doctorSchoolName" value="清华大学"><br/>
doctorMajor:<input type="text" name="doctorMajor" id="doctorMajor" value=""><br/>
<br/>
<input type="submit"  value="确认"/>
</form>
</body>
</html>