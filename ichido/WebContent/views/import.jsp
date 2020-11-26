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

 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fileinput.min.css">
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/all.css"> --%>

<%-- <link href="${pageContext.request.contextPath}/themes/explorer-fas/theme.css" media="all" rel="stylesheet" type="text/css"/> --%>
<script src="${pageContext.request.contextPath}/js/fileinput.min.js"></script>
<script src="${pageContext.request.contextPath}/js/locales/zh.js"></script>
<%-- <script src="${pageContext.request.contextPath}/themes/explorer-fas/theme.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/themes/fas/theme.js"></script>  --%>

<style type="text/css">

body, html{width:100%;height:100%;background-color:white;}
#wrapper.toggled #sidebar-wrapper {
    width: 130px;
}
#wrapper.toggled {
    padding-left: 130px;
}


.btn-secondary {
    color: #fff;
    background-color: #6c757d;
    border-color: #6c757d;
}
body a{
	text-decoration: none;
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
<li>
<a href="toImport.do"><i class="glyphicon glyphicon-import"></i> 导入数据</a>
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
<div class="col-lg-10 col-lg-offset-1 col-xs-10 col-xs-offset-1">
	<div class="col-lg-6 col-lg-offset-2" style="background: rgb(0,87,122,0.1);">
	<div class="row" style="text-align: center;">
    	<h3 style="color:black;">导入数据</h3>
	</div>
    <div class="row" style="padding-bottom: 10px;">
	<div class="col-lg-10 col-lg-offset-1 ">
	    <div class="file-loading">
	    <input id="uploadKpi" name="kpiFile" type="file" class="file" accept=".xls,.xlsx" >
	    </div>
	</div>
	</div>
	
	<div class="row" style="padding-bottom: 20px;">
	<div class="col-lg-10 col-lg-offset-1 ">
	    <div class="file-loading">
	    <input id="uploadDetail" name="detailFile" type="file" class="file" accept=".xls,.xlsx">
	    </div>
	</div>
	</div>
	</div>
</div>

</div>

</div>
</div>

</div>
</body>
<script type="text/javascript">


$('#uploadKpi').fileinput({
   /* theme: 'explorer-fas', */
   language: 'zh',
   showPreview: false,
   showRemove: false,
   dropZoneEnabled: false,
   showUpload: true,
  /*  browseIcon: "<i class=\"glyphicon glyphicon-folder-open\"></i> ",
   removeIcon: "<i class=\"glyphicon glyphicon-trash\"></i> ",
   uploadIcon: "<i class=\"glyphicon glyphicon-upload\"></i> ",
   fileIcon: "<i class=\"glyphicon glyphicon-file\"></i> ", */
   enctype : 'multipart/form-data',
   uploadAsync: true,
   msgPlaceholder: '选择kpi记录数据文件',
   uploadUrl: 'uploadKpi.do'
});

$('#uploadKpi').on('fileuploaded', function(event, data, previewId, index) {
	 if (data.response.status == "success") {
	 location.href="kpi.do";
	 }else{
			alert("上传失败！");
		}
});

$('#uploadDetail').fileinput({
  /*  theme: 'explorer-fas', */
   language: 'zh',
   showPreview: false,
   showRemove: false,
   dropZoneEnabled: false,
   showUpload: true,
  /*  browseIcon: "<i class=\"glyphicon glyphicon-folder-open\"></i> ",
   removeIcon: "<i class=\"glyphicon glyphicon-trash\"></i> ",
   uploadIcon: "<i class=\"glyphicon glyphicon-upload\"></i> ",
   previewFileIcon: "<i class=\"glyphicon glyphicon-file\"></i> ", */
   enctype : 'multipart/form-data',
   uploadAsync: true,
   msgPlaceholder: '选择交易记录数据文件',
   uploadUrl: 'uploadDetail.do'
}); 

$('#uploadDetail').on('fileuploaded', function(event, data, previewId, index) {
	if (data.response.status == "success") {
	 location.href="detail.do";
	}else{
		alert("上传失败！");
	}
});

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