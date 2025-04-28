<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ page import="com.prak.web.entities.Projects" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <head>
        <title>Главная страница</title>
    </head>
        <body>
            <jsp:include page="/templates/header.jsp"/>
            <h1 style="text-align: center">Привет, ${employee.getName()}!</h1>
            <table style="width: 100%">
                <tr>
                    <th>Мои проекты</th>
                    <th>Мои выплаты</th>
                </tr>

                <tr>
                    <td style="margin: 0px auto; width: 40%">
                        <table style="width: 100%">
                            <tr>
                                <th>Название</th>
                                <th>Дата начала</th>
                                <th>Дата окончания</th>
                            </tr>
                            <c:forEach var="p" items="${projects}">
                                <tr>
                                    <td><a href="/projects/${p.getId()}">${p.getName()}</a></td>
                                    <td>${p.getStart().toString()}</td>
                                    <td>${p.getEnd().toString()}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    <td style="margin: 0px auto; width: 40%">
                        <table style="width: 100%">
                            <tr>
                                <th>Дата выплаты</th>
                                <th>Сумма</th>
                                <th>Премия</th>
                            </tr>
                            <c:forEach var="p" items="${payouts}">
                                <tr>
                                    <td>${p.getPaid_at().toString()}</td>
                                    <td>${p.getAmount()}</td>
                                    <c:if test="${p.getBonus() != null}">
                                        <td><a href="/bonuses/${p.getBonus().getId()}">${p.getBonus().getName()}</a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
</html>