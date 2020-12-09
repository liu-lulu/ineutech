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
	width:15%;
	}
	
	.weui-desktop-table tr td:nth-child(2){
	width:50%;
	font-size: 20px;
	}
	
	.weui-desktop-table tr td:nth-child(2) a{
	color: #2d2d2d;
	} 
	
	.weui-desktop-table tr td:nth-child(3){
	width:20%;
	text-align:right;
	font-size: 20px;
	}
	
	.weui-desktop-table tr td:nth-child(4){
	width:15%;
	text-align: right;
	}
	.weui-desktop-table td {
    /* border-bottom: 1px solid #E4E8EB; */
	}


.profile_container {
    width: 535px;
    position: absolute;
  /*   top: 100%; */
    left: 10%;
    margin-top: 10px;
    font-size: 14px;
    *margin-top: 10px;
}

.profile_inner {
    position: relative;
    padding: 30px 22px 36px 144px;
    background-color: #fff;
    border: 1px solid #d9dadc;
    *zoom: 1;
}

.profile_nickname {
    font-size: 16px;
    font-weight: 400;
}

.profile_avatar {
    position: absolute;
    width: 100px;
    left: 24px;
    top: 24px;
    height: 100px!important;
}

.profile_meta {
    margin-top: 5px;
    overflow: hidden;
    *zoom: 1;
}

.profile_meta_label {
    float: left;
    width: 4em;
    margin-right: 1em;
}

.profile_meta_value {
    display: block;
    overflow: hidden;
    *zoom: 1;
    color: #adadad;
}
.profile_arrow_wrp {
    position: absolute;
    left: 50px;
    top: -8px;
}
.profile_arrow {
    display: inline-block;
    width: 0;
    height: 0;
    border-width: 8px;
    border-style: dashed;
    border-color: transparent;
    border-top-width: 0;
    border-bottom-color: #d9dadc;
    border-bottom-style: solid;
    position: absolute;
    top: 0;
}

.profile_arrow.arrow_in {
    margin-top: 1px;
    border-bottom-color: #fff;
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
			  url: 'morelist.do',
			  data: {
				  'pageno': parseInt($("#pageno").val())+1
					},
			success : function(data) {
				$("#pageno").val(data.pageno);
				for(var i=0;i<data.list.length;i++){
				    var url = data.list[i].url;
				    var title =data.list[i].title;
				    var image_url=data.list[i].image_url;
				    var publish_time=data.list[i].publish_time;
				    var id=data.list[i].id;
					/* $("#appmsgList").append("<tr><td><img src='img/list/"+image_url+"' style='width:180px;height:150px'></td><td><a href='"+url+"' target='_blank'>"+title+"</a></td><td style='color: #9A9A9A'>"+publish_time+"</td></tr>"); */
				    $("#appmsgList").append("<tr><td><img src='img/list/"+image_url+"' style='width:180px;height:150px'></td><td><a href='detail.do?id="+id+"' target='_blank'>"+title+"</a></td><td style='color: #9A9A9A'>"+publish_time+"</td><td><a href='toedit.do?id="+id+"' target='_blank'>修改</a></td></tr>");
				}
				if(data.list.length==10){
					$("#loadmore").html("上拉加载更多");
				}else if(data.list.length<10){
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

$(document).ready(function(){ 
	$("#showcenter").bind('click',function(e){ 
	    $('#js_profile_qrcode').show();
	    stopPropagation(e);
	}); 
	
	$(document).bind('click', function () {
	    $("#js_profile_qrcode").hide();
	});
});
function stopPropagation(e) { 
	  if (e.stopPropagation) 
	    e.stopPropagation(); 
	  else 
	    e.cancelBubble = true; 
	} 
</script>
</head>
<body>
	<div class="container container_complex" >
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
		                <!-- <a href="#" onclick="javascript:$('#modal_volume').fadeIn();" class=" account_nickname_inner">复旦智慧城市研究中心</a> -->
		                <a href="#" id="showcenter" class=" account_nickname_inner">复旦智慧城市研究中心</a>
		                
		                <div id="js_profile_qrcode" class="profile_container" style="display:none;">
                          <div class="profile_inner">
                              <strong class="profile_nickname">复旦智慧城市研究中心</strong>
                              <img class="profile_avatar" id="js_profile_qrcode_img" src="img/qrcode.dib" alt="">

                              <p class="profile_meta">
                              <label class="profile_meta_label">微信号</label>
                              <span class="profile_meta_value">FudanSmartCity</span>
                              </p>

                              <p class="profile_meta">
                              <label class="profile_meta_label">功能介绍</label>
                              <span class="profile_meta_value">探讨智慧城市的发展及运营问题，共同创造美好智慧生活</span>
                              </p>
                              
                          </div>
                          <span class="profile_arrow_wrp" id="js_profile_arrow_wrp">
                              <i class="profile_arrow arrow_out"></i>
                              <i class="profile_arrow arrow_in"></i>
                          </span>
       					</div>
		                
		            </div>
		        </div>
		        
		        
		        
		    </div>
		    

		</div>
    	</div>
    	
    	<div id="modal_volume" style="background: rgba(0, 0, 0, 0.5);position: fixed; text-align: center; width: 100%; height: 100%; top: 0; z-index: 9999; display: none;">
        <a href="#" onclick="javascript:$('#modal_volume').fadeOut();">
        <table style="width: 100%; height: 100%;">
            <tr>
                <td align="center" style="">
                    <div style="width: 900px; position: relative">
                           <img src="img/wechatqrcode.jpg" />
                    </div>
                </td>
            </tr>
        </table>
        </a>
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
		        		<img src="img/list/${data.image_url}" style="width:180px;height:150px">
		                </td>
		                <td>
		        			<%-- <a href="${data.url}" target="_blank">${data.title}</a> --%>
		        			<a href="detail.do?id=${data.id}" target="_blank">${data.title}</a>
		        		</td>
		        		<td style="color: #9A9A9A">${data.publish_time}</td>
		        		<td><a href="toedit.do?id=${data.id}" target="_blank">修改</a></td>
		                </tr>
					</c:forEach>
		    	</table>
		        
		    </div>
		</div>
		
		<div id="loadmore" style="text-align: center;">
			<c:if test="${list!= null && fn:length(list) == 10}">上拉加载更多</c:if>
			<c:if test="${list== null || fn:length(list) < 10}">没有更多了</c:if>
		</div>
		<input type="hidden" id="pageno" value="${pageno}">
		<br>
	</div>

</body>
</html>