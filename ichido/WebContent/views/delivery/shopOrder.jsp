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
<link rel="shortcut icon" href="../img/icon.jpg">
<title>订单列表</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bs4.pop.css">
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bs4.pop.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=giRq0SmgLeffVC6mmd2MAsXmw8L4U5fh"></script>

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

function toSelect(tr,order_id){//订单id
	var deliverymanId=tr.find("td").eq(2).find("input[type='hidden']").eq(0).val();
	if(deliverymanId!=null&&deliverymanId!=''){
		$("#deliveryman").val(deliverymanId);
	}else{
		$("#deliveryman option:first").prop("selected","selected");
	}
	$("#orderId").val(order_id);
	$('#mapModal').modal('toggle');
}
function select(){
	var data={'orderId':$("#orderId").val(),'deliveryman': $("#deliveryman").val()};
	url = "selectMan.do";
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		async: true,
		dataType:"json",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success: function(data){
			if(data.msg=="fail"){
				bs4pop.alert('选择失败', function(){});
			}else{
				bs4pop.alert('选择成功', function(){ location.reload();});
				
			}
		},
		error: function(msg){
			console.log("error:"+msg);
			
		},
		complete: function () {  
			
	    }
	});
	
}
//重写。避免微信弹窗带域名
/* window.alert = function(name){
 	  var iframe = document.createElement("IFRAME");
 	  iframe.style.display="none";
 	  iframe.setAttribute("src", 'data:text/plain,');
 	  document.documentElement.appendChild(iframe);
 	  window.frames[0].window.alert(name);
 	  iframe.parentNode.removeChild(iframe);
 	 };  */
 	


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

<!-- <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation" >
<ul class="nav sidebar-nav"  style="width:130px;">
<li class="sidebar-brand">
<a href="#">
Ichido
</a>
</li>
<li>
<a href="toNewOrder.do"><i class="fa fa-wpforms"></i> 新增订单</a>
</li>
<li>
<a href="orderList.do"><i class="fa fa-list-alt"></i> 订单记录</a>
</li>

</ul>
</nav> -->


<div id="page-content-wrapper" >

<!-- <button type="button" class="hamburger is-closed animated fadeInLeft " data-toggle="offcanvas" >
<span class="hamb-top" style="background-color: black;"></span>
<span class="hamb-middle" style="background-color: black;"></span>
<span class="hamb-bottom" style="background-color: black;"></span>
</button> -->

<div class="">
<div class="row">
<div class="col-lg-10 col-lg-offset-1">

	<div class="col-lg-12" style="">
	<div class="" style=""><h3 class="page-header" style="color:black;margin: 0;border-bottom: 0;">${delivery.shop_code }-${delivery.shop_name }</h3></div>
	<div class="col-lg-6 col-lg-offset-6 col-xs-12 " style="text-align: right;padding: 0;">
    <form class="form-inline" id="orderform" action="orderList.do"  method="post" role="form">
	<div class="form-group ">
	    <div class="col-xs-9" style="padding: 0;">
	    <input type="text" class="form-control" id="orderInfo" name="orderInfo" placeholder="订单编号/收货人/电话" value="${orderInfo }">
	    </div>
	    <div class="col-xs-3" style="padding-right: 0;">
	    <button type="submit" class="btn btn-primary" id="doLogin">查询</button>
	    </div>
	</div>
    
	</form>
	</div>
	</div>

	<div style="width:100%;float: left;margin-top:10px;" class="col-lg-12">
	<div style="overflow: auto;" class="table-responsive">
<table class="table table-bordered table-striped table-hover ">
	<thead>
		<tr>
			<th>订单编号</th>
			<th>送货时间</th>
			<th>配送员</th>
			<th>收货人</th>
			<th>收货地址</th>
			<th>联系电话</th>
			<th>商品名称</th>
			<th>商品数量</th>
			<th>下单时间</th>
			<th>送达时间</th>
			<th>送达地址</th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="order" items="${orders }">
		<tr <c:if test="${order.flag==0 }">onclick="toSelect($(this),${order.order_id });"</c:if>
			<c:if test="${order.flag==1}"> style="background-color:lightgray;"</c:if>>
			<td>${order.order_info }</td>
			<td>${order.scheduled_time }</td>
			<td>${order.deliverymanName }<input type="hidden" value="${order.deliveryman }"/></td>
			<td>${order.receiver }</td>
			<td>${order.receiver_address }</td>
			<td>${order.receiver_phone }</td>
			<td>${order.product_name }</td>
			<td>${order.product_num }</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${order.time }" /></td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${order.delivery_time }" /></td>
			<td>${order.delivery_address }</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</div>
<div id="kkpager" style="float:right;"><jsp:include page="/common/pagebar.jsp"></jsp:include></div>
</div>


<div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="color:black;">配送员</h4>
            </div>
            <div class="modal-body" >
            	<input type="hidden" id="orderId"/>
            	<div class="form-group" style="display:flex;">
				    <label for="deliveryman" class="col-sm-3 col-sm-offset-2 control-label" style="padding: 6px 12px;">配送员</label>
				    <div class="col-sm-5 col-xs-9">
				    <select class="form-control" id="deliveryman" name="deliveryman" placeholder="请选择配送员">
				    <c:forEach items="${deliverymans}" var="deliveryman">
				    <option value="${deliveryman.login_id }">${deliveryman.shop_name }</option>
				    </c:forEach>
				    </select>
				    </div>
				</div>
            	
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="select()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div id="alert-container-center" class="alert-container"></div>

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