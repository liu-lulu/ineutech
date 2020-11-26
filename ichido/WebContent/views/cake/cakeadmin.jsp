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
				<a href="#" data-toggle="modal" data-target="#updpwdModal">${cake.e_name }</a>
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addModal">
				  <span class="glyphicon glyphicon-user"></span> <span class="glyphicon glyphicon-plus"></span>
				</button>
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#delModal">
				  <span class="glyphicon glyphicon-user"></span> <span class="glyphicon glyphicon-minus"></span>
				</button>
				<a href="toLogin.do" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-log-out"></span>退出</a>
			</h3>
		</div>
	</div>
	<div class="row clearfix">
		<form class="form-horizontal" role="form" action="managerRecord.do" method="post" id="recordForm">
		<div class="col-md-12 column">
			<div class="row clearfix">
				<div class="col-md-3 col-xs-12 column">
					<select class="show-tick form-control"  title="请选择店长" name="e_id" id="e_id" >
					
					<c:forEach var="manager" items="${managers }">
						<option value="${manager.e_id }" <c:if test="${e_id eq manager.e_id }">selected="selected" </c:if> >${manager.e_number }-${manager.e_name }</option>
					</c:forEach>
				      
				    </select>
				</div>
				<div class="col-md-6 col-xs-12 column">
				
					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">日期</label>
						<div class="col-sm-10 input-group" style="padding:0 15px;">
						  	<input type="text" class="form-control" readonly id="beginDate" name="beginDate" <c:if test="${not empty beginDate }">value="${beginDate }"</c:if> <c:if test="${empty beginDate }">value="<fmt:formatDate value='<%= new java.util.Date() %>'  pattern='yyyy-MM-dd'/>"</c:if>>
							<span class="input-group-addon">至</span>
							<input type="text" class="form-control" readonly id="endDate" name="endDate" <c:if test="${not empty endDate }">value="${endDate }"</c:if> <c:if test="${empty endDate }">value="<fmt:formatDate value='<%= new java.util.Date() %>'  pattern='yyyy-MM-dd'/>"</c:if>>
						</div>
						
					</div>
					
				</div>
				<div class="col-md-3 col-xs-12 column">
					<div class="col-md-offset-0 col-xs-offset-8">
					<input type="button" class="btn btn-primary" onclick="toVaild()" value="查看">
					<div class="btn btn-primary" onclick="toExportEmployeeSale()" > 导出<i class="fa fa-spinner fa-pulse fa-fw"  id="xuanzhun"></i></div>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
	<c:if test="${empty records  && not empty e_id}"><h4 style="text-align:center;">无交易记录</h4></c:if>
	<c:if test="${not empty records }">
	<div class="row clearfix">
		<div class="col-md-3 column">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<table class="table">
					<caption>店铺销售额统计</caption>
					<tr><th style="border-left: 1px solid #ddd;">编号</th><th>店名</th><th style="min-width: 45px;">总数</th><th style="border-right: 1px solid #ddd;">金额</th></tr>
					<c:forEach items="${shopSales }" var="shopSale">
						 <tr>
					         <td style="border-left: 1px solid #ddd;">${shopSale.shop_code }</td>
					         <td>${shopSale.shop_name }</td>
							 <td>${shopSale.cake_num }</td>
							 <td style="border-right: 1px solid #ddd;">${shopSale.cake_price }</td>
					      </tr>
					</c:forEach>
					<tr style="border-bottom: 1px solid #ddd;">
						<td colspan=2 style="border-left: 1px solid #ddd;text-align: right;">共计</td>
						<td>${totalSaleCount }</td>
						<td style="border-right: 1px solid #ddd;">${totalSalePrice }</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="col-md-9 column">
			<div class="row clearfix">
				<div class="col-md-12 column" style="font-size: small;">
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th>
									门店编号
								</th>
								<th>
									门店名称
								</th>
								<th>
									工号
								</th>
								<th>
									会员账号
								</th>
								<th>
									交易时间
								</th>
								<th>
									蛋糕数量
								</th>
								<th>
									蛋糕交易额
								</th>
								<th>
									确认时间
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${records }" var="record">
								<tr>
								<td>${record.shop_code }</td>
								<td>${record.shop_name }</td>
								<td>${record.e_number }</td>
								<td>${record.member_code }</td>
								<td>${record.transaction_time }</td>
								<td>${record.cake_num }</td>
								<td>${record.cake_price }</td>
								<td>${record.confirm_time }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
		</div>
	</div>
	
	</c:if>
</div>

