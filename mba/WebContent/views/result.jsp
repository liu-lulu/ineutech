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
<script type="text/javascript">
	function myclick(index){
		var div1 = document.getElementById("web1");
		var div2 = document.getElementById("web2");
		if(index==1){
			if(div1.style.display=='none'){
				div1.style.display = "block";
			}
			if(div2.style.display=='block'){
				div2.style.display = "none";
			}
			
			/* if(div1.style.visibility=='hidden'){
				div1.style.visibility = "visible";
			}
			if(div2.style.visibility=='visible'){
				div2.style.visibility = "hidden";
			} */
			
			
		}else if(index==2){
			if(div1.style.display=='block'){
				div1.style.display = "none";
			}
			if(div2.style.display=='none'){
				div2.style.display = "block";
			}
			
			/* if(div1.style.visibility=='visible'){
				div1.style.visibility = "hidden";
			}
			if(div2.style.visibility=='hidden'){
				div2.style.visibility = "visible";
			} */
			
		}
		
	}

</script>
</head>
<body>
	<div class="main">
	<div class="content">
		<div class="title">
			${memberNo}:${fn:substring(result.info.name, 0, 1)}
			<c:forEach begin="1" end="${fn:length(result.info.name)-1}">
      			*
			</c:forEach>
			</div>
		
		<div class="detail1">
			<div>教育背景<br></div>
			<div class="data">
				 专业评分:
				 <c:if test="${empty result.majorScore}">--</c:if>
				 <c:if test="${not empty result.majorScore}">${result.majorScore}</c:if>
				 <br>
				
				 <table style="width:95%;height:95%;">
					<tr>
						<th style="width:10%">学历</th>
						<th style="width:35%">学校</th>
						<th style="width:35%">专业</th>
						<th style="width:10%">级别</th>
						<th style="width:10%">得分</th>
					</tr>
					
					<c:forEach var="info" items="${result.degrees}">
						<tr style="margin-top:5px;">
							<td>${info.degree }</td>
							<td>${info.school_name }</td>
							<td>${info.major }</td>
							<td>${info.rank }</td>
							<td>${info.score}</td>
						</tr>
					</c:forEach>
				</table>
				<div style="text-align:right;">评分参照</div>
			</div>
		</div>
		<div class="detail2">
			<div>第一学历评分<br></div>
			<div class="data">
				
				第一学历录取省内得分率推断 &nbsp;&nbsp;
			 		<c:if test="${not empty result.firstDegreeResult.scoreRank}"> 
				 		<fmt:formatNumber value="${fn:substring(result.firstDegreeResult.scoreRank, 0, 4)}" type="percent"/>
			 		</c:if>
			 		<c:if test="${empty result.firstDegreeResult.scoreRank}"> -- </c:if>
				&nbsp;&nbsp;评分 &nbsp;&nbsp;${result.firstDegreeResult.degreeScore}
				&nbsp;&nbsp;等级 &nbsp;&nbsp;${result.firstDegreeResult.degreeRank}
				<br>
				<c:if test="${not empty result.firstDegreeResult.locationRank}">
					该省本科得分率参考依据<br>
					<table>
						<tr>
							<c:forEach items="${result.firstDegreeResult.locationRank}" var="locationRank">
								<th style="margin-left:10px;">${locationRank.key}</th>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach items="${result.firstDegreeResult.locationRank}" var="locationRank">
								<td style="margin-left:10px;"><fmt:formatNumber value="${locationRank.value}" type="percent"/></td>
							</c:forEach>
						</tr>
					</table>
				</c:if>
				<c:if test="${not empty result.firstDegreeResult.ranking}">
					学校排名 &nbsp;&nbsp;
					<c:if test="${(empty result.firstDegreeResult.ranking)||result.firstDegreeResult.ranking==' '}">--</c:if>
					${result.firstDegreeResult.ranking} &nbsp;&nbsp;<br>
					QS世界大学排名参考依据<br>
						<div style="float:left;margin-left:20px;">
							[1,100)<br>
							A+<br>
						</div>
						<div style="float:left;margin-left:20px;">
							[100,200)<br>
							A<br>
						</div>
						<div style="float:left;margin-left:20px;">
							[200,300)<br>
							A-<br>
						</div>
						<div style="float:left;margin-left:20px;">
							[300,500)<br>
							B+<br>
						</div>
						<div style="float:left;margin-left:20px;">
							>=500<br>
							B<br>
						</div>
				</c:if>
			</div>
			
		</div>
		<div class="detail3">
			<div>个人荣誉<br></div>
			<div class="data">
				<table class="webInfo">
					<thead>
						<tr>
							<th style="width:45%">荣誉</th>
							<th style="width:45%">颁发机构</th>
							<th style="width:10%">授予日期</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="info" items="${result.honors}">
							<tr>
								<td style="width:45%">${fn:substring(info.honor, 0, 30)} <span class="tdtip">${info.honor }</span></td>
								<td style="width:45%">${fn:substring(info.issuing_authority, 0, 30)}<span class="tdtip">${info.issuing_authority }</span></td>
								<td style="width:10%;text-align: center;">${info.grant_date }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="detail4">
			<div>工作背景：<br></div>
			<div class="data">
				公司:${result.info.company}<br>
				职位:${result.info.job}<br>
				<div id="tab_bar">
					<ul>
						<li id="tab1" onclick="myclick(1)" style="background-color: lightgray">
							个人信息
						</li>
						<li id="tab2" onclick="myclick(2)">
							公司信息
						</li>
						
					</ul>
				</div>
				<div id="web1" style="display:block;">
					<c:if test="${empty result.workInfo.info }">
						个人信息 百度条目：0<br>
					</c:if>
					<c:if test="${not empty result.workInfo.info }">
						个人信息 百度条目：${result.workInfo.info.totalcount }<br>
						<c:if test="${result.workInfo.info.totalcount>0 }">
							<table class="webInfo" >
								<thead>
									<tr>
										<th style="width:45%">标题</th>
										<th style="width:45%">描述</th>
										<th style="width:10%">详情链接</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="work" items="${result.workInfo.info.result}">
										<tr>
											<td style="width:45%">${fn:substring(work.title, 0, 30)}<span class="tdtip">${work.title }</span></td>
											<td style="width:45%">${fn:substring(work.describe, 0, 30)}<span class="tdtip">${work.describe }</span></td>
											<td style="width:10%;text-align: center;"><a href="${work.link }" target="_blank">查看详情</a></td>
										</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</c:if>
					</c:if>
				</div>
				
				<div id="web2" style="display:none;">
					<c:if test="${empty result.workInfo.companyInfo }">
						公司信息 百度条目：0<br>
					</c:if>
					<c:if test="${not empty result.workInfo.companyInfo }">
						公司信息 百度条目：${result.workInfo.companyInfo.totalcount }<br>
						<table class="webInfo" >
							<thead>
								<tr>
									<th style="width:45%">标题</th>
									<th style="width:45%">描述</th>
									<th style="width:10%">详情链接</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="work" items="${result.workInfo.companyInfo.result}">
									<tr>
										<td style="width:45%">${fn:substring(work.title, 0, 30)}<span class="tdtip">${work.title }</span></td>
										<td style="width:45%">${fn:substring(work.describe, 0, 30)}<span class="tdtip">${work.describe }</span></td>
										<td style="width:10%;text-align: center;"><a href="${work.link }" target="_blank">查看详情</a></td>
									</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>