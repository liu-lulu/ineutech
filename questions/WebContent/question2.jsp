<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>题目</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<script src="${pageContext.request.contextPath}/js/jquery.min.js.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
</head>
<style type="text/css">
	.img_box {
	margin-top: 50px;
	}
	
	.img_box ul {
	width: 800px;
	margin: 0 auto;
	}
	
	.img_box ul li {
	list-style: none;
	display: block;
	list-style: none;
	float: left;
	width: 200px;
	height: 150px;
	text-align: center;
	}
</style>
<body>
<div class="container">
    <div class="palyer"><h2>1-2</h2><audio autoplay="autoplay" controls="controls"><source src="videoMP3/question2MP3.mp3" /></audio></div>
        <div class="tbox" style="margin-bottom:50px;">
            <div class="tiao"></div>
        </div>
        <!--  <div class="img_box">
            <img  src="images/3.png">
           </div>-->
    </div>
    <script type="text/javascript">
        var i = 0;
        var ans = [];
        /*add——创建tbx下的div加文字和变宽度的方法*/
        function add(i) {
            var tbox = $(".tbox");
            var tiao = $(".tiao");
            tiao.css("width", i + "%").html(i + "%");
        }
        function xh() {
            if (i > 100) {
            	$.cookie('choose2ans',ans);  
                window.location.href = 'choose2.jsp?fenshu=' + GetQueryString("fenshu");
            }
            if (i <= 100) {
                setTimeout("xh()",300)
                add(i);
                i++;
            }
        }
        /*调用xh()函数*/
        $(document).ready(function () {
            xh();
        });

        var i, j, tmp;
        tmp = "|";
        for (i = 0; i < 12; i++) {
            j = parseInt(Math.random() * (18 - 1 + 1) + 1);
            while (tmp.indexOf("|" + j + "|") != -1) {
                j = parseInt(Math.random() * (18 - 1 + 1) + 1);
            }
            tmp += j + "|";
            ans.push(j);
            document.write("<div class='img_box'><ul><li><img src='images/one/2/" + j + ".png'></li></ul></div>");
        }

        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
    </script>
</body>
</html>
