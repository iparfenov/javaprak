package com.prak.web.DAO;

import com.prak.web.Employees;
import com.prak.web.Employees_projects;
import com.prak.web.Projects;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends DAO<Projects> {

    public ProjectDAO(Session s) {
        super(s, Projects.class);
    }

    public List<Employees> getEmployees(Projects p) {
        int id = p.getId();
        List<Employees> res = new ArrayList<>();
        List<Employees_projects> eps = this.s.createSelectionQuery("from Employees_projects where project.id = :id",
                Employees_projects.class).setParameter("id", id).setReadOnly(true).getResultList();
        for (Employees_projects ep : eps) {
            res.addLast(ep.getEmployee());
        }
        return res;
    }
}
