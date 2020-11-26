<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<title>嘉宾信息</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap-table.min.css">
<link rel="stylesheet" href="css/bootstrap-table-fixed-columns.css">
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="js/bootstrap-table.min.js"></script>
<script src="js/bootstrap-table-fixed-columns.js"></script>

<!-- page -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/kkpager.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/paging.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/kkpager_blue.css" />
	
<!-- mobile Calendar -->
<script src="${pageContext.request.contextPath}/js/lCalendar.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/lCalendar.css" />
<style>

/* td {
	vertical-align: middle;
	width: max-content;
    padding: 0 5px;
}

tr:nth-child(odd) {
	color: #7f7f7f;
	background-color: #ebf0f6;
}

tr:nth-child(even) {
	background-color: #a3aeca;
}

.fixedTable thead tr {
	background-color: #2dc3f8
}

.table {
	width: 100%;
	overflow-x: scroll;
	background-color: #7c95b5;
	margin-bottom: 0px;
}

.fixedTable {
	width: max-content;
	
	text-align: center;
	color: #fff;
	border-collapse: collapse;
}

.fixedTable tr {
	line-height: 25px;
	border: 1px solid #fff;
}

.fixedTable tr:first-child {
	height: 25px;
	line-height: 25px;
}


.fixedTable td:first-child {
	position: fixed;
	width: 90px;
	height: 30px;
	background-color: #76c3c9;
	color: white;
	border: 1px solid #fff;
	margin: -1px 0px 0px -1px;
}


.fixedColumn {
	width: 90px;
} */

.list-inline>li {
	width: 32%;
	text-align: center;
}

.form-control, .form-group button {
	height: 50px;
	font-size: 15px;
}

.form-group {
	margin-top: 20px;
}
select {
	background-color: white;
	border-radius: 5px;
	border: 1px solid #19b5ee;
	height: 30px;
	font-size: 10px;
}

a {
	color: #5d5d5d;
}

a:hover, a:active, .nav-pills>li.active>a:focus {
	color: #609fa1;
	background-color: white;
}

input {
	width: 25%;
	height: 30px;
	font-size: 10px;
	border: 1px solid #19b5ee;
	border-radius: 5px;
	/* margin: 20px 5% 0 5%; */
	padding: 5px;
}

img {
	height: 20px;
}

.btn {
	height: 30px;
}

html,body {
      padding: 0;
      height:100%;
    } 
td{
    white-space:nowrap;
    overflow:hidden;
    word-break:keep-all;
}
    

</style>
	


<script>
window.onload = function() { 
	console.log("1");
	//$("table tr:nth-child(even)").addClass("even"); //这个是jquery代码 
	/* $(".fixed-table-head-scroll tr").css("background","#2dc3f8")//首列
	$(".fixed-table-main:first thead tr").css("background","#2dc3f8")//首行
	$(".fixed-table-main:last tr:even").css("background","#ebf0f6");//双行 */
}; 

$(function(){
	
	
	console.log("2");
	$("#arrival_pattern").change(function(){
		$("#arrival_position").html(transformType($("#arrival_pattern").val()));
	});
	
	$("#return_pattern").change(function(){
		$("#return_position").html(transformType($("#return_pattern").val()));
	});

});

