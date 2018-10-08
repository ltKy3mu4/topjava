<%--
  Created by IntelliJ IDEA.
  User: wwwno_000
  Date: 06.10.2018
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Meals table</title>
    <link rel="stylesheet" type="text/css" href="style.css" >
</head>
<body>
<table border="1" cellpadding="5" cellspacing="1" >
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    <c:forEach items="${mealList}" var="meal" >
        <tr class="${meal.exceed}">
            <td ><c:out value="${meal.dateTime}"/></td>
            <td ><c:out value="${meal.description}"/></td>
            <td ><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert">Add User</a></p>
</body>
</html>
