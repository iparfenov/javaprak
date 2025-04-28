package com.prak.web.components;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.hibernate.cfg.Configuration;

@Component
public class SessionFactoryBean {
    private final SessionFactory sessionFactory;

    public SessionFactoryBean() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getSessionFactory() { return sessionFactory; }

    public Session getSession() { return sessionFactory.openSession(); }

}
