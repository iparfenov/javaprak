<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Employees" %>
<!DOCTYPE html>
<html>
    <body>
        <nav style="margin-bottom: 10px">
            <div style="display: inline-flex; justify-content: space-around; width: 50%">
                <a id="nav-home" href="/home">Главная</a>
                <a id="nav-projects" href="/projects">Проекты</a>
                <a id="nav-bonuses" href="/bonuses">Премии</a>
                <a id="nav-employees" href="/employees">Поиск сотрудников</a>
                <c:if test="${employee.getIs_admin()}">
                    <a href="/payouts">Выплаты</a>
                </c:if>
            </div>
            <div style="display: inline-flex; justify-content: space-around; width: 40%"></div>
            <div style="display: inline-flex; justify-content: space-around; width: 9%">
                <a id="nav-logout" href="/logout">Выйти</a>
                <a id="nav-my-page" href="/employees/${employee.getId()}">${employee.getLogin()}</a>
            </div>
        </nav>
        <c:if test="${error != null}">
            <h3 style="border: 5px red solid; text-align: center">Ошибка: ${error}</h3>
        </c:if>
    </body>
</html>
