<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Mosaddek">
<meta name="keyword" content="">

<title>当前汇总</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/bootstrap-reset.css" rel="stylesheet">
<!--external css-->
<link href="../css/font-awesome.css" rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="../css/style.css" rel="stylesheet">
<link href="../css/style-responsive.css" rel="stylesheet" />
<link href="../css/newStyle.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
<!--[if lt IE 9]>
	  <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
	  <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
</head>

<body>

	<section id="container" class="">
		<!--header start-->
		<jsp:include page="/common/top.jsp"></jsp:include>
		<!--header end-->
		<!--sidebar start-->
		<jsp:include page="/common/left.jsp"></jsp:include>
		<!--sidebar end-->
		<!--main content start-->
		<section id="main-content">

<section class="wrapper">
			    <!---->
				<div class="index">
        <ul class="unstyled">
            <li class="data-current">
                <div class="content-title clearfix">
                    <span class="normal"></span>
                    <p>当前监控</p>
                    <em></em>
                </div>
                <ul class="unstyled data-list unstyled">
                    <li  class="list-all">
                        <span></span>
                        <p>监控车辆</p>
                        <em>${trucksDeviceCountVO.count}</em>
                    </li>
                    <li  class="list-online">
                        <span></span>
                        <p>在线车辆</p>
                        <em>${trucksDeviceCountVO.online}</em>
                    </li>
                    <li  class="list-offline">
                        <span></span>
                        <p>掉线车辆</p>
                        <em>${trucksDeviceCountVO.offline}</em>
                    </li>
                    <li onclick="location.href='#'" class="list-warning">
                        <span></span>
                        <p>报警车辆</p>
                        <em>${trucksDeviceCountVO.warn}</em>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
	
			</section>			


		</section>
		<!--main content end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<script src="../js/jquery.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery.scrollTo.min.js"></script>
	<script src="../js/jquery.nicescroll.js" type="text/javascript"></script>


	<!--common script for all pages-->
	<script src="../js/common-scripts.js"></script>
	
	<script type="text/javascript" src="../js/psylife/device/gettdtucount.js"></script>

</body>
</html>



