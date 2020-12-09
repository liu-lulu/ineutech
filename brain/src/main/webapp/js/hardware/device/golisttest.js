var pingLuning=!1,currentOnLine="";
/*$(function(c){
	$("#searchs").click(function(b){
		document.getElementById("searchsForm").submit();
		});$(".light").click(function(b){if(1!=pingLuning){b=$(this).attr("data-deviceid");var a=$(this).attr("data-value"),c=$(this).attr("data-type"),d=$(this).attr("data-time");currentOnLine=$(this).attr("data-online");jQuery.ajax({url:"../device/testlight.htm",data:{deviceId:b,tag:a,type:c,gaptime:d},type:"POST",beforeSubmit:function(){pingLuning=!0},error:function(a){pingLuning=!1;alert("\u63d0\u4ea4\u51fa\u9519\uff0c\u8bf7\u7a0d\u5019\u518d\u8bd5")},
success:function(a){pingLuning=!1;"1"==a.tag?(alert("\u64cd\u4f5c\u6210\u529f\uff01"),"0"==currentOnLine&&(window.location.href="../device/golisttest.htm")):"2"==a.tag?(alert("\u786c\u4ef6\u5df2\u79bb\u7ebf\uff01"),window.location.href="../device/golisttest.htm"):alert("\u64cd\u4f5c\u5931\u8d25\uff01")}})}});$("#initstepqueueconfig").click(function(b){1!=pingLuning&&jQuery.ajax({url:"../device/initstepqueueconfig.htm",data:{},type:"POST",beforeSubmit:function(){pingLuning=!0},error:function(a){pingLuning=
!1;alert("\u63d0\u4ea4\u51fa\u9519\uff0c\u8bf7\u7a0d\u5019\u518d\u8bd5")},success:function(a){pingLuning=!1;"1"==a.tag?alert("\u64cd\u4f5c\u6210\u529f\uff01"):alert("\u64cd\u4f5c\u5931\u8d25\uff01")}})});$(".modifieds").click(function(b){1!=pingLuning&&(b=$(this).attr("data-deviceid"),jQuery.ajax({url:"../device/modifieds.htm",data:{deviceId:b},type:"POST",beforeSubmit:function(){pingLuning=!0},error:function(a){pingLuning=!1;alert("\u63d0\u4ea4\u51fa\u9519\uff0c\u8bf7\u7a0d\u5019\u518d\u8bd5")},
success:function(a){pingLuning=!1;"1"==a.tag?alert("\u64cd\u4f5c\u6210\u529f\uff01"):"2"==a.tag?alert("\u786c\u4ef6  \uff0c\u5373\u6ca1\u6709\u5728\u7ebf\uff0c\u53c8\u6ca1\u4fee\u6b63\u4fe1\u606f"):alert("\u64cd\u4f5c\u5931\u8d25\uff01")}}))});$(".starttesthand").click(function(b){1!=pingLuning&&jQuery.ajax({url:"../device/starttestpad.htm",data:{},type:"POST",beforeSubmit:function(){pingLuning=!0},error:function(a){pingLuning=!1;alert("\u63d0\u4ea4\u51fa\u9519\uff0c\u8bf7\u7a0d\u5019\u518d\u8bd5")},
success:function(a){pingLuning=!1;"1"==a.tag?(alert("\u64cd\u4f5c\u6210\u529f\uff01"),window.location.href="../device/golisttest.htm"):"0"==a.tag?(alert("\u5728\u4f60\u64cd\u4f5c\u4e4b\u524d\u5c31\u5f00\u59cb\u6d4b\u8bd5\u4e86\uff01"),window.location.href="../device/golisttest.htm"):alert("\u64cd\u4f5c\u5931\u8d25\uff01")}})});$(".endtesthand").click(function(b){1!=pingLuning&&jQuery.ajax({url:"../device/endtestpad.htm",data:{},type:"POST",beforeSubmit:function(){pingLuning=!0},error:function(a){pingLuning=
!1;alert("\u63d0\u4ea4\u51fa\u9519\uff0c\u8bf7\u7a0d\u5019\u518d\u8bd5")},success:function(a){pingLuning=!1;"1"==a.tag?(alert("\u64cd\u4f5c\u6210\u529f\uff01"),window.location.href="../device/golisttest.htm"):"0"==a.tag?(alert("\u5728\u4f60\u64cd\u4f5c\u4e4b\u524d\u5c31\u7ed3\u675f\u6d4b\u8bd5\u4e86\uff01"),window.location.href="../device/golisttest.htm"):alert("\u64cd\u4f5c\u5931\u8d25\uff01")}})})});
*/
function editLabelName(c){
	$("#editLabelNameButton_"+c).hide();
	$("#editLabelNameDiv_"+c).css("display","inline-block");
}

function submitLabelName(c){
	if(0!=confirm("请谨慎修改硬件名,确认要修改吗")&&1!=pingLuning){
		var b=$("#inputLabelName_"+c).val();
		b=$.trim(b);
		if(""==b){
			alert("硬件名不能为空");
		}else{
			jQuery.ajax({
				url:"../device/savedeviceremark.htm",
				data:{
					deviceId:c,
					labelName:b
				},
				type:"POST",
				dataType: "json",
				beforeSubmit:function(){
					pingLuning=!0;
					},
				error:function(a){
					pingLuning=!1;
					alert("提交出错,请稍后再试");
					},
				success:function(a){
//					pingLuning=!1;
					var models = eval(a);
		        	var result = models.tag;
		        	if(result=="1"){
		    			alert("修改成功"); 
		    			$("#deviceLabelName_"+c).html(b);
		    			$("#editLabelNameButton_"+c).show();
		    			$("#editLabelNameDiv_"+c).css("display","none");
		    		}else{
		    			alert("操作失敗"); 
		    		}
//					"1"==a.tag?($("#deviceLabelName_"+c).html(b),$("#editLabelNameButton_"+c).show(),$("#editLabelNameDiv_"+c).css("display","none")):alert("操作失敗")
				}});
		}
		
		}
	}

function cancelLabelName(c){
	$("#editLabelNameButton_"+c).show();
	$("#editLabelNameDiv_"+c).css("display","none");
}