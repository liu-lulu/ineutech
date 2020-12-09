var deviceDataId=0;
var userIddeviceDataId=0;
var pageSize=100;
var times=new Date();
var intervalIds;
var shefenId;
$(function () {                                                                     
    $(document).ready(function() { 
    	window.slots = {}, window.c = document.getElementById('loadingProgress'), window.ctx = c
    	.getContext('2d'), window.ctx2 = document.getElementById('loadingProgress2')
    	.getContext('2d');
    	
    	
//    	spline1("containerChatDiv"); 
    	initSpline1();
    	if($("#firstShefenId").val()==undefined){
    		initUserDataChat("18FE349EA886");
    	}else{
    		initUserDataChat($("#firstShefenId").val());
    	}
    	
    	deviceshowchatbyalluser();////查询所有学生信息
    });                                                                             
                                                                                    
});
function userClick(){
	initUserDataChat($("#tempId1").val());
}
//学生六个波
function initUserDataChat(shefenIds){
	try {
		shefenId=shefenIds
		userIddeviceDataId=0;
		if(intervalIds){
			clearInterval(intervalIds);	
		}
		
		jQuery.ajax({
	        url: "../device/deviceshowchatbyuser.htm",   // 提交的页面
	        data:{"deviceDataId" : userIddeviceDataId,"pageSize":pageSize,"shefenId":shefenId}, // 从表单中获取数据
	        type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	        beforeSubmit: function()          // 设置表单提交前方法
	        {
	        	pingLuning2=true;
	        },
	        error: function(request) {      // 设置表单提交出错
	        	pingLuning2=false;
//	            alert("提交出错，请稍候再试");
	        	var delta=new Array();
        		var theta=new Array();
        		var lowAlpha=new Array();
        		var highAlpha=new Array();
        		var lowBeta=new Array();
        		var highBeta=new Array();
        		var attention=new Array();
        		var meditation=new Array();
        		initDatas(delta, theta, lowAlpha, highAlpha, lowBeta, highBeta,attention,meditation);
	        },
	        success: function(data) {
	        	pingLuning2=false;
	        	try {
	        		var delta=[];
	        		var theta=[];
	        		var lowAlpha=[];
	        		var highAlpha=[];
	        		var lowBeta=[];
	        		var highBeta=[];
	        		var attention=[];
	        		var meditation=[];
	        		if(data){
	        			if(data.deviceDatas){
	        				var ttt;
	        				var i=0;
	        				if(data.deviceDatas.length>20){
	        					i=data.deviceDatas.length-20;
	        				}
	        				for(;i<data.deviceDatas.length;i++){
	        					ttt=new Date(data.deviceDatas[i].createTime).getTime();
	        					if(i==0){
	        						if(new Date().getTime()-ttt>5*60*1000){
	        							userIddeviceDataId=data.deviceDatas[data.deviceDatas.length-1].deviceDataId;
	        							break;
	        						}
	        					}
	        					delta.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].delta                                       
	        			        });
	        					theta.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].theta                                       
	        			        });
	        					lowAlpha.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].lowAlpha                                       
	        			        });
	        					highAlpha.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].highAlpha                                       
	        			        });
	        					lowBeta.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].lowBeta                                       
	        			        });
	        					highBeta.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].highBeta                                       
	        			        });
	        					attention.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].attention                                       
	        			        });
	        					meditation.push({                                                 
	        			        	x: ttt,                                     
	        			            y: data.deviceDatas[i].meditation                                       
	        			        });
	        					userIddeviceDataId=data.deviceDatas[i].deviceDataId;
	        				}
	        			}
	        		}
	        		initDatas(delta, theta, lowAlpha, highAlpha, lowBeta, highBeta,attention,meditation);
				} catch (e) {
				}
	        }
	    });
		
		
		
	} catch (e) {
		// TODO: handle exception
	}
}
//数据组装
function initDatas(d1,d2,d3,d4,d5,d6,d7,d8){
	var time = times.getTime(),                              
    i;  
	if(d1.length==0){
		for (i = d1.length; i<pageSize; i++) {                                    
			   d1.push({                                                 
		    	x: time-((pageSize-i)*1000),                                     
		        y: 0//Math.random()                                        
		    });
			} 
	}
	if(d2.length==0){
		for (i = d2.length; i<pageSize; i++) {                                    
			   d2.push({                                                 
		    	x: time-((pageSize-i)*1000),                                     
		        y: 0//Math.random()                                        
		    });
			} 
	}
	if(d3.length==0){
		 for (i = d3.length; i<pageSize; i++) {                                    
			   d3.push({                                                 
		    	x: time-((pageSize-i)*1000),                                     
		        y: 0//Math.random()                                        
		    });
			} 
	}
   if(d4.length==0){
	   for (i = d4.length; i<pageSize; i++) {                                    
		   d4.push({                                                 
	    	x: time-((pageSize-i)*1000),                                     
	        y: 0//Math.random()                                        
	    });
		}  
   }
   if(d5.length==0){
	   for (i = d5.length; i<pageSize; i++) {                                    
		   d5.push({                                                 
	    	x: time-((pageSize-i)*1000),                                     
	        y: 0//Math.random()                                        
	    });
		}  
   }
   if(d6.length==0){
	   for (i = d6.length; i<pageSize; i++) {                                    
		   d6.push({                                                 
	    	x: time-((pageSize-i)*1000),                                     
	        y: 0//Math.random()                                        
	    });
		} 
   }
   if(d7.length==0){
	   for (i = d7.length; i<pageSize; i++) {                                    
		   d7.push({                                                 
	    	x: time-((pageSize-i)*1000),                                     
	        y: 0//Math.random()                                        
	    });
		} 
   }
   if(d8.length==0){
	   for (i = d8.length; i<pageSize; i++) {                                    
		   d8.push({                                                 
	    	x: time-((pageSize-i)*1000),                                     
	        y: 0//Math.random()                                        
	    });
		} 
   }
   
   
	spline2("containerUser", d1,d2,d3,d4,d5,d6,d7,d8);
}

