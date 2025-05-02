package com.prak.web.entities;

import java.sql.Timestamp;

public class Projects {
    private int id;
    private String name;
    private Timestamp start;
    private Timestamp end;
    private Employees head;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Timestamp getStart() { return start; }
    public void setStart(Timestamp start) { this.start = start; }

    public Timestamp getEnd() { return end; }
    public void setEnd(Timestamp end) { this.end = end; }

    public Employees getHead() { return head; }
    public void setHead(Employees head) { this.head = head; }

}