package com.prak.web;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.lang.Thread;
import java.time.Duration;

@SpringBootTest(classes = WebPrak.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class Webpage_test extends AbstractTestNGSpringContextTests {

    private WebDriver driver;

    private List<WebElement> getNav() {
        return driver.findElement(By.tagName("nav")).findElements(By.tagName("a"));
    }

    private void waitLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(1)).until(
                    driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));
    }

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test(groups = { "login" })
    public void testLoginFail() throws InterruptedException {
        driver.get("http://localhost:8080");

        Assert.assertEquals(driver.getTitle(), "Вход");

        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.get(0).sendKeys("svetlana");
        inputs.get(1).sendKeys("wrong password");

        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.id("error-message")).getText(), "Неправильный логин/пароль!");
        Assert.assertEquals(driver.getTitle(), "Вход");
    }

    @Test(groups = { "login" })
    public void testLoginSuccess() throws InterruptedException {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.get(0).sendKeys("svetlana");
        inputs.get(1).sendKeys("passwordThatDoesNotPissOffGoogle");
        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();
        waitLoad();
        Assert.assertEquals(driver.getTitle(), "Главная страница");
    }

    @Test(dependsOnGroups = { "login" }, groups = { "home" })
    public void testHomeNav() throws InterruptedException {
        driver.get("http://localhost:8080/home");
        Assert.assertEquals(driver.getTitle(), "Главная страница");
        List<WebElement> nav = getNav();
        Assert.assertEquals(nav.size(), 7);
    }

    @Test(dependsOnGroups = { "login" }, groups = { "home" })
    public void testHomeProjectLink() {
        driver.get("http://localhost:8080/home");
        WebElement projectLink = driver.findElement(By.id("projects")).findElements(By.tagName("a")).get(0);
        Assert.assertEquals(projectLink.getText(), "проект1");
        projectLink.click();
        Assert.assertEquals(driver.getTitle(), "проект1");
    }

    @Test(dependsOnGroups = { "login" }, groups = { "home" })
    public void testHomeBonusLink() {
        driver.get("http://localhost:8080/home");
        WebElement projectLink = driver.findElement(By.id("payouts")).findElements(By.tagName("a")).get(0);
        Assert.assertEquals(projectLink.getText(), "Премия на день рождения");
        projectLink.click();
        Assert.assertEquals(driver.getTitle(), "Премии");
        WebElement highlightedBonus = driver.findElement(By.className("highlighted"));
        Assert.assertEquals(highlightedBonus.findElements(By.tagName("td")).get(0).getText(), "Премия на день рождения");
    }

    @Test(dependsOnGroups = { "login" }, groups = { "projects" })
    public void testProjectsEmployeeLink() throws InterruptedException {
        driver.get("http://localhost:8080/projects/1");
        Assert.assertEquals(driver.getTitle(), "проект1");
        WebElement employeeLink = driver.findElement(By.tagName("table")).findElement(By.tagName("a"));
        Assert.assertEquals(employeeLink.getText(), "Светлана Ищенко");
        employeeLink.click();
        waitLoad();
        Assert.assertEquals(driver.getTitle(), "Светлана Ищенко");
    }

    @Test(dependsOnGroups = { "login" }, groups = { "projects" })
    public void testProjectsCreate() throws InterruptedException {
        driver.get("http://localhost:8080/projects");
        Assert.assertEquals(driver.getTitle(), "Проекты");

        driver.findElement(By.id("new-proj")).click();
        waitLoad();

        WebElement form = driver.findElement(By.tagName("form"));

        form.findElement(By.tagName("input")).sendKeys("новый проект");
        form.findElement(By.tagName("button")).click();
        waitLoad();
        Thread.sleep(500);
        Assert.assertEquals(driver.getTitle(), "Проекты");
        Assert.assertEquals(driver.findElements(By.tagName("tr")).get(4).findElement(By.tagName("td")).getText(), "новый проект");
    }

    @Test(dependsOnGroups = { "login" }, groups = { "employees" })
    public void testEmployeeEduChange() throws InterruptedException {
        driver.get("http://localhost:8080/employees/1");
        Assert.assertEquals(driver.getTitle(), "Светлана Ищенко");
        driver.findElement(By.id("change-edu")).click();
        Thread.sleep(500);

        WebElement form = driver.findElement(By.id("change-edu-form"));
        form.findElement(By.tagName("input")).sendKeys("новое значение");
        form.findElement(By.tagName("button")).click();
        Thread.sleep(500);

        Assert.assertTrue(driver.findElement(By.id("edu")).getText().contains("Образование: новое значение"));
    }

    @Test(dependsOnGroups = { "login" }, groups = { "employees" })
    public void testEmployeeEmailChange() throws InterruptedException {
        driver.get("http://localhost:8080/employees/1");
        Assert.assertEquals(driver.getTitle(), "Светлана Ищенко");
        driver.findElement(By.id("change-email")).click();
        Thread.sleep(500);

        WebElement form = driver.findElement(By.id("change-email-form"));
        form.findElement(By.tagName("input")).sendKeys("новое значение");
        form.findElement(By.tagName("button")).click();
        Thread.sleep(500);

        Assert.assertTrue(driver.findElement(By.id("email")).getText().contains("Электронная почта: новое значение"));
    }

    @Test(dependsOnGroups = { "login" }, groups = { "employees" })
    public void testEmployeeAddressChange() throws InterruptedException {
        driver.get("http://localhost:8080/employees/1");
        Assert.assertEquals(driver.getTitle(), "Светлана Ищенко");
        driver.findElement(By.id("change-address")).click();
        Thread.sleep(500);

        WebElement form = driver.findElement(By.id("change-address-form"));
        form.findElement(By.tagName("input")).sendKeys("новое значение");
        form.findElement(By.tagName("button")).click();
        Thread.sleep(500);

        Assert.assertTrue(driver.findElement(By.id("address")).getText().contains("Адрес: новое значение"));
    }

    @Test(dependsOnGroups = { "login" }, groups = { "employees" })
    public void testEmployeePositionChange() throws InterruptedException {
        driver.get("http://localhost:8080/employees/1");
        Assert.assertEquals(driver.getTitle(), "Светлана Ищенко");
        driver.findElement(By.id("change-pos")).click();
        Thread.sleep(500);

        WebElement form = driver.findElement(By.id("change-pos-form"));
        form.findElement(By.tagName("input")).sendKeys("новое значение");
        form.findElement(By.tagName("button")).click();
        Thread.sleep(500);

        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "Светлана Ищенко, новое значение");
    }

    @Test(dependsOnGroups = { "login" }, groups = { "search" })
    public void testSearch() throws InterruptedException {
        driver.get("http://localhost:8080/employees");
        Assert.assertEquals(driver.getTitle(), "Поиск сотрудников");
        driver.findElement(By.tagName("input")).sendKeys("на");
        driver.findElement(By.tagName("button")).click();
        Thread.sleep(500);

        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> names = table.findElements(By.tagName("a"));
        Assert.assertEquals(names.size(), 2);
        Assert.assertTrue(names.get(0).getText().contains("на"));
        Assert.assertTrue(names.get(1).getText().contains("на"));
    }

    @Test(dependsOnGroups = { "login" }, groups = { "search" })
    public void testSearchAddEmployee() throws InterruptedException {
        driver.get("http://localhost:8080/employees");
        Assert.assertEquals(driver.getTitle(), "Поиск сотрудников");
        driver.findElement(By.id("add-button")).click();
        Thread.sleep(500);

        WebElement form = driver.findElement(By.id("add-form"));
        List<WebElement> inputs = form.findElements(By.tagName("input"));

        inputs.get(0).sendKeys("name");
        inputs.get(1).sendKeys("addr");
        inputs.get(3).sendKeys("pos");
        inputs.get(4).sendKeys("email");
        inputs.get(6).sendKeys("LogIn");
        inputs.get(7).sendKeys("VeryGoodPasswordIstg");
        form.findElement(By.tagName("button")).click();
        Thread.sleep(500);

        Assert.assertEquals(driver.getTitle(), "Поиск сотрудников");
    }

    @Test(dependsOnGroups = {"login", "home", "projects", "employees", "search"})
    public void testLogout() throws InterruptedException {
        driver.get("http://localhost:8080/home");
        driver.findElement(By.id("nav-logout")).click();
        waitLoad();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8080/login");
    }

    @Test(dependsOnMethods = { "testLogout" })
    public void testNewEmpLogin() throws InterruptedException {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        inputs.get(0).sendKeys("LogIn");
        inputs.get(1).sendKeys("VeryGoodPasswordIstg");
        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.getTitle(), "Главная страница");
    }

    @Test(dependsOnMethods = { "testNewEmpLogin" })
    public void testNewEmpPage() throws InterruptedException {
        driver.findElement(By.id("nav-my-page")).click();
        waitLoad();

        Assert.assertEquals(driver.getTitle(), "name");
        Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("name, pos"));
        Assert.assertTrue(driver.findElement(By.id("address")).getText().contains("addr"));
        Assert.assertTrue(driver.findElement(By.id("email")).getText().contains("email"));
    }

    @AfterClass
    public void cleanUp() {
        driver.quit();
    }

}
