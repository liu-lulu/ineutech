<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>题目</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.min.js.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <!--<script src="js/timu.js"></script>-->
</head>
<body>
    <div class="container">
        <div id="choose_img">
            <img src="images/one/1/1.png" />
        </div>
        <div class="choose">
            <div class="true"><img id="right" src="images/勾.png" /></div>
            <div class="false"><img id="arrow" src="images/叉.png" /></div>
        </div>
        <!-- <div style="clear:both;"></div>
        <div class="next"><a href="question2.jsp"><img src="images/next.png" /></a></div>-->
    </div>
    <script type="text/javascript">
        var fenshu = 0;
        $(".container ul li").click(function () {
            $(this).addClass("bor").siblings().removeClass("bor");
        });
        var arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
        var truearrs = $.cookie('choose1ans');
        var truearr =  truearrs.split(",");
        var count = 0;
        //随机显示照片
        function changeImg() {
        	var arrindex = Math.floor(Math.random() * arr.length);
            $("#choose_img img").attr("src", 'images/one/1/' + arr[arrindex] + '.png');
            if (count >= 8) {
                window.location.href = 'question2.jsp?fenshu=' + fenshu;
            }
            arr.splice($.inArray(arr[arrindex],arr),1);
        }
        //setTimeout("changeImg();", 3000);
        //选择对错
        $(".true").click(function () {
           
            if (true) {
            	var imgzf = $(this).parent().prev().children().attr("src");
                var srrStr = imgzf.substring(imgzf.lastIndexOf("/")+1, imgzf.lastIndexOf("."));
                var cunzai = $.inArray(srrStr, truearr);
                if(cunzai!=-1){
                	fenshu++;
                }
            }
            ++count;
            var ansdata = {
            			dimension1:"第一大题",
            			dimension2:"第1小题",
            			que_no:count,
            			true_answer: cunzai==-1?"错":"对",
            			answers:"对",
            			score:(cunzai==-1?"错":"对")=="对"?1:0
                	};
            
            $.ajax({
				type: "POST",
				url: "addAnswerOneanswer.action",
				data:ansdata,
				async: false,
				success: function(msg){	
					 //调动图片随机出函数
		            changeImg();
		            $(this).addClass("C_true");
		            $(".false").removeClass("C_false")
				}			
			});
           
        });
        $(".false").click(function () {
            if (true) {
            	var imgzf = $(this).parent().prev().children().attr("src");
                var srrStr = imgzf.substring(imgzf.lastIndexOf("/")+1, imgzf.lastIndexOf("."));
                var cunzai = $.inArray(srrStr, truearr);
                if(cunzai==-1){
                	fenshu++;
                }
            }
            ++count;
            var ansdata = {
            			dimension1:"第一大题",
            			dimension2:"第1小题",
            			que_no:count,
            			true_answer: cunzai==-1?"错":"对",
            			answers:"错",
            			score:(cunzai==-1?"错":"对")=="错"?1:0
                	};
            
            $.ajax({
				type: "POST",
				url: "addAnswerOneanswer.action",
				data:ansdata,
				async: false,
				success: function(msg){	
					 //调动图片随机出函数
		            changeImg();
		            $(this).addClass("C_false");
		            $(".true").removeClass("C_true");
				}			
			});
        });
    </script>
</body>
</html>