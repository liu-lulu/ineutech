<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no">
<link rel="shortcut icon" href="img/icon.jpg">
<title>登录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css">
<style type="text/css">
html,body{
	width:100%;
	height:100%
}
.form-group .form-item input{
	border-bottom: 1px solid white;
	border-top: 0;
	border-left: 0;
	border-right: 0;
}
.form-group .form-item{
	background-color: transparent;
}

.button-group .login-btn {
    color: #39406c;
}

.form-group {
	margin-bottom: 0;
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
</style>
<script type="text/javascript">

window.onload =function()
{
	if(!isPC()){
		$(".login-bg").css("background","url('images/login-bg2.jpg') no-repeat fixed").css("background-size","100% 100%");
	}
}

function isPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

$(function() {
	$('form').bootstrapValidator({

		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			newPwd : {
				validators : {
					notEmpty : {
						message : '新密码不能为空'
					},
		            identical: {
		                field: 'confirmPwd',
		                message: '新密码与确认密码不一致！'
		              },
		              stringLength: {
		            	min: 6,
		                max: 10,
		                message: '新密码长度6到10位'
		              }
				}
			},
			confirmPwd : {
				validators : {
					notEmpty : {
						message : '确认密码不能为空'
					},
		            identical: {
		                field: 'newPwd',
		                message: '新密码与确认密码不一致'
		              },
		              stringLength: {
		            	min: 6,
		                max: 10,
		                message: '确认密码长度6到10位'
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
	var r=confirm("确定修改密码!");
	if (r==true){
		$.post($('form').attr('action'), $('form').serialize(), function(result) {
			if(result=='success'){
				alert("修改成功");
    			location.href="kpi.do";
			}else{
				alert("修改失败");
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
<body class="login-bg" >
	<div class="login-contain" style="width:100%;">
    <!-- <div class="login-header">
        <p>登录</p>
    </div> -->
    <div class="container">  
    <div class="form"> 
    <form class="form-horizontal col-xs-offset-1 col-sm-offset-2 col-md-offset-3" id="loginform" action="updPwd.do" onsubmit="return check();" method="post">
    <div class="col-xs-11 col-sm-10 col-md-9" style="background: rgb(0,87,122,0.2);">
    <div class="form-group" style="text-align: center;">
	    <label for="name" class="col-xs-12 control-label" style="font-size: large;text-align: center;">修改密码</label>
	</div>
	<div class="form-group">
	    <label for="newPwd" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">新密码<span style="color:red;">*</span></label>
	    <div class="col-xs-8">
	    <input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="请输入新密码">
	    </div>
	</div>
	<div class="form-group">
	    <label for="confirmPwd" class="col-sm-3 col-xs-4 control-label" style="padding: 6px 12px;">确认密码<span style="color:red;">*</span></label>
	    <div class="col-xs-8">
	    <input type="password" class="form-control" id="confirmPwd" name="confirmPwd" placeholder="请重新输入密码">
	    </div>
	</div>
    <div class="form-group">
	    <div class="col-sm-offset-3 col-xs-offset-4 col-xs-8">
	      <button type="submit" class="btn btn-primary" style="width: 90px;">修改</button>
	    </div>
   </div>
    </div>
	</form>
	</div>
	</div>
	
	</div>
<%-- <script src="${pageContext.request.contextPath}/js/jquery.js"></script> --%>
<script>
     $(function(){
        $(window).resize();
    });
    //js设置居中
    $(window).resize(function(){
        $(".login-contain").css({
            position: "absolute",
           //left: ($(window).width() - $(".login-contain").outerWidth())/2,
            top: ($(window).height() - $(".login-contain").outerHeight())/2
        });
    }); 
    
    

</script>
</body>
</html>