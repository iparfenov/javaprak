<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Projects" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ page import="com.prak.web.DAO.ProjectDAO" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <head>
        <title>${project.getName()}</title>
    </head>
    <body>
        <jsp:include page="/templates/header.jsp"/>
        <table style="width: 100%">
            <h1 style="text-align: center">
                ${project.getName()}
            </h1>
            <p>Руководитель: <a href="/employees/${project.getHead().getId()}">${project.getHead().getName()}</a></p>
            <br>
            <tr>
                <c:if test="${employee.getIs_admin()}">
                    <th style="border: 0px; width: 2%"></th>
                </c:if>
                <th>Служащий</th>
                <th>Дата назначения</th>
                <th>Дата снятия</th>
                <th>Роль</th>
            </tr>

            <c:forEach var="e" items="${pdao.getEmployees(project)}">
                <tr>
                    <c:if test="${employee.getIs_admin()}">
                        <td style="border: 0px; border-top: 1px solid black; height: 20px; width: 2%">
                        <c:if test="${pdao.getEmployeeQuitAt(project, e) == null}">
                            <form method="POST" action="/projects/${project.getId()}/deleteFromProject">
                                <input type="hidden" name="id" value="${e.getId()}"/>
                                <button style="background-color: red; height: 18px">X</button>
                            </form>
                        </c:if>
                        </td>
                    </c:if>
                    <td><a href="/employees/${e.getId()}">${e.getName()}</a></td>
                    <td>${pdao.getEmployeeAppointedAt(project, e).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</td>
                    <td>${pdao.getEmployeeQuitAt(project, e).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</td>
                    <td>${pdao.getEmployeePosition(project, e)}</td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${employee.getIs_admin()}">
            <c:if test="${employees == null}">
                <td style="border: 0px; border-top: 1px solid black; height: 20px; width: 2%">
                    <form method="GET" action="/projects/${project.getId()}/addToProject">
                        <button style="background-color: green; height: 18px">+</button>
                    </form>
                </td>
            </c:if>
            <c:if test="${employees != null}">
                <p>Добавить служащего в проект</p>
                <form method="POST" action="/projects/addToProject">
                    <select name="eid">
                        <c:forEach var="e" items="${employees}">
                            <option value="${e.getId()}">${e.getName()}</option>
                        </c:forEach>
                    </select>
                    <p>Должность в проекте:</p>
                    <input name="position"/>
                    <input type="hidden" name="id" value="${project.getId()}"/>
                    <button>Добавить</button>
                </form>
            </c:if>
        </c:if>
    </body>
</html>
