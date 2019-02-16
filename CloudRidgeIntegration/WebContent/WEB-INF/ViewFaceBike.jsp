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

	<table border="1">
            <h2>List of users in FaceBike</h2>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
            </tr>
            <c:forEach var="item" items="${list}">
                <tr>
                    <td><c:out value="${item.getId()}" /></td>
                    <td><c:out value="${item.getName()}" /></td>
                    <td><c:out value="${item.getEmail()}" /></td>
                    <td><c:out value="${item.getDepartment()}" /></td>
                </tr>
            </c:forEach>
        </table>
</body>
</html>