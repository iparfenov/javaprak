package com.prak.web.entities;

import java.util.Date;
import java.util.List;

public class Employee_history {
    private int employee_id;
    private Employees employee;
    private List<String> positions;
    private List<Date> promoted_at;

    public int getEmployee_id() { return employee_id; }
    public void setEmployee_id(int employee_id) { this.employee_id = employee_id; }

    public Employees getEmployee() { return employee; }
    public void setEmployee(Employees employee) { this.employee = employee; }

    public List<String> getPositions() { return positions; }
    public void setPositions(List<String> positions) { this.positions = positions; }

    public List<Date> getPromoted_at() { return promoted_at; }
    public void setPromoted_at(List<Date> promoted_at) { this.promoted_at = promoted_at; }

}
