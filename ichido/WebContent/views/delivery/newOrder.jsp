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
<link rel="shortcut icon" href="../img/icon.jpg">
<title>新增订单</title>

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


<style type="text/css">

body, html{width:100%;height:100%;background-color:white;}
#wrapper.toggled #sidebar-wrapper {
    width: 130px;
}
#wrapper.toggled {
    padding-left: 130px;
}


input {
	/* width: 25%; */
	height: 30px;
	font-size: 10px;
	border: 1px solid #19b5ee;
	border-radius: 5px;
	/* margin: 20px 5% 0 5%; */
	padding: 5px;
}
</style>

<script>

$(function() {
	$('form').bootstrapValidator({

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
        		$('form').submit();
        }
	});
}).on('success.form.bv', function(e) {//验证通过后会执行这个函数。
	e.preventDefault();
	bs4pop.confirm('确定新增订单信息!', function(sure){ 
		if (sure){
			$.post($('form').attr('action'), $('form').serialize(), function(result) {
				if(result=='success'){
					//alert("新增成功");
					bs4pop.alert('新增成功', function(){location.href="toNewOrder.do";});
	    			
				}else{
					//alert("新增失败");
					bs4pop.alert('新增失败', function(){$(':submit').removeAttr("disabled");});
					
				}
				});
		}else{
			$(':submit').removeAttr("disabled");
		}
	});
	
});

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
<a href="toNewOrder.do"><i class="glyphicon glyphicon-plus"></i> 新增订单</a>
</li>
<li>
<a href="orderList.do"><i class="glyphicon glyphicon-list-alt"></i> 订单记录</a>
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
<div class="col-lg-10 col-lg-offset-1" >

	<div class="col-lg-10">
	    <div class="form"> 
    <form class="form-horizontal col-sm-offset-2 col-md-offset-3" id="orderform" action="newOrder.do" method="post">
    <div class="col-xs-12 col-sm-10 col-md-9" style="background: rgb(0,87,122,0.1);">
    <div class="form-group" style="text-align: center;">
    	<h3 style="color:black;">订单信息</h3>
	</div>
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
    <div class="form-group">
	    <div class="col-sm-offset-3 col-xs-offset-4 col-xs-8">
	      <button type="submit" class="btn btn-primary" style="width: 90px;">新增</button>
	    </div>
   </div>
    </div>
	</form>
	</div>

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
	</script>
</html>