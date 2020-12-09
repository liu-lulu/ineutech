<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
   
   

<script type="text/javascript" src="../js/common.js"></script>
 <script src="../js/hardware/device/golisttest.js"></script>   
  <title>硬件列表</title>
</head>
<body>
   <jsp:include page="/common/commonattr.jsp"></jsp:include>
    <jsp:include page="/common/top.jsp"></jsp:include>
    <jsp:include page="/common/left.jsp"></jsp:include>
    
    <div class="main">
    

      
  <div class="container">
    <div id="search_bar" class="mt10">
       <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">硬件列表(${fn:length(devices)})</b></div>
            <div class="box_center pt10 pb10">
              <table class="form_table" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                         
                         <form action="../device/golisttest.htm" id="searchsForm" name="regForm" method="get"  onSubmit="return false">
                           <select name="type" class="select" id="type">
                          <option value="">请选择在线状态</option>
                          <option value='1' <c:if test='${type==1}'>selected="selected"</c:if>>在线</option>
                          <option value='2' <c:if test='${type==2}'>selected="selected"</c:if>>离线</option>
					     </select>
					     
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				  
			  <input type="button" value="查询数据" class="ext_btn ext_btn_submit" id="searchs"> 
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
                      <th width="60">身份ID</th>
                      <th width="">名称</th>
                      <th width="">座号</th>
                      <th width="60">在线状态</th>
                      <th width="40">类型</th>
                      <!-- <th width="33%">电量</th> -->
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${devices}" var="device">
                     <tr class="success">
                      <td>
                          ${device.shefen_id}
                      </td>
                      <td>
                          ${device.label_name}
                      </td>
                      <td>
                          <label id="deviceLabelName_${device.device_id}">${device.remark}</label>
                          <input type="button" value="修改" class="ext_btn ext_btn_success" onclick="editLabelName('${device.device_id}')" id="editLabelNameButton_${device.device_id}"/>
                          <div style="display: none;" id="editLabelNameDiv_${ device.device_id}">
                             <input type="text" class="input-text lh25" size="5" id="inputLabelName_${device.device_id}" value="${device.remark}">
                             <input type="button" value="提交" class="ext_btn ext_btn_success" onclick="submitLabelName('${device.device_id}')"/>
                             <input type="button" value="取消" class="ext_btn ext_btn_success" onclick="cancelLabelName('${device.device_id}')"/>
                          </div>
                      </td>
                      <td>
                      <c:choose>
                         <c:when test="${ device.isOnline==true}">在线</c:when>
                         <c:otherwise>离线</c:otherwise>
                      </c:choose>
                      </td>
                      <td>
                      <c:choose>
                         <c:when test="${device.type==1}">脑波</c:when>
                         <c:when test="${device.type==2}">手环</c:when>
                         <c:otherwise>未知</c:otherwise>
                      </c:choose>
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
   
 