<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Facebike Data</title>
</head>
<body>
<h2>Items in Facebike</h2>
	<c:forEach items="${list}" var="item">
	 <h2>${item.Full Name} </h2>
	</c:forEach>
</body>
</html>