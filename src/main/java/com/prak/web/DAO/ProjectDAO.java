package com.prak.web.DAO;

import com.prak.web.Employees;
import com.prak.web.Employees_projects;
import com.prak.web.Projects;
import com.prak.web.exceptions.ActionNotAllowedException;
import com.prak.web.exceptions.MalformedRequestException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDAO extends CommonDAO<Projects> {

    public ProjectDAO(Session s) {
        super(s, Projects.class);
    }

    public List<Employees> getEmployees(Projects p) {
        List<Employees> res = new ArrayList<>();
        List<Employees_projects> eps = this.s.createSelectionQuery(
                "from Employees_projects where project.id = :id", Employees_projects.class)
                .setParameter("id", p.getId()).getResultList();
        for (Employees_projects ep : eps) {
            res.addLast(ep.getEmployee());
        }
        return res;
    }

    public String getEmployeePosition(Projects p, Employees e) {
        Employees_projects ep = s.createSelectionQuery(
                "FROM Employees_projects WHERE project.id = :pid AND employee.id = :eid", Employees_projects.class)
                .setParameter("pid", p.getId()).setParameter("eid", e.getId())
                .getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Employee is not in this project");
        }
        return ep.getPosition();
    }

    public Date getEmployeeAppointedAt(Projects p, Employees e) {
        Employees_projects ep = s.createSelectionQuery(
                        "FROM Employees_projects WHERE project.id = :pid AND employee.id = :eid", Employees_projects.class)
                .setParameter("pid", p.getId()).setParameter("eid", e.getId())
                .getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Employee is not in this project");
        }
        return ep.getAppointed_at();
    }

    public Date getEmployeeQuitAt(Projects p, Employees e) {
        Employees_projects ep = s.createSelectionQuery(
                        "FROM Employees_projects WHERE project.id = :pid AND employee.id = :eid", Employees_projects.class)
                .setParameter("pid", p.getId()).setParameter("eid", e.getId())
                .getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Employee is not in this project");
        }
        return ep.getQuit_at();
    }

    public void create(Projects p, Employees requester) {
        checkPermissions(requester);
        if (p.getStart() == null) {
            p.setStart(new Date());
        }
        super.insert(p);
    }

    public Projects update(Projects p, Employees requester) {
        checkPermissions(requester);
        return super.update(p);
    }

    public void close(Projects p, Employees requester) {
        checkPermissions(requester);
        if (p.getEnd() != null) {
            throw new MalformedRequestException("The project has already ended!");
        }
        p.setEnd(new Date());
        super.update(p);
    }

}
