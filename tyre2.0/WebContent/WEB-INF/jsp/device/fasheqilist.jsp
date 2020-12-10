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

<title>传感器</title>

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
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12 col-xs-12">
						<section class="panel">
							<header class="panel-heading"> 
<form action="../device/fasheqilist.action" method="get" class="form-inline">
							<div class="form-group">
							<label for="keyWord" class="sr-only">关键字</label>
		   <input class="form-control" type="text" placeholder="请输入传感器Id或DTU或车牌号" title="请输入传感器Id或DTU或车牌号" style="width: 220px;" name="keyWord" id="keyWord" value='${keyWord}'>
		   </div>
		   <button type="submit" class="btn btn-primary btn-sm" style="width:60px;">查找</button>
		   </form>
 </header>
 
                        <div class="table-list content-item">
							<table class="table">
								<thead>
									<tr>
									    <th>传感器Id</th>
									    <th>DTU ID</th>
										<th>车牌号</th>
										<th>最后上传时间</th>
										<th>第一次上传时间</th>
										<th>胎号</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listInfo.currentList}"
										var="trucksWatchVO">
										<tr>
										    <td>${trucksWatchVO.fasheqi_id}</td>
										    <td>${trucksWatchVO.dtu_id}</td>
										    <td>${trucksWatchVO.trucks_id}</td>
										    <td><fmt:formatDate
													value="${trucksWatchVO.caiji_time}"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<td><fmt:formatDate
													value="${trucksWatchVO.create_time}"
													pattern="yyyy-MM-dd HH:mm" /></td>
										    <td>${trucksWatchVO.tyre_id}</td>
										    <td>
										    <c:choose>
										    <c:when test="${trucksWatchVO.warn==1}">
										    警告
										    </c:when>
										    <c:otherwise>
										    正常
										    </c:otherwise>
										    </c:choose>
										    </td>
											<td>
												 <button type="button" class="btn btn-info" onclick="historyfasheqi('${trucksWatchVO.fasheqi_id}')">查看</button>
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
	<script type="text/javascript" src="../js/psylife/device/fasheqilist.js"></script>

</body>
</html>



