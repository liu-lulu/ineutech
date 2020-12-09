<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    
%>
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="../css/common.css">
  <link rel="stylesheet" href="../css/style.css">
  <script type="text/javascript" src="../js/jquery.min.js"></script>
  <script type="text/javascript" src="../js/jquery.SuperSlide.js"></script>
      <link rel="stylesheet" href="../css/common.css">
   <link rel="stylesheet" href="../css/main.css">
   <script type="text/javascript" src="../js/colResizable-1.3.min.js"></script>
   
   <link rel="stylesheet" href="../css/jquery.ui.all.css" />
  <link rel="stylesheet" href="../css/demos.css" />
  <script src="../js/jquery.ui.core.js"></script>
<script src="../js/jquery.ui.datepicker.js"></script>
<script src="../js/jquery.ui.datepicker-zh-TW.js"></script>


<script type="text/javascript" src="../js/common.js"></script>
 <script src="../js/hardware/device/loginloglist.js"></script>   
  <title>脑波登录日志数据</title>
</head>
<body>
   <jsp:include page="/common/commonattr.jsp"></jsp:include>
    <jsp:include page="/common/top.jsp"></jsp:include>
    <jsp:include page="/common/left.jsp"></jsp:include>
    
    <div class="main">
    
<%-- <c:set var="listInfo" value="deviceLoginLogListInfo">
	</c:set> --%>
      
  <div class="container">
    <div id="search_bar" class="mt10">
       <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">注:退出一种是超过15秒没上传数据到服务器，服务器强制退出，另一种是脑波程序主动退出</b></div>
            <div class="box_center pt10 pb10">
              <table class="form_table" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                   <form action="../device/loginloglist.htm" id="searchsForm" name="regForm" method="get"  onSubmit="return false">
                     起日期&nbsp;&nbsp;<input type="text" class="input-text lh25" id="startTime" name="startTime" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd" />" class="datepickerUI" readonly="readonly">				  
			  止日期&nbsp;&nbsp;<input type="text" class="input-text lh25" id="endTime" name="endTime" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd" />"  class="datepickerUI" readonly="readonly">
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			             <c:set var="shefenIddd" value="${shefenId }"></c:set>
			             <select name="shefenId" class="select" id="shefenId">
                          <option value="">请选择名称</option>
                          <c:forEach var="device" items="${devices }">
                             <option value='${device.shefen_id }' <c:if test='${device.shefen_id eq shefenIddd}'>selected="selected"</c:if>>${device.label_name}</option>
                          </c:forEach>
					     </select>
					     &nbsp;&nbsp;&nbsp;&nbsp;
					     <select name="type" class="select" id="type">
                          <option value="">请选择类型</option>
                          <option value='1' <c:if test='${type==1}'>selected="selected"</c:if>>登录</option >
                          <option value='2' <c:if test='${type==2}'>selected="selected"</c:if>>退出</option >
					     </select>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				  
			  <input type="button" value="查询数据" class="ext_btn ext_btn_submit" id="searchs"> 
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
			   <input type="checkbox" <c:if test='${tag eq "1"}'>checked="checked"</c:if> value="1" name="tag" id="autoflush"><label for="autoflush">自动刷新</label>
               </form>
              
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
    </div>
     <div id="table" class="mt10">
        <div class="box span10 oh">
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                   <thead>
                    <tr>
                      <th width="60">名称</th>
                      <th>类型</th>
                      <th>登录时间</th>
                      <th>登录IP</th>
                      <th>登录端口号</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${listInfo.currentList}" var="deviceLoginLog">
                     <tr class="success">
                      <td>
                          ${deviceLoginLog.label_name}
                      </td>
                      <td>
						<c:choose>
						<c:when test="${deviceLoginLog.type==1}">登录</c:when><c:otherwise>退出</c:otherwise>
						</c:choose>
                      </td>
                      <td>
                      <fmt:formatDate value="${deviceLoginLog.create_time}" pattern="yyyy-MM-dd HH:mm:ss" />
                      </td>
                      <td>
                          ${deviceLoginLog.remote_ip}
                      </td>
                      <td>
                          ${deviceLoginLog.remote_port}
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
              </table>
              <div class="page mt10">
               <jsp:include page="/common/pagebar.jsp"></jsp:include>
              </div>
        </div>
     </div>
     
     
      
     
   </div> 
 
    </div>
    <div class="bottom">
      <div id="bottom_bg"></div>
    </div>
    <div class="scroll">
          <a href="javascript:;" class="per" title="使用鼠标滚轴滚动侧栏" onClick="menuScroll(1);"></a>
          <a href="javascript:;" class="next" title="使用鼠标滚轴滚动侧栏" onClick="menuScroll(2);"></a>
    </div>
</body>

</html>
   
 