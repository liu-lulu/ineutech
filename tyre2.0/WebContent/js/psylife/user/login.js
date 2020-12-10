$(function(evts){
			$("input[name='loginName']").focus();
			$('#loginform').validate({
		            rules: {
		                loginName: {
		                    required: true,
		                    remote: {
		                    	url: "../user/valiLoginName.action",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	loginName: function() {
		                    	return $("#loginName").val();
		                    	}
		                    	}
		                    	}
		                },
		                password: {
		                    required: true,
		                    remote: {
		                    	url: "../user/valiPassword.action",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	loginName: function() {
		                    	return $("#loginName").val();
		                    	}
		                    	}
		                    	}
		                },
		                remember: {
		                    required: false
		                }
		            },

		            messages: {
		                loginName: {
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
		            }
//		            ,errorPlacement : function(error, element) { 
//		            	//error.appendTo(element.parent().parent()); 
//		            }
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