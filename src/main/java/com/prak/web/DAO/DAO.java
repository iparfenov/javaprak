package com.prak.web.DAO;

import org.hibernate.Session;

import java.util.List;

public class DAO<T> {
    protected final Session s;
    protected final Class<T> c;

    public DAO(Session s, Class<T> c) {
        this.c = c;
        this.s = s;
    }

    public T getById(int id) {
        return s.find(c, id);
    }

    public List<T> getAll() {
        return s.createSelectionQuery("FROM " + c.getName(), c).getResultList();
    }

    public void insert(T t) {
        s.beginTransaction();
        s.persist(t);
        s.getTransaction().commit();
    }

    public void update(T t) {
        s.beginTransaction();
        s.merge(t);
        s.getTransaction().commit();
    }

    public void delete(T t) {
        s.beginTransaction();
        s.remove(t);
        s.getTransaction().commit();
    }
}
