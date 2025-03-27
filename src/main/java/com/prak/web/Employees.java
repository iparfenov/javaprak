package com.prak.web;

import java.util.Date;
import java.util.Set;

public class Employees {
    private int id;
    private String name;
    private String address;
    private Date birthday;
    private String education;
    private Date working_since;
    private String position;
    private String email;
    private Boolean is_admin;
    private String login;
    private String password;
    private Set<Employees_projects> projects;
    private Employee_history history;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public Date getWorking_since() { return working_since; }
    public void setWorking_since(Date working_since) { this.working_since = working_since; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getIs_admin() { return is_admin; }
    public void setIs_admin(Boolean is_admin) { this.is_admin = is_admin; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Employees_projects> getProjects() { return projects; }
    public void setProjects(Set<Employees_projects> projects) { this.projects = projects; }

    public Employee_history getHistory() { return history; }
    public void setHistory(Employee_history history) { this.history = history; }
}