<!-- 人员添加（Modal） -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="addModalLabel">添加人员</h4>
            </div>
            <div class="modal-body">
            	<form class="form-horizontal" role="form" id="addForm">
					<div class="form-group">
						<label for="e_number" class="col-sm-2 control-label">工号</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="e_number" name="e_number"
								   placeholder="请输入工号">
						</div>
					</div>
					<div class="form-group">
						<label for="e_name" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="e_name" name="e_name"
								   placeholder="请输入姓名">
						</div>
					</div>
					
					<div class="form-group">
						<label for="role" class="col-sm-2 control-label">职位</label>
						<div class="col-sm-10">
						<label class="radio-inline">
							<input type="radio" name="role" value="0" checked> 店员
						</label>
						<label class="radio-inline">
							<input type="radio" name="role" value="2"> 店长
						</label>
						</div>
					</div>
				</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="addButton">添加</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 人员删除（Modal） -->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="delModalLabel">删除人员</h4>
            </div>
            <div class="modal-body">
            	<form class="form-horizontal" role="form" id="delForm" style="margin-bottom:10px;">
					<div class="form-group">
						<label class="sr-only" for="e_info">请输入工号或姓名</label>
						<div class="col-sm-9 col-xs-9">
						<input type="text" class="form-control" id="e_info" name="e_info"
							   placeholder="请输入工号/姓名">
						</div>
						<div class="col-sm-3 col-xs-3">
							<button type="button" class="btn btn-default" id="getButton">查询</button>
						</div>
					</div>
				</form>
				<table id="employeeTable" class="table-striped table-hover" ></table>
				
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

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

<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/kkpager.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/paging.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bs4.pop.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-select.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-table-zh-CN.min.js"></script>
</body>
<script type="text/javascript">

function toVaild(){
    var val = $('#e_id').val();
    if(val!=null&&val!=''){
      $('#recordForm').submit();
    }else{
    	bs4pop.alert('请选择店长', function(){});
    }
}

function toExportEmployeeSale(){
	
	var data=$("#recordForm").serialize();
	url = "export.do";
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		dataType:"json",
		beforeSend: function(){  
			$("#xuanzhun").show();
		},  
		success: function(data){
				$("#xuanzhun").hide();
				if(data.msg=="导出失败！"){
					alert(msg);
				}else{
					alert("导出成功！");
					window.location.href = "xiazai.do?realPath="+data.msg;
				}
		},
		error: function(msg){
			$("#xuanzhun").hide();
			alert("error"+msg);
		},
		complete: function () {
			$("#xuanzhun").hide();
	    }
	});
}

function addformValidator() {
 	$('#addForm').bootstrapValidator({

		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			e_number : {
				verbose: false,
				validators : {
					notEmpty : {
						message : '工号不能为空'
					},
		            
	              stringLength: {
	                max: 20,
	                message: '工号最大长度20位'
	              },
	              remote:{
	            	  url:"numberValid.do",
	            	  message: "该工号已存在",
	            	  delay:500,
	            	  type: 'POST'
	              }
				}
			},
			e_name : {
				validators : {
					notEmpty : {
						message : '姓名不能为空'
					},
		            
		              stringLength: {
		                max: 20,
		                message: '姓名最大长度20位'
		              }
				}
			}
		}
       
	});
}

