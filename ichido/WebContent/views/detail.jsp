<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>会员交易信息</title>

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

<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
<ul class="nav sidebar-nav">
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

<c:if test="${shop.role==1 }">
<li>
<a href="toImport.do"><i class="glyphicon glyphicon-import"></i> 导入数据</a>
</li>
</c:if>


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
<div class="col-lg-10 col-lg-offset-1" >
<h3 class="page-header" style="color:black;margin: 20px 0 20px;">会员消费记录</h3>
    

	<div style="width: 100%;clear: both;">
	<div style="overflow: auto;" class="table-responsive">
<table class="table table-bordered table-striped table-hover" style="word-break: keep-all;">
	<thead>
		<tr>
			<th>门店编号</th>
			<th>门店名称</th>
			<th>交易时间</th>
			<th>收银员</th>
			<th>会员卡号</th>
			<th>会员名称</th>
			<th>交易金额</th>
			<th>折扣金额</th>
			<th>当天交易次数</th>
			<th>当天交易总额</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="history" items="${histories }">
		<tr>
			<td>${history.shop_code }</td>
			<td>${history.shop_name }</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd" value="${history.history_date }" />&nbsp;&nbsp;<fmt:formatDate pattern="HH:mm" value="${history.history_time }" /></td>
			<td>${history.cashier_code }</td>
			<td>${history.member_code }</td>
			<td>${history.member_name }</td>
			<td>${history.transaction_amount }</td>
			<td>${history.discount_amount }</td>
			<td>${history.daily_num }</td>
			<td>${history.daily_transactions }</td>
			
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
	</script>
</html>