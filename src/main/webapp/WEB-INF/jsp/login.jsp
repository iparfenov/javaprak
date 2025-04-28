<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <head>
        <title>Вход</title>
    </head>
    <body>
        <c:if test="${login_successful != null && !login_successful}">
            <h3>Неправильный логин/пароль!</h3>
        </c:if>
        <form action="login" method="post">
            <p>Логин:</p>
            <input name="login">
            <p>Пароль:</p>
            <input type="password" name="password">
            <br>
            <input type="submit" value="Войти">
        </form>
    </body>
</html>