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
    <title>Meals table</title>
    <link rel="stylesheet" type="text/css" href="style.css" >
</head>
<body>
<table border="1" cellpadding="5" cellspacing="1" >
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
       <!-- <th>Edit</th>
        <th>Delete</th> -->
    </tr>
    <c:forEach items="${mealList}" var="meal" >
        <tr class="${meal.exceed}">
            <td >${meal.dateTime}</td>
            <td >${meal.description}</td>
            <td >${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
