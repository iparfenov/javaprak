package com.prak.web.entities;

import java.util.Date;

public class Projects {
    private int id;
    private String name;
    private Date start;
    private Date end;
    private Employees head;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getStart() { return start; }
    public void setStart(Date start) { this.start = start; }

    public Date getEnd() { return end; }
    public void setEnd(Date end) { this.end = end; }

    public Employees getHead() { return head; }
    public void setHead(Employees head) { this.head = head; }

}