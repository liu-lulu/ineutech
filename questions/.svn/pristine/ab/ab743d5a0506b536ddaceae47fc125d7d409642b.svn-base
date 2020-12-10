<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>题目</title>
<link rel="stylesheet" href="css/style.css">
 <script src="js/jquery.min.js.js"></script>
 <style type="text/css">
.choose_one{
	 width:800px;
	 margin:0 auto;
	 margin-top:50px;
}
.choose_one ul li{
	 width:180px;
	 text-align:center;
	 margin:0 ;
	 padding:0;
}
 </style>
</head>
<body>
<div class="container">
    <div class="palyer">
    <h2>3-1</h2>
    </div>
    <div class="question_img">
      <img src="images/three/1/01.png" />
    </div>
    <div class="choose_one">
       <ul>
        <li id="1">A.<img src="images/three/1/1.png" /></li>
        <li id="2">B.<img src="images/three/1/2.png" /></li>
        <li id="3">C.<img src="images/three/1/3.png" /></li>
        <li id="4">D.<img src="images/three/1/4.png" /></li>
       </ul>
    </div>
    <input id="selectAnswer" type="hidden">
    <div style="clear:both;"></div>
    <div class="next"><img src="images/next.png" /></div>
</div>
   <script type="text/javascript"> 
  //选择答案
   //$(".choose_one ul li").click(function(){
	// $(this).addClass("bor").siblings().removeClass("bor");
	//	});
  
  	var index = 1;
  	var answer = [2,3];
	$(".choose_one ul li").click(function(){
	$(this).parent(".choose_one ul").find("li").removeClass("bor");
	$(this).addClass("bor");
	$("#selectAnswer").val($(this).attr("id"));
	});
	
	$(".next").click(function(){
		if(index==2){
			// 记录最后一题答案 并跳转至下一大题
			recordAnswer();
			window.location.href=("choose7.jsp");
		}else{
			// 记录答案，切换下一小题
			recordAnswer();
			changeImage();
		}
		index++;
		$(".palyer h2").text("3-"+index);
	});
	function changeImage(){
		$("ul").children("li").removeClass("bor");
		var currImageIndex = index+1;
        $(".question_img img").attr("src", 'images/three/' +currImageIndex +'/01.png');
		var items = $("ul").children("li");
		for(var i= 1;i<= items.length;i++){
			$("#"+i+" img").attr("src", 'images/three/' +currImageIndex +'/'+ i + '.png');
		}
	};
	
	function recordAnswer(){
		var ansdata = {
	 			dimension1:"第三大题",
	 			dimension2:"第"+index+"小题",
	 			que_no:index,
	 			true_answer: answer[index-1],
	 			answers:$("#selectAnswer").val(),
	 			score:(answer[index-1]==$("#selectAnswer").val())?1:0
	     	};
	 	
		 $.ajax({
				type: "POST",
				url: "addAnswerOneanswer.action",
				data:ansdata,
				async:false,
				success: function(msg){	
				$("#selectAnswer").val("");
				}			
			});
	}
</script> 
</body>
</html>