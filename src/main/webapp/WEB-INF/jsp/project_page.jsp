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
    <head>${project.getName()}</head>
    <body>
        <jsp:include page="/templates/header.jsp"/>
        <table style="width: 100%">
            <h1 style="text-align: center">${project.getName()}</h1>
            <p>Руководитель: <a href="employees/${project.getHead().getId()}">${project.getHead()}</a></p>
            <br>
            <tr>
                <th>Служащий</th>
                <th>Дата назначения</th>
                <th>Дата снятия</th>
                <th>Роль</th>
            </tr>

            <c:forEach var="e" items="${pdao.getEmployees(project)}">
                <tr>
                    <td><a href="employees/${e.getId()}">${e.getName()}</a></td>
                    <td>${pdao.getEmployeeAppointedAt(project, e).toString()}</td>
                    <td>${pdao.getEmployeeQuitAt(project, e).toString()}</td>
                    <td>${pdao.getEmployeePosition(project, e)}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
