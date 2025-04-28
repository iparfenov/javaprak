<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Projects" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <head>Проекты</head>
    <body>
        <jsp:include page="/templates/header.jsp"/>
        <table style="width: 100%">
            <tr>
                <th>Название</th>
                <th>Дата начала</th>
                <th>Дата завершения</th>
                <th>Руководитель</th>
            </tr>

            <c:forEach var="p" items="${projects}">
                <tr>
                    <td><a href="projects/${p.getId()}">${p.getName()}</a></td>
                    <td>${p.getStart().toString()}</td>
                    <td>${p.getEnd().toString()}</td>
                    <td><a href="employees/${p.getHead().getId()}">${p.getHead().getName()}</a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
