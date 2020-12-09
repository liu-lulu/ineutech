
$(document).ready(function(){
	
			$("input[name='oldPwd']").focus();
			$("#infoform").validate({
		            rules: {
		            	oldPwd: {
		                    required: true,
		                    remote: {
		                    	url: "../user/valiPwd.do",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
	                    		type: function() {
			                    	return $("input[name='type']:checked").val();
			                    	},
		                    	oldPwd: function() {
		                    	return $("#oldPwd").val();
		                    	}
		                    	}
		                    	} 
		                },
		                newPwd: {
		                    required: true
		                },
		                confirmPwd: {
		                    required: true,
		                    equalTo:"#newPwd" 
		                }
		            },

		            messages: {
		            	oldPwd: {
		                    required: "原密码必填.",
			                remote:"原密码错误."
		                },
		                newPwd: {
		                    required: "新密码必填."
		                },
		                confirmPwd: {
		                    required: "确认密码必填.",
		                    equalTo:"两次输入密码不一致"
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
   		url: "../user/updPassword.do",
   		data: $("#infoform").serialize(),
   		dataType: "json",
   		success: function(data){
   			var models = eval(data); //接受一个JSON字符串，返回解析后的对象。
        	var result = models.state;
        	if(result=="true"){
    			alert("密码修改成功"); 
    			window.location.href="../user/goUpdPassword.do";
    		}else{
    			alert("密码修改失败"); 
    		}
   		}
	});
}