function delformValidator() {
	$('#delForm').bootstrapValidator({

		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			e_info : {
				validators : {
					notEmpty : {
						message : '工号/姓名不能为空'
					},
		            
	              stringLength: {
	                max: 20,
	                message: '工号/姓名最大长度20位'
	              }
				}
			}
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

function del(e_id,e_number,e_name){
	bs4pop.confirm('确定删除'+e_number+":"+e_name+"?", function(sure){ 
		if (sure){
			$.ajax({
				type: "POST",
				url: "del.do",
				data: {'e_id':e_id},
				async: true,
				dataType:"text",
				success: function(data){
					if(data=='login'){
						location.href="toLogin.do";
					}else if(data=='success'){
						bs4pop.alert('删除成功', function(){ $('#delModal').modal('hide');});
					}else{
						bs4pop.alert('删除失败', function(){});
					}
				},
				error: function(){
					bs4pop.alert('删除失败', function(){});
				},
				complete: function () {  
					
			    }
			});
		}else{
			
		}
	});
}

function initbootstrapTable(){
	$('#employeeTable').bootstrapTable({
   	 pageNumber: 1, //初始化加载第一页，默认第一页
        pagination:true,//是否分页
        pageSize:5,//单页记录数
        pageList:[5],
        locale:'zh-CN',//中文支持
      columns: [
  		    {
  		    field: 'e_number',
  		    title: '工号'
  		  },{
  		    field: 'e_name',
  		    title: '姓名'
  		  }, {
  		    field: 'role',
  		    title: '职位',
           formatter: function(value, row, index) {
            return row.role==1?"管理员":(row.role==2?"店长":"店员");
        }
  	        
  		  }, {
  			    field: 'e_id',
  			    title: '操作',
  	            formatter: function(value, row, index) {
  	              return "<button class='btn btn-default' onclick=del("+row.e_id+",'"+row.e_number+"','"+row.e_name+"')>删除</button>";
                 }
  			  }]
	});
}

$(document).ready(function(){
	$("#xuanzhun").hide();
	
	addformValidator();
	delformValidator();
	updpwdformValidator();
	

	 $('#e_id').selectpicker({
		 liveSearch:true,
		 maxOptions:1,
		 noneResultsText:'无搜索结果匹配 {0}'
		})
		/* .on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
		 $('#e_id').selectpicker('toggle');
		 }) */
		 ;

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
     
     

	
 	$("#addButton").on("click", function(){

 	   var bootstrapValidator = $("#addForm").data('bootstrapValidator');
 	   bootstrapValidator.validate();
 	   if(bootstrapValidator.isValid()){
 		  var data = $("#addForm").serialize();
 		  $.ajax({
 				type: "POST",
 				url: "add.do",
 				data: data,
 				dataType:"text",
 				success: function(data){
 					if(data=='login'){
 						location.href="toLogin.do";
 					}else if(data=='success'){
 						bs4pop.alert('添加成功', function(){ $('#addModal').modal('hide');});
 					}else{
 						bs4pop.alert('添加失败', function(){});
 					}
 						
 				},
 				error: function(){
 					bs4pop.alert('添加失败', function(){});
 				},
 				complete: function () {  
 			    }
 			});
 		   
 	   }
 	});
 	
 	
 	$("#getButton").on("click", function(){

  	   var bootstrapValidator = $("#delForm").data('bootstrapValidator');
  	   bootstrapValidator.validate();
  	   if(bootstrapValidator.isValid()){
  		 initbootstrapTable();
  		  $.ajax({
  				type: "POST",
  				url: "getEmployee.do",
  				data: {'e_info':$("#e_info").val()},
  				dataType:"json",
  				success: function(data){
  					if(data.info=='login'){
  						location.href="toLogin.do";
  						return;
  					}
  					/* var employees='';
  					for(var e in data){
  					var detail="<tr><td>"+data[e].e_number+"</td><td>"+data[e].e_name+"</td><td>"+(data[e].role==1?"管理员":(data[e].role==2?"店长":"店员"))+"</td><td><button class='btn btn-default' onclick=del("+data[e].e_id+",'"+data[e].e_number+"','"+data[e].e_name+"')>删除</button></td></tr>";
  					employees=employees+detail;
  					}
  					
  					var tableInfo="<table class='table table-striped table-hover'><tr><th>工号</th><th>姓名</th><th>职位</th><th>操作</th></tr>"+employees+"</table>";
  					$('#tablediv').append(tableInfo); */
  					
  					$("#employeeTable").bootstrapTable("load",data);
  				},
  				error: function(){
  					bs4pop.alert('查询失败', function(){});
  					
  				},
  				complete: function () {  
  			    }
  			});
  		   
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
 	
 	
 	
 	// 关闭重置
 	$('#addModal').on('hidden.bs.modal', function () {
 		//清空表单校验信息
 		$("#addForm").data('bootstrapValidator').destroy();
 		$('#addForm').data('bootstrapValidator',null);
 		addformValidator();
 	    $("#addForm")[0].reset();
 	});
 	
 	$('#delModal').on('hidden.bs.modal', function () {
 		//清空表单校验信息
 		$("#delForm").data('bootstrapValidator').destroy();
 		$('#delForm').data('bootstrapValidator',null);
 		delformValidator();
 	    $("#delForm")[0].reset();
 	   $("#employeeTable").bootstrapTable('destroy');
 	});
 	
 	$('#updpwdModal').on('hidden.bs.modal', function () {
 		//清空表单校验信息
 		$("#updpwdForm").data('bootstrapValidator').destroy();
 		$('#updpwdForm').data('bootstrapValidator',null);
 		updpwdformValidator();
 	    $("#updpwdForm")[0].reset();
 	});
 	 
     
});
</script>
</html>