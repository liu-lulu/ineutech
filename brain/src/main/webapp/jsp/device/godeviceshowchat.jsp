<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    String path=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="psylife,脑电">
    <title>脑电</title>
    <!-- Bootstrap core CSS -->
	<link href="../css/bootstrap.min_show.css" rel="stylesheet">
	<link href="../css/simplify.min.css" rel="stylesheet">
	<link href="../css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="../css/font-awesome.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="../css/style2.css" rel="stylesheet">
	<!--[if IE]>
		<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
  </head>
  <body>
  <section id="container">
      <!--header start-->
      <header class="header white-bg">
          <div class="sidebar-toggle-box">
              <div data-original-title="Toggle Navigation" data-placement="right" class="icon-reorder tooltips"></div>
          </div>
          <!--logo start-->
          <a href="#" class="logo" >FU<span>DAN</span></a>
          <!--logo end-->
          <div class="top-nav ">
              <ul class="nav pull-right top-menu">
                  <li>
					 <img src="../img/logo.png"/>
                  </li>
                  <!-- user login dropdown start-->
                  <li class="dropdown">
                      <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                          <img alt="" src="../img/avatar1_small.jpg">
                          <span class="username">Jhon Doue</span>
                          <b class="caret"></b>
                      </a>
                      <ul class="dropdown-menu extended logout">
                          <div class="log-arrow-up"></div>
                          <li><a href="#"><i class="icon-key"></i> Log Out</a></li>
                      </ul>
                  </li>
                  <!-- user login dropdown end -->
              </ul>
          </div>
      </header>
      <!--header end-->
      <!--sidebar start-->
      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu">
                  <li class="">
                      <a class="" href="index.html" style="text-align:center; color:#fff;">
                          <i class=" icon-signal" style="font-size:50px;"></i>
                          <p>index</p>
                      </a>
                  </li>
              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
              <!-- page start-->
			  <div class="row">
				<div class="col-md-4">
					<div class="content-top-1">
						<div class="col-md-6 top-content" style="">
							<h5>Attention</h5>
							<label>专注度</label>
						</div>
						<div class="col-md-6">	   
						<div class="splash" id="splash">
							<div class="splash-inner">
								<h1></h1>
								<!--<h2>Make every day matter</h2>-->
								<div class="loading-circle" id="loadingCircle">
									<p><span id="loadedNum">0</span></p>
									<canvas class="mask" id="loadingProgress" width="140" height="140"></canvas>
									<canvas class="bg" width="140" height="140"></canvas>
								</div>
							</div>
						</div>
						</div>
						<div class="clearfix"> </div>
					</div>
				</div>
			<div class="col-md-4">
				<div class="content-top-1">
					<div class="col-md-6 top-content" style="">
						<h5>Meditation</h5>
						<label>冥想</label>
					</div>
						<div class="col-md-6">	   
						<div class="splash" id="splash">
							<div class="splash-inner">
								<h1></h1>
								<!--<h2>Make every day matter</h2>-->
								<div class="loading-circle" id="loadingCircle">
									<p><span id="loadedNum2">0</span></p>
									<canvas class="mask" id="loadingProgress2" width="140" height="140"></canvas>
									<canvas class="bg" width="140" height="140"></canvas>
								</div>
							</div>
						</div>
						</div>
						<div class="clearfix"> </div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="content-top-1">
					<div class="col-md-6 top-content" style="">
						<h5>Members</h5>
						<label><s:property value="%{devices.size()}"/></label>
					</div>
					<div class="col-md-6">	   
						<i class=" icon-user" style="font-size:150px; line-height:160px; color:#58C9F3;"></i>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
	   </div>
			 <!--图表1-->
			 <div class="row" style="margin-top:20px;">
			    <div class="col-lg-12 col-xs-12">
					<section class="panel">
						<header class="panel-heading">
						   专注度和冥想指标
						   <div class="pull-right">
						      <!-- 
						     <a href="../device/godeviceshowchat.htm"><button type="button" class="btn btn-info">动态</button></a>
							 <a href="../device/godeviceshowchat2.htm"><button type="button" class="btn btn-info">静态</button></a>
							  -->
						   </div>
						</header>
						<div class="panel-body">
						   <!-- <div id="tu1" style="width:100%;height:350px;"></div> -->
						   <div id="containerChatDiv" style="min-width:700px;width:100%;height:350px;"></div>
						</div>
					</section>
				</div>
			 </div>
			 <!--图表1end-->
			 <!--轮播start-->
			 <div id="full" class="carousel slide" data-ride="carousel" data-interval="0">
		    <div class="carousel-inner">
		        <s:iterator value="devices" var="device" status="sts">
		            <s:if test="#sts.first">
		                
		                <ul class="row item active">
		            </s:if>
		            <li class="col-xs-2 four-grid" style="margin-top:40px; cursor: pointer;" onclick="initUserDataChat('<s:property value="#device.shefenId"/>');">
					<div class="photoday-section">
						<div class="four-grid1">
							<div class="icon" style="text-align:center;">
								<i class="glyphicon glyphicon-align-justify bg-color1 " aria-hidden="true"></i>
							</div>
							<div class="contact-box">
							 <h3><strong>观众<s:property value="#device.remark"/>(<s:property value="#device.labelName"/>)</strong></h3>
							 <p><strong>Attention:</strong><span id="attention_<s:property value="#device.shefenId"/>">0</span></p>
							 <p><strong>Meditation:</strong><span id="meditation_<s:property value="#device.shefenId"/>">0</span></p>
							 <p><strong>Delta:</strong><span  id="delta_<s:property value="#device.shefenId"/>">0</span></p>
							 <p><strong>Theta:</strong><span  id="theta_<s:property value="#device.shefenId"/>">0</span></p>
							 <p><strong>Low Alpha:</strong><span  id="low_alpha_<s:property value="#device.shefenId"/>">0</span></p>
							 <p><strong>Hight Alpha:</strong><span  id="high_alpha_<s:property value="#device.shefenId"/>">0</span></p>
							 <p><strong>Low beta:</strong><span  id="low_beta_<s:property value="#device.shefenId"/>">0</span></p>
							 <p><strong>Hight beta:</strong><span  id="high_beta_<s:property value="#device.shefenId"/>">0</span></p>
						  </div>
					   </div>
					 </div>
		           </li>
		        <s:if test="%{#sts.count%6==0}">
		             <s:if test="#sts.last">
		                </ul>
		             </s:if>
		             <s:else>
		                </ul>
		                <ul class="row item">
		             </s:else>
		        </s:if>
		        
		        
		        </s:iterator>
		    </div>
		    <a class="carousel-control left " href="#full" data-slide="prev"></a>
		    <a class="carousel-control right" href="#full" data-slide="next"></a>
		  </div>	
		 </div>		 
		 <!--轮播end-->
		  <!--图表2-->
		   <div class="row" style="margin-top:20px;">
			    <div class="col-lg-12 col-xs-12">
					<section class="panel">
						<header class="panel-heading">
						   专注度和冥想指标
						</header>
						<div class="panel-body">
						  <!--  <div id="bar" style="width:100%;height:500px;"></div> -->
						   <div id="containerUser" style="width:100%;height:500px;"></div>
						</div>
					</section>
				</div>
			 </div>
		  <!--图表2end-->
         <!-- page end-->
          </section>
      </section>
      <!--main content end-->
  </section>
    <!-- js placed at the end of the document so the pages load faster -->
    <script src="../js/jquery-1.8.3.min.js"></script>  
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/jquery.scrollTo.min.js"></script>
	<script src="../js/jquery.nicescroll.js" type="text/javascript"></script>
    
   <script src="../js/index2.js"></script>
    <!--common script for all pages-->
    <script src="../js/common-scripts.js"></script>
    <!-- 
	<script type="text/javascript" src="../js/echarts.min.js"></script>
	 -->
	 <script src="../js/chat/highcharts.js"></script> 
	<script src="../js/hardware/device/godeviceshowchat.js"></script> 
	<script src="../js/hardware/device/pieam.js"></script> 
	<!--  
	<script src="../js/tubiao1.js"></script>
	<script src="../js/tubiao2.js"></script> -->
<script>
   
</script>
  </body>
</html>

   
 