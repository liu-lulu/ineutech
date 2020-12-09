// JavaScript Document

/**
 * jquery.validate.js v1.0 
 * author www.easyinker.com
 *
 */

 /**
  * 返回text文本数据
  * @param url
  * @param param
  * @param successDivId
  * @param messageDivId
  * @return
  */
function callText(url,param,successDivId,messageDivId){
	return call("text",url,param,successDivId,messageDivId);
}


/**
 * 密码验证
 * 1、密码必须由数字、字符、特殊字符三种中的两种组成；
 * 2、密码长度不能少于8个字符；
 **/
 function validate_pass(value){
        /*(?!^\\d+$)不能全是数字
		(?!^[a-zA-Z]+$)不能全是字母
		(?!^[_#@]+$)不能全是符号（这里只列出了部分符号，可自己增加，有的符号可能需要转义）
		.{8,}长度不能少于8*/位
	 var regm=/(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{8,}/;
	  var re = new RegExp(regm);
	   if (value.search(re) != -1){
		  return true;
		} else {
		  return false;
		}
 }
 
 /**
  * 验证正整数
  * @param str
  * @returns {Boolean}
  */
 function forcheck(str){

         var   type="^[0-9]*[1-9][0-9]*$";
         var   re   =   new   RegExp(type);
         if(str.match(re)==null)
         {
         
           return  true;
         }
 		return false;

 } 
 
 function va_pass(value){
	 /*  设计的要求是：
		密码限长为{5,20}
		密码的第一个字符必须是字母:^[a-zA-Z]
		其余的字符里面必须有一个非[a-zA-Z_0-9]*/
	var regm= /^[a-zA-Z]\w{5,20}$/;
	var re = new RegExp(regm);
	if (value.search(re) != -1){
		  return true;
	} else {
		  return false;
	}
 }
 
 
/**
 * 判断是否是空
 * @param value
 */
function isEmpty(value){
	if(value == null || value == "" || value == "undefined" || value == undefined || value == "null"){
		return true;
	}
	else{
		value = value.replace(/\s/g,"");
		if(value == ""){
			return true;
		}
		return false;
	}
}

/**
 * 判断是否是数字
 * author easyinker qidb
 */
function isNumber(value){
	if(isNaN(value)){
		return false;
	}
	else{
		return true;
	}
}

/**
 * 只包含中文和英文
 * @param cs
 * @returns {Boolean}
 */
function isGbOrEn(value){
    var regu = "^[a-zA-Z\u4e00-\u9fa5]+$";
    var re = new RegExp(regu);
    if (value.search(re) != -1){
      return true;
    } else {
      return false;
    }
}

/**
 * 检查邮箱格式
 * @param email
 * @returns {Boolean}
 */
function check_email(email){  
   if(email){
   var myReg=/(^\s*)\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\s*$)/;
   if(!myReg.test(email)){return false;}
   return true;
   }
   return false;
}

/**
 * 检查手机号码
 * @param mobile
 * @returns {Boolean}
 */
function check_mobile(mobile){
  var regu = /^\d{11}$/;
  var re = new RegExp(regu);
  if(!re.test(mobile)){
	 return  false;
  }
  return true;
}

/**
 * 验证电话号码，带"(,),-"字符和数字其他不通过
 * @param str
 * @returns {Boolean}
 */
function checkPhone(str){
   if(str.length > 20){
    return false;
   }
   var patternStr = "(0123456789-)";
   var  strlength=str.length; 
   for(var i=0;i<strlength;i++){ 
        var tempchar=str.substring(i,i+1); 
		if(patternStr.indexOf(tempchar)<0){
		    return false;
		}
   } 
   return true ; 
}


/**
 * 验证银行账户，带"(, ),-"字符和数字其他不通过
 * @param str
 * @returns {Boolean}
 */
function checkBankCount(str){
   if(str.length > 50){
    return false;
   }
   var patternStr = "(0123456789- )";
   var  strlength=str.length; 
   for(var i=0;i<strlength;i++){ 
        var tempchar=str.substring(i,i+1); 
		if(patternStr.indexOf(tempchar)<0){
		    return false;
		}
   } 
   return true ; 
}

//正则
function trimTxt(txt){
 return txt.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 检查是否含有非法字符
 * @param temp_str
 * @returns {Boolean}
 */
function is_forbid(temp_str){
    temp_str=trimTxt(temp_str);
	temp_str = temp_str.replace('*',"@");
	temp_str = temp_str.replace('--',"@");
	temp_str = temp_str.replace('/',"@");
	temp_str = temp_str.replace('+',"@");
	temp_str = temp_str.replace('\'',"@");
	temp_str = temp_str.replace('\\',"@");
	temp_str = temp_str.replace('$',"@");
	temp_str = temp_str.replace('^',"@");
	temp_str = temp_str.replace('.',"@");
	temp_str = temp_str.replace(';',"@");
	temp_str = temp_str.replace('<',"@");
	temp_str = temp_str.replace('>',"@");
	temp_str = temp_str.replace('"',"@");
	temp_str = temp_str.replace('=',"@");
	temp_str = temp_str.replace('{',"@");
	temp_str = temp_str.replace('}',"@");
	var forbid_str=new String('@,%,~,&');
	var forbid_array=new Array();
	forbid_array=forbid_str.split(',');
	for(i=0;i<forbid_array.length;i++){
		if(temp_str.search(new RegExp(forbid_array[i])) != -1)
		return false;
	}
	return true;
}

/**
 * 检查数量
 * @param txtObj
 * @returns {Number}
 */
function checkLength(txtObj){
	var val=txtObj;
	var valLength=0;
	for(var ii=0;ii<val.length;ii++){
		var word=val.substring(ii,1);
		if(/[^\x00-\xff]/g.test(word)){
			valLength+=2;
		}else{
			valLength++;
		}
	}
	return valLength;
}
/**
 * 配置公共参数
 * @returns {OrderAppConfig}
 */
function OrderAppConfig(){
}

/**
 * 从url里获取对应参数值
 * @param paramName
 * @returns {String}
 */
function getParam(paramName)
{
    var paramValue = "";
    isFound = false;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=")>1)
    {
        arrSource = unescape(this.location.search).substring(1,this.location.search.length).split("&");
        i = 0;
        while (i < arrSource.length && !isFound)
        {
            if (arrSource[i].indexOf("=") > 0)
            {
                 if (arrSource[i].split("=")[0].toLowerCase()==paramName.toLowerCase())
                 {
                    paramValue = arrSource[i].split("=")[1];
                    isFound = true;
                 }
            }
            i++;
        }   
    }
	return paramValue;
}


/**
 * @验证ZIPCOOD 的格式
 */
function check_zipCood(value){
        var re= /^[0-9][0-9]{5}$/
        if(re.test(value))
           return true
        else
        {
			return false;
          

        }

}
 function arrayIndexOf(array,obj) {
	for (var i = 0; i < array.length; i++) {
		if (array[i] == obj) {
		  return true;
		}
	}
   return false;
};


