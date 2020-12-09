
$(document).ready(function(){
	
			$("input[name='receiveName']").focus();
			$("#infoform").validate({
		            rules: {
		            	receiveName: {
		                    required: true,
		                    remote: {
		                    	url: "../user/valiLoginName.do",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	loginName: function() {
		                    	return $("#receiveName").val();
		                    	}
		                    	}
		                    	}
		                },
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
		                money: {
		                    required: true,
		                    number:true
		                }
		            },

		            messages: {
		            	receiveName: {
		                    required: "接收人用户名必填.",
			                remote:"接收人用户名不存在."
		                },
		            	pwd2: {
		                    required: "二级密码必填.",
			                remote:"二级密码错误."
		                },
		                money: {
		                    required: "转账金额必填.",
		                    number:"转账金额请输入数值."
		                }
		            },

		            submitHandler: function (form) {
		            	//form.submit();
		            	if(moneyCheck()){
		            		submitForm();
		            	}
	            		
		            }/*,
		            errorPlacement : function(error, element) { 
		            	error.appendTo(element.parent().parent()); 
		            }*/
		        });

		        $("#infoform input").keypress(function (e) {
		            if (e.which == 13) {
		                if ($("#infoform").validate().form()&&moneyCheck()) {
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
   		url: "../finance/transfer.do",
   		data: $("#infoform").serialize(),
   		dataType: "json",
   		success: function(data){
   			var models = eval(data); //接受一个JSON字符串，返回解析后的对象。
        	var result = models.state;
        	if(result=="true"){
    			alert("转账成功"); 
    			window.location.href="../finance/myAccount.do";
    		}else{
    			alert("转账失败"); 
    		}
   		}
	});
}

function moneyCheck(){
	var coin_type=document.getElementById('coin_type').value;
	var money=document.getElementById('awardCoins').value;
	if(coin_type=='购物钻'){
		money=document.getElementById('shoppingDrill').value;
	}
	
	var transfer=document.getElementById('money').value;
	if(transfer<=money){
		return true;
	}
	alert("转账金额超出余额限制");
	return false;
}
