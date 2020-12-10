var zhucheArray = new Array();
zhucheArray[0] = new Array("4*2","A1|A2|B1|B2|B3|B4");
zhucheArray[1] = new Array("6*2","A1|A2|A3|A4|B1|B2|B3|B4");
zhucheArray[2] = new Array("6*4","A1|A2|B1|B2|B3|B4|B5|B6|B7|B8");
zhucheArray[3] = new Array("8*4","A1|A2|A3|A4|B1|B2|B3|B4|B5|B6|B7|B8");
zhucheArray[4] = new Array("4*2+2","A1|A2|B1|B2|B3|B4|C1|C2|C3|C4|C5|C6|C7|C8","C1|C2|C3|C4|C5|C6|C7|C8");
zhucheArray[5] = new Array("4*2+3","A1|A2|B1|B2|B3|B4|C1|C2|C3|C4|C5|C6|C7|C8|C9|C10|C11|C12","C1|C2|C3|C4|C5|C6|C7|C8|C9|C10|C11|C12");
zhucheArray[6] = new Array("6*2+3","A1|A2|A3|A4|B1|B2|B3|B4|C1|C2|C3|C4|C5|C6|C7|C8|C9|C10|C11|C12","C1|C2|C3|C4|C5|C6|C7|C8|C9|C10|C11|C12");
zhucheArray[7] = new Array("6*4+3","A1|A2|B1|B2|B3|B4|B5|B6|B7|B8|C1|C2|C3|C4|C5|C6|C7|C8|C9|C10|C11|C12","C1|C2|C3|C4|C5|C6|C7|C8|C9|C10|C11|C12");
zhucheArray[8] = new Array("4*2+","A1|A2|B1|B2|B3|B4");
zhucheArray[9] = new Array("6*2+","A1|A2|A3|A4|B1|B2|B3|B4");
zhucheArray[10] = new Array("6*4+","A1|A2|B1|B2|B3|B4|B5|B6|B7|B8");


var guacheStyleArray=new Array();
guacheStyleArray[0] = new Array("+2","C1|C2|C3|C4|C5|C6|C7|C8");
guacheStyleArray[1] = new Array("+3","C1|C2|C3|C4|C5|C6|C7|C8|C9|C10|C11|C12");


$(document).ready(function(){
	
			$("input[name='truckId']").focus();
			$("#infoform").validate({
		            rules: {
		            	truckId: {
		                    required: true,
		                    remote: {
		                    	url: "../trucks/checkTrucks_id.action",  //后台处理程序
		                    	type: "post",  //数据发送方式
		                    	cache: false,
		                    	dataType: "json",  //接受数据格式
		                    	data: {  //要传递的数据
		                    	trucks_id: function() {
		                    	return $("#truckId").val();
		                    	}
		                    	}
		                    	} 
		                },
		                mabiao: {
		                    required: true,
		                    number:true 
		                }
		            },

		            messages: {
		            	truckId: {
		                    required: "车牌号必填.",
			                remote:"车牌号已存在."
		                },
		                mabiao: {
		                    required: "码表数必填.",
		                    number:"请输入数值."
		                }
		            },

		            submitHandler: function (form) {
		            	if (checkTyreIdRepeat()&&checkTyreWhereRepeat()&&checkDepth()&&checkTyreId()) {
		            	//form.submit();
	            		submitForm();
		            	}
		            }/*,
		            errorPlacement : function(error, element) { 
		            	error.appendTo(element.parent().parent()); 
		            }*/
		        });

		        $("#infoform input").keypress(function (e) {
		            if (e.which == 13) {
		                if ($("#infoform").validate().form()&&checkTyreIdRepeat()&&checkTyreWhereRepeat()&&checkDepth()&&checkTyreId()) {
		                	//$("#infoform").submit();
		                	submitForm();
		                }
		                return false;
		            }
		        });
		        
		        checkTruckType();
		        $("#guacheTrucksIdDiv").hide();
		        $("#guacheSaveFlag").val('是');
		    	$("#guacheSaveFlagDiv").hide();
		    	$("#dtuMultiFlag").val('一个');
		    	$("#dtuMultiFlagDiv").hide();
		
   });

/**
 * form提交
 */
function submitForm(){
	$.ajax({
   		type: "POST",
   		url: "../trucks/inBasicInfo.action",
   		data: $("#infoform").serialize(),
   		dataType: "json",
   		success: function(data){
   			var models = eval(data); //接受一个JSON字符串，返回解析后的对象。
        	var result = models.state;
        	if(result=="true"){
    			alert("入库成功"); 
    			window.location.href="../trucks/watchlist.action";
    		}else{
    			alert("入库失败"); 
    		}
   		}
	});
}
/**
 * 判断轮胎id在数据库中是否存在
 * @returns true：不存在  false：已存在
 */
