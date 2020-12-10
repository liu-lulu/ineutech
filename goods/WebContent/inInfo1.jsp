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

<title>新建商品介绍</title>

<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/bootstrap-reset.css" rel="stylesheet">
<!--external css-->
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/newStyle.css" rel="stylesheet">
<style type="text/css">
	/*定义图像以及大小*/
	.imageFileInput{
	    width: 150px;
	    height: 150px;
	    position: absolute;
	    background-image:url("img/upload.png");
		background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
		cursor:pointer;
	}
	
	/*定义上传*/
	.fileInput{
		width: 100%;
	    height: 100%;
	    position: absolute;
	    right: 0;
	    top: 0;
	    opacity: 0;
	}
	#inputBox{
		width: 100%;
		height: 40px;
		border: 1px solid cornflowerblue;
		color: cornflowerblue;
		border-radius: 20px;
		position: relative;
		text-align: center;
		line-height: 40px;
		overflow: hidden;
		font-size: 16px;
	}
	 #inputBox input{
		width: 114%;
		height: 40px;
		opacity: 0;
		cursor: pointer;
		position: absolute;
		top: 0;
		left: -14%;
		
	}
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
				<form class="form-horizontal tasi-form" id="modifyForm" action="interface/edit.do" method="post" enctype="multipart/form-data" target="_blank">
				<div >
					<div class="row">
					<div class="col-lg-12 col-xs-12">
					<section class="panel">
                    <header class="panel-heading">商品信息：</header>
                    <div class="panel-body" >
                    	<label class="control-label col-sm-4 col-xs-4">desc_id：&nbsp;<input type="text" name="desc_id" ></label>
                    	<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">商品状态：&nbsp;</label>
							<div class="col-xs-4">
								<input type="radio" name="shuxing" value="1">国内现货--
								<input type="radio" name="goods_state" value="1">现货
								<input type="radio" name="goods_state" value="2">非现货&nbsp;&nbsp;
								<!-- <input type="checkbox" name="shuxing" value="2">速抢好货&nbsp;&nbsp; -->
								<input type="radio" name="shuxing" value="3">今日秒杀
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">品牌：&nbsp;</label>
							<div class="col-xs-4">
								<input type="text" name="brand" >
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">类别</label>
							<div class="col-xs-4">
								<input type="text" name="category" >
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">微店地址</label>
							<div class="col-xs-4">
								<input type="text" name="url" >
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">其它：&nbsp;</label>
							<div class="col-xs-4">
								<input type="text" name="other" >
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">属性类别：&nbsp;</label>
							<div class="col-xs-4">
								<input type="text" name="goods_property" >
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">基数：&nbsp;</label>
							<div class="col-xs-4">
								<input type="text" name="base" >
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">内容描述：&nbsp;</label>
							<div class="col-xs-4">
								<textarea name="content" style="height:150px;width:400px;resize:none;" placeholder="内容描述"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4 col-xs-4">小图：&nbsp;</label>
							 <div class="col-lg-12" style="height:150px"> 
								<div style="float:left;">
								<div class="imageFileInput" id="smallImag1">
									<input class="fileInput" type=file name="smallfiles" id="smallPicture1" accept="image/*" onchange="setImagePreviewb('smallPicture1','smallImag1');">
								</div>
								</div>
								<div style="float:left;margin-left:160px">
								<div class="imageFileInput" id="smallImag2">
									<input class="fileInput" type=file name="smallfiles" id="smallPicture2" accept="image/*" onchange="setImagePreviewb('smallPicture2','smallImag2');">
								</div>
								</div>
								<div style="float:left;margin-left:320px">
								<div class="imageFileInput" id="smallImag3">
									<input class="fileInput" type=file name="smallfiles" id="smallPicture3" accept="image/*" onchange="setImagePreviewb('smallPicture3','smallImag3');">
								</div>
								</div>
								<div style="float:left;margin-left:480px">
								<div class="imageFileInput" id="smallImag4">
									<input class="fileInput" type=file name="smallfiles" id="smallPicture4" accept="image/*" onchange="setImagePreviewb('smallPicture4','smallImag4');">
								</div>
								</div>
							</div>
						</div>
		
	                    <div class="form-group" id="des1">
		                    <label class="control-label col-sm-4 col-xs-4">图片描述&nbsp;</label>
		                    <div class="col-lg-12">
		                    	<div class="col-xs-4">
									<textarea name="img1_describe" style="height:150px;width:400px;resize:none;" placeholder="图片详情"></textarea>
								</div>
								<div class="col-xs-3">
									<div class="imageFileInput" id="localImag1">
										<input class="fileInput" type=file name="files" id="selfPicture1" accept="image/*" onchange="setImagePreviewb('selfPicture1','localImag1');">
									</div>
								</div>
		                     </div>
						</div>
						
						<div class="form-group" id="btns">
							<input type="button" class="btn btn-primary btn-sm" value="添加商品" onclick="addgoods();" /> 
							<input type="button" class="btn btn-primary btn-sm" value="删除商品" onclick="delgoods();" /> 
						</div>
<!-- 						<div class="form-group">
		                    <label class="control-label col-sm-4 col-xs-4">图2描述&nbsp;</label>
		                    <div class="col-lg-12">
		                   		 <div class="col-xs-4">
									<textarea name="img2_describe" style="height:150px;width:400px;resize:none;" placeholder="图片详情"></textarea>
								</div>
								<div class="col-xs-3">
									<div class="imageFileInput" id="localImag2">
										<input class="fileInput" type=file name="files" id="selfPicture2" accept="image/*" onchange="javascript:setImagePreviewb('selfPicture2','localImag2');">
									</div>
								</div>
		                     </div>
						</div>
						
						<div class="form-group">
	                    <label class="control-label col-sm-4 col-xs-4">图3描述&nbsp;</label>
	                     <div class="col-lg-12">
	                     	 <div class="col-xs-4">
									<textarea name="img3_describe" style="height:150px;width:400px;resize:none;" placeholder="图片详情"></textarea>
								</div>
								<div class="col-xs-3">
									<div class="imageFileInput" id=localImag3>
										<input class="fileInput" type=file name="files" id="selfPicture3" accept="image/*" onchange="javascript:setImagePreviewb('selfPicture3','localImag3');">
									</div>
								</div>
	                     </div>
						</div>
						
						<div class="form-group">
	                    <label class="control-label col-sm-4 col-xs-4">图4描述&nbsp;</label>
	                    <div class="col-lg-12">
	                    	<div class="col-xs-4">
									<textarea name="img4_describe" style="height:150px;width:400px;resize:none;" placeholder="图片详情"></textarea>
								</div>
								<div class="col-xs-3">
									<div class="imageFileInput" id=localImag4>
										<input class="fileInput" type=file name="files" id="selfPicture4" accept="image/*" onchange="javascript:setImagePreviewb('selfPicture4','localImag4');">
									</div>
							</div>
	                     </div>
						</div> -->
                    </div>
				</section> 
				</div>
				</div>
				</div>
				<!-- <input type="button" class="btn btn-primary btn-sm" value="提交" onclick="submitForm();" />  -->
				
				<input type="button" class="btn btn-primary btn-sm" value="修改" onclick="submitForm();" /> 
				</form>
				<!-- page end-->
			</section>
		</section>
		<!--main content end-->

	<!-- js placed at the end of the document so the pages load faster -->
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/common-scripts.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.validate.metadata.js"></script>


	<script src="${pageContext.request.contextPath}/js/uploadImg.js" type="text/javascript" charset="utf-8"></script>


</section>
</body>
</html>



