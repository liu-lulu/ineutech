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
		width:70%;
		height:98%;
		margin:1% 15%;
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
		/* margin-top:50px; */
		width: 100%;
	}
	
	.data{
		margin-left:5%;
		height:90%;
		float:left;
		width:95%;
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
		<span style="color:red;">${msg }</span><br>
		<c:if test="${not empty result}">
		<div class="title">
			${result.info.member_serialno}：${fn:substring(result.info.name, 0, 1)}
			<c:forEach begin="1" end="${fn:length(result.info.name)-1}">
      			*
			</c:forEach>
		</div>
		<div style="text-align:left;width: 95%;">
			评分一览：<c:if test="${ empty type}">${fn:substring(result.totalScore, 0, 5)}/61</c:if>
			<c:if test="${type==1}">${fn:substring(result.majorScore+result.firstDegrees.degreeScore+result.interExam, 0, 5)}/28</c:if>
			<c:if test="${type==2}">${fn:substring(result.companyScore+result.workExpScore+result.manageExpScore+result.jobScore+result.certificateScore, 0, 5)}/33</c:if>
			<br>
			<div style="margin-left:5%;">
			<c:if test="${empty type || type==1}">学位学历：<c:if test="${empty result.majorScore}">--</c:if><c:if test="${not empty result.majorScore}">${result.majorScore}/10</c:if>，第一学历：${result.firstDegrees.degreeScore}/16，国际考试：${result.interExam}/2<br></c:if>
			<c:if test="${empty type || type==2}">公司情况：${result.companyScore}/10，工作经验：${result.workExpScore}/4，管理经验：${result.manageExpScore}/10，职务：${result.jobScore}/8，职业发展培训：${result.certificateScore}/1<br></c:if>
			<br>
			</div>
		</div>
		
		<c:if test="${empty type || type==1}">
		<div class="detail1">
			<div>教育背景<br></div>
			<div class="data">
				 学位学历得分:
				 <c:if test="${empty result.majorScore}">--</c:if>
				 <c:if test="${not empty result.majorScore}"><fmt:formatNumber value="${result.majorScore}" type="number" maxFractionDigits="3"/>/10</c:if>
				
				 <br>
				
				 <table style="width:95%;height:95%;border: 1px solid black;border-spacing: 0px;">
					<tr style="border: 1px solid black;">
						<th style="width:7%;border: 1px solid black;">学历</th>
						<th style="width:7%;border: 1px solid black;">学位</th>
						<th style="width:7%;border: 1px solid black;">学习形式</th>
						<th style="width:20%;border: 1px solid black;">学校</th>
						<th style="width:20%;border: 1px solid black;">第一学科</th>
						<th style="width:20%;border: 1px solid black;">专业</th>
						<th style="width:8%;border: 1px solid black;">级别</th>
						<th style="width:8%;border: 1px solid black;">得分</th>
					</tr>
					
					<c:forEach var="info" items="${result.degrees}">
						<tr style="margin-top:5px;border: 1px solid black;">
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.degree_type}">${info.degree_type }</c:if>
							<c:if test="${ empty info.degree_type}">--</c:if>
							</td>
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.degree}">${info.degree }</c:if>
							<c:if test="${ empty info.degree}">--</c:if>
							</td>
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.learn_format}">${info.learn_format }</c:if>
							<c:if test="${ empty info.learn_format}">--</c:if>
							</td>
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.school_name}">${info.school_name }</c:if>
							<c:if test="${ empty info.school_name}">--</c:if>
							</td>
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.subject_background}">${info.subject_background }</c:if>
							<c:if test="${ empty info.subject_background}">--</c:if>
							</td>
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.major}">${info.major }</c:if>
							<c:if test="${ empty info.major}">--</c:if>
							</td>
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.rank}">${info.rank }</c:if>
							<c:if test="${ empty info.rank}">--</c:if>
							</td>
							<td style="border: 1px solid black;">
							<c:if test="${not empty info.score}">${info.score }</c:if>
							<c:if test="${ empty info.score}">--</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div style="text-align:right;"><!-- 评分参照 --></div>
				
				<div><br>第一学历得分：${fn:substring(result.firstDegrees.degreeScore, 0, 4)}/16</div>
					
					<c:forEach items="${result.firstDegrees.firstDegreeResults}" var="firstDegree">
						<div style="width:100%;float:left;margin-bottom:10px;">
							${firstDegree.school_name }&nbsp;&nbsp;${firstDegree.major }&nbsp;&nbsp;${firstDegree.learn_format }&nbsp;&nbsp;
							<c:if test="${empty firstDegree.degree||firstDegree.degree=='无'}">无学位</c:if>
							<c:if test="${not empty firstDegree.degree&&firstDegree.degree!='无'}">${firstDegree.degree }</c:if>
							<br>
							<div>
								<c:if test="${empty firstDegree.ranking}">
								生源地：${result.info.province }&nbsp;&nbsp;
								省内得分率推断 &nbsp;&nbsp;
								<c:if test="${empty firstDegree.scoreRank}"> -- </c:if>
						 		<c:if test="${not empty firstDegree.scoreRank}"> 
							 		<fmt:formatNumber value="${fn:substring(firstDegree.scoreRank, 0, 4)}" type="percent"/>
						 		</c:if>
						 		&nbsp;&nbsp;得分 &nbsp;&nbsp;${firstDegree.degreeScore}
								&nbsp;&nbsp;等级 &nbsp;&nbsp;<c:if test="${ empty firstDegree.degreeRank}">--</c:if><c:if test="${not empty firstDegree.degreeRank}">${firstDegree.degreeRank}</c:if>
								<c:if test="${not empty firstDegree.locationRank}">
									<br><span style="font-size: 16px;">该省本科得分率参考依据</span><br>
									<table>
										<tr>
											<c:forEach items="${firstDegree.locationRank}" var="locationRank">
												<th style="margin-left:10px;font-weight:400;font-size: 16px;">${locationRank.key}</th>
											</c:forEach>
										</tr>
										<tr>
											<c:forEach items="${firstDegree.locationRank}" var="locationRank">
												<td style="margin-left:10px;font-size: 16px;"><fmt:formatNumber value="${locationRank.value}" type="percent"/></td>
											</c:forEach>
										</tr>
									</table>
								</c:if>
								</c:if>
								<c:if test="${not empty firstDegree.ranking}">
									学校排名 &nbsp;&nbsp;
									<c:if test="${(empty firstDegree.ranking)||firstDegree.ranking==' '}">>300</c:if>
									${firstDegree.ranking} &nbsp;&nbsp;
									&nbsp;&nbsp;等级 &nbsp;&nbsp;<c:if test="${ empty firstDegree.degreeRank}">--</c:if><c:if test="${not empty firstDegree.degreeRank}">${firstDegree.degreeRank}</c:if>
							 		&nbsp;&nbsp;得分 &nbsp;&nbsp;${firstDegree.degreeScore}<br>
									
									<span style="font-size: 16px;">QS世界大学排名参考依据</span><br>
										<div style="float:left;margin-left:20px;font-size: 16px;">
											[1,50]<br>
											A<br>
										</div>
										<div style="float:left;margin-left:20px;font-size: 16px;">
											[51,100]<br>
											A-<br>
										</div>
										<div style="float:left;margin-left:20px;font-size: 16px;">
											[101,150]<br>
											B<br>
										</div>
										<div style="float:left;margin-left:20px;font-size: 16px;">
											[151,200]<br>
											B-<br>
										</div>
										<div style="float:left;margin-left:20px;font-size: 16px;">
											[201,300]<br>
											C<br>
										</div>
										<div style="float:left;margin-left:20px;font-size: 16px;">
											>300<br>
											C-<br>
										</div>
								</c:if>
								<br>
							</div>
						</div>
					</c:forEach>
			<!-- </div> -->
			<c:if test="${result.majorScore<5&&(0.2<=(result.firstDegrees.degreeScore/16-result.majorScore/10)||0.2<=(result.majorScore/10-result.firstDegrees.degreeScore/16)) }">
				<div style="width:100%;float:left;margin-bottom:10px;"><span style="color:red;">学位学历与第一学历得分差距较大，请多加留意。</span></div>
			</c:if>
			<%-- <c:if test="${result.companyScore==0 }">
				<div style="width:100%;float:left;margin-bottom:10px;"><span style="color:red;">公司情况得分为零，请多加留意。</span></div>
			</c:if> --%>
			<%-- <c:if test="${result.workInfo.shixin==1 }">
				<div style="width:100%;float:left;margin-bottom:10px;"><span style="color:red;">该人员疑有失信记录，请多加留意。</span></div>
			</c:if> --%>
			<%-- <c:if test="${fn:length(result.firstDegrees.firstDegreeResults)==0}">
				<div style="width:100%;float:left;margin-bottom:10px;"><span style="color:red;">第一学历没填，请多加留意。</span></div>
			</c:if> --%>
			
			<div style="float:left;width: 100%;text-align: left;">
			<div>国际考试得分：${result.interExam }/2</div>
				<c:if test="${0<result.info.toefl_score||0<result.info.ielts_score ||0<result.info.gmat_score}">
					<div style="width: 95%;margin-bottom: 20px;float: left;">
						<table style="border: 1px solid black;border-spacing: 0;width: 50%;">
							<thead>
								<tr>
									<th style="width:10%;border: 1px solid black;">考试</th>
									<th style="width:8%;border: 1px solid black;">成绩</th>
									<th style="width:8%;border: 1px solid black;">获得日期</th>
									<th style="width:8%;border: 1px solid black;">得分</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${0<result.info.toefl_score }">
								<tr>
									<td style="width:10%;border: 1px solid black;">TOEFL</td>
									<td style="width:8%;border: 1px solid black;">${result.info.toefl_score }</td>
									<td style="width:8%;border: 1px solid black;">${result.info.toefl_date }</td>
									<td style="width:8%;border: 1px solid black;"><c:if test="${result.info.toefl_score>=90&&result.info.toefl_volid}">1</c:if><c:if test="${result.info.toefl_score<90||!result.info.toefl_volid}">0</c:if></td>
								</tr>
								</c:if>
								<c:if test="${0<result.info.ielts_score }">
								<tr>
									<td style="width:10%;border: 1px solid black;">IELTS</td>
									<td style="width:8%;border: 1px solid black;">${result.info.ielts_score }</td>
									<td style="width:8%;border: 1px solid black;">${result.info.ielts_date }</td>
									<td style="width:8%;border: 1px solid black;"><c:if test="${result.info.ielts_score>=6&&result.info.ielts_volid}">1</c:if><c:if test="${result.info.ielts_score<6||!result.info.ielts_volid}">0</c:if></td>
								</tr>
								</c:if>
								<c:if test="${0<result.info.gmat_score }">
								<tr>
									<td style="width:10%;border: 1px solid black;">GMAT</td>
									<td style="width:8%;border: 1px solid black;">${result.info.gmat_score }</td>
									<td style="width:8%;border: 1px solid black;">${result.info.gmat_date }</td>
									<td style="width:8%;border: 1px solid black;"><c:if test="${result.info.gmat_score>=600&&result.info.gmat_volid}">1</c:if><c:if test="${result.info.gmat_score<600||!result.info.gmat_volid}">0</c:if></td>
								</tr>
								</c:if>
								
							</tbody>
						</table>
					</div>
				</c:if>
				
			</div>
			</div>
			

			
		</div>
		
		</c:if>

		
		<c:if test="${empty type || type==2}">
		<div class="detail4">
			职业背景（<c:if test="${result.info.program==1 }">在职MBA</c:if><c:if test="${result.info.program==2 }">国际MBA</c:if>）
			<div style="margin-left:5%;float: left;width: 95%;">
				<c:if test="${result.workInfo.shixin==1 }">
					<div style="width:100%;float:left;margin-bottom:10px;"><span style="color:red;">该人员疑有失信记录，请多加留意。</span></div>
				</c:if>
				<c:if test="${result.companyScore==0 }">
					<div style="width:100%;float:left;margin-bottom:10px;"><span style="color:red;">公司情况得分为零，请多加留意。</span></div>
				</c:if>
				<c:if test="${result.jobScore==0}">
				<div style="width:100%;float:left;margin-bottom:10px;"><span style="color:red;">职务得分为零，请多加留意。</span></div>
				</c:if>
				公司情况：${result.info.company}，得分：${result.companyScore}/10<br>
				工作经验：${result.info.work_time}年，得分：${result.workExpScore}/4<br>
				管理经验：${result.info.manage_time}年，得分：${result.manageExpScore}/10<br>
				职务：${result.info.job }，职务级别：${result.info.job_level }，行业：${result.info.industry }，职能：${result.info.job_function }，得分：${result.jobScore}/8<br>
				职业发展培训得分：${result.certificateScore}/1
			</div>
			<div class="data">
				<c:if test="${not empty result.certificates}">
				<div style="width: 95%;margin-bottom: 20px;float: left;">
					<table style="border: 1px solid black;border-spacing: 0;width: 100%;">
						<thead>
							<tr>
								<th style="width:92%;border: 1px solid black;">证书</th>
								<th style="width:8%;border: 1px solid black;">认证</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="info" items="${result.certificates}">
								<tr>
									<td style="width:92%;border: 1px solid black;">${info.certificate_name}</td>
									<td style="width:8%;border: 1px solid black;"><c:if test="${info.calculate}">是</c:if><c:if test="${!info.calculate}">否</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				</c:if>
				<div id="tab_bar">
					<ul>
						<li id="tab1" onclick="myclick(2)" style="background-color: lightgray">
							公司信息
						</li>
						<li id="tab2" onclick="myclick(1)">
							个人信息参考
						</li>
						
					</ul>
				</div>
				<div id="web1" style="display:none;float: left; text-align: center; width: 100%;">
					<c:if test="${empty result.workInfo.info }">
						个人信息 百度条目：0<br>
					</c:if>
					<c:if test="${not empty result.workInfo.info }">
						个人信息 百度条目：${result.workInfo.info.totalcount }<br>
						<%-- <c:if test="${result.workInfo.info.totalcount>0 }"> --%>
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
						<%-- </c:if> --%>
					</c:if>
				</div>
				
				<div id="web2" style="display:block;float: left; text-align: center; width: 100%;">
					<c:if test="${empty result.workInfo.companyInfo }">
						公司信息 百度条目：0/8<br>
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
		</c:if>
	</c:if>
	</div>
	</div>
</body>
</html>