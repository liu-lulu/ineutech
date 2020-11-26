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
</style>

<script>

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

</ul>
</nav>


<div id="page-content-wrapper">
<button type="button" class="hamburger is-closed animated fadeInLeft" data-toggle="offcanvas">
<span class="hamb-top" style="background-color: black;"></span>
<span class="hamb-middle" style="background-color: black;"></span>
<span class="hamb-bottom" style="background-color: black;"></span>
</button>
<div class="">
<div class="row">
<div class="col-lg-10 col-lg-offset-1">
	<%-- <h1 style="color:black;">${shop.shop_code }&nbsp;:&nbsp;${shop.shop_name }</h1> --%>
<div class="col-lg-10 col-lg-offset-1" class="table-responsive" style="">
<h3 style="color:black;">${shop.shop_code }&nbsp;:&nbsp;${shop.shop_name }</h3>


<c:if test="${(fn:length(kpis))==1}">
<table class="table">
<thead style="border: 2px solid #ddd;background-color: rgba(54,188,248,0.3);">
<tr>
<th>指标</th>
<th>KPI&nbsp;/&nbsp;基值</th>
<th>排名</th>
</tr>
</thead>
<tbody style="border: 2px solid #ddd;">
<tr>
<td>会员贡献销售额</td>
<td><fmt:formatNumber type="percent" value="${kpis[0].member_sales }" maxFractionDigits="2"/>&nbsp;/&nbsp;<fmt:formatNumber type="percent" value="${kpiLevel.member_money }" maxFractionDigits="2"/></td>
<td>${kpis[0].ms_rk }</td>
</tr>
<tr>
<td>会员成交笔数</td>
<td><fmt:formatNumber type="percent" value="${kpis[0].member_num }" maxFractionDigits="2"/>&nbsp;/&nbsp;<fmt:formatNumber type="percent" value="${kpiLevel.member_num }" maxFractionDigits="2"/></td>
<td>${kpis[0].mn_rk }</td>
</tr>
<tr>
<td>微信买单金额</td>
<td><fmt:formatNumber type="percent" value="${kpis[0].wechat_money }" maxFractionDigits="2"/>&nbsp;/&nbsp;<fmt:formatNumber type="percent" value="${kpiLevel.wechat_money }" maxFractionDigits="2"/></td>
<td>${kpis[0].wm_rk }</td>
</tr>
<tr>
<td>会员消费记录均衡度</td>
<td><fmt:formatNumber type="number" value="${kpis[0].fc }" pattern="0.00" maxFractionDigits="2"/>&nbsp;/&nbsp;${kpiLevel.fc }</td>
<td>${kpis[0].fc_rk }</td>
</tr>
<%-- <tr>
<td>得分</td>
<td colspan="3">${kpis[0].score }</td>
</tr>
<tr>
<td>缺失</td>
<td colspan="3">${kpis[0].missing }</td>
</tr> --%>
</tbody>
</table>

<div style="border:2px solid #ddd;">
<div style="height:40px;background-color: rgba(74,206,206,0.4);color: black;padding: 10px 10px;">神秘客评分&nbsp;:&nbsp;${kpis[0].score }</div>
<div style="color: grey;padding: 10px 10px;">不足之处&nbsp;:&nbsp;${kpis[0].missing }</div>
</div>

</c:if>
</div>

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