	var formN="#form";
	var submitButtonN="#submitButton"
    window.linkLatter="";
	window.ajaxFormSumbit=ajaxFormSumbit;
	/**
	 * 配置
	 * bindArray 为需要绑定的元素，及函数,格式为[["#id","click",fun],["#id1","click",fun1],["#id2","click",fun2]]或[[".className","click",fun],[".className1","click",fun1],[".className2","click",fun2]]可选
	 * insertHtml，为异步提交或翻页后，内容插入元素,如"#insertHtml",可选，默认为#insertHtml
	 * formName  form表单名,可选,默认为#form
	 * submitButton 提交按扭名称，默认为#submitButton
	 * links 为提交链接action，可选,默认为当前action地址
	 * callFun 回调函数,异步调用成功后，执行的函数
	 * callFun 回调函数,异步调用前，执行的函数
	 */
	function setAjaxSumbitConfig(bindArray,insertHtml,formName,submitButton,links,callFun,beforeFun){
		if(bindArray){
			window.bindArray=bindArray;
		}
		if(insertHtml){
			window.insertHtml=insertHtml;
		}
		if(formName){
			formN=formName;
		}
		if(submitButton){
			submitButtonN=submitButton;
		}
		if(links){
			window.linkLatter=links;
		}else{
			window.linkLatter= jQuery("#hrefLatterss").val();
		}
		if(beforeFun){
			window.beforeFun=beforeFun;
		}
		if(callFun){
			window.callFun=callFun;
		}
		$(submitButtonN).bind("click",ajaxFormSumbit);
		if(window.bindArray){
			for(var i=0;i<window.bindArray.length;i++){
				if ($(window.bindArray[i][0]).length > 0) {
					$(window.bindArray[i][0]).bind(window.bindArray[i][1], window.bindArray[i][2]);
				}
			}
		}
	}
	//为表单提交函数
  function ajaxFormSumbit(){
		   $.ajax({
				type : "post",
				url : linkLatter,
				data : $(formN).serialize(),
				error : function(request) {
					alert("请求出错");
				},
				beforeSend: function(request) {
					try{
						if(window.beforeFun){
							window.beforeFun();
						}
					}catch(e){}
				},
				success : function(data) {
					if(data){
						try{
							if(data.indexOf('<title>哎呀…太不给力</title>')>0){
								alert("请求出错");
								return;
							}
						}catch(e){
							
						}
						try {
							if(window.bindArray){
								for(var i=0;i<window.bindArray.length;i++){
									if ($(window.bindArray[i][0]).length > 0) {
										$(window.bindArray[i][0]).unbind(window.bindArray[i][1], window.bindArray[i][2]);
									}
								}
							}
							$(submitButtonN).unbind("click", ajaxFormSumbit);
						} catch (e) {
							
						}
						jQuery(insertHtml).html(data);	
						pageInit();
						try {
							if(window.bindArray){
								for(var j=0;j<window.bindArray.length;j++){
									if ($(window.bindArray[j][0]).length > 0) {
										$(window.bindArray[j][0]).bind(window.bindArray[j][1], window.bindArray[j][2]);
									}
								}
							}
							$(submitButtonN).bind("click", ajaxFormSumbit);
							if(window.callFun){
								window.callFun();
							}
						} catch (e) {
							
						}
						
					}
				}
			});
		  }
	//为ajax非表单提交函数
  function ajaxSumbit(link,datas){
	       if(typeof(link)=="undefined"||link==null){
	    	   var link=window.linkLatter;
	       }
	       if(typeof(datas)=="undefined"||datas==null){
	    	   var datas={};
	       }
		   $.ajax({
				type : "post",
				url : link,
				data : datas,
				error : function(request) {
					alert("请求出错");
				},
				success : function(data) {
					if(data){
						try{
							if(data.indexOf('<title>哎呀…太不给力</title>')>0){
								alert("请求出错");
								return;
							}
						}catch(e){
							
						}
						try {
							if(window.bindArray){
								for(var i=0;i<window.bindArray.length;i++){
									if ($(window.bindArray[i][0]).length > 0) {
										$(window.bindArray[i][0]).unbind(window.bindArray[i][1], window.bindArray[i][2]);
									}
								}
								$(submitButtonN).unbind("click", ajaxFormSumbit);
							}
						} catch (e) {
							
						}
						jQuery(insertHtml).html(data);	
						paging_Adjax.pageInit();
						try {
							if(window.bindArray){
								for(var j=0;j<window.bindArray.length;j++){
									if ($(window.bindArray[j][0]).length > 0) {
										$(window.bindArray[j][0]).bind(window.bindArray[j][1], window.bindArray[j][2]);
									}
								}
							}
							$(submitButtonN).bind("click", ajaxFormSumbit);
							if(window.callFun){
								window.callFun();
							}
						} catch (e) {
							
						}
						
					}
				}
			});
		  }