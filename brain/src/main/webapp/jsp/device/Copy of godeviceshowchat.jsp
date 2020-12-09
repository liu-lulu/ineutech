<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    String path=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    
%>
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <script src="../js/jquery-1.8.3.min.js"></script>  
  <script src="../js/chat/highcharts.js"></script>  
  <script src="../js/hardware/device/godeviceshowchat.js"></script>   
  
  <title>脑电波</title>
</head>
<body>
   <div id="container" style="min-width:700px;height:400px"></div>
   <input type="text" value="18FE349EA886" id="tempId1">
   <button onclick='userClick()'>换人</button>
   <div id="containerUser" style="min-width:700px;height:400px"></div>
</body>

</html>
   
 