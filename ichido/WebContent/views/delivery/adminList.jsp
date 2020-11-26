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

$(function() {
	$('#updform').bootstrapValidator({

		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			order_info : {
				validators : {
					notEmpty : {
						message : '订单编号不能为空'
					},
		            
	              stringLength: {
	                max: 20,
	                message: '订单编号最大长度20位'
	              }
				}
			},
			scheduled_time : {
				validators : {
					
					date: {
						format: 'YYYY/MM/DD HH:mm:ss',
						message: '送货时间格式为YYYY/MM/DD HH:mm:ss'
					}
				}
			},
			receiver : {
				validators : {
					notEmpty : {
						message : '收货人不能为空'
					},
		            
		              stringLength: {
		                max: 10,
		                message: '收货人最大长度10位'
		              }
				}
			},
			receiver_address : {
				validators : {
					notEmpty : {
						message : '收货地址不能为空'
					},
		            
		              stringLength: {
		                max: 50,
		                message: '收货地址最大长度50位'
		              }
				}
			},
			receiver_phone : {
				validators : {
					notEmpty : {
						message : '联系电话不能为空'
					},
					phone: {
						country: 'CN',
						message: '请输入正确的联系电话'
					}
				}
			},
			product_name : {
				validators : {
					notEmpty : {
						message : '商品信息不能为空'
					},
		            
		              stringLength: {
		                max: 50,
		                message: '商品信息最大长度50位'
		              }
				}
			},
			product_num : {
				validators : {
					notEmpty : {
						message : '商品总数量不能为空'
					},
		            
					digits: {
		                message: '商品总数量只能包含数字'
		              },
		              greaterThan: {
		            	  value: 1,
		            	  message: '商品总数量最小输入1'
		            	  }
				}
			},
			shop_code : {
				validators : {
					notEmpty : {
						message : '门店编码不能为空'
					},
		            
		              stringLength: {
		                max: 20,
		                message: '门店编码最大长度20位'
		              }
				}
			}
		}
        ,submitHandler: function (validator, form, submitButton) {
        		$('#updform').submit();
        }
	});
}).on('success.form.bv', function(e) {//验证通过后会执行这个函数。
	e.preventDefault();
	bs4pop.confirm('确定修改订单信息!', function(sure){ 
		if (sure){
			$.post($('#updform').attr('action'), $('#updform').serialize(), function(result) {
				if(result=='success'){
					//alert("新增成功");
					bs4pop.alert('修改成功', function(){location.reload();});
	    			
				}else{
					//alert("新增失败");
					bs4pop.alert('修改失败', function(){$('#updform :submit').removeAttr("disabled");});
					
				}
				});
		}else{
			$('#updform :submit').removeAttr("disabled");
		}
	});
	
});


function toUpd(tr,order_id){//订单id
	$("#updorderId").val(order_id);
	$("#shop_code").val(tr.find("td").eq(0).text());
	$("#order_info").val(tr.find("td").eq(2).text());
	$("#scheduled_time").val(tr.find("td").eq(3).text());
	$("#receiver").val(tr.find("td").eq(4).text());
	$("#receiver_phone").val(tr.find("td").eq(6).text());
	$("#product_name").val(tr.find("td").eq(7).text());
	$("#product_num").val(tr.find("td").eq(8).text());
	$("#receiver_address").val(tr.find("td").eq(5).text());
	
	$('#updModal').modal('toggle');
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

<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation" >
<ul class="nav sidebar-nav"  style="width:130px;">
<li class="sidebar-brand">
<a href="#">
Ichido
</a>
</li>
<li>
<a href="toNewOrder.do"><i class="glyphicon glyphicon-plus"></i> 新增订单</a>
</li>
<li>
<a href="orderList.do"><i class="glyphicon glyphicon-list-alt"></i> 订单记录</a>
</li>

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

	<div class="col-lg-12" style="">
	<div class="" style=""><h3 class="page-header" style="color:black;margin: 0;border-bottom: 0;"></h3></div>
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
			<th>门店编号</th>
			<th>门店名称</th>
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
		<tr <c:if test="${order.flag==0}">onclick="toUpd($(this),${order.order_id });"</c:if>
			<c:if test="${order.flag==1}"> style="background-color:lightgray;"</c:if>>
			
			<td>${order.shop_code }</td>
			<td>${order.shop_name }</td>
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

<div class="modal fade" id="updModal" tabindex="-1" role="dialog" aria-labelledby="updModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    	<form class="form-horizontal col-xs-offset-1 col-sm-offset-2 col-md-offset-3" id="updform" action="updOrder.do"  method="post">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="updModalLabel" style="color:black;">订单修改</h4>
            </div>
            <div class="modal-body" >
            	<input type="hidden" id="updorderId" name="updorderId"/>
            	<div class="form-group">
				    <label for="shop_code" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">门店编码<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <select class="form-control" id="shop_code" name="shop_code" placeholder="请选择门店">
				    <c:forEach items="${shops}" var="shop">
				    <option value="${shop.shop_code }">${shop.shop_code }-${shop.shop_name }</option>
				    </c:forEach>
				    </select>
				    </div>
				</div>
           		<div class="form-group">
				    <label for="order_info" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">订单编号<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="order_info" name="order_info" placeholder="请输入订单编号">
				    </div>
				</div>
				<div class="form-group">
				    <label for="scheduled_time" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">送货时间<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="scheduled_time" name="scheduled_time" placeholder="请输入送货时间">
				    </div>
				</div>
				<div class="form-group">
				    <label for="receiver" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">&nbsp;&nbsp;&nbsp;&nbsp;收货人<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="receiver" name="receiver" placeholder="请输入收货人">
				    </div>
				</div>
				<div class="form-group">
				    <label for="receiver_phone" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">联系电话<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="receiver_phone" name="receiver_phone" placeholder="请输入联系电话">
				    </div>
				</div>
				<div class="form-group">
				    <label for="product_name" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">商品信息<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <textarea class="form-control" rows="5" id="product_name" name="product_name" placeholder="请输入商品信息"></textarea>
				    </div>
				</div>
				<div class="form-group">
				    <label for="product_num" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">&nbsp;&nbsp;&nbsp;&nbsp;总数量<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="product_num" name="product_num" placeholder="请输入商品总数量">
				    </div>
				</div>
				<div class="form-group">
				    <label for="receiver_address" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">收货地址<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <textarea class="form-control" rows="5" id="receiver_address" name="receiver_address" placeholder="请输入收货地址"></textarea>
				    </div>
				</div>
            	
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary">提交</button>
            </div>
        </div><!-- /.modal-content -->
        </form>
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