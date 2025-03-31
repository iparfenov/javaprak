package com.prak.web.DAO;

import com.prak.web.Bonuses;
import com.prak.web.Employees;
import com.prak.web.exceptions.ActionNotAllowedException;
import org.hibernate.Session;

public class BonusesDAO extends CommonDAO<Bonuses> {
    public BonusesDAO(Session s) {
        super(s, Bonuses.class);
    }

    public Bonuses update(Bonuses bonuses, Employees requester) {
        if (requester.getIs_admin()) {
            return super.update(bonuses);
        } else {
            throw new ActionNotAllowedException();
        }
    }

    public void insert(Bonuses bonuses, Employees requester) {
        if (requester.getIs_admin()) {
            super.insert(bonuses);
        } else {
            throw new ActionNotAllowedException();
        }
    }
}
