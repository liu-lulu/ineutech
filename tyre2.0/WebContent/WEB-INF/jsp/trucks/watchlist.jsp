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

<title>监控车辆</title>

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
<form action="../trucks/watchlist.action" method="get" class="form-inline">
							<div class="form-group">
							<label for="keyWord" class="sr-only">关键字</label>
		   <input class="form-control" type="text" placeholder="请输入车牌号或DTU" style="" name="keyWord" id="keyWord" value='${keyWord}'>
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
										<th>车队</th>
										<th>报警</th>
										<th>状态</th>
										<th>GPS状态</th>
										<th>最后上传时间</th>
										<th>第一次上传时间</th>
										<th>手机号</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listInfo.currentList}"
										var="trucksWatchVO">
										<tr>
										    <td>${trucksWatchVO.trucks_id}</td>
										    <td>${trucksWatchVO.dtu_id}</td>
										    <td>${trucksWatchVO.company}</td>
										    <c:choose>
										    <c:when test="${trucksWatchVO.warn==1}">
										    <td class="td-alert">Yes</td>
										    </c:when>
										    <c:otherwise>
										   <td class="td-normal">No</td>
										    </c:otherwise>
										    </c:choose>
										    
										    <td>
										    <c:choose>
										    <c:when test="${trucksWatchVO.status==1}">
										    在线
										    </c:when>
										    <c:otherwise>
										    离线
										    </c:otherwise>
										    </c:choose>
										    </td>
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
													value="${trucksWatchVO.caiji_time}"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<td><fmt:formatDate
													value="${trucksWatchVO.create_time}"
													pattern="yyyy-MM-dd HH:mm" /></td>
											<td>${trucksWatchVO.phone}</td>
											<td>
												 <c:if test="${not empty trucksWatchVO.trucks_id}">
												 <a class="btn btn-info" style="color: #FFFFFF;" target="_blank" href="../device/fasheqipos.action?dtu_id=${trucksWatchVO.dtu_id}">查看分布图</a>
												 </c:if>
												 <a class="btn btn-info" style="color: #FFFFFF;" href="../device/map.action?s=${trucksWatchVO.longitude},${trucksWatchVO.latitude}" target="_blank">定位</a>
												 <c:choose>
										             <c:when test="${trucksWatchVO.id==null}">
										                <a class="btn btn-info" style="color: #FFFFFF;" href="../trucks/truckslistbybind.action?dtu_id=${trucksWatchVO.dtu_id}&phone=${trucksWatchVO.phone}" target="_blank">绑定</a>
										             </c:when>
										             <c:otherwise>
										                  <button type="button" class="btn btn-info" onclick="removebind('${trucksWatchVO.dtu_id}')">解绑</button>
										            </c:otherwise>
										          </c:choose>
												 <c:if test="">
												    
												 </c:if>
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
	<script type="text/javascript" src="../js/psylife/trucks/watchlist.js"></script>

</body>
</html>



