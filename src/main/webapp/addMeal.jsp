<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Title</title>
</head>

<body>
    <form method="POST" action='meals' name="frmAddUser">
        <table border="0">
            <tr>
                <td>Date</td>
                <td><input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}" />"></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><input type="text" name="description" value="<c:out value="${meal.description}" />"></td>
            </tr>
            <tr>
                <td>Calories</td>
                <td><input type="text" name="calories" value="<c:out value="${meal.calories}" />"></td>
            </tr>
            <tr>
                <td> <input type="hidden" name="id" value="<c:out value="${meal.id}" />"> </td>
            </tr>
            <tr><input type="submit" value="Submit" /></tr>
        </table>

    </form>
</body>
</html>
