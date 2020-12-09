<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="container" id="app">
		<div class="page tabbar js_show">
			<div class="page__bd">
				<c:if test="${!empty stuNo}">
					当前扫码信息:${stuNo}<br/><br/>
				</c:if>
				<c:if test="${empty userInfo}">
					<a href="user/toBind?openId=${openId}&stuNo=${stuNo}" class="weui-btn weui-btn_default">注册信息</a>
				</c:if>
				<c:if test="${not empty userInfo}">
					<div class="weui-cells weui-cells_form" style="margin-top: 1px;">
						<div class="weui-cells weui-cells_radio">
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">昵称:${userInfo.nickname}</label>
								</div>
								<%-- <div class="weui-cell__bd">
									<input readonly class="weui-input" type="text"
										value="${userInfo.nickname}">
								</div> --%>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">真实姓名:${userInfo.name}</label>
								</div>
								<%-- <div class="weui-cell__bd">
									<input readonly class="weui-input" type="text"
										value="${userInfo.name}">
								</div> --%>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">学号:${userInfo.stu_no}</label>
								</div>
								<%-- <div class="weui-cell__bd">
									<input readonly class="weui-input" type="text"
										value="${userInfo.stu_no}">
								</div> --%>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">电话号码:${userInfo.phone}</label>
								</div>
								<%-- <div class="weui-cell__bd">
									<input readonly class="weui-input" type="text"
										value="${userInfo.phone}">
								</div> --%>
							</div>
							<%-- <div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">性别</label>
								</div>
								<div class="weui-cell__bd">
									<input readonly class="weui-input" type="text"
										value='${e_stu.sex eq 1? "男":"女"}'>
								</div>
							</div> --%>
						</div>
						<div class="weui-btn-area">
							<div style="margin: 5px; color: red;" id="tips">
								<p class="msg"></p>
							</div>
							<a href="user/toBind?openId=${openId}&stuNo=${stuNo}" class="weui-btn weui-btn_default">编辑</a>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<div id="loadingToast" style="opacity: 1; display: none;">
		<div class="weui-mask_transparent"></div>
		<div class="weui-toast">
			<i class="weui-loading weui-icon_toast"></i>
			<p class="weui-toast__content">数据加载中</p>
		</div>
	</div>
</body>
</html>