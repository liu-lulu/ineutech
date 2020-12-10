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

<title>定位</title>

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
	
	<style type="text/css">
	.tire-active .tire-msg {
        text-align: center;
        padding-top: 25px;
    }
    
    #latLng {
    width: 240px;
    height: 100px;
    border: 1px solid #e0e0e0;
    border-radius: 5px;
    padding: 8px 5px;
    font-size: 12px;
    color: #666;
}
	</style>
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
<div class="row">
                  <div class="col-lg-12 col-xs-12"> 
                  <section class="panel"><header class="panel-heading">地图</header>
                  
                  <div class="panel-body" id="map_canvas" style="min-height: 480px;">
                  
                  </div>
                  </section>
                  
                  </div>
              </div>
              
  <div class="row">
                  <div class="col-lg-12 col-xs-12">
                      <section class="panel">
                          <header class="panel-heading">
                              工具信息
                          </header>
                          <table class="table table-condensed table-bordered">
                          <thead>
                          <tr>
                              <th style="width: 240px;"><button id="toAddressBtn" class="btn btn-info">解析经纬度</button></th>
                              <th>解析结果</th>
                          </tr>
                          </thead>
                          <tbody>
                          <tr>
                              <td>
                              <p style="line-height:24px;color:#999;">每个经纬度换一行，格式：经度,纬度</p>
                              <textarea name="" id="latLng" class="input">${param.s}</textarea></td>
                              <td><div id="showResults">
                    <p style="color: #999;">等待解析</p>
                </div></td>
                          </tr>
                         
                          </tbody>
                          </table>
                      </section>
                  </div>
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
	
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ndYEn2os3DVI5fW83tzaUkD4"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
	
	<script type="text/javascript" src="../js/kkbc/device/map.js"></script>

</body>
</html>



