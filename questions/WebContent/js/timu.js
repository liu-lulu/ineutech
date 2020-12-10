// JavaScript Document
 <script type="text/javascript"> 
	var i=0;
	/*add——创建tbx下的div加文字和变宽度的方法*/
	function add(i){
		var tbox =$(".tbox");
		var tiao =$(".tiao");
		tiao.css("width",i+"%").html(i+"%");
		}
	function xh(){
		if(i>100){
			window.location.href='choose1.jsp';
			}
		if(i<=100){
			setTimeout("xh()",100)
			add(i);
		    i++;
			}
		}
    /*调用xh()函数*/
	$(document).ready(function(){	
		xh();
	});
	
	var i,j,tmp;
	tmp="|";
	for(i=0;i<12;i++){
		j=parseInt(Math.random()*(20-1+1)+1);
		while(tmp.indexOf("|"+j+"|")!=-1){
			j=parseInt(Math.random()*(20-1+1)+1);
		}
		tmp+=j+"|";
		document.write("<div class='img_box' style='maegin-top:50px;'><ul><li><img src='images/0"+j+".png'></li></ul></div>");
}




	$(".container ul li").click(function() {
	$(this).addClass("bor").siblings().removeClass("bor");
	});
	var arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15,15,17,18,19,20];
	var count = 0;
	//随机显示照片
	function changeImg() {
	$("#choose_img img").attr("src", 'images/0' + arr[Math.floor(Math.random() * arr.length)] + '.png');
	}
	//setTimeout("changeImg();", 3000);
	//选择对错
	$(".true").click(function() {
	 if(count>=5)
		{
			//alert("答题结束！");
			//return;
			window.location.href='question2.jsp';
		}
		++count;
	//调动图片随机出函数
	changeImg()
	$(this).addClass("C_true");
	$(".false").removeClass("C_false")
	});
	$(".false").click(function() {
			 if(count>=5)
		{
			//alert("答题结束！");
			//return;
			window.location.href='question2.jsp';
		}
		++count;
	//调动图片随机出函数
	changeImg()
	$(this).addClass("C_false");
	$(".true").removeClass("C_true");
	});
</script> 