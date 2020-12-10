var flags=true;
$(function(evts) {

});

function fasheqipos(ids){
	location.href="../device/fasheqipos.do?dtu_id="+ids;
}

function mapdtu(ids){
	location.href="../device/map.do?s="+ids;
}

function removebind(ids){//解绑
	if(flags==false){
		return;
	}
	 if(confirm("确定要解绑吗？")){
		 flags=false;
		$.ajax({
				type : "post",
				url :"../trucks/removebind.do",
				data : {"dtu_id":ids},
				dataType:"json",
				error : function(request) {
				  flags=true;
		          alert("请求出错");
				},
				success : function(data) {
					try {
						if(data.tag=="1"){
							alert("解绑成功");
							location.href="../trucks/watchlist.do";
						}else{
							flags=true;
							alert("解绑失败");
						}
					} catch (e) {
						
					}
				}
			});
	 }
	
}