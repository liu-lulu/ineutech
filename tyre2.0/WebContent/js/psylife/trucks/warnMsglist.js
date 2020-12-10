
$(function(evts) {
	setInterval("myAutoFlush()",15000);
});
var tt=false;
function myAutoFlush(){//自动刷新
	if($("#autoflush").prop("checked")==true&&tt==false){
		$("#warnMsgForm").submit();
		tt=true;
	}
}