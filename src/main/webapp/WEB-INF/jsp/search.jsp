<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <head>
        <title>Поиск сотрудников</title>
    </head>
    <body>
        <jsp:include page="/templates/header.jsp"/>
        <form action="/employees" method="post">
            <nobr>
                <p>Имя: </p>
                <input name="search">
            </nobr>
            <input type="submit" value="Поиск">
        </form>
        <c:if test="${employees != null}">
            <table style="width: 100%">
                <tr>
                    <th>Имя</th>
                    <th>Должность</th>
                </tr>
                <c:forEach var="e" items="${employees}">
                    <tr>
                        <td><a href="employees/${e.getId()}">${e.getName()}</a></td>
                        <td>${e.getPosition()}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
