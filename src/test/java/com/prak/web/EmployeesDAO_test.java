package com.prak.web;

import com.prak.web.DAO.EmployeeDAO;

import com.prak.web.DAO.ProjectDAO;
import com.prak.web.entities.Employees;
import com.prak.web.entities.Payouts;
import com.prak.web.entities.Projects;
import com.prak.web.exceptions.ActionNotAllowedException;
import com.prak.web.exceptions.MalformedRequestException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.*;
import org.testng.Assert;

import java.util.List;
import java.util.Date;

public class EmployeesDAO_test {

    SessionFactory sf = new Configuration().configure().buildSessionFactory();
    Session s = sf.openSession();

    ProjectDAO pdao = new ProjectDAO(s);
    Projects p = pdao.getById(1);

    EmployeeDAO edao = new EmployeeDAO(s);
    Employees e1 = edao.getById(1);
    Employees e2 = edao.getById(2);
    Employees e3 = edao.getById(3);

    Employees new_e = new Employees();

    @BeforeClass
    public void setUp() {
        new_e.setAddress("addr");
        new_e.setBirthday(new Date());
        new_e.setEmail("email");
        new_e.setName("name");
        new_e.setLogin("login");
        new_e.setPassword("password");
        new_e.setIs_admin(false);
        new_e.setPosition("position");
    }

    @Test
    public void testInsert() {
        edao.insert(new_e);
        new_e = edao.getById(new_e.getId());
        Assert.assertNotNull(new_e);
        Assert.assertNotNull(new_e.getWorking_since());
        Assert.assertNotNull(edao.getHistory(new_e));
        Assert.assertEquals(edao.getHistory(new_e).getPositions().getFirst(), new_e.getPosition());
    }

    @Test(dependsOnMethods = {"testInsert"})
    public void testSearch() {
        List<Employees> le = edao.search(new_e.getName());
        Assert.assertTrue(le.contains(new_e));
    }

    @Test(dependsOnMethods = {"testInsert"})
    public void testAdminUpdate() {
        new_e.setPassword("newpassword");
        new_e = edao.update(new_e, e1);
        Assert.assertEquals(new_e.getPassword(), "newpassword");
    }

    @Test(dependsOnMethods = {"testInsert"})
    public void testUserUpdate() {
        new_e.setPassword("newuserpassword");
        new_e = edao.update(new_e, new_e);
        Assert.assertEquals(new_e.getPassword(), "newuserpassword");
    }

    @Test
    public void testUnauthorizedUpdate() {
        try {
            edao.update(new_e, e3);
        } catch (ActionNotAllowedException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testInsert"})
    public void testPromote() {
        edao.promote(new_e, "newposition", null);
        Assert.assertEquals(edao.getHistory(new_e).getPositions().getLast(), "newposition");
        Assert.assertNotNull(edao.getHistory(new_e).getPromoted_at().getLast());
    }

    @Test(dependsOnMethods = {"testInsert"})
    public void testPromoteWithDate() {
        edao.promote(new_e, "newposition2", new Date());
        Assert.assertEquals(edao.getHistory(new_e).getPositions().getLast(), "newposition2");
        Assert.assertNotNull(edao.getHistory(new_e).getPromoted_at().getLast());
    }

    @Test(dependsOnMethods = {"testInsert"})
    public void testMalformedChangePositionInProject() {
        try {
            edao.changePositionInProject(new_e, p, "newposition");
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testInsert", "testMalformedChangePositionInProject"})
    public void testAddToProject() {
        edao.addToProject(new_e, p, "position", null);
        Assert.assertNotNull(pdao.getEmployeeAppointedAt(p, new_e));
    }

    @Test(dependsOnMethods = {"testInsert", "testMalformedChangePositionInProject"})
    public void testAddToProjectWithDate() {
        edao.addToProject(e3, p, "position", new Date());
        Assert.assertNotNull(pdao.getEmployeeAppointedAt(p, new_e));
    }

    @Test(dependsOnMethods = {"testAddToProject"})
    public void testMalformedAddToProject() {
        try {
            edao.addToProject(new_e, p, "position", null);
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testAddToProject"})
    public void testChangePositionInProject() {
        edao.changePositionInProject(new_e, p, "newposition");
        Assert.assertEquals(pdao.getEmployeePosition(p, new_e), "newposition");
    }

    @Test(dependsOnMethods = {"testChangePositionInProject"})
    public void testRemoveFromProject() {
        Assert.assertNull(pdao.getEmployeeQuitAt(p, new_e));
        edao.removeFromProject(new_e, p, null);
        Assert.assertNotNull(pdao.getEmployeeQuitAt(p, new_e));
    }

    @Test(dependsOnMethods = {"testChangePositionInProject"})
    public void testRemoveFromProjectWithDate() {
        Assert.assertNull(pdao.getEmployeeQuitAt(p, e2));
        edao.removeFromProject(e2, p, new Date());
        Assert.assertNotNull(pdao.getEmployeeQuitAt(p, e2));
    }

    @Test(dependsOnMethods = {"testRemoveFromProject"})
    public void testMalformedRemoveFromProject() {
        try {
            edao.removeFromProject(new_e, p, null);
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testRemoveFromProject"})
    public void testMalformedRemoveFromProject2() {
        try {
            edao.removeFromProject(e2, p, null);
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testRemoveFromProject"})
    public void testMalformedChangePositionInProject2() {
        try {
            edao.changePositionInProject(new_e, p, "newposition");
        } catch (MalformedRequestException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test(dependsOnMethods = {"testUserUpdate", "testAdminUpdate", "testMalformedRemoveFromProject", "testMalformedChangePositionInProject2"})
    public void testDelete() {
        edao.delete(new_e);
        Employees check = edao.getById(new_e.getId());
        Assert.assertNull(check);
    }

    @Test
    public void testGetProjects() {
        List<Projects> lp = edao.getProjects(e1);
        Assert.assertEquals(lp.size(), 2);
        for (Projects p : lp) {
            Assert.assertTrue(p.getId() == 1 || p.getId() == 2);
        }
    }

    @Test
    public void testGetOwnPayouts() {
        List<Payouts> lp = edao.getPayouts(e3);
        Assert.assertEquals(lp.size(), 2);
        Assert.assertEquals(lp.get(0).getId(), 5);
        Assert.assertEquals(lp.get(1).getId(), 6);
    }

    @Test
    public void testGetPayoutsByAdmin() {
        List<Payouts> lp = edao.getPayouts(e3);
        Assert.assertEquals(lp.size(), 2);
        Assert.assertEquals(lp.get(0).getId(), 5);
        Assert.assertEquals(lp.get(1).getId(), 6);
    }

    @Test
    public void testGetPayoutsUnauthorized() {
        try{
            edao.getPayouts(e1);
        } catch (ActionNotAllowedException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }
}
