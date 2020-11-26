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
            <li class="active"><a href="toEmployee"> <i class="icon-grid"></i>员工信息</a></li>
            <li ><a href="toClient"> <i class="icon-user"></i>客户信息</a></li>
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
      <!-- Counts Section -->
      <section class="dashboard-counts section-padding">
        <div class="container-fluid">
          <div class="row">
            <!-- Count item widget-->
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-user"></i></div>
                <div class="name"><strong class="text-uppercase">员工</strong><span>共计</span>
                  <div class="count-number">10</div>
                </div>
              </div>
            </div>
            <!-- Count item widget-->
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-padnote"></i></div>
                <div class="name"><strong class="text-uppercase">客户拜访次数</strong><span>共计</span>
                  <div class="count-number">237</div>
                </div>
              </div>
            </div>
            <!-- Count item widget-->
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-check"></i></div>
                <div class="name"><strong class="text-uppercase">本月拜访次数</strong><span>共计</span>
                  <div class="count-number">23</div>
                </div>
              </div>
            </div>
            <!-- Count item widget-->
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-bill"></i></div>
                <div class="name"><strong class="text-uppercase">P客户</strong><span>共计</span>
                  <div class="count-number">25</div>
                </div>
              </div>
            </div>
            <!-- Count item widget-->
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-list"></i></div>
                <div class="name"><strong class="text-uppercase">A客户</strong><span>共计</span>
                  <div class="count-number">5</div>
                </div>
              </div>
            </div>
            <!-- Count item widget-->
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-list-1"></i></div>
                <div class="name"><strong class="text-uppercase">C客户</strong><span>共计</span>
                  <div class="count-number">15</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      <!-- Header Section-->
      <section class="dashboard-header section-padding">
        <div class="container-fluid">
          <div class="row d-flex align-items-md-stretch">
            <!-- To Do List-->
            <div class="col-lg-3 col-md-6">
              <div class="card to-do">
                <h2 class="display h4">任务列表</h2>
                <p>员工任务。</p>
                <ul class="check-lists list-unstyled">
                  <li class="d-flex align-items-center"> 
                    <i class="fa fa-heart-o" aria-hidden="true" style="margin-right: 10px;"></i>
                    送红薯
                  </li>                  
                  <li class="d-flex align-items-center"> 
                  <i class="fa fa-heart-o" aria-hidden="true" style="margin-right: 10px;"></i>
                    推广新保险
                  </li>
				  <li class="d-flex align-items-center"> 
                    <i class="fa fa-heart-o" aria-hidden="true" style="margin-right: 10px;"></i>
                    递送保单
                  </li>
                </ul>
              </div>
            </div>
            <!-- Pie Chart-->
            <div class="col-lg-3 col-md-6">
              <div class="card project-progress">
                <h2 class="display h4">完成量</h2>
                <p> 每个任务的完成量。</p>
                <div class="pie-chart">
                  <canvas id="pieChart" width="300" height="300"> </canvas>
                </div>
              </div>
            </div>
            <!-- Line Chart -->
            <div class="col-lg-6 col-md-12 flex-lg-last flex-md-first align-self-baseline">
              <div class="card sales-report">
                <h2 class="display h4">任务分析</h2>
                <p> 每阶段的每个任务的完成量</p>
                <div class="line-chart">
                  <canvas id="lineChart"></canvas>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      <!-- Updates Section -->
      <section class="mt-30px mb-30px">
        <div class="container-fluid">
          <div class="row">
            <div class="col-lg-4 col-md-6">
              <!-- Daily Feed Widget-->
              <div id="daily-feeds" class="card updates daily-feeds">
                <div id="feeds-header" class="card-header d-flex justify-content-between align-items-center">
                  <h2 class="h5 display"><a data-toggle="collapse" data-parent="#daily-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box">员工 </a></h2>
                  <div class="right-column">
                    <a data-toggle="collapse" data-parent="#daily-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box"><i class="fa fa-angle-down"></i></a>
                  </div>
                </div>
                <div id="feeds-box" role="tabpanel" class="collapse show" style="max-height: 750px;overflow-y: auto;">
                  <div class="feed-box">
                    <ul class="feed-elements list-unstyled"  onclick="detail();">
                      <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020001 王启帆</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>15888888888</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15852808761</small></div>
                        </div>
                      </li>                      
                      <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020002 周俊廷</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>Today 5:60 pm - 12.06.2014</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15852335482</small></div>
                        </div>
                      </li>                      
                      <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020003 田展旭</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>Today 5:60 pm - 12.06.2014</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15812547895</small></div>
                        </div>
                      </li>
					  <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020004 徐浩然</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>15888888888</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15814562743</small></div>
                        </div>
                      </li>                      
                      <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020005 唐艺芯</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>Today 5:60 pm - 12.06.2014</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15854668799</small></div>
                        </div>
                      </li>                      
                      <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020006 安禹诺</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>Today 5:60 pm - 12.06.2014</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15821545625</small></div>
                        </div>
                      </li>
					  <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020007 方若熹</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>15888888888</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15854785152</small></div>
                        </div>
                      </li>                      
                      <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020008 赵悦竹</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>Today 5:60 pm - 12.06.2014</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15848756231</small></div>
                        </div>
                      </li>                      
                      <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020009 Ashley Wood</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>Today 5:60 pm - 12.06.2014</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15854126589</small></div>
                        </div>
                      </li>
					  <!-- List-->
                      <li class="clearfix">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong>202020010 Sam Martinez</strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>Today 5:60 pm - 12.06.2014</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>15812156221</small></div>
                        </div>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <!-- Daily Feed Widget End-->
            </div>
            <div class="col-lg-4 col-md-6">
              <!-- Recent Activities Widget      -->
              <div id="recent-activities-wrapper" class="card updates activities">
                <div id="activites-header" class="card-header d-flex justify-content-between align-items-center">
                  <h2 class="h5 display"><a data-toggle="collapse" data-parent="#recent-activities-wrapper" href="#activities-box" aria-expanded="true" aria-controls="activities-box">今天完成任务</a></h2><a data-toggle="collapse" data-parent="#recent-activities-wrapper" href="#activities-box" aria-expanded="true" aria-controls="activities-box"><i class="fa fa-angle-down"></i></a>
                </div>
                <div id="activities-box" role="tabpanel" class="collapse show">
                  <ul class="activities list-unstyled">
                    <!-- Item-->
                    <li>
                      <div class="row">
                        <div class="col-4 date-holder text-right">
                          <div class="icon"><i class="icon-clock"></i></div>
                          <div class="date"> <span>16:00</span></div>
                        </div>
                        <div class="col-8 content"><strong>送红薯</strong>
                          <p>王启帆拜访了赵敏</p>
                        </div>
                      </div>
                    </li>
                    <!-- Item-->
                    <li>
                      <div class="row">
                        <div class="col-4 date-holder text-right">
                          <div class="icon"><i class="icon-clock"></i></div>
                          <div class="date"> <span>16:00</span></div>
                        </div>
                        <div class="col-8 content"><strong>送红薯</strong>
                          <p>周俊廷拜访了赵敏</p>
                        </div>
                      </div>
                    </li>
                    <!-- Item-->
                    <li>
                      <div class="row">
                        <div class="col-4 date-holder text-right">
                          <div class="icon"><i class="icon-clock"></i></div>
                          <div class="date"> <span>15:00</span></div>
                        </div>
                        <div class="col-8 content"><strong>递送保单</strong>
                          <p>王启帆拜访了赵敏</p>
                        </div>
                      </div>
                    </li>
                    <!-- Item-->
                    <li>
                      <div class="row">
                        <div class="col-4 date-holder text-right">
                          <div class="icon"><i class="icon-clock"></i></div>
                          <div class="date"> <span>13:00</span></div>
                        </div>
                        <div class="col-8 content"><strong>递送保单</strong>
                          <p>王启帆拜访了赵敏</p>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
	<!-- Modal -->
	<div class="modal fade" id="employee" tabindex="-1" role="dialog" aria-labelledby="employeeLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
		  <div class="modal-header">
			<h5 class="modal-title" id="employeeLabel">拜访记录</h5>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			  <span aria-hidden="true">&times;</span>
			</button>
		  </div>
		  <div class="modal-body" style="background-color: #F4F7FA;">
			<div class="row">
				<div class="col-lg-4">
					<fieldset>
						<legend>拜访次数</legend>
						<h4 style="text-align:center;">60</h4>
					</fieldset>
				</div>
				<div class="col-lg-8">
					<fieldset>
						<legend>最近拜访时间</legend>
						<h4 style="text-align:center;">2020-01-03 15:45:00</h4>
					</fieldset>
				</div>
				<div class="col-lg-6" style="margin-top:20px;">
					<div class="card polar-chart-example">
						<div class="card-header d-flex align-items-center">
						  <h4>客户分类</h4>
						</div>
						<div class="card-body">
						  <div class="chart-container">
							<canvas id="employeepolarChart"></canvas>
						  </div>
						</div>
					</div>
				</div>
				<div class="col-lg-6" style="margin-top:20px;">
					<div class="card pie-chart-example">
						<div class="card-header d-flex align-items-center">
						  <h4>任务拜访量</h4>
						</div>
						<div class="card-body">
						  <div class="chart-container">
							<canvas id="employeepieChart"></canvas>
						  </div>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="card line-chart-example">
						<div class="card-header d-flex align-items-center">
						  <h4>拜访情况</h4>
						</div>
						<div class="card-body">
						  <canvas id="employeelineChart"></canvas>
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
			
			var PIECHARTEXMPLE    = $('#pieChart'),        
			LINECHARTEXMPLE   = $('#lineChart'),
			EMPLOYEEPIECHARTEXMPLE    = $('#employeepieChart'),        
			EMPLOYEELINECHARTEXMPLE   = $('#employeelineChart'),
			EMPLOYEEPOLARCHART  = $('#employeepolarChart');
			
			
			
			var pieChartExample = new Chart(PIECHARTEXMPLE, {
				type: 'doughnut',
				data: {
					labels: [
						"送红薯",
						"推广新保险",
						"递送保单"
					],
					datasets: [
						{
							data: [30, 25, 40],
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

			var pieChartExample = {
				responsive: true
			};
			
			var lineChartExample = new Chart(LINECHARTEXMPLE, {
				type: 'line',
				data: {
					labels: ["2019-10", "2019-11", "2019-12", "2020-01", "2020-02", "2020-03", "2020-04"],
					datasets: [
						{
							label: "送红薯",
							fill: true,
							lineTension: 0.3,
							backgroundColor: "rgba(51, 179, 90, 0.38)",
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
							data: [1, 2, 3, 2, 4, 2, 1],
							spanGaps: false
						},
						{
							label: "推广新保险",
							fill: true,
							lineTension: 0.3,
							backgroundColor: "rgba(75,192,192,0.4)",
							borderColor: "rgba(75,192,192,1)",
							borderCapStyle: 'butt',
							borderDash: [],
							borderDashOffset: 0.0,
							borderJoinStyle: 'miter',
							borderWidth: 1,
							pointBorderColor: "rgba(75,192,192,1)",
							pointBackgroundColor: "#fff",
							pointBorderWidth: 1,
							pointHoverRadius: 5,
							pointHoverBackgroundColor: "rgba(75,192,192,1)",
							pointHoverBorderColor: "rgba(220,220,220,1)",
							pointHoverBorderWidth: 2,
							pointRadius: 1,
							pointHitRadius: 10,
							data: [2, 3, 1, 5, 4, 2, 3],
							spanGaps: false
						},
						{
							label: "递送保单",
							fill: true,
							lineTension: 0.3,
							backgroundColor: "rgba(255,206,86,0.4)",
							borderColor: "rgba(255,206,86,1)",
							borderCapStyle: 'butt',
							borderDash: [],
							borderDashOffset: 0.0,
							borderJoinStyle: 'miter',
							borderWidth: 1,
							pointBorderColor: "rgba(255,206,86,1)",
							pointBackgroundColor: "#fff",
							pointBorderWidth: 1,
							pointHoverRadius: 5,
							pointHoverBackgroundColor: "rgba(255,206,86,1)",
							pointHoverBorderColor: "rgba(220,220,220,1)",
							pointHoverBorderWidth: 2,
							pointRadius: 1,
							pointHitRadius: 10,
							data: [3, 4, 3, 2, 5, 3, 5],
							spanGaps: false
						}
					]
				}
			});
			
			var employeepieChartExample = new Chart(EMPLOYEEPIECHARTEXMPLE, {
				type: 'doughnut',
				data: {
					labels: [
						"送红薯",
						"推广新保险",
						"递送保单"
					],
					datasets: [
						{
							data: [15, 20, 25],
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

			var employeepieChartExample = {
				responsive: true
			};
			
			var employeelineChartExample = new Chart(EMPLOYEELINECHARTEXMPLE, {
				type: 'line',
				data: {
					labels: ["2019-10", "2019-11", "2019-12", "2020-01", "2020-02", "2020-03", "2020-04"],
					datasets: [
						{
							label: "送红薯",
							fill: true,
							lineTension: 0.3,
							backgroundColor: "rgba(51, 179, 90, 0.38)",
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
							data: [1, 2, 3, 2, 4, 2, 1],
							spanGaps: false
						},
						{
							label: "推广新保险",
							fill: true,
							lineTension: 0.3,
							backgroundColor: "rgba(75,192,192,0.4)",
							borderColor: "rgba(75,192,192,1)",
							borderCapStyle: 'butt',
							borderDash: [],
							borderDashOffset: 0.0,
							borderJoinStyle: 'miter',
							borderWidth: 1,
							pointBorderColor: "rgba(75,192,192,1)",
							pointBackgroundColor: "#fff",
							pointBorderWidth: 1,
							pointHoverRadius: 5,
							pointHoverBackgroundColor: "rgba(75,192,192,1)",
							pointHoverBorderColor: "rgba(220,220,220,1)",
							pointHoverBorderWidth: 2,
							pointRadius: 1,
							pointHitRadius: 10,
							data: [2, 3, 1, 5, 4, 2, 3],
							spanGaps: false
						},
						{
							label: "递送保单",
							fill: true,
							lineTension: 0.3,
							backgroundColor: "rgba(255,206,86,0.4)",
							borderColor: "rgba(255,206,86,1)",
							borderCapStyle: 'butt',
							borderDash: [],
							borderDashOffset: 0.0,
							borderJoinStyle: 'miter',
							borderWidth: 1,
							pointBorderColor: "rgba(255,206,86,1)",
							pointBackgroundColor: "#fff",
							pointBorderWidth: 1,
							pointHoverRadius: 5,
							pointHoverBackgroundColor: "rgba(255,206,86,1)",
							pointHoverBorderColor: "rgba(220,220,220,1)",
							pointHoverBorderWidth: 2,
							pointRadius: 1,
							pointHitRadius: 10,
							data: [3, 4, 3, 2, 5, 3, 5],
							spanGaps: false
						}
					]
				}
			});
			
			var employeepolarChart = new Chart(EMPLOYEEPOLARCHART, {
		        type: 'polarArea',
		        data: {
		            datasets: [{
		                data: [
		                    11,
		                    16,
		                    7
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

		    var employeepolarChart = {
		        responsive: true
		    };


			$('.VivaTimeline').vivaTimeline({
                carousel: true,
                carouselTime: 3000
            });
        
       
		
            
        });
		
		function detail(){
			$('#employee').modal('toggle');
		}
		
	</script>
  </body>
</html>