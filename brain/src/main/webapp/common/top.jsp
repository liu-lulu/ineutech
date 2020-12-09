<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#loading-mask{
        position:absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
        z-index:20000;
        background-color:gray;
        filter:alpha(opacity=50); /*IE滤镜，透明度50%*/
        -moz-opacity:0.5; /*Firefox私有，透明度50%*/
        opacity:0.5;/*其他，透明度50%*/
    }
    #loading{
        position:absolute;
        left:45%;
        top:45%;
        padding:2px;
        z-index:20001;
        height:auto;
 }
    #loading .loading-indicator{
        color:#444;
        font:bold 20px tahoma,arial,helvetica;
        padding:10px;
        margin:0;
        height:auto;
    }
    #loading-msg {
        font: normal 18px arial,tahoma,sans-serif;
		margin-top:20px;
    }
</style>
<!-- 进度 -->
<div id="loadingPop" style="display: none;">
<div id='loading-mask'></div>
<div id="loading">
    <div class="loading-indicator">
       <img src="../images/run.gif" width="35" height="35" style="margin-right:8px;float:left; vertical-align:middle;"/>
       <br/>
	   <p id="loading-msg">Loading ...</p>
    </div>
</div>
</div>

<div class="top">
      <div id="top_t">
        <div id="logo" class="fl" style="margin-left:20px;"></div>
        <div id="photo_info" class="fr">
          <div id="photo" class="fl">
            <a href="#"><img src="../images/a.png" alt="" width="60" height="60"></a>
          </div>
          <div id="base_info" class="fr">
            <div class="help_info">
              <%--../user/clearLogin.htm --%><a id="hp">&nbsp;</a>
              <a id="gy">&nbsp;</a>
              <a href="../user/clearLogin.htm" id="out">&nbsp;</a>
            </div>
            <div class="info_center">
            <c:choose>
              <c:when test="${session.kkbcPassport!=null}">
			    ${session.kkbcPassport.userName}
			</c:when>
			<c:otherwise>
			    管理员
			</c:otherwise>
			</c:choose>
            </div>
          </div>
        </div>
      </div>
      <div id="side_here">
        <div id="side_here_l" class="fl"></div>
        <div id="here_area" class="fl">当前位置：
        <c:if test="${session.kkbcPassport!=null}">
        <c:choose>
			<c:when test="${session.kkbcPassport.schoolId==2550}">威海幼儿园</c:when><c:when test="${session.kkbcPassport.schoolId==4017}">莘庄幼儿园</c:when><c:when test="${session.kkbcPassport.schoolId==100}">襄一</c:when><c:when test="${session.kkbcPassport.schoolId==2559}">机关建国</c:when>
		</c:choose>
		后台系统
		</c:if>
        </div>
      </div>
    </div>
