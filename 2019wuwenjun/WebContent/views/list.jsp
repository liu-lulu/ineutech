<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<meta name="format-detection" content="telephone=no"/>
<link rel="shortcut icon" href="img/icon.png">
<title>嘉宾信息</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/kkpager.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/paging.js"></script>
<script src="${pageContext.request.contextPath}/js/lCalendar.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/lCalendar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/kkpager_blue.css" />
<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css">


<style type="text/css">


td {
	vertical-align: middle;
	width: max-content;
    padding: 0 5px;
    border:1px solid #e5e5e5;
}



.table {
	width: 100%;
	overflow-x: scroll;
	/* background-color: #7c95b5; */
	margin-bottom: 0px;
}

.fixedTable {
	width: max-content;
	
	text-align: center;
	/* color: #fff; */
	border-collapse: collapse;
}

.fixedTable tr {
	line-height: 38px;
	border: 1px solid #fff;
}

.fixedTable tr:first-child {
	height: 38px;
	line-height: 38px;
}


.fixedTable td:first-child {
	position: fixed;
	width: 90px;
	height: 40px;
	/* background-color: #76c3c9;
	color: white; */
	border: 1px solid #e5e5e5;
	margin: -1px 0px 0px -1px;
	overflow: hidden;
}


.fixedColumn {
	width: 88px;
}

tr:nth-child(odd) {
	/* color: #7f7f7f; */
	background-color: white;/* #ebf0f6 */
}

tr:nth-child(even) {
	background-color: whitesmoke;/* #a3aeca */
}

tbody tr:nth-child(odd)> td:first-child{
	background-color: white;/* #ebf0f6 */
}
tbody tr:nth-child(even)> td:first-child{
	background-color: whitesmoke;/* #a3aeca */
}

.fixedTable thead tr td{
	background-color: #2dc3f8;
	color:white;
}

.list-inline>li {
	width: 45%;
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
	/* width: 25%; */
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
body{position:fixed;width:100%;height:100%;overflow: hidden;}
</style>

<script>
$(function(){
	$("#arrival_pattern").change(function(){
		$("#arrival_position").html(transformType($("#arrival_pattern").val()));
	});
	
	$("#return_pattern").change(function(){
		$("#return_position").html(transformType($("#return_pattern").val()));
	});
	
	$('form').bootstrapValidator({

		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			responsible_person_phone : {
				verbose: false, //代表验证按顺序验证。验证成功才会下一个（验证成功才会发最后一个remote远程验证）
				validators : {
                     phone: {
                    	 country: 'CN',
                    	 message: '请输入正确的电话号码'
                	 }
				}
			}
		}
        
		});
		});

