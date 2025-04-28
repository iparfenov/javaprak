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
    </body>
</html>
