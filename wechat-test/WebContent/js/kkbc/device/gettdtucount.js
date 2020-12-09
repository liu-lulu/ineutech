var flags=true;
$(function(evts) {

});

function itemDetial(ids){
	//location.href="../content/contentdetail.action?content_id="+ids;
	window.open("../item/itemdetailbypad.action?item_id="+ids,"","width=300,scrollbars=yes,resizable=yes");
}
function itemEdit(ids){
	location.href="../item/gocreateitem.action?item_id="+ids;
}
function itemDelete(ids){//区域删除提交
	if(flags==false){
		return;
	}
	 if(confirm("确定要删除吗？")){
		 flags=false;
		$.ajax({
				type : "post",
				url :"../item/delitem.action",
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
							location.href="../item/itemlist.action";
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
