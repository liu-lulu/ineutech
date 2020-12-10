<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>题目</title>
<link rel="stylesheet" href="css/style.css">
 <script src="js/jquery.min.js.js"></script>
</head>
<body>
<div class="container">
   <div class="palyer"><h2>2-1</h2><audio autoplay="autoplay" controls="controls"><source src="videoMP3/question-2MP3.mp3"/></audio></div>
    <div class="question_img">
      <img src="images/two/1/01.png"/>
    </div>
    <div class="choose_one">
       <ul class="kind_1">
            <li id="1">A.<img src="images/two/1/1.png"/></li>
            <li id="2">B.<img src="images/two/1/2.png"/></li>
            <li id="3">C.<img src="images/two/1/3.png"/></li>
            <li id="4">D.<img src="images/two/1/4.png"/></li>
            <li id="5">E.<img src="images/two/1/5.png"/></li>
            <li id="6">F.<img src="images/two/1/6.png"/></li>
       </ul>
    </div>
    <input id="selectAnswer" type="hidden">
    <div style="clear:both;"></div>
    <div class="next"><img src="images/next.png" /></div>
</div>
   <script type="text/javascript">
	$(".choose_one ul li").click(function(){
		$(this).parent(".choose_one ul").find("li").removeClass("bor");
		$(this).addClass("bor");
		//alert($(this).attr("id"));
		$("#selectAnswer").val($(this).attr("id"));
		
	});
	
</script>  
 <script src="js/choose3.js"></script>
</body>
</html>