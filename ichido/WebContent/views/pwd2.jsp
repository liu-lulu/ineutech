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
			newPwd : {
				validators : {
					notEmpty : {
						message : '新密码不能为空'
					},
		            identical: {
		                field: 'confirmPwd',
		                message: '新密码与确认密码不一致！'
		              },
		              stringLength: {
		            	min: 6,
		                max: 10,
		                message: '新密码长度6到10位'
		              }
				}
			},
			confirmPwd : {
				validators : {
					notEmpty : {
						message : '确认密码不能为空'
					},
		            identical: {
		                field: 'newPwd',
		                message: '新密码与确认密码不一致'
		              },
		              stringLength: {
		            	min: 6,
		                max: 10,
		                message: '确认密码长度6到10位'
		              }
				}
			}
		}
        ,submitHandler: function (validator, form, submitButton) {
        		$('form').submit();;
        }
	});
}).on('success.form.bv', function(e) {//验证通过后会执行这个函数。
	e.preventDefault();
	var r=confirm("确定修改密码!");
	if (r==true){
		$.post($('form').attr('action'), $('form').serialize(), function(result) {
			if(result=='success'){
				alert("修改成功");
    			location.href="kpi.do";
			}else{
				alert("修改失败");
				$(':submit').removeAttr("disabled");
			}
			});
	}
	else{
		$(':submit').removeAttr("disabled");
	}
	
	
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


<div id="page-content-wrapper">
<button type="button" class="hamburger is-closed animated fadeInLeft" data-toggle="offcanvas">
<span class="hamb-top" style="background-color: black;"></span>
<span class="hamb-middle" style="background-color: black;"></span>
<span class="hamb-bottom" style="background-color: black;"></span>
</button>
<div class="">
<div class="row">
<div class="col-lg-10 col-lg-offset-1" >
<!-- <h3 class="page-header" style="color:black;margin: 20px 0 20px;">修改密码</h3> -->

	<div class="col-lg-10">
	    <div class="form"> 
    <form class="form-horizontal col-xs-offset-1 col-sm-offset-2 col-md-offset-3" id="loginform" action="updPwd.do" onsubmit="return check();" method="post">
    <div class="col-xs-11 col-sm-10 col-md-9" style="background: rgb(0,87,122,0.1);">
    <div class="form-group" style="text-align: center;">
    	<h3 style="color:black;">修改密码</h3>
	</div>
	<div class="form-group">
	    <label for="newPwd" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">新密码<span style="color:red;">*</span></label>
	    <div class="col-xs-8">
	    <input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="请输入新密码">
	    </div>
	</div>
	<div class="form-group">
	    <label for="confirmPwd" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">确认密码<span style="color:red;">*</span></label>
	    <div class="col-xs-8">
	    <input type="password" class="form-control" id="confirmPwd" name="confirmPwd" placeholder="请重新输入密码">
	    </div>
	</div>
    <div class="form-group">
	    <div class="col-sm-offset-3 col-xs-offset-4 col-xs-8">
	      <button type="submit" class="btn btn-primary" style="width: 90px;">修改</button>
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