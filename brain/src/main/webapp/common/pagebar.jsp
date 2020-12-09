<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="java.util.Enumeration" %>
<%
	String query = "";
	Enumeration<String> e = request.getParameterNames();
	while (e.hasMoreElements()) {
		String paramName = e.nextElement();
		if (!"currentPageNO".equals(paramName)
				&& !"pagesize".equals(paramName)) {
			query = paramName + "=" + request.getParameter(paramName) + "&"
					+ query;
		}
	}
%>
<script>
	function goPage(page) {
				var uri = "<c:set var='aa' value='#request["javax.servlet.forward.request_uri"]'/>";
				var url = uri + "?<%=query%>" + "currentPageNO=" + page;
				window.location.href = url;
	}
</script>
<c:set var="iCurrPage" value="${currentPageNO}" /> 
<c:set var="iProCount" value="${listInfo.sizeOfTotalList}" />
<c:set var="iPageCount" value="${listInfo.maxPageNO}" />
<c:set var="iPageSize" value="${pageSize}" />

<c:set var="first" value="${(iCurrPage - 1)*iPageSize + 1}" />
<%-- <c:set var="last" value="${(iCurrPage - 1)*iPageSize + fn:length(listInfo.currentList)}" /> --%>

  <c:if test="${ listInfo.maxPageNO>1}">
 <div class="pagination"><input type="hidden" id="wh_id_currentPageNO" value='${listInfo.currentPageNO}'>
 <ul>
 		<c:choose>
		<c:when test="${ listInfo.currentPageNO>1}">
		   <li class="first-child"><a title="首页" href="javascript:goPage(1)">首页</a></li>
		   <li><a title="进入到前一页" href="javascript:goPage(${iCurrPage - 1 })">上一页</a></li>
		</c:when>
		<c:otherwise>
		   <li class="first-child disabled"><a title="首页" href="#">首页</a></li>
		   <li class="disabled"><a href="#">上一页</a></li>
		</c:otherwise>
		</c:choose>
		<c:forEach items="${listInfo.pagesBar }" var="page" >
				<c:choose>
		       <c:when test="${listInfo.currentPageNO==page}">
		        <li class="active"><a  href="javascript:goPage(${page})">${page}</a></li>
		        </c:when>
		      <c:when test="${page==0}">
		           <li class="disabled"><a href="#">...</a></li>
		      </c:when>
		      <c:otherwise>
		        <li><a href="javascript:goPage(${page})">${page}</a></li>
		      </c:otherwise>
		      </c:choose>
		</c:forEach>
		<c:choose>
		<c:when test="${listInfo.currentPageNO==listInfo.maxPageNO}">
		    <li class="disabled"><a title="进入到后一页" href="#">下一页</a></li>
		    <li class="last-child disabled"><a title="尾页" href="#">尾页</a></li>
		</c:when>
		<c:otherwise>
		   <li><a title="进入到后一页" href="javascript:goPage(${iCurrPage + 1 })">下一页 </a></li>
		   <li><a class="last-child" title="尾页" href="javascript:goPage(${listInfo.maxPageNO })">尾页 </a></li>
		</c:otherwise>
		</c:choose>
	</ul>
   </div>
  </c:if>