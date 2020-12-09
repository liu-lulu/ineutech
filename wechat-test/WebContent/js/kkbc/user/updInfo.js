
$(document).ready(function(){
	
			$("input[name='trueName']").focus();
			$("#infoform").validate({
		            rules: {
		            	
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
		            	IDCardNo: {
		            		required:"身份证号必填.",
		            		digits:"只能输入数字."
		                },
		                phone: {
		                	required:"手机号必填.",
		                	digits: "只能输入数字."
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
   		url: "../user/updInfo.do",
   		data: $("#infoform").serialize(),
   		dataType: "json",
   		success: function(data){
   			var models = eval(data); //接受一个JSON字符串，返回解析后的对象。
        	var result = models.state;
        	if(result=="true"){
    			alert("信息修改成功"); 
    			window.location.href="../user/goUpdInfo.do";
    		}else{
    			alert("信息修改失败"); 
    		}
   		}
	});
}
