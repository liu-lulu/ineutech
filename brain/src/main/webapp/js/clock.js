var timeOut=-1;
var se, m = 0, h = 0, s = 0;
var isSubmit=false;
var myDate=null;
function second() {
    if (s > 0 && (s % 60) == 0) { m += 1; s = 0; }
    if (m > 0 && (m % 60) == 0) { h += 1; m = 0; }
    t = (m <= 9 ? "0" + m : m) + ":" + (s <= 9 ? "0" + s : s); 
    $("#showtime").text(t);
    s += 1;
    if(timeOut!=-1&&false==isSubmit){
    	if(timeOut<s){
    		isSubmit=true;
    		if(submitProcess){
    			submitProcess();
    		}
    		document.forms[0].submit();	
    	}
    }
}
function calcCurrentTime(){
	
}
$(document).ready(
	function startclock() { 
		myDate = new Date().getTime();
		se = setInterval("second()", 1000); 
	}
);
function getUseTime(){
	var tt=(new Date()).getTime()-  myDate;  
	return tt;
}