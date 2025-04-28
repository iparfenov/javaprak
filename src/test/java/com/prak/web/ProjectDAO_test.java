package com.prak.web;

import com.prak.web.DAO.EmployeeDAO;
import com.prak.web.DAO.ProjectDAO;
import com.prak.web.entities.Employees;
import com.prak.web.entities.Projects;
import com.prak.web.exceptions.MalformedRequestException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.*;
import org.testng.Assert;

import java.util.Date;
import java.util.List;

public class ProjectDAO_test {

    SessionFactory sf = new Configuration().configure().buildSessionFactory();
    Session s = sf.openSession();

    ProjectDAO dao = new ProjectDAO(s);
    Projects p = new Projects();
    Employees e1;
    Employees e3;

    @BeforeClass
    public void setUp() {
        EmployeeDAO eDao = new EmployeeDAO(s);
        e1 = eDao.getById(1);
        e3 = eDao.getById(3);

        p.setName("project");
        p.setHead(e1);
    }

    @Test
    public void testCreate() {
        dao.create(p, e1);
        p = dao.getById(p.getId());
        Assert.assertNotNull(p);
        Assert.assertNotNull(p.getStart());
    }

    @Test
    public void testCreateWithDate() {
        Projects p2 = new Projects();
        p2.setName("project2");
        p2.setHead(e1);
        p2.setStart(new Date());
        dao.create(p2, e1);
        p2 = dao.getById(p2.getId());
        Assert.assertNotNull(p2);
    }
    @Test(dependsOnMethods = {"testCreate"})
    public void testUpdate() {
        p.setName("project__");
        p = dao.update(p, e1);
        Assert.assertEquals(p.getName(), "project__");
    }

    @Test(dependsOnMethods = {"testUpdate"})
    public void testClose() {
        Assert.assertNull(p.getEnd());
        dao.close(p, e1);
        Assert.assertNotNull(p.getEnd());
    }

    @Test(dependsOnMethods = {"testClose"})
    public void testMalformedClose() {
        try {
            dao.close(p, e1);
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testUpdate"})
    public void testGetEmployees() {
        EmployeeDAO edao = new EmployeeDAO(s);
        edao.addToProject(p.getHead(), p, "position", null);
        List<Employees> le = dao.getEmployees(p);
        Assert.assertEquals(le.size(), 1);
        Assert.assertEquals(le.getFirst(), e1);
        Assert.assertEquals(dao.getEmployeePosition(p, e1), "position");
        Assert.assertNotNull(dao.getEmployeeAppointedAt(p, e1));
        Assert.assertNull(dao.getEmployeeQuitAt(p, e1));
        edao.removeFromProject(e1, p, null);
        Assert.assertNotNull(dao.getEmployeeQuitAt(p, e1));
    }

    @Test(dependsOnMethods = {"testGetEmployees"})
    public void testMalformedEmployeeRequests() {
        try {
            dao.getEmployeePosition(p, e3);
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testGetEmployees"})
    public void testMalformedEmployeeRequests2() {
        try {
            dao.getEmployeeAppointedAt(p, e3);
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testGetEmployees"})
    public void testMalformedEmployeeRequests3() {
        try {
            dao.getEmployeeQuitAt(p, e3);
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

}
