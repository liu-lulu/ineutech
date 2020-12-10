var flags=true;
$(function(evts) {
	$.datetimepicker.setLocale('zh');
	var e = $("#startTime"), t = $("#endTime");
	e.datetimepicker({
		lang : 'zh',
		format : "Y-m-d H:i",
		formatDate : "Y-m-d",
		defaultTime : "00:00"
	}), t.datetimepicker({
		lang : 'zh',
		format : "Y-m-d H:59",
		formatTime : "H:59",
		defaultTime : "23:59",
		formatDate : "Y-m-d"
	});
	
	$("a[title='尾页']").hide();
	$(".infoTextAndGoPageBtnWrap").hide();
	
});

function itemDetial(ids){
	//location.href="../content/contentdetail.action?content_id="+ids;
	window.open("../item/itemdetailbypad.do?item_id="+ids,"","width=300,scrollbars=yes,resizable=yes");
}
function itemEdit(ids){
	location.href="../item/gocreateitem.do?item_id="+ids;
}
function itemDelete(ids){//区域删除提交
	if(flags==false){
		return;
	}
	 if(confirm("确定要删除吗？")){
		 flags=false;
		$.ajax({
				type : "post",
				url :"../item/delitem.do",
				data : {"id":ids},
				dataType:"json",
				error : function(request) {
				  flags=true;
		          alert("请求出错");
				},
				success : function(data) {
					try {
						if(data.tag=="1"){
							alert("删除成功");
							location.href="../item/itemlist.do";
						}else{
							flags=true;
							alert("删除失败");
						}
					} catch (e) {
						
					}
				}
			});
	 }
	
}
