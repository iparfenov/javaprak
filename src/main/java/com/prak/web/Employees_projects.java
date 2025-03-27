package com.prak.web;

import java.util.Date;

public class Employees_projects {
//    private int id;
    private Projects project;
    private int project_id;
    private Employees employee;
    private int employee_id;
    private String position;
    private Date appointed_at;
    private Date quit_at;

    public Projects getProject() { return project; }
    public void setProject(Projects project) { this.project = project; }

    public int getProject_id() { return project_id; }
    public void setProject_id(int project_id) { this.project_id = project_id; }

    public Employees getEmployee() { return employee; }
    public void setEmployee(Employees employee) { this.employee = employee; }

    public int getEmployee_id() { return employee_id; }
    public void setEmployee_id(int employee_id) { this.employee_id = employee_id; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Date getAppointed_at() { return appointed_at; }
    public void setAppointed_at(Date appointed_at) { this.appointed_at = appointed_at; }

    public Date getQuit_at() { return quit_at; }
    public void setQuit_at(Date quit_at) { this.quit_at = quit_at; }

}
