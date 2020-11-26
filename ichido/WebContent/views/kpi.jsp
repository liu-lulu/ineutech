<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<meta name="format-detection" content="telephone=no"/>
<link rel="shortcut icon" href="img/icon.jpg">
<title>kpi</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/my.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/kkpager.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/paging.js"></script>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/kkpager_blue.css" />
<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css">

<link href="${pageContext.request.contextPath}/css/bootstrap-table.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-table-fixed-columns.css" rel="stylesheet">

<script src="${pageContext.request.contextPath}/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-table-fixed-columns.js"></script>

<style type="text/css">

body, html{width:100%;height:100%;background-color:white;}
#wrapper.toggled #sidebar-wrapper {
    width: 130px;
}
#wrapper.toggled {
    padding-left: 130px;
}
th,td{
 white-space:nowrap;
}
</style>

<script>

function exportKpi(){
	var data;
	url = "exportKpi.do";

	$.ajax({
		type: "POST",
		url: url,
		data: data,
		async: true,
		dataType:"json",
		beforeSend: function(){  
			alert("正在导出,请稍等！");
			/* $("#loadingdiv").show(); */
		},  
		success: function(data){
				if(data.msg=="导出失败！"){
					alert(msg);
				}else{
					alert("导出成功！");
					window.location.href = "xiazai.do?realPath="+data.msg;
				}
		},
		error: function(msg){
			alert("error"+msg);
			
		},
		complete: function () {  
			/* layer.close(); */
		/* 	$("#loadingdiv").hide(); */
	    }
	});
}

//重写。避免微信弹窗带域名
window.alert = function(name){
 	  var iframe = document.createElement("IFRAME");
 	  iframe.style.display="none";
 	  iframe.setAttribute("src", 'data:text/plain,');
 	  document.documentElement.appendChild(iframe);
 	  window.frames[0].window.alert(name);
 	  iframe.parentNode.removeChild(iframe);
 	 };

 	window.confirm = function (message) {
 	   var iframe = document.createElement("IFRAME");
 	   iframe.style.display = "none";
 	   iframe.setAttribute("src", 'data:text/plain,');
 	   document.documentElement.appendChild(iframe);
 	   var alertFrame = window.frames[0];
 	   var result = alertFrame.window.confirm(message);
 	   iframe.parentNode.removeChild(iframe);
 	   return result;
 	 }; 

</script>
</head>
<body>
<div id="wrapper">
<div class="overlay" style="background-color: rgba(0,0,0,.0);"></div>

<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation" >
<ul class="nav sidebar-nav"  style="width:130px;">
<li class="sidebar-brand">
<a href="#">
Ichido
</a>
</li>
<li>
<a href="kpi.do"><i class="fa fa-fw fa-folder"></i> KPI</a>
</li>
<li>
<a href="detail.do"><i class="fa fa-fw fa-home"></i> 消费记录</a>
</li>
<li>
<a href="toUpdPwd.do"><i class="fa fa-fw fa-key"></i> 修改密码</a>
</li>
<li>
<a href="toImport.do"><i class="glyphicon glyphicon-import"></i> 导入数据</a>
</li>

<!-- <li class="dropdown">
<a href="http://www.jq22.com/demo/Bootstrap3Nav201703142259/#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-fw fa-plus"></i> Dropdown <span class="caret"></span></a>
<ul class="dropdown-menu" role="menu">
<li class="dropdown-header">Dropdown heading</li>
<li><a href="http://www.jq22.com/demo/Bootstrap3Nav201703142259/#">Action</a></li>
<li><a href="http://www.jq22.com/demo/Bootstrap3Nav201703142259/#">Another action</a></li>
</ul>
</li> -->

</ul>
</nav>


<div id="page-content-wrapper" >

<button type="button" class="hamburger is-closed animated fadeInLeft " data-toggle="offcanvas" >
<span class="hamb-top" style="background-color: black;"></span>
<span class="hamb-middle" style="background-color: black;"></span>
<span class="hamb-bottom" style="background-color: black;"></span>
</button>