function transformType(type){
	var defaultOption="<option value=''>请选择</option>";
	var trainOption="<option value=''>请选择</option><option value='苏州火车站'>苏州火车站</option><option value='苏州北站'>苏州北站</option><option value='苏州园区站'>苏州园区站</option><option value='苏州新区站'>苏州新区站</option><option value='上海虹桥站'>上海虹桥站</option><option value='上海火车站'>上海火车站</option>";
	var airOption="<option value=''>请选择</option><option value='无锡硕放机场'>无锡硕放机场</option><option value='无锡硕放机场T1'>无锡硕放机场T1</option><option value='无锡硕放机场T2'>无锡硕放机场T2</option><option value='上海虹桥机场'>上海虹桥机场</option><option value='虹桥机场T1'>虹桥机场T1</option><option value='虹桥机场T2'>虹桥机场T2</option><option value='上海浦东机场'>上海浦东机场</option>";
	var selfOption="<option value=''>请选择</option><option value='苏州园区站'>苏州园区站</option>";
	
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
	$("#contact").text(tr.find("td").eq(0).find("input[type='hidden']").eq(12).val());
	$("#contact_phone").text(tr.find("td").eq(0).find("input[type='hidden']").eq(13).val());
	$("#responsible_person").val(tr.find("td").eq(14).text());
	$("#responsible_person_phone").val(tr.find("td").eq(14).find("input[type='hidden']").eq(0).val());
	$("#title").text(tr.find("td").eq(0).find("input[type='hidden']").eq(5).val());
	$("#company").text(tr.find("td").eq(0).find("input[type='hidden']").eq(6).val());
	var redcarpet=tr.find("td").eq(0).find("input[type='hidden']").eq(1).val();
	if(redcarpet==null||redcarpet==''||redcarpet=='0'){
		$("#thisredcarpet").css("color","");
		$("#thisredcarpet").text('不走红毯');
	}else{
		$("#thisredcarpet").css("color","firebrick");
		$("#thisredcarpet").text('走红毯');
	}
	var dined_level=tr.find("td").eq(0).find("input[type='hidden']").eq(2).val();
	
	if(dined_level==null||dined_level==''||dined_level=='无会餐'){
		$("#thisdined_level").css("color","");	
		$("#thisdined_level").text('无会餐');
	}else{
		$("#thisdined_level").css("color","dodgerblue");
		$("#thisdined_level").text(dined_level);
	}
	
	
	var speechPosition=tr.find("td").eq(0).find("input[type='hidden']").eq(7).val();
	if(speechPosition==null||speechPosition==''){
		$("#thisspeech").css("color","");
		$("#thisspeech").text('无演讲');
	}else{
		
		$("#thisspeech").css("color","#19b5ee");
		$("#thisspeech").text(speechPosition);
	}
	
	$("#inviter_id").val(userId);
	$("#arrival_pattern").val(tr.find("td").eq(6).find("input[type='hidden']").eq(0).val());
	$("#arrival_position").html(transformType($("#arrival_pattern").val())).val(tr.find("td").eq(6).find("input[type='hidden']").eq(1).val());
	$("#arrival_info").val(tr.find("td").eq(6).find("input[type='hidden']").eq(2).val());
	$("#arrival_time").val(tr.find("td").eq(8).find("input[type='hidden']").eq(0).val());
	$("#return_pattern").val(tr.find("td").eq(10).find("input[type='hidden']").eq(0).val());
	$("#return_position").html(transformType($("#return_pattern").val())).val(tr.find("td").eq(10).find("input[type='hidden']").eq(1).val());
	$("#return_info").val(tr.find("td").eq(10).find("input[type='hidden']").eq(2).val());
	$("#return_time").val(tr.find("td").eq(12).find("input[type='hidden']").eq(0).val());
	$("#pick_up").val(tr.find("td").eq(13).text());
	$("#thislodging_select").text(tr.find("td").eq(0).find("input[type='hidden']").eq(4).val());
	$("#hotel_select").text(tr.find("td").eq(0).find("input[type='hidden']").eq(0).val());
	
	$("#hotel_room").html(tr.find("td").eq(0).find("input[type='hidden']").eq(8).val()+":"+tr.find("td").eq(0).find("input[type='hidden']").eq(9).val()+"-"+tr.find("td").eq(0).find("input[type='hidden']").eq(10).val()+":"+tr.find("td").eq(0).find("input[type='hidden']").eq(11).val());
	$("label[name='comment']").each(function(){
		$(this).text(tr.find("td").eq(0).find("input[type='hidden']").eq(15).val());
	});
	
	$("#myModal").modal('toggle');
}

function preUpdateHotel(tr,userId){
	$("#thishotelname").text(tr.find("td").eq(0).text()+":");
	$("#thistitle").text(tr.find("td").eq(0).find("input[type='hidden']").eq(5).val());
	$("#thiscompany").text(tr.find("td").eq(0).find("input[type='hidden']").eq(6).val());
	var redcarpet=tr.find("td").eq(0).find("input[type='hidden']").eq(1).val();
	if(redcarpet==null||redcarpet==''||redcarpet=='0'){
		$("#redcarpet").css("color","");
		$("#redcarpet").text('不走红毯');
	}else{
		$("#redcarpet").css("color","firebrick");
		$("#redcarpet").text('走红毯');
	}
	
	var dined_level=tr.find("td").eq(0).find("input[type='hidden']").eq(2).val();
	
	if(dined_level==null||dined_level==''||dined_level=='无会餐'){
		$("#dined_level").css("color","");	
		$("#dined_level").text('无会餐');
	}else{
		$("#dined_level").css("color","dodgerblue");
		$("#dined_level").text(dined_level);
	}
	
	
	var speechPosition=tr.find("td").eq(0).find("input[type='hidden']").eq(7).val();
	if(speechPosition==null||speechPosition==''){
		$("#speech").css("color","");
		$("#speech").text('无演讲');
	}else{
		
		$("#speech").css("color","#19b5ee");
		$("#speech").text(speechPosition);
	}

	$("#hotelinviter_id").val(userId);
	$("#lodging_select").val(tr.find("td").eq(7).text()=='有'?'需要安排':'不需要安排');
	$("#newhotel_select").val(tr.find("td").eq(8).text());
	$("#room_level").val(tr.find("td").eq(11).find("input[type='hidden']").eq(0).val());
	$("#room_num").val(tr.find("td").eq(11).find("input[type='hidden']").eq(1).val());
	$("#room_level2").val(tr.find("td").eq(11).find("input[type='hidden']").eq(2).val());
	$("#room_num2").val(tr.find("td").eq(11).find("input[type='hidden']").eq(3).val());
	$("#in_time").val(tr.find("td").eq(9).find("input[type='hidden']").eq(0).val());
	$("#out_time").val(tr.find("td").eq(10).find("input[type='hidden']").eq(0).val());
	$("#sign").val(tr.find("td").eq(0).find("input[type='hidden']").eq(14).val());
	
	var sign=tr.find("td").eq(0).find("input[type='hidden']").eq(14).val();
	
	if(sign==null||sign==''){
		$("#sign").text('不需要');
	}else{
		$("#sign").text(sign);
	}
	
	$("label[name='comment']").each(function(){
		$(this).text(tr.find("td").eq(0).find("input[type='hidden']").eq(15).val());
	});
	
	var property=tr.find("td").eq(6).text();
	
	if(property!=null&&property!=''){
		$("#property").text("("+property+")");
	}else{
		$("#property").text("");
	}
	
	$("#hotelModal").modal('toggle');
}

