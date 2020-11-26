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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bs4.pop.css">
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
            <h2 class="h5">${employee.employee_name }</h2><!-- <span>银行保险部</span> -->
          </div>
          <!-- Small Brand information, appears on minimized sidebar-->
          <div class="sidenav-header-logo"><a href="#" class="brand-small text-center"> <strong>C</strong><strong class="text-primary">L</strong></a></div>
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
            <div class="col-xl-4 col-md-4 col-4">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-user"></i></div>
                <div class="name"><strong class="text-uppercase">员工</strong><span>共计</span>
                  <div class="count-number">${fn:length(employees) }</div>
                </div>
              </div>
            </div>
            <!-- Count item widget-->
            <div class="col-xl-4 col-md-4 col-4">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-padnote"></i></div>
                <div class="name"><strong class="text-uppercase">客户拜访次数</strong><span>共计</span>
                  <div class="count-number">${visitClientNum }</div>
                </div>
              </div>
            </div>
            <!-- Count item widget-->
            <div class="col-xl-4 col-md-4 col-4">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-check"></i></div>
                <div class="name"><strong class="text-uppercase">本月拜访次数</strong><span>共计</span>
                  <div class="count-number">${visitClientCurMonth }</div>
                </div>
              </div>
            </div>
            <!-- <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-bill"></i></div>
                <div class="name"><strong class="text-uppercase">P客户</strong><span>共计</span>
                  <div class="count-number">25</div>
                </div>
              </div>
            </div>
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-list"></i></div>
                <div class="name"><strong class="text-uppercase">A客户</strong><span>共计</span>
                  <div class="count-number">5</div>
                </div>
              </div>
            </div>
            <div class="col-xl-2 col-md-4 col-6">
              <div class="wrapper count-title d-flex">
                <div class="icon"><i class="icon-list-1"></i></div>
                <div class="name"><strong class="text-uppercase">C客户</strong><span>共计</span>
                  <div class="count-number">15</div>
                </div>
              </div>
            </div> -->
            
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
                <h2 class="display h4">关键字</h2>
                <p>谈话关键字&nbsp;&nbsp;<i class="fa fa-plus-square-o" aria-hidden="true" onclick="addKeyword()"></i></p>
                <ul class="check-lists list-unstyled" id="keywordslist" style="max-height:350px;overflow:auto;">
                
                <c:forEach items="${keywords }" var="keyword">
                	<li class="d-flex align-items-center" id="keyword_id_${keyword.keyword_id}"> 
                    <i class="fa fa-heart-o" aria-hidden="true" style="margin-right: 10px;"></i>
                    ${keyword.keyword}&nbsp;&nbsp;<i class="fa fa-minus-square-o" aria-hidden="true" style="color: #888;" onclick="delKeyword(${keyword.keyword_id},'${keyword.keyword}')"></i>
                  </li>
                </c:forEach>
                
                </ul>
                
              </div>
            </div>
            <!-- Pie Chart-->
            <div class="col-lg-3 col-md-6">
              <div class="card project-progress">
                <h2 class="display h4">完成量</h2>
                <input type="hidden" id="purposeCount" value='${purposeCount }'>
                <p> 每个任务的完成量</p>
                <div class="pie-chart">
                  <canvas id="pieChart" width="300" height="300"> </canvas>
                </div>
              </div>
            </div>
            <!-- Line Chart -->
            <div class="col-lg-6 col-md-12 flex-lg-last flex-md-first align-self-baseline">
              <div class="card sales-report">
                <h2 class="display h4">任务分析</h2>
                <input type="hidden" id="purposeDateCount" value='${purposeDateCount }'>
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
                  <input class="form-control" style="width:70%;" id="employeeName" type="text" placeholder="请输入员工名">
                  <div class="right-column">
                    <a data-toggle="collapse" data-parent="#daily-feeds" href="#feeds-box" aria-expanded="true" aria-controls="feeds-box"><i class="fa fa-angle-down"></i></a>
                  </div>
                </div>
                <div id="feeds-box" role="tabpanel" class="collapse show" style="max-height: 750px;overflow-y: auto;">
                  <div class="feed-box">
                    <ul class="feed-elements list-unstyled">
                      <!-- List-->
                      <c:forEach var="employee" items="${employees }">
                      	<li class="clearfix" onclick="visitShow(${employee.employee_id })">
                        <div class="feed d-flex justify-content-between">
                          <div class="feed-body d-flex justify-content-between"><a href="#" class="feed-profile"><img src="${pageContext.request.contextPath}/img/user.png" alt="person" class="img-fluid rounded-circle"></a>
                            <div class="content"><strong><label style="margin-bottom:0;" name="employee_name">${employee.employee_name }</label></strong><small>业务员 </small>
                              <!-- <div class="full-date"><small>15888888888</small></div>-->
                            </div>
                          </div>
                          <div class="date"><small>${employee.employee_phone}</small></div>
                        </div>
                      	</li>
                      </c:forEach>
                      
                    </ul>
                  </div>
                </div>
              </div>
              <!-- Daily Feed Widget End-->
            </div>
            <div class="col-lg-4 col-md-6" id="visitDiv" style="display:none;">
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
						<%-- <div class="col-lg-6" style="margin-top:20px;">
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
						</div> --%>
						
						
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
            
            <div class="col-lg-4 col-md-6">
              <!-- Recent Activities Widget      -->
              <div id="recent-activities-wrapper" class="card updates activities">
                <div id="activites-header" class="card-header d-flex justify-content-between align-items-center">
                  <h2 class="h5 display"><a data-toggle="collapse" data-parent="#recent-activities-wrapper" href="#activities-box" aria-expanded="true" aria-controls="activities-box">今天完成任务</a></h2><a data-toggle="collapse" data-parent="#recent-activities-wrapper" href="#activities-box" aria-expanded="true" aria-controls="activities-box"><i class="fa fa-angle-down"></i></a>
                </div>
                <div id="activities-box" role="tabpanel" class="collapse show">
                  <ul class="activities list-unstyled" style="max-height: 550px;overflow: auto;">
                  <c:forEach items="${curDateRecords }" var="curDateRecord">
                  	<!-- Item-->
                    <li>
                      <div style="display:flex;">
                        <div class="col-3 date-holder text-right">
                          <div class="icon"><i class="icon-clock"></i></div>
                          <div class="date"> <span>${fn:substring(curDateRecord.visit_time,11,16) }</span></div>
                        </div>
                        <div class="col-9 content"><strong>${curDateRecord.purpose }</strong>
                          <p>${curDateRecord.employee.employee_name }拜访了${curDateRecord.client.client_name }</p>
                        </div>
                      </div>
                    </li>
                  </c:forEach>
                    
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      <!--放大图的imgModal-->
        <div class="modal fade bs-example-modal-lg text-center" id="imgModal"tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >
          <div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">
            <div class="modal-content">
             <img  id="imgInModalID" src="" >
            </div>
          </div>
        </div>

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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bs4.pop.js"></script>
	<script>
	String.prototype.replaceAll  = function(s1,s2){     
	    return this.replace(new RegExp(s1,"gm"),s2);     
	}   
		var baseUrl=window.location.protocol+"//"+window.location.host+"/xiaobaovisit/";
	
		var allRGB=['rgba(51, 179, 90,1)','rgba(75,192,192,1)','rgba(23,162,184,1)','rgba(255,206,86,1)','rgba(255,222,173,1)'];
		var defaultRGB='rgba(220,220,220,1)';
		$(document).ready(function () {
			
			var purposeCountPai=JSON.parse($("#purposeCount").val());
			var purposeDateCountLine=JSON.parse($("#purposeDateCount").val());
			
			var taskRGB={};
			
			for(var j=0;j<Object.keys(purposeCountPai).length;j++){
				var purpose=Object.keys(purposeCountPai)[j];
				if(j<allRGB.length){
					taskRGB[purpose]=allRGB[j];
				}else{
					taskRGB[purpose]=defaultRGB;
				}
			}
			
			var purposeDateCountLineDataSets=[];
			for(var key in purposeDateCountLine.purposeNum){
				
				var purposeDateCountLineData={};
				purposeDateCountLineData.label=key;
				purposeDateCountLineData.data=purposeDateCountLine.purposeNum[key];
				purposeDateCountLineData.fill=true;
				purposeDateCountLineData.lineTension= 0.3;
				purposeDateCountLineData.backgroundColor= taskRGB[key].substring(0,taskRGB[key].length-2)+"0.38)";
				purposeDateCountLineData.borderColor= taskRGB[key];
				purposeDateCountLineData.borderCapStyle= 'butt';
				purposeDateCountLineData.borderDash= [];
				purposeDateCountLineData.borderDashOffset= 0.0;
				purposeDateCountLineData.borderJoinStyle= 'miter';
				purposeDateCountLineData.borderWidth= 1;
				purposeDateCountLineData.pointBorderColor= taskRGB[key];
				purposeDateCountLineData.pointBackgroundColor= "#fff";
				purposeDateCountLineData.pointBorderWidth= 1;
				purposeDateCountLineData.pointHoverRadius= 5;
				purposeDateCountLineData.pointHoverBackgroundColor= taskRGB[key];
				purposeDateCountLineData.pointHoverBorderColor= "rgba(220,220,220,1)";
				purposeDateCountLineData.pointHoverBorderWidth= 2;
				purposeDateCountLineData.pointRadius= 1;
				purposeDateCountLineData.pointHitRadius= 10;
				purposeDateCountLineData.spanGaps= false;
				
				purposeDateCountLineDataSets.push(purposeDateCountLineData);
			}
			//console.log(purposeDateCountLineDataSets);
			
			var brandPrimary = 'rgba(51, 179, 90, 1)';
			
			var PIECHARTEXMPLE    = $('#pieChart'),        
			LINECHARTEXMPLE   = $('#lineChart'),
			
			EMPLOYEEPIECHARTEXMPLE    = $('#employeepieChart'),        
			EMPLOYEELINECHARTEXMPLE   = $('#employeelineChart'),
			EMPLOYEEPOLARCHART  = $('#employeepolarChart');
			
			var pieChartExample = new Chart(PIECHARTEXMPLE, {
				type: 'doughnut',
				data: {
					labels: Object.keys(purposeCountPai),
					datasets: [
						{
							data: Object.values(purposeCountPai),
							borderWidth: [1, 1, 1],
							backgroundColor: Object.values(taskRGB),
							hoverBackgroundColor: Object.values(taskRGB)
						}]
					}
			});

			var pieChartExample = {
				responsive: true
			};
			
			var lineChartExample = new Chart(LINECHARTEXMPLE, {
				type: 'line',
				data: {
					labels: purposeDateCountLine.visitDate,
					datasets: purposeDateCountLineDataSets
					/* datasets: [
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
					] */
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
        
       
			$('#voiceContentModal').on('hide.bs.modal',
				function() {
				$('#voiceContent').children().remove();
		    });
			
			$("#employeeName").on("keyup", function() {
                var value = $(this).val();
                $("label[name='employee_name']").filter(function() {
                   $(this).closest("li").toggle($(this).text().indexOf(value) > -1)
                });
             });
            
        });
		
		function detail(){
			$('#employee').modal('toggle');
		}
		
		function visitHide(){
			$('#visitDiv').hide("slow");
		}
		
		function visitShow(employee_id){
			$("#visitNum").text("");
			$("#latestVisitTime").text("");
			$("#visitDetail").empty();
			$.ajax({
				type: "GET",
				url: "../api/visit",
				data: {'employeeId':employee_id},
				dataType:"json",
				
				success: function(data){
					var visitRecord=data.data.records;
					var visitNum=visitRecord.length;
					$("#visitNum").text(visitNum);
					if(visitNum>0){
						$("#latestVisitTime").text(visitRecord[0].visit_time);
						for(var i = 0;i<visitRecord.length;i++){
							
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
											"<div style='float: left;'>拜访了"+clientName+"（事宜:"+visitRecord[i].purpose+"）"+wechatContent+"<br>"+allvoice+"</div>"+
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
					console.log(msg);
					//alert("error"+msg);
				}
			});
			
			
		}
		
		function delKeyword(id,keyword){
			bs4pop.confirm("确定要删除:"+keyword+"?", function(sure){ 
				if (sure){
					$.ajax({
						type: "DELETE",
						url: "../api/keyword?keyword_id="+id,
						//data: {'keyword_id':id},
						dataType:"json",
						
						success: function(data){
							if(data.code='success'){
								bs4pop.alert('删除成功', function(){$('#keyword_id_'+id).remove();});
							}else{
								bs4pop.alert('删除失败', function(){});
							}
							
						},
						error: function(msg){
							console.log(msg);
							//alert("error"+msg);
						}
					});
					
					
				}else{
					
				}
			});
		}
		
		function addKeyword(){
			bs4pop.prompt('请输入关键字', '', function(sure, value){ 
				if(sure){
					$.ajax({
						type: "POST",
						url: "../api/keyword",
						data: {'keyword':value},
						dataType:"json",
						
						success: function(data){
							if(data.code='success'){
								bs4pop.alert('添加成功:'+value, function(){
									var id=data.data;
									var keywordli="<li class='d-flex align-items-center' id='keyword_id_"+id+"'> <i class='fa fa-heart-o' aria-hidden='true' style='margin-right: 10px;'></i>"+value+"&nbsp;&nbsp;<i class='fa fa-minus-square-o' aria-hidden='true' style='color: #888;' onclick='delKeyword("+id+',"'+value+'")'+"'></i></li>";
									$("#keywordslist").append(keywordli);
								});
							}else{
								bs4pop.alert('添加失败', function(){});
							}
						},
						error: function(msg){
							console.log(msg);
							//alert("error"+msg);
						}
					});
					
					
					
					
				}
			});
		}
	</script>
  </body>
</html>