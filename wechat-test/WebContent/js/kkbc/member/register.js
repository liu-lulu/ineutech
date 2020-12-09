
$(document).ready(function(){
	
			$("input[name='loginName']").focus();
			$("#infoform").validate({
		            rules: {
		            	loginName: {
		                    required: true,
		                    remote: {
		                    	url: "../user/loginNameExist.do",  //后台处理程序
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
		                    required: true
		                },
		                confirmPass: {
		                    required: true,
		                    equalTo:"#password" 
		                },
		                referral: {
		                    required: true,
		                    remote: {
		                    	url: "../user/valiLoginName.do",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	loginName: function() {
		                    	return $("#referral").val();
		                    	}
		                    	}
		                    	}
		                },
		                contactPerson: {
		                    required: true,
		                    remote: {
		                    	url: "../user/valiLoginName.do",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	loginName: function() {
		                    	return $("#contactPerson").val();
		                    	}
		                    	}
		                    	}
		                },
		                pwd2: {
		                    required: true
		                },
		                confirmPwd2: {
		                    required: true,
		                    equalTo:"#pwd2" 
		                },
		                trueName: {
		                    required: true
		                },
		            	IDCardNo: {
		            		required: true,
		                    digits:true
		                },
		                phone: {
		                	required: true,
		                    digits:true
		                }
		            },

		            messages: {
		            	loginName:{
		            		required:"登陆名必填.",
		            		remote:"登陆名已存在."
		            	},
		            	password: {
		                    required: "登陆密码必填."
		                },
		                confirmPass: {
		                    required: "确认登陆密码必填.",
		                    equalTo:"登陆密码两次输入不一致." 
		                },
		                referral: {
		                    required: "推荐人必填.",
		                    remote:"推荐人不存在."
		                },
		                contactPerson: {
		                    required: "接点人必填.",
		                    remote:"接点人不存在."
		                },
		                pwd2: {
		                    required: "二级密码必填."
		                },
		                confirmPwd2: {
		                    required: "确认二级密码必填.",
		                    equalTo:"二级密码两次输入不一致." 
		                },
		                trueName: {
		                    required: "真实名必填."
		                },
		            	IDCardNo: {
		            		digits:"只能输入数字"
		                },
		                phone: {
		                	digits: "只能输入数字"
		                }
		            },

		            submitHandler: function (form) {
		            	//form.submit();
	            		submitForm();
		            }/*,
		            errorPlacement : function(error, element) { 
		            	error.appendTo(element.parent().parent()); 
		            }*/
		        });

		        $("#infoform input").keypress(function (e) {
		            if (e.which == 13) {
		                if ($("#infoform").validate().form()) {
		                	//form.submit();
		            		submitForm();
		                }
		                return false;
		            }
		        });
		
   });

/**
 * form提交
 */
function submitForm(){
	$.ajax({
   		type: "POST",
   		url: "../member/register.do",
   		data: $("#infoform").serialize(),
   		dataType: "json",
   		success: function(data){
   			var models = eval(data); //接受一个JSON字符串，返回解析后的对象。
        	var result = models.state;
        	if(result=="true"){
    			alert("注册成功,请登陆进行激活"); 
    			window.location.href="../member/diagram.do";
    		}else{
    			alert("注册失败"); 
    		}
   		}
	});
}
