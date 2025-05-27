package com.prak.web.DAO;

import com.prak.web.entities.*;
import com.prak.web.exceptions.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends CommonDAO<Employees> {

    final CommonDAO<Employee_history> ehdao;

    public EmployeeDAO() {
        super(Employees.class);
        ehdao = new CommonDAO<>(this.s, Employee_history.class);
    }

    public EmployeeDAO(Session s) {
        super(s, Employees.class);
        ehdao = new CommonDAO<>(s, Employee_history.class);
    }

    public Employee_history getHistory(Employees e) {
        return ehdao.getById(e.getId());
    }

    public void promote(Employees e, String position, Timestamp promoted_at) {
        if (promoted_at == null) {
            promoted_at = new Timestamp(System.currentTimeMillis());
        }
        Employee_history eh = ehdao.getById(e.getId());
        eh.getPositions().add(position);
        eh.getPromoted_at().add(promoted_at);
        e.setPosition(position);
        this.update(e);
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

    public void addToProject(Employees e, Projects p, String position, Timestamp appointed_at) {
        CommonDAO<Employees_projects> epdao = new CommonDAO<>(s, Employees_projects.class);
        Employees_projects ep = s.createSelectionQuery(
                "FROM Employees_projects WHERE employee.id = :eid AND project.id = :pid", Employees_projects.class)
                .setParameter("eid", e.getId()).setParameter("pid", p.getId())
                .getSingleResultOrNull();
        if (ep != null) {
            throw new MalformedRequestException("Служащий уже участвует в этом проекте!");
        }
        ep = new Employees_projects();
        ep.setEmployee(e);
        ep.setProject(p);
        ep.setPosition(position);
        if (appointed_at == null) {
            appointed_at = new Timestamp(System.currentTimeMillis());
        }
        ep.setAppointed_at(appointed_at);
        epdao.update(ep);
    }

    public void changePositionInProject(Employees e, Projects p, String position) {
        CommonDAO<Employees_projects> epdao = new CommonDAO<>(s, Employees_projects.class);
        Employees_projects ep = s.createSelectionQuery(
                "FROM Employees_projects WHERE employee.id = :eid AND project.id = :pid", Employees_projects.class)
                .setParameter("eid", e.getId()).setParameter("pid", p.getId()).getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Служащий не участвует в этом проекте");
        }
        if (ep.getQuit_at() != null) {
            throw new MalformedRequestException("Служащий уже вышел из этого проекта");
        }
        ep.setPosition(position);
        epdao.update(ep);
    }

    public void removeFromProject(Employees e, Projects p, Timestamp removed_at) {
        CommonDAO<Employees_projects> epdao = new CommonDAO<>(s, Employees_projects.class);
        Employees_projects ep = s.createSelectionQuery(
                "FROM Employees_projects WHERE employee.id = :eid AND project.id = :pid", Employees_projects.class)
                .setParameter("eid", e.getId()).setParameter("pid", p.getId()).getSingleResultOrNull();
        if (ep == null) {
            throw new MalformedRequestException("Служащий не участвует в этом проекте");
        }
        if (ep.getQuit_at() != null) {
            throw new MalformedRequestException("Служащий уже вышел из этого проекта");
        }
        if (removed_at == null) {
            removed_at = new Timestamp(System.currentTimeMillis());
        }
        ep.setQuit_at(removed_at);

        epdao.update(ep);
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

    public void insert(Employees new_e) {
        if (new_e.getWorking_since() == null) {
            new_e.setWorking_since(new Timestamp(System.currentTimeMillis()));
        }
        Employee_history history = new Employee_history();
        history.setEmployee(new_e);
        List<String> positions = new ArrayList<>();
        List<Timestamp> dates = new ArrayList<>();
        Timestamp t = new Timestamp(new_e.getWorking_since().getTime());

        positions.add(new_e.getPosition());
        history.setPositions(positions);

        dates.add(t);
        history.setPromoted_at(dates);

        ehdao.insert(history);

        super.insert(new_e);
    }

    public void delete(Employees e) {
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

    public Employees getByLogin(String login) {
        return s.createSelectionQuery("FROM Employees WHERE login = :login", Employees.class)
                .setParameter("login", login).getSingleResultOrNull();
    }

}
