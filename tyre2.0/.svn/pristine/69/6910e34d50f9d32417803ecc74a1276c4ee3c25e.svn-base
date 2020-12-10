<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<input type="hidden" value="${listInfo.maxPageNO}" id="totalPage"/>
<input type="hidden" value="${listInfo.sizeOfTotalList}" id="totalRecords"/>
<input type="hidden" value="${listInfo.currentPageNO}" id="pageNo"/>
<input type="hidden" value='${requestScope["javax.servlet.forward.request_uri"]}' id="hrefLatterss"/>
<input type="hidden" value="<%=query%>" id="queryss"/>
    <div id="kkpager"></div>