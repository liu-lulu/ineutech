<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <h2 class="h5">王绵杰</h2><span>银行保险部</span>
          </div>
          <!-- Small Brand information, appears on minimized sidebar-->
          <div class="sidenav-header-logo"><a href="index.html" class="brand-small text-center"> <strong>C</strong><strong class="text-primary">L</strong></a></div>
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
            <div class="col-lg-6 col-md-6">
              <!-- Daily Feed Widget-->
              <div id="daily-feeds" class="card updates daily-feeds">
                <div id="feeds-header" class="card-header d-flex justify-content-between align-items-center">
                  <h2 class="h5 display"><a data-toggle="collapse" data-parent="#daily-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box">客户 </a></h2>
                  <div class="right-column">
                    <a data-toggle="collapse" data-parent="#daily-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box"><i class="fa fa-angle-down"></i></a>
                  </div>
                </div>
                <div id="feeds-box" role="tabpanel" class="collapse show" style="max-height: 750px;overflow-y: auto;">
                  <div class="feed-box">
                    <ul class="feed-elements list-unstyled">
                      <!-- List-->
                      <li class="clearfix" onclick="visit();">
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


					  
                    </ul>
                  </div>
                </div>
              </div>
              <!-- Daily Feed Widget End-->
            </div>
			
			<div class="col-lg-6 col-md-6">
				<!-- Pie Chart-->
				<div class="col-lg-8">
				  <div class="card polar-chart-example">
					<div class="card-header d-flex align-items-center">
					  <h4>客户分类</h4>
					</div>
					<div class="card-body">
					  <div class="chart-container">
						<canvas id="polarChartExample"></canvas>
					  </div>
					</div>
				  </div>
				</div>
				
				<div class="col-lg-8">
				  <div class="card bar-chart-example">
					<div class="card-header d-flex align-items-center">
					  <h4>年龄</h4>
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
	<script>
		$(document).ready(function () {
			var brandPrimary = 'rgba(51, 179, 90, 1)';
			
			var POLARCHARTEXMPLE    = $('#polarChartExample'),
			BARCHARTEXMPLE    = $('#barChartExample'),
			TASKPIECHART    = $('#taskpieChart');
			
			var polarChartExample = new Chart(POLARCHARTEXMPLE, {
		        type: 'polarArea',
		        data: {
		            datasets: [{
		                data: [
		                    25,
		                    30,
		                    10
		                ],
		                backgroundColor: [
		                    "rgba(51, 179, 90, 1)",
		                    "#17a2b8",
		                    "#FFCE56"
		                ],
		                label: '人数' // for legend
		            }],
		            labels: [
		                "P",
		                "A",
		                "C"
		            ]
		        }
		    });
			
			

			var pieChartExample = {
				responsive: true
			};
			
			var barChartExample = new Chart(BARCHARTEXMPLE, {
				type: 'bar',
				data: {
					labels: ["21~30", "31~40", "41~50", "51~60", "61~70", "71~80", "81~90"],
					datasets: [
						{
							label: "人数",
							backgroundColor: [
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)',
								'rgba(51, 179, 90, 0.6)'
							],
							borderColor: [
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)',
								'rgba(51, 179, 90, 1)'
							],
							borderWidth: 1,
							data: [65, 59, 80, 81, 56, 55, 45],
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
			
    
       	
            
        });
		
		function visit(){
			$('#visit').modal('toggle');
		}
		
	</script>
  </body>
</html>