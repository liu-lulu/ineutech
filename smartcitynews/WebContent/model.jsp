<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="img/fudan.jpg" mce_href="images/icon.png" type="image/x-icon">
<title>动态发布</title>
<meta name="viewport" content="width=device-width">
	
<link rel="stylesheet" type="text/css" href="css/style.css" /> 		

<script type="text/javascript" src="js/redactor/jquery-1.7.min.js"></script>	

<link rel="stylesheet" href="css/redactor/redactor.css" />
<script src="js/redactor/redactor.js"></script>

<script>
function toadd(){
	var content=$('#redactor_content').val();
	console.log("aa:"+content);
	$.ajax({
		  type: 'POST',
		  async: false,
		  url: 'add.do',
		  data: {
			  'title': $('#title').val(),
			  'img_url': $('#img_url').val(),
			  'content': content
				},
		success : function(data) {
			window.location.href="newslist.do";

		},
		dataType : 'json'
	});
}
$(document).ready(
		function()
		{
			$('#redactor_content').redactor();
		}
	);

</script>
</head>
<body>
<div id="page" style="width: 960px;margin-left: 10%;">
<form action="add.do" method="post" target="_blank">
标题:<input type="text" id="title" name="title" size="100"><br><br>
图片:<input type="text" id="img_url" name="img_url">&nbsp;&nbsp;<label style="font-size: 1px;color: gray;">备注:图片名，例:1.jpg</label><br><br>
<textarea id="redactor_content" name="content" style="height: 560px;width: 100%; display: none;"></textarea>
<label style="font-size: 1px;color: gray;">
<br>备注:
<br>插入图片:需输入图片的完整的URL,例：http://www.fdsm.fudan.edu.cn/smartcitynews/img/detail/2017072904.jpg

<br>插入视频:Video embed code输入以下代码。其中,将src中的https://www.fdsm.fudan.edu.cn/smartcitynews/img/detail/2019101519.mp4改为自己的视频地址<xmp><iframe width="600" height="315" src="https://www.fdsm.fudan.edu.cn/smartcitynews/img/detail/2019101519.mp4" frameborder="0" allowfullscreen=""></iframe></xmp>


</label>
<br><br>
<input type="button" onclick="toadd()" value="确认">
</form>
</div>
</body>
</html>