function transformType(type){
	var defaultOption="<option>请选择</option>";
	var trainOption="<option>请选择</option>	<option value='苏州火车站'>苏州火车站</option><option value='苏州北站'>苏州北站</option>";
	var airOption="<option>请选择</option><option value='无锡硕放机场'>无锡硕放机场</option><option value='上海虹桥机场'>上海虹桥机场</option><option value='上海浦东机场'>上海浦东机场</option>";
	var selfOption="<option>请选择</option><option value='苏州园区站'>苏州园区站</option>";
	
	if(type=="火车"){
		return trainOption;
	}else if(type=="飞机"){
		return airOption;
	}else if(type=="自驾车"){
		return selfOption
	}
	return defaultOption;
}
function preUpdate(tr,userId){
	$("#thisname").text(tr.find("td").eq(0).text()+":");
	$("#inviter_id").val(userId);
	$("#arrival_pattern").val(tr.find("td").eq(4).find("input[type='hidden']").eq(0).val());
	$("#arrival_position").html(transformType($("#arrival_pattern").val())).val(tr.find("td").eq(4).text());
	$("#arrival_time").val(tr.find("td").eq(5).html());
	$("#return_pattern").val(tr.find("td").eq(6).find("input[type='hidden']").eq(0).val());
	$("#return_position").html(transformType($("#return_pattern").val())).val(tr.find("td").eq(6).text());
	$("#return_time").val(tr.find("td").eq(7).html());
	$("#pick_up").val(tr.find("td").eq(8).text());
	
	$("#myModal").modal('toggle');
}

function toUpd(){
	$.ajax({
        url: 'updInfo.do',
        type: 'POST',
        datatype: 'JSON',               
        data: $('#updForm').serialize(),
    })
    .done(function() {
        console.log("success");
        window.location.reload();
    })
    .fail(function() {
        console.log("error");       
    });
	
}
</script>
<style>

    .table-fixed-wrap {
      height: 80%;
      min-height: 250px;
      border:0;
    }

.table-fixed-wrap.is-scroll {
    border-bottom: 0;
}

