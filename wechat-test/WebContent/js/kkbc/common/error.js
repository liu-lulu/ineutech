   var intDiff = parseInt(10);//倒计时总秒数量
    $(function(){
    	 timer(intDiff);
    }); 
    
    function timer(intDiff){
        window.setInterval(function(){
          if(intDiff<0){
        	  window.location.href=window.localDomain+"";
          }else{
        	  $("#totalSecond").html(intDiff);
              intDiff--;
          }
        }, 1000);
    } 