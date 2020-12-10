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

<title>传感器分布</title>

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

<ul>
<c:if test="${fasheqiPosVO!=null&&fasheqiPosVO.trucks_id!=null}">
<li style="float: left; margin: 10px;">
<section class="panel">
<header class="panel-heading">
                              实际胎位置
                          </header>
						<div class="content-item">
					<div class="tire-ctn clearfix">
					<c:forEach items="${fasheqis}" var="fasheqi" varStatus="sts">
					   <c:set value="${sts.count.toString()}" var="countSet"></c:set>
					   <c:choose>
					      <c:when test="${mapbk[countSet]!=null}">
					         <c:choose>
					            <c:when test="${mapbk[countSet]==2}">
					               <div class="tire"></div><div class="tire"></div>
					            </c:when>
					            <c:otherwise>
					               <div class="tire"></div>
					            </c:otherwise>
					         </c:choose>
					      </c:when>
					      <c:when test="${verticalSep[countSet]!=null}">
					         <div class="tire-middle tire-middle-top"></div>
					      </c:when>
					   </c:choose>
					   <c:if test="${map[countSet]!=null}">
					      <div class="tire-horizontal"></div>
					   </c:if>
					   
					   <c:choose>
					      <c:when test="${fasheqi==null}">
					         <div class="tire tire-active tire-null">
					            <a target="_blank">
					               <div class="tire-number">${sts.count}</div>
					               <div class="tire-msg">
					                  <p>未安装</p>
					               </div>
					          </a>
					        </div>
					      </c:when>
					      <c:when test="${fasheqi.shilian==1}">
					         <div class="tire tire-active tire-lost">
					           <a href="../device/historyfasheqilist.action?keyWord=${fasheqi.fasheqi_id}&startTime=<fmt:formatDate value="${fasheqi.caiji_time}" pattern="yyyy-MM-dd HH:mm" />" target="_blank" data-placement="right" data-toggle="tooltip" data-original-title="传感器${fasheqi.fasheqi_id}" class="tooltips">
					              <div class="tire-number">${sts.count}</div>
					              <div class="tire-msg">
					                  <p>失联</p>
					              </div>
					            </a>
					         </div>
					      </c:when>
					      <c:otherwise>
					         <div class="tire tire-active">
					             <a href="../device/historyfasheqilist.action?keyWord=${fasheqi.fasheqi_id}&startTime=<fmt:formatDate value="${fasheqi.caiji_time}" pattern="yyyy-MM-dd HH:mm" />" target="_blank">
					                 <div class="tire-number">${sts.count}</div>
					                 <div class="tire-msg">
					                     <p style="margin-bottom: 3px;">Id号：<span class="">${fasheqi.fasheqi_id}</span></p>
					                     <p style="margin-bottom: 3px;">胎号：<span class="">${fasheqi.tyre_id}</span></p>
					                     <p style="margin-bottom: 3px;">温度：<span class="j_temp_c">${fasheqi.wendu}℃</span></p>
					                     <p style="margin-bottom: 3px;">压力：<span class="j_pressure_b">${fasheqi.yali}bar</span></p>
					                     <p style="margin-bottom: 3px;">报警：<c:choose><c:when test="${fasheqi.warn==1}">有</c:when><c:otherwise>无</c:otherwise></c:choose></p>
					                     
					                </div>
					            </a>
					         </div>
					      </c:otherwise>
					   </c:choose>
					   
					</c:forEach>
					
					</div>
            
        </div>
						</section>
</li>

</c:if>


<li style="float: left; margin: 10px;">

<section class="panel">

<header class="panel-heading">
                           对码器位置   
                          </header>
                          
						<div class="content-item">
					<div class="tire-ctn clearfix">
					
					<c:forEach items="${fasheqis2}" var="fasheqi" varStatus="sts">
					   <c:set value="${sts.count.toString()}" var="countSet"></c:set>
					   <c:choose>
					      <c:when test="${sts.count==1||sts.count==3}">
					         <div class="tire"></div>
					      </c:when>
					      <c:when test="${sts.count==2||(sts.count-1)%4==0}">
					         <div class="tire-middle tire-middle-top"></div>
					      </c:when>
					   </c:choose>
					   <c:if test="${sts.count==11}">
					      <div class="tire-horizontal"></div>
					   </c:if>
					   
					   <c:choose>
					      <c:when test="${fasheqi==null}">
					         <div class="tire tire-active tire-null">
					            <a target="_blank">
					               <div class="tire-number">${numarr[sts.index]}</div>
					               <div class="tire-msg">
					                  <p>未安装</p>
					               </div>
					          </a>
					        </div>
					      </c:when>
					      <c:when test="${fasheqi.shilian==1}">
					         <div class="tire tire-active tire-lost">
					           <a href="../device/historyfasheqilist.action?keyWord=${fasheqi.fasheqi_id}&startTime=<fmt:formatDate value="${fasheqi.caiji_time}" pattern="yyyy-MM-dd HH:mm" />" target="_blank"  data-placement="right" data-toggle="tooltip" data-original-title="传感器${fasheqi.fasheqi_id}" class="tooltips">
					              <div class="tire-number">${numarr[sts.index]}</div>
					              <div class="tire-msg">
					                  <p>失联</p>
					              </div>
					            </a>
					         </div>
					      </c:when>
					      <c:otherwise>
					         <div class="tire tire-active">
					             <a href="../device/historyfasheqilist.action?keyWord=${fasheqi.fasheqi_id}&startTime=<fmt:formatDate value="${fasheqi.caiji_time}" pattern="yyyy-MM-dd HH:mm" />" target="_blank">
					                 <div class="tire-number">${numarr[sts.index]}</div>
					                 <div class="tire-msg">
					                     <p style="margin-bottom: 3px;">Id号：<span class="">${fasheqi.fasheqi_id}</span></p>
					                     <p style="margin-bottom: 3px;">温度：<span class="j_temp_c">${fasheqi.wendu}℃</span></p>
					                     <p style="margin-bottom: 3px;">压力：<span class="j_pressure_b">${fasheqi.yali}bar</span></p>
					                     <p style="margin-bottom: 3px;">报警：<c:choose><c:when test="${fasheqi.warn==1}">有</c:when><c:otherwise>无</c:otherwise></c:choose></p>
					                     
					                </div>
					            </a>
					         </div>
					      </c:otherwise>
					   </c:choose>
					   
					</c:forEach>

					
					</div>
            
            
            
            
        </div>
						</section>
</li>
</ul>
				
	
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



