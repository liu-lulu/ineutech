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

<title>历史数据</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/bootstrap-reset.css" rel="stylesheet">
<!--external css-->
<link href="../css/font-awesome.css" rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="../css/style.css" rel="stylesheet">
<link href="../css/style-responsive.css" rel="stylesheet" />
<link href="../css/newStyle.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="../css/jquery.datetimepicker.css"/>

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
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12 col-xs-12">
						<section class="panel">
							<header class="panel-heading"> 
<form action="../device/historydtulist.action" method="get" class="form-inline">
							<div class="form-group">
							<label for="keyWord" class="sr-only">关键字</label>
		   <input class="form-control" type="text" placeholder="请输入车牌号或DTU" style="" name="keyWord" id="keyWord" value='${keyWord}'>
		   </div>
		   
		   <div class="form-group">
							<label for="startTime" class="sr-only">开始时间</label>
		   <input class="form-control tooltips" type="text" placeholder="开始时间" title="开始日期" data-placement="bottom" data-toggle="tooltip" id="startTime" name="startTime" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd HH:mm" />" autocomplete="off">
		   </div>
		   
		   <div class="form-group">
							<label for="endTime" class="sr-only">结束时间</label>
		   <input class="form-control tooltips" type="text" placeholder="结束时间" title="结束时间" data-placement="bottom" data-toggle="tooltip" id="endTime" name="endTime" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm" />" autocomplete="off">
		   </div>
		   
		   <button type="submit" class="btn btn-primary btn-sm" style="width:60px;">查找</button>
		   </form>
 </header>
 
                        <div class="table-list content-item">
							<table class="table">
								<thead>
									<tr>
										<th>车牌号</th>
										<th>DTU ID</th>
										<th>GPS状态</th>
										<th>上报时间</th>
										<th>DTU 状态</th>
										<th>速度</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listInfo.currentList}"
										var="trucksWatchVO">
										<tr>
										    <td>${trucksWatchVO.trucks_id}</td>
										    <td>${trucksWatchVO.dtu_id}</td>
										    <td>
										    <c:choose>
										    <c:when test="${trucksWatchVO.gps_status!=null&&trucksWatchVO.gps_status==0}">
										      有效定位
										    </c:when>
										    <c:otherwise>
										    无效定位
										    </c:otherwise>
										    </c:choose>
										    </td>
										    <td><fmt:formatDate
													value="${trucksWatchVO.create_time}"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<td>
										    <c:choose>
										    <c:when test="${trucksWatchVO.warn==1}">
										    <button data-original-title="信息" data-content="${trucksWatchVO.warnInfo}" data-placement="bottom" data-trigger="hover" class="btn btn-warning popovers" data-html="true">警告</button>
										    </c:when>
										    <c:otherwise>
										    正常
										    </c:otherwise>
										    </c:choose>
										    </td>	
										    <td>${trucksWatchVO.dimian_sulu}</td>	
											<td>
												 <a class="btn btn-info" style="color: #FFFFFF;" href="../device/map.action?s=${trucksWatchVO.longitude},${trucksWatchVO.latitude}" target="_blank">定位</a>
											</td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
</div>
							<div class="row">
								<div class="col-sm-6">
									
								</div>
								<div class="col-sm-6">
									<div class="dataTables_paginate paging_bootstrap pagination">
										<div id="kkpager"><jsp:include page="/common/pagebar.jsp"></jsp:include></div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
				<!-- page end-->
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


	<script type="text/javascript" src="../js/kkpager.js"></script>
	<script type="text/javascript" src="../js/paging.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/kkpager_blue.css" />
	
	<script type="text/javascript" src="../js/jquery.datetimepicker.full.min.js"></script>
	
	<script type="text/javascript" src="../js/psylife/device/historydtulist.js"></script>

</body>
</html>



