<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>题目</title>
<link rel="stylesheet" href="css/style.css">
 <script src="js/jquery.min.js.js"></script>
</head>
<body>

<div class="container">
<div class="palyer"><h2>4-1（六选二）</h2><audio autoplay="autoplay" controls="controls"><source src="videoMP3/question6MP3.mp3"/></audio></div>
    <div class="question_img">
      <img src="images/four/1/01.png" />
    </div>
    <div class="choose_one">
       <ul class="kind1">
        <li id="1">A.<img src="images/four/1/1.png"/></li>
        <li id="2">B.<img src="images/four/1/2.png" /></li>
        <li id="3">C.<img src="images/four/1/3.png"/></li>
        <li id="4">D.<img src="images/four/1/4.png" /></li>
        <li id="5">E.<img src="images/four/1/5.png"/></li>
        <li id="6">F.<img src="images/four/1/6.png" /></li>
       </ul>
    </div>
    <div style="clear:both;"></div>
    <div class="next"><img src="images/next.png" /></div>
</div>
   <script type="text/javascript"> 
	//选择答案
	var index = 1;
	var answer = ["2_4", "4_6", "1_6","2_4", "3_4"];
	var selectedStatusArray = [0,0,0,0,0,0];
	var selectedCount = 0;
	var selectedAnswers = [];
	var cScore = 0;
	$(".choose_one ul li").click(function(){
		// 选择的li 对应的索引
		var selectedIndex = $(this).attr("id") - 1;
		// 选项的状态
		var selectedStatus = selectedStatusArray[selectedIndex];
		
		if(selectedStatus == 1){
			$("#"+(selectedIndex+1)).removeClass("bor");
			selectedCount--;
			selectedStatusArray[selectedIndex] = 0;
			selectedAnswers.splice($.inArray((selectedIndex+1),selectedAnswers),1);
		}else{
			if(selectedCount < 2){
				$(this).addClass("bor");
				selectedCount++;
				selectedStatusArray[selectedIndex] = 1;
				selectedAnswers.push(selectedIndex+1);
			}else{
			}
		}
		
	});
	
	$(".next").click(function(){
		var tpans = answer[index-1].split("_");
		
		for(var i = 0;i<selectedAnswers.length;i++){
			var tpcontent = selectedAnswers[i];
			for(var j = 0; j<tpans.length;j++){
				var tptrueans = tpans[j];
				if(tpcontent == tptrueans){
					cScore++;
				}
			}
		}
		
		if(index == 5){
			recordAnswer();
			window.location.href=("choose14.html");
		}else{
			recordAnswer();
			changeImage();
		}
		index++;
		selectedStatusArray = [0,0,0,0,0,0];
		selectedCount = 0;
		$(".palyer h2").text("4-"+index+"（六选二）");
	});
	
	function changeImage(){
		$("ul").children("li").removeClass("bor");
		var currImageIndex = index+1;
		$(".question_img img").attr("src", 'images/four/' +currImageIndex +'/0'+currImageIndex+'.png');
		var items = $("ul").children("li");
		for(var i= 1;i<= items.length;i++){
			$("#"+i+" img").attr("src", 'images/four/' +currImageIndex +'/'+ i + '.png');
		}
	};
	function recordAnswer(){
		var ansdata = {
	 			dimension1:"第四大题",
	 			dimension2:"第"+index+"小题",
	 			que_no:index,
	 			true_answer: answer[index-1].split("_").toString(),
	 			answers:selectedAnswers.sort().toString(),
	 			score:cScore
	     	};
	 
		 $.ajax({
				type: "POST",
				url: "addAnswerOneanswer.action",
				data:ansdata,
				async:false,
				success: function(msg){	
				cScore=0;
				selectedAnswers = [];
				}			
			});
	}
</script> 
</body>
</html>