var chartCurr;
var pingLuning=false;
/**
 * 放松度,专注度数据
 */
function avgChatData(chart1){
	chartCurr=chart1;
	if(pingLuning==true){
		return;
	}
	jQuery.ajax({
        url: "../device/deviceshowchat.htm",   // 提交的页面
        data:{"deviceDataId" : deviceDataId,"pageSize":pageSize}, // 从表单中获取数据
        type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
        beforeSubmit: function()          // 设置表单提交前方法
        {
        	pingLuning=true;
        },
        error: function(request) {      // 设置表单提交出错
        	pingLuning=false;
//            alert("提交出错，请稍候再试");
        },
        success: function(data) {
        	pingLuning=false;
        	try {
        		if(data){
        			if(data.deviceDatas){
        				var ttt;
        				var i=0;
        				if(data.deviceDatas.length>20){
        					i=data.deviceDatas.length-20;
        				}
        				for(var i=0;i<data.deviceDatas.length;i++){
        					ttt=new Date(data.deviceDatas[i].createTime).getTime();
        					chartCurr.series[0].addPoint([ttt,data.deviceDatas[i].attention ], true, true);
        					chartCurr.series[1].addPoint([ttt,data.deviceDatas[i].meditation], true, true);
        					deviceDataId=data.deviceDatas[i].deviceDataId;
        				}
        				if(data.deviceDatas.length>0){
        					loadingProgressAttention(data.deviceDatas[data.deviceDatas.length-1].attention);
        					loadingProgressMeditation(data.deviceDatas[data.deviceDatas.length-1].meditation);
        				}
        				if(data.deviceDataUsers){
                            for(var i=0;i<data.deviceDataUsers.length;i++){
                            	$("#delta_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].delta );
                            	$("#theta_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].theta );
                            	$("#low_alpha_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].lowAlpha );
                            	$("#high_alpha_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].highAlpha );
                            	$("#low_beta_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].lowBeta );
                            	$("#high_beta_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].highBeta );
                            	$("#attention_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].attention );
                            	$("#meditation_"+data.deviceDataUsers[i].device.shefenId).html(data.deviceDataUsers[i].meditation ); 
                            }
	        			}
        			}
        		}
        		
			} catch (e) {
			}
        }
    });
}

function initSpline1(){
	jQuery.ajax({
        url: "../device/deviceshowchat.htm",   // 提交的页面
        data:{"deviceDataId" : deviceDataId,"pageSize":pageSize}, // 从表单中获取数据
        type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
        beforeSubmit: function()          // 设置表单提交前方法
        {
        	pingLuning=true;
        },
        error: function(request) {      // 设置表单提交出错
        	pingLuning=false;
//            alert("提交出错，请稍候再试");
        	var arr1=[],arr2=[];
        	initttt(arr1, arr2);
        },
        success: function(data) {
        	pingLuning=false;
        	try {
        		var arr1=[],arr2=[];
        		if(data){
        			if(data.deviceDatas){
        				var ttt;
        				var i=0;
        				if(data.deviceDatas.length>20){
        					i=data.deviceDatas.length-20;
        				}
        				for(;i<data.deviceDatas.length;i++){
        					ttt=new Date(data.deviceDatas[i].createTime).getTime();
        					deviceDataId=data.deviceDatas[i].deviceDataId;
        					if(i==0){
        						if(new Date().getTime()-ttt>5*60*1000){
        							deviceDataId=data.deviceDatas[data.deviceDatas.length-1].deviceDataId
        							break;
        						}
        					}
        					arr1.push({                                                 
        			        	x: ttt,                                     
        			            y: data.deviceDatas[i].attention                                       
        			        });
        					arr2.push({                                                 
        			        	x: ttt,                                     
        			            y: data.deviceDatas[i].meditation                                       
        			        });
        					
        				}
        				if(data.deviceDatas.length>0){
        					loadingProgressAttention(data.deviceDatas[data.deviceDatas.length-1].attention);
        					loadingProgressMeditation(data.deviceDatas[data.deviceDatas.length-1].meditation);
        				}
        			}
        		}
        		initttt(arr1, arr2);
			} catch (e) {
			}
        }
    });
}
function initttt(arr1,arr2){
	if(arr1.length<20){
		arr1=(function() {                                                 
	        // generate an array of random data                             
	        var data = [],                                                  
	            time = times.getTime(),                              
	            i;                                                          
	                                                                        
	        for (i = pageSize; i>=0; i--) {                                    
	            data.push({                                                 
	            	x: time-(i*1000),                                     
	                y: 0//Math.random()                                        
	            });                                                         
	        }                                                               
	        return data;                                                    
	    })() ;
		arr2=arr1;
	}
	  
	spline1("containerChatDiv",arr1,arr2);   
}


//查询所有学生信息
function deviceshowchatbyalluser(){
	try {
		jQuery.ajax({
	        url: "../device/deviceshowchatbyalluser.htm",   // 提交的页面
	        data:{}, // 从表单中获取数据
	        type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	        beforeSubmit: function()          // 设置表单提交前方法
	        {
//	        	pingLuning2=true;
	        },
	        error: function(request) {      // 设置表单提交出错
//	        	pingLuning2=false;
	        },
	        success: function(data) {
//	        	pingLuning2=false;
	        	try {
	        		var delta=[];
	        		var theta=[];
	        		var lowAlpha=[];
	        		var highAlpha=[];
	        		var lowBeta=[];
	        		var highBeta=[];
	        		if(data){
	        			if(data.deviceDataVOs){
                            for(var i=0;i<data.deviceDataVOs.length;i++){
                            	$("#delta_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].delta );
                            	$("#theta_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].theta );
                            	$("#low_alpha_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].lowAlpha );
                            	$("#high_alpha_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].highAlpha );
                            	$("#low_beta_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].lowBeta );
                            	$("#high_beta_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].highBeta );
                            	$("#attention_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].attention );
                            	$("#meditation_"+data.deviceDataVOs[i].shefenId).html(data.deviceDataVOs[i].meditation ); 
                            }
	        			}
	        		}
				} catch (e) {
				}
	        }
	    });
		
		
		
	} catch (e) {
		// TODO: handle exception
	}
}



var chartCurr2;
var pingLuning2=false;
/**
 * 六个波数据
 */
function userChatData(chart1){
	chartCurr2=chart1;
	if(pingLuning2==true){
		return;
	}
	jQuery.ajax({
        url: "../device/deviceshowchatbyuser.htm",   // 提交的页面
        data:{"deviceDataId" : userIddeviceDataId,"pageSize":pageSize,"shefenId":shefenId}, // 从表单中获取数据
        type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
        beforeSubmit: function()          // 设置表单提交前方法
        {
        	pingLuning2=true;
        },
        error: function(request) {      // 设置表单提交出错
        	pingLuning2=false;
//            alert("提交出错，请稍候再试");
        },
        success: function(data) {
        	pingLuning2=false;
        	try {
        		if(data){
        			if(data.deviceDatas){
        				var ttt;
        				var i=0;
        				if(data.deviceDatas.length>20){
        					i=data.deviceDatas.length-20;
        				}
        				for(var i=0;i<data.deviceDatas.length;i++){
        					ttt=new Date(data.deviceDatas[i].createTime).getTime();
        					chartCurr2.series[0].addPoint([ttt,data.deviceDatas[i].delta], true, true);
        					chartCurr2.series[1].addPoint([ttt,data.deviceDatas[i].theta ], true, true);
        					chartCurr2.series[2].addPoint([ttt,data.deviceDatas[i].lowAlpha], true, true);
        					chartCurr2.series[3].addPoint([ttt,data.deviceDatas[i].highAlpha], true, true);
        					chartCurr2.series[4].addPoint([ttt,data.deviceDatas[i].lowBeta], true, true);
        					chartCurr2.series[5].addPoint([ttt,data.deviceDatas[i].highBeta], true, true);
                                                chartCurr2.series[6].addPoint([ttt,data.deviceDatas[i].attention], true, true);
                                                chartCurr2.series[7].addPoint([ttt,data.deviceDatas[i].meditation], true, true);
        					userIddeviceDataId=data.deviceDatas[i].deviceDataId;
        				}
        			}
        		}
        		
			} catch (e) {
			}
        }
    });
}

function spline1(renderTo,arr1,arr2){
	    var chart1 = new Highcharts.Chart({                                                
            chart: {   
            	renderTo: renderTo,  
                type: 'area',    //spline  area                                                                               
                animation: Highcharts.svg, // don't animate in old IE               
                marginRight: 10,                                                    
                events: {                                                           
                    load: function() {                                              
                                                                                    
                        // set up the updating of the chart each second             
                        var series = this.series[0];  
                        var series1 = this.series[1];  
                        var tt=this;
                        setInterval(function() {    
                        	avgChatData(tt);
//                            var x = (new Date()).getTime(), // current time         
//                                y = Math.random();                                  
//                            series.addPoint([x, y], true, true);
//                            series1.addPoint([x, y+2], true, true);                  	               	
                        }, 2000);                                                   
                    }                                                               
                }                                                                   
            },                                                                      
            title: {                                                                
                text: null                                            
            },                                                                      
            xAxis: {                                                                
//                type: 'datetime',                                                   
                tickPixelInterval: 1  //tickPixelInterval: 150  
                ,
                labels: {//不显示x轴标签
                    enabled: false
                },
                tickLength:0//刻度长度为0
            },                                                                      
            yAxis: {                                                                
                title: {                                                            
                    text: null                                                   
                },                                                                  
                plotLines: [{                                                       
                    value: 0,                                                       
                    width: 1,                                                       
                    color: '#808080'                                                
                }]                                                                  
            },   
            credits:{                   //右边下标HighCharts.com去除  
                enabled:false  
            }, 
            tooltip: {                                                              
                formatter: function() {                                             
                        return '<b>'+ this.series.name +'</b><br/>'+                
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);
                	//return this.x + '<br>'+this.series.name+":"+ this.y +'';  
                }                                                                   
            },                                                                      
            legend: {                                                               
                enabled: true                                                      
            },                                                                      
            exporting: {                                                            
                enabled: false                                                      
            },
            series: [{    
            	color:"#FF6C60",
                name: 'Attention',  
                marker:{
                    enabled: false
                    //fillColor:'#ff3300'         /*数据点颜色*/
                },
                data: arr1                                                             
            },{    
            	color:"#23B7E5",
                name: 'Meditation',    
                marker: {
                    enabled: false, /*数据点是否显示*/
                    radius: 5,  /*数据点大小px*/
                    //fillColor:'#ff3300'         /*数据点颜色*/
                },
                data: arr2                                                             
            }]                                                                      
        }); 
	    return chart1;
}

//六个波
function spline2(renderTo,d1,d2,d3,d4,d5,d6,d7,d8){
    var chart1 = new Highcharts.Chart({                                                
        chart: {   
        	renderTo: renderTo,  
            type: 'area',        //spline  area                                             
            animation: Highcharts.svg, // don't animate in old IE               
            marginRight: 10,                                                    
            events: {                                                           
                load: function() {                                              
                                                                                
                    // set up the updating of the chart each second             
                    var series = this.series[0];  
                    var series1 = this.series[1];  
                    var tt=this;
                    intervalIds=setInterval(function() {    
                    	userChatData(tt);
//                        var x = (new Date()).getTime(), // current time         
//                            y = Math.random();                                  
//                        series.addPoint([x, y], true, true);
//                        series1.addPoint([x, y+2], true, true);                  	               	
                    }, 2000);                                                   
                }                                                               
            }                                                                   
        },                                                                      
        title: {                                                                
            text: null                                        
        },                                                                      
        xAxis: {                                                                
            //type: 'datetime',                                                   
            tickPixelInterval: 1  //tickPixelInterval: 150  
            ,
            labels: {//不显示x轴标签
                enabled: false
            },
            tickLength:0//刻度长度为0
        },                                                                      
        yAxis: {                                                                
            title: {                                                            
                text: null                                                 
            },                                                                  
            plotLines: [{                                                       
                value: 0,                                                       
                width: 1,                                                       
                color: '#808080'                                                
            }]                                                                  
        },   
        credits:{                   //右边下标HighCharts.com去除  
            enabled:false  
        }, 
        tooltip: {                                                              
            formatter: function() {                                             
                    return '<b>'+ this.series.name +'</b><br/>'+                
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                    Highcharts.numberFormat(this.y, 2);
            	//return this.x + '<br>'+this.series.name+":"+ this.y +'';  
            }                                                                   
        },                                                                      
        legend: {                                                               
            enabled: true                                                      
        },                                                                      
        exporting: {                                                            
            enabled: false                                                      
        }, 
        plotOptions: {
            area: {
                fillOpacity: 0.3
            }
        },
        series: [{     
        	color:"#FF6C60",
            name: 'Delta',  
            marker:{
                enabled: false
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data:d1                                                               
        },{         
        	color:"#23B7E5",
            name: 'Theta',    
            marker: {
                enabled: false, /*数据点是否显示*/
                radius: 5,  /*数据点大小px*/
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data: d2                                                              
        },{    
        	color:"#FF6C60",
            name: 'Low Alpha',    
            marker: {
                enabled: false, /*数据点是否显示*/
                radius: 5,  /*数据点大小px*/
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data: d3                                                              
        },{    
        	color:"#FCB322",                                                         
            name: 'Hight Alpha',    
            marker: {
                enabled: false, /*数据点是否显示*/
                radius: 5,  /*数据点大小px*/
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data: d4                                                              
        },{   
        	color:"#39B2A9",
            name: 'Low beta',    
            marker: {
                enabled: false, /*数据点是否显示*/
                radius: 5,  /*数据点大小px*/
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data: d5                                                              
        },{  
        	color:"#caa3da",
            name: 'Hight beta',    
            marker: {
                enabled: false, /*数据点是否显示*/
                radius: 5,  /*数据点大小px*/
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data: d6                                                              
        },{  
        	//color:"#caa3da",
            name: 'Attention',    
            marker: {
                enabled: false, /*数据点是否显示*/
                radius: 5,  /*数据点大小px*/
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data: d7                                                             
        },{  
        	//color:"#caa3da",
            name: 'Meditation',    
            marker: {
                enabled: false, /*数据点是否显示*/
                radius: 5,  /*数据点大小px*/
                //fillColor:'#ff3300'         /*数据点颜色*/
            },
            data: d8                                                              
        }]                                                                      
    }); 
    return chart1;
}

