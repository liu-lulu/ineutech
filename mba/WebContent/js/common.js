$(function(){
	//表格行，鼠标放上去变色
	$(".tr:odd").css("background", "#FFFCEA");
	$(".tr:odd").each(function(){
		$(this).hover(function(){
			$(this).css("background-color", "#FFE1FF");
		}, function(){
			$(this).css("background-color", "#FFFCEA");
		});
	});
	$(".tr:even").each(function(){
		$(this).hover(function(){
			$(this).css("background-color", "#FFE1FF");
		}, function(){
			$(this).css("background-color", "#fff");
		});
	}); 

	/*ie6,7下拉框美化*/
    if ( $.browser.msie ){
    	if($.browser.version == '7.0' || $.browser.version == '6.0'){
    		$('.select').each(function(i){
			   $(this).parents('.select_border,.select_containers').width($(this).width()+5); 
			 });
    		
    	}
    }
 
    $(".sideMenu h3,.sideMenu ul li a").click(function(e) {
    	$("#loadingPop").show();
		var obj=$(this).attr("attr-href");
		if(obj){
			var tagsd=$("#tagsd").val();
			if(tagsd){
				obj=obj+"?tag="+tagsd;
			}			
			var startTime=$("#startTime").val();
			var endTime=$("#endTime").val();
			var parametersd="";
			if(startTime){
				parametersd="startTime="+startTime;
			}
			if(endTime){
				if(startTime){
					parametersd+="&";
				}
				parametersd+="endTime="+endTime;
			}
			if(parametersd!=""){
				if(obj.indexOf("?tag=")>0){
					parametersd="&"+parametersd;	
				}else{
					parametersd="?"+parametersd;
				}
			}
			window.location.href=obj+parametersd;
		}
	});
      var defaultSideMenuIndex=parseInt($("#currentLeftIndex").attr("attr-value"));
      $(".sideMenu").slide({//菜单
         titCell:"h3", 
         targetCell:"ul",
         defaultIndex:defaultSideMenuIndex, 
         effect:'slideDown', 
         delayTime:'150' , 
         trigger:'click', 
         triggerTime:'150', 
         defaultPlay:true, 
         returnDefault:false,
         easing:'easeInQuint',
         endFun:function(){
              scrollWW();
         }
       });
      $(window).resize(function() {
          scrollWW();
      });
	  
  
});
//菜单
function scrollWW(){
    if($(".side").height()<$(".sideMenu").height()){
       $(".scroll").show();
       var pos = $(".sideMenu ul:visible").position().top-38;
       $('.sideMenu').animate({top:-pos});
    }else{
       $(".scroll").hide();
       $('.sideMenu').animate({top:0});
       n=1;
    }
  } 

var n=1;
function menuScroll(num){
  var Scroll = $('.sideMenu');
  var ScrollP = $('.sideMenu').position();
  /*alert(n);
  alert(ScrollP.top);*/
  if(num==1){
     Scroll.animate({top:ScrollP.top-38});
     n = n+1;
  }else{
    if (ScrollP.top > -38 && ScrollP.top != 0) { ScrollP.top = -38; }
    if (ScrollP.top<0) {
      Scroll.animate({top:38+ScrollP.top});
    }else{
      n=1;
    }
    if(n>1){
      n = n-1;
    }
  }
}