<div class="">
<div class="row">
<div class="col-lg-10 col-lg-offset-1">

	<div class="col-lg-12" style="margin-bottom:10px;border-bottom: 1px solid #eee;">
	<div class="col-lg-3 col-xs-12" style=""><h3 class="page-header" style="color:black;margin: 0;border-bottom: 0;">本周KPI排名&nbsp;&nbsp;<span style="font-size: small;">更新至${updDate}</span></h3></div>
	<div class="col-lg-6 col-lg-offset-3 col-xs-12 " style="text-align: right;padding-right: 0;">
    <form class="form-inline" id="kpiform" action="kpi.do"  method="post" role="form">
	<div class="form-group ">
	    <div class="col-xs-6">
	    <input type="text" class="form-control" id="shop_info" name="shop_info" placeholder="门店编号/名称" value="${shop_info }">
	    </div>
	    <div class="col-xs-6" style="padding-right: 0;">
	    <button type="submit" class="btn btn-primary" id="doLogin">查询</button>&nbsp;&nbsp;
	    <input type="button" class="btn btn-primary" onclick="exportKpi()" value="导出" />
	    </div>
	</div>
    
	</form>
	</div>
	</div>
	
	<div class="" style=" float:left;margin:10px 0;width: 100%;">

	<%-- <div class="" style="background-color: #ff8472;text-align: center;width: 23.5%;float: left;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi1.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">会员贡献销售额</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;">${kpiLevel.member_money }</div></div>
	</div>
	
	<div class="" style="background-color: #5788d6;text-align: center;width: 23.5%;float: left;margin-left: 2%;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi4.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">会员成交笔数</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;">${kpiLevel.member_num }</div></div>
	</div>
	
	<div class="" style="background-color: #36bcf8;text-align: center;width: 23.5%;float: left;margin-left: 2%;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi2.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">微信买单金额</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;">${kpiLevel.wechat_money }</div></div>
	</div>
	
	<div class="" style="background-color: #4acece;text-align: center;width: 23.5%;float: left;margin-left: 2%;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi3.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">消费记录</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;">${kpiLevel.fc }</div></div>
	</div> --%>
	
	<div class="col-xs-12 col-sm-3 " >
	<div class="col-xs-12 col-sm-11 col-sm-offset-1" style="background-color: #ff8472;text-align: center;float: left;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi3.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">会员贡献销售额</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;"><fmt:formatNumber type="percent" value="${kpiLevel.member_money }" maxFractionDigits="2"/></div></div>
	</div>
	</div>
	
	<div class="col-xs-12 col-sm-3" >
	<div class="col-xs-12 col-sm-11 col-sm-offset-1" style="background-color: #5788d6;text-align: center;float: left;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi2.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">会员成交笔数</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;"><fmt:formatNumber type="percent" value="${kpiLevel.member_num }" maxFractionDigits="2"/></div></div>
	</div>
	</div>
	
	<div class="col-xs-12 col-sm-3" >
	<div class="col-xs-12 col-sm-11 col-sm-offset-1" style="background-color: #36bcf8;text-align: center;float: left;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi4.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">微信买单金额</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;"><fmt:formatNumber type="percent" value="${kpiLevel.wechat_money }" maxFractionDigits="2"/></div></div>
	</div>
	</div>
	
	<div class="col-xs-12 col-sm-3" >
	<div class="col-xs-12 col-sm-11 col-sm-offset-1" style="background-color: #4acece;text-align: center;float: left;display: flex;justify-content: center;padding: 10px;color: white;">
	<div style="float:left;"><img alt="" src="img/kpi1.png"></div>
	<div style="float:left;margin-left: 20px;"><div style="text-align: left;">会员消费记录均衡度</div><div style="float:left;">KPI基准值：</div><div style="float:left;font-size: large;">${kpiLevel.fc }</div></div>
	</div>
	</div>
	
	</div>
	<div style="width:100%;;float: left;" class="col-lg-12">
	<div style="overflow: auto;" class="table-responsive">
