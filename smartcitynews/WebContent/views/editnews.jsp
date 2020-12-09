<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="img/fudan.jpg" mce_href="images/icon.png" type="image/x-icon">
<title>动态修改</title>
<meta name="viewport" content="width=device-width">
	
<link rel="stylesheet" type="text/css" href="css/style.css" /> 		

<script type="text/javascript" src="js/redactor/jquery-1.7.min.js"></script>	

<link rel="stylesheet" href="css/redactor/redactor.css" />
<script src="js/redactor/redactor.js"></script>

<script>

$(document).ready(
		function()
		{
			$('#redactor_content').redactor();
			$('#redactor_content').val($('#newscontent').html());
		}
	);

</script>
</head>
<body>
<div style="width: 960px;margin-left: 10%;">
<form action="edit.do" method="post" target="_blank">
标题:<input type="text" id="title" name="title" value="${info.title}" size="100"><br><br>
图片:<input type="text" id="img_url" name="img_url" value="${info.image_url}">&nbsp;&nbsp;<label style="font-size: 1px;color: gray;">备注:图片名，例:1.jpg</label><br><br>
<textarea id="redactor_content" name="content" style="height: 560px;width: 100%; display: none;"></textarea>

<label style="font-size: 1px;color: gray;">
<br>备注:
<br>插入图片:需输入图片的完整的URL,例：http://www.fdsm.fudan.edu.cn/smartcitynews/img/detail/2017072904.jpg
<!-- 
<br>插入视频:其中,将src中的http://v.qq.com/iframe/player.html?vid=d0163kxz8di改为自己的视频地址<xmp><iframe class="video_iframe" style="z-index:1;" src="http://v.qq.com/iframe/player.html?vid=d0163kxz8di&amp;width=500&amp;height=375&amp;auto=0" frameborder="0" height="375" width="500"></iframe></xmp>
 -->
</label>
<input type="hidden" id="id" name="id" value="${info.id}">

<br><br>
<input type="submit"  value="修改">
</form>
<div style="display:none" id="newscontent" >${info.content}</div>
</div>
</body>
</html>