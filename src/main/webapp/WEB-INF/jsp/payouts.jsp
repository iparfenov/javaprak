<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
        <table style="width: 100%">
            <tr>
                <th>Дата</th>
                <th>Сотрудник</th>
                <th>Сумма</th>
                <th>Бонус</th>
            </tr>

            <c:forEach var="p" items="${payouts}">
                <tr>
                    <td>${p.getPaid_at().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))}</td>
                    <td>
                        <a href="/employees/${p.getId()}">${p.getEmployee().getName()}</a>
                    </td>
                    <td>${p.getAmount()}</td>
                    <td>
                        <c:if test="${p.getBonus() != null}">
                            <a href="/bonuses/${p.getBonus().getId()}"> ${p.getBonus().getName()}</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br/><br/><br/>
        <h3>Добавить выплату</h3>
        <form method="POST" action="/payouts/add">
            <p>Сотрудник: <select name="eid" required>
                            <c:forEach var="e" items="${employees}">
                                <option value="${e.getId()}">${e.getName()}</option>
                            </c:forEach>
                           </select>
            </p>
            <p>Сумма: <input name="amount" required></p>
            <p>Премия: <select name="bonus">
                        <option value="-1"></option>
                        <c:forEach var="b" items="${bonuses}">
                            <option value="${b.getId()}">${b.getName()}</option>
                        </c:forEach>
                       </select>
            </p>
            <br/>
            <button>Добавить</button>
        </form>
    </body>
</html>
