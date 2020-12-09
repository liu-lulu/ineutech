<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<div class="side">
        <div class="sideMenu" style="margin:0 auto" align="justify">
        <!-- 公共部分 -->
          <h3 attr-href="../device/golisttest.htm">硬件</h3>
          <ul>
          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"device/golisttest.htm")>0}'>class="on" id="currentLeftIndex" attr-value="0"</c:if>><a style="cursor: pointer;" attr-href="../device/golisttest.htm">硬件列表</a></li>
          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"device/devicelinechat.htm")>0}'>class="on" id="currentLeftIndex" attr-value="0"</c:if>><a style="cursor: pointer;" attr-href="../device/devicelinechat.htm">脑电波图表</a></li>
          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"device/loginloglist.htm")>0}'>class="on" id="currentLeftIndex" attr-value="0"</c:if>><a style="cursor: pointer;" attr-href="../device/loginloglist.htm">脑波登录日志</a></li>
          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"device/godeviceshowchat.htm")>0}'>class="on" id="currentLeftIndex" attr-value="0"</c:if>><a style="cursor: pointer;" attr-href="../device/godeviceshowchat.htm">展示</a></li>
          <!-- <li <c:if test='%{#request["javax.servlet.forward.request_uri"].indexOf("device/godeviceshowchat2.htm")>0}'>class="on" id="currentLeftIndex" attr-value="0"</c:if>><a style="cursor: pointer;" attr-href="../device/godeviceshowchat2.htm">展示2</a></li> -->
          </ul>
       </div>
    </div>
	
