<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/dashboard.css" >
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/main.css">
<link rel="stylesheet" href="../css/common.css">
<link rel="stylesheet" href="../css/explicit.css">
<link rel="stylesheet" href="../css/jquery.ui.all.css"> 


 <script type="text/javascript" src="../js/jquery.min.js"></script>
 <script type="text/javascript" src="../js/jquery.validate.min.js"></script>
 <script type="text/javascript" src="../js/jquery.validate.metadata.js"></script>
 <script type="text/javascript" src="../js/kkpager.js"></script>
 <script type="text/javascript" src="../js/paging.js"></script>
 <script type="text/javascript" src="../js/popshow.js"></script>
 <script type="text/javascript"  src="../js/jquery.ui.core.js"></script>
 
 <link rel="stylesheet" type="text/css" href="../css/kkpager_blue.css" />
 <link rel="stylesheet" type="text/css" href="../css/popshow.css" />
     <style type="text/css">
    #pageForm label.error{
	color: red;
	display: block;
	position: relative;
    margin: 0 auto;
    text-align: left;
}

.login-box p input:hover{ border:1px #0065ff solid;}
</style>
<script type="text/javascript">
function toDetail(info){
	$("#resultDetail").val(JSON.stringify(info));
	var form = document.getElementById('detailForm');
	form.submit();
}



</script>
<title>评分结果</title>
</head>
<body>
<div class="col-sm-6 col-sm-offset-3 col-md-8 col-md-offset-2 main">
	<div style="float:left;width: 100%;display:none;">
		<form action="detail.do" method="post" id="detailForm" target="_blank">
			<input id="resultDetail" name="resultDetail">
			<input type="submit">
		</form>
	</div>
	<div style="float:left;width: 100%;">
		<form action="scoreList.do" method="post" id="listForm">
			<div style="float:left;">
			<label>序列号：&nbsp;<input name="memberNo" id="memberNo"  type="text" value="${memberNo }"></label>
			<label>&nbsp;&nbsp;姓名：&nbsp;<input name="name" id="name"  type="text" value="${name }"></label>
			<input type="submit" value="查询" style="border-style:outset;"/><br>
			</div>
		</form>
	</div>
	<table class="table table-bordered table-hover " style="margin-top:20px; text-align:center;">
		<thead style="background:#F9F9F9;">
			<tr>
				<th style="text-align:center;">序列号</th>
				<th style="text-align:center;">姓名</th>
				<th style="text-align:center;">得分</th>
				<th style="text-align:center;">详情</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${datas }" var="data">
				<tr>
					<td>${data.key.member_serialno}</td>
					<td>${data.key.name}</td>
					<td>${data.value.totalScore}</td>
					<td><%-- <a target="_blank" href="degreeResult.do?memberNo=${data.key.member_serialno}">查看详情</a>  --%>
					<a target="_blank" onclick="toDetail(${fn:escapeXml(data.value)})" href="javascript:void(0);">查看详情</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div style="margin-top:20px;">
				<nav  style=" float:right;">
				<div id="kkpager"><jsp:include page="/common/pagebar.jsp"></jsp:include></div>
			</nav>
	</div>
	
</div>
	
</body>
</html>