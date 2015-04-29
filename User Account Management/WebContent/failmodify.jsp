<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modify Failed</title>
</head>
<body>
Modify Failed.
<%
out.println("<h1>hohoho</h1>");
out.println("<h1>error code = " + request.getAttribute("errorCode") + "</h1>");
%>
</body>
</html>