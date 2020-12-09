<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<%
String url="iframe.do";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript">
console.log("<%=url%>");
</script>


</head>

<body>
main page<br>
<iframe src="<%=url %>" width="600px" height="400px"></iframe>
</body>

</html>