<table class="table table-bordered table-striped table-hover ">
	<thead>
		<tr>
			<th>门店编号</th>
			<th>门店名称</th>
			<th>品牌</th>
			<th>会员贡献销售额KPI</th>
			<th>
			排名
			<a href="kpi.do?order=1"><span class="glyphicon glyphicon-arrow-up" style="color:#19b5ee;font-size: small;"></span></a>
			<a href="kpi.do?order=2"><span class="glyphicon glyphicon-arrow-down" style="color:#19b5ee;font-size: small;"></span></a>
			</th>
			<th>会员成交笔数KPI</th>
			<th>
			排名
			<a href="kpi.do?order=3"><span class="glyphicon glyphicon-arrow-up" style="color:#19b5ee;font-size: small;"></span></a>
			<a href="kpi.do?order=4"><span class="glyphicon glyphicon-arrow-down" style="color:#19b5ee;font-size: small;"></span></a>
			</th>
			<th>微信买单金额KPI</th>
			<th>
			排名
			<a href="kpi.do?order=5"><span class="glyphicon glyphicon-arrow-up" style="color:#19b5ee;font-size: small;"></span></a>
			<a href="kpi.do?order=6"><span class="glyphicon glyphicon-arrow-down" style="color:#19b5ee;font-size: small;"></span></a>
			</th>
			<th>会员消费记录均衡度KPI</th>
			<th>
			排名
			<a href="kpi.do?order=7"><span class="glyphicon glyphicon-arrow-up" style="color:#19b5ee;font-size: small;"></span></a>
			<a href="kpi.do?order=8"><span class="glyphicon glyphicon-arrow-down" style="color:#19b5ee;font-size: small;"></span></a>
			</th>
			<th>评分</th>
			<th>不足之处</th>
			
			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="kpi" items="${kpis }">
		<tr onclick="javascript:window.location.href='detail.do?shop_code=${kpi.shop_code }';">
			<td>${kpi.shop_code }</td>
			<td>${kpi.shop_name }</td>
			<td>${kpi.shop_brand }</td>
			<td><fmt:formatNumber type="percent" value="${kpi.member_sales }" maxFractionDigits="2"/></td>
			<td>${kpi.ms_rk }</td>
			<td><fmt:formatNumber type="percent" value="${kpi.member_num }" maxFractionDigits="2"/></td>
			<td>${kpi.mn_rk }</td>
			<td><fmt:formatNumber type="percent" value="${kpi.wechat_money }" maxFractionDigits="2"/></td>
			<td>${kpi.wm_rk }</td>
			
			<td><fmt:formatNumber type="number" value="${kpi.fc }" pattern="0.00" maxFractionDigits="2"/></td>
			<td>${kpi.fc_rk }</td>
			<td>${kpi.score }</td>
			
		    <td style="text-overflow:ellipsis;"><%-- <abbr title="${kpi.missing }">${fn:substring(kpi.missing, 0, 20)}</abbr> --%> ${kpi.missing }</td>
			
		</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</div>
<div id="kkpager" style="float:right;"><jsp:include page="/common/pagebar.jsp"></jsp:include></div>
</div>

</div>

</div>
</div>

</div>
</body>
<script type="text/javascript">
		$(document).ready(function () {
		  var trigger = $('.hamburger'),
		      overlay = $('.overlay'),
		     isClosed = false;

		    trigger.click(function () {
		      hamburger_cross();      
		    });

		    function hamburger_cross() {

		      if (isClosed == true) {
		        overlay.hide();
		        trigger.removeClass('is-open');
		        trigger.addClass('is-closed');
		        
		        isClosed = false;
		      } else {
		        overlay.show();
		        trigger.removeClass('is-closed');
		        trigger.addClass('is-open');
		        isClosed = true;
		      }
		  }
		  
		  $('[data-toggle="offcanvas"]').click(function () {
		        $('#wrapper').toggleClass('toggled');
		  });  
		});
		
		$(function(){
			 //#table表示的是上面table表格中的id
			 $("#table").bootstrapTable('destroy').bootstrapTable({
			  fixedColumns: true, 
			  fixedNumber: 2 //固定列数
			 });
			})
	</script>
</html>