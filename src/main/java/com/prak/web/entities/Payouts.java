package com.prak.web.entities;

import java.util.Date;

public class Payouts {
    private int id;
    private float amount;
    private Date paid_at;
    private Bonuses bonus;
    private Employees employee;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount; }

    public Date getPaid_at() { return paid_at; }
    public void setPaid_at(Date paid_at) { this.paid_at = paid_at; }

    public Bonuses getBonus() { return bonus; }
    public void setBonus(Bonuses bonus) { this.bonus = bonus; }

    public Employees getEmployee() { return employee; }
    public void setEmployee(Employees employee) { this.employee = employee; }

}