function checkTyreId(){
	var tyreIds = document.getElementsByName('tyreId');
	for(i = 0; i < tyreIds.length; i++){
		if(tyreIds[i].value==""){
			$("#tyreMsg").html("第"+(i+1)+"个胎号为空"); 
			return false;
		}
		var result = new Object();
		$.ajax({     
            url: "../trucks/checkTyre_id.action?tyre_id="+tyreIds[i].value,
            type: "POST",
            async:false,
            dataType: "json",
            success: function (data) {
            	var models = eval(data); //接受一个JSON字符串，返回解析后的对象。
            	result = models.state;  
                console.log(result);
            }
        });
		if(result=="false"){
			$("#tyreMsg").html("胎号"+tyreIds[i].value+"已存在");  
			return false;
		}
	}
	return true;
}

/**
 * 检查填写轮胎id是否有重复
 * @returns true：没有重复胎号  false：有重复胎号
 */
function checkTyreIdRepeat(){
	var tyreIds = getValuesByEleName('tyreId');
	
	var repeatTyreId = checkArrayRepeat(tyreIds);
	if(repeatTyreId!=null){
		$("#tyreMsg").html("胎号"+repeatTyreId+"填写重复");  
		return false;
	}
	
	return true;
}

/**
 * 检查轮胎安装位置选择是否重复
 * @returns true：不重复 false：重复
 */
function checkTyreWhereRepeat(){
	var wheres = getValuesByEleName('tyreWhere');
	
	var repeatWhere = checkArrayRepeat(wheres);
	if(repeatWhere!=null){
		$("#tyreMsg").html("胎位置"+repeatWhere+"选择重复");
		return false;
	}
	return true;
}

/**
 * 检查每个轮胎的原始深度值是否为数字
 * @returns true：数字 false：非数字
 */
function checkDepth(){
	var depths=getValuesByEleName('tyreDepth');
	for(var i=0;i<depths.length;i++){
		if(!checkRate(depths[i])){
			$("#tyreMsg").html("第"+(i+1)+"个胎的原始深度值"+depths[i]+"非数字");
			return false;
		}
	}
	return true;
}

/**
 * 获取所有同为name属性的value
 * @param name
 * @returns {Array}
 */
function getValuesByEleName(name){
	var eles = document.getElementsByName(name);
	var eleValues = new Array(eles.length);
	for(var i=0;i<eles.length;i++){
		eleValues[i]=eles[i].value;
	}
	return eleValues;
}


/**
 * 检查数组中的重复数据
 * @param arr
 * @returns
 */
function checkArrayRepeat(arr){
	var arrSort = arr.sort();
	for(var i=0;i<arrSort.length;i++){
		if (arrSort[i]==arrSort[i+1]){
			return arrSort[i];
		}
	}
	return null;
}

/**
 * 判断字符串是否为数字 
 * @param nubmer 字符串
 * @returns true:数字 false：非数字
 */
function checkRate(nubmer){
     var re = /^[0-9]+.?[0-9]*$/;//判断正整数 /^[1-9]+[0-9]*]*$/  
     if (!re.test(nubmer)){
        return false;
     }
     return true
}

/**
 * 根据车型获取胎个数
 * @param style 车型
 * @returns
 */
function getTyreNumByStyle(style){
	var styleArray=zhucheArray;
    
    if(document.getElementById('truckType').value=="挂车"){
    	styleArray=guacheStyleArray;
    }
	for (i = 0; i < styleArray.length; i++) {
		if(styleArray[i][0]==style){
			return styleArray[i][1].split("|").length;
		}
    }
	return 0;
}

/**
 * 添加轮胎
 */
function addTyre() {
	var style = document.getElementById('truckStyle').value;
	var tyreNum=getTyreNumByStyle(style);
	if(document.getElementsByName('tyreId').length<tyreNum){
		$("#tyres").append($("#tyre").clone());
	}else{
		alert(document.getElementById('truckType').value+"车型"+style+"共有"+tyreNum+"个轮胎,不能再添加轮胎");
	}
}

/**
 * 根据车型设置车胎位置下拉列表框选项
 */
