var flags=true;
$(function(evts) {

});
function selectUserSubmit(){
	var selects=$("input[name='replaceUserCla']:checked");
	if(selects.length>0){
		$("#trucks_id").val(selects.val());
		document.getElementById('trucksbindform').submit();
	}else{
		alert("请选择一个车辆再提交");
	}
}