package com.prak.web;

import com.prak.web.DAO.EmployeeDAO;
import com.prak.web.DAO.PayoutsDAO;
import com.prak.web.entities.Employees;
import com.prak.web.entities.Payouts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.*;
import org.testng.Assert;

import java.util.Date;

public class PayoutsDAO_test {

    SessionFactory sf = new Configuration().configure().buildSessionFactory();
    Session s = sf.openSession();
    PayoutsDAO dao;

    @BeforeClass
    public void setUp() {
        dao = new PayoutsDAO(s);
    }

    @Test
    public void testInsertPayout() {
        EmployeeDAO edao = new EmployeeDAO(s);
        Employees e1 = edao.getById(1);
        Payouts p = new Payouts();
        p.setAmount(10000);
        p.setEmployee(e1);
        dao.insert(p, e1);
        Payouts check = edao.getPayouts(e1).getLast();
        Assert.assertEquals(check.getId(), p.getId());
        Assert.assertNotNull(check.getPaid_at());
    }

    @Test
    public void testInsertPayout2() {
        EmployeeDAO edao = new EmployeeDAO(s);
        Employees e1 = edao.getById(1);
        Payouts p = new Payouts();
        p.setAmount(10000);
        p.setEmployee(e1);
        p.setPaid_at(new Date());
        dao.insert(p, e1);
        Payouts check = edao.getPayouts(e1).getLast();
        Assert.assertEquals(check.getId(), p.getId());
        Assert.assertNotNull(check.getPaid_at());
    }
}
