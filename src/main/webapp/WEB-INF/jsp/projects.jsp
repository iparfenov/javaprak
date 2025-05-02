<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Projects" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="org.apache.commons.lang3.Range" %>
<%@ page import="java.util.Calendar" %>
<html>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <head>
        <title>Проекты</title>
    </head>
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
                    <td>${p.getStart().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</td>
                    <td>${p.getEnd().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</td>
                    <td><a href="employees/${p.getHead().getId()}">${p.getHead().getName()}</a></td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${employee.getIs_admin()}">
            <c:if test="${employees == null}">
                <a href="/projects/add">Новый проект</a>
            </c:if>
            <c:if test="${employees != null}">
                <br><br><br>
                <form method="POST" action="/projects/add">
                    <p>Название проекта: </p>
                    <input name="name">
                    <p>Руководитель:</p>
                    <select name="head">
                        <c:forEach var="e" items="${employees}">
                            <option value="${e.getId()}">${e.getName()}</option>
                        </c:forEach>
                    </select>
                    <br>
                    <p>Дата начала:</p>
                    <% Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);
                    %>
                    <select name="start_day">
                        <% for (int i = 1; i <= 31; i += 1) { %>
                        <option value="<%=i%>" <%if (i == day) out.print("selected");%>><%=i%></option>
                        <% } %>
                    </select>
                    <select name="start_month">
                        <% for (int i = 0; i < 12; i += 1) { %>
                        <option value="<%=i%>" <%if (i == month) out.print("selected");%>><%=i+1%></option>
                        <% } %>
                    </select>
                    <select name="start_year">
                        <% for (int i = 2025; i < 2035; i += 1) { %>
                        <option value="<%=i%>" <%if (i == year) out.print("selected");%>><%=i%></option>
                        <% } %>
                    </select>
                    <br>
                    <p>Дата окончания:</p>
                    <select name="end_day">
                        <% for (int i = 1; i <= 31; i += 1) { %>
                        <option value="<%=i%>"><%=i%></option>
                        <% } %>
                    </select>
                    <select name="end_month">
                        <% for (int i = 0; i < 12; i += 1) { %>
                        <option value="<%=i%>"><%=i+1%></option>
                        <% } %>
                    </select>
                    <select name="end_year">
                        <% for (int i = 2025; i < 2035; i += 1) { %>
                        <option value="<%=i%>"><%=i%></option>
                        <% } %>
                    </select>
                    <br><br>
                    <button>Создать новый проект</button>
                </form>
            </c:if>
        </c:if>
    </body>
</html>
