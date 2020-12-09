<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="img/fudan.jpg" mce_href="images/icon.png" type="image/x-icon">
<title>复旦智慧城市研究中心</title>
<link rel="stylesheet" href="homepage_files/index42f400.css">
<link rel="stylesheet" type="text/css" href="homepage_files/video433882.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<style type="text/css">

.profile_container {
    width: 535px;
    position: absolute;
  /*   top: 100%; */
    /* left: 10%; */
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

.rich_media_content {
    overflow: hidden;
    color: #333;
    font-size: 17px;
    word-wrap: break-word;
    -webkit-hyphens: auto;
    -ms-hyphens: auto;
    hyphens: auto;
    text-align: justify;
    position: relative;
    z-index: 0;
}
</style>
<script type="text/javascript">
$(document).ready(function(){ 
	$("#showcenter").bind('click',function(e){ 
	    $('#js_profile_qrcode').show();
	    stopPropagation(e);
	}); 
	
	$(document).bind('click', function () {
	    $("#js_profile_qrcode").hide();
	});
	
});

$(window).bind("load",function(){
	$("img").each(function(){
		if($(this).attr("id")!="js_profile_qrcode_img"&$(this).width()>500){
			$(this).attr("style","width:661px;height:auto");
		}
		
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

	<div class="container container_complex">
		<div style="/* margin-left: 10%; */">
			<div class="articles_header">
				<div style="max-width: 677px; margin-left: auto; margin-right: auto;margin-bottom: 22px;">
					<h2 style="font-size: 22px; line-height: 1.4; margin-bottom: 13px;">${info.title}</h2>
					<div>
						<a href="#" id="showcenter" class=" account_nickname_inner"
							style="display: inline-block; margin-top: 3px;">复旦智慧城市研究中心</a>
						&nbsp;<label style="font-size: 15px; color: rgba(0, 0, 0, 0.3);">${info.publish_time}</label>
						
						<div id="js_profile_qrcode" class="profile_container"
							style="display: none; ">
							<div class="profile_inner">
								<strong class="profile_nickname">复旦智慧城市研究中心</strong> <img
									class="profile_avatar" id="js_profile_qrcode_img"
									src="img/qrcode.dib" alt="">

								<p class="profile_meta">
									<label class="profile_meta_label">微信号</label> <span
										class="profile_meta_value">FudanSmartCity</span>
								</p>

								<p class="profile_meta">
									<label class="profile_meta_label">功能介绍</label> <span
										class="profile_meta_value">探讨智慧城市的发展及运营问题，共同创造美好智慧生活</span>
								</p>

							</div>
							<span class="profile_arrow_wrp" id="js_profile_arrow_wrp">
								<i class="profile_arrow arrow_out"></i> <i
								class="profile_arrow arrow_in"></i>
							</span>
						</div>
					</div>

				</div>
				
				<div style="max-width: 677px; margin-left: auto; margin-right: auto;">${info.content}</div>
				
				<div style="text-align: center;color: rgb(117, 117, 118);margin-top: 50px;">— END —</div>
			</div>
		</div>
	</div>
</body>
</html>