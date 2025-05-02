package com.prak.web.components;

import com.prak.web.DAO.BonusesDAO;
import com.prak.web.DAO.EmployeeDAO;
import com.prak.web.DAO.PayoutsDAO;
import com.prak.web.DAO.ProjectDAO;
import com.prak.web.entities.Employees;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class DAOsBean {
    private final Session session;
    private final EmployeeDAO employeeDAO;
    private final ProjectDAO projectDAO;
    private final BonusesDAO bonusesDAO;
    private final PayoutsDAO payoutsDAO;
    private Employees employee = null;

    public DAOsBean(SessionFactoryBean sessionfactory) {
        this.session = sessionfactory.getSession();
        this.employeeDAO = new EmployeeDAO(session);
        this.projectDAO = new ProjectDAO(session);
        this.bonusesDAO = new BonusesDAO(session);
        this.payoutsDAO = new PayoutsDAO(session);
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public BonusesDAO getBonusesDAO() {
        return bonusesDAO;
    }

    public PayoutsDAO getPayoutsDAO() { return payoutsDAO; }

}
