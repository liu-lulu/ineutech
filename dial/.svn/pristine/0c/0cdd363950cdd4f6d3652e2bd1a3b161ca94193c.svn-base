$(function(){
	pageInit();
})
function pageInit(){
	var totalPage = jQuery("#totalPage").val();
	var totalRecords = jQuery("#totalRecords").val();
	var pageNo = jQuery("#pageNo").val();
	var hrefLatterss = jQuery("#hrefLatterss").val();
	var queryss=jQuery("#queryss").val();
	if(!pageNo){
		pageNo = 1;
	}
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		pno : pageNo,
		//总页码
		total : totalPage,
		//总数据条数
		totalRecords : totalRecords,
		//链接前部
		hrefFormer : '',
		//链接尾部
		hrefLatter : hrefLatterss,
		getLink : function(n){
			
			return this.hrefFormer + this.hrefLatter + "?"+queryss+"currentPageNO="+n;
		},
		lang : {
			firstPageText : '首页',
			lastPageText : '尾页',
			prePageText : '上一页',
			nextPageText : '下一页',
			//totalPageBeforeText : '共',
			//totalPageAfterText : '页',
			//totalRecordsAfterText : '条数据',
			//gopageBeforeText : '转到',
			//gopageButtonOkText : '确定',
			//gopageAfterText : '页',
			//buttonTipBeforeText : '第',
			//buttonTipAfterText : '页'
		},
		mode : 'click',//默认值是link，可选link或者click
		click : function(n){
			if(window.linkLatter){
				this.hrefLatter=window.linkLatter;
			}
			 $.ajax({
					type : "post",
					url :this.hrefFormer + this.hrefLatter + "?"+queryss+"currentPageNO="+n,
//					data : $("#regForm").serialize(),
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
							var insertHtmlp="#insertHtml";
							if(window.insertHtml){
								insertHtmlp=window.insertHtml;
							}
							try {
								if(window.bindArray){
									for(var i=0;i<window.bindArray.length;i++){
										if ($(window.bindArray[i][0]).length > 0) {
											$(window.bindArray[i][0]).unbind(window.bindArray[i][1], window.bindArray[i][2]);
										}
									}
								}
							} catch (e) {
								
							}
							jQuery(insertHtmlp).html(data);	
							pageInit();
							try {
								if(window.bindArray){
									for(var i=0;i<window.bindArray.length;i++){
										if ($(window.bindArray[i][0]).length > 0) {
											$(window.bindArray[i][0]).bind(window.bindArray[i][1], window.bindArray[i][2]);
										}
									}
								}
								if(window.callFun){
									window.callFun();
								}
							} catch (e) {
								
							}
							
						}
					}
				});
		   return false;
		}
	},true);

}