package com.prak.web;

import com.prak.web.exceptions.ActionNotAllowedException;
import org.testng.Assert;
import org.testng.annotations.*;

import com.prak.web.DAO.CommonDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;

public class CommonDAO_test {

    SessionFactory sf = new Configuration().configure().buildSessionFactory();
    Session s = sf.openSession();
    CommonDAO<Employees> dao = new CommonDAO<>(s, Employees.class);
    Employees new_e = new Employees();

    public CommonDAO_test() {
        new_e.setAddress("addr");
        new_e.setBirthday(new Date());
        new_e.setEmail("email");
        new_e.setName("name");
        new_e.setLogin("login");
        new_e.setPassword("password");
        new_e.setIs_admin(false);
        new_e.setPosition("position");
        new_e.setWorking_since(new Date());
    }

    @Test
    public void testGetById() {
        Employees e = dao.getById(1);
        Assert.assertEquals(e.getId(), 1);
    }

    @Test
    public void testGetAll() {
        List<Employees> list = dao.getAll();
        Assert.assertEquals(list.size(), 3);
        Assert.assertEquals(list.get(0).getId(), 1);
        Assert.assertEquals(list.get(1).getId(), 2);
        Assert.assertEquals(list.get(2).getId(), 3);
    }

    @Test(dependsOnMethods = {"testGetAll"})
    public void testCorrectInsert() {
        dao.insert(new_e);
        Assert.assertNotNull(dao.getById(new_e.getId()));
    }

    @Test(dependsOnMethods = {"testCorrectInsert"})
    public void testCorrectUpdate() {
        new_e.setPassword("newpass");
        new_e = dao.update(new_e);
        Assert.assertEquals(new_e.getPassword(), "newpass");
    }

    @Test(dependsOnMethods = {"testCorrectUpdate"})
    public void testCorrectDelete() {
        dao.delete(new_e);
        Assert.assertNull(dao.getById(new_e.getId()));
    }

    @Test(dependsOnMethods = "testCorrectDelete")
    public void testIncorrectInsert() {
        new_e.setId(1);
        try {
            dao.insert(new_e);
        } catch (Exception e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testIncorrectInsert"})
    public void testIncorrectUpdate() {
        new_e.setId(5);
        try {
            dao.update(new_e);
        } catch (Exception e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testIncorrectUpdate"})
    public void testIncorrectDelete() {
        new_e.setId(5);
        try {
            dao.delete(new_e);
        } catch (Exception e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testPermissionc() {
        try {
            dao.checkPermissions(new_e);
        } catch (ActionNotAllowedException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }
}
