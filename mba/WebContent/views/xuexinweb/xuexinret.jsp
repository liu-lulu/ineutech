<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
		/* font-family: "华文细黑"; */
		font-size: 20px;
		color: black;
		/* background:url("../image/back.jpg") no-repeat; */
		background-size: 100% 100%;
		position: absolute;
	}
	.main{
		width:100%;
		height:98%;
		overflow-y:auto;
		position:fixed;
	}
	.content{
		width:50%;
		height:98%;
		margin:1% 25%;
		text-align:center;
		/* position:fixed; */
		
	}
	
	.main::-webkit-scrollbar {
        display: none;
    }
	
	.title{
		width:100%;
		/* height:5%; */
		margin:1% auto;
		float:left;
	}
	
	.detail1{
		width:100%;
		/* height:20%; */
		margin:1% auto;
		text-align:left;
		float:left;
	}
	
	.detail2{
		width:100%;
		/* height:20%; */
		margin:1% auto;
		text-align:left;
		float:left;
	}
	
	.detail3{
		width:100%;
		/* height:50%; */
		margin:1% auto;
		text-align:left;
		float:left;
		margin-top:50px;
	}
	
	.detail4{
		margin:1% auto;
		text-align:left;
		float:left;
		margin-top:50px;
	}
	
	.data{
		margin-left:5%;
		height:90%;
	}
	
	.webInfo tbody{
	  display:block;
	  overflow:auto;
	  /* height:200px; */
	  max-height:200px;
	  width:100%;
	}
	
	.webInfo tbody tr{
		height:10px;
		font-size: 15px;
		display: table;
		width: 100%;
		margin-top:5px;
	}
	.webInfo thead tr{
		display:table;
		width:100%;
		table-layout:fixed;
		}
	
	.webInfo thead {
		width: calc( 100% - 1em )
		}
		
	.webInfo tbody tr td{
		
	    position:relative; 
	    z-index:2;
	}
	.webInfo tbody tr td:hover{
	    z-index:3;
	    background:none; 
	}
	.webInfo tbody tr td .tdtip  {
	    display: none;
	}
	.webInfo tbody tr td:hover .tdtip  { 
	    display: block;
	    position: absolute;
	    top: 8px; 
	    background-color: whitesmoke;
	    color: royalblue;
	    word-break: break-all; 
	}
	
/* 	.webInfo tbody {
		 height:195px;
		overflow-y:scroll; 
	
	}
	
	.webInfo>tbody>tr{
		height:10px;
		font-size: 15px;
	}
	
	.webInfo>thead,>tbody>tr {
		display:table;
		width:100%;
		table-layout:fixed;
	}
	
	.webInfo>thead {
	width: calc( 100% - 1em )
	}
	
.webInfo tbody tr td{
    position:relative; 
    z-index:2;
}
.webInfo tbody tr td:hover{
    z-index:3;
    background:none; 
}
.webInfo tbody tr td .tdtip  {
    display: none;
}
.webInfo tbody tr td:hover .tdtip  { 
    display: block;
    position: absolute;
    top: 8px; 
    background-color: whitesmoke;
    color: royalblue;
    word-break: break-all; 
}*/

	
	/* table thead th{ background:#ccc;} */
	
		#tab_bar {
			width: 400px;
			height: 20px;
			/* float: left; */
		}
		#tab_bar ul {
			padding: 0px;
			margin: 0px;
			height: 25px;
			text-align: center;
		}
		
		#tab_bar li {
			list-style-type: none;
			float: left;
			width: 133.3px;
			height: 25px;
			background-color: gray;
		}


</style>

</head>
<body>
	<div class="main">
	<div class="content">
		<div class="title">
			${name}
		</div>
		
		<div class="detail1">
			<div>
			性别:${sex }<br>
			证件号码:暂时隐藏<br>
			民族:${nation }<br>
			出生日期:${birthday }<br>
			院校:${academy }<br>
			层次:${level }<br>
			院系:${faculty }<br>
			班级:${classInfo }<br>
			专业:${major }<br>
			学号:${studentNo }<br>
			形式:${form }<br>
			入学时间:${inTime }<br>
			学制:${educational }<br>
			类型:${type }<br>
			学籍状态:${status }<br>
			</div>
		</div>

		
		
	</div>
	</div>
</body>
</html>