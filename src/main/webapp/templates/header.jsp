<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.prak.web.entities.Employees" %>
<!DOCTYPE html>
<style>
</style>
<html>
    <body>
        <nav style="margin-bottom: 10px">
            <div style="display: inline-flex; justify-content: space-around; width: 49%">
                <a href="/home">Главная</a>
                <a href="/projects">Проекты</a>
                <a href="/salary">Моя зарплата</a>
                <a href="/search">Поиск сотрудников</a>
            </div>
            <div style="display: inline-flex; justify-content: flex-end; width: 50%">
                <a href="/${employee.getId()}">${employee.getLogin()}</a>
            </div>
        </nav>
    </body>
</html>