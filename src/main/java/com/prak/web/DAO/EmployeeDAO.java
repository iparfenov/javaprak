package com.prak.web.DAO;

import com.prak.web.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends DAO<Employees> {
    public EmployeeDAO(Session s) {
        super(s, Employees.class);
    }

    public List<Projects> getProjects(Employees e) {
        int id = e.getId();
        List<Projects> res = new ArrayList<>();
        List<Employees_projects> eps = this.s.createSelectionQuery("from Employees_projects where employee.id = :id",
                    Employees_projects.class).setParameter("id", id).getResultList();
        for (Employees_projects ep : eps) {
            res.addLast(ep.getProject());
        }
        return res;
    }

    public List<Payouts> getPayouts(Employees e) {
        return this.s.createSelectionQuery("from Payouts where employee.id = :id", Payouts.class)
                .setParameter("id", e.getId()).getResultList();
    }

    public List<Employees> search(String req) {
        String reg = "%" + req + "%";
        return this.s.createSelectionQuery("from Employees where name like :reg", Employees.class)
                .setParameter("reg", reg).getResultList();
    }

}
