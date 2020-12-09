<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
 <script src="../js/hardware/device/devicelinechat.js"></script>   
 <script src="../js/chat/highcharts.js"></script>  
  
  <script type="text/javascript">
  
  var lvcontentCountUserNameList=new Array();
  var deltaValueList=new Array();
  var thetaList=new Array();
  var lowAlphaList=new Array();
  var highAlphaList=new Array();
  var lowBetaList=new Array();
  var highBetaList=new Array();
  var attentionList=new Array();
  var meditationList=new Array();
  
	  <!-- 波 -->
      <s:iterator value="listInfo.currentList" var="devicevo">
      lvcontentCountUserNameList.push("<s:date name="#devicevo.createTime" format="yyyy-MM-dd HH:mm:ss" />");
      deltaValueList.push(<s:property value="#devicevo.delta"/>);
      thetaList.push(<s:property value="#devicevo.theta"/>);
      lowAlphaList.push(<s:property value="#devicevo.lowAlpha"/>);
      highAlphaList.push(<s:property value="#devicevo.highAlpha"/>);
      lowBetaList.push(<s:property value="#devicevo.lowBeta"/>);
      highBetaList.push(<s:property value="#devicevo.highBeta"/>);
      attentionList.push(<s:property value="#devicevo.attention"/>);
      meditationList.push(<s:property value="#devicevo.meditation"/>);
      
             
      </s:iterator>
	</script>
  <title>脑电波</title>
</head>
<body>
   <jsp:include page="/common/commonattr.jsp"></jsp:include>
    <jsp:include page="/common/top.jsp"></jsp:include>
    <jsp:include page="/common/left.jsp"></jsp:include>
    
    <div class="main">
   
<s:set name="listInfo" value="listInfo">
	</s:set>
      
  <div class="container">
    <div id="search_bar" class="mt10">
       <div class="box">
          <div class="box_border">
            <div class="box_top"><b class="pl15">搜索</b></div>
            <div class="box_center pt10 pb10">
              <table class="form_table" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                   <form action="../device/devicelinechat.htm" id="searchsForm" name="regForm" method="get"  onSubmit="return false">
                     起日期&nbsp;&nbsp;<input type="text" class="input-text lh25" id="startTime" name="startTime" value="<s:date name="startTime" format="yyyy-MM-dd" />" class="datepickerUI" readonly="readonly">				  
			<select name="startHour" class="select" id="startHour">
                     		<option value=''>时</option >
                       <s:bean name="org.apache.struts2.util.Counter">
                       <s:param name="first" value="0" />
                       <s:param name="last" value="23" />
                       <s:iterator>
                           <option value='<s:property value="current-1"/>' <s:if test='startHour==(current-1)'>selected="selected"</s:if>><s:property value="current-1"/></option >
                       </s:iterator>
                    </s:bean>
                     </select>
                     <select name="startMinute" class="select" id="startMinute">
                     		<option value=''>分</option >
                       <s:bean name="org.apache.struts2.util.Counter">
                       <s:param name="first" value="0" />
                       <s:param name="last" value="59" />
                       <s:iterator>
                           <option value='<s:property value="current-1"/>' <s:if test='startMinute==(current-1)'>selected="selected"</s:if>><s:property value="current-1"/></option >
                       </s:iterator>
                    </s:bean>
                     </select>
			  止日期&nbsp;&nbsp;<input type="text" class="input-text lh25" id="endTime" name="endTime" value="<s:date name="endTime" format="yyyy-MM-dd" />"  class="datepickerUI" readonly="readonly">
			  <select name="endHour" class="select" id="endHour">
                     		<option value=''>时</option >
                       <s:bean name="org.apache.struts2.util.Counter">
                       <s:param name="first" value="0" />
                       <s:param name="last" value="23" />
                       <s:iterator>
                           <option value='<s:property value="current-1"/>' <s:if test='endHour==(current-1)'>selected="selected"</s:if>><s:property value="current-1"/></option >
                       </s:iterator>
                    </s:bean>
                     </select>
                     <select name="endMinute" class="select" id="endMinute">
                     		<option value=''>分</option >
                       <s:bean name="org.apache.struts2.util.Counter">
                       <s:param name="first" value="0" />
                       <s:param name="last" value="59" />
                       <s:iterator>
                           <option value='<s:property value="current-1"/>' <s:if test='endMinute==(current-1)'>selected="selected"</s:if>><s:property value="current-1"/></option >
                       </s:iterator>
                    </s:bean>
                     </select>
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			             <s:set var="shefenIddd" value="shefenId"></s:set>
			             <select name="shefenId" class="select" id="shefenId">
                          <s:iterator var="device" value="devices">
                             <option value='<s:property value="#device.shefenId"/>' <s:if test='#device.shefenId==#shefenIddd'>selected="selected"</s:if>><s:property value="#device.labelName"/></option >
                          </s:iterator>
					     </select>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        排序&nbsp;&nbsp;
			            <select name="sortData" class="select">
                             <option value='0' <s:if test='sortData==null||sortData==0'>selected="selected"</s:if>>时间降序</option >
                             <option value='1' <s:if test='sortData==1'>selected="selected"</s:if>>时间升序</option >
					     </select>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				  
			  <input type="button" value="查询数据" class="ext_btn ext_btn_submit" id="searchs"> 
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
			   <input type="checkbox" <s:if test='%{tag=="1"}'>checked="checked"</s:if> value="1" name="tag" id="autoflush"><label for="autoflush">自动刷新</label>
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
                   <tbody>
                    <tr>
                        <td><span>图表1</span><div style="margin-top: 20px;" id="lvcontentCount"></div></td>
                    </tr>
                    <tr>
                        <td><span>图表2</span><div style="margin-top: 50px;" id="lvcontentCountSport"></div></td>
                    </tr>
                  </tbody>
              </table>
              <div class="page mt10">
                <%--<jsp:include page="/common/pagebar.jsp"></jsp:include> --%>
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
   
 