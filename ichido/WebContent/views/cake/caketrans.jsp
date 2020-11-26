<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<link rel="shortcut icon" href="../img/icon.jpg">
<title>蛋糕交易记录</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/kkpager_blue.css" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bs4.pop.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome/css/font-awesome.min.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-table.min.css">

</head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column" style="text-align: center;">
			<h3>
				店长：<a href="#" data-toggle="modal" data-target="#updpwdModal">${cake.e_name }</a>
				<a href="toLogin.do" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-log-out"></span>退出</a>
			</h3>
		</div>
	</div>
	<div class="row clearfix">
		<form class="form-horizontal" role="form" action="transactionList.do" method="post" id="listForm" >
		<div class="col-md-12 column">
			<div class="row clearfix">
				<div class="col-md-3 col-xs-12 column">
					<select class="show-tick form-control" multiple="" title="请选择店铺" name="shopCode" id="shopCode" >
					
					<c:forEach var="shop" items="${shops }">
						<option value="${shop.shop_code }" <c:if test="${shopCode eq shop.shop_code }">selected="selected" </c:if> >${shop.shop_code }-${shop.shop_name }</option>
					</c:forEach>
				      
				    </select>
				</div>
				<div class="col-md-7 col-xs-12 column">
				
					<div class="form-group">
						<label for="beginDate" class="col-sm-2 control-label">日期</label>
						<div class="col-sm-10 input-group" style="padding:0 15px;">
						  	<input type="text" class="form-control" readonly id="beginDate" name="beginDate" <c:if test="${not empty beginDate }">value="${beginDate }"</c:if> <c:if test="${empty beginDate }">value="<fmt:formatDate value='<%= new java.util.Date() %>'  pattern='yyyy-MM-dd'/>"</c:if>>
							<span class="input-group-addon">至</span>
							<input type="text" class="form-control" readonly id="endDate" name="endDate" <c:if test="${not empty endDate }">value="${endDate }"</c:if> <c:if test="${empty endDate }">value="<fmt:formatDate value='<%= new java.util.Date() %>'  pattern='yyyy-MM-dd'/>"</c:if>>
						</div>
						
					</div>
					<input type="hidden" value="${currentPageNO}" name="currentPageNO" id="currentPageNO"/>
				</div>
				<div class="col-md-2 col-xs-12 column">
					<div class="col-md-offset-0 col-xs-offset-10">
					<input type="button" class="btn btn-primary" onclick="toVaild()" value="查看">
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
	<c:if test="${empty caketransactions && not empty shopCode}"><h4 style="text-align:center;">无交易记录</h4></c:if>
	<c:if test="${not empty caketransactions }">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th>
									工号
								</th>
								<th>
									姓名
								</th>
								<th>
									成交单数
								</th>
								<th>
									已确认单数
								</th>
								<th>
									成交金额
								</th>
								<th>
									已确认金额
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${caketransactions }" var="caket">
								<tr onclick="transDetail('${shopCode}','${caket.e_number }','${beginDate }','${endDate }')">
								<td>${caket.e_number }</td>
								<td>${caket.e_name }</td>
								<td>${caket.totalCount }</td>
								<td>${caket.confirmCount }</td>
								<%-- <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${caket.transaction_time }" /></td> --%>
								<td>${caket.totalPrice }</td>
								<td>${caket.confirmPrice }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="kkpager" style="float:right;"><jsp:include page="/common/pagebar.jsp"></jsp:include></div>
				</div>
			</div>
		</div>
	</div>
	</c:if>
<!-- 修改密码（Modal） -->
<div class="modal fade" id="updpwdModal" tabindex="-1" role="dialog" aria-labelledby="updpwdModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="updpwdModalLabel">修改密码</h4>
            </div>
            <div class="modal-body">
            	<form class="form-horizontal" id="updpwdForm" method="post">
				<div class="form-group">
				    <label for="newPwd" class="col-sm-2 control-label" >新密码<span style="color:red;">*</span></label>
				    <div class="col-sm-10">
				    <input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="请输入新密码">
				    </div>
				</div>
				<div class="form-group">
				    <label for="confirmPwd" class="col-sm-2 control-label" >确认密码<span style="color:red;">*</span></label>
				    <div class="col-sm-10">
				    <input type="password" class="form-control" id="confirmPwd" name="confirmPwd" placeholder="请重新输入密码">
				    </div>
				</div>
				</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updpwdButton">修改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 成交详情（Modal） -->
