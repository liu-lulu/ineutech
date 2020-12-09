<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<style type="text/css">
.div_htdq, .div_fpdq, .div_dqfk, .div_dztx {
    float: left;
    width: 107%;
    box-sizing: border-box;
    position: relative;
    padding: 2px 6px 0px;
}

.notic_line {
    width: 1px;
    height: 100%;
    background: #e5e5e5;
    display: block;
    position: absolute;
    top: 30px;
    margin-left: 12px;
}
.div_tx_box_hide {
    width: 100%;
    overflow: hidden;
}
.div_tx_box ul {
    position: relative;
    height: 100%;
    overflow-y: scroll;
    margin-top: 10px;
}
.div_tx_box ul li {
    width: 98%;
    position: relative;
    font-size: 12px;
    overflow: hidden;
    padding: 8px 0 12px;
    cursor: pointer;
}
.span_circle {
    width: 8px;
    height: 8px;
    left: 0;
    border: 1px solid #cbcbcb;
    position: absolute;
    border-radius: 25px;
    margin-right: 2%;
    margin-left: 8px;
    vertical-align: middle;
    margin-top: 6px;
    background: #fff;
}
.div_tx_box ul li .span_title {
    float: left;
    width: 90%;
    margin-top: 4px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-left: 3%;
}
.div_tx_box .navContent {
    width: 94%;
    position: relative;
    float: left;
    margin-left: 3%;
    padding: 10px 0 10px;
    display: none;
    padding-left: 1%;
    border-bottom: 1px solid #eee;
}
.span_dktx_htbh, .span_dktx_xdfmc, .span_dktx_dkje, .span_dktx_dkrq {
    width: 100%;
    float: left;
    padding: 5px 0;
    font-size: 12px;
    color: #666;
    cursor: pointer;
}
a:-webkit-any-link {
    color: -webkit-link;
    cursor: pointer;
    text-decoration: underline;
}

ul, li {
    list-style: none;
}

.span_ckxq {
    padding: 8px 18px;
    font-size: 12px;
    color: #666;
    cursor: pointer;
    background: #efefef;
    border-radius: 5px;
    margin-top: 12px;
    display: inline-block;
    text-align: center;
}

/*  .div_tx_box::-webkit-scrollbar {
        display: none;
    } */
</style>

<script type="text/javascript">

$(document).ready(function(){ 
	 //滚动条到页面底部加载更多案例
  /*  $(".div_tx_box").scroll(function(){
    	alert("1");
    }); */
	 
	$("#box li").on("click",function(){
		$(this).next().toggle();
	});
	
	$(".span_title").hover(function(){
		$(this).prev().css("background","#ccc");
		$(this).parent().css("background","#f7f7f7");
	},function(){
		if($(this).parent().next().is(":hidden")){
			$(this).prev().css("background","#fff");
			$(this).parent().removeAttr("style");
		}
		
	});
});
</script>


</head>

<body>
	<%-- <table>
   		<c:forEach items="${list }" var="data">
   			<tr>
            <td>
       			<a href="detail.do?id=${data.id}" target="_blank">${data.title}</a>
       		</td>
            </tr>
		</c:forEach>
   	</table> --%>

	<div class="div_tx_box" style=" overflow-y:auto;overflow-x:hidden; ">
		<div class="div_htdq">
			<span class="notic_line"></span>
			<div class="div_tx_box_hide">
				<ul id="box" style="padding-left: 0px;">
					<li><span class="span_circle"></span><span class="span_title">上海医药协同办公平台一期项目销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="51">201809-xs-000082</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海医药集团股份有限公司</span><span
							class="span_dktx_dkje">到款金额：420000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=420&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
<!-- 					<li><span class="span_circle"></span><span class="span_title">弘晖资产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="52">201809-xs-000083</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海弘晖资产管理有限公司</span><span
							class="span_dktx_dkje">到款金额：450000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=421&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海纺织协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="54">201809-xs-000085</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海纺织（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：360000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=418&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海华谊E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="55">201809-xs-000086</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海华谊集团投资有限公司</span><span
							class="span_dktx_dkje">到款金额：450000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=417&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div>
					<li><span class="span_circle"></span><span class="span_title">上海地产E9协同办公平台销售合同</span></li>
					<div class="navContent">
						<span class="span_dktx_htbh">合同编号：<a id="57">201809-xs-000088</a></span><span
							class="span_dktx_xdfmc">相对方名称：上海地产（集团）有限公司</span><span
							class="span_dktx_dkje">到款金额：540000.00</span><span
							class="span_dktx_dkrq">到款日期：2018-09-17</span><a
							href="/spa/workflow/index_form.jsp#/main/workflow/req?requestid=419&amp;_workflowid=158&amp;_workflowtype=&amp;isovertime=0"
							target="_blank"><span class="span_ckxq">查看详情</span></a>
					</div> -->
				</ul>
			</div>
		</div>

	</div>

</body>
</html>