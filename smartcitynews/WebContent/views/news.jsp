<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="img/fudan.jpg" mce_href="images/icon.png" type="image/x-icon">
<title>中心动态</title>
<link rel="stylesheet" href="homepage_files/index42f400.css">
<link rel="stylesheet" type="text/css" href="homepage_files/video433882.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<style type="text/css">

	.weui-desktop-table {
    width: 100%;
    border-collapse: collapse;
    text-align: left;
	}
	.weui-desktop-table th, .weui-desktop-table td {
	    padding: 18.8px 10px;
	    word-break: break-all;
	    font-size: 15px;
	}
	
	.weui-desktop-table tr td:nth-child(1){
	width:20%;
	}
	
	.weui-desktop-table tr td:nth-child(2){
	width:65%;
	}
	
	.weui-desktop-table tr td:nth-child(3){
	width:15%;
	text-align:right;
	}
	.weui-desktop-table td {
    /* border-bottom: 1px solid #E4E8EB; */
	}


</style>
<script type="text/javascript">

//滚动条到页面底部加载更多案例 
$(window).scroll(function(){
	if($("#appmsgList").find("tr").length % 10!=0){
		return;
	}

	var scrollTop = $(this).scrollTop();    //滚动条距离顶部的高度
	var scrollHeight = $(document).height();   //当前页面的总高度
	var clientHeight = $(this).height();    //当前可视的页面高度
	// console.log("top:"+scrollTop+",doc:"+scrollHeight+",client:"+clientHeight);
	if(scrollTop + clientHeight  >= scrollHeight){   //距离顶部+当前高度 >=文档总高度 即代表滑动到底部 
		//滚动条到达底部
		$.ajax({
			  type: 'POST',
			  async: false,
			  url: 'more.do',
			  data: {
				  'pageno': parseInt($("#pageno").val())+1
					},
			success : function(data) {
				$("#pageno").val(data.pageno);
				for(var key in data.list){
					var image_url=data.list[key][0].image_url;
					var time = key;
					var content='';
					
					for(var i=0;i<data.list[key].length;i++){
						var title =data.list[key][i].title;
					    var url=data.list[key][i].url;
					    content+="<a href='"+url+"' target='_blank'>"+(i+1)+"."+title+"</a><br>";
					}
					
					$("#appmsgList").append("<tr><td><img src='img/"+image_url+"' style='width:235px;height:100px'></td><td>"+content+"</td><td style='color: #9A9A9A'>"+time+"</td></tr>");
				}
				if(Object.getOwnPropertyNames(data.list).length==10){
					$("#loadmore").html("上拉加载更多");
				}else if(Object.getOwnPropertyNames(data.list).length<10){
					$("#loadmore").html("没有更多了");
				}

			},
			dataType : 'json'
		});
	}else if(scrollTop<=0){
	//滚动条到达顶部
	//滚动条距离顶部的高度小于等于0 TODO
	
	}
});

</script>
</head>
<body>
	<div class="container container_complex">
		<div style="margin-left:10%;">
		<div class="articles_header">
		    
		    <h2 class="rich_media_title">复旦智慧城市研究中心</h2>
		    <div id="h5_profile_btn" class="flex_context account_info">
		        <div class="flex_hd">
		            <span class="js_go_profile radius_avatar account_avatar">
						<img class="account_avatar" src="img/fudan.jpg" style="-webkit-touch-callout: none; -webkit-user-select: none;">
		            </span>
		        </div>
		        <div class="flex_bd">
		            <div>
		                <a href="#" class=" account_nickname_inner">复旦智慧城市研究中心</a>
		            </div>
		        </div>
		        
		    </div>
		</div>
    	</div>
    	
    	<!-- <div class="slider" id="slider" style="margin-left:10%;width: 80%;">   
	    <div class="swiper jsSwiper " style="float: left;width: 100%; height: 350px;">
	        <div class="item jsItem js_post" style="width: 100%; height: 350px;">
	            <a href="http://mp.weixin.qq.com/s?__biz=MzA3NTAyODUxOQ==&amp;mid=2450165160&amp;idx=1&amp;sn=b0abc3820ec59ce6f3f85fdc2cf6f5fa&amp;scene=19#wechat_redirect" target="_blank">
	                <div class="img js_img" style="background-image: url('img/qidong_1626x316.jpg');height:300px;" ></div>
	                <p class="desc js_title">活动回顾 | “智慧让城市更精彩”主题研讨会暨中心市北高新办公室启用仪式圆满举行</p>
	            </a>
	        </div>
	        
	        <div style="width: 10px; box-sizing: border-box; margin-left: 10px; flex-shrink: 0;" class="space"></div> 
	    </div>
	    <div style="clear: both;"></div>
		</div> -->
		
		<div class="tab" style="width:80%;margin-left: 10%;margin-top: 30px;">
		    <div class="tab_hd ">
		        <div class="tab_hd_inner">
		            
		            <!-- <div class="item active jsCate">中心动态</div> -->
		            
		        </div>
		    </div>
		    <div class="tab_bd">
		    	
		    	<table class="weui-desktop-table " id="appmsgList">
		    		<c:forEach items="${list }" var="data">
		    			<tr>
		        		<td>
		        		<img src="img/${data.value[0].image_url}" style="width:235px;height:100px">
		                </td>
		                <td>
		        		<c:forEach items="${data.value }" var="smartcitynew" varStatus="id" begin="0">
		        			<a href="${smartcitynew.url}" target="_blank">${id.index+1}.${smartcitynew.title}</a><br>
		        		</c:forEach>
		        		</td>
		        		<td style="color: #9A9A9A">${data.key}</td>
		                </tr>
					</c:forEach>
		    	</table>
		        
		    </div>
		</div>
		
		<%-- <div id="loadmore" style="text-align: center;">
			<c:if test="${list!= null && fn:length(list) == 20}"><a href="javascript:void(0);" onclick="more(${pageno})">点击加载更多</a></c:if>
			<c:if test="${list== null || fn:length(list) < 20}">没有更多了</c:if>
		</div> --%>
		
		<div id="loadmore" style="text-align: center;">
			<c:if test="${list!= null && fn:length(list) == 10}">上拉加载更多</c:if>
			<c:if test="${list== null || fn:length(list) < 10}">没有更多了</c:if>
		</div>
		<input type="hidden" id="pageno" value="${pageno}">
		<br>
	</div>

</body>
</html>