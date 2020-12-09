<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${binded?"编辑":"绑定"}个人信息</title>
<link rel="stylesheet" href="css/weui.css" />
<link rel="stylesheet" href="css/example.css" />
<link rel="stylesheet" href="css/docs.min.css" />
<link rel="stylesheet" href="css/bootstrap.min.css" />

</head>
<body>
	<div class="container" id="container">
		<div class="page msg_success js_show">

			<div class="page__bd">
				<div style="padding-left: 10px;">
					<c:if test="${binded}">
						<c:if test="${!empty stuNo}">
						当前扫码信息:${stuNo}<br/>
						</c:if>
						<br/>
						<div class="bs-callout bs-callout-info">编辑个人信息</div>
					</c:if>
					<c:if test="${not binded}">
						<div class="bs-callout bs-callout-info">请输入个人信息以绑定</div>
					</c:if>
				</div>
				<form id="driver-info-form" class="weui-cells weui-cells_form" action="user/bind" method="post">
				<input value="${openId}" name="openId" type="hidden" placeholder="openId">
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">昵称</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" value="${userInfo.nickname}" name="nickname" type="text" placeholder="请输入昵称">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">姓名</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" type="text" name="name" value="${userInfo.name}" placeholder="请输入真实姓名">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">学号</label>
						</div>
						<div class="weui-cell__bd">
							<c:if test="${binded}">
								<input class="weui-input" type="text" name="stu_no" value="${userInfo.stu_no}" placeholder="请输入学号">
							</c:if>
							<c:if test="${not binded}">
								<input class="weui-input" type="text" name="stu_no" value="${stuNo}" placeholder="请输入学号">
							</c:if>
							
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">联系电话</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" name="phone" value="${userInfo.phone}" type="text" placeholder="请输入手机号">
						</div>
					</div>
					<!-- <label for="weuiAgree" class="weui-agree"> <input id="agree" name="agree" type="checkbox" class="weui-agree__checkbox"> <span class="weui-agree__text"> 阅读并同意<a
							href="javascript:void(0);">《相关条款》</a>
					</span>
					</label> -->

					<div class="weui-btn-area">
						<c:if test="${binded}">
							<button class="weui-btn weui-btn_warn" id="submit_btn">提交更新个人信息</button>
						</c:if>
						<c:if test="${not binded}">
							<button class="weui-btn weui-btn_primary" id="submit_btn">提交绑定</button>
						</c:if>
					</div>
				</form>
				
				<div class="weui-btn-area">
								<a
									href="javascript:WeixinJSBridge.invoke('closeWindow', {}, function(res) {});"
									class="weui-btn weui-btn_warn">返回微信</a>
				</div>


			</div>
			<%-- <jsp:include page="/common/footer.jsp"></jsp:include> --%>
		</div>
	</div>
</body>
<script type="text/javascript" src="js/jquery.min.js"></script>
<!-- <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> -->
<script type="text/javascript" src="plugins/jquery-validation/js/jquery.validate.js"></script>
<script type="text/javascript" src="plugins/jquery-validation/js/additional-methods.js"></script>
<script type="text/javascript" src="js/wechat/bind-driver.js"></script>
</html>