<div class="modal fade" id="transModal" tabindex="-1" role="dialog" aria-labelledby="transModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="transModalLabel">成交详情</h4>
            </div>
            <div class="modal-body">
            	<div class="form-group">
            	
            	<table id="transDetailTable" class="table-striped table-hover" style="margin-top:10px;"></table>
            	</div>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="toconfirm()">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
</div>
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/kkpager.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/paging.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bs4.pop.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-select.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-table-zh-CN.min.js"></script>
</body>
<script type="text/javascript">
Date.prototype.format = function(fmt){
	  var o = {
	    "M+" : this.getMonth()+1,                 //月份
	    "d+" : this.getDate(),                    //日
	    "h+" : this.getHours(),                   //小时
	    "m+" : this.getMinutes(),                 //分
	    "s+" : this.getSeconds(),                 //秒
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度
	    "S"  : this.getMilliseconds()             //毫秒
	  };

	  if(/(y+)/.test(fmt)){
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	  }
	        
	  for(var k in o){
	    if(new RegExp("("+ k +")").test(fmt)){
	      fmt = fmt.replace(
	        RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));  
	    }       
	  }

	  return fmt;
	}
	
function transDetail(shopCode,e_number,beginDate,endDate){
	$.ajax({
			type: "POST",
			url: "transactionDetail.do",
			data: {'e_number':e_number,'shopCode':shopCode,'beginDate':beginDate,'endDate':endDate},
			dataType:"json",
			success: function(data){
				console.log(data);
				if(data.info=='login'){
					location.href="toLogin.do";
				}else{
					$('#transModal').modal('toggle');
					$("#transDetailTable").bootstrapTable("load",data);
				}
			},
			error: function(){
				bs4pop.alert('查询详情记录失败', function(){});
				
			},
			complete: function () {  
		    }
		});
	
}
function toVaild(){
    var val = $('#shopCode').val();
    if(val!=null){
      $('#currentPageNO').val('1');
      $('#listForm').submit();
    }else{
    	bs4pop.alert('请选择店铺', function(){});
    }
}

function toconfirm(){
	var idlist = $('#transDetailTable').bootstrapTable('getAllSelections');
	if(idlist.length==0){
		bs4pop.alert('请选择交易记录', function(){});
		return;
	}
	var ids="";
	for (var i = 0; i < idlist.length; i++) {
		ids+=(idlist[i].cake_id+",");
    }
	
	ids=ids.substring(0,ids.length-1);
	confirm(ids,1);
}

function confirm(cake_id,type){
	console.log(cake_id);
	var data={'cake_id':cake_id,'type': type};
	console.log(data);
	url = "confirm.do";
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		async: true,
		dataType:"text",
		success: function(data){
			if(data=='login'){
				location.href="toLogin.do";
			}else if(data=='success'){
				bs4pop.alert('操作成功', function(){ 
					$('#listForm').submit();
					});
			}else{
				bs4pop.alert('操作失败', function(){});
			}
		},
		error: function(){
			bs4pop.alert('操作失败', function(){});
			
		},
		complete: function () {  
			
	    }
	});
	
}
function updpwdformValidator() {
	$('#updpwdForm').bootstrapValidator({

		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			newPwd : {
				validators : {
					notEmpty : {
						message : '新密码不能为空'
					},
		            identical: {
		                field: 'confirmPwd',
		                message: '新密码与确认密码不一致！'
		              },
		              stringLength: {
		            	min: 6,
		                max: 10,
		                message: '新密码长度6到10位'
		              }
				}
			},
			confirmPwd : {
				validators : {
					notEmpty : {
						message : '确认密码不能为空'
					},
		            identical: {
		                field: 'newPwd',
		                message: '新密码与确认密码不一致'
		              },
		              stringLength: {
		            	min: 6,
		                max: 10,
		                message: '确认密码长度6到10位'
		              }
				}
			}
		}
       
	});
}


