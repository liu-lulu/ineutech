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
<title>班车报名</title>
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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/build.css" />

<style type="text/css">

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

input {
	/* width: 25%; */
	height: 30px;
	font-size: 10px;
	border: 1px solid #19b5ee;
	border-radius: 5px;
	/* margin: 20px 5% 0 5%; */
	padding: 5px;
}



body{position:fixed;width:100%;height:100%;overflow: hidden;}


</style>

<script>
$(function() {
	$('form').bootstrapValidator({

		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : '姓名不能为空'
					},
					stringLength: {
						max: 50,
						message: '姓名长度不能超过50位'
					}
				}
			},
			phone : {
				verbose: false, //代表验证按顺序验证。验证成功才会下一个（验证成功才会发最后一个remote远程验证）
				validators : {
					notEmpty : {
						message : '手机号码不能为空'
					},
					
                     phone: {
                    	 country: 'CN',
                    	 message: '请输入正确的手机号码'
                	 }
                    
				}
			}

		}
        ,submitHandler: function (validator, form, submitButton) {
        		$('form').submit();;
        }
	});
}).on('success.form.bv', function(e) {//验证通过后会执行这个函数。
	e.preventDefault();
	var r=confirm("确定提交班车信息!");
	if (r==true){
		$.post($('form').attr('action'), $('form').serialize(), function(result) {
			if(result=='success'){
				alert("报名成功");
    			location.href="bus.jsp";
			}else{
				alert("报名失败");
				$(':submit').removeAttr("disabled");
			}
			});
	}
	else{
		$(':submit').removeAttr("disabled");
	}
	
	
});

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
		<div class="row" style="text-align: center;padding-top: 15px;"><h4 style="font-weight: bold;">2019吴文俊科学技术奖颁奖礼<br>返程班车报名</h4></div>
		<div class="row" >
			<form class="form-horizontal" id="busForm" role="form" action="bus.do" method="post" class="form-horizontal">
			<div class="form-group">
			<div class="col-xs-4" style="text-align: right;"><label for="name" class="control-label">姓名<span style="color:red;">*</span></label></div>
			<div class="col-xs-8">
			<input id="name" name="name"  class="control-label" type="text" placeholder="姓名">
			</div>
			</div>
			<div class="form-group">
				<div class="col-xs-4" style="text-align: right;"><label for="phone" class="control-label">手机号码<span style="color:red;">*</span></label></div>
				<div class="col-xs-8">
				<input id="phone" name="phone"  class="control-label" type="text" placeholder="手机号码">						
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-4" style="text-align: right;"><label for="start_pos" class="control-label">发车地点<span style="color:red;">*</span></label></div>
				<div class="col-xs-8"><label name="start_pos" class="control-label">希尔顿酒店</label></div>
			</div>
			
			<div class="form-group">
				<div class="col-xs-4" style="text-align: right;"><label for="start_time" class="control-label">发车时间<span style="color:red;">*</span></label></div>
				<div class="col-xs-8">
				<select id="start_time" name="start_time">
					<option value="2019/12/1 12:00">2019/12/1 12:00</option>
					<option value="2019/12/1 12:15">2019/12/1 12:15</option>
					<option value="2019/12/1 12:30">2019/12/1 12:30</option>
					<option value="2019/12/1 12:45">2019/12/1 12:45</option>
					<option value="2019/12/1 13:00">2019/12/1 13:00</option>
					<option value="2019/12/1 13:15">2019/12/1 13:15</option>
					<option value="2019/12/1 13:30">2019/12/1 13:30</option>
					<option value="2019/12/1 13:45">2019/12/1 13:45</option>
					<option value="2019/12/1 14:00">2019/12/1 14:00</option>
					<option value="2019/12/1 14:15">2019/12/1 14:15</option>
					<option value="2019/12/1 14:30">2019/12/1 14:30</option>
					<option value="2019/12/1 14:45">2019/12/1 14:45</option>
					<option value="2019/12/1 15:00">2019/12/1 15:00</option>
					<option value="2019/12/1 15:15">2019/12/1 15:15</option>
					<option value="2019/12/1 15:30">2019/12/1 15:30</option>
					<option value="2019/12/1 15:45">2019/12/1 15:45</option>
					<option value="2019/12/1 16:00">2019/12/1 16:00</option>
					<option value="2019/12/1 16:15">2019/12/1 16:15</option>
					<option value="2019/12/1 16:30">2019/12/1 16:30</option>
					<option value="2019/12/1 16:45">2019/12/1 16:45</option>
					<option value="2019/12/1 17:00">2019/12/1 17:00</option>
					<option value="2019/12/1 17:15">2019/12/1 17:15</option>
					<option value="2019/12/1 17:30">2019/12/1 17:30</option>
					<option value="2019/12/1 17:45">2019/12/1 17:45</option>
					<option value="2019/12/1 18:00">2019/12/1 18:00</option>
					
				</select>
				</div>
			</div>

			<div class="form-group">
				<div class="col-xs-4" style="text-align: right;"><label for="end_pos" class="control-label" >目的地<span style="color:red;">*</span></label></div>
				<div class="col-xs-8">
				<div class="radio radio-info">
					 <input type="radio" name="end_pos" id="end_pos1"
				         value="苏州园区站" checked>
				   <label> 苏州园区站（预计30min到达）
				   </label>
				</div>
				<div class="radio radio-info">
					<input type="radio" name="end_pos" id="end_pos2" 
				         value="苏州站" >
				   <label>苏州站（预计50min到达）</label>
				</div>
				<div class="radio radio-info">
					 <input type="radio" name="end_pos" id="end_pos3" 
				         value="苏州北站" >
				   <label>苏州北站（预计50min到达）</label>
				</div>
				<div class="radio radio-info">
					<input type="radio" name="end_pos" id="end_pos4"  value="无锡硕放机场" >
				   <label>无锡硕放机场（预计2h到达）</label>
				</div>
				</div>
			</div>
			
			<div class="form-group">
				    <div class="col-xs-offset-4 col-xs-8">
				      <button type="submit" class="btn btn-primary" style="margin-top:10px;width: 90px;">报名</button>
				    </div>
			    </div>
			
			</form>
		</div>
		
			

	</div>
</body>
</html>