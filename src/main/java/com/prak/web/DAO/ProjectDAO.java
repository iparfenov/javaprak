package com.prak.web.DAO;

import com.prak.web.entities.Employees;
import com.prak.web.entities.Employees_projects;
import com.prak.web.entities.Projects;
import com.prak.web.exceptions.MalformedRequestException;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends CommonDAO<Projects> {

    public ProjectDAO() {
        super(Projects.class);
    }

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
            throw new MalformedRequestException("Служащий не участвует в этом проекте");
        }
        return ep.getPosition();
    }

    public Timestamp getEmployeeAppointedAt(Projects p, Employees e) {
        Employees_projects ep = s.createSelectionQuery(
                        "FROM Employees_projects WHERE project.id = :pid AND employee.id = :eid", Employees_projects.class)
                .setParameter("pid", p.getId()).setParameter("eid", e.getId())
                .getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Служащий не участвует в этом проекте");
        }
        return ep.getAppointed_at();
    }

    public Timestamp getEmployeeQuitAt(Projects p, Employees e) {
        Employees_projects ep = s.createSelectionQuery(
                        "FROM Employees_projects WHERE project.id = :pid AND employee.id = :eid", Employees_projects.class)
                .setParameter("pid", p.getId()).setParameter("eid", e.getId())
                .getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Служащий не участвует в этом проекте");
        }
        return ep.getQuit_at();
    }

    public void create(Projects p) {
        if (p.getName().isEmpty()) {
            throw new MalformedRequestException("Проект должен иметь название");
        }
        if (p.getStart() == null) {
            p.setStart(new Timestamp(System.currentTimeMillis()));
        }
        super.insert(p);
    }


    public void close(Projects p) {
        if (p.getEnd() != null) {
            throw new MalformedRequestException("Проект уже завершен!");
        }
        p.setEnd(new Timestamp(System.currentTimeMillis()));
        super.update(p);
    }

}
