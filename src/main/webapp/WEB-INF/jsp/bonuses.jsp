<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <style>
        table, th, td {
            border: 1px solid black;
        }
        .highlighted {
            background-color: yellow;
        }
    </style>
    <head>
        <title>Премии</title>
    </head>
    <body>
        <jsp:include page="/templates/header.jsp"/>
        <table style="width: 100%">
            <tr>
                <th>Название</th>
                <th>Процент зарплаты</th>
            </tr>
            <c:forEach var="b" items="${bonuses}">
                <tr <c:if test="${id == b.getId()}">class="highlighted" </c:if> >
                    <td>${b.getName()}</td>
                    <td>${b.getPercentage()}%</td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${employee.getIs_admin()}">
            <c:if test="${add == null}">
                <a href="/bonuses/add">Добавить премию</a>
            </c:if>
            <c:if test="${add != null}">
                <form method="POST" action="/bonuses/add">
                    <p>Название:</p>
                    <input name="name">
                    <p>Процент зарплаты:</p>
                    <input name="percentage">
                    <button>Создать</button>
                </form>
            </c:if>
        </c:if>
    </body>
</html>