function toUpd(){
	 $("#updForm").bootstrapValidator('validate');//提交验证
     if ($("#updForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
    		var r=confirm("确定修改嘉宾信息!");
    		if (r==true){
    			$.ajax({
    		        url: 'updInfo.do',
    		        type: 'POST',
    		       /*  datatype: 'JSON',  */              
    		        data: $('#updForm').serialize(),
    		    })
    		    .done(function(data) {
    		    	if(data=='success'){
    		    		 console.log("success");
    		    		 window.location.reload();
    		    	}else{
    		    		<%-- console.log("login");
    		    		window.location.href="<%=basePath%>"; --%>
    		    		alert("嘉宾信息修改失败");
    		    		
    		    	}
    		       
    		    })
    		    .fail(function(xhr) {
    		        console.log("error");
    		        var REDIRECT = xhr.getResponseHeader("REDIRECT");
                    //如果响应头中包含 REDIRECT 则说明是拦截器返回的
                    if (REDIRECT == "REDIRECT"){
                    	alert('登录已过期，请重新登录哟！');
                    	window.location.href = xhr.getResponseHeader("CONTENTPATH");
                    }
    		    });
    		}else{
    		}
     }
	 

}

function toUpdHotel(){
	var r=confirm("确定修改酒店信息!");
	if (r==true){
		$.ajax({
	        url: 'updHotelInfo.do',
	        type: 'POST',
	        datatype: 'JSON',               
	        data: $('#updHotelForm').serialize(),
	    })
	    .done(function(data) {
	    	if(data=='success'){
	    		 console.log("success");
	    		 window.location.reload();
	    	}else{
	    		<%-- console.log("login");
	    		window.location.href="<%=basePath%>"; --%>
	    		alert('酒店信息修改失败!');
	    	}
	    })
	    .fail(function(xhr) {
	        console.log("error");
	        var REDIRECT = xhr.getResponseHeader("REDIRECT");
            //如果响应头中包含 REDIRECT 则说明是拦截器返回的
            if (REDIRECT == "REDIRECT"){
            	alert('登录已过期，请重新登录哟！');
            	window.location.href = xhr.getResponseHeader("CONTENTPATH");
            }
	    });
	}
	else{
	}
}

function toUpdState(url,event){
	event.stopPropagation();
	var r=confirm("确定该操作!");
	if (r==true){
		window.location.href=url;
	}
	/* else{
		window.event.cancelBubble=true;
	} */
}

//重写。避免微信弹窗带域名
window.alert = function(name){
 	  var iframe = document.createElement("IFRAME");
 	  iframe.style.display="none";
 	  iframe.setAttribute("src", 'data:text/plain,');
 	  document.documentElement.appendChild(iframe);
 	  window.frames[0].window.alert(name);
 	  iframe.parentNode.removeChild(iframe);
 	 };

 	window.confirm = function (message) {
 	   var iframe = document.createElement("IFRAME");
 	   iframe.style.display = "none";
 	   iframe.setAttribute("src", 'data:text/plain,');
 	   document.documentElement.appendChild(iframe);
 	   var alertFrame = window.frames[0];
 	   var result = alertFrame.window.confirm(message);
 	   iframe.parentNode.removeChild(iframe);
 	   return result;
 	 }; 

</script>
</head>
<body>
	<div class="container" style="width: 95%;">
		<div class="row" style="margin-top: 10px; margin-bottom: 10px;position: absolute;">
			<a href="#" data-toggle="modal" data-target="#filterModal"><span class="glyphicon glyphicon-filter" style="color:#19b5ee;font-size: large;"></span></a>
			<label><c:if test="${manager.group!=null }">${manager.group.group_name }_</c:if>${manager.user_name }</label>
		</div>
		<div class="modal fade" id="filterModal">
			<div class="modal-dialog">
				
				<div class="modal-content">
					<form class="form-horizontal" role="form" action="invitators.do" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="myModalLabel">
							筛选
						</h4>
					</div>
					<div class="modal-body">
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="name" class="control-label">受邀人</label></div>
							<div class="col-xs-7"><input type="text"
								 id="name" name="name" placeholder="受邀人姓名"
								value="${name }">
							</div>
							</div>
							
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="assignUser" class="control-label">对接人</label></div>
							<div class="col-xs-7"><input type="text"
								 id="assignUser" name="assignUser" placeholder="对接人姓名"
								value="${assignUser }">
							</div>
							</div>
							
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="position" class="control-label">车站</label></div>
							<div class="col-xs-7">
							<select id="position" name="position">
								<option value=''>请选择</option>
								<option value="无锡硕放机场T2" <c:if test="${position=='无锡硕放机场T2' }">selected="seleted"</c:if>>无锡硕放机场T2</option>
								<option value="虹桥机场T2" <c:if test="${position=='虹桥机场T2' }">selected="seleted"</c:if>>虹桥机场T2</option>
								<option value="上海浦东机场" <c:if test="${position=='上海浦东机场' }">selected="seleted"</c:if>>上海浦东机场</option>
								<option value="苏州火车站" <c:if test="${position=='苏州火车站' }">selected="seleted"</c:if>>苏州火车站</option>
								<option value="苏州北站" <c:if test="${position=='苏州北站' }">selected="seleted"</c:if>>苏州北站</option>
								<option value="苏州园区站" <c:if test="${position=='苏州园区站' }">selected="seleted"</c:if>>苏州园区站</option>
							</select>
							</div>
							</div>
							
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="info" class="control-label">车次</label></div>
							<div class="col-xs-7"><input type="text"
								 id="info" name="info" placeholder="车次"
								value="${info }">
							</div>
							</div>
							
							<%-- <c:if test="${empty menu or menu==1}"> --%>
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="pickup" class="control-label">接送选择</label></div>
							<div class="col-xs-7">
							<select id="pickup" name="pickup" style="">
									<option value="0">接送选择</option>
									<option value="1"
										<c:if test="${pickup==1 }">selected="seleted"</c:if>>需要接站</option>
									<option value="2"
										<c:if test="${pickup==2 }">selected="seleted"</c:if>>需要送站</option>
									<option value="3"
										<c:if test="${pickup==3 }">selected="seleted"</c:if>>都需要</option>
									<option value="4"
										<c:if test="${pickup==4 }">selected="seleted"</c:if>>都不需要</option>
								</select>
							</div>
							</div>
							<%-- </c:if> --%>
							
							<%-- <c:if test="${menu==2}"> --%>
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="lodging" class="control-label">住宿选择</label></div>
							<div class="col-xs-7">
							<select id="lodging" name="lodging" style="">
									<option value="0">住宿选择</option>
									<option value="1"
										<c:if test="${lodging==1 }">selected="seleted"</c:if>>需要安排</option>
									<option value="2"
										<c:if test="${lodging==2 }">selected="seleted"</c:if>>无需安排</option>
								</select>
							</div>
							</div>
							<%-- </c:if> --%>
							
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="recived" class="control-label">状态</label></div>
							<div class="col-xs-7">
							<select id="userState" name="userState" style="">
									<option value="0">状态</option>
									<option value="1"
										<c:if test="${userState==1 }">selected="seleted"</c:if>>已启程</option>
									<option value="2"
										<c:if test="${userState==2 }">selected="seleted"</c:if>>已接站</option>
									<option value="3"
										<c:if test="${userState==3 }">selected="seleted"</c:if>>已签到</option>
									<option value="4"
										<c:if test="${userState==4 }">selected="seleted"</c:if>>已返程</option>
							</select>
							</div>
							</div>
							
<%-- 							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="recived" class="control-label">到达状态</label></div>
							<div class="col-xs-7">
							<select id="recived" name="recived" style="">
									<option value="0">到达状态</option>
									<option value="1"
										<c:if test="${recived==1 }">selected="seleted"</c:if>>已接站</option>
									<option value="2"
										<c:if test="${recived==2 }">selected="seleted"</c:if>>已启程</option>
							</select>
							</div>
							</div>
							
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="recived" class="control-label">返程状态</label></div>
							<div class="col-xs-7">
							<select id="returned" name="returned" style="">
									<option value="0">返程状态</option>
									<option value="1"
										<c:if test="${returned==1 }">selected="seleted"</c:if>>已返程</option>
									<option value="2"
										<c:if test="${returned==2 }">selected="seleted"</c:if>>未送</option>
							</select>
							</div>
							</div>
							
							<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="recived" class="control-label">签到状态</label></div>
							<div class="col-xs-7">
							<select id="signed" name="signed" style="">
									<option value="0">签到状态</option>
									<option value="1"
										<c:if test="${signed==1 }">selected="seleted"</c:if>>已签到</option>
									<option value="2"
										<c:if test="${signed==2 }">selected="seleted"</c:if>>未签到</option>
							</select>
							</div>
							</div> --%>

						<input type="hidden" name="menu" value="${menu }">
					</div>
					<div class="modal-footer">
								<!-- <button type="reset" name="reset" class="btn btn-default" style="float:left;">清空
								</button> -->
								
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭
								</button>
								<button type="submit" class="btn btn-primary" >
									确定
								</button>
					</div>
					</form>
			</div>
			
			</div>
			
		</div>
		<div class="row" style="position: absolute; top: 40px; bottom: 40px;">
			<div style="position: absolute; width: 100%; height: auto; overflow: auto;">
				<div class="table" style="position: fixed;">

					<table class="fixedTable">
						<thead>
							<tr>
								<td>姓名</td>
								<td class="fixedColumn"></td>
								<td>性别</td>
								<td>手机</td>
								<td>随员</td>
								<td>状态</td>

								<c:if test="${(manager.group.type==1&&(empty menu || menu==1)) || manager.group.type==2}">
									
									<td>到达地点</td>
									<td>到达班次</td>
									<td>到达时间</td>
									<td>签到酒店</td>
									<td style="background-color: dodgerblue;">返回地点</td>
									<td style="background-color: dodgerblue;">返回班次</td>
									<td style="background-color: dodgerblue;">返回时间</td>
									<td>接送</td>
									<td>对接人</td>
								</c:if>

								<c:if test="${(manager.group.type==1&&(menu==2)) || manager.group.type==3}">
									<td>类别</td>
									<td>住宿</td>
									<td>酒店</td>
									<td>入住</td>
									<td>退房</td>
									<td>房间信息</td>
									
								</c:if>

								<c:if test="${menu==3 }">
								</c:if>

								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${users }">
								<tr	<c:if test="${user.level=='一级'}"> style="background-color:#cce2ff;"</c:if>
									<c:if test="${manager.group.type==2||(manager.group.type==1&&(empty menu || menu==1))}">onclick="preUpdate($(this),${user.inviter_id });"</c:if>
									<c:if test="${manager.group.type==3||(manager.group.type==1&&(menu==2))}">onclick="preUpdateHotel($(this),${user.inviter_id });"</c:if>>
									
									<td <c:if test="${user.level=='一级'}"> style="background-color:#cce2ff;"</c:if>>
									${user.name }<c:if test="${user.level=='特级'}"> (VIP)</c:if>
									<input type="hidden" value="${user.hotel_select }" />
									<input type="hidden" value="${user.redcarpet }" />
									<input type="hidden" value="${user.dined_level }" />
									<input type="hidden" value="${user.speech }" />
									<input type="hidden" value="${user.lodging_select}" />
									<input type="hidden" value="${user.title}" />
									<input type="hidden" value="${user.company}" />
									<input type="hidden" value="${user.speech_position}" />
									<input type="hidden" value="${user.room_level }"><input type="hidden" value="${user.room_num }"><input type="hidden" value="${user.room_level2 }"><input type="hidden" value="${user.room_num2 }">
									<input type="hidden" value="${user.contact}" />
									<input type="hidden" value="${user.contact_phone}" />
									<input type="hidden" value="${user.sign}" />
									<input type="hidden" value="${user.comment}" />
									<input type="hidden" value="${user.property}" />
									</td>
									<td class="fixedColumn"></td>
									<td>${user.sex }</td>
									<td>${user.phone }</td>
									<td>${user.attache_num }</td>
									
									<td>
									<c:if test="${user.returned==1 }">已返程</c:if>
									<c:if test="${user.returned!=1 && user.signed==1 }">已签到</c:if>
									<c:if test="${user.signed!=1 && user.returned!=1 && user.recived==1}">已接站</c:if>
									<c:if test="${user.signed!=1 && user.returned!=1 && user.recived!=1 && user.started==1}">已启程</c:if>
									<c:if test="${user.signed!=1 && user.returned!=1 && user.recived!=1 && user.started!=1&&user.confirm==1}">已确认</c:if>
									<c:if test="${user.signed!=1 && user.returned!=1 && user.recived!=1 && user.started!=1&&user.confirm!=1}">--</c:if>
									</td>

									<c:if test="${(manager.group.type==1&&(empty menu || menu==1)) || manager.group.type==2}">
										<td>
										<input type="hidden" value="${user.arrival_pattern }" />
										<input type="hidden" value="${user.arrival_position }" />
										<input type="hidden" value="${user.arrival_info }" />
										${user.arrival_position }
										</td>
										<td>${user.arrival_info}</td>
										<td><input type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
												value="${user.arrival_time }" />" />
										<fmt:formatDate pattern="MM-dd HH:mm"
												value="${user.arrival_time }" />
										</td>
										<td>${user.hotel_select }</td>
										<td>
										<input type="hidden" value="${user.return_pattern }" />
										<input type="hidden" value="${user.return_position }" />
										<input type="hidden" value="${user.return_info }" />
										${user.return_position }
										</td>
										<td>${user.return_info }</td>
										<td>
										<input type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
												value="${user.return_time }" />" />
										<fmt:formatDate pattern="MM-dd HH:mm"
												value="${user.return_time }" /></td>
										<td>${user.pick_up }</td>
										<td>${user.responsible_person }<input type="hidden" value="${user.responsible_person_phone }" /></td>
										<td>
											<c:if test="${user.confirm!=1}">
												<div style="float:left">
													<a class="btn btn-default" onclick="toUpdState('updState.do?userId=${user.inviter_id }&type=8&state=1&name=${name}&assignUser=${assignUser }&position=${position}&info=${info}&pickup=${pickup}&lodging=${lodging}&userState=${userState}&recived=${recived}&returned=${returned }&signed=${signed }&menu=1&currentPageNO=${currentPageNO }',event);"
													href="#">确认</a>
												</div>
											</c:if>
											<c:if test="${user.started!=1&&user.confirm==1}">
												<div style="float:left">
													<a class="btn btn-default" onclick="toUpdState('updState.do?userId=${user.inviter_id }&type=5&state=1&name=${name}&assignUser=${assignUser }&position=${position}&info=${info}&pickup=${pickup}&lodging=${lodging}&userState=${userState}&recived=${recived}&returned=${returned }&signed=${signed }&menu=1&currentPageNO=${currentPageNO }',event);"
													href="#">启程</a>
												</div>
											</c:if>
											
											<c:if test="${user.recived==0 && user.started==1}">
												<div style="float:left">
													<a class="btn btn-default" onclick="toUpdState('updState.do?userId=${user.inviter_id }&type=1&state=1&name=${name}&assignUser=${assignUser }&position=${position}&info=${info}&pickup=${pickup}&lodging=${lodging}&userState=${userState}&recived=${recived}&returned=${returned }&signed=${signed }&menu=1&currentPageNO=${currentPageNO }',event);"
													href="#">到达</a>
													
												</div>
											</c:if> 
											<c:if test="${user.returned==0 && user.recived==1}">
												<div style="float:right">
													<a class="btn btn-default" onclick="toUpdState('updState.do?userId=${user.inviter_id }&type=2&state=1&name=${name}&assignUser=${assignUser }&position=${position}&info=${info}&pickup=${pickup}&lodging=${lodging}&userState=${userState}&recived=${recived}&returned=${returned }&signed=${signed }&menu=1&currentPageNO=${currentPageNO }',event);"
													href="#">送达</a>
													</div>
											</c:if>
										</td>
									</c:if>

									<c:if test="${(manager.group.type==1&&(menu==2)) || manager.group.type==3}">
										<td>${user.property}</td>
										<td><c:if test="${user.lodging_select=='需要安排'}">有</c:if><c:if test="${user.lodging_select!='需要安排'}">无</c:if></td>
										<td>${user.hotel_select }</td>
										<td>
										<input type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd"
												value="${user.in_time }" />">
										<fmt:formatDate pattern="MM-dd"
												value="${user.in_time }" /></td>
										<td>
										<input type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd"
												value="${user.out_time }" />">
										<fmt:formatDate pattern="MM-dd"
												value="${user.out_time }" /></td>
										<td><input type="hidden" value="${user.room_level }"><input type="hidden" value="${user.room_num }"><input type="hidden" value="${user.room_level2 }"><input type="hidden" value="${user.room_num2 }">
										${user.room_level }:${user.room_num }-${user.room_level2 }:${user.room_num2 }
										</td>
										
										<td>
											<c:if test="${user.signed==0 }">
													<a class="btn btn-default" onclick="toUpdState('updState.do?userId=${user.inviter_id }&type=3&state=1&name=${name}&assignUser=${assignUser }&position=${position}&info=${info}&pickup=${pickup}&lodging=${lodging}&userState=${userState}&recived=${recived}&returned=${returned }&signed=${signed }&menu=2&currentPageNO=${currentPageNO }',event);"
													href="#">签到</a>
											</c:if>
										</td>
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
					

				</div>
				
			</div>
			<div id="kkpager" style="position: fixed;bottom: inherit;"><jsp:include page="/common/pagebar.jsp"></jsp:include></div>
		</div>
		
		
		<c:if test="${manager.group.type==1}">
		<div class="row">
			<ul class="list-unstyled list-inline navbar-fixed-bottom" style="background-color: #eeeeee;height: 40px;font-size: 15px;">
				<li><a <c:if test="${empty menu or menu==1}">style="color:#2dc3f8;"</c:if>
					href="invitators.do?menu=1&name=${name}&assignUser=${assignUser }&position=${position}&info=${info}&pickup=${pickup}&lodging=${lodging}&userState=${userState}&recived=${recived}&returned=${returned }&signed=${signed }"><c:if test="${empty menu or menu==1}"><img src="img/jiesong2.png"></c:if><c:if test="${not empty menu and menu!=1}"><img alt="" src="img/jiesong1.png"></c:if><br>接送</a></li>
				<li><a <c:if test="${menu==2}">style="color:#2dc3f8;"</c:if>
					href="invitators.do?menu=2&name=${name}&assignUser=${assignUser }&position=${position}&info=${info}&pickup=${pickup}&lodging=${lodging}&userState=${userState}&recived=${recived}&returned=${returned }&signed=${signed }"><c:if test="${menu==2 }"><img alt="" src="img/zhusu2.png"></c:if><c:if test="${menu!=2 }"><img alt="" src="img/zhusu1.png"></c:if><br>住宿</a></li>
				
			</ul>
		</div>
		</c:if>
		
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
						<div class="form-group">
							<div class="col-xs-5">
							<label id="thisname" class="control-label" style="word-wrap: break-word;"></label><br>
							
							</div>
							<div class="col-xs-7">
							<label id="company" class="control-label"></label>&nbsp;：&nbsp;<label id="title" class="control-label"></label><br>
							<!-- <label id="thisredcarpet" class="control-label"></label><br> -->
							<label id="thisspeech" class="control-label"></label><br>
							<label id="thisdined_level" class="control-label"></label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;">
							<label for="contact"  class="control-label">联系人</label><br>
							</div>
							<div class="col-xs-7">
							<label id="contact" class="control-label"></label>&nbsp;&nbsp;
							<label id="contact_phone" class="control-label"></label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="arrival_pattern" class="control-label">到达方式</label></div>
							<div class="col-xs-7">
							<select id="arrival_pattern" name="arrival_pattern">
								<option value=''>请选择</option>
								<option value="飞机">飞机</option>
								<option value="火车">火车</option>
								<option value="自驾车">自驾车</option>
								<option value="自行安排">自行安排</option>
							</select>
							<select id="arrival_position" name="arrival_position">
								<option value=''>请选择</option>
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
							<input id="arrival_time" name="arrival_time" type="text" readonly="readonly" placeholder="到达时间" style="width:130px;background-color: white;color: #888888;"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="arrival_time" class="control-label">到达航班号/车次</label></div>
							<div class="col-xs-7">
							<input id="arrival_info" name="arrival_info" type="text" placeholder="到达航班号/车次" style=""/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="return_pattern" class="control-label">返程方式</label></div>
							<div class="col-xs-7">
							<select id="return_pattern" name="return_pattern">
								<option value=''>请选择</option>
								<option value="飞机">飞机</option>
								<option value="火车">火车</option>
								<option value="自驾车">自驾车</option>
								<option value="自行安排">自行安排</option>
							</select>
							<select id="return_position" name="return_position">
								<option value=''>请选择</option>
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
							<input id="return_time" name="return_time" type="text" readonly="readonly" placeholder="返程时间" style="width:130px;background-color: white;color: #888888;"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="return_time" class="control-label">返程航班号/车次</label></div>
							<div class="col-xs-7">
							<input id="return_info" name="return_info" type="text" placeholder="返程航班号/车次" style=""/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="pick_up" class="control-label">是否需要接送站</label></div>
							<div class="col-xs-7">
							<select id="pick_up" name="pick_up">
								<option value=''>请选择</option>
								<option value="需要接站">需要接站</option>
								<option value="需要送站">需要送站</option>
								<option value="都需要">都需要</option>
								<option value="都不需要">都不需要</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="responsible_person" class="control-label">对接人</label></div>
							<div class="col-xs-7">
							<input id="responsible_person" name="responsible_person" type="text" placeholder="对接人" style=""/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="responsible_person_phone" class="control-label">对接人电话</label></div>
							<div class="col-xs-7">
							<input id="responsible_person_phone" name="responsible_person_phone" type="text" placeholder="对接人电话" style=""/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="hotel_select" class="control-label">酒店</label></div>
							<div class="col-xs-7">
							<label id="thislodging_select" class="control-label"></label><br>
							<label id="hotel_select" class="control-label"></label>
							<label id="hotel_room" class="control-label"></label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="comment" class="control-label">备注</label></div>
							<div class="col-xs-7">
							<label name="comment" class="control-label"></label>
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
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="hotelModal" tabindex="-1" role="dialog"  aria-hidden="true">
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
						<form id="updHotelForm" role="form" action="updHotelInfo.do" method="post" class="form-horizontal">
						<input type="hidden" id="hotelinviter_id" name="hotelinviter_id">
						<div class="form-group">
						<div class="col-xs-5">
						<label id="thishotelname" class="control-label" style="word-wrap: break-word;"></label><br>
						<label id="property" class="control-label"></label>
						</div>
						<div class="col-xs-7">
						<label id="thiscompany" class="control-label"></label>&nbsp;：&nbsp;<label id="thistitle" class="control-label"></label><br>
						<!-- <label id="redcarpet" class="control-label"></label><br> -->
						<label id="speech" class="control-label"></label><br>
						<label id="dined_level" class="control-label"></label>
						</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="sign" class="control-label">签字</label></div>
							<div class="col-xs-7">
							<label id="sign" name="sign" class="control-label"></label>							
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="lodging_select" class="control-label">住宿选择</label></div>
							<div class="col-xs-7">
							<select id="lodging_select" name="lodging_select" onchange="lodgingChange()">
								<option value="">请选择</option>
								<option value="需要安排">需要安排</option>
								<option value="不需要安排">不需要安排</option>
							</select>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="room_level" class="control-label">需求数量</label></div>
							<div class="col-xs-7">
							<select id="room_level" name="room_level">
								<option value="">请选择</option>
								<option value="大床房">大床房</option>
								<option value="高级大床房">高级大床房</option>
								<option value="关爱大床房">关爱大床房</option>
								<option value="行政大床房">行政大床房</option>
								<option value="豪华大床房">豪华大床房</option>
							</select>
							<select id="room_num" name="room_num">
								<option value="">请选择</option>
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="room_level2" class="control-label">需求数量</label></div>
							<div class="col-xs-7">
							<select id="room_level2" name="room_level2">
								<option value="">请选择</option>
								<option value="双人间">双人间</option>
								<option value="高级双人间">高级双人间</option>
								<option value="行政双人间">行政双人间</option>
							</select>
							<select id="room_num2" name="room_num2">
								<option value="">请选择</option>
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
							</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="in_time" class="control-label">入住时间</label></div>
							<div class="col-xs-7">
							<input id="in_time" name="in_time" type="text" readonly="readonly" placeholder="入住时间" style="width:130px;background-color: white;color: #888888;"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="out_time" class="control-label">退房时间</label></div>
							<div class="col-xs-7">
							<input id="out_time" name="out_time" type="text" readonly="readonly" placeholder="退房时间" style="width:130px;background-color: white;color: #888888;"/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="newhotel_select" class="control-label">酒店</label></div>
							<div class="col-xs-7">
							<input id="newhotel_select" name="newhotel_select"  class="control-label" type="text" placeholder="酒店名称">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-5" style="text-align: right;"><label for="comment" class="control-label">备注</label></div>
							<div class="col-xs-7">
							<label name="comment" class="control-label"></label>
							</div>
						</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" onclick="toUpdHotel();">
							提交更改
						</button>
					</div>
					
				</div>
				
			</div>
		</div>
	</div>
		<script>

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
			
			var calendardatetime = new lCalendar();
			calendardatetime.init({
				'trigger': '#in_time',
				'type': 'date'
			});
			var calendartime = new lCalendar();
			calendartime.init({
				'trigger': '#out_time',
				'type': 'date'
			});
		</script>
</body>
</html>