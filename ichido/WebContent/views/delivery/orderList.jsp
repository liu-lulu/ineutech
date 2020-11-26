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

function toMap(tr,order_id,flag,beginPos){//订单id,送货地址
	$('#mapModal').modal('toggle');
	$("#order_id").val(order_id);
	$("#order_info").html("订单编号:"+tr.find("td").eq(0).text());
	$("#receiver").html(tr.find("td").eq(2).text());
	$("#receiver_address").html(tr.find("td").eq(3).text());
	$("#receiver_phone").html(tr.find("td").eq(4).text());
	$("#product_name").html(tr.find("td").eq(5).text());
	$("#product_num").html("×"+tr.find("td").eq(6).text());
	$("#time").html("下单时间:"+tr.find("td").eq(7).text());
	$("#scheduled_time").html("送货时间:"+tr.find("td").eq(1).text());
	
	/* $("#receiver_address").html(beginPos); */
	
	if(flag==1){
		
		$("#delivery_time").html("配送时间:"+tr.find("td").eq(8).text());
		$("#delivery_address").html("配送地址:"+tr.find("td").eq(9).text());
		$("#title").css('display','block');
		$("#send").css('display','none');
		$("#mapInfo").css('display','none');
		$("#mapdiv").css('display','none');
		return;
	}else{
		$("#delivery_time").html("");
		$("#delivery_address").html("");
		$("#title").css('display','none');
		$("#send").css('display','block');
		$("#mapInfo").css('display','block');
		$("#mapdiv").css('display','block');
		
	}
	var beginPoint;//送货地址点
	var endPoint;//当前位置点
	var geolocation = new BMap.Geolocation(); 
    geolocation.getCurrentPosition(function(r){//定位当前位置
        if(this.getStatus() == BMAP_STATUS_SUCCESS){  
        	endPoint=r.point;//当前位置点
            //以指定的经度与纬度创建一个坐标点  
            var pt = new BMap.Point(r.point.lng,r.point.lat);  
            //创建一个地理位置解析器  
            var geoc = new BMap.Geocoder();  
            geoc.getLocation(pt, function(rs){//解析格式：城市，区县，街道  
               var curLoation= rs.address; //当前位置
               $("#myLocation").html(curLoation);
               
               
             	//创建一个地图实例
				var map = new BMap.Map("mapdiv");
				
				
				//添加平移缩放控件，PC端默认位于地图左上方，它包含控制地图的平移和缩放的功能
				map.addControl(new BMap.NavigationControl());
				//添加比例尺
				map.addControl(new BMap.ScaleControl());  
				//开启鼠标滚轮缩放
				map.enableScrollWheelZoom();
				
				
               //解析送货地址点
               var myGeo = new BMap.Geocoder();
				// 将地址解析结果显示在地图上,并调整地图视野
				myGeo.getPoint(beginPos, function(point){
					beginPoint=point;
					
					//地图上标注
				    map.addOverlay(new BMap.Marker(beginPoint));
				    map.addOverlay(new BMap.Marker(endPoint));
					
					//获取两点距离,保留小数点后两位
					var distance=(map.getDistance(beginPoint,endPoint)).toFixed(2);
					 $("#distance").html(distance+'米');
					 
				    var overlays =[beginPoint,endPoint];
					//添加折线到地图上
					var polyline = new BMap.Polyline(overlays, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});  //定义折线
					map.addOverlay(polyline); 
					
					map.centerAndZoom(beginPoint,11);
		
	       			
				}, "上海市");
            });      
        }  
        else {  
        	bs4pop.alert('不能获取当前位置', function(){});
        }          
    },{enableHighAccuracy: true});
}
function send(){
	var data={'orderId':$("#order_id").val(),'curAddress': $("#myLocation").html()};
	url = "complete.do";
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		async: true,
		dataType:"json",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success: function(data){
			if(data.msg=="配送失败"){
				//alert("配送失败");
				bs4pop.alert('配送失败', function(){});
			}else{
				//alert("配送成功");
				bs4pop.alert('配送成功', function(){ location.reload();});
				
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
		<tr onclick="toMap($(this),${order.order_id },${order.flag },'${order.receiver_address }');"
			<c:if test="${order.flag==1}"> style="background-color:lightgray;"</c:if>>
			<td>${order.order_info }</td>
			<td>${order.scheduled_time }</td>
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
                <span class="modal-title" style="color:black;" id="title">订单信息</span>
                <button type="button" class="btn btn-primary" onclick="send()" id="send">送达</button>
            </div>
            <div class="modal-body" >
            	<div style="display:flex;margin-bottom: 10px;">
            		<div style="float:left;padding: 6px 0;font-size: x-large;"><i class="glyphicon glyphicon-user" ></i></div>
            		<div style="float:left;padding-left: 6px;">
            		<span id="receiver" style="color: #999;"></span>&nbsp;&nbsp;<span id="receiver_phone" style="color: #999;"></span><br>
            		<span id="receiver_address"></span>
            		</div>
            	</div>
            	<div style="display:flex;font-size: x-large;margin-bottom: 10px;">
            		<div style="float:left;padding: 6px 0;"><i class="glyphicon glyphicon-heart" ></i></div>
            		<div style="float:left;padding-left: 6px;">
            		<span id="product_name" style=""></span>&nbsp;&nbsp;
            		<span id="product_num" style=""></span>
            		</div>
            	</div>
            	<div style="display:flex;">
            		<div style="float:left;padding: 6px 0;font-size: x-large;"><i class="glyphicon glyphicon-time" ></i></div>
            		<div style="float:left;padding-left: 6px;">
            		<span id="order_info" style="color: #999;"></span><br>
            		<span id="time" style="color: #999;"></span><br>
            		<span id="scheduled_time" style="color: #999;"></span><br>
            		<span id="delivery_time" style="color: #999;"></span><br>
            		<span id="delivery_address" style="color: #999;"></span>
            		</div>
            	</div>
            	
            	<input type="hidden" id="order_id" />
            	<div id="mapInfo">
            	<i class="glyphicon glyphicon-map-marker" ></i><span id="myLocation"></span><br>
            	&nbsp;&nbsp;&nbsp;&nbsp;距离:<span id="distance"></span>
            	</div>
            	<div id="mapdiv" style="height:400px;"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <!-- <button type="button" class="btn btn-primary" onclick="send()">送达</button> -->
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