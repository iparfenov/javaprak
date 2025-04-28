package com.prak.web.entities;

import java.util.Date;

public class Employees_projects {
    private Projects project;
    private Employees employee;
    private String position;
    private Date appointed_at;
    private Date quit_at;

    public Projects getProject() { return project; }
    public void setProject(Projects project) { this.project = project; }

    public Employees getEmployee() { return employee; }
    public void setEmployee(Employees employee) { this.employee = employee; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Date getAppointed_at() { return appointed_at; }
    public void setAppointed_at(Date appointed_at) { this.appointed_at = appointed_at; }

    public Date getQuit_at() { return quit_at; }
    public void setQuit_at(Date quit_at) { this.quit_at = quit_at; }

}
