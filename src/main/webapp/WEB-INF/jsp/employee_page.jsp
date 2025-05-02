<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Employees" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.prak.web.entities.Employee_history" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <head>
        <title>${data.getName()}</title>
    </head>
    <body>
        <jsp:include page="/templates/header.jsp"/>
        <h1 style="text-align: center">${data.getName()}, ${data.getPosition()}</h1>
        <p style="text-align: center">Работает в компании с ${data.getWorking_since().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy"))}</p>

        <p>День рождения: ${data.getBirthday().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</p>

        <c:if test="${data.getEducation() != null}">
            <p>Образование: ${data.getEducation()}
                <c:if test="${data.getId() == employee.getId() || employee.getIs_admin()}">
                    <a style="margin-left: 10px" href="/employees/${data.getId()}/changeEducation">изменить</a>
                </c:if>
            </p>
            <c:if test="${changing_education}">
                <form method="POST" action="/employees/${data.getId()}/changeEducation">
                    <p>Новый текст: <input name="education"></p>
                    <button>Подтвердить</button>
                </form>
            </c:if>
        </c:if>

        <p>Электронная почта: ${data.getEmail()}
            <c:if test="${data.getId() == employee.getId() || employee.getIs_admin()}">
                <a style="margin-left: 10px" href="/employees/${data.getId()}/changeEmail">изменить</a>
            </c:if>
            <c:if test="${changing_email}">
                <form method="POST" action="/employees/${data.getId()}/changeEmail">
                    <p>Новый E-mail: <input name="email"></p>
                    <button>Подтвердить</button>
                </form>
            </c:if>
        </p>

        <c:if test="${data.getId() == employee.getId() || employee.getIs_admin()}">
            <p>Адрес: ${data.getAddress()}
                <a style="margin-left: 10px" href="/employees/${data.getId()}/changeAddress">изменить</a>
            </p>
            <c:if test="${changing_address}">
                <form method="POST" action="/employees/${data.getId()}/changeAddress">
                    <p>Новый адрес: <input name="address"></p>
                    <button>Подтвердить</button>
                </form>
            </c:if>
        </c:if>

        <h2 style="text-align: center">История работы</h2>
        <table>
            <tr>
                <th>Позиция</th>
                <th>Дата назначения</th>
            </tr>
            <% Employee_history history = (Employee_history) request.getAttribute("history");
                for (int i = 0; i < history.getPositions().size(); i += 1) { %>
                <tr>
<%--                    <td>${history.getPositions[<%=i%>]}<td>--%>
<%--                    <td>${history.getPromoted_at()[<%=i%>}</td>--%>
                    <td><%=history.getPositions().get(i)%></td>
                    <td><%=history.getPromoted_at().get(i).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))%></td>
                </tr>
            <% } %>
        </table>

        <c:if test="${employee.getIs_admin()}">
            <a style="margin-left: 10px" href="/employees/${data.getId()}/promote">Изменить позицию</a>
        </c:if>
        <c:if test="${changing_position}">
            <form method="POST" action="/employees/${data.getId()}/promote">
                <p>Новая позиция: <input name="position"></p>
                <button>Подтвердить</button>
            </form>
        </c:if>

    </body>
</html>