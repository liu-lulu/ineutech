$(function(a){
	$("#startTime").datepicker();
	$("#endTime").datepicker();
	$("#searchs").click(function(a){
		document.getElementById("searchsForm").submit()});
	setInterval("myAutoFlush()",1E4)});
var tt=!1;
function myAutoFlush(){
	1==$("#autoflush").prop("checked")&&0==tt&&(document.getElementById("searchsForm").submit(),tt=!0)};