package com.prak.web;

import com.prak.web.DAO.BonusesDAO;
import com.prak.web.DAO.EmployeeDAO;
import com.prak.web.entities.Bonuses;
import com.prak.web.entities.Employees;
import com.prak.web.exceptions.ActionNotAllowedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.Assert;
import org.testng.annotations.*;

public class BonusesDAO_test {
    SessionFactory sf = new Configuration().configure().buildSessionFactory();
    Session s = sf.openSession();
    BonusesDAO dao = new BonusesDAO(s);

    Bonuses new_b = new Bonuses();
    Employees e1;
    Employees e3;

    @BeforeClass
    public void setUp() {
        new_b.setName("bonus");
        new_b.setPercentage(50);

        EmployeeDAO edao = new EmployeeDAO(s);
        e1 = edao.getById(1);
        e3 = edao.getById(3);
    }

    @Test
    public void testAuthorizedInsert() {
        dao.insert(new_b, e1);
        Assert.assertNotNull(dao.getById(new_b.getId()));
    }

    @Test
    public void testAuthorizedUpdate() {
        new_b.setPercentage(10);
        new_b = dao.update(new_b, e1);
        Assert.assertEquals(new_b.getPercentage(), 10);
    }

    @Test
    public void testUnauthorizedInsert() {
        try {
            dao.insert(new_b, e3);
        } catch (ActionNotAllowedException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testUnauthorizedUpdate() {
        try {
            dao.update(new_b, e3);
        } catch (ActionNotAllowedException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }
}
