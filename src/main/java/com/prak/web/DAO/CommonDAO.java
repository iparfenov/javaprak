package com.prak.web.DAO;

import com.prak.web.Employees;
import com.prak.web.exceptions.ActionNotAllowedException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CommonDAO<T> {
    protected final Session s;
    protected final Class<T> c;

    public CommonDAO(Session s, Class<T> c) {
        this.c = c;
        this.s = s;
    }

    public void checkPermissions(Employees e) {
        if (!e.getIs_admin()) throw new ActionNotAllowedException();
    }

    public T getById(int id) {
        return s.find(c, id);
    }

    public List<T> getAll() {
        return s.createSelectionQuery("FROM " + c.getName(), c).getResultList();
    }

    public void insert(T t) {
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.persist(t);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public T update(T t) {
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            T res = s.merge(t);
            tx.commit();
            return res;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void delete(T t) {
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.remove(t);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
