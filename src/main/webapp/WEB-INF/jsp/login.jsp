<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <head>
        <title>Вход</title>
    </head>
    <body>
        <c:if test="${login_successful != null && !login_successful}">
            <h3 style="background-color: red;" id="error-message">Неправильный логин/пароль!</h3>
        </c:if>
        <form id="login" action="login" method="post">
            <p>Логин:</p>
            <input name="login">
            <p>Пароль:</p>
            <input type="password" name="password">
            <br>
            <button>Войти</button>
        </form>
    </body>
</html>
