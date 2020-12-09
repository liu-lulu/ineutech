
$(document).ready(function(){
	
			$("input[name='convertMoney']").focus();
			$("#infoform").validate({
		            rules: {
		            	pwd2: {
		                    required: true,
		                    remote: {
		                    	url: "../user/valiPwd2.do",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	pwd2: function() {
		                    	return $("#pwd2").val();
		                    	}
		                    	}
		                    	} 
		                },
		                convertMoney: {
		                    required: true,
		                    number:true,
		                    min:100
		                }
		            },

		            messages: {
		            	pwd2: {
		                    required: "二级密码必填.",
			                remote:"二级密码错误."
		                },
		                convertMoney: {
		                    required: "转换金额必填.",
		                    number:"转换金额请输入数值.",
		                    min:"转换金额最少100."
		                }
		            },

		            submitHandler: function (form) {
		            	//form.submit();
		            	if(checkMoney()){
		            		submitForm();
		            	}
		            }/*,
		            errorPlacement : function(error, element) { 
		            	error.appendTo(element.parent().parent()); 
		            }*/
		        });

		        $("#infoform input").keypress(function (e) {
		            if (e.which == 13) {
		                if ($("#infoform").validate().form()&&checkMoney()) {
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
   		url: "../finance/coinConvert.do",
   		data: $("#infoform").serialize(),
   		dataType: "json",
   		success: function(data){
   			var models = eval(data); //接受一个JSON字符串，返回解析后的对象。
        	var result = models.state;
        	if(result=="true"){
    			alert("转换成功"); 
    			window.location.href="../finance/myAccount.do";
    		}else{
    			alert("转换失败"); 
    		}
   		}
	});
}

function checkMoney(){
	var money=document.getElementById('awardCoins').value;
	var convert=document.getElementById('convertMoney').value;
	if(convert<=money){
		return true;
	}
	alert("转换金额超出余额限制");
	return false;
}
