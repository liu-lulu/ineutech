var answer = [3,3,5,6,1,2,6,1,5,3,2,6,4,4,2,5,1,6,3,6,4,5,2,1,4,3,6,2,1,6,1,6,2,4,5,3,5,2,1,6,4];

var num=1;

function xh(){
	if(num>41){
		window.location.href='choose4.jsp';
		}
	if(num<=41){
		add(num);
		
		}
	}

function add(i){
	$(".palyer h2").text("2-"+num);//题号
	
	//$(".palyer audio source").attr("src", 'images/two/question2MP3.mp3');//音频
	
	var titleImg =$(".question_img img");//题目图片
	titleImg.attr("src", 'images/two/'+num+'/0' + i + '.png');
	
	for(i=1;i<=6;i++){//选择项图片
		var selectImg =$("#"+i+" img");
		selectImg.attr("src", 'images/two/'+num+'/' + i + '.png');
	}
	}
/*调用xh()函数*/
$(document).ready(function(){	
	xh();
});

$(".next").click(function() {
	
	 var ansdata = {
 			dimension1:"第二大题",
 			dimension2:"第"+num+"小题",
 			que_no:num,
 			true_answer: answer[num-1],
 			answers:$("#selectAnswer").val(),
 			score:(answer[num-1]==$("#selectAnswer").val())?1:0
     	};
 
	 $.ajax({
			type: "POST",
			url: "addAnswerOneanswer.action",
			data:ansdata,
			async: false,
			success: function(msg){	
			$(".choose_one ul").find("li").removeClass("bor");
			$("#selectAnswer").val("");
			num++;
			//调动图片随机出函数
			xh();
			$(this).addClass("C_true");
			$(".false").removeClass("C_false")
			}			
		});
	
});

