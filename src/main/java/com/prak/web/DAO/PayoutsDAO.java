package com.prak.web.DAO;

import com.prak.web.Employees;
import com.prak.web.Payouts;
import com.prak.web.exceptions.ActionNotAllowedException;
import org.hibernate.Session;

import java.util.Date;

public class PayoutsDAO extends CommonDAO<Payouts> {
    public PayoutsDAO(Session s) {
        super(s, Payouts.class);
    }

    public void insert(Payouts payout, Employees requester) {
        checkPermissions(requester);
        if (payout.getPaid_at() == null) {
            payout.setPaid_at(new Date());
        }
        super.insert(payout);
    }

}