.fixed-table-body.is-width-scroll {
    box-shadow: 0;
}
  
    
/* tr:nth-child(odd) {
	color: #7f7f7f;
	background-color: #ebf0f6;
}

tr:nth-child(even) {
	background-color: #a3aeca;
}

.fixed-table-main thead tr {
	background-color: #2dc3f8
} */
</style>
</head>
<body>
	<div class="container" style="height: 100%;">
		<div style="margin-top: 10px; margin-bottom: 10px;">
			<form class="form-inline" role="form" action="invitators.do"
				method="post">
				
					<label class="sr-only" for="assignUser">分配人姓名</label> <input
						type="text"  id="assignUser" name="assignUser"
						placeholder="分配人姓名" value="${assignUser }">
				
				
					<label class="sr-only" for="name">受邀人姓名</label> <input type="text"
						 id="name" name="name" placeholder="受邀人姓名"
						value="${name }">
				
				<c:if test="${empty menu or menu==1}">
					
						<select id="pickup" name="pickup" >
							<option value="0">接送选择</option>
							<option value="1"
								<c:if test="${pickup==1 }">selected="seleted"</c:if>>需要接站</option>
							<option value="2"
								<c:if test="${pickup==2 }">selected="seleted"</c:if>>需要送站</option>
						</select>
					
				</c:if>
				<c:if test="${menu==2}">
					
						<select id="lodging" name="lodging" >
							<option value="0">住宿选择</option>
							<option value="1"
								<c:if test="${lodging==1 }">selected="seleted"</c:if>>需要安排</option>
							<option value="2"
								<c:if test="${lodging==2 }">selected="seleted"</c:if>>无需安排</option>
						</select>
					
				</c:if>
				
				<input type="hidden" name="menu" value="${menu }">
				<button type="submit" class="btn btn-default">查询</button>
				
			</form>
		</div>
		<div style="height:85%;">
			

					<table style="width: 300%;height:90%;" id="table" 
						<c:if test="${menu==3}">style="width:100%;"</c:if>>
						<thead>
							<tr>
								<th width="100">姓名</th>
								<th width="100">手机</th>
								<th>分配人</th>

								<c:if test="${empty menu or menu==1}">
									<th>随员</th>
									<th>到达地点</th>
									<th>到达时间</th>
									<th>返回地点</th>
									<th>返回时间</th>
									<th>接送</th>
									<th>到/送</th>
								</c:if>

								<c:if test="${menu==2 }">
									<th>住宿选择</th>
									<th>酒店价位</th>
									<th>酒店选择</th>
									<th>入住日期</th>
									<th>退房日期</th>
									<th>房间标准</th>
									<th>需求数量</th>
								</c:if>

								<c:if test="${menu==3 }">
								</c:if>

								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${users }">
								<tr
									<c:if test="${empty menu or menu==1}">onclick="preUpdate($(this),${user.inviter_id });"</c:if>>
									<td>${user.name }</td>
									<td>${user.phone }</td>
									<td>${user.sign }</td>

									<c:if test="${empty menu or menu==1}">
										<td>${user.attache_num }</td>
										<td><input type="hidden" value="${user.arrival_pattern }" />${user.arrival_position }</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
												value="${user.arrival_time }" /></td>
										<td><input type="hidden" value="${user.return_pattern }" />${user.return_position }</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
												value="${user.return_time }" /></td>
										<td>${user.pick_up }</td>
										<td><c:if test="${user.recived==0 }">未到</c:if> <c:if
												test="${user.recived==1 }">已到</c:if>/ <c:if
												test="${user.returned==0 }">未送</c:if> <c:if
												test="${user.returned==1 }">已送</c:if></td>
										<td><c:if test="${user.recived==0 }">
												<div style="float:left">
												<a class="btn btn-default"
													href="updState.do?userId=${user.inviter_id }&type=1&state=1&assignUser=${assignUser }&name=${name}&pickup=${pickup}&menu=1">已到</a>
													</div>
												</c:if> 
												<c:if test="${user.returned==0 }">
												<div style="float:right"><a class="btn btn-default"
													href="updState.do?userId=${user.inviter_id }&type=2&state=1&assignUser=${assignUser }&name=${name}&pickup=${pickup}&menu=1">已送</a>
													</div>
											</c:if></td>
									</c:if>

									<c:if test="${menu==2 }">
										<td>${user.lodging_select }</td>
										<td>${user.hotel_price }</td>
										<td>${user.hotel_select }</td>
										<td>${user.in_time }</td>
										<td>${user.out_time }</td>
										<td>${user.room_level }</td>
										<td>${user.room_num }</td>
										<td><c:if test="${user.signed==0 }">
												<a class="btn btn-default"
													href="updState.do?userId=${user.inviter_id }&type=3&state=1&assignUser=${assignUser }&name=${name}&lodging=${lodging}&menu=2">已签到</a>
											</c:if></td>
									</c:if>

									<c:if test="${menu==3 }">
										<td><c:if test="${user.dined==0 }">
												<a class="btn btn-default"
													href="updState.do?userId=${user.inviter_id }&type=4&state=1&assignUser=${assignUser }&name=${name}&menu=3">已参加</a>
											</c:if></td>
									</c:if>

								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					
				
			<div id="kkpager" style="height:10%;"><jsp:include page="/common/pagebar.jsp"></jsp:include></div>
		</div>
		
		
		<div class="row">
			
			<ul class="list-unstyled list-inline navbar-fixed-bottom" style="background-color: #eeeeee;height: 40px;font-size: 15px;">
				<li><a <c:if test="${empty menu or menu==1}">style="color:#2dc3f8;"</c:if>
					href="invitators.do?menu=1"><c:if test="${empty menu or menu==1}"><img src="img/jiesong2.png"></c:if><c:if test="${not empty menu and menu!=1}"><img alt="" src="img/jiesong1.png"></c:if><br>接送</a></li>
				<li><a <c:if test="${menu==2}">style="color:#2dc3f8;"</c:if>
					href="invitators.do?menu=2"><c:if test="${menu==2 }"><img alt="" src="img/zhusu2.png"></c:if><c:if test="${menu!=2 }"><img alt="" src="img/zhusu1.png"></c:if><br>住宿</a></li>
				<li><a <c:if test="${menu==3}">style="color:#2dc3f8;"</c:if>
					href="invitators.do?menu=3"><c:if test="${menu==3 }"><img alt="" src="img/huican2.png"></c:if><c:if test="${menu!=3 }"><img alt="" src="img/huican1.png"></c:if><br>会餐</a></li>
			</ul>
		</div>
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="myModalLabel">
							信息修改
						</h4>
					</div>
					<div class="modal-body">
						<form id="updForm" role="form" action="updInfo.do" method="post" class="form-horizontal">
						<input type="hidden" id="inviter_id" name="inviter_id">
						<div class="form-group"><div class="col-xs-5"><label id="thisname" class="control-label"></label></div></div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="arrival_pattern" class="control-label">到达方式</label></div>
							<div class="col-xs-7">
							<select id="arrival_pattern" name="arrival_pattern">
								<option>请选择</option>
								<option value="飞机">飞机</option>
								<option value="火车">火车</option>
								<option value="自驾车">自驾车</option>
							</select>
							<select id="arrival_position" name="arrival_position">
								<option>请选择</option>
								<option value="无锡硕放机场">无锡硕放机场</option>
								<option value="上海虹桥机场">上海虹桥机场</option>
								<option value="上海浦东机场">上海浦东机场</option>
								<option value="苏州火车站">苏州火车站</option>
								<option value="苏州北站">苏州北站</option>
								<option value="苏州园区站">苏州园区站</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="arrival_time" class="control-label">到达时间</label></div>
							<div class="col-xs-7">
							<!-- <input id="arrival_time" name="arrival_time" readonly="readonly" class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" placeholder="到达时间" style="height:50px;margin: 0 20px;background-color: white;color: #888888;"/> -->
							<input id="arrival_time" name="arrival_time" type="text" placeholder="到达时间" style="width:130px;background-color: white;color: #888888;"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="return_pattern" class="control-label">返程方式</label></div>
							<div class="col-xs-7">
							<select id="return_pattern" name="return_pattern">
								<option>请选择</option>
								<option value="飞机">飞机</option>
								<option value="火车">火车</option>
								<option value="自驾车">自驾车</option>
							</select>
							<select id="return_position" name="return_position">
								<option>请选择</option>
								<option value="无锡硕放机场">无锡硕放机场</option>
								<option value="上海虹桥机场">上海虹桥机场</option>
								<option value="上海浦东机场">上海浦东机场</option>
								<option value="苏州火车站">苏州火车站</option>
								<option value="苏州北站">苏州北站</option>
								<option value="苏州园区站">苏州园区站</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="return_time" class="control-label">返程时间</label></div>
							<div class="col-xs-7">
							<input id="return_time" name="return_time" type="text" placeholder="返程时间" style="width:130px;background-color: white;color: #888888;"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="pick_up" class="control-label">是否需要接送站</label></div>
							<div class="col-xs-7">
							<select id="pick_up" name="pick_up">
								<option>请选择</option>
								<option value="需要接站">需要接站</option>
								<option value="需要送站">需要送站</option>
								<option value="都需要">都需要</option>
								<option value="都不需要">都不需要</option>
							</select>
							</div>
						</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" onclick="toUpd();">
							提交更改
						</button>
					</div>
					
				</div>
				
			</div>
		</div>
	</div>

	<!-- table fix -->
	<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jq.js"></script> --%>
	<%-- <script type="text/javascript"
		src="${pageContext.request.contextPath}/js/tablefix.min.js"></script> --%>
			<script>
				$("#table").bootstrapTable({
				
				  fixedColumns: true, 
				  fixedNumber: 1 //固定列数
				 })
	
				var calendardatetime = new lCalendar();
				calendardatetime.init({
					'trigger': '#arrival_time',
					'type': 'datetime'
				});
				var calendartime = new lCalendar();
				calendartime.init({
					'trigger': '#return_time',
					'type': 'datetime'
				});
				
				
				
				console.log("3");
			</script>


</body>

</html>