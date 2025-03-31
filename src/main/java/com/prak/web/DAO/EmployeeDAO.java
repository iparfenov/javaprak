package com.prak.web.DAO;

import com.prak.web.*;
import com.prak.web.exceptions.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDAO extends CommonDAO<Employees> {

    final CommonDAO<Employee_history> ehdao;

    public EmployeeDAO(Session s) {
        super(s, Employees.class);
        ehdao = new CommonDAO<>(s, Employee_history.class);
    }

    public Employee_history getHistory(Employees e) {
        return ehdao.getById(e.getId());
    }

    public void promote(Employees e, String position, Date promoted_at, Employees requester) {
        this.checkPermissions(requester);
        if (promoted_at == null) {
            promoted_at = new Date();
        }
        Employee_history eh = ehdao.getById(e.getId());
        eh.getPositions().add(position);
        eh.getPromoted_at().add(promoted_at);
        ehdao.update(eh);
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

    public void addToProject(Employees e, Projects p, String position, Date appointed_at, Employees requester) {
        this.checkPermissions(requester);
        CommonDAO<Employees_projects> epdao = new CommonDAO<>(s, Employees_projects.class);
        Employees_projects ep = s.createSelectionQuery(
                "FROM Employees_projects WHERE employee.id = :eid AND project.id = :pid", Employees_projects.class)
                .setParameter("eid", e.getId()).setParameter("pid", p.getId())
                .getSingleResultOrNull();
        if (ep != null) {
            throw new MalformedRequestException("Employee is already in this project!");
        }
        ep = new Employees_projects();
        ep.setEmployee(e);
        ep.setProject(p);
        ep.setPosition(position);
        if (appointed_at == null) {
            appointed_at = new Date();
        }
        ep.setAppointed_at(appointed_at);
        epdao.update(ep);
    }

    public void changePositionInProject(Employees e, Projects p, String position, Employees requester) {
        this.checkPermissions(requester);
        CommonDAO<Employees_projects> epdao = new CommonDAO<>(s, Employees_projects.class);
        Employees_projects ep = s.createSelectionQuery(
                "FROM Employees_projects WHERE employee.id = :eid AND project.id = :pid", Employees_projects.class)
                .setParameter("eid", e.getId()).setParameter("pid", p.getId()).getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Employee is not in this project");
        }
        if (ep.getQuit_at() != null) {
            throw new MalformedRequestException("Employee has already left this project");
        }
        ep.setPosition(position);
        epdao.update(ep);
    }

    public void removeFromProject(Employees e, Projects p, Date removed_at, Employees requester) {
        this.checkPermissions(requester);
        CommonDAO<Employees_projects> epdao = new CommonDAO<>(s, Employees_projects.class);
        Employees_projects ep = s.createSelectionQuery(
                "FROM Employees_projects WHERE employee.id = :eid AND project.id = :pid", Employees_projects.class)
                .setParameter("eid", e.getId()).setParameter("pid", p.getId()).getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Employee is not in this project");
        }
        if (ep.getQuit_at() != null) {
            throw new MalformedRequestException("Employee has already left this project");
        }
        if (removed_at == null) {
            removed_at = new Date();
        }
        ep.setQuit_at(removed_at);
        if (p.getHead() == e) {
            p.setHead(null);
        }
    }

    public List<Payouts> getPayouts(Employees e, Employees requester) {
        if (requester.getIs_admin() || requester.getId() == e.getId()) {
            return this.s.createSelectionQuery("from Payouts where employee.id = :id", Payouts.class)
                    .setParameter("id", e.getId()).getResultList();
        } else {
            throw new ActionNotAllowedException();
        }
    }

    public List<Employees> search(String req) {
        String reg = "%" + req + "%";
        return this.s.createSelectionQuery("from Employees where name like :reg", Employees.class)
                .setParameter("reg", reg).getResultList();
    }

    public void insert(Employees new_e, Employees requester) {
        this.checkPermissions(requester);
        if (new_e.getWorking_since() == null) {
            new_e.setWorking_since(new Date());
        }
        Employee_history history = new Employee_history();
        history.setEmployee(new_e);
        List<String> positions = new ArrayList<>();
        List<Date> dates = new ArrayList<>();

        positions.add(new_e.getPosition());
        history.setPositions(positions);

        dates.add(new_e.getWorking_since());
        history.setPromoted_at(dates);

        ehdao.insert(history);

        super.insert(new_e);
    }

    public Employees update(Employees e, Employees requester) {
        if (requester.getIs_admin() || requester.getId() == e.getId()) {
            return super.update(e);
        } else {
            throw new ActionNotAllowedException();
        }
    }

    public void delete(Employees e, Employees requester) {
        this.checkPermissions(requester);
        ehdao.delete(ehdao.getById(e.getId()));
        CommonDAO<Employees_projects> edao = new CommonDAO<>(s, Employees_projects.class);
        List<Employees_projects> lep = s.createSelectionQuery
                ("FROM Employees_projects WHERE employee.id = :eid", Employees_projects.class)
                .setParameter("eid", e.getId()).getResultList();
        for (Employees_projects ep : lep) {
            edao.delete(ep);
        }
        super.delete(e);
    }

}