function getWhere(){
	removeExtraTyre();
	var tyreWhere = document.getElementsByName('tyreWhere');
	for (i = 0; i < tyreWhere.length; i++) {
        getWhere1(tyreWhere[i]);
    }
	var style = document.getElementById('truckStyle').value;
	var type = document.getElementById('truckType').value;
	if(0<style.indexOf("+")&&type=="主车"){
		if(style.length==5){
			$("#guacheTrucksIdDiv").show();
			$("#guacheSaveFlagDiv").show();
			$("#dtuMultiFlagDiv").show();
		}else{
			$("#guacheTrucksIdDiv").hide();
			$("#guacheSaveFlag").val('否');
			$("#guacheSaveFlag").removeAttr("disabled");
			$("#guacheSaveFlagDiv").hide();
			$("#dtuMultiFlag").val('一个');
			$("#dtuMultiFlagDiv").hide();
		}
		
	}else{
		$("#guacheTrucksIdDiv").hide();
		$("#guacheSaveFlag").val('是');
		$("#guacheSaveFlag").removeAttr("disabled");
		$("#guacheSaveFlagDiv").hide();
		if(type=="挂车"){
			$("#dtuMultiFlagDiv").show();
		}else{
			$("#dtuMultiFlag").val('一个');
			$("#dtuMultiFlagDiv").hide();
		}
	}
}

/**
 * 根据车类型[主车/挂车]设置车型和胎位置的下拉选项
 */
function checkTruckType(){
	var type = document.getElementById('truckType').value;
	$("#guacheTrucksIdDiv").hide();
    $("#guacheSaveFlag").val('是');
    $("#guacheSaveFlag").removeAttr("disabled");
	$("#guacheSaveFlagDiv").hide();
	if(type=="主车"){
		$("#dtuMultiFlag").val('一个');
    	$("#dtuMultiFlagDiv").hide();
	}else{
		$("#dtuMultiFlagDiv").show();
	}
	setStyleAndWhereByType(type);
	removeExtraTyre();
}

/**
 * 根据车类型[主车/挂车]设置车型和胎位置的下拉选项
 * @param type 车类型[主车/挂车]
 */
function setStyleAndWhereByType(type){
	var styleArray=zhucheArray;
	style = document.getElementById('truckStyle');
    style.length = 0;
    
    if(type=="挂车"){
    	styleArray=guacheStyleArray;
    }
    for (i = 0; i < styleArray.length; i++) {
        style.options[style.length] = new Option(styleArray[i][0], styleArray[i][0]);
    }
    var tmpwhereArray = styleArray[0][1].split("|");
    var tyreWheres = document.getElementsByName('tyreWhere');
    for(var i=0;i<tyreWheres.length;i++){
    	tyreWhere = tyreWheres[i];
    	tyreWhere.length = 0;
        for (j = 0; j < tmpwhereArray.length; j++) {
        	tyreWhere.options[tyreWhere.length] = new Option(tmpwhereArray[j], tmpwhereArray[j]);
        }
    }
}

/**
 * 根据车型设置胎位置的下拉列表项
 * @param tyreWhere 胎位置下拉列表
 */
function getWhere1(tyreWhere) {
    var currtruckStyle = $("#truckStyle").val();
    if (currtruckStyle == "") {
        $("#tyreWhere").empty();
        $("#tyreWhere").append("<option value=''>请选择</option>");
    }
    else {
    	var styleArray=zhucheArray;
        
        if(document.getElementById('truckType').value=="挂车"){
        	styleArray=guacheStyleArray;
        }
        tyreWhere.length = 0;
        for (var i = 0; i < styleArray.length; i++) {
            if (styleArray[i][0] == currtruckStyle) {
                tmpstyleArray = styleArray[i][1].split("|")
                for (j = 0; j < tmpstyleArray.length; j++) {
                	tyreWhere.options[tyreWhere.length] = new Option(tmpstyleArray[j], tmpstyleArray[j]);
                    
                }
            }
        }
    }
}

/**
 * 删除超出轮胎总个数的多余的轮胎
 */
function removeExtraTyre(){
	var tyreNum=getTyreNumByStyle($("#truckStyle").val());
	var currenNum = document.getElementsByName('tyre').length;
	if(currenNum>tyreNum){
		for(var i=tyreNum;i<currenNum;i++){
			$("#tyres div:last").remove();
		}
	}
}


function dtuChange(){
	var dtuValue=$("#dtuMultiFlag").val();
	if(dtuValue=='分开'){
		$("#guacheSaveFlag").val('否');
		$("#guacheSaveFlag").attr("disabled","disabled");
	}else{
		$("#guacheSaveFlag").removeAttr("disabled");
	}
}
