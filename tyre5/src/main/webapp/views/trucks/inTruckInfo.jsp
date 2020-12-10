<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Mosaddek">
<meta name="keyword" content="">

<title>车辆轮胎信息入库</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/bootstrap-reset.css" rel="stylesheet">
<!--external css-->
<link href="../css/font-awesome.css" rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="../css/style.css" rel="stylesheet">
<link href="../css/style-responsive.css" rel="stylesheet" />
<link href="../css/newStyle.css" rel="stylesheet">
<style type="text/css">
    #infoform label.error{
	color: red;
	display: block;
	position: relative;
    margin: 0 auto;
    text-align: left;
}

.login-box p input:hover{ border:1px #0065ff solid;}

.aa{
border:1px solid #517397;
} 
.bb{
border:1px solid #e2e2e4;
</style>

</head>

<body>
<section id="container" class="">
		<!--header start-->
		<jsp:include page="/common/top.jsp"></jsp:include>
		<!--header end-->
		<!--sidebar start-->
		<jsp:include page="/common/left.jsp"></jsp:include>
		<!--sidebar end-->
		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<!-- page start-->
				<form class="form-horizontal tasi-form" id="infoform">
				<div >
					<div class="row">
					<div class="col-lg-12 col-xs-12">
					<section class="panel">
                    <header class="panel-heading">车辆信息：</header>
                    <div class="panel-body">
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">车牌&nbsp;</label>
					<div class="col-lg-2">
					<input class="form-control" name="truckId" id="truckId"  type="text" value="${truckId }">
					</div>
					</div>
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">厂牌&nbsp;</label>
					<div class="col-lg-2">
                      <select name="truckBrand" class="select"> 
                      <!--  
                        <c:forEach items="${truckBrandlist }" var="tb" >
                          <option  <c:if test="${truckBrand eq tb.name}">selected="selected"</c:if> >${tb.name}</option> 
                        </c:forEach>-->
                        <option  <c:if test="${truckBrand eq '沃尔沃'}">selected="selected"</c:if> >沃尔沃</option> 
                        <option  <c:if test="${truckBrand eq '现代'}">selected="selected"</c:if> >现代</option> 
                        <option  <c:if test="${truckBrand eq '解放'}">selected="selected"</c:if> >解放</option> 
                        <option  <c:if test="${truckBrand eq '申沃客车'}">selected="selected"</c:if> >申沃客车</option> 
                        <option  <c:if test="${truckBrand eq '陕汽德龙'}">selected="selected"</c:if> >陕汽德龙</option> 
                        <option  <c:if test="${truckBrand eq '日野'}">selected="selected"</c:if> >日野</option> 
                       </select>
                     </div>
                    </div>
                    <div class="form-group">
                    <label class="control-label col-sm-4 col-xs-4">类型&nbsp;</label>
                    <div class="col-lg-2">
                      <select name="truckType" id="truckType" class="select" onChange="checkTruckType();"> 
                          <option  <c:if test="${truckType eq '主车'}">selected="selected"</c:if> >主车</option> 
                          <option  <c:if test="${truckType eq '挂车'}">selected="selected"</c:if> >挂车</option> 
                       </select>
                     </div>
					</div>
                    <div class="form-group">
                    <label class="control-label col-sm-4 col-xs-4">车型&nbsp;</label>
                    <div class="col-lg-2">
                      <select name="truckStyle" id="truckStyle" class="select" onchange="getWhere();"> 
                      <!--  
                          <option  <c:if test="${truckStyle eq '4*2'}">selected="selected"</c:if> >4*2</option>
                          <option  <c:if test="${truckStyle eq '4*2+3'}">selected="selected"</c:if> >4*2+3</option>-->
                       </select>
                     </div>
                    </div>
					<div class="form-group">
                    <label class="control-label col-sm-4 col-xs-4">公司&nbsp;</label>
                    <div class="col-lg-2">
                      <select name="company" class="select"> 
                        <c:forEach items="${companylist }" var="c" >
                          <option  <c:if test="${company eq c.company}">selected="selected"</c:if> >${c.company}</option> 
                        </c:forEach>
                       </select>
                     </div>
					</div>
					<!--  
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">gps里程数&nbsp;</label>
					<div class="col-lg-2">
					<input class="form-control" name="liCheng" id="liCheng" placeholder="单位:公里" type="text" value="${liCheng }">
					</div>
					</div>
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">行驶里程&nbsp;</label>
					<div class="col-lg-2">
					<input class="form-control" name="liChengRun" id="liChengRun"  type="text" value="${liChengRun }">
					</div>
					</div>-->
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">码表数&nbsp;</label>
					<div class="col-lg-2">
					<input class="form-control" name="mabiao" id="mabiao"  type="text" value="${mabiao }">
					</div>
					</div>
					<!--  
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">入库码表数&nbsp;</label>
					<div class="col-lg-2">
					<input class="form-control" name="mabiaoRuku" id="mabiaoRuku"  type="text" value="${mabiaoRuku }">
					</div>
					</div>-->
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">运输类型&nbsp;</label>
					<div class="col-lg-2">
                      <select name="transportType" class="select"> 
                          <option  <c:if test="${transportType eq '危险品'}">selected="selected"</c:if> >危险品</option> 
                          <option  <c:if test="${transportType eq '快递'}">selected="selected"</c:if> >快递</option> 
                          <option  <c:if test="${transportType eq '公交'}">selected="selected"</c:if> >公交</option> 
                          <option  <c:if test="${transportType eq '冷链'}">selected="selected"</c:if> >冷链</option> 
                          <option  <c:if test="${transportType eq '客运'}">selected="selected"</c:if> >客运</option> 
                          <option  <c:if test="${transportType eq '其他'}">selected="selected"</c:if> >其他</option> 
                       </select>
                    </div>
					</div>
					<div class="form-group" id="guacheTrucksIdDiv">
					<label class="control-label col-sm-4 col-xs-4">挂车车牌&nbsp;</label>
					<div class="col-lg-2">
					<input class="form-control" name="guacheTrucksId" id="guacheTrucksId"  type="text" value="${guacheTrucksId }">
					</div>
					</div>
					<div class="form-group">
					<label class="control-label col-sm-4 col-xs-4">车辆型号&nbsp;</label>
					<div class="col-lg-2">
					<input class="form-control" name="trucksMode" id="trucksMode"  type="text" value="${trucksMode }">
					</div>
					</div>
					<div class="form-group" id="guacheSaveFlagDiv">
					<label class="control-label col-sm-4 col-xs-4">主挂一体&nbsp;</label>
					<div class="col-lg-2">
                      <select name="guacheSaveFlag" id="guacheSaveFlag" class="select"> 
                          <option  <c:if test="${guacheSaveFlag eq '0'}">selected="selected"</c:if> >是</option> 
                          <option  <c:if test="${guacheSaveFlag eq '1'}">selected="selected"</c:if> >否</option> 
                       </select>
                    </div>
					</div>
					<div class="form-group" id="dtuMultiFlagDiv">
                    <label class="control-label col-sm-4 col-xs-4">主挂dtu&nbsp;</label>
                    <div class="col-lg-2">
                      <select name="dtuMultiFlag" id="dtuMultiFlag" class="select" onChange="dtuChange();"> 
                          <option  <c:if test="${dtuMultiFlag eq '0'}">selected="selected"</c:if> >一个</option> 
                          <option  <c:if test="${dtuMultiFlag eq '1'}">selected="selected"</c:if> >分开</option> 
                       </select>
                     </div>
                    </div>
                    </div>
				</section> 
				</div>
				</div>
				<div class="row">
				<div class="col-lg-12 col-xs-12">
				<section class="panel"><header class="panel-heading">轮胎信息：<input type="button" class="btn btn-info btn-xs" value="+添加轮胎" onclick="addTyre()"></header>
				<div id="tyres" class="col-lg-12 col-xs-12" >
					<label id="tyreMsg" style="color:red"></label>
					<div id="tyre" name="tyre">
					<div>
					<label>胎号&nbsp;<input name="tyreId" class="bb" onfocus="javascript:this.className='aa'" onblur="javascript:this.className='bb'" type="text" value="${tyreId }"></label>
					<label>&nbsp;品牌&nbsp;
                      <select name="tyreBrand" class="select"> 
                        <c:forEach items="${tyreBrandlist }" var="tb" >
                          <option  <c:if test="${tyreBrand eq tb.name}">selected="selected"</c:if> >${tb.name}</option> 
                        </c:forEach>
                       </select>
                    </label>
                    <label>&nbsp;规格&nbsp;
                      <select name="tyreType1" class="select"> 
                        <c:forEach items="${specificationlist }" var="s" >
                          <option  <c:if test="${tyreType1 eq s.name}">selected="selected"</c:if> >${s.name}</option> 
                        </c:forEach>
                       </select>
                    </label>
                    <label>&nbsp;花纹代码&nbsp;
                      <select name="tyreType2" class="select"> 
                        <c:forEach items="${patternlist }" var="p" >
                          <option  <c:if test="${tyreType2 eq p.name}">selected="selected"</c:if> >${p.name}</option> 
                        </c:forEach>
                       </select>
                    </label>
                    <label>&nbsp;花纹&nbsp;
                      <select name="tyreType3" class="select"> 
                          <option  <c:if test="${tyreType3 eq '条纹'}">selected="selected"</c:if> >条纹</option> 
                          <option  <c:if test="${tyreType3 eq '块状'}">selected="selected"</c:if> >块状</option> 
                       </select>
                    </label>
                    <label>&nbsp;轮毂&nbsp;
                      <select name="tyreType4" class="select"> 
                          <option  <c:if test="${tyreType4 eq '1'}">selected="selected"</c:if> >有</option> 
                          <option  <c:if test="${tyreType4 eq '0'}">selected="selected"</c:if> >无</option> 
                       </select>
                    </label>
                    <label>&nbsp;气门帽&nbsp;
                      <select name="tyreType5" class="select"> 
                          <option  <c:if test="${tyreType5 eq '1'}">selected="selected"</c:if> >有</option> 
                          <option  <c:if test="${tyreType5 eq '0'}">selected="selected"</c:if> >无</option> 
                       </select>
                    </label>
                    <label>&nbsp;内胎&nbsp;
                      <select name="tyreType6" class="select"> 
                          <option <c:if test="${tyreType6 eq '1'}">selected="selected"</c:if> >有</option> 
                          <option <c:if test="${tyreType6 eq '0'}">selected="selected"</c:if> >无</option> 
                       </select>
                    </label>
                    <label>&nbsp;性质&nbsp;
                      <select name="tyreType7" class="select"> 
                          <option  <c:if test="${tyreType7 eq '1'}">selected="selected"</c:if> >全钢</option> 
                          <option  <c:if test="${tyreType7 eq '0'}">selected="selected"</c:if> >半钢</option> 
                       </select>
                    </label>
                    <label>&nbsp;车胎位置&nbsp;
                      <select name="tyreWhere" class="select" id="tyreWhere"> 
                      <!--  
                          <option  <c:if test="${tyreWhere eq 'A1'}">selected="selected"</c:if> >A1</option> 
                          <option  <c:if test="${tyreWhere eq 'A2'}">selected="selected"</c:if> >A2</option> 
                          <option  <c:if test="${tyreWhere eq 'A3'}">selected="selected"</c:if> >A3</option> 
                          <option  <c:if test="${tyreWhere eq 'A4'}">selected="selected"</c:if> >A4</option> 
                          <option  <c:if test="${tyreWhere eq 'A5'}">selected="selected"</c:if> >A5</option> 
                          <option  <c:if test="${tyreWhere eq 'A6'}">selected="selected"</c:if> >A6</option> 
                          <option  <c:if test="${tyreWhere eq 'B1'}">selected="selected"</c:if> >B1</option> 
                          <option  <c:if test="${tyreWhere eq 'B2'}">selected="selected"</c:if> >B2</option> 
                          <option  <c:if test="${tyreWhere eq 'B3'}">selected="selected"</c:if> >B3</option> 
                          <option  <c:if test="${tyreWhere eq 'B4'}">selected="selected"</c:if> >B4</option> 
                          <option  <c:if test="${tyreWhere eq 'B5'}">selected="selected"</c:if> >B5</option> 
                          <option  <c:if test="${tyreWhere eq 'B6'}">selected="selected"</c:if> >B6</option> 
                          <option  <c:if test="${tyreWhere eq 'B7'}">selected="selected"</c:if> >B7</option> 
                          <option  <c:if test="${tyreWhere eq 'B8'}">selected="selected"</c:if> >B8</option> 
                          <option  <c:if test="${tyreWhere eq 'C1'}">selected="selected"</c:if> >C1</option> 
                          <option  <c:if test="${tyreWhere eq 'C2'}">selected="selected"</c:if> >C2</option> 
                          <option  <c:if test="${tyreWhere eq 'C3'}">selected="selected"</c:if> >C3</option> 
                          <option  <c:if test="${tyreWhere eq 'C4'}">selected="selected"</c:if> >C4</option> 
                          <option  <c:if test="${tyreWhere eq 'C5'}">selected="selected"</c:if> >C5</option> 
                          <option  <c:if test="${tyreWhere eq 'C6'}">selected="selected"</c:if> >C6</option> 
                          <option  <c:if test="${tyreWhere eq 'C7'}">selected="selected"</c:if> >C7</option> 
                          <option  <c:if test="${tyreWhere eq 'C8'}">selected="selected"</c:if> >C8</option> 
                          <option  <c:if test="${tyreWhere eq 'C9'}">selected="selected"</c:if> >C9</option> 
                          <option  <c:if test="${tyreWhere eq 'C10'}">selected="selected"</c:if> >C10</option> 
                          <option  <c:if test="${tyreWhere eq 'C11'}">selected="selected"</c:if> >C11</option> 
                          <option  <c:if test="${tyreWhere eq 'C12'}">selected="selected"</c:if> >C12</option> 
                          <option  <c:if test="${tyreWhere eq 'C13'}">selected="selected"</c:if> >C13</option> 
                          <option  <c:if test="${tyreWhere eq 'C14'}">selected="selected"</c:if> >C14</option> 
                          <option  <c:if test="${tyreWhere eq 'C15'}">selected="selected"</c:if> >C15</option> 
                          <option  <c:if test="${tyreWhere eq 'C16'}">selected="selected"</c:if> >C16</option> -->
                       </select>
                    </label>
                    <label>原始深度&nbsp;<input name="tyreDepth" class="bb" onfocus="javascript:this.className='aa'" onblur="javascript:this.className='bb'" type="text" value="${tyreDepth }" ></label>
                    
                    <!--  
                    <label>翻新&nbsp;
                       <select name="tyreFanxin" class="select"> 
                          <option  <c:if test="${tyreFanxin eq '1'}">selected="selected"</c:if> >是</option> 
                          <option  <c:if test="${tyreFanxin eq '0'}">selected="selected"</c:if> >否</option> 
                       </select>
                    </label>-->
                    </div>
                    </div>
                    </div>
                    </section>
                    </div>
                    </div>
                    
				</div>
				<input type="submit" class="btn btn-primary btn-sm" value="提交">
				</form>
				<!-- page end-->
			</section>
		</section>
		<!--main content end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<!-- js placed at the end of the document so the pages load faster -->
	<script src="../js/jquery.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery.scrollTo.min.js"></script>
	<script src="../js/jquery.nicescroll.js" type="text/javascript"></script>


	<!--common script for all pages-->
	<script src="../js/common-scripts.js"></script>
	<script src="../js/jquery.validate.min.js"></script>
	<script src="../js/jquery.validate.metadata.js"></script>


	<script type="text/javascript" src="../js/kkbc/trucks/inTruckInfo.js"></script>


</section>
</body>
</html>



