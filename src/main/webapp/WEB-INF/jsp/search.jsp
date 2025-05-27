<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ page import="java.util.Calendar" %>
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
            <button>Поиск</button>
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

        <c:if test="${employee.getIs_admin() && !adding_employee}">
            <a id="add-button" href="/employees/add">Добавить нового сотрудника</a>
        </c:if>

        <c:if test="${employee.getIs_admin() && adding_employee}">
            <%
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
            %>
            <form id="add-form" method="POST" action="/employees/add">
                <p>Имя: <input name="name"></p>
                <p>Адрес: <input name="address"> </p>
                <p>День рождения:
                    <select name="birthday_year">
                        <% for (int i = 1900; i <= year; i += 1) { %>
                        <option value="<%=i%>"><%=i%></option>
                        <% } %>
                    </select>
                    <select name="birthday_month">
                        <% for (int i = 0; i < 12; i += 1) { %>
                        <option value="<%=i%>"><%=i+1%></option>
                        <% } %>
                    </select>
                    <select name="birthday_day">
                        <% for (int i = 1; i <= 31; i += 1) { %>
                        <option value="<%=i%>"><%=i%></option>
                        <% } %>
                    </select>
                </p>
                <p> Образование: <input name="education"> </p>
                <p>Дата начала работы:
                    <select name="working_since_year">
                        <% for (int i = 1970; i <= year; i += 1) { %>
                        <option value="<%=i%>" <% if (i == year) out.print("selected"); %>><%=i%></option>
                        <% } %>
                    </select>
                    <select name="working_since_month">
                        <% for (int i = 0; i < 12; i += 1) { %>
                        <option value="<%=i%>" <% if (i == month) out.print("selected"); %>><%=i+1%></option>
                        <% } %>
                    </select>
                    <select name="working_since_day">
                        <% for (int i = 1; i <= 31; i += 1) { %>
                        <option value="<%=i%>" <% if (i == day) out.print("selected"); %>><%=i%></option>
                        <% } %>
                    </select>
                </p>
                <p>Должность: <input name="position"></p>
                <p>E-mail: <input name="email"> </p>
                <p>Является администратором: <input type="checkbox" name="is_admin" value="true"> </p>
                <p>Логин: <input name="login"> </p>
                <p>Пароль: <input name="password"> </p>
                <button>Добавить</button>
            </form>
        </c:if>

    </body>
</html>
