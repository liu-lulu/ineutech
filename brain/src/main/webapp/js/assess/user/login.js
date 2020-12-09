$(function(evts){
			$("input[name='username']").focus();
			$('#loginform').validate({
		            rules: {
		                username: {
		                    required: true,
		                    remote: {
		                    	url: "../user/searchByUserName.htm",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	username: function() {
		                    	return $("#username").val();
		                    	}
		                    	}
		                    	}
		                },
		                password: {
		                    required: true,
		                    remote: {
		                    	url: "../user/checkUser.htm",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	username: function() {
		                    	return $("#username").val();
		                    	}
		                    	}
		                    	}
		                },
		                remember: {
		                    required: false
		                }
		            },

		            messages: {
		                username: {
		                    required: "用户名必填.",
		                    remote:"用户名不存在."
		                },
		                password: {
		                    required: "密码必填 .",
		                    remote:"用户名或密码错误。"
		                }
		            },

		            submitHandler: function (form) {
		            	form.submit();
		            	//window.location.href="../user/loginByUserName.htm?username="+$("#username").val()+"&password="+$("#password").val()+"&remember="+$("#rm").val();
		            }
		        });

		        $('#loginform input').keypress(function (e) {
		            if (e.which == 13) {
		                if ($('#loginform').validate().form()) {
		                	$('#loginform').submit();
		                }
		                return false;
		            }
		        });
		
		
   });