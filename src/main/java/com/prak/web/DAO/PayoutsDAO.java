package com.prak.web.DAO;

import com.prak.web.entities.Employees;
import com.prak.web.entities.Payouts;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.Date;

public class PayoutsDAO extends CommonDAO<Payouts> {
    public PayoutsDAO(Session s) {
        super(s, Payouts.class);
    }

    public void insert(Payouts payout) {
        if (payout.getPaid_at() == null) {
            payout.setPaid_at(new Timestamp(System.currentTimeMillis()));
        }
        super.insert(payout);
    }

}