$(document).ready(function(){
	updpwdformValidator();

	 $('#shopCode').selectpicker({
		 liveSearch:true,
		 maxOptions:1,
		 noneResultsText:'无搜索结果匹配 {0}'
		}).on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
		 $('#shopCode').selectpicker('toggle');
	 });

	var curDate=new Date();
    //日期插件初始化      
    $('#beginDate').datetimepicker({
    	language:  'zh-CN', 
        format:'yyyy-mm-dd', 
    	/* initialDate:curDate, */
    	endDate:new Date(),
       weekStart: 1,
        todayBtn:  1,
        autoclose: 1, 
        minView:2, 
        pickerPosition: "bottom-left" 
     }).on("changeDate",function(ev){  //值改变事件
        //选择的日期不能大于第二个日期控件的日期
        if(ev.date){
             $("#endDate").datetimepicker('setStartDate', new Date(ev.date.valueOf()));
         }else{
             $("#endDate").datetimepicker('setStartDate',null);
         }
     });
 	
     $('#endDate').datetimepicker({
    	 language:  'zh-CN', 
         format:'yyyy-mm-dd', 
    	 startDate:curDate,
          weekStart: 1, /*以星期一为一星期开始*/
          todayBtn:  1,
          autoclose: 1, 
          minView:2, /*精确到天*/
          pickerPosition: "bottom-left"
       }).on("changeDate",function(ev){
          //选择的日期不能小于第一个日期控件的日期
          if(ev.date){
               $("#beginDate").datetimepicker('setEndDate', new Date(ev.date.valueOf()));
          }else{
               $("#beginDate").datetimepicker('setEndDate',curDate);
          }
       });
     
     $("#updpwdButton").on("click", function(){

    	   var bootstrapValidator = $("#updpwdForm").data('bootstrapValidator');
    	   bootstrapValidator.validate();
    	   if(bootstrapValidator.isValid()){
    		  var data = $("#updpwdForm").serialize();
    		  $.ajax({
    				type: "POST",
    				url: "updPwd.do",
    				data: data,
    				dataType:"text",
    				success: function(data){
    					if(data=='login'){
    						location.href="toLogin.do";
    					}else if(data=='success'){
    						bs4pop.alert('修改成功', function(){ $('#updpwdModal').modal('hide');});
    					}else{
    						bs4pop.alert('修改失败', function(){});
    					}
    						
    				},
    				error: function(){
    					bs4pop.alert('修改失败', function(){});
    				},
    				complete: function () {  
    			    }
    			});
    		   
    	   }
    	});
     
     $('#updpwdModal').on('hidden.bs.modal', function () {
  		//清空表单校验信息
  		$("#updpwdForm").data('bootstrapValidator').destroy();
  		$('#updpwdForm').data('bootstrapValidator',null);
  		updpwdformValidator();
  	    $("#updpwdForm")[0].reset();
  	});
     
     $('#transDetailTable').bootstrapTable({
    	 pageNumber: 1, //初始化加载第一页，默认第一页
         pagination:true,//是否分页
         pageSize:5,//单页记录数
         locale:'zh-CN',//中文支持
		  columns: [
		    {
	            checkbox : true,
	            formatter: function(value, row, index) {
                   if (row.flag == 0) {
                       return {
                           disabled: false,
                       }
                   } else {
                       return {
                           disabled: true,
                       }
                   }
               }

	        },{
		    field: 'member_code',
		    title: '会员账号'
		  },{
		    field: 'cake_num',
		    title: '蛋糕数量'
		  }, {
		    field: 'cake_price',
		    title: '蛋糕交易额'
	        
		  }, {
		    field: 'transaction_time',
		    title: '交易时间'
           /* ,formatter: function(value, row, index) {
               if (row.transaction_time == null) {
                   return '';
               } else {
               	var rq=new Date(row.transaction_time).format("yyyy-MM-dd hh:mm"); 
                   return rq;  
               }
           } */
		  }, {
			    field: 'flag',
			    title: '操作',
	            formatter: function(value, row, index) {
                   if (row.flag == 1) {
                       return '<button type="button" class="btn btn-default" onclick="confirm('+row.cake_id+',0)">取消确认</button>';
                   } else {
                       return '';
                   }
               }
			  }]
		});
     
     
});
</script>

</html>