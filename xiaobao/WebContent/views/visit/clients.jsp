<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>中国人寿</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/font-awesome/css/font-awesome.min.css">
    <!-- Fontastic Custom icon font-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontastic.css">
    <!-- Google fonts - Roboto -->
    <!-- <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700"> -->
    <!-- jQuery Circle-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/grasp_mobile_progress_circle-1.0.0.min.css">
    <!-- Custom Scrollbar-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.default.css" id="theme-stylesheet">
    
    <!-- Favicon-->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/chinalife.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.eeyellow.Timeline.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/audioplayer.css">
	<!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
  	<style type="text/css">

	</style>
  
  </head>
  <body>
    <!-- Side Navbar -->
    <nav class="side-navbar">
      <div class="side-navbar-wrapper">
        <!-- Sidebar Header    -->
        <div class="sidenav-header d-flex align-items-center justify-content-center">
          <!-- User Info-->
          <div class="sidenav-header-inner text-center"><img src="${pageContext.request.contextPath}/img/chinalife.png" alt="person" class="img-fluid rounded-circle">
            <h2 class="h5">${employee.employee_name }</h2><!-- <span>银行保险部</span> -->
          </div>
          <!-- Small Brand information, appears on minimized sidebar-->
          <div class="sidenav-header-logo"><a href="#" class="brand-small text-center"> <strong>C</strong><strong class="text-primary">L</strong></a></div>
        </div>
        <!-- Sidebar Navigation Menus-->
        <div class="main-menu">
          <h5 class="sidenav-heading">Main</h5>
          <ul id="side-main-menu" class="side-menu list-unstyled">                  
            <li><a href="toEmployee"> <i class="icon-grid"></i>员工信息</a></li>
            <li class="active"><a href="toClient"> <i class="icon-user"></i>客户信息</a></li>
          </ul>
        </div>
        
      </div>
    </nav>
    <div class="page">
      <!-- navbar-->
      <header class="header">
        <nav class="navbar">
          <div class="container-fluid">
            <div class="navbar-holder d-flex align-items-center justify-content-between">
              <div class="navbar-header"><a id="toggle-btn" href="#" class="menu-btn"><i class="icon-bars"> </i></a><a href="toEmployee" class="navbar-brand">
                  <div class="brand-text d-none d-md-inline-block"><span>中国 </span><strong class="text-primary">人寿</strong></div></a></div>
              <ul class="nav-menu list-unstyled d-flex flex-md-row align-items-md-center">
                
                <!-- Log out-->
                <li class="nav-item"><a href="toLogin" class="nav-link logout"> <span class="d-none d-sm-inline-block">退出登录</span><i class="fa fa-sign-out"></i></a></li>
              </ul>
            </div>
          </div>
        </nav>
      </header>      
      
      <!-- Updates Section -->
      <section class="mt-30px mb-30px">
        <div class="container-fluid">
          <div class="row">
            <div class="col-lg-4 col-md-4">
              <!-- Daily Feed Widget-->
              <div id="daily-feeds" class="card updates daily-feeds">
                <div id="feeds-header" class="card-header d-flex justify-content-between align-items-center">
                  <h2 class="h5 display"><a data-toggle="collapse" data-parent="#daily-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box">客户  </a></h2>
                  <input class="form-control" style="width:70%;" id="clientName" type="text" placeholder="请输入客户名">
                  <div class="right-column">
                    <a data-toggle="collapse" data-parent="#daily-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box"><i class="fa fa-angle-down"></i></a>
                  </div>
                </div>
                <div id="feeds-box" role="tabpanel" class="collapse show" style="max-height: 750px;overflow-y: auto;">
                  <div class="feed-box">
                    <ul class="feed-elements list-unstyled">
                    <c:forEach items="${clients}" var="client">
                    	<li class="clearfix" onclick="visitShow(${client.client_id })">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content">
                            	<strong><label style="margin-bottom:0;" name="client_name">${fn:substring(client.client_name, 0, 1)}**</label><div <c:if test="${client.client_type=='P'}">class="badge badge-primary"</c:if><c:if test="${client.client_type=='A'}">class="badge badge-info"</c:if><c:if test="${client.client_type=='C'}">class="badge badge-warning"</c:if>>${client.client_type}</div></strong>
                            	<small>${fn:substring(client.client_phone, 0, 3)}****<%-- <c:forEach begin="3" end="${fn:length(client.client_phone)-5}">*</c:forEach> --%>${fn:substring(client.client_phone, fn:length(client.client_phone)-4, fn:length(client.client_phone))}</small>
                              <div class="full-date"><small>${client.client_job}&nbsp;&nbsp;&nbsp;${client.client_marriage}</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;${client.client_label }<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;${client.client_address}<!-- <br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海电气集团股份有限公司 --></small></div>
                          <div class="date">
                          	<small>
                          	${client.age}&nbsp;&nbsp;
                          	<c:if test="${client.client_sex=='男' }">
                          	<i class="fa fa-male fa-lg" aria-hidden="true" style="color: deepskyblue;"></i>
                          	</c:if>
                          	<c:if test="${client.client_sex=='女' }">
                          	<i class="fa fa-female fa-lg" aria-hidden="true" style="color: orangered;"></i>
                          	</c:if>
                          	
                          	</small>
                          	
                          	<i class="fa fa-car" aria-hidden="true" <c:if test="${client.client_car=='是' }">style="color: limegreen;"</c:if>></i>
                          	
                          	<i class="fa fa-home fa-lg" aria-hidden="true" <c:if test="${client.client_house=='是' }">style="color: limegreen;"</c:if>></i>
                          	<!-- <i class="fa fa-car" aria-hidden="true" style="color: limegreen;"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i>--></div> 
                        </div>
                        <!-- <div class="message-card"> <small>股票、金融</small></div>-->
                        <div class="CTAs pull-right"><small style="background-color: #343a40;color: white;border: 1px solid transparent;text-align: center;">${client.employee_name}</small></div> 
                      </li>
                    </c:forEach>
                      <!-- List-->
                      <%-- <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>李贺<div class="badge badge-primary">P</div></strong><small>15852808888 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;未婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;旅游、美食<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;浦东新区华夏小区<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海电气集团股份有限公司</small></div>
                          <div class="date"><small>29&nbsp;&nbsp;<i class="fa fa-female fa-lg" aria-hidden="true" style="color: orangered;"></i></small><i class="fa fa-car" aria-hidden="true" style="color: limegreen;"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>                      
                       <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>程志军<div class="badge badge-info">A</div></strong><small>18835990022 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;篮球<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;徐汇区天平小区<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海市电信有限公司</small></div>
                          <div class="date"><small>45&nbsp;&nbsp;<i class="fa fa-male fa-lg" aria-hidden="true" style="color: deepskyblue;"></i></small><i class="fa fa-car" aria-hidden="true" style="color: limegreen;"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" ></i></div>
                        </div>
                        <div class="message-card"> <small>金融、科技、美食</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>         
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>刘杰<div class="badge badge-warning">C</div></strong><small>15896600271 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;科技、游戏<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;宝山区呼玛三村<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海医药集团股份有限公司</small></div>
                          <div class="date"><small>39&nbsp;&nbsp;<i class="fa fa-male fa-lg" aria-hidden="true" style="color: deepskyblue;"></i></small><i class="fa fa-car" aria-hidden="true" ></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>         
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>唐秀云<div class="badge badge-primary">P</div></strong><small>15896600785 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;金融<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;静安区博园小区<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海普兰金融服务有限公司</small></div>
                          <div class="date"><small>47&nbsp;&nbsp;<i class="fa fa-female fa-lg" aria-hidden="true" style="color: orangered;"></i></small><i class="fa fa-car" aria-hidden="true" style="color: limegreen;"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>         
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>张梅花<div class="badge badge-info">A</div></strong><small>15815644895 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;未婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;游泳<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;闵行区金虹桥<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海东方传媒集团有限公司</small></div>
                          <div class="date"><small>30&nbsp;&nbsp;<i class="fa fa-female fa-lg" aria-hidden="true" style="color: orangered;"></i></small><i class="fa fa-car" aria-hidden="true" style="color: limegreen;"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" ></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>         
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>孙建林<div class="badge badge-warning">C</div></strong><small>15814588524 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;股票<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;宝山区通河一村<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海国际集团有限公司</small></div>
                          <div class="date"><small>42&nbsp;&nbsp;<i class="fa fa-male fa-lg" aria-hidden="true" style="color: deepskyblue;"></i></small><i class="fa fa-car" aria-hidden="true" style="color: limegreen;"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" ></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>         
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>曹艳丽<div class="badge badge-primary">P</div></strong><small>15896644587 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;看小说、看电视剧<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;宝山区呼玛三村<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上海东昌企业集团有限公司</small></div>
                          <div class="date"><small>27&nbsp;&nbsp;<i class="fa fa-female fa-lg" aria-hidden="true" style="color: orangered;"></i></small><i class="fa fa-car" aria-hidden="true" style="color: limegreen;"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>         
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>吴传贵<div class="badge badge-info">A</div></strong><small>15854422874 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;打牌<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;宝山区共江小区<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;退休</small></div>
                          <div class="date"><small>60&nbsp;&nbsp;<i class="fa fa-male fa-lg" aria-hidden="true" style="color: deepskyblue;"></i></small><i class="fa fa-car" aria-hidden="true" ></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>         
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>李文欢<div class="badge badge-warning">C</div></strong><small>15865544105 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;跳舞<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;静安区汇丽花园<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;上汽集团</small></div>
                          <div class="date"><small>58&nbsp;&nbsp;<i class="fa fa-female fa-lg" aria-hidden="true" style="color: orangered;"></i></small><i class="fa fa-car" aria-hidden="true"></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li>
					  <!-- List-->
                      <li class="clearfix" onclick="visit();">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>王明忠<div class="badge badge-primary">P</div></strong><small>15835544789 </small>
                              <div class="full-date"><small>经理&nbsp;&nbsp;&nbsp;已婚</small></div>
                            </div>
                          </div>
						  <div><small><i class="fa fa-tags" aria-hidden="true"></i>&nbsp;&nbsp;运动<br><i class="fa fa-location-arrow" aria-hidden="true"></i>&nbsp;&nbsp;闵行区古北壹号<br><i class="fa fa-building" aria-hidden="true"></i>&nbsp;中国远洋海运集团有限公司</small></div>
                          <div class="date"><small>48&nbsp;&nbsp;<i class="fa fa-male fa-lg" aria-hidden="true" style="color: deepskyblue;"></i></small><i class="fa fa-car" aria-hidden="true" ></i>&nbsp;&nbsp;<i class="fa fa-home fa-lg" aria-hidden="true" style="color: limegreen;"></i></div>
                        </div>
                        <div class="message-card"> <small>股票、金融</small></div>
                        <div class="CTAs pull-right"><a href="#" class="btn btn-xs btn-dark">wang</a></div>
                      </li> 
 --%>

					  
                    </ul>
                  </div>
                </div>
              </div>
              <!-- Daily Feed Widget End-->
            </div>
			
			<div class="col-lg-4 col-md-4" id="visitDiv" style="display:none;">
              <!-- Daily Feed Widget-->
              <div id="visit-feeds" class="card updates daily-feeds">
                <div id="feeds-header" class="card-header d-flex justify-content-between align-items-center">
                  <h2 class="h5 display"><a data-toggle="collapse" data-parent="#visit-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box">拜访信息 </a></h2>
                  <div class="right-column">
                    <a data-toggle="collapse" data-parent="#visit-feeds" href="#" onclick="visitHide()" aria-expanded="true" aria-controls="feeds-box"><i class="fa fa-close"></i></a>
                  </div>
                </div>
                <div id="visit-box" role="tabpanel" class="collapse show" >
                  <div class="feeds-box">
                    <div class="row" style="margin:0;">
						<div class="col-lg-5">
							<fieldset>
								<legend>拜访次数</legend>
								<h4 style="text-align:center;" id="visitNum"></h4>
							</fieldset>
						</div>
						<div class="col-lg-7">
							<fieldset>
								<legend>最近拜访时间</legend>
								<h4 style="text-align:center;" id="latestVisitTime"></h4>
							</fieldset>
						</div>
						
						<div class="col-lg-12">
							<div class="card">
								<div class="card-header d-flex align-items-center">
									<h4>拜访记录</h4>
								</div>
								<div class="card-body" style="max-height: 400px;overflow-y: auto;">
									<div class="VivaTimeline">
										<dl id="visitDetail">								
											
											
										</dl>
									</div>
								</div>
							</div>
						</div>
				
						
					</div>
                  </div>
                </div>
              </div>
              <!-- Daily Feed Widget End-->
            </div>
            
			<div class="col-lg-4 col-md-4">
				<!-- Pie Chart-->
				<div class="col-lg-12">
				  <div class="card polar-chart-example">
					<div class="card-header d-flex align-items-center">
					  <h4>性别</h4>
					  <input type="hidden" id="sexCount" value='${sexCount }'>
					</div>
					<div class="card-body">
					  <div class="chart-container">
						<canvas id="polarChartExample"></canvas>
					  </div>
					</div>
				  </div>
				</div>
				
				<div class="col-lg-12">
				  <div class="card bar-chart-example">
					<div class="card-header d-flex align-items-center">
					  <h4>年龄</h4>
					  <input type="hidden" id="ageCount" value='${ageCount }'>
					</div>
					<div class="card-body">
					  <canvas id="barChartExample"></canvas>
					</div>
				  </div>
				</div>
			
			</div>
			
          </div>
        </div>
      </section>
	  
	<!-- Modal -->
	<div class="modal fade" id="visit" tabindex="-1" role="dialog" aria-labelledby="visitLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
		  <div class="modal-header">
			<h5 class="modal-title" id="visitLabel">拜访记录</h5>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			  <span aria-hidden="true">&times;</span>
			</button>
		  </div>
		  <div class="modal-body" style="background-color: #F4F7FA;">
			<div class="row">
				<div class="col-lg-4">
					<fieldset>
						<legend>拜访次数</legend>
						<h4 style="text-align:center;">3</h4>
					</fieldset>
				</div>
				<div class="col-lg-8">
					<fieldset>
						<legend>最近拜访时间</legend>
						<h4 style="text-align:center;">2020-01-03 15:45:00</h4>
					</fieldset>
				</div>
			
				<div class="col-lg-12">
					<fieldset>
						<legend>最近拜访讨论关键词</legend>
						<h4>金融、科技、美食</h4>
					</fieldset>
				</div>
				
				<div class="col-lg-12">
					<fieldset>
						<legend>客户标签</legend>
						<h4>积极、金融、科技、健康、运动</h4>
					</fieldset>
				</div>
				
					
				
				<div class="col-lg-6" style="margin-top:20px;">
				  <div class="card bar-chart-example">
					<div class="card-header d-flex align-items-center">
					  <h4>拜访次数</h4>
					</div>
					<div class="card-body">
					  <canvas id="visitbarChart"></canvas>
					</div>
				  </div>
				</div>
				<div class="col-lg-6" style="margin-top:20px;">
					<div class="card line-chart-example">
						<div class="card-header d-flex align-items-center">
						  <h4>情绪识别</h4>
						</div>
						<div class="card-body">
						  <canvas id="visitlineChart"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="card pie-chart-example">
						<div class="card-header d-flex align-items-center">
						  <h4>任务拜访量</h4>
						</div>
						<div class="card-body">
						  <div class="chart-container">
							<canvas id="taskpieChart"></canvas>
						  </div>
						</div>
					</div>
				</div>
				
				<div class="col-lg-6">
					<div class="card">
						<div class="card-header d-flex align-items-center">
							<h4>拜访记录</h4>
						</div>
						<div class="card-body">
							<div class="VivaTimeline">
								<dl>								
									<dd class="pos-right clearfix">
										<div class="circ"></div>
										<div class="time">5-01</div>
										<div class="events" style="float: left;">
											
											<div class="events-header">
												<div>您当面拜访了赵敏（事宜:递送保单）</div>
												<div>您修改了赵敏的基本信息</div>
											</div>
											
										</div>
									</dd>
		
									<dd class="pos-right clearfix">
										<div class="circ"></div>
										<div class="time">2-01</div>
										<div class="events" style="float: left;">
											
											<div class="events-header">
												<div>您当面拜访了赵敏（事宜:递送保单）</div>
												<div>您修改了赵敏的基本信息</div>
												<div>您修改了赵敏的基本信息</div>
											</div>
											
										</div>
									</dd>
										
									<dd class="pos-right clearfix">
										<div class="circ"></div>
										<div class="time">1-01</div>
										<div class="events" style="float: left;">
											
											<div class="events-header">
												<div>您当面拜访了赵敏（事宜:递送保单）</div>
												
											</div>
											
										</div>
									</dd>	
									
								</dl>
							</div>
						</div>
					</div>
				</div>
		
				
			</div>
			
		  </div>
		  <div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
			
		  </div>
		</div>
	  </div>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="voiceContentModal" tabindex="-1" role="dialog" aria-labelledby="voiceModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="voiceModalLabel">
						音频内容
					</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					
				</div>
				<div class="modal-body" id="voiceContent" >
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>				
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!--放大图的imgModal-->
        <div class="modal fade bs-example-modal-lg text-center" id="imgModal"tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >
          <div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">
            <div class="modal-content">
             <img  id="imgInModalID" src="" >
            </div>
          </div>
        </div>
        
        <!-- 模态框（Modal） -->
	<div class="modal fade" id="wechatContentModal" tabindex="-1" role="dialog" aria-labelledby="wechatModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="wechatModalLabel">
						微信聊天记录
					</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					
				</div>
				<div class="modal-body" id="wechatContent" style="max-height: 600px;overflow-y: auto;">
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>				
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
      <footer class="main-footer">
        <div class="container-fluid">
          <div class="row">
            <div class="col-sm-6">
              <p>China Life All rights reserved.  &copy; 2019-2020</p>
            </div>
            <div class="col-sm-6 text-right">
              
              
            </div>
          </div>
        </div>
      </footer>
    </div>
    <!-- JavaScript files-->
    <script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/vendor/popper.js/umd/popper.min.js"> </script>
    <script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/grasp_mobile_progress_circle-1.0.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="${pageContext.request.contextPath}/vendor/chart.js/Chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/vendor/jquery-validation/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/vendor/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
    
    <!-- Main File-->
    <script src="${pageContext.request.contextPath}/js/front.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.eeyellow.Timeline.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/audioplayer.js"></script>
	<script>
	String.prototype.replaceAll  = function(s1,s2){     
	    return this.replace(new RegExp(s1,"gm"),s2);     
	}   
		var baseUrl=window.location.protocol+"//"+window.location.host+"/xiaobaovisit/";
		$(document).ready(function () {
			
			var sexCount=JSON.parse($("#sexCount").val());
			var ageCount=JSON.parse($("#ageCount").val());
			
			var brandPrimary = 'rgba(51, 179, 90, 1)';
			
			var POLARCHARTEXMPLE    = $('#polarChartExample'),
			BARCHARTEXMPLE    = $('#barChartExample'),
			TASKPIECHART    = $('#taskpieChart');
			
			var polarChartExample = new Chart(POLARCHARTEXMPLE, {
		        type: 'polarArea',
		        data: {
		            datasets: [{
		                data: [sexCount.男,sexCount.女],
		                backgroundColor: [
		                    "deepskyblue",
		                    "orangered"
		                ],
		                label: '人数' // for legend
		            }],
		            labels: ['男','女']
		        }
		    });
			
			

			var pieChartExample = {
				responsive: true
			};
			
			var backgroundColorData=[Object.keys(ageCount).length];
			var borderColorData=[Object.keys(ageCount).length];
			for(var i=0;i<Object.keys(ageCount).length;i++){
				backgroundColorData[i]='rgba(51, 179, 90, 0.6)';
				borderColorData[i]='rgba(51, 179, 90, 1)';
			}
			//console.log(borderColorData);
			var barChartExample = new Chart(BARCHARTEXMPLE, {
				type: 'bar',
				data: {
					labels: Object.keys(ageCount),
					datasets: [
						{
							label: "人数",
							backgroundColor: backgroundColorData,
							borderColor: borderColorData,
							borderWidth: 1,
							data: Object.values(ageCount)
						}
					]
				}
			});
			
			var VISITLINECHART   = $('#visitlineChart'),        
			VISITBARCHART    = $('#visitbarChart');		
			
			
			var visitbarChart = new Chart(VISITBARCHART, {
				type: 'bar',
				data: {
					labels: ["2020-02","2020-03", "2020-04"],
					datasets: [
						{
							label: "拜访次数",
							backgroundColor: [
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)'
							],
							borderColor: [
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)'
							],
							borderWidth: 1,
							data: [1, 1, 1],
						}
					]
				}
			});
			
			var visitlineChart = new Chart(VISITLINECHART, {
				type: 'line',
				data: {
					labels: ["2020-02","2020-03", "2020-04"],
					datasets: [
						{
							label: "情绪",
							fill: true,
							lineTension: 0.3,
							backgroundColor: "transparent",
							borderColor: brandPrimary,
							borderCapStyle: 'butt',
							borderDash: [],
							borderDashOffset: 0.0,
							borderJoinStyle: 'miter',
							borderWidth: 1,
							pointBorderColor: brandPrimary,
							pointBackgroundColor: "#fff",
							pointBorderWidth: 1,
							pointHoverRadius: 5,
							pointHoverBackgroundColor: brandPrimary,
							pointHoverBorderColor: "rgba(220,220,220,1)",
							pointHoverBorderWidth: 2,
							pointRadius: 1,
							pointHitRadius: 10,
							data: [90,70, 80],
							spanGaps: false
						}
					]
				}
			});
			
			var taskpieChart = new Chart(TASKPIECHART, {
				type: 'doughnut',
				data: {
					labels: [
						"送红薯",
						"推广新保险",
						"递送保单"
					],
					datasets: [
						{
							data: [1, 1, 1],
							borderWidth: [1, 1, 1],
							backgroundColor: [
								brandPrimary,
								"rgba(75,192,192,1)",
								"#FFCE56"
							],
							hoverBackgroundColor: [
								brandPrimary,
								"rgba(75,192,192,1)",
								"#FFCE56"
							]
						}]
					}
			});

			var taskpieChart = {
				responsive: true
			};
        
       
		
            $('.VivaTimeline').vivaTimeline({
                carousel: true,
                carouselTime: 3000
            });
			
            $('#voiceContentModal').on('hide.bs.modal',
    				function() {
    				$('#voiceContent').children().remove();
    		    });
            
            $("#clientName").on("keyup", function() {
                var value = $(this).val();
                $("label[name='client_name']").filter(function() {
                   $(this).closest("li").toggle($(this).text().indexOf(value) > -1)
                });
             });
       	
            
        });
		
		function visitHide(){
			$('#visitDiv').hide("slow");
		}
		
		function visitShow(client_id){
			$("#visitNum").text("");
			$("#latestVisitTime").text("");
			$("#visitDetail").empty();
			$.ajax({
				type: "GET",
				url: "../api/visit",
				data: {'clientId':client_id},
				dataType:"json",
				
				success: function(data){
					var visitRecord=data.data.records;
					var visitNum=visitRecord.length;
					$("#visitNum").text(visitNum);
					if(visitNum>0){
						$("#latestVisitTime").text(visitRecord[0].visit_time);
						for(var i = 0;i<visitRecord.length;i++){
							var baseUrl=window.location.protocol+"//"+window.location.host+"/xiaobaovisit/";
							var allvoice="";
							for(var j = 0;j<visitRecord[i].voices.length;j++){
								var voice="<i class='fa fa-volume-up' aria-hidden='true' onclick='showVoice("+visitRecord[i].voices[j].voice_id+")'>&nbsp;</i>";
								allvoice+=voice;
							}
							var client=visitRecord[i].client;
							var clientName=client.client_name;
							
							var imgInfo="";
							if(visitRecord[i].img_file!=null&&visitRecord[i].img_file!=''){
								var imgUrl=baseUrl+"visit_img/"+visitRecord[i].img_file;
								//imgInfo="<img src='"+imgUrl+"' style='float: right;margin-top: -10px;' class='img-thumbnail' width='50' height='50' data-container='body' data-toggle='popover' data-trigger='hover' data-placement='top' data-html='true' data-content='"+'<img src="'+imgUrl+'" width="250">'+"' >";
								imgInfo="<img src='"+imgUrl+"' onclick='showBigImg("+'"'+imgUrl+'"'+")' style='float: right;margin-top: -10px;' class='img-thumbnail' width='50' height='50' >";
							}

							var wechatContent="";
							if(visitRecord[i].purpose=='微信聊天'){
								wechatContent="<i class='fa fa-wechat' aria-hidden='true' style='color: green;' onclick='showWechat("+visitRecord[i].visit_id+")'></i>";
								
							}
							
							var dd="<dd class='pos-right clearfix'>"+
									"<div class='circ'></div>"+
									"<div class='time'>"+visitRecord[i].visit_time.substr(5,5)+"</div>"+
									"<div class='events' style='float: left;'>"+
										"<div class='events-header' style='float: left;width: 100%;'>"+
											"<div style='float: left;'>"+visitRecord[i].employee.employee_name+"拜访了"+clientName.substring(0,1)+"**（事宜:"+visitRecord[i].purpose+"）"+wechatContent+"<br>"+allvoice+"</div>"+
											imgInfo+
										"</div>"+
										
									"</div>"+
								"</dd>";
								
							$("#visitDetail").append(dd);
							//$("[data-toggle='popover']").popover();
						}
						
						
					}
				},
				error: function(msg){
					console.log(msg);
					//alert("error"+msg);
				}
			});
			
			
			$('#visitDiv').show("slow");
			
		}
		
		function showBigImg(imgSrc){
			$("#imgInModalID").attr("src",imgSrc);
			$('#imgModal').modal('show');
		}
		function showVoice(voice_id){
			$('#voiceContent').children().remove();
			$.ajax({
				type: "GET",
				url: "../api/voiceContent",
				data: {'voice_id':voice_id},
				dataType:"json",
				
				success: function(data){
					var voiceUrl=baseUrl+"visit_voice/"+data.data.voice_file;
					var voiceContent=data.data.voice_content;
					
					if(voiceContent==null){
						$('#voiceContent').html("<audio preload='auto' controls ><source src='"+voiceUrl+"' ></audio>");
						$('#voiceContentModal audio').audioPlayer();
						$('#voiceContentModal').modal('show');
					}else{
						$.ajax({
							type: "GET",
							url: "../api/keyword",
							dataType:"json",
							
							success: function(data){
								for(var i = 0;i<data.data.length;i++){
									var keyword=data.data[i].keyword;									
									voiceContent=voiceContent.replaceAll(keyword,"<em>"+keyword+"</em>");
								}
								
								$('#voiceContent').html("<audio preload='auto' controls ><source src='"+voiceUrl+"' ></audio><div style='max-height: 600px;overflow-y: auto;'>"+voiceContent+"</div>");
								$('#voiceContentModal audio').audioPlayer();
								$('#voiceContentModal').modal('show');
							},
							error: function(msg){
								console.log(msg);
							}
						});
						
					}
				},
				error: function(msg){
					
					alert("error"+msg);
				}
			});
			
			
		}
		
		function showWechat(visit_id){
			$.ajax({
				type: "GET",
				url: "../api/wechat",
				data: {'visit_id':visit_id},
				dataType:"json",
				
				success: function(data){
					$('#wechatContent').html(data.data);
					$('#wechatContentModal').modal('show');
				},
				error: function(msg){
					
					alert("error"+msg);
				}
			});
			
			
		}
	</script>
  </body>
</html>